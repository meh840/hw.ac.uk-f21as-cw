/**
 * 
 */
package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.io.IOException;
import java.util.ArrayList;
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
public class CheckinController {
	public DataModel model;
	public AutomatedFrame view;
	public static TreeMap<String, Flight> flights;
	private String flightPath;
	private FlightLoader flightLoader;
	public static TreeMap<String, Booking> bookings;
	private String bookingPath;
	private BookingLoader bookingLoader;
	private ArrayList<String> invalidFormatErrors;
	private ErrorLogger errorLogger;
	public static ArrayList<Passenger> passengerQueue;
	
	
	public CheckinController (DataModel givenModel, AutomatedFrame givenView){
		model=givenModel;
		view=givenView;
		flightPath = "/FlightFile.txt";
		flightLoader = new FlightLoader(flightPath);
		flights = new TreeMap<String, Flight>();
		bookingPath = "/BookingFile.txt";
		bookingLoader = new BookingLoader(bookingPath);
		bookings = new TreeMap<String, Booking>();
		invalidFormatErrors = new ArrayList<String>();
		errorLogger = new ErrorLogger();
		passengerQueue= new ArrayList<Passenger>();
	}
	/**
	 * loads Booking and Flight Data, also saves errors in a file
	 */
	public void LoadFiles() {
		try {
			bookings = bookingLoader.LoadBookings();
			invalidFormatErrors = bookingLoader.GetErrors();
		}
		catch (NullPointerException e) {
			errorLogger.addUnexpectedError("Booking file triggered a NullPointerException");
		}
		catch (IOException e) {
			errorLogger.addUnexpectedError("Booking file triggered an IOException");
		}
		
		try {
			flights = flightLoader.LoadFlight();
		} 
		catch (NullPointerException e) {
			errorLogger.addUnexpectedError("Flight file triggered a NullPointerException");
		} 
		catch (IOException e) {
			errorLogger.addUnexpectedError("Flight file triggered an IOException");
		}
	}

	
	public void StartCheckin(){
		//model.getPassengerGenerator().start();
		model.getKiosk1().start();
		model.getKiosk2().start();
		// Add Display Events GUI
		
	}

}
