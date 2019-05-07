package coo2018.ui.view.accueil;

import java.io.IOException;

import coo2018.utils.rooting.Route;
import coo2018.utils.rooting.RoutingUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Andréa Christophe
 *
 */
public class AccueilPage extends Application {

	public static void main(String[] args) {

		launch(args);
	}

	/**
	 * Créer et charge la page d'accueil
	 */

	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
			
			Parent elementPageParent = FXMLLoader.load(RoutingUtils.class.getResource(Route.ELEMENT.getPath()));
			Scene elementPageScene = new Scene(elementPageParent);
			Stage sceneActuel = primaryStage;
			sceneActuel.setScene(elementPageScene);
			primaryStage.show();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
//		Parent root = FXMLLoader.load(getClass().getResource("ElementPresenter.fxml"));
//		primaryStage.setTitle("Gestion de production");
//		primaryStage.setScene(new Scene(root));
//		primaryStage.setMaximized(false);
//		primaryStage.centerOnScreen();
//		primaryStage.show();
		
	}
}
