package avans.concertplanner.view;

import avans.concertplanner.MainApp;
import avans.concertplanner.model.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class StageDialogController {
	@FXML
	private TextField nameField;
	@FXML
	private TextField descField;
	@FXML
	private ComboBox<Stage> selectBox;
	
	private MainApp mainApp;
	private javafx.stage.Stage dialogStage;
	
	@FXML
	public void initialize() {
		selectBox.setCellFactory(
				new Callback<ListView<Stage>, ListCell<Stage>>() {
					@Override
					public ListCell<Stage> call(ListView<Stage> p) {
						ListCell<Stage> cell = new ListCell<Stage>() {
							@Override
							protected void updateItem(Stage item, boolean empty) {
								super.updateItem(item, empty);
								if(empty) {
									setText("");
								} else {
									setText(item.getName());
								}
							}
						};
						return cell;
					}
				});
		selectBox.setButtonCell(
				new ListCell<Stage>() {
					protected void updateItem(Stage t, boolean bln) {
						super.updateItem(t, bln);
						if(bln) {
							setText("");
						} else {
//							if(concert.getStageId() != null && t.getId() != null) {
//								mainApp.setConcertStage(concert.getId(), t.getId());
//							}
							nameField.setText(t.getName());
							descField.setText(t.getDescription());
							nameField.addEventHandler(ActionEvent.ACTION, event -> mainApp.updateStage(nameField.getText(), descField.getText(), t));
							descField.addEventHandler(ActionEvent.ACTION, event -> mainApp.updateStage(nameField.getText(), descField.getText(), t));
							setText(t.getName());
						}
					}
				});
		selectBox.setConverter(
				new StringConverter<Stage>() {
					@Override
					public String toString(Stage t) {
						if(t == null) {
							return "";
						} else {
							return t.getName();
						}
					}
					
					@Override
					public Stage fromString(String s) {
						return null;
					}
				});
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		selectBox.getItems().addAll(mainApp.getAllStages());
	}
	
	public void setDialogStage(javafx.stage.Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
}
