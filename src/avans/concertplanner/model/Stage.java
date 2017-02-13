package avans.concertplanner.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Stage {
	
	private StringProperty m_id;
	private StringProperty m_name;
	private StringProperty m_description;
	
	public String getId() { return m_id.get(); }
	public StringProperty getIdProp() { return m_id; }
	
	public String getName() { return m_name.get(); }
	public void setName(String name) { m_name.set(name); }
	public StringProperty getNameProp() { return m_name; }
	
	public String getDescription() { return m_description.get(); }
	public void setDescription(String description) { m_description.set(description); }
	public StringProperty getDescriptionProp() { return m_description; }
	
	public Stage(String id, String name, String description) {
		m_id = new SimpleStringProperty(id);
		m_name = new SimpleStringProperty(name);
		m_description = new SimpleStringProperty(description);
	}
}
