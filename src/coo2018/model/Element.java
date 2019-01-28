package coo2018.model;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import coo2018.model.event.ElementEvent;
import coo2018.utils.csv.CSVUtils;
 
public class Element extends Observable {
 
    private String id;
    private String nom;
    private int quantite;
    private String unite;
    private double prixAchat;
    private double prixVente;
 
    public Element() {
        this.id = "";
        this.quantite = 0;
        this.nom = "";
        this.unite = "";
        this.prixAchat = 0;
        this.prixVente = 0;
        this.addObserver(new ElementEvent());
    }
 
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
     * @return the nom
     */
    public String getNom() {
        return this.nom;
    }
 
    /**
     * @param nom the nom to set
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
     * @param prenom the id to set
     */
    public Element setId(String id) {
        this.id = id;
        return this;
    }
    
    /**
     * @return the quantite
     */
    public int getQuantite() {
        return this.quantite;
    }
 
    /**
     * @param prenom the prenom to set
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
     * @return the unite
     */
    public String getUnite() {
        return this.unite;
    }
 
    /**
     * @param prenom the unite to set
     */
    public Element setUnite(String unite) {
        this.unite = unite;
        return this;
    }
    
    /**
     * @return the prixAchat
     */
    public double getPrixAchat() {
        return this.prixAchat;
    }
 
    /**
     * @param prenom the unite to set
     */
    public Element setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
        return this;
    }
    
    /**
     * @return the prixAchat
     */
    public double getPrixVente() {
        return this.prixVente;
    }
 
    /**
     * @param prenom the unite to set
     */
    public Element setPrixVente(double prixVente) {
        this.prixVente = prixVente;
        return this;
    }
    
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
	
	public static int nbElement(String path) {
		
		int nb = 0;
		
		for (@SuppressWarnings("unused") CSVRecord parser : CSVUtils.getReader(path)) {
			
			nb++;
		}
		
		return nb;
	}
    
    @Override
	public String toString() {
    	
		return "Element [id=" + id + ", quantite=" + quantite + ", nom=" + nom + ", unite=" + unite + ", prixAchat=" + prixAchat
				+ ", prixVente=" + prixVente + "]";
	}
 
}