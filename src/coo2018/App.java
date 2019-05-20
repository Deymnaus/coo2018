package coo2018;

import coo2018.model.Chaine;
import coo2018.model.Element;
import coo2018.ui.view.accueil.AccueilPage;
import coo2018.utils.persistence.dao.DAO;
import coo2018.utils.persistence.dao.impl.ChaineDAO;
import coo2018.utils.persistence.dao.impl.ElementDAO;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Point d'entrée de l'application
 * 
 * @author Andréa Christophe
 *
 */
public class App extends Application {
	
    public static DAO<Element> elementDao = new ElementDAO();
    public static DAO<Chaine> chaineDao = new ChaineDAO();

	
    public static void main(String[] args) {
    	
        App.launch(AccueilPage.class, args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		
	}
}