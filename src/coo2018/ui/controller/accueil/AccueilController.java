package coo2018.ui.controller.accueil;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import coo2018.model.Chaine;
import coo2018.utils.message.MessageUtils;
import coo2018.utils.persistence.Path;
import coo2018.utils.persistence.PersistenceUtils;
import coo2018.utils.rooting.Route;
import coo2018.utils.rooting.RoutingUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Andréa Christophe
 *
 */
public class AccueilController implements Initializable {

	@FXML
	private Button bElement;

	@FXML
	private Button bChaine;
	
	@FXML
	private Button openFileElement;
	
	@FXML
	private Button openFileChaine;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		this.openFileElement.setOnAction(keyEvent -> {

			try {
				
				// On créer un FileChooser pour choisir un fichier dans l'ordinateur 
				FileChooser fileChooser = new FileChooser();
				
				// Les fichiers sélectionner ne seront que du type ".csv"
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
				fileChooser.getExtensionFilters().add(extFilter);
				File file = fileChooser.showOpenDialog(this.stage);
					
				
				PersistenceUtils.savePath(Path.CHAINE, file.getAbsolutePath());
				
				
			} catch (Exception e) {
				
				MessageUtils.messageAlert(AlertType.ERROR, "Erreur", e.getMessage());
				PersistenceUtils.savePath(Path.CHAINE, "");
			}
			
		});
		
		this.bElement.setOnKeyPressed(actionEvent -> {
			RoutingUtils.goTo(actionEvent, Route.ELEMENT);
		});
		
		this.bChaine.setOnKeyPressed(actionEvent -> {

			if (!Path.ELEMENT.getPath().equals("")) {
				
				RoutingUtils.goTo(actionEvent, Route.CHAINE);
			} else {
				
				MessageUtils.messageAlert(AlertType.ERROR, "Fichier manquant", "Vous devez d'abord renseigner un fichier d'éléments");
			}
		});
	}

	Stage stage;

	void setStage(Stage stg) { 
		stage = stg;
	}

}