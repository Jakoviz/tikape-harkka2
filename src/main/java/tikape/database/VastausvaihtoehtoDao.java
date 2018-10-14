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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import tikape.Kurssi;
import tikape.database.Database;
import tikape.Kysymys;
import tikape.Vastausvaihtoehto;

public class VastausvaihtoehtoDao {

    private Database database;

    public VastausvaihtoehtoDao(Database database) {
        this.database = database;
    }
    public Vastausvaihtoehto findOne(Vastausvaihtoehto vastausvaihtoehto) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
		PreparedStatement stmt = conn.prepareStatement(
			"Select * FROM Vastausvaihtoehto WHERE id = ?");
		stmt.setInt(1, vastausvaihtoehto.getId());
		ResultSet vastausvaihtoehtoRs = stmt.executeQuery();
		if (!vastausvaihtoehtoRs.next()) {
			return null;
		}
		return new Vastausvaihtoehto(vastausvaihtoehtoRs.getInt("id"), 
		    vastausvaihtoehtoRs.getInt("kysymys_id"), 
		    vastausvaihtoehtoRs.getString("vastaus"), 
		    vastausvaihtoehtoRs.getBoolean("oikein")); 
	}
    }
    public List<Kysymys> findAll() throws SQLException, Exception {
	    throw new NotImplementedException();
    }
    public Vastausvaihtoehto saveOrUpdate(Vastausvaihtoehto vastausvaihtoehto) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
		PreparedStatement stmt = conn.prepareStatement(
			"SELECT * FROM Vastausvaihtoehto WHERE kysymys_id = ? AND vastausteksti = ?");
		stmt.setInt(1, vastausvaihtoehto.getKysymys_id());
		stmt.setString(2, vastausvaihtoehto.getVastaus());
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
		stmt = conn.prepareStatement(
			"SELECT * FROM Vastausvaihtoehto WHERE kysymys_id = ? AND vastausteksti = ?");
		stmt.setInt(1, vastausvaihtoehto.getKysymys_id());
		stmt.setString(2, vastausvaihtoehto.getVastaus());
		ResultSet kasiteltyRs = stmt.executeQuery();
		if (!kasiteltyRs.next()) {
			throw new Exception("Vastausvaihtoehdon saveOrUpdate:ssa virhe");	
		}
		return new Vastausvaihtoehto(kasiteltyRs.getInt("id"), 
		    kasiteltyRs.getInt("kysymys_id"),
		    kasiteltyRs.getString("vastausteksti"), 
		    kasiteltyRs.getBoolean("oikein"));
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
