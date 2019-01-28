package coo2018.model.event;

import java.util.Observable;
import java.util.Observer;

import coo2018.model.Element;

public class ElementEvent implements Observer {

	@Override
	public void update(Observable observable, Object message) {

		Element c = ((Element)observable);
		System.out.println(c.toString() + "\n" + (String) message);
	}

	public static void main(String[] args) {
		
		Element e = new Element("E005", "or", 100, "grammes", 100.0, 30.0);
		e.setQuantite(0);
		
		
	}
}
