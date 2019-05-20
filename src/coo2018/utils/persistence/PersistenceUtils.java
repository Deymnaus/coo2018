package coo2018.utils.persistence;

import java.io.IOException;

import org.apache.commons.csv.CSVPrinter;

import coo2018.utils.csv.CSVUtils;

/**
 * 
 * @author Andréa Christophe
 *
 */
public class PersistenceUtils {
	
	public static void savePath(Path fileToWrite, String path) {
		   
		String otherPathElement = "";
		String otherPathChaine = "";
		String otherPathElementSimulation = "";
		String otherPathElementAchat = "";

		switch (fileToWrite) {
		
		case ELEMENT:
			otherPathChaine = Path.CHAINE.getPath();
			otherPathElementSimulation = Path.ELEMENT_SIMULATION.getPath();
			otherPathElementAchat = Path.ELEMENT_ACHAT.getPath();
			break;
		case CHAINE:
			otherPathElement = Path.ELEMENT.getPath();
			otherPathElementSimulation = Path.ELEMENT_SIMULATION.getPath();
			otherPathElementAchat = Path.ELEMENT_ACHAT.getPath();
			break;
		case ELEMENT_SIMULATION:
			otherPathElement = Path.ELEMENT.getPath();
			otherPathChaine = Path.ELEMENT.getPath();
			otherPathElementAchat = Path.ELEMENT_ACHAT.getPath();
			break;
		case ELEMENT_ACHAT:
			otherPathElement = Path.ELEMENT.getPath();
			otherPathElementSimulation = Path.ELEMENT_SIMULATION.getPath();
			otherPathChaine = Path.ELEMENT.getPath();
			break;
		default:
			break;
	   }
	   
	   CSVPrinter printer = CSVUtils.getPrinter("valuesTest.csv");
		
		try {
			
			printer.printRecord("elementPath", "chainePath", "elementSimulationPath", "elementAchatPath");
		
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		
		try {
			
			switch (fileToWrite) {
			
			case ELEMENT:
				printer.printRecord(path, otherPathChaine, otherPathElementSimulation, otherPathElementAchat);
				break;
			case CHAINE:
				printer.printRecord(otherPathElement, path, otherPathElementSimulation, otherPathElementAchat);
				break;
			case ELEMENT_SIMULATION:
				printer.printRecord(otherPathElement, otherPathChaine, path, otherPathElementAchat);
				break;
			case ELEMENT_ACHAT:
				printer.printRecord(otherPathElement, otherPathChaine, otherPathElementSimulation, path);
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
