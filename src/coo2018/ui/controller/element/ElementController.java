package coo2018.ui.controller.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import coo2018.model.Element;
import coo2018.utils.persistence.Path;
import coo2018.utils.rooting.Route;
import coo2018.utils.rooting.RoutingUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ElementController implements Initializable {

	private ObservableList<Element> elements = FXCollections.observableArrayList();

	@FXML
	private MenuItem openFile;

	@FXML
	private TableView<Element> table;

	@FXML
	private TextField tfId;

	@FXML
	private TextField tfNom;

	@FXML
	private TextField tfQuantite;

	@FXML
	private TextField tfUnite;

	@FXML
	private TextField tfPrixAchat;

	@FXML
	private TextField tfPrixVente;
	
	@FXML
	private Button bRetour;

	@FXML
	private Button bAjouter;

	@FXML
	private Button bSupprimer;
	
	
	public void addElementToList() {

		if (isChampValid()) {

			// créer un élément avec les champs du formulaire
			Element element = new Element(
					tfId.getText(), 
					tfNom.getText(), 
					Integer.parseInt(tfQuantite.getText()), 
					tfUnite.getText(),
					Double.parseDouble(tfPrixAchat.getText()), 
					Double.parseDouble(tfPrixVente.getText())
				);

			// rajoute cet objet dans la liste des éléments
			ObservableList<Element> elementObservable = this.table.getItems();
			elementObservable.add(element);

			this.table.setItems(elementObservable);
			clearTextField();

			Element.addElementToCSV(element, Path.ELEMENT.getPath());

		} else {

			messageAlert();
		}
	}		

	@Override
	public void initialize(URL url, ResourceBundle rb) {
					
		String res = Path.ELEMENT.getPath();
		
		if (!res.equals("")) {
			
			this.elements.clear();
			this.elements.addAll(Element.CSVToElement(res));
			
			this.table.getItems().clear();
			this.table.getItems().addAll(this.elements);
		}
		
		this.openFile.setOnAction(keyEvent -> {

			try {
				
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
				fileChooser.getExtensionFilters().add(extFilter);
				File file = fileChooser.showOpenDialog(this.stage);
				
				Path.savePath(Path.ELEMENT, file.getAbsolutePath());
				
				this.elements.clear();
				this.elements.addAll(Element.CSVToElement(file.getAbsolutePath()));
				
				this.table.getItems().clear();
				this.table.getItems().addAll(this.elements);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		});
		
		this.bRetour.setOnAction(actionEvent -> {

			RoutingUtils.goTo(actionEvent, Route.ACCUEIL);
		});

		// événements sur les composants du formulaire
		this.tfId.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER))
				addElementToList();
		});

		this.tfNom.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER))
				addElementToList();
		});

		this.tfQuantite.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER))
				addElementToList();
		});

		this.tfUnite.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER))
				addElementToList();
		});

		this.tfPrixAchat.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER))
				addElementToList();
		});

		this.tfPrixVente.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER))
				addElementToList();
		});

		this.bAjouter.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER))
				addElementToList();
		});

		this.bAjouter.setOnAction(actionEvent -> {

			addElementToList();
		});

		this.bSupprimer.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER)) {

				try {
					Element.removeElementToCSV(this.table.getSelectionModel().getSelectedItem().getId(), Path.ELEMENT.getPath());
				} catch (IOException e) {

					e.printStackTrace();
				}
				this.table.getSelectionModel().getSelectedItems().forEach(this.table.getItems()::remove);
			}
		});

		this.bSupprimer.setOnAction(actionEvent -> {

			try {
				Element.removeElementToCSV(this.table.getSelectionModel().getSelectedItem().getId(),  Path.ELEMENT.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.table.getSelectionModel().getSelectedItems().forEach(this.table.getItems()::remove);
		});
	}

	/*
	 * affiche un message d'alerte
	 */
	public void messageAlert() {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(null);
		alert.setContentText("Les champs n'ont pas été correctement remplis !");
		alert.showAndWait();
	}

	/*
	 * remet la valeur des champs à nul
	 */
	public void clearTextField() {

		this.tfId.setText("");
		this.tfNom.setText("");
		this.tfQuantite.setText("");
		this.tfUnite.setText("");
		this.tfPrixAchat.setText("");
		this.tfPrixVente.setText("");
	}

	/*
	 * retourne vrai si tout les champs sont valides
	 */
	public boolean isChampValid() {

		return (this.tfId.getText().isEmpty() || this.tfNom.getText().isEmpty() || this.tfQuantite.getText().isEmpty()
				|| this.tfUnite.getText().isEmpty() || this.tfPrixAchat.getText().isEmpty()
				|| this.tfPrixVente.getText().isEmpty()) ? false : true;
	}


	Stage stage;

	void setStage(Stage stg) {
		stage = stg;
	}

}