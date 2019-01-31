package coo2018.ui.controller.chaine;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import coo2018.model.Chaine;
import coo2018.model.Element;
import coo2018.utils.message.MessageUtils;
import coo2018.utils.persistence.Path;
import coo2018.utils.rooting.Route;
import coo2018.utils.rooting.RoutingUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * s
 * @author Andréa Christophe
 *
 */
public class ChaineController implements Initializable {

	private ObservableList<Chaine> chaines = FXCollections.observableArrayList();

	@FXML
	private MenuItem openFile;
	
	@FXML
	private Button bRetour;
	
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

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		String res = Path.CHAINE.getPath();
		
		if (!res.equals("")) {
			
			// On clear la liste de chaînes (pour ajouter les nouveaux objets)  
			this.chaines.clear();
			this.chaines.addAll(Chaine.CSVToChaine(res));
			
			// On clear la tableview et on ajoute les nouveaux objets 
			this.table.getItems().clear();
			this.table.getItems().addAll(this.chaines);
		}
		
		// Au click sur Fichier > Ouvrir 
		this.openFile.setOnAction(keyEvent -> {

			try {
				
				// On créer un FileChooser pour choisir un fichier dans l'ordinateur 
				FileChooser fileChooser = new FileChooser();
				
				// Les fichiers sélectionner ne seront que du type ".csv"
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
				fileChooser.getExtensionFilters().add(extFilter);
				File file = fileChooser.showOpenDialog(this.stage);
					
				// Si le fichier est valide (CF : commentaires de la méthode isValide())
				if (isValide()) {
					
					Path.savePath(Path.CHAINE, file.getAbsolutePath());
					
					this.chaines.clear();
					this.chaines.addAll(Chaine.CSVToChaine(file.getAbsolutePath()));
					
					this.table.getItems().clear();
					this.table.getItems().addAll(this.chaines);
				} else {
					
					MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Le fichier que vous essayez de charger contient des éléments non présent dans le fichier des éléments actuel");
				}
				
				
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
				addChaineToList();
		});

		this.tfNom.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER))
				addChaineToList();
		});

		this.tfNiveauActivation.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER))
				addChaineToList();
		});

		this.bAjouter.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER))
				addChaineToList();
		});

		this.bAjouter.setOnAction(actionEvent -> {

			addChaineToList();
		});

		this.bSupprimer.setOnKeyPressed(keyEvent -> {

			if (keyEvent.getCode().equals(KeyCode.ENTER)) {

				try {
					Chaine.removeChaineToCSV(this.table.getSelectionModel().getSelectedItem().getId(), Path.CHAINE.getPath());
				} catch (IOException e) {

					e.printStackTrace();
				}
				this.table.getSelectionModel().getSelectedItems().forEach(this.table.getItems()::remove);
			}
		});

		this.bSupprimer.setOnAction(actionEvent -> {

			try {
				Chaine.removeChaineToCSV(this.table.getSelectionModel().getSelectedItem().getId(), Path.CHAINE.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.table.getSelectionModel().getSelectedItems().forEach(this.table.getItems()::remove);
		});
				
		this.bVisualiser.setOnAction(actionEvent -> {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../../view/chaine/ChaineDetailPresenter.fxml"));
			
			try {
				loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ChaineDetailController display = loader.getController();
			
			display.setTabElements(this.table.getSelectionModel().getSelectedItem().getElementsEntree());
			
			Parent p = loader.getRoot();
			Stage stage = new Stage();
			stage.setScene(new Scene(p));
			stage.showAndWait();
			
			
			
//			RoutingUtils.goTo(actionEvent, Route.CHAINE_DETAIL);
		});
	}
	
	public void addChaineToList() {

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

			Chaine.addChaineToCSV(chaine, Path.CHAINE.getPath());

		} else {

			MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Les champs ne sont pas valides.");
		}
	}	
	
	/**
	 * Remet la valeur des champs du formulaire à defaut 
	 */
	public void clearTextField() {

		this.tfId.setText("");
		this.tfNom.setText("");
		this.tfNiveauActivation.setText("");	}

	/**
	 * @return true si tout les champs du formulaire sont valides
	 */
	public boolean isChampValid() {

		return (this.tfId.getText().isEmpty() || this.tfNom.getText().isEmpty() || this.tfNiveauActivation.getText().isEmpty()) ? false : true;
	}
	
	/**
	 * Une fichier chaine.csv est valide si tout les éléments en entrées/sorties des chaînes sont présent dans le fichier elements.csv
	 * 
	 * @return
	 */
	public boolean isValide() {
		
		boolean res = true;
		
		// On parcours les chaînes pour tester si les éléments en entrées/sorties des chaînes sont bien présent dans le fichier element.csv
		for (Chaine chaine : Chaine.CSVToChaine(Path.CHAINE.getPath())) {
			
			System.out.println("Chaine :\nElements entrée :");
			
			for (Element elementEntree : chaine.getElementsEntree()) {
				
				if (Element.CSVToElementMap(Path.ELEMENT.getPath()).containsKey(elementEntree.getId())) {
					
					System.out.println("OUI CONTIENT");
				} else {
					
					return false;
				}
				System.out.println(elementEntree.toString());
			}
			
			for (Element elementSortie : chaine.getElementsSortie()) {
				
				if (Element.CSVToElementMap(Path.ELEMENT.getPath()).containsKey(elementSortie.getId())) {
					
					System.out.println("OUI CONTIENT");
				} else {
					
					return false;
				}
				System.out.println(elementSortie.toString());
			}
		}
		
		return res;
	}
	
	Stage stage;

	void setStage(Stage stg) { 
		stage = stg;
	}

}