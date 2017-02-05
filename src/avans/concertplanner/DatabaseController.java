package avans.concertplanner;

public class DatabaseController {
	
	private String url = "";
	private String user = "";
	private String pass = "";
	
	public DatabaseController(String host, String port, String db, String user, String pass) {
		url = "jbdc:mysql://" + host + ":" + port + "/" + db;
		this.user = user;
		this.pass = pass;
	}
}
