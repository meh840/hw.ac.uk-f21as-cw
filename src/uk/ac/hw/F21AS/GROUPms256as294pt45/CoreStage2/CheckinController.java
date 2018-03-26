
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
 * Interacts with GUI of stage 2, interacts with AutoKiosks and MannedKiosk,
 * interacts with data/log files  
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
	private AutoKiosk autoKiosk1, autoKiosk2;
	private MannedKiosk mannedKiosk;
	//private String kiosk1Event, kiosk2Event;
	private ArrayList<String> CurrentEvent;
	private SimulationClock simulationClock;
	private boolean checkinRunning;
	private SimulationGUI gui;
	private static String kioskTitle;
	private static Passenger currentPassenger;
		
	public CheckinController(){
		gui = new SimulationGUI();
		
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
		checkinRunning=true;
		Thread clockThread = new Thread(simulationClock);
		clockThread.start();
		
		autoKiosk1 = new AutoKiosk();
		autoKiosk2 = new AutoKiosk();
		mannedKiosk = new MannedKiosk();
		
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
		Thread pgThread = new Thread(passengerGenerator);
		pgThread.start(); 
		
		// Define Kiosks.
		autoKiosk1.SetKioskNumber(1);
		autoKiosk2.SetKioskNumber(2);
		
		// Set up observations.
		autoKiosk1.addObserver(this);
		autoKiosk2.addObserver(this);
		//mannedKiosk.addObserver(this);
		passengerGenerator.addObserver(this);
		passengerQueue.addObserver(this);
		
		// Start up desks.
		Thread kiosk1Thread = new Thread(autoKiosk1);
		//kiosk1Thread.start();
		Thread kiosk2Thread = new Thread(autoKiosk2);
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
		gui.settheList(passengerQueue.HeadOfTheQueue());
		gui.setqueuestage(Integer.toString(passengerQueue.SizeOfQueue()));
		gui.setclock(simulationClock.CurrentTime());
		//gui.setflight1title(flighttitle);
		// TODO: Give gui queue information passengerQueue.HeadOfTheQueue()
	}
	
	@Override
	public void update(Observable observable, Object object) {
		ObservablesList sourceOfEvent=GetChangedSubject();
		switch (sourceOfEvent){
		case AUTO_KIOSK1:
			ApplyKioskUpdates(autoKiosk1);
			break;
		case AUTO_KIOSK2:
			ApplyKioskUpdates(autoKiosk2);
			break;			
		case MANNED_KIOSK:
			//ApplyMannedKioskUpdates((Passenger)object);
			//TODO: What is being passed in?
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
	
	private ObservablesList GetChangedSubject(){
		if (autoKiosk1.hasChanged()){
			return ObservablesList.AUTO_KIOSK1;
		}else if (autoKiosk2.hasChanged()){
			return ObservablesList.AUTO_KIOSK2;
		}else if (mannedKiosk.hasChanged()){
			return ObservablesList.MANNED_KIOSK;
		}else if (passengerGenerator.hasChanged()){
			return ObservablesList.PASSENGER_GENERATOR;
		}else if (passengerQueue.hasChanged()){
			return ObservablesList.PASSENGER_QUEUE;
		}else if (simulationClock.hasChanged()){
			return ObservablesList.SIMULATION_CLOCK;
		}
		return null;
	}

	/**
	 * @param mannedKiosk2
	 */
	private void ApplyMannedKioskUpdates(MannedKiosk updatedKiosk) {
		// TODO Auto-generated method stub
		updatedKiosk.SetKioskStatus(KioskStatusList.WAITING_FOR_PASSENGER);
		String bookingRef = updatedKiosk.GetAttempt().BookingReference();
		Booking booking = bookings.get(bookingRef);
		BaggageDetails baggageInfo = updatedKiosk.GetBaggageInfo();
		double fee = baggageInfo.Fee();
		booking.CheckIn();
		String flightCode = booking.GetFlightCode();
		Flight flight = flights.get(flightCode);
		flight.AddToFees(fee);				
		flight.AddToWeight(baggageInfo.Weight());
		flight.AddToVolume(baggageInfo.Volume());
		String kioskEvent = "Passenger with booking reference "+ bookingRef + " was sent to Plane";
		updatedKiosk.SetKioskEvent(kioskEvent);
		String str = " MannedKiosk : "+kioskEvent;
		CurrentEvent.add(str);
		
		
		
	}
	
	private void ApplyKioskUpdates(AutoKiosk updatedKiosk){
		
/*		if (updatedKiosk1 instanceof AutoKiosk){
			AutoKiosk updatedKiosk= (AutoKiosk) updatedKiosk1;
		}else{
			MannedKiosk updatedKiosk=(MannedKiosk) updatedKiosk1;
		}*/
		KioskStatusList kioskStatusList=updatedKiosk.GetKioskStatus();
		Passenger passenger;
		Attempt attempt;
		String bookingRef, flightCode, kioskEvent;
		Booking booking;
		Flight flight;
		BaggageDetails baggageInfo;
		double fee;
		mannedKiosk.SetPassenger(null);
		String str;
		boolean planeIsAvailable;
		switch (kioskStatusList){
/*		case HAS_PASSENGER:
			updatedKiosk.SetPassenger(passengerQueue.HeadToKiosk());
			kioskEvent="A Passenger from the Queue just referred";
			str=" AutoKiosk "+updatedKiosk.GetKioskNumber()+" : "+kioskEvent;
			CurrentEvent.add(str);
			break;*/
		case CHECKING_ENTRIES:
			attempt=updatedKiosk.GetAttempt();
			bookingRef=attempt.BookingReference();
			updatedKiosk.SetBookingRefIsValid(bookings.containsKey(bookingRef));
			kioskEvent="Passenger with booking reference "+bookingRef+
					" is entering their details";
			updatedKiosk.SetKioskEvent(kioskEvent);
			str=" AutoKiosk "+updatedKiosk.GetKioskNumber()+" : "+kioskEvent;
			CurrentEvent.add(str);
			break;
		case SEND_TO_MANNED_KIOSK:
			updatedKiosk.SetKioskStatus(KioskStatusList.WAITING_FOR_PASSENGER);
			passenger= updatedKiosk.GetPassenger();
			mannedKiosk.SetPassenger(passenger);
			kioskEvent="A passenger came";
			mannedKiosk.SetKioskEvent(kioskEvent);			
			str=" MannedKiosk : "+ kioskEvent;
			CurrentEvent.add(str);			
			//TODO:
			Object[] info = passenger.QueueDisplayInformation();
			flightCode=(String) info[2];
			break;
		case GETTING_BAGGAGE:
			bookingRef=updatedKiosk.GetAttempt().BookingReference();
			kioskEvent="Booking details of the Passenger with booking reference " + bookingRef +
			" was verified. Baggage was received";
			updatedKiosk.SetKioskEvent(kioskEvent);
			str=" AutoKiosk "+updatedKiosk.GetKioskNumber()+" : "+kioskEvent;
			CurrentEvent.add(str);
			break;
		case DOING_PAYMENT:
			bookingRef=updatedKiosk.GetAttempt().BookingReference();
			baggageInfo=updatedKiosk.GetBaggageInfo();
			fee=baggageInfo.Fee();
			kioskEvent="Passenger with booking reference " + bookingRef +
			" was charged �" + fee + " for baggage";
			updatedKiosk.SetKioskEvent(kioskEvent);
			str=" AutoKiosk "+updatedKiosk.GetKioskNumber()+" : "+kioskEvent;
			CurrentEvent.add(str);
			break;
		case CHECK_PLANE:
			bookingRef=updatedKiosk.GetAttempt().BookingReference();
			booking=bookings.get(bookingRef);
			flightCode=booking.GetFlightCode();
			planeIsAvailable=planes.get(flightCode).StillAvailableForBoarding();
			updatedKiosk.SetPlaneIsAvailable(planeIsAvailable);
			if (!planeIsAvailable){
				updatedKiosk.SetKioskStatus(KioskStatusList.WAITING_FOR_PASSENGER);
				kioskEvent="Flight Number "+flightCode+" has left. "+
						"Passenger with booking reference " + bookingRef +
						" failed to chick-in on-time.";
				updatedKiosk.SetKioskEvent(kioskEvent);
				str=" AutoKiosk "+updatedKiosk.GetKioskNumber()+" : "+kioskEvent;
				CurrentEvent.add(str);
			}
			break;
		case SEND_TO_PLANE:
			updatedKiosk.SetKioskStatus(KioskStatusList.WAITING_FOR_PASSENGER);
			bookingRef=updatedKiosk.GetAttempt().BookingReference();
			booking=bookings.get(bookingRef);
			baggageInfo=updatedKiosk.GetBaggageInfo();
			fee=baggageInfo.Fee();
			booking.CheckIn();
			flightCode=booking.GetFlightCode();
			flight=flights.get(flightCode);
			flight.AddToFees(fee);				
			flight.AddToWeight(baggageInfo.Weight());
			flight.AddToVolume(baggageInfo.Volume());
			kioskEvent="Passenger with booking reference "+ bookingRef + " was sent to Plane";
			updatedKiosk.SetKioskEvent(kioskEvent);
			str=" AutoKiosk "+updatedKiosk.GetKioskNumber()+" : "+kioskEvent;
			CurrentEvent.add(str);
			break;
		default:
			break;
		}

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
			
			if (!passengerQueue.IsQueueEmpty()){
				if (autoKiosk1.GetKioskStatus()==KioskStatusList.WAITING_FOR_PASSENGER){
					autoKiosk1.SetQueueEmpty(false);
				} else if (autoKiosk2.GetKioskStatus()==KioskStatusList.WAITING_FOR_PASSENGER){
					autoKiosk2.SetQueueEmpty(false);
				}
			}
		}
		
		if(passengerQueue.HasChangeToQueueDisplayInfoBeenMade() ) {
			// TODO: Use passengerQueue.HeadOfTheQueue() to update GUI queue display. 
			passengerQueue.ResetChangeToQueueDisplayIndicator();
		}
		
	}
	
	private void ClockUpdate() {
		if(simulationClock.IsThereADepartureDue()) {
			//TODO: Check planes 
		} else {
			
		}
	}
}

