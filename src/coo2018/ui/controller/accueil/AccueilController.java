package coo2018.ui.controller.accueil;

import java.net.URL;
import java.util.ResourceBundle;

import coo2018.utils.message.MessageUtils;
import coo2018.utils.persistence.Path;
import coo2018.utils.rooting.Route;
import coo2018.utils.rooting.RoutingUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		this.bElement.setOnAction(actionEvent -> {

			RoutingUtils.goTo(actionEvent, Route.ELEMENT);
		});
		
		this.bChaine.setOnAction(actionEvent -> {

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