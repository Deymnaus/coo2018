package coo2018.ui.controller.chaine;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

import coo2018.App;
import coo2018.model.Chaine;
import coo2018.model.Element;
import coo2018.ui.IActionFormulaire;
import coo2018.ui.controller.menu.MenuController;
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

	//contient toutes les chaînes de production qui ne demandent que des matières premières
	private ArrayList<Chaine> listeChaineMatierePremiere = new ArrayList<>();

	private ArrayList<Chaine> listeChaine = new ArrayList<>();

	private ArrayList<Chaine> listeChaineNonBloquante = new ArrayList<>();

	private ArrayList<Chaine> listeChaineBloquante = new ArrayList<>();

	private HashMap<String, Integer> elementsManquant = new HashMap<>();

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		String res = "";
		File tempFile = null;
		this.tempsTotal = 0;
		
		try {
			res = Path.CHAINE.getPath();
			tempFile = new File(res);

		} catch(Exception e) {
			
		}
		
		if (!res.equals("") && tempFile != null) {

			// On clear la liste de chaînes (pour ajouter les nouveaux objets)
			this.chaines.clear();
			try {
				this.chaines.addAll(App.chaineDao.findAll());

			} catch (Exception e) {

				e.printStackTrace();
				MessageUtils.messageAlert(AlertType.ERROR, "Erreur", e.getMessage());
			}

			// On clear la tableview et on ajoute les nouveaux objets
			this.table.getItems().clear();
			this.table.getItems().addAll(this.chaines);
		} else {
			
			MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Vous n'avez renseignez aucun fichier de chaînes de production.");
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
			
//			if (!Element.CSVToElementMap(Path.ELEMENT_SIMULATION.getPath()).isEmpty()) {
			if (!MenuController.elementsSimulation.isEmpty()) {
				
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

//			if (!Element.CSVToElementMap(Path.ELEMENT_SIMULATION.getPath()).isEmpty()) {
			if (!MenuController.elementsSimulation.isEmpty()) {
				
				MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Une simulation est déjà en cours, cliquez sur visualier.");
				
			} else {
				
				optimizer();
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
				App.chaineDao.create(chaine);
//				Chaine.addChaineToCSV(chaine, Path.CHAINE.getPath());

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
			App.chaineDao.delete(this.table.getSelectionModel().getSelectedItem());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {			
			this.table.getSelectionModel().getSelectedItems().forEach(this.table.getItems()::remove);
		} catch (Exception e) {
			MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Aucun chaîne selectionnée.");
		}
		
		
//		System.out.println("Elem : " + this.table.getSelectionModel().getSelectedItem().toString());
	}
	
	public boolean estChaineValide(Chaine c) {
		
		boolean res = true;
		Map<String, Element> elementsStock = null;
		List<Element> elementsEntree = c.getElementsEntree();
		
//		if (!Element.CSVToElementMap(Path.ELEMENT_SIMULATION.getPath()).isEmpty()) {
		if (!MenuController.elementsSimulation.isEmpty()) {
			
//			elementsStock = (HashMap<String, Element>) Element.CSVToElementMap(Path.ELEMENT_SIMULATION.getPath());
			elementsStock = new HashMap<String, Element>();
			for (Element element : MenuController.elementsSimulation) {
				
				elementsStock.put(element.getId(), element);
			}
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
		
		MenuController.elementsAchat.clear();
		MenuController.elementsSimulation.clear();

		for(int i=0; i<this.table.getItems().size(); i++) {
			
//			if (!Element.CSVToElementMap(Path.ELEMENT_SIMULATION.getPath()).isEmpty()) {
//				elements = (HashMap<String, Element>) Element.CSVToElementMap(Path.ELEMENT_SIMULATION.getPath());
////				System.out.println(elements.toString());
//			}

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
			
//			Element.clearCSV(Path.ELEMENT_SIMULATION.getPath());

			elements.forEach((id, element) -> {

				MenuController.elementsSimulation.add(element);
				//Element.addElementToCSV(element, Path.ELEMENT_SIMULATION.getPath());
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
			this.chaines.addAll(App.chaineDao.findAll());
			this.table.getItems().clear();
			this.table.getItems().addAll(this.chaines);
		}catch (Exception e) {
			MessageUtils.messageAlert(Alert.AlertType.ERROR, "Erreur", e.getMessage());
			PersistenceUtils.savePath(Path.CHAINE, "");
		}
	}


	public void optimizer(){
		initialiseListe();
		HashMap<String, Element> elements = (HashMap<String, Element>) Element.CSVToElementMap(Path.ELEMENT.getPath());;
		tempsTotal = 0;
		calculTemps(listeChaineMatierePremiere);
		elements = transformeListe(listeChaineMatierePremiere, elements);
		HashMap<String, Element> elementsTemp = elements;
		for(int i=0;i<listeChaineNonBloquante.size();i++){
			boolean elementsDisponible = true;
			for(Element element : listeChaineNonBloquante.get(i).getElementsEntree()) {
				if(element.getQuantite()>elements.get(element.getId()).getQuantite() && element.getPrixAchat()==-1.0){
					elementsDisponible = false;
					elementsManquant.put(element.getId(),element.getQuantite()-elements.get(element.getId()).getQuantite());
				}else{
					elementsTemp.get(element.getId()).decrementeQuantite(element.getQuantite());
				}
			}
			if(!elementsDisponible){
				listeChaineBloquante.add(listeChaineNonBloquante.get(i));
				listeChaineNonBloquante.remove(i);
				i--;
			}
		}
		calculTemps(listeChaineNonBloquante);
		elements = transformeListe(listeChaineNonBloquante, elements);
		listeChaineNonBloquante.clear();
		prodList(elements);
		calculTemps(listeChaine);
		elements = transformeListe(listeChaine,elements);
		listeChaine.clear();
		if(tempsTotal>60){
			MessageUtils.messageAlert(AlertType.INFORMATION, "Information", "Il n'est pas possible de " +
					"lancer la simulation car elle dépasse les 60h de production.\nTemps total : " + tempsTotal + "h.");
		}else{
			int compteur = 0;
			while(!listeChaineBloquante.isEmpty() && compteur<3) {
				if (gestionListeBloquante(elements)) {
					calculTemps(listeChaineBloquante);
					elements = transformeListe(listeChaineBloquante, elements);
				}else{
					compteur ++;
				}
			}
			if(!listeChaineBloquante.isEmpty()){
				String nomsElements = "";
				for (Map.Entry mapentry : elementsManquant.entrySet()) {
					nomsElements += mapentry.getValue()+" de "
							+ mapentry.getKey()+" ";
				}
				MessageUtils.messageAlert(AlertType.INFORMATION, "Information", "La transformation a été partiellement " +
						" effectuée. Il manque les élements suivants : "+nomsElements+
						"\nTemps total : " + tempsTotal + "h.");
			}else{
				MessageUtils.messageAlert(AlertType.INFORMATION, "Information", "La transformation a " +
						"été effectuée avec succès.\nTemps total : " + tempsTotal + "h.");
			}
		}
		MenuController.elementsSimulation.clear();
//		Element.clearCSV(Path.ELEMENT_SIMULATION.getPath());

		elements.forEach((id, element) -> {

			
//			Element.addElementToCSV(element, Path.ELEMENT_SIMULATION.getPath());
			MenuController.elementsSimulation.add(element);

		});
	}

	public void calculTemps(ArrayList<Chaine> chaine){
		int temps = 0;
		for(int i=0;i<chaine.size();i++){
			if(chaine.get(i).getTemps()>temps){
				temps = chaine.get(i).getTemps();
			}
		}
		tempsTotal += temps;
	}

	public boolean gestionListeBloquante(HashMap<String, Element> elements){
		boolean debloque = false;
		for(int i=0;i<listeChaineBloquante.size();i++){
			boolean hasAllElement = true;
			for(Element element : listeChaineBloquante.get(i).getElementsEntree()){
				if(elementsManquant.containsKey(element.getId())){
					hasAllElement = false;
				}
			}
			if(hasAllElement){
				listeChaine.add(listeChaineBloquante.get(i));
				listeChaineBloquante.remove(i);
				i--;
				debloque = true;
			}
		}
		return debloque;
	}

	public void prodList(HashMap<String, Element> elements){
		for(int i=0;i<listeChaine.size();i++) {
//			System.out.println(listeChaine.get(i).getId());
			boolean elementsDisponible = true;
			for (Element element : listeChaine.get(i).getElementsEntree()) {
				if (element.getQuantite() > elements.get(element.getId()).getQuantite() && element.getPrixAchat() == -1) {
					elementsDisponible = false;
					elementsManquant.put(element.getId(), element.getQuantite() - elements.get(element.getId()).getQuantite());
				}else{
//					System.out.println(listeChaine.get(i).getId()+" "+element.getId()+ " "+element.getQuantite()+ " "+elements.get(element.getId()).getQuantite());
					elements.get(element.getId()).decrementeQuantite(element.getQuantite());
				}
			}
			if(!elementsDisponible){
				listeChaineBloquante.add(listeChaine.get(i));
				listeChaine.remove(i);
				i--;
			}
		}

	}

	public HashMap<String, Element> transformeListe(ArrayList<Chaine> listeChaine, HashMap<String, Element> elements){
		for(Chaine chaine : listeChaine) {
			for (Element element : chaine.getElementsEntree()){
				elements.get(element.getId()).decrementeQuantite(element.getQuantite());
			}
			for(Element element : chaine.getElementsSortie()){
				elements.get(element.getId()).incrementeQuantite(element.getQuantite());
				if(elementsManquant.containsKey(element.getId()) && elementsManquant.get(element.getId())>element.getQuantite()){
					elementsManquant.remove(element.getId());
				}
			}
		}
		return elements;
	}

	public void initialiseListe(){
		for(int i=0; i<this.table.getItems().size(); i++) {
			Chaine chaine = this.table.getItems().get(i);
			boolean matiereP = true;
			boolean neBloquePas = true;
			for(Element element : chaine.getElementsEntree()) {
				if(element.getPrixAchat() == -1.0) {
					matiereP = false;
				}
				for (int j = 0; j < this.table.getItems().size(); j++) {
					for (Element el : this.table.getItems().get(j).getElementsEntree()) {
						if (chaine != this.table.getItems().get(j) && element.getId() == el.getId()) {
							neBloquePas = false;
						}
					}
				}
			}
			for (int j = 0; j < chaine.getNiveauActivation(); j++){
				if(matiereP) {
					listeChaineMatierePremiere.add(chaine);
				}else if(neBloquePas){
					listeChaineNonBloquante.add(chaine);
				}else{
					listeChaine.add(chaine);
				}
			}
		}
	}

}