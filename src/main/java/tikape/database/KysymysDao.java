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
import tikape.Kurssi;
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
        List<Kysymys> kysymykset = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            ResultSet result = conn.prepareStatement(
				"SELECT a.kysymysteksti, a.aihe, b.nimi as kurssi FROM Kysymys a"
				+ "JOIN Kurssi b on a.kurssi_id = b.id").executeQuery();
            while (result.next()) {
                kysymykset.add(new Kysymys(result.getString("kysymysteksti"), 
				result.getString("aihe"), new Kurssi(result.getString("kurssi"))));
            }
        }
        return kysymykset;
    }
    public Kysymys saveOrUpdate(Kysymys kysymys) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM Kurssi WHERE nimi = (?)");
			stmt.setString(1, kysymys.getKurssi().getNimi());
			ResultSet kurssit = stmt.executeQuery();
			if (kurssit == null) {
				stmt = conn.prepareStatement(
					"INSERT INTO Kurssi (nimi) VALUES (?)");
				stmt.setString(1, kysymys.getKurssi().getNimi());
				stmt.executeUpdate();
			}
			stmt = conn.prepareStatement(
				"SELECT id FROM Kurssi WHERE nimi = (?)");
			stmt.setString(1, kysymys.getKurssi().getNimi());
			ResultSet kurssiRs = stmt.executeQuery();
			String kurssiId = kurssiRs.getString("id");
			
            stmt = conn.prepareStatement(
                "INSERT INTO Kysymys (aihe, kysymysteksti, kurssi_id) VALUES (?, ?, ?)");
            stmt.setString(1, kysymys.getAihe());
			stmt.setString(2, kysymys.getKysymysteksti());
			stmt.setString(3, kurssiId);
            stmt.executeUpdate();
        }
        return null;
    }
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
