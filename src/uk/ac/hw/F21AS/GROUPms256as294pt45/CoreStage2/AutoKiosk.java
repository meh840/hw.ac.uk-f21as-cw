/**
 * 
 */
package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.util.ArrayList;
import java.util.Observable;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.BaggageDetails;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Booking;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Flight;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.KioskCheckIn;

/**Simulates the AutoKiosks
 * @author mehdi seddiq (ms256)
 *
 */
public class AutoKiosk extends Observable implements Runnable {
	private TreeMap<String, Booking> bookings;
	private TreeMap<String, Flight> flights;
	private ArrayList<Passenger> passengerQueue;
	private boolean checkinRunning;
	private boolean queueEmpty;
	private String kioskEvent; //the message showing recent event happened in kiosk
	private String entrySatus; //the text showing the validity of booking reference and surname
	private Object updatedElement;
	private int pauseForPayment;
	private int pauseForEntryCheck;	
	private int pauseForBoarding;
 
	/**
	 * Constructor for AutoKiosk
	 * @param
	 */
	public AutoKiosk(){
		checkinRunning=true;
		queueEmpty=true;
		kioskEvent=null;
		entrySatus=null;
		updatedElement=null;
		pauseForPayment = 0;
		pauseForEntryCheck=0;	
		pauseForBoarding=0;
		
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
		boolean entryIsValid;
		while (checkinRunning){
			while (queueEmpty) {
				try { wait(); }
				catch (InterruptedException e) {}
			}
			currentPassenger=passengerQueue.get(0);
			updatedElement=passengerQueue;
			setChanged();
			notifyObservers();
			do{ 
				attempt= currentPassenger.CheckInDetails();
				bookingRef=attempt.BookingReference();
				surname=attempt.Surname();
				entryIsValid=VerifyBooking(bookingRef,surname);
/*				if (entryIsValid){
				}
				else{
				}*/
			}while (!attempt.UseMannedKiosk() && !entryIsValid);
			if (attempt.UseMannedKiosk()){//the Passenger is sent to Manned Kiosk
				kioskEvent="Passenger with booking reference " +
						currentPassenger.CheckInDetails().BookingReference() +
						" directed to manual kiosk";
				notifyObservers();
				try {
					Thread.sleep(PAUSE_MANUAL_KIOSK);
				} catch (InterruptedException e) {
					//System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
			else{ //entryIsValid
				currentBooking=bookings.get(bookingRef);
				baggageInfo=currentBooking.GetBaggageInfo();
				flightCode=currentBooking.GetFlightCode();
				currentFlight=flights.get(flightCode);
				fee=baggageInfo.Fee();
				if (fee!=0){
					kioskEvent="Passenger with booking reference " + bookingRef +
							" charged £" + fee + " for baggage";
					try {
						Thread.sleep(PAUSE_FEE);
					} catch (InterruptedException e) {
						//System.out.println(e.getMessage());
						e.printStackTrace();
					}
				}
				kioskEvent="Passenger with booking reference " + bookingRef +
						" sent to Plane";
				currentBooking.CheckIn();
				currentFlight.AddToFees(fee);				
				currentFlight.AddToWeight(baggageInfo.Weight());
				currentFlight.AddToVolume(baggageInfo.Volume());
				notifyObservers();
				try {
					Thread.sleep(PAUSE_BOARDING);
				} catch (InterruptedException e) {
					//System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
				
		}
	}// run

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
			entrySatus= "The given string does not match the format of booking reference";
			return false;
		}
		if (!CheckSurnameFormat(surString)){
			entrySatus= "The given string could not be a surname";
			return false;			
		}
		if (bookings.get(refString)==null){
			entrySatus= "The given code is not found in the booking records";
			return false;			
		}
		if (!surString.equals(bookings.get(refString).GetSurname())){
			entrySatus= "The given surname does not correspond to the recorded booking refernce";
			return false;			
		}
		entrySatus= "The booking was found in the records successfully";
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
	
	public void SetCheckinRunning(boolean givenCheckinRunning){
		this.checkinRunning=givenCheckinRunning;
	}
	
	public void SetQueueEmpty(boolean givenQueueEmpty){	
		this.queueEmpty=givenQueueEmpty;
	}
	
	public void SetPauseForPayment(int givenPauseForPayment){
		pauseForPayment=givenPauseForPayment;
	}
	
	public void SetPauseForEntryCheck(int givenPauseForEntryCheck){
		pauseForEntryCheck=givenPauseForEntryCheck;
	}

	public void SetPauseForBoarding(int givenPauseForBoarding){
		pauseForBoarding=givenPauseForBoarding;
	}	
	
	public String getKioskEvent(){
		return kioskEvent;
	}
	
	public Object getUpdatedElement(){
		return updatedElement;
	}
	
}


