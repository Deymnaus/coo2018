package coo2018.model.event;

import java.util.Observable;
import java.util.Observer;

import coo2018.model.Chaine;

/**
 * 
 * @author Andréa Christophe
 *
 */
public class ChaineEvent implements Observer {

	/**
	 * 
	 */
	@Override
	public void update(Observable observable, Object message) {

		@SuppressWarnings("unused")
		Chaine c = ((Chaine)observable);
	}
		
	/*
	 * 
	 * Faire une notification quand on change le fichier csv des chaines de productions et que des éléments utilisées dans les chaînes ne sont pas présents dans
	 * le fichier des éléments, annuler l'action. 
	 */
}
