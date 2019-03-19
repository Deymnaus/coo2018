package coo2018.model;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.apache.commons.csv.CSVPrinter;

import coo2018.model.event.ElementEvent;
import coo2018.utils.csv.CSVUtils;
import coo2018.utils.csv.IActionCSV;
 
/**
 * 
 * @author Andréa Christophe
 *
 */
public class Element extends Observable implements IActionCSV<Element> {
 
    private String id;
    private String nom;
    private int quantite;
    private String unite;
    private double prixAchat;
    private double prixVente;
 
    /**
     * 
     */
    public Element() {
        this.id = "";
        this.quantite = 0;
        this.nom = "";
        this.unite = "";
        this.prixAchat = 0;
        this.prixVente = 0;
        this.addObserver(new ElementEvent());
    }
 
    /**
     * 
     * @param id
     * @param nom
     * @param quantite
     * @param unite
     * @param prixAchat
     * @param prixVente
     */
    public Element(String id, String nom, int quantite, String unite, double prixAchat, double prixVente) {
        this.id = id;
        this.quantite = quantite;
        this.nom = nom;
        this.unite = unite;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.addObserver(new ElementEvent());
    }
 
    /**
     * 
     * @return
     */
    public String getNom() {
        return this.nom;
    }
 
    /**
     * 
     * @param nom
     * @return
     */
    public Element setNom(String nom) {
        this.nom = nom;
        return this;
    }
 
    /**
     * @return the id
     */
    public String getId() {
        return this.id;
    }
 
    /**
     * 
     * @param id
     * @return
     */
    public Element setId(String id) {
        this.id = id;
        return this;
    }
    
    /**
     * 
     * @return
     */
    public int getQuantite() {
        return this.quantite;
    }
 
    /**
     * 
     * @param quantite
     * @return
     */
    public Element setQuantite(int quantite) {
    	
    	if (quantite==0) {
    		
    		setChanged();
    		notifyObservers("Le stock pour cet élément est nul !\nPensez à réapprovisionner !");
    	}
        this.quantite = quantite;
        return this;
    }
    
    /**
     * 
     * @return
     */
    public String getUnite() {
        return this.unite;
    }
 
    /**
     * 
     * @param unite
     * @return
     */
    public Element setUnite(String unite) {
        this.unite = unite;
        return this;
    }
    
    /**
     * 
     * @return
     */
    public double getPrixAchat() {
        return this.prixAchat;
    }
 
    /**
     * 
     * @param prixAchat
     * @return
     */
    public Element setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
        return this;
    }
    
    /**
    * 
    * @return
    */
    public double getPrixVente() {
        return this.prixVente;
    }
 
    /**
     * 
     * @param prixVente
     * @return
     */
    public Element setPrixVente(double prixVente) {
        this.prixVente = prixVente;
        return this;
    }
    
    public int decrementeQuantite(int pfQuantite) {
    	
    	System.out.println("Quantité actuelle : " + this.quantite + "... va être supprimé : " + pfQuantite);
    	this.quantite -= pfQuantite;
    	return this.quantite;
    }
    
    public int incrementeQuantite(int pfQuantite) {
    	
    	this.quantite += pfQuantite;
    	return this.quantite;
    }
    
    /**
     * 
     * @param path
     * @return List<Element>
     */
    public static List<Element> CSVToElement(String path) {
    	
		List<Element> elements = new ArrayList<Element>();
		
		CSVUtils.getReader(path).forEach(csvRecord -> {
			
			elements.add(new Element(
						csvRecord.toMap().get("id"),
						csvRecord.toMap().get("nom"),
						Integer.parseInt(csvRecord.toMap().get("quantite")),
						csvRecord.toMap().get("unite"),
						csvRecord.toMap().get("prixAchat").equals("NA") ? 0.0 : Double.parseDouble(csvRecord.toMap().get("prixAchat")),
						csvRecord.toMap().get("prixVente").equals("NA") ? 0.0 : Double.parseDouble(csvRecord.toMap().get("prixVente"))
						)	
					);
		});
		
		return elements;
	}
    
    /**
     * 
     * @param path
     * @return Map<String, Element>
     */
    public static Map<String, Element> CSVToElementMap(String path) {
		
		Map<String, Element> elements = new HashMap<String, Element>();
		
		CSVUtils.getReader(path).forEach(csvRecord -> {
			
			elements.put(csvRecord.toMap().get("id"), 
					new Element(
							csvRecord.toMap().get("id"),
							csvRecord.toMap().get("nom"),
							Integer.parseInt(csvRecord.toMap().get("quantite")),
							csvRecord.toMap().get("unite"),
							csvRecord.toMap().get("prixAchat").equals("NA") ? 0.0 : Double.parseDouble(csvRecord.toMap().get("prixAchat")),
							csvRecord.toMap().get("prixVente").equals("NA") ? 0.0 : Double.parseDouble(csvRecord.toMap().get("prixVente"))
							));
		});
		
		return elements;
	}

    /**
     * 
     * @param id
     * @param path
     * @throws IOException
     */
	public static void removeElementToCSV(String id, String path) throws IOException {
		
		List<Element> elements = CSVToElement(path);
		CSVPrinter printer = CSVUtils.getPrinter(path);
		
		try {
			
			printer.printRecord("id", "nom", "quantite", "unite", "prixAchat", "prixVente");
		
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		
		elements.forEach(e -> {
			
			System.out.println(e.toString());
			
			try {
				
				// Remplacer par getId()
				if (!e.getId().equals(id)) {
					
					printer.printRecord(e.getId(), e.getNom(), e.getQuantite(), e.getUnite(), e.getPrixAchat(), e.getPrixVente());
				}
				
				
			} catch (IOException ex) {

				ex.printStackTrace();
			}
		});
		
		printer.close();
	}
	
	/**
	 * 
	 * @param element
	 * @param path
	 */
	public static void addElementToCSV(Element element, String path) {
		
		List<Element> elements = CSVToElement(path);
		CSVPrinter printer = CSVUtils.getPrinter(path);
		
		try {
			
			printer.printRecord("id", "nom", "quantite", "unite", "prixAchat", "prixVente");
		
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		
		elements.forEach(e -> {
			
			try {
				
				printer.printRecord(e.getId(), e.getNom(), e.getQuantite(), e.getUnite(), e.getPrixAchat(), e.getPrixVente());
			
			} catch (IOException ex) {

				ex.printStackTrace();
			}
		});
		
		try {
			
			printer.printRecord(element.getId(), element.getNom(), element.getQuantite(), element.getUnite(), element.getPrixAchat(), element.getPrixVente());
			printer.close();
			
		} catch (IOException ex) {

			ex.printStackTrace();
		}
	
	}
    
    @Override
	public String toString() {
    	
		return "Element [id=" + id + ", quantite=" + quantite + ", nom=" + nom + ", unite=" + unite + ", prixAchat=" + prixAchat
				+ ", prixVente=" + prixVente + "]";
	}

	@Override
	public List<Element> toList(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addToFile(Element object, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeToCSV(String id, String path) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public static void clearCSV(String path) {
		
		CSVPrinter printer = CSVUtils.getPrinter(path);
		
		try {
			
			printer.printRecord("id", "nom", "quantite", "unite", "prixAchat", "prixVente");
		
		} catch (IOException e1) {

			e1.printStackTrace();
		}
	}

}