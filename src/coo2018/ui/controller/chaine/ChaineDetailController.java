package coo2018.ui.controller.chaine;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import coo2018.model.Element;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * 
 * @author Andréa Christophe
 *
 */
public class ChaineDetailController implements Initializable {

	private ObservableList<Element> elements = FXCollections.observableArrayList();
	public List<Element> tabElements = new ArrayList<Element>();
			
	@FXML
	private TableView<Element> tableEntree;
	
	@FXML  
	private TableColumn<Element, String> name;
	
	@FXML 
	private Button bRetour;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		ObservableList<String> liste = FXCollections.observableArrayList();
		
		//this.name.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), liste));
		
		System.out.println("ELEMENT : " + this.tabElements.size());
		this.tabElements.forEach(o -> {
			
			System.out.println(o.toString());
		});
		
		this.bRetour.setOnAction(actionEvent -> {
			
			System.out.println("ELEMENT : " + this.tabElements.size());
			this.tabElements.forEach(o -> {
				
				System.out.println(o.toString());
			});
//			RoutingUtils.goTo(actionEvent, Route.CHAINE);
		});
	}

	public ObservableList<Element> getElements() {
		return elements;
	}

	public void setElements(ObservableList<Element> elements) {
		this.elements = elements;
	}

	public List<Element> getTabElements() {
		return tabElements;
	}

	public void setTabElements(List<Element> tabElements) {
		
		System.out.println("WOOOOOOOOOOOOOOOOOW : " + tabElements.size());
		this.tabElements = tabElements;
	}

	Stage stage;

	void setStage(Stage stg) {
		stage = stg;
	}

}