package avans.concertplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import avans.concertplanner.model.Artist;

public class DatabaseController {
	
	private String url = "";
	private String user = "";
	private String pass = "";
	
	public DatabaseController(String host, String port, String db, String user, String pass) {
		url = "jdbc:mysql://" + host + ":" + port + "/" + db;
		this.user = user;
		this.pass = pass;
	}
	
	public List<Artist> getAllArtists() {
		Connection db = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Artist> result = null;
		try {
			db = DriverManager.getConnection(url, user, pass);
			st = db.prepareStatement("SELECT * FROM artist");
			rs = st.executeQuery();
			
			while (rs.next()) {
				result.add(new Artist(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(rs != null) { rs.close(); }
				if(st != null) { st.close(); }
				if(db != null) { db.close(); }
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return result;
	}
}
