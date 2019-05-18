package coo2018.utils.persistence.dao.impl;
 
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import coo2018.model.Chaine;
import coo2018.model.Element;
import coo2018.model.event.ElementEvent;
import coo2018.utils.csv.CSVUtils;
import coo2018.utils.csv.IActionCSV;
import coo2018.utils.persistence.Path;
import coo2018.utils.persistence.dao.DAO;
 
/**
 * 
 * @author Andréa Christophe
 *
 */
public class ChaineDAO extends DAO<Chaine> {

	public ChaineDAO() {
	}

	@Override
	public boolean create(Chaine obj) throws Exception {
		
		boolean res = true;
		List<Chaine> chaines = new ChaineDAO().findAll();
		CSVPrinter printer = CSVUtils.getPrinter(Path.CHAINE.getPath());
		
		try {
			
			printer.printRecord("id", "nom", "niveauActivation", "temps", "entree", "sortie");
		
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		
		for (Chaine c : chaines) {
			
			try {
				
				printer.printRecord(c.getId(), c.getNom(), c.getNiveauActivation(), c.getTemps(), c.elementsEntreeToString(), c.elementsSortieToString());
			
			} catch (IOException ex) {

				res = false;
			}
		}
		
		try {
			
			printer.printRecord(obj.getId(), obj.getNom(), obj.getNiveauActivation(), obj.elementsEntreeToString(), obj.elementsSortieToString());
			printer.close();
			
		} catch (IOException ex) {

			res = false;
		}
		
		return res;
	}

	@Override
	public boolean delete(Chaine obj) throws Exception {
				
		boolean res = true;
		List<Chaine> chaines = new ChaineDAO().findAll();
		CSVPrinter printer = CSVUtils.getPrinter(Path.CHAINE.getPath());
		
		try {
			printer.printRecord("id", "nom", "niveauActivation", "temps", "entree", "sortie");
			for (Chaine c : chaines) {
								
				try {
					
					System.out.println("UNE CHAINE : " + c.toString());
					
					if (!c.getId().equals(obj.getId())) {
						
						printer.printRecord(c.getId(), c.getNom(), c.getNiveauActivation(), c.getTemps(), c.elementsEntreeToString(), c.elementsSortieToString());
					}
					
					
				} catch (IOException ex) {

					res = false;
				}
			}
			
			printer.close();
			
		} catch (Exception e) {
			
			throw new Exception("Erreur dans la suppression de l'élément " + obj.getId());
		}	
		
		return res;
	}

	@Override
	public boolean update(Chaine obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Chaine find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Chaine> findAll() throws Exception {
		
		List<Chaine> chaines = new ArrayList<Chaine>();
		Map<String, Element> elements = Element.CSVToElementMap(Path.ELEMENT.getPath());
		
		for(CSVRecord csvRecord : CSVUtils.getReader(Path.CHAINE.getPath())) {
						
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
					
					throw new Exception("L'element \"" + identifiantQuantiteElementEntree[0] + "\" présent dans la chaîne \"" + csvRecord.toMap().get("id") + "\" n'existe pas dans le fichier element.");
					
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

	
}