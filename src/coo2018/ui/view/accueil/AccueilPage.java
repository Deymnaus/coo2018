package coo2018.ui.view.accueil;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import coo2018.utils.message.MessageUtils;
import coo2018.utils.rooting.Route;
import coo2018.utils.rooting.RoutingUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
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
	
	private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        // Set title for DirectoryChooser
        directoryChooser.setTitle("Select Some Directories");
 
        // Set Initial Directory
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

	/**
	 * Créer et charge la page d'accueil
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

//		MessageUtils.messageAlert(AlertType.INFORMATION, "Configuration", "Renseignez un dossier où seront stockés les fichiers de configuration.");
//		
//		final DirectoryChooser directoryChooser = new DirectoryChooser();
//        configuringDirectoryChooser(directoryChooser);
//		
//        
//        File dir = directoryChooser.showDialog(primaryStage);
//        if (dir != null) {
//        	System.out.println(dir.getAbsolutePath());
//        } else {
//        	System.out.println(dir.getAbsolutePath());
//        }
//        
//		try {
//			
//			PrintWriter simulation = new PrintWriter("elementsSimulationTest.csv", "UTF-8");
//			simulation.close();
//			
//			PrintWriter achat = new PrintWriter("elementsAchatTest.csv", "UTF-8");
//			achat.println("id,nom,quantite,unite,prixAchat,prixVente");
//			achat.close();
//			
//			PrintWriter values = new PrintWriter("valuesTest.csv", "UTF-8");
//			values.println("elementPath,chainePath,elementSimulationPath,elementAchatPath\n"
//					+ ";;elementsSimulationTest.csv;elementsAchatTest.csv");
//			values.close();
//			
//		} catch (Exception e) {
//			
//		}
		
		
		try {
			
			Parent elementPageParent = FXMLLoader.load(RoutingUtils.class.getResource(Route.ELEMENT.getPath()));
			Scene elementPageScene = new Scene(elementPageParent);
			Stage sceneActuel = primaryStage;
			sceneActuel.setScene(elementPageScene);
			primaryStage.setTitle("Gestion de la production");
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
