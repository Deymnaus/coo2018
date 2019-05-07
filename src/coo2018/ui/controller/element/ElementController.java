package coo2018.ui.controller.element;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import coo2018.model.Element;
import coo2018.ui.IActionFormulaire;
import coo2018.utils.message.MessageUtils;
import coo2018.utils.persistence.Path;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.soap.Text;

/**
 *
 * @author Andréa Christophe
 *
 */
public class ElementController implements Initializable, IActionFormulaire {


    private ObservableList<Element> elements = FXCollections.observableArrayList();

    @FXML
    private TableView<Element> table;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfQuantite;

    @FXML
    private TextField tfUnite;

    @FXML
    private TextField tfPrixAchat;

    @FXML
    private TextField tfPrixVente;

    @FXML
    private Button bAjouter;

    @FXML
    private Button bSupprimer;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String res = Path.ELEMENT.getPath();
        File tempFile = new File(res);

        // Si l'utilisateur n'a jamais renseigné de fichier CSV ou que le chemin du fichier est incorrect
        if (!res.equals("") && tempFile.exists()) {
            this.elements.clear();
            this.elements.addAll(Element.CSVToElement(res));
            this.table.getItems().clear();
            this.table.getItems().addAll(this.elements);
        }
        
        /*this.table.getColumns().get(0).setOnEditCommit((TableColumn<Element, String>.CellEditEvent<Element, String> idColumn) -> {
        	
        	(idColumn.getTableView().getItems().get(idColumn.getTablePosition().getRow())).setId(idColumn.getNewValue().);
        });*/


        /*
         * Événements liés au boutons de l'interface
         */

        this.tfId.setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode().equals(KeyCode.ENTER))
                addToList();
        });

        this.tfNom.setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode().equals(KeyCode.ENTER))
                addToList();
        });

        this.tfQuantite.setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode().equals(KeyCode.ENTER))
                addToList();
        });

        this.tfUnite.setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode().equals(KeyCode.ENTER))
                addToList();
        });

        this.tfPrixAchat.setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode().equals(KeyCode.ENTER))
                addToList();
        });

        this.tfPrixVente.setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode().equals(KeyCode.ENTER))
                addToList();
        });

        this.bAjouter.setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode().equals(KeyCode.ENTER))
                addToList();
        });

        this.bAjouter.setOnAction(actionEvent -> {

            addToList();
        });

        this.bSupprimer.setOnKeyPressed(keyEvent -> {

            if (keyEvent.getCode().equals(KeyCode.ENTER)) {

                removeToList();
            }
        });

        this.bSupprimer.setOnAction(actionEvent -> {

            removeToList();
        });

    }

    /**
     * Remet la valeur des champs du formulaire à defaut
     */
    @Override
    public void clearTextField() {

        this.tfId.setText("");
        this.tfNom.setText("");
        this.tfQuantite.setText("");
        this.tfUnite.setText("");
        this.tfPrixAchat.setText("");
        this.tfPrixVente.setText("");
    }


    /**
     * @return true si tout les champs du formulaire sont valides
     */
    @Override
    public boolean isChampValid() {

        return this.tfId.getText().isEmpty() || this.tfNom.getText().isEmpty() || this.tfQuantite.getText().isEmpty()
                || !isNumeric(this.tfQuantite) || this.tfUnite.getText().isEmpty() ||
                this.tfPrixAchat.getText().isEmpty() || !isNumeric(this.tfPrixAchat) ||
                this.tfPrixVente.getText().isEmpty() || !isNumeric(this.tfPrixVente) ? false : true;
    }

    /**
     * Ajoute un élément au tableau et au fichier CSV via les champs du formulaire
     */
    @Override
    public void addToList() {

        if (isChampValid()) {

            // Créer un élément avec les champs du formulaire
            Element element = new Element(
                    tfId.getText(),
                    tfNom.getText(),
                    Integer.parseInt(tfQuantite.getText()),
                    tfUnite.getText(),
                    Double.parseDouble(tfPrixAchat.getText()),
                    Double.parseDouble(tfPrixVente.getText())
            );

            // Rajoute cet objet dans la liste des éléments
            ObservableList<Element> elementObservable = this.table.getItems();
            elementObservable.add(element);

            this.table.setItems(elementObservable);
            clearTextField();

            // On rajoute l'élément dans le fichier CSV
            Element.addElementToCSV(element, Path.ELEMENT.getPath());

        } else {

            MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Champs invalides.");
        }
    }

    /**
     * Retire un élément au tableau et au fichier CSV
     */
    @Override
    public void removeToList() {

        try {
            Element.removeElementToCSV(this.table.getSelectionModel().getSelectedItem().getId(), Path.ELEMENT.getPath());
        } catch (Exception e) {
            MessageUtils.messageAlert(AlertType.ERROR, "Erreur", "Aucun élément selectionné.");
        }
        this.table.getSelectionModel().getSelectedItems().forEach(this.table.getItems()::remove);
    }

    /**
     * Permet de savoir si la valeur d'un TextField est bien numérique
     *
     * @param tf textfield à tester
     * @return true si numérique, sinon false
     */

    public boolean isNumeric(TextField tf) {
        try {
            double num = Double.parseDouble(tf.getText());
        } catch (NumberFormatException | NullPointerException ne) {
            return false;
        }
        return true;
    }

}