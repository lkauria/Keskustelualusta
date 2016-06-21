package tikape.runko;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.AlueDao;
import tikape.runko.database.*;
import tikape.runko.domain.*;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:keskustelualue.db");
        //database.init();
        Date d = new java.util.Date(); // Lisäsin date-olion millä saa Timestampin viestiin
        AlueDao alueDao = new AlueDao(database);
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/alueet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAll());

            return new ModelAndView(map, "alueet");
        }, new ThymeleafTemplateEngine());

        get("/alueet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alue", alueDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("keskustelut", keskusteluDao.findAllIn(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        post("/alueet/:id", (req, res) -> {
            //ei toimi
            return "Alue lisätty.";
        });

        get("/keskustelut/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelu", keskusteluDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("viestit", viestiDao.findAllIn(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());

        post("/keskustelut/:id", (req, res) -> {
            String sisalto = req.params("sisalto"); //teoriassa tän pitäisi hakea sieltä html sivulta tuo "sisalto" 
            Keskustelu k = keskusteluDao.findOne(Integer.parseInt(req.params("id"))); //id parsetetaan ja sillä etsitään oikea keskustelu
            int id = viestiDao.palautaSuurinId(); // katso viestidao luokka, tein tommosen purkkametodin
            Viesti v = new Viesti(id, k, new Timestamp(d.getTime()), sisalto); // uusi viesti
            
            Connection connection = database.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO Viesti (?, ?, ?, ?);");
            stmt.setInt(1, id);
            stmt.setTimestamp(2, new Timestamp(d.getTime()));
            stmt.setString(3, sisalto);
            stmt.setObject(4, k);
            
            stmt.execute();
            
            stmt.close();
            connection.close(); // Tein tällaisen härpäkkeen uuden viestin lähetystä varten
                                // toimivuudesta en tosiaan tiedä! :)
            return "Viesti lähetetty.";
        });
    }
}
