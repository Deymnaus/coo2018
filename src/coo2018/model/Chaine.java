package coo2018.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import coo2018.model.event.ChaineEvent;
import coo2018.utils.csv.CSVUtils;
import coo2018.utils.csv.IActionCSV;
import coo2018.utils.persistence.Path;

/**
 * 
 * @author Andréa Christophe
 *
 */
public class Chaine extends Observable implements IActionCSV<Chaine> {

	private String id;
	private String nom;
	private int niveauActivation;
	private int temps;
	private List<Element> elementsEntree;
	private List<Element> elementsSortie;
	
	public Chaine() {
		
		this.id = ""; 
		this.nom = "";
		this.niveauActivation = 0;
		this.temps = 0;
		this.elementsEntree = new ArrayList<Element>();
		this.elementsSortie = new ArrayList<Element>();
		this.addObserver(new ChaineEvent());
	}
	
	/**
	 * 
	 * @param id
	 * @param nom
	 */
	public Chaine(String id, String nom) {
		
		this.id = id; 
		this.nom = nom;
		this.niveauActivation = 20;
		this.temps = 0;
		this.elementsEntree = new ArrayList<Element>();
		this.elementsSortie = new ArrayList<Element>();
		this.addObserver(new ChaineEvent());
	}
	
	/**
	 * 
	 * @param id
	 * @param nom
	 * @param niveauActivation
	 */
	public Chaine(String id, String nom, int niveauActivation, int temps) {
		
		this.id = id; 
		this.nom = nom;
		this.niveauActivation = niveauActivation;
		this.temps = temps;
		this.elementsEntree = new ArrayList<Element>();
		this.elementsSortie = new ArrayList<Element>();
		this.addObserver(new ChaineEvent());
	}
	
	/**
	 * 
	 * @param id
	 * @param nom
	 * @param entree
	 * @param sortie
	 */
	public Chaine(String id, String nom, List<Element> entree, List<Element> sortie) {
		
		this.id = id; 
		this.nom = nom;
		this.niveauActivation = 20;
		this.temps = 0;
		this.elementsEntree = entree;
		this.elementsSortie = sortie;
		this.addObserver(new ChaineEvent());
	}
	
	/**
	 * 
	 * @param id
	 * @param nom
	 * @param niveauActivation
	 * @param entree
	 * @param sortie
	 */
	public Chaine(String id, String nom, int niveauActivation, int temps, List<Element> entree, List<Element> sortie) {
		
		this.id = id; 
		this.nom = nom;
		this.niveauActivation = niveauActivation;
		this.temps = temps;
		this.elementsEntree = entree;
		this.elementsSortie = sortie;
		this.addObserver(new ChaineEvent());
	}

	/**
	 * 
	 * @return
	 */
	public int getNiveauActivation() {
		return niveauActivation;
	}
	
	/**
	 * 
	 * @param niveauActivation
	 * @return
	 */
	public Chaine setNiveauActivation(int niveauActivation) {
		this.niveauActivation = niveauActivation;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public List<Element> getElementsEntree() {
		return elementsEntree;
	}

	/**
	 * 
	 * @param elementsEntree
	 * @return
	 */
	public Chaine setElementsEntree(List<Element> elementsEntree) {
		this.elementsEntree = elementsEntree;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public List<Element> getElementsSortie() {
		return elementsSortie;
	}

	/**
	 * 
	 * @param elementsSortie
	 * @return
	 */
	public Chaine setElementsSortie(List<Element> elementsSortie) {
		this.elementsSortie = elementsSortie;
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * 
	 * @param nom
	 * @return
	 */
	public Chaine setNom(String nom) {
		this.nom = nom;
		return this;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Chaine setId(String id) {
		this.id = id;
		return this;
	}

	public int getTemps() {
		return temps;
	}

	public Chaine setTemps(int temps) {
		this.temps = temps;
		return this;
	}

	
	/**
	 * 
	 * @return String
	 */
	public String elementsEntreeToString() {
		
		String res = "";	
		
		for (Element e : this.elementsEntree) {
			
			res += e.getId() + "&" + e.getQuantite();
			
			if (!this.elementsEntree.get(this.elementsEntree.size()-1).equals(e)) {
				
				res += "-";
			} 
		}
		
		return res;
	}
	
	public String elementsSortieToString() {
		
		String res = "";	
		
		for (Element e : this.elementsSortie) {
			
			res += e.getId() + "&" + e.getQuantite();
			
			if (!this.elementsSortie.get(this.elementsSortie.size()-1).equals(e)) {
				
				res += "-";
			} 
		}
		
		return res;
	}

	@Override
	public String toString() {
		return "Chaine [id=" + id + ", nom=" + nom + ", niveauActivation=" + niveauActivation + ", elementsEntree="
				+ elementsEntree + ", elementsSortie=" + elementsSortie + "]";
	}

	@Override
	public List<Chaine> toList(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addToFile(Chaine chaine, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeToCSV(String id, String path) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
