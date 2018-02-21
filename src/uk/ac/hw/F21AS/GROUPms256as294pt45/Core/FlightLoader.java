package uk.ac.hw.F21AS.GROUPms256as294pt45.Core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * This class is responsible for loading a flight text file from resources folder and 
 * producing a collection of flights (using TreeMap).
 * @author Alan Spence (as294)
 *
 */
public class FlightLoader {
	private TreeMap<String, Flight> flights;
	private ArrayList<String> fileEntries;
	private String path;
	
	/**
	 * Constructor for FlightLoader class.
	 * @param path String stating location of flight file.
	 */
	public FlightLoader(String path) {
		flights = new TreeMap<String, Flight>();
		fileEntries = new ArrayList<String>();
		this.path = path;
	}
	
	/**
	 * Loads flight entries from file.
	 * @return A collection of flight details.
	 * @throws NullPointerException, IOException 
	 */
	public TreeMap<String, Flight> LoadFlight() throws NullPointerException, IOException {
		RetieveEntries();
		ConvertEntries();
		
		return flights;
	}
	
	/**
	 * Gets the entries from the flight file.
	 * @throws NullPointerException, IOException 
	 */
	private void RetieveEntries() throws NullPointerException, IOException {
		InputStream in = null;
		InputStreamReader streamReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			// Code on resource loading taken from https://stackoverflow.com/questions/15749192/how-do-i-load-a-file-from-resource-folder
			in = getClass().getResourceAsStream(path);
			streamReader = new InputStreamReader(in);
			bufferedReader = new BufferedReader(streamReader);
			for (String line; (line = bufferedReader.readLine()) != null;) {
			    fileEntries.add(line);
			}
		} catch (NullPointerException e) {
			throw e;
		} finally {
			 try {
                if (bufferedReader != null) {
                	bufferedReader.close();
                }
                if (streamReader != null) {
                	streamReader.close();
                }
                if (in != null) {
                	in.close();
                }
            } catch (IOException ex) {
                throw ex;
            }
		}
	}
	
	/**
	 * Takes the file entries and splits them on the commas. 
	 * Note: This does no checks and assumes in correct format.
	 */
	private void ConvertEntries() {
		String[] entryList;
		Flight newFlight;
		
		for (String entry : fileEntries) {
			entryList = entry.split(",");
			
			newFlight = new Flight(entryList[0], entryList[1], entryList[2], Integer.parseInt(entryList[3]), 
					Double.parseDouble(entryList[4]), Double.parseDouble(entryList[5]));
			
			flights.put(newFlight.FlightCode(), newFlight);
		}
	}
}
