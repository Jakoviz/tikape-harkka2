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
			PreparedStatement stmt = conn.prepareStatement(
				"SELECT a.kysymysteksti, a.aihe, b.nimi as kurssinimi FROM Kysymys as a"
					+ " JOIN Kurssi as b on a.kurssi_id = b.id");
			ResultSet kysymyksetRs = stmt.executeQuery();
            while (kysymyksetRs.next()) {
                kysymykset.add(new Kysymys(kysymyksetRs.getString("kysymysteksti"), 
				kysymyksetRs.getString("aihe"), new Kurssi(kysymyksetRs.getString("kurssinimi"))));
            }
        }
        return kysymykset;
    }
    public Kysymys saveOrUpdate(Kysymys kysymys) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM Kurssi WHERE nimi = ?");
			stmt.setString(1, kysymys.getKurssi().getNimi());
			ResultSet olemassaolevaRs = stmt.executeQuery();
			if (!olemassaolevaRs.next()) {
				stmt = conn.prepareStatement(
					"INSERT INTO Kurssi (nimi) VALUES (?)");
				stmt.setString(1, kysymys.getKurssi().getNimi());
				stmt.executeUpdate();
			}
			stmt = conn.prepareStatement(
				"SELECT id FROM Kurssi WHERE nimi = ?");
			stmt.setString(1, kysymys.getKurssi().getNimi());
			ResultSet kurssiRs = stmt.executeQuery();
			if (!kurssiRs.next()) {
				return null;
			}
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
