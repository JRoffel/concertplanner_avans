package avans.concertplanner.model;

public class Artist {
	
	private int m_id;
	private String m_name;
	private String m_description;
	
	public int getId() { return m_id; }
	
	public String getName() { return m_name; }
	
	public String getDescription() { return m_description; }
	
	public Artist(int id, String name, String description) {
		m_id = id;
		m_name = name;
		m_description = description;
	}
}
