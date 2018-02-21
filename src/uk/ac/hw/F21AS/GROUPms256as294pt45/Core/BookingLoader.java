package uk.ac.hw.F21AS.GROUPms256as294pt45.Core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * This class is responsible for loading a booking text file from resources folder and 
 * producing a collection of bookings (using TreeMap) after checking booking reference is 
 * in the correct format. Creates an error list of invalid entries for logging.
 * @author Alan Spence (as294)
 *
 */
public class BookingLoader {
	private TreeMap<String, Booking> Bookings;
	private ArrayList<String> fileEntries;
	private String path;
	private ArrayList<String> errors;
	
	/**
	 * Constructor for BookingLoader class.
	 * @param path String stating location of booking file.
	 */
	public BookingLoader(String path) {
		Bookings = new TreeMap<String, Booking>();
		fileEntries = new ArrayList<String>();
		this.path = path;
		errors = new ArrayList<String>();
	}
	
	/**
	 * Loads booking entries from file.
	 * @return A collection of booking details.
	 * @throws NullPointerException, IOException 
	 */
	public TreeMap<String, Booking> LoadBookings() throws NullPointerException, IOException {
		RetieveEntries();
		
		String[] entryList;
		for (String entry : fileEntries) {
			entryList = entry.split(",");
			if(ValidBooking(entryList[0])) {
				AddToValidBooking(entryList);
			} else {
				AddToErrors(entry);
			}
		}
		return Bookings;
	}
	
	/**
	 * Gets the entries from the booking file.
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
	 * Takes a list of entry elements to produce and add a booking. 
	 */
	private void AddToValidBooking(String[] entryList) {
		Booking newBooking = new Booking(entryList[0], entryList[1], entryList[2], entryList[3], Boolean.parseBoolean(entryList[4]));
			
		Bookings.put(newBooking.GetBookingReference(), newBooking);
	}
	
	/**
	 * Checks if a booking reference has the correct format to it.
	 * @param reference Reference to check.
	 * @return True: Valid format. False: Invalid format.
	 */
	private boolean ValidBooking(String reference) {
		return BookingReferenceChecker.CheckABookingReference(reference);
	}
	
	/**
	 * Add an entry which has an incorrect formatting to it's booking reference to an error list for later.
	 * @param entry One which has an invalid formatting to it's booking reference.
	 */
	private void AddToErrors(String entry) {
		errors.add(entry);
	}
	
	/**
	 * Gives a list of entries which have an error.
	 * @return A list of entries which have invalid formatted booking references.
	 */
	public ArrayList<String> GetErrors() {
		return errors;
	}
}