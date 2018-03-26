
package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;
import java.util.Map.Entry;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.BaggageDetails;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Booking;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.BookingLoader;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.ErrorLogger;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Flight;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.FlightLoader;

/**
 * Controller in the MVC pattern
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
	private TreeMap<String,Plane> planes;
	private ArrayList<String> invalidFormatErrors;
	private ErrorLogger errorLogger;
	private PassengerGenerator passengerGenerator;
	private PassengerQueue passengerQueue;
	private AutoKiosk kiosk1, kiosk2;
	private MannedKiosk mannedKiosk;
	private String kiosk1Event, kiosk2Event;
	private ArrayList<String> CurrentEvent;
	private SimulationClock simulationClock;
	private StageSelectionFrame gui;
		
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
		planes = new TreeMap<String, Plane>();
		
		errorLogger = new ErrorLogger();
		simulationClock = SimulationClock.GetInstance();
		Thread clockThread = new Thread(simulationClock);
		clockThread.start();
		
		
		CollectDataFromFiles();
		
		// Set up passenger generation and have an initial amount in the queue.
		passengerGenerator = new PassengerGenerator(bookings, invalidFormatErrors);
		passengerQueue = new PassengerQueue();
		ArrayList<Passenger> passengersForQueue = passengerGenerator.InitialPassengersForQueue();
		for(Passenger newPassenger : passengersForQueue) {
			passengerQueue.PassengerJoiningQueue(newPassenger);
		}
		
		PreparePlanes();
		PrepareGUI();
	}
	
	public void StartCheckin(){
		// Start having passengers randomly join queue.
		passengerGenerator.run(); 
		//simulationClock = SimulationClock.GetInstance();
		// Define Kiosks.
		kiosk1.SetKioskNumber(1);
		kiosk2.SetKioskNumber(2);
		
		// Set up observations.
		kiosk1.addObserver(this);
		kiosk2.addObserver(this);
		mannedKiosk.addObserver(this);
		passengerGenerator.addObserver(this);
		passengerQueue.addObserver(this);
		
		// Start up desks.
		Thread kiosk1Thread = new Thread(kiosk1);
		//kiosk1Thread.start();
		Thread kiosk2Thread = new Thread(kiosk2);
		//kiosk2Thread.start();
		Thread mannedKioskThread = new Thread(mannedKiosk);
		//mannedKioskThread.start();
		
		// Send passengers to kiosks.
	
	}

	/**
	 * Loads Booking and Flight Data.
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
	
	/**
	 * Create the planes for the passengers to board.
	 */
	private void PreparePlanes() {
		// Create planes for all flights.
		for(Entry<String, Flight> entry : flights.entrySet()) {
		  Flight value = entry.getValue();

		  Plane newPlane = new Plane(value.FlightCode(), value);
		  planes.put(value.FlightCode(), newPlane);
		}
	}
	
	public void PrepareGUI() {
		gui = new StageSelectionFrame();
		// TODO: Give gui queue information passengerQueue.HeadOfTheQueue()
	}
	
	@Override
	public void update(Observable observable, Object object) {
		ListOfObservables sourceOfEvent=GetChangedSubject();
		switch (sourceOfEvent){
		case AUTO_KIOSK1:
			ApplyKioskUpdates(kiosk1);
			break;
		case AUTO_KIOSK2:
			ApplyKioskUpdates(kiosk2);
			break;			
		case MANNED_KIOSK:
			ApplyMannedKioskUpdates(mannedKiosk);
			break;
		case PASSENGER_GENERATOR:
			GetPassengersForQueue();
			break;
		case PASSENGER_QUEUE:
			QueueUpdate(); 
			break;
		case SIMULATION_CLOCK:
			break;
		default:
			break;
		
		}
	}
	
	private ListOfObservables GetChangedSubject(){
		if (kiosk1.hasChanged()){
			return ListOfObservables.AUTO_KIOSK1;
		}else if (kiosk2.hasChanged()){
			return ListOfObservables.AUTO_KIOSK2;
		}else if (mannedKiosk.hasChanged()){
			return ListOfObservables.MANNED_KIOSK;
		}else if (passengerGenerator.hasChanged()){
			return ListOfObservables.PASSENGER_GENERATOR;
		}else if (passengerQueue.hasChanged()){
			return ListOfObservables.PASSENGER_QUEUE;
		}else if (simulationClock.hasChanged()){
			return ListOfObservables.SIMULATION_CLOCK;
		}
		return null;
	}

	/**
	 * @param mannedKiosk2
	 */
	private void ApplyMannedKioskUpdates(Passenger givenPassenger) {
		// TODO Auto-generated method stub
		
		
		
	}
	
	private void ApplyKioskUpdates(AutoKiosk updatedKiosk){
		KioskStatus kioskStatus=updatedKiosk.GetKioskStatus();
		Passenger passenger;
		Attempt attempt;
		String bookingRef, surname, flightCode, kioskEvent;
		Booking booking;
		Flight flight;
		BaggageDetails baggageInfo;
		double fee;
		mannedKiosk.SetPassenger(null);
		String str;
		switch (kioskStatus){
/*		case GET_PASSENGER:
			updatedKiosk.SetPassenger(passengerQueue.HeadToKiosk());
			kioskEvent="A Passenger from the Queue just referred";
			str=" AutoKiosk "+updatedKiosk.GetKioskNumber()+" : "+kioskEvent;
			CurrentEvent.add(str);
			break;*/
		case CHECK_ENTRIES:
			passenger= updatedKiosk.GetPassenger();
			attempt=passenger.CheckInDetails();
			bookingRef=attempt.BookingReference();
			//surname=attempt.Surname();
			updatedKiosk.SetBookingRefIsValid(bookings.containsKey(bookingRef));
			kioskEvent="Passenger with booking reference "+bookingRef+
					" is entering their details";
			str=" AutoKiosk "+updatedKiosk.GetKioskNumber()+" : "+kioskEvent;
			CurrentEvent.add(str);
			break;
		case SEND_TO_MANNED_KIOSK:
			passenger= updatedKiosk.GetPassenger();
			mannedKiosk.SetPassenger(passenger);
			//TODO:
			Object[] info = passenger.QueueDisplayInformation();
			flightCode=(String) info[2];
			str="A passenger was sent from the Manned Kiosk to Flight No."+flightCode;
			CurrentEvent.add(str);
			break;
		case GET_BAGGAGE:
			passenger= updatedKiosk.GetPassenger();
			bookingRef=passenger.CheckInDetails().BookingReference();
			kioskEvent="Passenger with booking reference " + bookingRef +
			" delivered  their baggage";
			str=" AutoKiosk "+updatedKiosk.GetKioskNumber()+" : "+kioskEvent;
			CurrentEvent.add(str);
			break;
		case DO_PAYMENT:
			passenger= updatedKiosk.GetPassenger();
			bookingRef=passenger.CheckInDetails().BookingReference();
			booking=bookings.get(bookingRef);
			baggageInfo=updatedKiosk.GetBaggageInfo();
			fee=baggageInfo.Fee();
			kioskEvent="Passenger with booking reference " + bookingRef +
			" charged �" + fee + " for baggage";
			str=" AutoKiosk "+updatedKiosk.GetKioskNumber()+" : "+kioskEvent;
			CurrentEvent.add(str);
			break;
		case SEND_TO_PLANE:
			passenger= updatedKiosk.GetPassenger();
			bookingRef=passenger.CheckInDetails().BookingReference();
			booking=bookings.get(bookingRef);
			baggageInfo=booking.GetBaggageInfo();
			fee=baggageInfo.Fee();
			booking.CheckIn();
			flightCode=booking.GetFlightCode();
			flight=flights.get(flightCode);
			flight.AddToFees(fee);				
			flight.AddToWeight(baggageInfo.Weight());
			flight.AddToVolume(baggageInfo.Volume());
			kioskEvent="Passenger with booking reference "+ bookingRef + " sent to Plane";
			str=" AutoKiosk "+updatedKiosk.GetKioskNumber()+" : "+kioskEvent;
			CurrentEvent.add(str);
			break;
		default:
			break;
		}

	}

	/**
	 * checks if the given strings refer to a valid booking in the records 
	 * of Booking Reference and whether it is found in the record of booking list. 
	 * @param refString is checked to match the predefined format of Booking Reference 
	 * and also to exist into the recorded bookings
	 * @param surString is checked to be a typical surname
	 * also it is checked if it matches the booking reference
	 * @return a String showing the validity of the inputs
	 *  if the given string matches one of the booking records
	 * or otherwise what the type of mismatch is there  
	 */
	
	private void GetPassengersForQueue() {
		ArrayList<Passenger> passengersForQueue = passengerGenerator.PassengersToJoinTheQueue();
		for(Passenger newPassenger : passengersForQueue) {
			passengerQueue.PassengerJoiningQueue(newPassenger);
		}
		
		passengerGenerator.PassengersAreNowInQueue();
	}
		
	private void QueueUpdate() {
		if(passengerQueue.HasAPassengerJoinedTheQueue()) {
			// TODO: Use passengerQueue.SizeOfQueue to update GUI size of queue info.
			passengerQueue.ResetPassengerJoinedIndicator();
			
			if (!passengerQueue.IsQueueEmpty()){
				if (kiosk1.GetKioskStatus()==KioskStatus.WAIT_FOR_PASSENGER){
					kiosk1.SetQueueEmpty(false);
				} else if (kiosk2.GetKioskStatus()==KioskStatus.WAIT_FOR_PASSENGER){
					kiosk2.SetQueueEmpty(false);
				}
			}
				}
		
		if(passengerQueue.HasChangeToQueueDisplayInfoBeenMade() ) {
			// TODO: Use passengerQueue.HeadOfTheQueue() to update GUI queue display. 
			passengerQueue.ResetChangeToQueueDisplayIndicator();
		}
		
	}
}

