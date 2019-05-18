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
	 * @param path
	 * @return
	 */
	public static List<Chaine> CSVToChaine(String path) throws Exception {
		
		List<Chaine> chaines = new ArrayList<Chaine>();
		Map<String, Element> elements = Element.CSVToElementMap(Path.ELEMENT.getPath());
		
		for(CSVRecord csvRecord : CSVUtils.getReader(path)) {
						
			List<Element> elementsEntree = new ArrayList<Element>();
			List<Element> elementsSortie = new ArrayList<Element>();
			String[] elementsEntreeCSV = new String[1];
			String[] elementsSortieCSV = new String[1];
			
			// ex : E005&2;E003&3 donnera un tableau avec en pos0 : E005&2 et en pos1 : E003&3
			
			// Si les elements en entrée sont > à 1
			if (csvRecord.toMap().get("entree").contains("-")) {

				elementsEntreeCSV = csvRecord.toMap().get("entree").split("-");
				
			// Si il n'y a qu'un seul element en entrée
			} else {
				
				elementsEntreeCSV[0] = csvRecord.toMap().get("entree");
			}
			
			for (int i=0; i<elementsEntreeCSV.length; i++) {
			
				// ex : E005&2 donnera un tableau avec en pos0 : E005 et en pos1 : 3
				String[] identifiantQuantiteElementEntree = elementsEntreeCSV[i].split("&");
				
				// on essaie de récupérer l'element en fonction de son identifiant
				if (elements.get(identifiantQuantiteElementEntree[0]) == null) {
					
					throw new Exception("L'element n°" + identifiantQuantiteElementEntree[0] + " de cette chaîne n'existe pas dans le fichier element");
					
				} else {
				
					elements.get(identifiantQuantiteElementEntree[0]).setQuantite(Integer.parseInt(identifiantQuantiteElementEntree[1]));
					elementsEntree.add(elements.get(identifiantQuantiteElementEntree[0]));
				}
				
			}
			
			
			// ex : E005&2;E003&3 donnera un tableau avec en pos0 : E005&2 et en pos1 : E003&3
			if (csvRecord.toMap().get("sortie").contains("-")) {

				elementsSortieCSV = csvRecord.toMap().get("sortie").split("-");
			} else {
				
				elementsSortieCSV[0] = csvRecord.toMap().get("sortie");
			}
			
			for (int i=0; i<elementsSortieCSV.length; i++) {
			
				// ex : E005&2 donnera un tableau avec en pos0 : E005 et en pos1 : 3
				String[] identifiantQuantiteElementSortie = elementsSortieCSV[i].split("&");
				
				if (elements.get(identifiantQuantiteElementSortie[0]) == null) {
					
					throw new Exception("L'element n°" + identifiantQuantiteElementSortie[0] + " de cette chaîne n'existe pas dans le fichier element");
					
				} else {
					
					elements.get(identifiantQuantiteElementSortie[0]).setQuantite(Integer.parseInt(identifiantQuantiteElementSortie[1]));
					elementsSortie.add(elements.get(identifiantQuantiteElementSortie[0]));
				}
			}
			
			Chaine chaine = new Chaine(
					csvRecord.toMap().get("id"),
					csvRecord.toMap().get("nom"),
					Integer.parseInt(csvRecord.toMap().get("niveauActivation")),
					Integer.parseInt(csvRecord.toMap().get("temps")),
					elementsEntree,
					elementsSortie
				);
			
			chaines.add(chaine);
		}
		
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
	 * @throws Exception 
	 */
	public static void removeChaineToCSV(String id, String path) throws Exception {
		
		List<Chaine> chaine = new ArrayList<Chaine>();
		
		try {
			chaine = CSVToChaine(path);
			CSVPrinter printer = CSVUtils.getPrinter(path);
			printer.printRecord("id", "nom", "niveauActivation", "temps", "entree", "sortie");
			chaine.forEach(c -> {
								
				try {
					
					// Remplacer par getId()
					if (!c.getId().equals(id)) {
						
						printer.printRecord(c.getId(), c.getNom(), c.getNiveauActivation(), c.getTemps(), c.elementsEntreeToString(), c.elementsSortieToString());
					}
					
					
				} catch (IOException ex) {

					ex.printStackTrace();
				}
			});
			printer.close();
			
		} catch (Exception e) {
			
			throw new Exception("Erreur dans la suppression de l'élément " + id);
		}		
	}
	
	/**
	 * 
	 * @param chaine
	 * @param path
	 * @throws Exception 
	 */
	public static void addChaineToCSV(Chaine chaine, String path) throws Exception {
		
		List<Chaine> chaines = CSVToChaine(path);
		CSVPrinter printer = CSVUtils.getPrinter(path);
		
		try {
			
			printer.printRecord("id", "nom", "niveauActivation", "temps", "entree", "sortie");
		
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		
		chaines.forEach(c -> {
			
			try {
				
				printer.printRecord(c.getId(), c.getNom(), c.getNiveauActivation(), c.getTemps(), c.elementsEntreeToString(), c.elementsSortieToString());
			
			} catch (IOException ex) {

				ex.printStackTrace();
			}
		});
		
		try {
			
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
