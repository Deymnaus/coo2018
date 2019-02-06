package coo2018.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.apache.commons.csv.CSVPrinter;

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
	private List<Element> elementsEntree;
	private List<Element> elementsSortie;
	
	public Chaine() {
		
		this.id = ""; 
		this.nom = "";
		this.niveauActivation = 0;
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
	public Chaine(String id, String nom, int niveauActivation) {
		
		this.id = id; 
		this.nom = nom;
		this.niveauActivation = niveauActivation;
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
	public Chaine(String id, String nom, int niveauActivation, List<Element> entree, List<Element> sortie) {
		
		this.id = id; 
		this.nom = nom;
		this.niveauActivation = niveauActivation;
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

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static List<Chaine> CSVToChaine(String path) {
		
		List<Chaine> chaines = new ArrayList<Chaine>();
		
		Map<String, Element> elements = Element.CSVToElementMap(Path.ELEMENT.getPath());
		
		CSVUtils.getReader(path).forEach(csvRecord -> {
						
			List<Element> elementsEntree = new ArrayList<Element>();
			List<Element> elementsSortie = new ArrayList<Element>();
			String[] elementsEntreeCSV = new String[1];
			String[] elementsSortieCSV = new String[1];
			
			// ex : E005&2;E003&3 donnera un tableau avec en pos0 : E005&2 et en pos1 : E003&3
			System.out.println(csvRecord.toMap().get("entree"));
			if (csvRecord.toMap().get("entree").contains(";")) {

				elementsEntreeCSV = csvRecord.toMap().get("entree").split(";");
			} else {
				
				elementsEntreeCSV[0] = csvRecord.toMap().get("entree");
			}
			
			for (int i=0; i<elementsEntreeCSV.length; i++) {
			
				// ex : E005&2 donnera un tableau avec en pos0 : E005 et en pos1 : 3
				String[] identifiantQuantiteElementEntree = elementsEntreeCSV[i].split("&");
				elementsEntree.add(elements.get(identifiantQuantiteElementEntree[0]));
			}
			
			
			// ex : E005&2;E003&3 donnera un tableau avec en pos0 : E005&2 et en pos1 : E003&3
			if (csvRecord.toMap().get("sortie").contains(";")) {

				elementsSortieCSV = csvRecord.toMap().get("entree").split(";");
			} else {
				
				elementsSortieCSV[0] = csvRecord.toMap().get("sortie");
			}
			
			for (int i=0; i<elementsSortieCSV.length; i++) {
			
				// ex : E005&2 donnera un tableau avec en pos0 : E005 et en pos1 : 3
				String[] identifiantQuantiteElementSortie = elementsSortieCSV[i].split("&");
				elementsSortie.add(elements.get(identifiantQuantiteElementSortie[0]));
			}
			
			Chaine chaine = new Chaine(
					csvRecord.toMap().get("id"),
					csvRecord.toMap().get("nom"),
					Integer.parseInt(csvRecord.toMap().get("niveauActivation")),
					elementsEntree,
					elementsSortie
				);
			
			chaines.add(chaine);
		});
		
		return chaines;
	}
	
	/**
	 * 
	 */
	public void transforme() {
		
		if (this.niveauActivation == 0) return;
		
		this.elementsEntree.forEach(elementEntree -> {
			
		});
	}
    
	/**
	 * 
	 * @param id
	 * @param path
	 * @throws IOException
	 */
	public static void removeChaineToCSV(String id, String path) throws IOException {
		
		List<Chaine> chaine = CSVToChaine(path);
		CSVPrinter printer = CSVUtils.getPrinter(path);
		
		try {
			
			printer.printRecord("id", "nom", "niveauActivation", "entree", "sortie");
		
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		
		chaine.forEach(c -> {
			
			System.out.println(c.toString());
			
			try {
				
				// Remplacer par getId()
				if (!c.getId().equals(id)) {
					
					printer.printRecord(c.getId(), c.getNom(), c.getNiveauActivation(), c.elementsEntreeToString(), c.elementsSortieToString());
				}
				
				
			} catch (IOException ex) {

				ex.printStackTrace();
			}
		});
		
		printer.close();
	}
	
	/**
	 * 
	 * @param chaine
	 * @param path
	 */
	public static void addChaineToCSV(Chaine chaine, String path) {
		
		List<Chaine> chaines = CSVToChaine(path);
		CSVPrinter printer = CSVUtils.getPrinter(path);
		
		try {
			
			printer.printRecord("id", "nom", "niveauActivation", "entree", "sortie");
		
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		
		chaines.forEach(c -> {
			
			try {
				
				printer.printRecord(c.getId(), c.getNom(), c.getNiveauActivation(), c.elementsEntreeToString(), c.elementsSortieToString());
			
			} catch (IOException ex) {

				ex.printStackTrace();
			}
		});
		
		try {
			
			System.out.println("ID : " + chaine.getId());
			printer.printRecord(chaine.getId(), chaine.getNom(), chaine.getNiveauActivation(), chaine.elementsEntreeToString(), chaine.elementsSortieToString());
			printer.close();
			
		} catch (IOException ex) {

			ex.printStackTrace();
		}
	
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
				
				res += ";";
			} 
		}
		
		return res;
	}
	
	public String elementsSortieToString() {
		
		String res = "";	
		
		for (Element e : this.elementsSortie) {
			
			res += e.getId() + "&" + e.getQuantite();
			
			if (!this.elementsSortie.get(this.elementsSortie.size()-1).equals(e)) {
				
				res += ";";
			} 
		}
		
		return res;
	}

	@Override
	public List<Chaine> toCSV(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addToCSV(Chaine chaine, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeToCSV(String id, String path) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
