package coo2018.ui.view.accueil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
		
		Parent root = FXMLLoader.load(getClass().getResource("AccueilPresenter.fxml"));
		primaryStage.setTitle("Gestion de production");
		primaryStage.setScene(new Scene(root));
		primaryStage.setMaximized(false);
        primaryStage.centerOnScreen();
		primaryStage.show();
	}
}
