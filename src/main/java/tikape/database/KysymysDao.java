/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.database.Database;
import tikape.Kysymys;

public class KysymysDao {

    private Database database;

    public KysymysDao(Database database) {
        this.database = database;
    }
    public Kysymys findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public List<Kysymys> findAll() throws SQLException, Exception {
        List<Kysymys> tasks = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            ResultSet result = conn.prepareStatement("SELECT * FROM Tehtava").executeQuery();
            while (result.next()) {
                tasks.add(new Kysymys(result.getString("name")));
            }
        }
        return tasks;
    }
    public Kysymys saveOrUpdate(Kysymys object) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO Tehtava (nimi) VALUES (?)");
            stmt.setString(1, object.getNimi());
            stmt.executeUpdate();
        }
        return null;
    }
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
