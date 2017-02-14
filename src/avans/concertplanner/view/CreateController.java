package avans.concertplanner.view;

import avans.concertplanner.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class CreateController {
	@FXML
	private TextField nameField;
	@FXML
	private TextArea descriptionField;
	@FXML
	private RadioButton stageField;
	@FXML
	private RadioButton artistField;
	
	private ToggleGroup group = new ToggleGroup();
	private MainApp mainApp;
	private Stage dialogStage;
	private Boolean okClicked = false;
	
	@FXML
	private void initialize() {
		stageField.setToggleGroup(group);
		artistField.setToggleGroup(group);
		stageField.setSelected(true);
		artistField.setSelected(false);
	}
	
	@FXML
	private void okClicked() {
		if(nameField.getText() != null && descriptionField.getText() != null) {
			if(stageField.isSelected()) {
				mainApp.createNewStage(nameField.getText(), descriptionField.getText());
			}
			
			if(artistField.isSelected()) {
				mainApp.createNewArtist(nameField.getText(), descriptionField.getText());
			}
			
			okClicked = true;
			dialogStage.close();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Fill fields!");
			alert.setHeaderText("Empty fields found");
			alert.setContentText("Please fill out all fields to create a new user");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void cancelClicked() {
		dialogStage.close();
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
}
