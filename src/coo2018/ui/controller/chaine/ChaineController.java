package coo2018.ui.controller.chaine;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import coo2018.model.Chaine;
import coo2018.model.Element;
import coo2018.ui.IActionFormulaire;
import coo2018.utils.message.MessageUtils;
import coo2018.utils.persistence.Path;
import coo2018.utils.persistence.PersistenceUtils;
import coo2018.utils.persistence.dao.DAO;
import coo2018.utils.persistence.dao.impl.ChaineDAO;
import coo2018.utils.persistence.dao.impl.ElementDAO;
import coo2018.utils.rooting.Route;
import coo2018.utils.rooting.RoutingUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * s
 * @author Andréa Christophe
 *
 */
public class ChaineController implements Initializable, IActionFormulaire, ITransformation {

	private ObservableList<Chaine> chaines = FXCollections.observableArrayList();

	@FXML
	private TableView<Chaine> table;

	@FXML
	private TextField tfId;

	@FXML
	private TextField tfNom;

	@FXML
	private TextField tfNiveauActivation;
	
	@FXML
	private TextField tfTemps;

	@FXML
	private Button bAjouter;

	@FXML
	private Button bSupprimer;

	@FXML
	private Button bVisualiser;

	@FXML
	private Button bProduction;
	
	@FXML
	private Button bDetail;
	
	private int tempsTotal;
	
	DAO<Chaine> chaineDao = new ChaineDAO();

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		String res = Path.CHAINE.getPath();
		File tempFile = new File(res);
		
		this.tempsTotal = 0;

		if (!res.equals("") && tempFile.exists()) {

			// On clear la liste de chaînes (pour ajouter les nouveaux objets)
			this.chaines.clear();
			try {
				this.chaines.addAll(chaineDao.findAll());

			} catch (Exception e) {

				e.printStackTrace();
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
		
		this.tfTemps.setOnKeyPressed(keyEvent -> {

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

				removeToList();
			}
		});

		this.bSupprimer.setOnAction(actionEvent -> {

			removeToList();
		});

		this.bVisualiser.setOnAction(actionEvent -> {
			
			if (!Element.CSVToElementMap(Path.ELEMENT_SIMULATION.getPath()).isEmpty()) {
				
				try {
					RoutingUtils.goTo(actionEvent, Route.ELEMENT_SIMULATION);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else {
				
				MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Aucune simulation en cours.");
			}
		});

		this.bProduction.setOnAction(actionEvent -> {

			if (!Element.CSVToElementMap(Path.ELEMENT_SIMULATION.getPath()).isEmpty()) {
				
				MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Une simulation est déjà en cours, cliquez sur visualier.");
				
			} else {
				
				transforme();
			}
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
		this.tfTemps.setText("");
	}

	/**
	 * @return true si tout les champs du formulaire sont valides
	 */
	@Override
	public boolean isChampValid() {

		return (this.tfId.getText().isEmpty() || this.tfNom.getText().isEmpty() || this.tfNiveauActivation.getText().isEmpty() || this.tfTemps.getText().isEmpty()) ? false : true;
	}

	@Override
	public void addToList() {

		if (isChampValid()) {

			// créer un élément avec les champs du formulaire
			Chaine chaine = new Chaine(
					tfId.getText(),
					tfNom.getText(),
					Integer.parseInt(tfNiveauActivation.getText()),
					Integer.parseInt(tfTemps.getText())
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

			MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Champs invalides.");
		}
	}

	@Override
	public void removeToList() {

		try {
			chaineDao.delete(this.table.getSelectionModel().getSelectedItem());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {			
			this.table.getSelectionModel().getSelectedItems().forEach(this.table.getItems()::remove);
		} catch (Exception e) {
			MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Aucun chaîne selectionnée.");
		}
		
		
		System.out.println("Elem : " + this.table.getSelectionModel().getSelectedItem().toString());
	}
	
	public boolean estChaineValide(Chaine c) {
		
		boolean res = true;
		Map<String, Element> elementsStock = null;
		List<Element> elementsEntree = c.getElementsEntree();
		
		if (!Element.CSVToElementMap(Path.ELEMENT_SIMULATION.getPath()).isEmpty()) {
			
			elementsStock = (HashMap<String, Element>) Element.CSVToElementMap(Path.ELEMENT_SIMULATION.getPath());
		} else {
			
			elementsStock = Element.CSVToElementMap(Path.ELEMENT.getPath());
		}
		
		if (c.getNiveauActivation() == 0) {
			
			return false;
		}
		
		for(Element element : elementsEntree) { 
			Element elementStock = elementsStock.get(element.getId());
			
			if (elementStock.getQuantite()-element.getQuantite()*c.getNiveauActivation() < 0) {
				
				if (element.getPrixAchat() == -1.0) {
					
					res = false;
				}
			}
		}
		
		return res;
	}

	@Override
	public boolean transforme() {
		
		boolean valide = true;
		HashMap<String, Element> elements = (HashMap<String, Element>) Element.CSVToElementMap(Path.ELEMENT.getPath());;
		int tempsTotal = 0;
		
				
		for(int i=0; i<this.table.getItems().size(); i++) {
			
			if (!Element.CSVToElementMap(Path.ELEMENT_SIMULATION.getPath()).isEmpty()) {
				
				System.out.println("test");
				elements = (HashMap<String, Element>) Element.CSVToElementMap(Path.ELEMENT_SIMULATION.getPath());
				System.out.println(elements.toString());
			}

			Chaine chaine = this.table.getItems().get(i);
			
			if (estChaineValide(chaine)) {
				
				tempsTotal += chaine.getTemps()*chaine.getNiveauActivation();
				
				for(Element element : chaine.getElementsEntree()) { 
					
					if (elements.get(element.getId()).decrementeQuantite(element.getQuantite()*chaine.getNiveauActivation()) < 0) {

						if (elements.get(element.getId()).getPrixAchat() == -1.0) {

							valide = false;
							
						} else {
							
						}
					}
				}

				for(Element element : chaine.getElementsSortie()) {

					elements.get(element.getId()).incrementeQuantite(element.getQuantite()*chaine.getNiveauActivation());
				}
				
			}
			
			Element.clearCSV(Path.ELEMENT_SIMULATION.getPath());

			elements.forEach((id, element) -> {

				Element.addElementToCSV(element, Path.ELEMENT_SIMULATION.getPath());
			});

		}

		if (!valide) {

			MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Conflit entre la quantité des éléments en entrée et les éléments présent en stock.");

		} else {
			
			for(Chaine chaine : this.table.getItems()) {
				
				this.tempsTotal += chaine.getTemps();
			}

			

			MessageUtils.messageAlert(AlertType.INFORMATION, "Information", "Transformation a été effectuée avec succès.\nTemps total : " + tempsTotal + "h.");

		}
		
		return valide;
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

}