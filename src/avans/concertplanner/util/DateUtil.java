package avans.concertplanner.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
	private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
	
	public static String format(LocalDateTime dateTime) {
		if (dateTime == null) {
			return null;
		}
		
		return DATE_FORMATTER.format(dateTime);
	}
	
	public static LocalDateTime parse(String dateTimeString) {
		try {
			return DATE_FORMATTER.parse(dateTimeString, LocalDateTime::from);
		} catch (DateTimeParseException ex) {
			return null;
		}
	}
	
	public static Boolean isValidDateTime(String dateTimeString) {
		return DateUtil.parse(dateTimeString) != null;
	}
}
