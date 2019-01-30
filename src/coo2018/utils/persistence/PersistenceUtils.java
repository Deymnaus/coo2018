package coo2018.utils.persistence;

import java.io.IOException;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import coo2018.utils.csv.CSVUtils;

/**
 * 
 * @author Andréa Christophe
 *
 */
public class PersistenceUtils {
	
	/**
	 * TODO :
	 * RÉDUIRE CETTE PUTAIN DE CLASSE PAS ASSEZ GÉNÉRIQUE
	 */

	public static void saveFilePathElement(String path) {
		
		String chainePath = PersistenceUtils.filePathElement();
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
	
	public static void saveFilePathChaine(String path) {
		
		String elementPath = PersistenceUtils.filePathElement();
		CSVPrinter printer = CSVUtils.getPrinter("values.csv");
		
		try {
			
			printer.printRecord("elementPath", "chainePath");
		
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		
		try {
						
			printer.printRecord(elementPath, path);
			printer.close();
			
		} catch (IOException ex) {

			ex.printStackTrace();
		}
	
	}
	
	public static String filePathElement() {
		
		String res = "";
		
		for(CSVRecord parser : CSVUtils.getReader("values.csv")) {
			
			res = parser.toMap().get("elementPath");
		}
		
		
		return res;
	}
	
	public static String filePathChaine() {
		
		String res = "";
		
		for(CSVRecord parser : CSVUtils.getReader("values.csv")) {
			
			res = parser.toMap().get("chainePath");
		}
		
		return res;
	}
}
