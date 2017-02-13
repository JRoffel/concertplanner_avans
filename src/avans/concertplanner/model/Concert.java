package avans.concertplanner.model;

import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Concert {
	
	private StringProperty m_id;
	private StringProperty m_artistId;
	private StringProperty m_stageId;
	private ObjectProperty<LocalDateTime> m_startTime;
	private ObjectProperty<LocalDateTime> m_endTime;
	
	public Concert(String id, String artistId, String stageId, LocalDateTime startTime, LocalDateTime endTime) {
		m_id = new SimpleStringProperty(id);
		m_artistId = new SimpleStringProperty(artistId);
		m_stageId = new SimpleStringProperty(stageId);
		m_startTime = new SimpleObjectProperty<LocalDateTime>(startTime);
		m_endTime = new SimpleObjectProperty<LocalDateTime>(endTime);
	}
	
	public String getId() { return m_id.get(); }
	public StringProperty getIdProp() { return m_id; }
	
	public String getArtistId() { return m_artistId.get(); }
	public void setArtistId(String id) { m_artistId.set(id); }
	public StringProperty getArtistIdProp() { return m_artistId; }
	
	public String getStageId() { return m_stageId.get(); }
	public void setStageId(String id) { m_stageId.set(id); }
	public StringProperty getStageIdProp() { return m_stageId; }
	
	public LocalDateTime getStartTime() { return m_startTime.get(); }
	public void setStartTime(LocalDateTime startTime) { m_startTime.set(startTime); }
	public ObjectProperty<LocalDateTime> getStartTimeProp() { return m_startTime; }
	
	public LocalDateTime getEndTime() { return m_endTime.get(); }
	public void setEndTime(LocalDateTime endTime) { m_endTime.set(endTime); }
	public ObjectProperty<LocalDateTime> getEndTimeProp() { return m_endTime; }
}
