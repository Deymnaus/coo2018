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
public class ElementDAO extends DAO<Element> {

	public ElementDAO() {
	}

	@Override
	public boolean create(Element obj) throws Exception {
		
		boolean res = true;
		List<Element> elements = new ElementDAO().findAll();
		CSVPrinter printer = CSVUtils.getPrinter(Path.ELEMENT.getPath());
		
		if (Path.ELEMENT.getPath().equals("")) throw new Exception("Aucun fichier élément n'a été renseigné");
		
		
		
		try {
			
			printer.printRecord("id", "nom", "quantite", "unite", "prixAchat", "prixVente");
		
		} catch (IOException e1) {

			res = false;
		}
		
		for (Element e : elements) {
			
			try {
				
				printer.printRecord(e.getId(), e.getNom(), e.getQuantite(), e.getUnite(), e.getPrixAchat(), e.getPrixVente());
			
			} catch (IOException ex) {

				res = false;
			}
		}
		
		try {
			
			printer.printRecord(obj.getId(), obj.getNom(), obj.getQuantite(), obj.getUnite(), obj.getPrixAchat(), obj.getPrixVente());
			printer.close();
			
		} catch (IOException ex) {

			res = false;
		}
		
		return res;
	}

	@Override
	public boolean delete(Element obj) throws Exception {
		
		boolean res = true;
		List<Element> elements = new ElementDAO().findAll();
		CSVPrinter printer = CSVUtils.getPrinter(Path.ELEMENT.getPath());
		
		if (Path.ELEMENT.getPath().equals("")) throw new Exception("Aucun fichier élément n'a été renseigné");
		
		try {
			
			printer.printRecord("id", "nom", "quantite", "unite", "prixAchat", "prixVente");
		
			for (Element e : elements) {
				
				if (!e.getId().equals(obj.getId())) {
					
					try {
						printer.printRecord(e.getId(), e.getNom(), e.getQuantite(), e.getUnite(), e.getPrixAchat(), e.getPrixVente());
					} catch (IOException e1) {
	
						res = false;
					}
				}
			}	
	
			printer.close();
			
		} catch (IOException ex) {

			throw new Exception("");
		}
		
		return res;
	}

	@Override
	public boolean update(Element obj) {
		
		return false;
	}

	@Override
	public Element find(String id) {
		
		Element element = null;
		
		for (CSVRecord csvRecord : CSVUtils.getReader(Path.ELEMENT.getPath())) {
			
			if (csvRecord.toMap().get("id").equals(id)) {
				
				element = new Element(
					csvRecord.toMap().get("id"),
					csvRecord.toMap().get("nom"),
					Integer.parseInt(csvRecord.toMap().get("quantite")),
					csvRecord.toMap().get("unite"),
					csvRecord.toMap().get("prixAchat").equals("NA") ? 0.0 : Double.parseDouble(csvRecord.toMap().get("prixAchat")),
					csvRecord.toMap().get("prixVente").equals("NA") ? 0.0 : Double.parseDouble(csvRecord.toMap().get("prixVente"))
				);	
			}
		}
		
		return element;
	}

	@Override
	public List<Element> findAll() {
		
		List<Element> elements = new ArrayList<Element>();
		
		CSVUtils.getReader(Path.ELEMENT.getPath()).forEach(csvRecord -> {
			
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
}