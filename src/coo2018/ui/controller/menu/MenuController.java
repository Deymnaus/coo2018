package coo2018.ui.controller.menu;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import coo2018.utils.message.MessageUtils;
import coo2018.utils.persistence.Path;
import coo2018.utils.persistence.PersistenceUtils;
import coo2018.utils.persistence.dao.impl.ChaineDAO;
import coo2018.utils.rooting.Route;
import coo2018.utils.rooting.RoutingUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    private Stage stage;

    @FXML
    private Button bImportElement;

    @FXML
    private Button bImportChaine;

    @FXML
    private Button bElement;

    @FXML
    private Button bChaine;

    @FXML
    private Button bAchat;

    public void initialize(URL url, ResourceBundle rb){

        this.bElement.setOnAction(actionEvent -> {
            RoutingUtils.goTo(actionEvent, Route.ELEMENT);
        });

        this.bChaine.setOnAction(actionEvent -> {

            if (!Path.ELEMENT.getPath().equals("")) {
            	
            	try {
    				new ChaineDAO().findAll();
                    RoutingUtils.goTo(actionEvent, Route.CHAINE);

    			} catch (Exception e) {

    				e.printStackTrace();
    				MessageUtils.messageAlert(AlertType.ERROR, "Erreur", e.getMessage());
    			}
            	
            } else {
                MessageUtils.messageAlert(Alert.AlertType.ERROR, "Erreur", "Renseigner d'abord un fichier d'éléments.");
            }
        });

        this.bAchat.setOnAction(actionEvent -> {
            RoutingUtils.goTo(actionEvent, Route.ELEMENT_ACHAT);
        });

        this.bImportChaine.setOnAction(actionEvent -> {
            try {
                openAndSaveFile(Path.CHAINE);
            } catch (Exception e) {
                MessageUtils.messageAlert(Alert.AlertType.ERROR, "Erreur", "Importation annulée.");
            }
        });

        this.bImportElement.setOnAction(actionEvent -> {
            try {
                openAndSaveFile(Path.ELEMENT);
            } catch (Exception e) {
                MessageUtils.messageAlert(Alert.AlertType.ERROR, "Erreur", "Importation annulée.");
            }
        });
    }

    /**
     * Permet d'importer un fichier
     * @param fileToWrite du fichier à sauvergarder
     */

    public void openAndSaveFile(Path fileToWrite){
        // On créer un FileChooser pour choisir un fichier dans l'ordinateur
        FileChooser fileChooser = new FileChooser();
        // Les fichiers sélectionner ne seront que du type ".csv"
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(this.stage);

        PersistenceUtils.savePath(fileToWrite, file.getAbsolutePath());
    }

    void setStage(Stage stg) {
        stage = stg;
    }
}
