package coo2018.utils.rooting;

import java.io.IOException;

import org.apache.commons.csv.CSVPrinter;

import coo2018.utils.csv.CSVUtils;

public enum Route {
	
	ACCUEIL("../../ui/view/accueil/AccueilPresenter.fxml"), 
	
	CHAINE("../../ui/view/chaine/ChainePresenter.fxml"),
	CHAINE_DETAIL("../../ui/view/chaine/ChaineDetailPresenter.fxml"),
	
	ELEMENT("../../ui/view/element/ElementPresenter.fxml");
    
    private String path;  
     
    private Route(String path) {  
    	
        this.path = path ;  
   }  
     
    public String getPath() {  
        return  this.path ;  
   }
    
    public void savePathElement(String path) {
    	
    	String chainePath = getPath();
		CSVPrinter printer = CSVUtils.getPrinter("values.csv");
		
		try {
			
			printer.printRecord("elementPath", "chainePath");
		
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		
		try {
			
			printer.printRecord(path, chainePath);
			printer.close();
			
		} catch (IOException ex) {

			ex.printStackTrace();
		}
    }
}
