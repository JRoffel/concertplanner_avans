package avans.concertplanner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import avans.concertplanner.model.Configuration;

/**
 * Contains all functions related to the config file
 * @author Jason
 *
 */

public class ConfigurationController {
	
	public Configuration getConfigFromFile() throws IOException {
		InputStream inputStream = null;
		Configuration configurationModel = new Configuration("", "", "", "", "");
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
			
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("Could not find " + propFileName);
			}
			
			configurationModel.setHost(prop.getProperty("host"));
			configurationModel.setPort(prop.getProperty("port"));
			configurationModel.setDatabaseName(prop.getProperty("databaseName"));
			configurationModel.setUser(prop.getProperty("user"));
			configurationModel.setPassword(prop.getProperty("password"));
			
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(inputStream != null) { inputStream.close(); }
		}
		
		return configurationModel;
	}
}
