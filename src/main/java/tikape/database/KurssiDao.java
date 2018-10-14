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

public class KurssiDao {

    private Database database;

    public KurssiDao(Database database) {
        this.database = database;
    }
    public Kurssi findOne(Kurssi kurssi) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
		PreparedStatement stmt = conn.prepareStatement(
			"Select * FROM Kurssi WHERE nimi = ?");
		stmt.setString(1, kurssi.getNimi());
		ResultSet kysymysRs = stmt.executeQuery();
		if (!kysymysRs.next()) {
			Kurssi saveOrUpdate = saveOrUpdate(kurssi);
			return new Kurssi(saveOrUpdate.getNimi(), saveOrUpdate.getId());
		}
		return new Kurssi(kysymysRs.getString("nimi"), kysymysRs.getInt("id"));
	}
    }
    public List<Kysymys> findAll() throws SQLException, Exception {
	    throw new NotImplementedException();
    }
    public Kurssi saveOrUpdate(Kurssi kurssi) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM Kurssi WHERE nimi = ?");
			stmt.setString(1, kurssi.getNimi());
			ResultSet olemassaolevaRs = stmt.executeQuery();
			if (!olemassaolevaRs.next()) {
				stmt = conn.prepareStatement(
					"INSERT INTO Kurssi (nimi) VALUES (?)");
				stmt.setString(1, kurssi.getNimi());
				stmt.executeUpdate();
			}
			stmt = conn.prepareStatement(
				"SELECT id FROM Kurssi WHERE nimi = ?");
			stmt.setString(1, kurssi.getNimi());
			ResultSet kurssiRs = stmt.executeQuery();
			if (!kurssiRs.next()) {
				throw new Exception("Kurssin saveOrUpdate:ssa lisays ei onnistunut");
			}
			return new Kurssi(kurssiRs.getString("nimi"), kurssiRs.getInt("id"));
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
