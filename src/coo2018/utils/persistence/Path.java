package coo2018.utils.persistence;

import java.io.IOException;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import coo2018.utils.csv.CSVUtils;

public enum Path {
	
	ELEMENT("element"),
	CHAINE("chaine");
    
    private String path;  
     
    private Path(String path) {  
    	
        this.path = path ;  
   }  
     
    public String getPath() {  
    	
		String res = "";
		
		for(CSVRecord parser : CSVUtils.getReader("values.csv")) {
			
			switch (this.path) {
			
				case "element":
					res = parser.toMap().get("elementPath");
					break;
				case "chaine":
					res = parser.toMap().get("chainePath");
					break;
				default:
					break;
			}
			
			
		}
				
		return res;
   }  
    
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
