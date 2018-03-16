/**
 * 
 */
package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.util.ArrayList;
import java.util.Observable;
import java.util.TreeMap;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.BaggageDetails;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Booking;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Flight;

/**Simulates the Kiosks
 * @author mehdi seddiq (ms256)
 *
 */
public class Kiosk extends Observable implements Runnable {
	private static final int PAUSE_MANUAL_KIOSK = 50;
	private static final int PAUSE_FEE=100;	
	private static final int PAUSE_BOARDING=10;
	private TreeMap<String, Booking> bookings;
	private TreeMap<String, Flight> flights;
	private ArrayList<Passenger> passengerQueue;
	public boolean checkinRunning;
	//private KioskSearch kioskSearch;
	private String kioskEvent; //the message showing recent event happened in kiosk 
	//private static final int MAX_AUTO_ATTEMPTS=3; // maximum times that Auto Desks check passenger's name/bookingRef 
	/**
	 * Constructor for Kiosk
	 * @param
	 */
	public Kiosk(){
		checkinRunning=true;
		kioskEvent=null;
	}
	
	/** (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run(){
		Booking currentBooking;
		Flight currentFlight;
		String bookingRef, flightCode, surname;
		Passenger currentPassenger;
		Attempt attempt;
		BaggageDetails baggageInfo;
		double fee;
		boolean entryValid; 
		while (checkinRunning){
			currentPassenger=passengerQueue.get(0);
			passengerQueue.remove(0);// current passenger is no longer in the Queue
			do{ 
				attempt= currentPassenger.CheckInDetails();
				bookingRef=attempt.BookingReference();
				if (VerifyBookingRef(bookingRef)){
					entryValid=true;
					surname=attempt.Surname();
					if (!VerifySurname(surname)){
						entryValid=false;
					}
				}
				else{
					entryValid=false;
				}
			}while (!attempt.UseMannedKiosk() && !entryValid);
			if (attempt.UseMannedKiosk()){
				//setChanged();
				notifyObservers();
				//eventExpression='Passenger with booking reference '+currentPassenger.BookingRef+' directed to manual kiosk';
				//notify view
				try {
					Thread.sleep(PAUSE_MANUAL_KIOSK);
				} catch (InterruptedException e) {
					//System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
			else{ //entryValid would be true
				currentBooking=bookings.get(bookingRef);
				baggageInfo=currentBooking.GetBaggageInfo();
				flightCode=currentBooking.GetFlightCode();
				currentFlight=flights.get(flightCode);
				fee=baggageInfo.Fee();
				if (fee!=0){
					//eventExpression='Passenger with booking reference'+currentPassenger.BookingRef+'charged �' + fee + ' for their Baggage';					
					currentFlight.AddToFees(fee);
					try {
						Thread.sleep(PAUSE_FEE);
					} catch (InterruptedException e) {
						//System.out.println(e.getMessage());
						e.printStackTrace();
					}
				}
				//eventExpression='Passenger with booking reference'+currentPassenger.BookingRef+'sent to boarding';
				currentBooking.CheckIn();
				currentFlight.AddToWeight(baggageInfo.Weight());
				currentFlight.AddToVolume(baggageInfo.Volume());
				//notify view
				try {
					Thread.sleep(PAUSE_BOARDING);
				} catch (InterruptedException e) {
					//System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
				
		}
	}// run
	
	private Boolean VerifyBookingRef (String refString) {
		final int referenceSize=7;
		if (refString == null || refString.isEmpty() || 
				refString.toCharArray().length <referenceSize ||
				refString.toCharArray().length >referenceSize) {
			return false;
		}
		return true;
	}
 
	private Boolean VerifySurname(String surString) {
		if (surString == null || surString.isEmpty()) {
			return false;
		}
		for(char c : surString.toCharArray()){
	        if(Character.isDigit(c)|| Character.isSpaceChar(c)){
	        	return false;
	        }
		}        
		return true;
	}

	public void SetBookings(TreeMap<String, Booking> givenBookings){
		bookings=givenBookings;
	}
	
	public void SetFlights(TreeMap<String, Flight> givenFlights){
		flights=givenFlights;
	}
	
	public TreeMap<String, Booking> getBookings(){
		return bookings;
	}
	
	public TreeMap<String, Flight> getFlights(){
		return flights;
	}	

	// check return type
	public ArrayList<Passenger> getPassengerQueueList(){
		return passengerQueue; 
	}
	
	public String getKioskEvent(){
		return kioskEvent;
	}
	
}

