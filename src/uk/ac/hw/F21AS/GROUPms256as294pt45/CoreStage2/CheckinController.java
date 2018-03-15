/**
 * 
 */
package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;
import java.util.TreeMap;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Booking;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.BookingLoader;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.ErrorLogger;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Flight;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.FlightLoader;

/**Controller in the MVC pattern
 * Gets user orders from GUI, Displays events, Saves log into File...  
 * @author mehdi seddiq (ms256)
 *
 */
public class CheckinController{
	private String bookingPath, flightPath;
	private BookingLoader bookingLoader;
	private FlightLoader flightLoader;
	private TreeMap<String, Booking> bookings;
	private TreeMap<String, Flight> flights;
	private ArrayList<String> invalidFormatErrors;
	private ErrorLogger errorLogger;
	private static RuntimeSpeedController simulationSpeed;
	private PassengerGenerator passengerGenerator;
	
	//public DataModel model;
	//public AutomatedFrame view;
	//public static ArrayList<Passenger> passengerQueue;
	
	
	public CheckinController(){
		//model=givenModel;
		//view=givenView;
		//passengerQueue= new ArrayList<Passenger>();

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
		
		errorLogger = new ErrorLogger();
		
		simulationSpeed = RuntimeSpeedController.getInstance();
		
		CollectDataFromFiles();
		
		passengerGenerator = new PassengerGenerator(bookings, invalidFormatErrors);
	}
	
	public void StartCheckin(){
		
		//model.getKiosk1().start();
		//model.getKiosk2().start();
		// Add Display Events GUI
		
	}

	/**
	 * Loads Booking and Flight Data
	 */
	private void CollectDataFromFiles() {
		try {
			bookings = bookingLoader.LoadBookings();
			invalidFormatErrors = bookingLoader.GetErrors();
		} catch (NullPointerException e) {
			errorLogger.addUnexpectedError("Booking file triggered a NullPointerException");
		} catch (IOException e) {
			errorLogger.addUnexpectedError("Booking file triggered an IOException");
		}
		
		try {
			flights = flightLoader.LoadFlight();
		} catch (NullPointerException e) {
			errorLogger.addUnexpectedError("Flight file triggered a NullPointerException");
		} catch (IOException e) {
			errorLogger.addUnexpectedError("Flight file triggered an IOException");
		}
	}
}
