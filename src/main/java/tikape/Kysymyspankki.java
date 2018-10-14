package tikape;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.database.Database;
import tikape.database.KysymysDao;

public class Kysymyspankki {

    public static void main(String[] args) throws Exception {
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        List<Kysymys> kysymykset = new ArrayList<>();

        Database database = new Database("org.sqlite.JDBC", "jdbc:sqlite:tasks.db");

        KysymysDao kysymysDao = new KysymysDao(database);
        
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kysymykset", kysymysDao.findAll());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.post("/kysymykset", (req, res) -> {
            String kysymysteksti = req.queryParams("kysymysteksti");
            String aihe = req.queryParams("aihe");
            String kurssinimi = req.queryParams("kurssi");
            Kysymys kysymys = new Kysymys(-1, kysymysteksti, aihe, 
				new Kurssi(kurssinimi));
		    kysymysDao.saveOrUpdate(kysymys);
		    res.redirect("/");
		    return "";
        });

        Spark.post("/kysymykset/:id/delete)", (req, res) -> {
			String id = req.params("id");
		    kysymysDao.delete(Integer.parseInt(id));
		    res.redirect("/");
		    return "";
        });
    }

}
