/**
 * 
 */
package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.util.ArrayList;
import java.util.TreeMap;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Booking;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.KioskSearch;

/**Simulates the Desks
 * @author mehdi seddiq (ms256)
 *
 */
public class Kiosk extends Thread{
	private static TreeMap<String, Booking> bookings;
	private static ArrayList<Passenger> passengerQueue;
	private boolean CheckinRunning=true;
	private KioskSearch kioskSearch;
	//private static final int MAX_AUTO_ATTEMPTS=3; // maximum times that Auto Desks check passenger's name/bookingRef 
	/**
	 * Constructor for Kiosk
	 * @param
	 */
	public Kiosk(TreeMap<String, Booking> givenBookings, ArrayList<Passenger> givenPassengerQueue){
		bookings=givenBookings;
		passengerQueue=givenPassengerQueue;
	}
	
	/**
	 * 
	 */
	public void run(){
		String bookingRef, surname;
		while (CheckinRunning){
			bookingRef=passengerQueue.get(0).CheckInDetails().BookingReference();
			surname=passengerQueue.get(0).CheckInDetails().Surname();
			
		}
	}
	
	private Boolean check_reference (String refString) {
		final int referenceSize=7;
		if (refString == null || refString.isEmpty() || 
				refString.toCharArray().length <referenceSize ||
				refString.toCharArray().length >referenceSize) {
			return false;
		}
		return true;
	}
 
	private Boolean check_surname(String surString) {
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
	
}

