package avans.concertplanner.view;

import java.util.List;

import avans.concertplanner.MainApp;
import avans.concertplanner.model.Artist;
import avans.concertplanner.model.Concert;
import avans.concertplanner.model.Stage;
import avans.concertplanner.util.DateTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ConcertOverviewController {
	@FXML
	private TableView<Artist> artistTable;
	@FXML
	private TableColumn<Artist, String> artistColumn;
	@FXML
	private TableColumn<Artist, Button> editColumn;
	@FXML
	private TableColumn<Artist, Button> deleteColumn;
	@FXML
	private GridPane gridPane;
	
	@FXML
	private TextField artistDescription;
	@FXML
	private TextField artistName;
	
	private MainApp mainApp;
	
	public ConcertOverviewController() {
		
	}
	
	@FXML
	private void initialize() {
		artistColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProp());
		deleteColumn.setCellValueFactory(cellData -> cellData.getValue().getDeleteProp());
//		artistColumn.setCellFactory(TextFieldTableCell.<Artist>forTableColumn());
//		artistColumn.setOnEditCommit(
//				(CellEditEvent<Artist, String> t) -> {
//					((Artist) t.getTableView().getItems().get(
//							t.getTablePosition().getRow())).mainApp.setArtistName(t.getNewValue());
//				});
		
//		artistColumn.addEventHandler(ActionEvent.ACTION, event -> mainApp.setArtistName());
		
		showConcertDetails(null);
		
		artistTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showConcertDetails(newValue));
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		artistTable.setItems(mainApp.getArtistData());
	}
	
	private void showConcertDetails(Artist artist) {
		gridPane.getChildren().clear();
		if(artist != null) {
			artistName.setText(artist.getName());
			artistDescription.setText(artist.getDescription());
			artistName.addEventHandler(ActionEvent.ACTION, event -> mainApp.setArtistName(artistName.getText(), artist));
			artistDescription.addEventHandler(ActionEvent.ACTION, event -> mainApp.setArtistDescription(artistDescription.getText(), artist));
			List<Concert> concerts = mainApp.getConcertsFromArtistId(artist.getId());
			int i = 0;
			if(concerts.isEmpty()) {
				Button createButton = new Button("create");
				createButton.addEventHandler(ActionEvent.ACTION, event -> createConcertForArtist(artist));
				gridPane.add(createButton, 0, 1);
			} else {
				for(Concert concert : concerts) {
					ComboBox<Stage> stage = new ComboBox<Stage>();
					stage.setCellFactory(
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
					stage.setButtonCell(
							new ListCell<Stage>() {
								protected void updateItem(Stage t, boolean bln) {
									super.updateItem(t, bln);
									if(bln) {
										setText("");
									} else {
										if(concert.getStageId() != null && t.getId() != null) {
											mainApp.setConcertStage(concert.getId(), t.getId());
										}
										setText(t.getName());
									}
								}
							});
					stage.setConverter(
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
					stage.getItems().addAll(mainApp.getAllStages());
					stage.setValue(mainApp.getStageById(concert.getStageId()));
					DateTimePicker startTime = new DateTimePicker();
					startTime.setDateTimeValue(concert.getStartTime());
					startTime.addEventHandler(ActionEvent.ACTION, event -> mainApp.setConcertStart(concert.getId(), startTime.getDateTimeValue()));
					DateTimePicker endTime = new DateTimePicker();
					endTime.setDateTimeValue(concert.getEndTime());
					endTime.addEventHandler(ActionEvent.ACTION, event -> mainApp.setConcertStart(concert.getId(), startTime.getDateTimeValue()));
					Button deleteConcert = new Button("del");
					deleteConcert.addEventHandler(ActionEvent.ACTION, event -> deleteConcert(concert.getId(), artist));
					gridPane.add(stage, 0, i);
					gridPane.add(startTime, 1, i);
					gridPane.add(endTime, 2, i);
					gridPane.add(deleteConcert, 3, i);
					i++;
				}
				Button createButton = new Button("create");
				createButton.addEventHandler(ActionEvent.ACTION, event -> createConcertForArtist(artist));
				gridPane.add(createButton, 0, i);
			}
		} else {
			gridPane.add(new Label("Please select an artist"), 0, 1);
		}
	}
	
	public void deleteConcert(String id, Artist artist) {
		mainApp.deleteConcert(id);
		showConcertDetails(artist);
	}
	
	public void createConcertForArtist(Artist artist) {
		mainApp.createConcertForArtist(artist.getId());
		showConcertDetails(artist);
	}
	
	@FXML
	private void showCreateDialog() {
		boolean okClicked = mainApp.showCreateDialog();
		if(okClicked) {
			MainApp.updateArtists();
		}
	}
}
