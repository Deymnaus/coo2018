package coo2018.utils.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

/**
 * 
 * @author Andréa Christophe
 *
 */
public class CSVUtils {

	/**
	 * 
	 * @param path
	 * @return CSVParser object
	 * @throws Exception
	 */
	public static CSVParser getReader(String path) {
		
		CSVParser reader = null;
		
		try {
			reader = CSVParser.parse(
					
					new File(path), 
					Charset.defaultCharset(), 
					CSVFormat.DEFAULT.withHeader());
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		return reader;
	}
	
	/**
	 * 
	 * @param path
	 * @return CSVPrinter object
	 * @throws Exception
	 */
	public static CSVPrinter getPrinter(String path) {
		
		CSVPrinter printer = null;
		
		try {
			
			printer = new CSVPrinter(new FileWriter(path), CSVFormat.EXCEL);
			
		 } catch (IOException ex) {
			 
		     ex.printStackTrace();
		 }
		
		return printer;
	}
}
