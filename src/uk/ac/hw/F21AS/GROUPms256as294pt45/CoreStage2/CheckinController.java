/**
 * 
 */
package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.util.ArrayList;
import java.util.TreeMap;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Booking;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.BookingLoader;
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
		passengerQueue= new ArrayList<Passenger>();
	}
	
	public void StartCheckin(){
		
		model.getKiosk1().start();
		model.getKiosk2().start();
		// Add Display Events GUI
		
	}

}
