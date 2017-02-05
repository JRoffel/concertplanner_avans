package avans.concertplanner.model;

/**
 * Model for the configuration file.
 * @author Jason
 *
 */

public class ConfigurationModel {
	private String m_host;
	private String m_port;
	private String m_databaseName;
	private String m_user;
	private String m_password;

	//Getter and setter for host.
	public String getHost() { return m_host; }
	public void setHost(String host) { m_host = host; }
	
	//Getter and setter for port.
	public String getPort() { return m_port; }
	public void setPort(String port) { m_port = port; }
	
	//Getter and setter for database name.
	public String getDatabaseName() { return m_databaseName; }
	public void setDatabaseName(String databaseName) { m_databaseName = databaseName; }
	
	//Getter and setter for username.
	public String getUser() { return m_user; }
	public void setUser(String user) { m_user = user; }
	
	//Getter and setter for password.
	public String getPassword() { return m_password; }
	public void setPassword(String pass) { m_password = pass; }
	
	public ConfigurationModel(String host, String port, String databaseName, String user, String password) {
		setHost(host);
		setPort(port);
		setDatabaseName(databaseName);
		setUser(user);
		setPassword(password);
	}
}
