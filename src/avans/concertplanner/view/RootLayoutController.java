package avans.concertplanner.view;

import avans.concertplanner.MainApp;
import javafx.fxml.FXML;

public class RootLayoutController {
	private MainApp mainApp;
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	private void showCreateDialog() {
		boolean okClicked = mainApp.showCreateDialog();
		if(okClicked) {
			MainApp.updateArtists();
		}
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	private void showStageDialog() {
		mainApp.showStageDialog();
	}
}
