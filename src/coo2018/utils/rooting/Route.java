package coo2018.utils.rooting;

/**
 * 
 * @author Andréa Christophe
 *
 */
public enum Route {
	
	ACCUEIL("../../ui/view/accueil/AccueilPresenter.fxml"), 
	
	CHAINE("../../ui/view/chaine/ChainePresenter.fxml"),
	CHAINE_DETAIL("../../ui/view/chaine/ChaineDetailPresenter.fxml"),
	
	ELEMENT("../../ui/view/element/ElementPresenter.fxml"),
	ELEMENT_SIMULATION("../../ui/view/element/ElementSimulationPresenter.fxml"),
	ELEMENT_ACHAT("../../ui/view/element/ElementAchatPresenter.fxml");
    
    private String path;  
     
    /**
     * 
     * @param path
     */
    private Route(String path) {  
    	
        this.path = path ;  
   }  
     
    /**
     * 
     * @return path (chemin vers le fichier)
     */
    public String getPath() {  
        return  this.path ;  
    }
}
