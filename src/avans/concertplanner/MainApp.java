package avans.concertplanner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import avans.concertplanner.model.Artist;
import avans.concertplanner.model.Concert;
import avans.concertplanner.model.Configuration;
import avans.concertplanner.view.ConcertOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main class containing startup and render logic
 * Also routes generic function calls from controller to controller
 * @author Jason
 *
 */

public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	private ConfigurationController configurationController;
	private static DatabaseController databaseController;
	private static ObservableList<Artist> artistData = FXCollections.observableArrayList();

	public MainApp() {
		configurationController = new ConfigurationController();
		Configuration conf = null;
		
		try {
			conf = configurationController.getConfigFromFile();
			//TODO: Figure out how to config
			databaseController = new DatabaseController(conf.getHost(), conf.getPort(), conf.getDatabaseName(), conf.getUser(), conf.getPassword());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		List<Artist> artists = databaseController.getAllArtists();
		
		for (Artist artist : artists) {
			artistData.add(artist);
		}
	}
	
	public ObservableList<Artist> getArtistData() {
		return artistData;
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("ConcertPlanner");
		
		initRootLayout();
		
		showConcertOverview();
	}
	
	/**
	 * Initializes the root layout.
	 */
	
	public void initRootLayout() {
		try {
			// Load root layout from FXML file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			// Show the scene with the retrieved root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Shows the overview of concerts inside the root layout.
	 */
	
	public void showConcertOverview() {
		try {
			// Load concert overview from FXML file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ConcertOverview.fxml"));
			AnchorPane concertOverview = (AnchorPane) loader.load();
			
			// Set the retrieved concert overview into the center of the root layout.
			rootLayout.setCenter(concertOverview);
			
			ConcertOverviewController controller = loader.getController();
			controller.setMainApp(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return the main stage.
	 * @return
	 */
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public List<Concert> getConcertsFromArtistId(String id) {
		return databaseController.getConcertsByArtistId(id);
	}
	
	public avans.concertplanner.model.Stage getStageById(String id) {
		return databaseController.getStageById(id);
	}
	
	public List<avans.concertplanner.model.Stage> getAllStages() {
		return databaseController.getAllStages();
	}
	
	public void setConcertStage(String concertId, String stageId) {
		databaseController.setConcertStage(concertId, stageId);
	}
	
	public void setConcertStart(String concertId, LocalDateTime newTime) {
		databaseController.setConcertStart(concertId, newTime);
	}
	
	public void setConcertEnd(String concertId, LocalDateTime newTime) {
		databaseController.setConcertEnd(concertId, newTime);
	}
	
	public static void deleteArtist(String id) {
		databaseController.deleteArtist(id);
	}
	
	public static void updateArtists() {
		artistData.clear();
		List<Artist> artists = databaseController.getAllArtists();
		for(Artist artist : artists) {
			artistData.add(artist);
		}
	}
	
	public void deleteConcert(String id) {
		databaseController.deleteConcert(id);
	}
	
	public void deleteStage(String id) {
		databaseController.deleteStage(id);
	}
	
	public void createConcertForArtist(String id) {
		databaseController.createConcertForArtist(id);
	}
}
