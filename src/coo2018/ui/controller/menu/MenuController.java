package coo2018.ui.controller.menu;

import coo2018.model.Chaine;
import coo2018.model.Element;
import coo2018.utils.message.MessageUtils;
import coo2018.utils.persistence.Path;
import coo2018.utils.persistence.PersistenceUtils;
import coo2018.utils.rooting.Route;
import coo2018.utils.rooting.RoutingUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private Button bImportElement;

    @FXML
    private Button bImportChaine;

    @FXML
    private Button bElement;

    @FXML
    private Button bChaine;

    public void initialize(URL url, ResourceBundle rb){
        this.bElement.setOnAction(actionEvent -> {
            RoutingUtils.goTo(actionEvent, Route.ELEMENT);
        });

        this.bChaine.setOnAction(actionEvent -> {

            if (!Path.ELEMENT.getPath().equals("")) {
                RoutingUtils.goTo(actionEvent, Route.CHAINE);
            } else {
                MessageUtils.messageAlert(Alert.AlertType.ERROR, "Fichier manquant", "Vous devez d'abord renseigner un fichier d'éléments");
            }
        });

        this.bImportChaine.setOnAction(actionEvent -> {
            openAndSaveFile(Path.CHAINE);
        });

        this.bImportElement.setOnAction(actionEvent -> {
            openAndSaveFile(Path.ELEMENT);
        });
    }

    Stage stage;

    void setStage(Stage stg) {
        stage = stg;
    }

    public void openAndSaveFile(Path fileToWrite){
        // On créer un FileChooser pour choisir un fichier dans l'ordinateur
        FileChooser fileChooser = new FileChooser();
        // Les fichiers sélectionner ne seront que du type ".csv"
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(this.stage);

        PersistenceUtils.savePath(fileToWrite, file.getAbsolutePath());
    }
}
