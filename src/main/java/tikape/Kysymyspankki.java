package tikape;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.database.Database;
import tikape.database.KurssiDao;
import tikape.database.KysymysDao;
import tikape.database.VastausvaihtoehtoDao;

public class Kysymyspankki {

    public static void main(String[] args) throws Exception {
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        List<Kysymys> kysymykset = new ArrayList<>();

        Database database = new Database("org.sqlite.JDBC", "jdbc:sqlite:tasks.db");

        KysymysDao kysymysDao = new KysymysDao(database);
		VastausvaihtoehtoDao vastausvaihtoehtoDao = new VastausvaihtoehtoDao(database);
		KurssiDao kurssiDao = new KurssiDao(database);
		
        Spark.get("/kysymykset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kysymykset", kysymysDao.findAll());
            return new ModelAndView(map, "kysymykset");
        }, new ThymeleafTemplateEngine());

        Spark.get("/kysymykset/:id", (Request req, Response res) -> {
            HashMap map = new HashMap<>();
			int id = Integer.parseInt(req.params(":id"));
            map.put("kysymys", kysymysDao.findOne(new Kysymys(id, null, null, null)));
            return new ModelAndView(map, "kysymys");
        }, new ThymeleafTemplateEngine());

        Spark.get("/kysymykset/:id/vastausvaihtoehdot", (req, res) -> {
            HashMap map = new HashMap<>();
			String id = req.params(":id");
            map.put("kysymys", kysymysDao.findOne(
				new Kysymys(Integer.parseInt(id), null, null, null)));
            return new ModelAndView(map, "kysymys");
        }, new ThymeleafTemplateEngine());

        Spark.post("/kysymykset/:id/vastausvaihtoehdot", (req, res) -> {
            String vastausteksti = req.queryParams("vastausteksti");
			String oikein = req.queryParams("oikein");
			String id = req.params(":id");
			Kysymys kysymys = kysymysDao.findOne(
				new Kysymys(Integer.parseInt(id), null, null, null));
			vastausvaihtoehtoDao.saveOrUpdate(new Vastausvaihtoehto(-1, 
				kysymys.getId(), vastausteksti, Boolean.parseBoolean(oikein)));
		    res.redirect("/kysymykset/:id/vastausvaihtoehdot");
		    return "OK";
        });

        Spark.post("/kysymykset", (req, res) -> {
            String kysymysteksti = req.queryParams("kysymysteksti");
            String aihe = req.queryParams("aihe");
            String kurssinimi = req.queryParams("kurssi");
			Kurssi kurssi = kurssiDao.findOne(new Kurssi(kurssinimi, -1));
			kysymysDao.saveOrUpdate(new Kysymys(-1, kysymysteksti, aihe, kurssi));
		    res.redirect("/kysymykset");
		    return "OK";
        });

        Spark.post("/kysymykset/:id/delete", (req, res) -> {
			String id = req.params(":id");
		    kysymysDao.delete(Integer.parseInt(id));
		    res.redirect("/kysymykset");
		    return "OK";
        });
    }

}
