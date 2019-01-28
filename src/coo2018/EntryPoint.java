package coo2018;

import coo2018.ui.view.accueil.AccueilPage;
import javafx.application.Application;
import javafx.stage.Stage;

public class EntryPoint extends Application {
	
    public static void main(String[] args) {
    	
        Application.launch(AccueilPage.class, args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		
	}
}
