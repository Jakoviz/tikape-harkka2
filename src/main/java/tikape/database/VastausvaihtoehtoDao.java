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
import tikape.Vastausvaihtoehto;

public class VastausvaihtoehtoDao {

    private Database database;

    public VastausvaihtoehtoDao(Database database) {
        this.database = database;
    }
    public Kysymys findOne(Integer key) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(
				"Select * FROM Vastausvaihtoehto WHERE id = ?");
			stmt.setInt(1, key);
			ResultSet vastausvaihtoehtoRs = stmt.executeQuery();
			if (!vastausvaihtoehtoRs.next()) {
				return null;
			}
			return new Kysymys(vastausvaihtoehtoRs.getInt("id"), vastausvaihtoehtoRs.getString("kysymysteksti"), 
				vastausvaihtoehtoRs.getString("aihe"), new Kurssi(vastausvaihtoehtoRs.getString("kurssi")));
		}
    }
    public List<Kysymys> findAll() throws SQLException, Exception {
        List<Kysymys> kysymykset = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(
				"SELECT a.id, a.kysymysteksti, a.aihe, b.nimi as kurssinimi FROM Kysymys as a"
					+ " JOIN Kurssi as b on a.kurssi_id = b.id");
			ResultSet vastausvaihtoehdotRs = stmt.executeQuery();
            while (vastausvaihtoehdotRs.next()) {
                kysymykset.add(new Kysymys(vastausvaihtoehdotRs.getInt("id"), vastausvaihtoehdotRs.getString("kysymysteksti"), 
				vastausvaihtoehdotRs.getString("aihe"), new Kurssi(vastausvaihtoehdotRs.getString("kurssinimi"))));
            }
        }
        return kysymykset;
    }
    public Vastausvaihtoehto saveOrUpdate(Vastausvaihtoehto vastausvaihtoehto) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM Vastausvaihtoehto WHERE  = ?");
			stmt.setString(1, vastausvaihtoehto.getVastaus());
			ResultSet olemassaolevaRs = stmt.executeQuery();
			if (!olemassaolevaRs.next()) {
				stmt = conn.prepareStatement(
					"INSERT INTO Vastausvaihtoehto (vastausteksti, oikein, kysymys_id) VALUES (?, ?, ?)");
				stmt.setString(1, vastausvaihtoehto.getVastaus());
				stmt.setBoolean(2, vastausvaihtoehto.isOikein());
				stmt.setInt(3, vastausvaihtoehto.getKysymys_id());
				stmt.executeUpdate();
			} else {
				stmt = conn.prepareStatement(
					"UPDATE Vastausvaihtoehto (oikein) VALUES (?) WHERE vastausteksti = (?)");
				stmt.setBoolean(1, vastausvaihtoehto.isOikein());
				stmt.setString(2, vastausvaihtoehto.getVastaus());
				stmt.executeUpdate();			
			}	

//			stmt = conn.prepareStatement(
//				"SELECT id FROM Vastausvaihtoehto WHERE vastausteksti = (?)");
//			stmt.setString(1, vastausvaihtoehto.getVastaus());
//			ResultSet kurssiRs = stmt.executeQuery();
//			if (!kurssiRs.next()) {
//				return null;
//			}
//			int kurssiId = kurssiRs.getInt("id");
//			
//            stmt = conn.prepareStatement(
//                "INSERT INTO Kysymys (aihe, kysymysteksti, kurssi_id) VALUES (?, ?, ?)");
//            stmt.setString(1, kysymys.getAihe());
//			stmt.setString(2, kysymys.getKysymysteksti());
//			stmt.setInt(3, kurssiId);
//            stmt.executeUpdate();
        }
        return null;
    }
    public void delete(Integer key) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(
				"DELETE FROM Kysymys WHERE id = ?");
			stmt.setInt(1, key);
			stmt.executeUpdate();
		}
    }
}
