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
			return null;
		}
		return new Kurssi(kysymysRs.getString("nimi"), kysymysRs.getInt("id"));
		}
    }
	
	public Kurssi findOne(Kysymys kysymys) throws SQLException, Exception {
        try (Connection conn = database.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(
				"Select * FROM Kysymys as a JOIN Kurssi as b on a.kurssi_id = b.id WHERE b.id = ?");
			stmt.setInt(1, kysymys.getId());
			ResultSet kysymysRs = stmt.executeQuery();
			if (!kysymysRs.next()) {
				return null;
			}
			return new Kurssi(kysymysRs.getString("nimi"), kysymysRs.getInt("kurssi_id"));
		}
    }
	
    public List<Kurssi> findAll() throws SQLException, Exception {
	List<Kurssi> kurssit = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kurssi");
			ResultSet kurssitRs = stmt.executeQuery();
            while (kurssitRs.next()) {
                kurssit.add(new Kurssi(
				kurssitRs.getString("nimi"), 
				kurssitRs.getInt("id")));
            }
        } 
        return kurssit;
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
			"SELECT * FROM Kurssi WHERE nimi = ?");
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
