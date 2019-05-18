package coo2018.ui.controller.element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import coo2018.model.Element;
import coo2018.utils.message.MessageUtils;
import coo2018.utils.persistence.Path;
import coo2018.utils.rooting.Route;
import coo2018.utils.rooting.RoutingUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Andréa Christophe
 *
 */
public class ElementAchatController implements Initializable {

    private ObservableList<Element> elements = FXCollections.observableArrayList();

    @FXML
    private TableView<Element> table;
    
    private Stage stage;

    @FXML
    private Button openFile;

    @FXML
    private Button bAcheter;

    @FXML
    private Text tPrixTotal;

    @FXML
    private Button bExport;

    private int prixTotal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.prixTotal = 0;
        this.elements.clear();

        //List<Element> elementAAcheter = new ArrayList<Element>();

        Element.CSVToElement(Path.ELEMENT.getPath()).forEach(element -> {

            if (element.getQuantite() < 0) {

                element.setQuantite(element.getQuantite()*(-1));
                this.elements.add(element);

                prixTotal += element.getQuantite()*element.getPrixAchat();
            }
        });

        this.tPrixTotal.setText("Prix total : " + new DecimalFormat("#,##0").format(this.prixTotal) + " €");

        //this.elements.addAll(elementAAcheter);
        this.table.getItems().clear();
        this.table.getItems().addAll(this.elements);


        /*
         * Événements liés au boutons de l'interface
         */

        this.bExport.setOnAction(actionEvent -> {
            exportFile();
        });

        this.bAcheter.setOnAction(actionEvent -> {

            HashMap<String, Element> elementsAvecAchat = (HashMap<String, Element>) Element.CSVToElementMap(Path.ELEMENT.getPath());

            if (!this.elements.isEmpty()) {

                Optional<ButtonType> result = MessageUtils.messageAlert(AlertType.CONFIRMATION, "Validation", "Voulez-vous acheter les éléments manquant pour un prix de " + new DecimalFormat("#,##0").format(this.prixTotal) + "€ ?");
                if (result.get() == ButtonType.OK){

                    Element.clearCSV(Path.ELEMENT.getPath());

                    elementsAvecAchat.forEach((id, element) -> {

                        this.elements.forEach(elementAAcheter -> {

                            if (element.getQuantite() < 0) {

                                if (elementAAcheter.getId().equals(id)) {

//									element.setQuantite(elementAAcheter.getQuantite());
                                    element.setQuantite(0);
                                    Element.addElementToCSV(element, Path.ELEMENT.getPath());
                                }

                            } else {

                                Element.addElementToCSV(element, Path.ELEMENT.getPath());
                            }
                        });
                    });

                    this.elements.clear();
                    this.table.getItems().clear();

                    RoutingUtils.goTo(actionEvent, Route.ELEMENT);
                }

            } else {

                MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Aucun élément à acheter");
            }
        });
    }

    /**
     * Permet d'exporter et d'enregister le fichier liste d'achat
     */

    public void exportFile(){
        // On créer un FileChooser pour choisir un fichier dans l'ordinateur
        FileChooser fileChooser = new FileChooser();
        // Le fichier enregistrer sera du type ".csv"
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(this.stage);
        if (file != null) {
            writeFile(file);
        }
    }

    /**
     * Permet d'écrire un fichier avec les élements à acheter
     * @param file fichier sur lequel écrire
     */

    private void writeFile(File file){
        try {
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(file);
            String a = this.table.getItems().toString();
            fileWriter.write(a);
            fileWriter.close();
        } catch (IOException ex) {
            MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Impossible d'enregistrer le fichier.");
        }

    }
    void setStage(Stage stg) {
        stage = stg;
    }


}