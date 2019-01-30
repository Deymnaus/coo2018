package coo2018.model.event;

import java.util.Observable;
import java.util.Observer;

import coo2018.model.Element;

/**
 * 
 * @author Andréa Christophe
 *
 */
public class ElementEvent implements Observer {

	/**
	 * 
	 */
	@Override
	public void update(Observable observable, Object message) {

		Element c = ((Element)observable);
		System.out.println(c.toString() + "\n" + (String) message);
	}
}
