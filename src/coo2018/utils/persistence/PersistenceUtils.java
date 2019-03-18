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
	
	public static void savePath(Path fileToWrite, String path) {
		   
		String otherPath = "";

		switch (fileToWrite) {
		
		case ELEMENT:
			otherPath = Path.CHAINE.getPath();
			break;
		case CHAINE:
			otherPath = Path.ELEMENT.getPath();
			break;
		default:
			break;
	   }
	   
	   CSVPrinter printer = CSVUtils.getPrinter("values.csv");
		
		try {
			
			printer.printRecord("elementPath", "chainePath");
		
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		
		try {
			
			switch (fileToWrite) {
			
			case ELEMENT:
				printer.printRecord(path, otherPath);
				break;
			case CHAINE:
				printer.printRecord(otherPath, path);
				break;
			default:
				break;
		   }
			
			printer.close();
			
		} catch (IOException ex) {

			ex.printStackTrace();
		}
	}
}
