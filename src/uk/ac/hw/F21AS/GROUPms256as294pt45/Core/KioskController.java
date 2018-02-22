package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class KioskController {
	private String bookingPath, flightPath;
	private BookingLoader bookingLoader;
	private FlightLoader flightLoader;
	private TreeMap<String, Booking> bookings;
	private TreeMap<String, Flight> flights;
	private ArrayList<String> invalidFormatErrors, checkInErrors;
	private ErrorLogger errorLogger;
	
	public KioskController() {
		// File locations.
		bookingPath = "/BookingFile.txt";
		flightPath = "/FlightFile.txt";
		
		// Loaders
		bookingLoader = new BookingLoader(bookingPath);
		flightLoader = new FlightLoader(flightPath);
		
		// Data collections.
		bookings = new TreeMap<String, Booking>();
		flights = new TreeMap<String, Flight>();
		invalidFormatErrors = new ArrayList<String>();
		checkInErrors = new ArrayList<String>();
		
		errorLogger = new ErrorLogger();
	}
	
	public void ReadyKiosk() {
		try {
			bookings = bookingLoader.LoadBookings();
			invalidFormatErrors = bookingLoader.GetErrors();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			flights = flightLoader.LoadFlight();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void StartUpKiosk() {
		if(!invalidFormatErrors.isEmpty()) {
			for(String error : invalidFormatErrors) {
				errorLogger.addEntry(error);
			}
		}
		
		//KioskSearch kiosk = new KioskSearch(bookings, flights, errorLogger);
		
		//SummaryGUI summary = new SummaryGUI(flights);
	}
}
