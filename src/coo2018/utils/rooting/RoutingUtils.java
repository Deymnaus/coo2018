package coo2018.utils.rooting;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RoutingUtils {
	
	public static void goTo(ActionEvent actionEvent, Route route) {
		
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
