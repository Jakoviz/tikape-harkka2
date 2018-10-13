package tikape;

import java.io.File;
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
        
        String hei_maailma = "Hei maailma!";
        
        List<Kysymys> kysymykset = new ArrayList<>();

        Database database = new Database("org.sqlite.JDBC", "jdbc:sqlite:tasks.db");

        KysymysDao kysymysDao = new KysymysDao(database);
        
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
	    map.put("tervehdys", hei_maailma);
//            map.put("tehtavat", kysymysDao.findAll());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.post("/", (req, res) -> {
            String nimi = req.queryParams("nimi");
            Kysymys tehtava = new Kysymys(nimi);
            kysymysDao.saveOrUpdate(tehtava);
            res.redirect("/");
            return "";
        });
    }
}
