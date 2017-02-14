package avans.concertplanner.model;

import avans.concertplanner.MainApp;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Artist {
	
	private StringProperty m_id;
	private StringProperty m_name;
	private StringProperty m_description;
	private ObjectProperty<Button> m_deleteButton;
	
	public String getId() { return m_id.get(); }
	public StringProperty getIdProp() { return m_id; }
	
	public String getName() { return m_name.get(); }
	public void setName(String name) { m_name.set(name); }
	public StringProperty getNameProp() { return m_name; }
	
	public String getDescription() { return m_description.get(); }
	public void setDescription(String description) { m_description.set(description); }
	public StringProperty getDescriptionProp() { return m_description; }
	
	
	public Button getDelete() { return m_deleteButton.get(); }
	public ObjectProperty<Button> getDeleteProp() { return m_deleteButton; }
	
	public Artist(String id, String name, String description) {
		m_id = new SimpleStringProperty(id);
		m_name = new SimpleStringProperty(name);
		m_description = new SimpleStringProperty(description);
		m_deleteButton = new SimpleObjectProperty<Button>();
		
		Button deleteButton = new Button("delete");
		deleteButton.addEventHandler(ActionEvent.ACTION, event -> MainApp.deleteArtist(id));
		
		m_deleteButton.set(deleteButton);
	}
}
