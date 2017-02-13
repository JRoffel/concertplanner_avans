package avans.concertplanner.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

public class DateTimePicker extends DatePicker {
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private DateTimeFormatter formatter;
	private ObjectProperty<LocalDateTime> dateTimeValue = new SimpleObjectProperty<>(LocalDateTime.now());
	private ObjectProperty<String> format = new SimpleObjectProperty<String>() {
		public void set(String newValue) {
			super.set(newValue);
			formatter = DateTimeFormatter.ofPattern(newValue);
		}
	};
	
	public DateTimePicker() {
		getStyleClass().add("datetime-picker");
		setFormat(DEFAULT_FORMAT);
		setConverter(new InternalConverter());
		
		valueProperty().addListener((obervable, oldValue, newValue) -> {
			if(newValue == null) {
				dateTimeValue.set(null);
			} else {
				if(dateTimeValue.get() == null) {
					dateTimeValue.set(LocalDateTime.of(newValue, LocalTime.now()));
				} else {
					LocalTime time = dateTimeValue.get().toLocalTime();
					dateTimeValue.set(LocalDateTime.of(newValue, time));
				}
			}
		});
		
		dateTimeValue.addListener((observable, oldValue, newValue) -> {
			setValue(newValue == null ? null : newValue.toLocalDate());
		});
		
		getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
			if(!newValue)
				simulateEnterPressed();
		});
	}
	
	private void simulateEnterPressed() {
		getEditor().commitValue();
	}
	
	public LocalDateTime getDateTimeValue() { return dateTimeValue.get(); }
	public void setDateTimeValue(LocalDateTime dateTimeValue) { this.dateTimeValue.set(dateTimeValue); }
	public ObjectProperty<LocalDateTime> dateTimeValueProperty() { return dateTimeValue; }
	
	public String getFormat() { return format.get(); }
	public void setFormat(String format) { this.format.set(format); }
	public ObjectProperty<String> formatProperty() { return format; }
	
	class InternalConverter extends StringConverter<LocalDate> {
		public String toString(LocalDate object) {
			LocalDateTime value = getDateTimeValue();
			return (value != null) ? value.format(formatter) : "";
		}
		
		public LocalDate fromString(String value) {
			if (value == null || value.isEmpty()) {
				dateTimeValue.set(null);
				return null;
			}
			
			dateTimeValue.set(LocalDateTime.parse(value, formatter));
			return dateTimeValue.get().toLocalDate();
		}
	}
}
