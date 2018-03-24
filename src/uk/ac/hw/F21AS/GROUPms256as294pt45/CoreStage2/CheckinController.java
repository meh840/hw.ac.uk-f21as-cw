
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
	private ArrayList<String> invalidFormatErrors;
	private ErrorLogger errorLogger;
	private PassengerGenerator passengerGenerator;
	private PassengerQueue passengerQueue;
	private AutoKiosk kiosk1, kiosk2;
	private MannedKiosk mannedKiosk;
	public String kioskEvent;
	private SimulationClock simulationClock;
		
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
		simulationClock = SimulationClock.GetInstance();
		
		CollectDataFromFiles();
		
		// Set up passenger generation and have an initial amount in the queue.
		passengerGenerator = new PassengerGenerator(bookings, invalidFormatErrors);
		passengerQueue = new PassengerQueue();
		ArrayList<Passenger> passengersForQueue = passengerGenerator.InitialPassengersForQueue();
		for(Passenger newPassenger : passengersForQueue) {
			passengerQueue.PassengerJoiningQueue(newPassenger);
		}
	}
	
	public void StartCheckin(){
		// Start having passengers randomly join queue.
		passengerGenerator.run(); 
		
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
		kiosk1.run();
		kiosk2.run();
		mannedKiosk.run();
		
		// Add Display Events GUI
		
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
	//		case MANNED_KIOSK:
	//			ApplyKioskUpdates(mannedKiosk);
	//			break;
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

	
	private void ApplyKioskUpdates(AutoKiosk updatedKiosk){
		KioskStatus kioskStatus=updatedKiosk.GetKioskStatus();
		Passenger passenger;
		Attempt attempt;
		String bookingRef, surname, flightCode;
		Booking booking;
		Flight flight;
		BaggageDetails baggageInfo;
		double fee;
		switch (kioskStatus){
		case GET_PASSENGER:
			updatedKiosk.SetPassenger(passengerQueue.HeadToKiosk());
			kioskEvent="A Passenger from the Queue refered to AutoKiosk "+updatedKiosk.GetKioskNumber();
			break;
		case CHECK_ENTRIES:
			passenger= updatedKiosk.GetPassenger();
			attempt=passenger.CheckInDetails();
			bookingRef=attempt.BookingReference();
			surname=attempt.Surname();
			updatedKiosk.SetEntryIsValid(VerifyBooking(bookingRef,surname));
			updatedKiosk.SetUseManned(attempt.UseMannedKiosk());
			kioskEvent="Passenger with booking reference "+bookingRef+
					" is entering their details on AutoKiosk "+updatedKiosk.GetKioskNumber();
			break;
		case SEND_TO_MANNED_KIOSK:
			passenger= updatedKiosk.GetPassenger();
			Object[] info = passenger.QueueDisplayInformation();
			flightCode=(String) info[2];
			kioskEvent="A passenger was sent from the Manned Kiosk to Flight No."+flightCode;
			break;
		case GET_BAGGAGE:
			passenger= updatedKiosk.GetPassenger();
			bookingRef=passenger.CheckInDetails().BookingReference();
			booking=bookings.get(bookingRef);
			baggageInfo=booking.GetBaggageInfo();
			fee=baggageInfo.Fee();
			updatedKiosk.SetFee(fee);
			kioskEvent="Passenger with booking reference " + bookingRef +
			" on AutoKiosk "+updatedKiosk.GetKioskNumber()+" charged £" +
					fee + " for baggage";
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
			kioskEvent="Passenger with booking reference "+ bookingRef +
					" on AutoKiosk "+updatedKiosk.GetKioskNumber()+" sent to Plane";
			break;
		default:
			break;
		}

	}

	/**
	 * verifies if the given string matches the predefined format of booking references	
	 * @param refString is a string which is supposed to be a booking reference
	 * @returns true if the string matches the format, false otherwise
	 */
	private Boolean CheckBookingRefFormat (String refString) {
		final int referenceSize=7;
		String bookingRef=refString.trim();
		bookingRef=bookingRef.toUpperCase();
		if (refString == null || refString.isEmpty() || 
			refString.toCharArray().length <referenceSize ||
			refString.toCharArray().length >referenceSize ||
			!(Character.isLetter(bookingRef.charAt(0)) &&
			  Character.isLetter(bookingRef.charAt(1)) &&
			  Character.isDigit(bookingRef.charAt(2)) &&
			  Character.isDigit(bookingRef.charAt(3)) &&
			  Character.isLetter(bookingRef.charAt(4)) &&
			  Character.isLetter(bookingRef.charAt(5)) &&
			  Character.isDigit(bookingRef.charAt(6)))){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * verifies the given string to be potentially a surname 
	 * @param refString which is supposed to be a surname
	 * @return true if the input is deduced as a surname, false otherwise
	 */
	private Boolean CheckSurnameFormat(String surString) {
		String surname=surString.trim();
		String s1 = surname.substring(0, 1).toUpperCase();
		surname=s1 + surname.substring(1).toLowerCase();
		if (surString == null || surString.isEmpty()) {
			return false;
		}
		for(char c : surString.toCharArray()){
	        if(Character.isDigit(c) || Character.isSpaceChar(c)){
	        	return false;
	        }
		}
		return true;
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
	private boolean VerifyBooking (String refString, String surString) {
		if (!CheckBookingRefFormat(refString)){
			kioskEvent= "The given string does not match the format of booking reference";
			return false;
		}
		if (!CheckSurnameFormat(surString)){
			kioskEvent= "The given string could not be a surname";
			return false;			
		}
		if (bookings.get(refString)==null){
			kioskEvent= "The given code is not found in the booking records";
			return false;			
		}
		if (!surString.equals(bookings.get(refString).GetSurname())){
			kioskEvent= "The given surname does not correspond to the recorded booking refernce";
			return false;			
		}
		kioskEvent= "The booking was found in the records successfully";
		return true;
	}
	
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
		}
		
		if(passengerQueue.HasChangeToQueueDisplayInfoBeenMade() ) {
			// TODO: Use passengerQueue.HeadOfTheQueue() to update GUI queue display. 
			passengerQueue.ResetChangeToQueueDisplayIndicator();
		}
	}
}
