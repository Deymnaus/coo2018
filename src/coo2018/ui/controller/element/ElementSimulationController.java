package coo2018.ui.controller.element;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import coo2018.App;
import coo2018.model.Element;
import coo2018.ui.controller.menu.MenuController;
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

/**
 *
 * @author Andréa Christophe
 *
 */
public class ElementSimulationController implements Initializable {

    private ObservableList<Element> elements = FXCollections.observableArrayList();

    @FXML
    private Button openFile;

    @FXML
    private Button bValider;

    @FXML
    private Button bAnnuler;

    @FXML
    private TableView<Element> table;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        String res = Path.ELEMENT_SIMULATION.getPath();
//        File tempFile = new File(res);

        // Si l'utilisateur n'a jamais renseigné de fichier CSV ou que le chemin du fichier est incorrect
//        if (!res.equals("") && tempFile.exists()) {
            this.elements.clear();
            
//            this.elements.addAll(Element.CSVToElement(res));
            this.elements.addAll(MenuController.elementsSimulation);
            this.table.getItems().clear();
            this.table.getItems().addAll(this.elements);
//        }


        /*
         * Événements liés au boutons de l'interface
         */

        this.bValider.setOnAction(actionEvent -> {

            if (!this.table.getItems().isEmpty()) {

                Element.clearCSV(Path.ELEMENT.getPath());

//                Element.CSVToElement(Path.ELEMENT_SIMULATION.getPath()).forEach(element -> {
//
//                    Element.addElementToCSV(element, Path.ELEMENT.getPath());
//                });
                
                MenuController.elementsSimulation.forEach(element -> {

                	try {
						App.elementDao.create(element);
					} catch (Exception e) {
						e.printStackTrace();
					}
//                    Element.addElementToCSV(element, Path.ELEMENT.getPath());
                });

                MenuController.elementsSimulation.clear();
//                Element.clearCSV(Path.ELEMENT_SIMULATION.getPath());

                this.elements.clear();
                this.table.getItems().clear();

                RoutingUtils.goTo(actionEvent, Route.ELEMENT);

            } else {

                MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Aucune simulation en cours");
            }


        });

        this.bAnnuler.setOnAction(actionEvent -> {

            Optional<ButtonType> result = MessageUtils.messageAlert(AlertType.CONFIRMATION, "Validation", "Voulez-vous abandonner cette simulation ?");
            if (result.get() == ButtonType.OK){

            	MenuController.elementsSimulation.clear();
//                Element.clearCSV(Path.ELEMENT_SIMULATION.getPath());

                this.elements.clear();
                this.table.getItems().clear();

                RoutingUtils.goTo(actionEvent, Route.CHAINE);

            }
        });


    }
}