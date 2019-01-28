package coo2018.utils.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

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
	
	public static void main(String[] args) {
		
		//CSVParser reader = getReader("test.csv");
		CSVPrinter printer = getPrinter("test.csv");
		
		try {
			
			 printer.printRecord("id", "userName", "firstName", "lastName", "birthday");
		     printer.printRecord(1, "john73", "John", "Doe", LocalDate.of(1973, 9, 15));
		     printer.println();
		     printer.printRecord(2, "mary", "Mary", "Meyer", LocalDate.of(1985, 3, 29));
		     System.out.println("OK");
		     printer.close();
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		/*try (CSVPrinter printer = new CSVPrinter(new FileWriter("csv.csv"), CSVFormat.EXCEL)) {
		     printer.printRecord("id", "userName", "firstName", "lastName", "birthday");
		     printer.printRecord(1, "john73", "John", "Doe", LocalDate.of(1973, 9, 15));
		     printer.println();
		     printer.printRecord(2, "mary", "Mary", "Meyer", LocalDate.of(1985, 3, 29));
		 } catch (IOException ex) {
		     ex.printStackTrace();
		 }*/
	}
}
