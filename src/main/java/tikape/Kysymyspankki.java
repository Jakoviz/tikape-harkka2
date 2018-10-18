package tikape;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
        
        Database database = new Database("org.sqlite.JDBC", "jdbc:sqlite:tasks.db");

        KysymysDao kysymysDao = new KysymysDao(database);
		VastausvaihtoehtoDao vastausvaihtoehtoDao = new VastausvaihtoehtoDao(database);
		KurssiDao kurssiDao = new KurssiDao(database);

        Spark.get("/", (req, res) -> {
	    res.redirect("/kysymykset");
	    return null;
        }, new ThymeleafTemplateEngine());

        Spark.get("/kysymykset", (req, res) -> {
            HashMap map = new HashMap<>();
			List<Kurssi> kaikkiKurssit = kurssiDao.findAll();
			List<Kurssi> naytettavatKurssit = new LinkedList<Kurssi>();
			for (Kurssi kurssi : kaikkiKurssit) {
				kurssi.setKysymykset(kysymysDao.findAll(kurssi.getId()));
				if (!kurssi.getKysymykset().isEmpty()) {
					naytettavatKurssit.add(kurssi);
				}
			}
            map.put("kurssit", naytettavatKurssit);
            return new ModelAndView(map, "kysymykset");
        }, new ThymeleafTemplateEngine());

        Spark.post("/kysymykset", (req, res) -> {
            String kysymysteksti = req.queryParams("kysymysteksti");
            String aihe = req.queryParams("aihe");
            String kurssinimi = req.queryParams("kurssi");
			Kurssi kurssi = kurssiDao.findOne(new Kurssi(kurssinimi, -1));
			if (kurssi == null) {
				kurssi = kurssiDao.saveOrUpdate(new Kurssi(kurssinimi, -1));
			}
			kysymysDao.saveOrUpdate(new Kysymys(-1, kysymysteksti, aihe,
				kurssiDao.findOne(kurssi)));
			res.redirect("/kysymykset");
			return "";
        });
		
		Spark.post("/kysymykset/:id/delete", (req, res) -> {
			String id = req.params(":id");
			kysymysDao.delete(Integer.parseInt(id));
			res.redirect("/kysymykset");
			return "";
		});

        Spark.get("/kysymykset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
			int id = Integer.parseInt(req.params(":id"));
			Kysymys kysymys = new Kysymys(id, null, null, null);
			kysymys.setVastausvaihtoehdot(vastausvaihtoehtoDao.findAll(id));
			Kurssi tamanKysymyksenKurssi = kurssiDao.findOne(kysymys);
			kysymys.setKurssi(tamanKysymyksenKurssi);
			map.put("kysymys", kysymys); 
            return new ModelAndView(map, "kysymys");
        }, new ThymeleafTemplateEngine());

        Spark.post("/kysymykset/:id", (req, res) -> {
            String vastausteksti = req.queryParams("vastausteksti");
			boolean oikein;
			if (req.queryParams("oikein").equals("on")) {
				oikein = true; 
			} else {
				oikein = false;
			}
			String id = req.params(":id");
			Kysymys kysymys = kysymysDao.findOne(
				new Kysymys(Integer.parseInt(id), null, null, null));
			vastausvaihtoehtoDao.saveOrUpdate(new Vastausvaihtoehto(-1, 
				kysymys.getId(), vastausteksti, oikein));
			res.redirect("/kysymykset/" + id);
			return "";
        });

		Spark.post("/kysymykset/:id/:id2/delete", (req, res) -> {
			String kysymysId = req.params(":id");
			String vastausvaihtoehtoId = req.params(":id2");
			vastausvaihtoehtoDao.delete(Integer.parseInt(vastausvaihtoehtoId));
			res.redirect("/kysymykset/" + kysymysId);
			return "";
		});

    }

}
