package coo2018.ui;

public interface IActionFormulaire {

	public void clearTextField();
	
	public boolean isChampValid();
	
	public void addToList() throws Exception;
	
	public void removeToList() throws Exception;
}
