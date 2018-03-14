package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.util.ArrayList;
import java.util.Map.Entry;

import jdk.nashorn.internal.ir.WhileNode;

import java.util.Observable;
import java.util.TreeMap;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Booking;

/**
 * @author Alan Spence (as294)
 *
 */
public class PassengerGenerator extends Observable implements Runnable  {
	private ArrayList<Passenger> passengers;
	private ArrayList<Passenger> goingToQueue; 
	private int maxInitialNumber, maxJoinAmount, maxWaitTime;
	private RuntimeSpeedController simulationSpeed;
	
	/**
	 * Constructor which makes a collection of passengers from all the entries from the booking file. 
	 * Also sets the maximum initial list size of 30, maximum join queue of 5 and maximum wait time of 60seconds.
	 * @param Bookings The valid bookings loaded from the bookings file.
	 * @param invalidFormats The invalid bookings loaded from the booking file.
	 */
	public PassengerGenerator(TreeMap<String, Booking> Bookings, ArrayList<String> invalidFormats) {
		passengers = new ArrayList<Passenger>();
		goingToQueue = new ArrayList<Passenger>();
		maxInitialNumber = 30;
		maxJoinAmount = 5;
		maxWaitTime = 60;
		simulationSpeed = RuntimeSpeedController.getInstance();
		Passenger newPassenger;
		
		// Create passengers for all valid bookings.
		for(Entry<String, Booking> entry : Bookings.entrySet()) {
		  Booking value = entry.getValue();

		  newPassenger = new Passenger(value);
		  passengers.add(newPassenger);
		}
		
		// Create passenger for all the invalid bookings.
		String[] entryList;
		for(String invalidFormatBooking : invalidFormats) {
			entryList = invalidFormatBooking.split(",");
			Booking newBooking = new Booking(entryList[0], entryList[1], entryList[2], entryList[3], Boolean.parseBoolean(entryList[4]));
			
			newPassenger = new Passenger(newBooking);
			passengers.add(newPassenger);
		}
	}
	
	/**
	 * Get a random number of passengers from 0 to 30 (Can change in constructor).
	 * @return An ArrayList of Passengers. Note: could have no passengers.
	 */
	public ArrayList<Passenger> InitialPassengersForQueue() {
		ArrayList<Passenger> initialPassengers = new ArrayList<Passenger>();
		int add = (int) (Math.random() * maxInitialNumber);
		
		while(add != 0 && passengers.size() != 0) {
			initialPassengers.add(RandomlySelectAPassenger());
			add--;
		}
		
		return initialPassengers;
	}
	
	/**
	 * Gives a list of passengers that are waiting to join the queue. 
	 * @return An ArrayList of Passengers.
	 */
	public ArrayList<Passenger> PassengersToJoinTheQueue() {
		return goingToQueue;
	}
	
	/**
	 * Gets a single passenger from the list of passengers.
	 * @return Randomly picked passenger.
	 */
	private Passenger RandomlySelectAPassenger() {
		int index = (int) (Math.random() * passengers.size());
		
		return passengers.remove(index);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		int waitTime = 0;
		int howManyWantToJoin = 0;
		
		while(passengers.size() != 0) {
			// Work out wait time then wait.
			waitTime = simulationSpeed.MatchSimulationSpeed((double)waitTime);
			
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				// TODO What to do with exception.
			}
			
			// Work out randomly how many should join queue, at least 1 to maxJoinAmount. 
			howManyWantToJoin = (int) (Math.random() * (maxJoinAmount - 1)) + 1;
			
			while(howManyWantToJoin > 0 && passengers.size() != 0) {
				goingToQueue.add(RandomlySelectAPassenger());
			}
			
			setChanged();
			notifyObservers();
		}
		
	}
}
