package coo2018.ui.controller.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import coo2018.model.Element;
import coo2018.utils.message.MessageUtils;
import coo2018.utils.persistence.Path;
import coo2018.utils.rooting.Route;
import coo2018.utils.rooting.RoutingUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Andréa Christophe
 *
 */
public class ElementController implements Initializable {

	private ObservableList<Element> elements = FXCollections.observableArrayList();

	@FXML
	private Button openFile;

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
	private Button bAjouter;

	@FXML
	private Button bSupprimer;
	
	
	/**
	 * Ajoute un élément au tableau et au fichier CSV via les champs du formulaire
	 */
	public void addElementToList() {

		if (isChampValid()) {

			// Créer un élément avec les champs du formulaire
			Element element = new Element(
					tfId.getText(), 
					tfNom.getText(), 
					Integer.parseInt(tfQuantite.getText()), 
					tfUnite.getText(),
					Double.parseDouble(tfPrixAchat.getText()), 
					Double.parseDouble(tfPrixVente.getText())
				);

			// Rajoute cet objet dans la liste des éléments
			ObservableList<Element> elementObservable = this.table.getItems();
			elementObservable.add(element);

			this.table.setItems(elementObservable);
			clearTextField();

			// On rajoute l'élément dans le fichier CSV
			Element.addElementToCSV(element, Path.ELEMENT.getPath());

		} else {

			MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Le fichier que vous essayez de charger contient des éléments non présent dans le fichier des éléments actuel");
		}
	}		

	@Override
	public void initialize(URL url, ResourceBundle rb) {
					
		String res = Path.ELEMENT.getPath();
		
		// Si l'utilisateur n'a jamais renseigné de fichier CSV
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
		
		
		/*
		 * Événements liés au boutons de l'interface
		 */

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

	/**
	 * Remet la valeur des champs du formulaire à defaut 
	 */
	public void clearTextField() {

		this.tfId.setText("");
		this.tfNom.setText("");
		this.tfQuantite.setText("");
		this.tfUnite.setText("");
		this.tfPrixAchat.setText("");
		this.tfPrixVente.setText("");
	}

	/**
	 * @return true si tout les champs du formulaire sont valides
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