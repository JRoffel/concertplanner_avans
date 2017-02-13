package avans.concertplanner.view;

import avans.concertplanner.model.Artist;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateController {
	@FXML
	private TextField nameField;
	@FXML
	private TextField descriptionField;
	
	private Stage dialogStage;
	private Artist artist;
	private Boolean okClicked = false;
	@FXML
	private void initialize() {
		
	}
}
