/**
 * 
 */
package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.BaggageDetails;
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
public class CheckinController implements Observer{
	private String bookingPath, flightPath;
	private BookingLoader bookingLoader;
	private FlightLoader flightLoader;
	private TreeMap<String, Booking> bookings;
	private TreeMap<String, Flight> flights;
	private ArrayList<String> invalidFormatErrors;
	private ErrorLogger errorLogger;
	private static RuntimeSpeedController simulationSpeed;
	private PassengerGenerator passengerGenerator;
	private AutoKiosk kiosk1, kiosk2;
	private MannedKiosk mannedKiosk;
	private PassengerQueue passengerQueue;
	public String kioskEvent;
	public int NumPassengersInQueue; 
	private int pauseForPayment;
	private int pauseForEntryCheck;	
	private int pauseForBoarding;
	private int pauseInMannedKiosk;
	
	
	public CheckinController(){
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
		passengerQueue.list= passengerGenerator.InitialPassengersForQueue();
		
		kiosk1.SetBookings(bookings);
		kiosk2.SetBookings(bookings);
		kiosk1.SetFlights(flights);
		kiosk2.SetFlights(flights);

		pauseForPayment = 0;
		pauseForEntryCheck=0;	
		pauseForBoarding=0;
		pauseInMannedKiosk=0;
	
		NumPassengersInQueue=0;
	}
	
	public void StartCheckin(){
		passengerGenerator.run(); 
		//passengerQueue.list=passengerGenerator.PassengersToJoinTheQueue();
		kiosk1.addObserver(this);
		kiosk2.addObserver(this);
		mannedKiosk.addObserver(this);
		kiosk1.run();
		kiosk2.run();
		mannedKiosk.run();
		
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
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	//@Override 
	public void update() {
		// TODO Auto-generated method stub
/*		bookings=kiosk.getBookings(); 
		flights=kiosk.getFlights();
		passengerQueue.list=kiosk.getPassengerQueueList();
		kioskEvent=kiosk.getKioskEvent();
		NumPassengersInQueue=passengerQueue.list.size();*/
		
			
		ListOfObservables sourceOfEvent=getChangedSubject();
		switch (sourceOfEvent){
		case AUTO_KIOSK1:
			ApplyKioskUpdates(kiosk1);
			break;
		case AUTO_KIOSK2:
			ApplyKioskUpdates(kiosk2);
			break;			
		case MANNED_KIOSK:
			ApplyKioskUpdates(mannedKiosk);
			break;
		case PASSENGER_GENERATOR:
			break;
		case PASSENGERQUEUE:
			break;
		case SIMULATION_CLOCK:
			break;
		
		}
		
	}
	
	private ListOfObservables getChangedSubject(){
		if (kiosk1.hasChanged()){
			return ListOfObservables.AUTO_KIOSK1;
		}else if (kiosk2.hasChanged()){
			return ListOfObservables.AUTO_KIOSK2;
		}else if (mannedKiosk.hasChanged()){
			return ListOfObservables.MANNED_KIOSK;
		}
		return null;
	}
	
	private void ApplyKioskUpdates(AutoKiosk updatedKiosk){
		Object updatedElement=updatedKiosk.getUpdatedElement();
		kioskEvent=updatedKiosk.getKioskEvent();
		if (updatedElement instanceof Booking){
			Booking updatedBooking=(Booking) updatedElement;
			bookings.replace(updatedBooking.GetBookingReference(), updatedBooking);
		}
		if (updatedElement instanceof Flight){
			Flight updatedFlight=(Flight) updatedElement;
			flights.replace(updatedFlight.FlightCode(), updatedFlight);
		}
		if (updatedElement instanceof PassengerQueue){
			PassengerQueue updatedPassengerQueue=(PassengerQueue) updatedElement;
			passengerQueue=updatedPassengerQueue;
			//.HeadToKiosk();// current passenger is no longer in the Queue		}
		}

	}

	
	private void ApplyKioskUpdates(MannedKiosk updatedKiosk){

	}
	
	private void SetKiosksPause(){
		pauseForPayment = simulationSpeed.RandomWaitTime(200);
		pauseForEntryCheck=simulationSpeed.TimeToEnterDetails();	
		pauseForBoarding=simulationSpeed.RandomWaitTime(50);

		kiosk1.SetPauseForPayment(pauseForPayment);
		kiosk1.SetPauseForEntryCheck(pauseForEntryCheck);
		kiosk1.SetPauseForBoarding(pauseForBoarding);
		kiosk2.SetPauseForPayment(pauseForPayment);
		kiosk2.SetPauseForEntryCheck(pauseForEntryCheck);
		kiosk2.SetPauseForBoarding(pauseForBoarding);
		mannedKiosk.SetPauseTotal(pauseInMannedKiosk);
	}

	
	
}
