package coo2018.ui.controller.chaine;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import coo2018.model.Chaine;
import coo2018.model.Element;
import coo2018.ui.IActionFormulaire;
import coo2018.utils.message.MessageUtils;
import coo2018.utils.persistence.Path;
import coo2018.utils.persistence.PersistenceUtils;
import coo2018.utils.rooting.Route;
import coo2018.utils.rooting.RoutingUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * s
 * @author Andréa Christophe
 *
 */
public class ChaineController implements Initializable, IActionFormulaire, ITransformation {

	private ObservableList<Chaine> chaines = FXCollections.observableArrayList();
	
	private Stage stage;


	@FXML
	private TableView<Chaine> table;
	
	@FXML
	private TextField tfId;

	@FXML
	private TextField tfNom;
	
	@FXML
	private TextField tfNiveauActivation;
	
	@FXML
	private Button bAjouter;
	
	@FXML
	private Button bSupprimer;
	
	@FXML
	private Button bVisualiser;
	
	@FXML
	private Button bProduction;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		String res = Path.CHAINE.getPath();
		File tempFile = new File(res);

		if (!res.equals("") && tempFile.exists()) {
			
			// On clear la liste de chaînes (pour ajouter les nouveaux objets)  
			this.chaines.clear();
			try {
				this.chaines.addAll(Chaine.CSVToChaine(res));
				
			} catch (Exception e) {

				MessageUtils.messageAlert(AlertType.ERROR, "Erreur", e.getMessage());
			}
			
			// On clear la tableview et on ajoute les nouveaux objets 
			this.table.getItems().clear();
			this.table.getItems().addAll(this.chaines);
		}
		

		// événements sur les composants du formulaire
		this.tfId.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER))
				addToList();
		});

		this.tfNom.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER))
				addToList();
		});

		this.tfNiveauActivation.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER))
				addToList();
		});

		this.bAjouter.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER))
				addToList();
		});

		this.bAjouter.setOnAction(actionEvent -> {

			addToList();
		});

		this.bSupprimer.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER)) {

				try {
					Chaine.removeChaineToCSV(this.table.getSelectionModel().getSelectedItem().getId(), Path.CHAINE.getPath());
				} catch (Exception e) {
					MessageUtils.messageAlert(AlertType.ERROR, "Erreur", e.getMessage());
				}
				this.table.getSelectionModel().getSelectedItems().forEach(this.table.getItems()::remove);
			}
		});

		this.bSupprimer.setOnAction(actionEvent -> {

			try {
				Chaine.removeChaineToCSV(this.table.getSelectionModel().getSelectedItem().getId(), Path.CHAINE.getPath());
			} catch (Exception e) {
				MessageUtils.messageAlert(AlertType.ERROR, "Erreur", e.getMessage());
			}			
			this.table.getSelectionModel().getSelectedItems().forEach(this.table.getItems()::remove);
		});
				
		this.bVisualiser.setOnAction(actionEvent -> {
						
			RoutingUtils.goTo(actionEvent, Route.CHAINE_DETAIL);
		});
		
		this.bProduction.setOnAction(actionEvent -> {

			transforme();
		});
	}
	
	/**
	 * Remet la valeur des champs du formulaire à defaut 
	 */
	@Override
	public void clearTextField() {

		this.tfId.setText("");
		this.tfNom.setText("");
		this.tfNiveauActivation.setText("");	
	}

	/**
	 * @return true si tout les champs du formulaire sont valides
	 */
	@Override
	public boolean isChampValid() {

		return (this.tfId.getText().isEmpty() || this.tfNom.getText().isEmpty() || this.tfNiveauActivation.getText().isEmpty()) ? false : true;
	}

	@Override
	public void addToList() {
		
		if (isChampValid()) {

			// créer un élément avec les champs du formulaire
			Chaine chaine = new Chaine(
					tfId.getText(), 
					tfNom.getText(), 
					Integer.parseInt(tfNiveauActivation.getText())
				);

			// rajoute cet objet dans la liste des éléments
			ObservableList<Chaine> chaineObservable = this.table.getItems();
			chaineObservable.add(chaine);

			this.table.setItems(chaineObservable);
			clearTextField();

			try {
				Chaine.addChaineToCSV(chaine, Path.CHAINE.getPath());
			
			} catch (Exception e) {

				e.printStackTrace();
			}

		} else {

			MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Les champs ne sont pas valides.");
		}
	}

	@Override
	public void removeToList() {
		
		try {
			Chaine.removeChaineToCSV(this.table.getSelectionModel().getSelectedItem().getId(), Path.CHAINE.getPath());
		} catch (Exception e) {
			MessageUtils.messageAlert(AlertType.ERROR, "Erreur", e.getMessage());
		}
		this.table.getSelectionModel().getSelectedItems().forEach(this.table.getItems()::remove);
	}
	
	@Override
	public void transforme() {
	
		boolean valide = true;
		
		HashMap<String, Element> elements = (HashMap<String, Element>) Element.CSVToElementMap(Path.ELEMENT.getPath());
		
		for(Chaine chaine : this.table.getItems()) {
			
			for(Element element : chaine.getElementsEntree()) {
								
				// On récupère l'élément du CSV avec l'identifiant de l'élément en entrée de chaîne et on soustrait la quantité
				if (elements.get(element.getId()).decrementeQuantite(element.getQuantite()) < 0) {
										
					if (elements.get(element.getId()).getPrixAchat() == -1.0) {
						
						valide = false;
					}
				}
			}
			
			for(Element element : chaine.getElementsSortie()) {
				
				elements.get(element.getId()).incrementeQuantite(element.getQuantite());
			}
			
		}
		
		if (!valide) {
			
			MessageUtils.messageAlert(AlertType.ERROR, "Transformation annulée", "Il y a un problème avec la quantité des éléments en entrée et les éléments présent en stock");

		} else {
			
			Element.clearCSV(Path.ELEMENT.getPath());
			
			elements.forEach((id, element) -> {
				
				Element.addElementToCSV(element, Path.ELEMENT.getPath());
			});
			
			MessageUtils.messageAlert(AlertType.INFORMATION, "Fin de la transformation", "La transformation a bien été effectuée");

		}	
	}

	public void reinitializeTableAndChaines(){
		try {
			this.chaines.clear();
			this.chaines.addAll(Chaine.CSVToChaine(Path.CHAINE.getPath()));
			this.table.getItems().clear();
			this.table.getItems().addAll(this.chaines);
		}catch (Exception e) {
			MessageUtils.messageAlert(Alert.AlertType.ERROR, "Erreur", e.getMessage());
			PersistenceUtils.savePath(Path.CHAINE, "");
		}
	}


	void setStage(Stage stg) { 
		stage = stg;
	}

}