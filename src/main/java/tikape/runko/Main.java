package tikape.runko;

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


public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:keskustelualue.db");
        database.init();

        AlueDao alueDao = new AlueDao(database);

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

        get("/alueet/Ohjelmointi", (req, res) -> {
            KeskusteluDao kesk = new KeskusteluDao(database);
            
            HashMap map = new HashMap<>();
            map.put("alue", alueDao.findOne("Ohjelmointi"));
            map.put("keskustelut", kesk.findAllIn("Ohjelmointi"));

            return new ModelAndView(map, "ohjelmointi");
        }, new ThymeleafTemplateEngine());
    }
}
