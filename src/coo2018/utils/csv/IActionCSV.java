package coo2018.utils.csv;

import java.io.IOException;
import java.util.List;

import coo2018.model.Chaine;

/**
 * 
 * @author Andréa Christophe
 * @param <T>
 *
 */
public interface IActionCSV<T> {

	/*
	 * Définira les comportements face au CSV, implémenté dans le model
	 */
	public List<T> toCSV(String path);
	
	public void addToCSV(T object, String path);
	
	public void removeToCSV(String id, String path) throws IOException;
}
