package coo2018.utils.rooting;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * 
 * @author Andréa Christophe
 *
 */
public class RoutingUtils {
	
	/**
	 * Lance la vue passer en paramètre 
	 * 
	 * @param actionEvent
	 * @param route
	 */
	public static void goTo(KeyEvent actionEvent, Route route) {
		
		try {
			Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
			Parent elementPageParent = FXMLLoader.load(RoutingUtils.class.getResource(route.getPath()));
			Scene elementPageScene = new Scene(elementPageParent);
			Stage sceneActuel = stage;
			sceneActuel.setScene(elementPageScene);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
