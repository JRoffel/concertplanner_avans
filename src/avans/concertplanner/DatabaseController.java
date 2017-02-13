package avans.concertplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import avans.concertplanner.model.Artist;
import avans.concertplanner.model.Concert;
import avans.concertplanner.model.Stage;

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
		ResultSet rs = null;
		List<Artist> result = new ArrayList<Artist>();
		
		try {
			rs = executeQuery("SELECT * FROM artist");
			
			while (rs.next()) {
				result.add(new Artist(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(rs != null) { rs.close(); }
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return result;
	}
	
	public List<Concert> getConcertsByArtistId(String id) {
		ResultSet rs = null;
		List<Concert> result = new ArrayList<Concert>();
		
		try {
			rs = executeQuery("SELECT * FROM performance WHERE performance.artistId = " + id);
			
			while (rs.next()) {
				LocalDateTime startTime = new Date(rs.getTimestamp(4).getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				LocalDateTime endTime = new Date(rs.getTimestamp(5).getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				result.add(new Concert(rs.getString(1), rs.getString(2), rs.getString(3), startTime, endTime));
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(rs != null) { rs.close(); }
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return result;
	}

	public avans.concertplanner.model.Stage getStageById(String id) {
		ResultSet rs = null;
		Stage result = null;
		
		try {
			rs = executeQuery("SELECT * FROM stage WHERE stage.id = " + id);
			
			while (rs.next()) {
				result = new Stage(rs.getString(1), rs.getString(3), rs.getString(2));
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(rs != null) { rs.close(); }
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return result;
	}
	
	public List<avans.concertplanner.model.Stage> getAllStages() {
		ResultSet rs = null;
		List<avans.concertplanner.model.Stage> result = new ArrayList<avans.concertplanner.model.Stage>();
		
		try {
			rs = executeQuery("SELECT * FROM stage");
			
			while (rs.next()) {
				result.add(new Stage(rs.getString(1), rs.getString(3), rs.getString(2)));
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(rs != null) { rs.close(); }
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return result;
	}
	
	public void setConcertStage(String concertId, String stageId) {
		try {
			executeUpdate("UPDATE performance SET stageId=" + stageId + " WHERE id=" + concertId);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void setConcertStart(String concertId, LocalDateTime newTime) {
		try {
			executeUpdate("UPDATE performance SET startTime=\"" + newTime.toString() + "\" WHERE id=" + concertId);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void setConcertEnd(String concertId, LocalDateTime newTime) {
		try {
			executeUpdate("UPDATE performance SET endTime=\"" + newTime.toString() + "\" WHERE id=" + concertId);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void deleteArtist(String id) {		
		try {
			executeUpdate("DELETE FROM performance WHERE artistId=" + id);
			executeUpdate("DELETE FROM artist WHERE id=" + id);
			MainApp.updateArtists();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void deleteConcert(String id) {
		try {
			executeUpdate("DELETE FROM performance WHERE id=" + id);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void deleteStage(String id) {
		try {
			executeUpdate("DELETE FROM performance WHERE stageId=" + id);
			executeUpdate("DELETE FROM stage WHERE id=" + id);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void editArtist(Artist newArtist) {
		String query = "UPDATE artist SET name=" + newArtist.getName() + ", description=" + newArtist.getDescription() + " WHERE id=" + newArtist.getId();
		try {
			executeUpdate(query);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void editConcert(Concert newConcert) {
		String query = "UPDATE concert SET artistId=" + newConcert.getArtistId() + ", stageId=" + newConcert.getStageId() + ", startTime=" + newConcert.getStartTime() + ", endTime=" + newConcert.getEndTime() + " WHERE id=" + newConcert.getId();
		try {
			executeUpdate(query);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void editStage(Stage newStage) {
		String query = "UPDATE stage SET name=" + newStage.getName() + ", description=" + newStage.getDescription() + " WHERE id=" + newStage.getId();
		try {
			executeUpdate(query);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void createConcertForArtist(String id) {
		List<Stage> stages = getAllStages();
		String query = "INSERT INTO performance(artistId, stageId, startTime, endTime) VALUES (" + id + ", " + stages.get(0).getId() + ", '" + LocalDateTime.now() + "', '" + LocalDateTime.now() + "')";
		try {
			System.out.println(query);
			executeUpdate(query);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	private ResultSet executeQuery(String query) throws SQLException {
		Connection db = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		db = DriverManager.getConnection(url, user, pass);
		st = db.prepareStatement(query);
		rs = st.executeQuery();
		
		return rs;
	}
	
	private void executeUpdate(String query) throws SQLException {
		Connection db = null;
		PreparedStatement st = null;
		
		db = DriverManager.getConnection(url, user, pass);
		st = db.prepareStatement(query);
		st.executeUpdate();
		
		if(st != null) { st.close(); }
		if(db != null) { db.close(); }
	}
}
