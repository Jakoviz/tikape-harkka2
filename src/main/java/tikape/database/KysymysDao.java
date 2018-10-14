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
    public Kysymys findOne(Kysymys kysymys) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(
				"Select * FROM Kysymys WHERE id = ?");
			stmt.setInt(1, kysymys.getId());
			ResultSet kysymysRs = stmt.executeQuery();
			if (!kysymysRs.next()) {
				throw new Exception("Kysymyksen findOne:ssa virhe");	
			}
			return new Kysymys(kysymysRs.getInt("id"), 
				kysymysRs.getString("kysymysteksti"), 
				kysymysRs.getString("aihe"), 
				new Kurssi(null, kysymysRs.getInt("kurssi_id")));
		}
    }
    public List<Kysymys> findAll() throws SQLException, Exception {
        List<Kysymys> kysymykset = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(
				"SELECT a.id, a.kysymysteksti, a.aihe, b.nimi as kurssinimi, a.kurssi_id FROM Kysymys as a"
					+ " JOIN Kurssi as b on a.kurssi_id = b.id");
			ResultSet kysymyksetRs = stmt.executeQuery();
            while (kysymyksetRs.next()) {
                kysymykset.add(new Kysymys(
					kysymyksetRs.getInt("id"), 
					kysymyksetRs.getString("kysymysteksti"), 
					kysymyksetRs.getString("aihe"), 
					new Kurssi(kysymyksetRs.getString("kurssinimi"), 
						kysymyksetRs.getInt("kurssi_id"))));
            }
        }
        return kysymykset;
    }
    public Kysymys saveOrUpdate(Kysymys kysymys) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM Kysymys WHERE aihe = ? AND kysymysteksti = ? AND kurssi_id = ?");
			stmt.setString(1, kysymys.getAihe());
			stmt.setString(2, kysymys.getKysymysteksti());
			stmt.setInt(3, kysymys.getKurssi().getId());
			ResultSet olemassaolevaRs = stmt.executeQuery();
			if (!olemassaolevaRs.next()) {
				stmt = conn.prepareStatement(
					"INSERT INTO Kysymys (aihe, kysymysteksti, kurssi_id ) VALUES (?, ?, ?)");
				stmt.setString(1, kysymys.getAihe());
				stmt.setString(2, kysymys.getKysymysteksti());
				stmt.setInt(3, kysymys.getKurssi().getId());
				stmt.executeUpdate();
			}
			stmt = conn.prepareStatement(
				"SELECT * FROM Kysymys WHERE aihe = ? AND kysymysteksti = ? AND kurssi_id = ?");
			stmt.setString(1, kysymys.getAihe());
			stmt.setString(2, kysymys.getKysymysteksti());
			stmt.setInt(3, kysymys.getKurssi().getId());
			ResultSet kurssiRs = stmt.executeQuery();
			if (!kurssiRs.next()) {
				throw new Exception("Kysymyksen saveOrUpdate:ssa lisays ei onnistunut");
			}
			return new Kysymys(kurssiRs.getInt("id"), kurssiRs.getString("kysymysteksti"), 
				kurssiRs.getString("aihe"), new Kurssi(kysymys.getKurssi().getNimi(), 
				kysymys.getKurssi().getId()));
        }
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
