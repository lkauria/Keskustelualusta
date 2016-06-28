package tikape.runko;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.AlueDao;
import tikape.runko.database.KeskusteluDao;
import tikape.runko.database.*;
import tikape.runko.domain.Keskustelu;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:keskustelualue.db");

        AlueDao alueDao = new AlueDao(database);
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.alueListaus());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/alue/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alue", alueDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("keskustelut", keskusteluDao.keskusteluListaus(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());

        get("/keskustelu/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Keskustelu k = keskusteluDao.findOne(Integer.parseInt(req.params("id")));
            map.put("alue", alueDao.findOne(k.getAlue().getId()));
            map.put("keskustelu", keskusteluDao.findOne(k.getId()));
            map.put("viestit", viestiDao.findAllIn(k.getId()));

            return new ModelAndView(map, "keskustelu");
        }, new ThymeleafTemplateEngine());

        post("/", (req, res) -> {
            String alue = req.queryParams("alue");
            Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Alue (nimi) VALUES (?)");
            statement.setString(1, alue);
            statement.execute();
            statement.close();
            connection.close();
            res.redirect("/");
            return "";
        });

        post("/alue", (req, res) -> {
            Integer alue_id = Integer.parseInt(req.queryParams("alue_id"));
            String aihe = req.queryParams("aihe");
            String viesti = req.queryParams("viesti");
            String nimimerkki = req.queryParams("nimimerkki");
            Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Keskustelu (aihe, alue) VALUES (?, ?)");
            statement.setString(1, aihe);
            statement.setInt(2, alue_id);
            statement.execute();
            statement.close();
            Integer keskustelu_id = keskusteluDao.haeSuurinId();
            statement = connection.prepareStatement("INSERT INTO Viesti (aika, sisalto, nimimerkki, keskustelu) VALUES (" + System.currentTimeMillis() + ", ?, ?, ?)");
            statement.setString(1, viesti);
            statement.setString(2, nimimerkki);
            statement.setInt(3, keskustelu_id);
            statement.execute();
            statement.close();
            connection.close();
            res.redirect("/alue/" + alue_id);
            return "";
        });

        post("/keskustelu", (req, res) -> {
            Integer keskustelu_id = Integer.parseInt(req.queryParams("keskustelu_id"));
            String viesti = req.queryParams("viesti");
            String nimimerkki = req.queryParams("nimimerkki");
            Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Viesti (aika, sisalto, nimimerkki, keskustelu) VALUES (" + System.currentTimeMillis() + ", ?, ?, ?)");
            statement.setString(1, viesti);
            statement.setString(2, nimimerkki);
            statement.setInt(3, keskustelu_id);
            statement.execute();
            statement.close();
            connection.close();
            res.redirect("/keskustelu/" + keskustelu_id);
            return "";
        });

    }
}
