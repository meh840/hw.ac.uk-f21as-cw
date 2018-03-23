package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Passenger queue class, notifies when passenger enters queue and when a passenger goes 
 * to a kiosk. Has booleans to indicate which has happened.
 * @author Mehdi Seddiq (ms256) & Alan Spence (as294)
 *
 */
public class PassengerQueue extends Observable {
	private boolean queueDisplayChange, passengerJoinsQueue;
	private ArrayList<Passenger> queue;
	
	/**
	 * 
	 */
	public PassengerQueue() {
		queue = new ArrayList<Passenger>();
		queueDisplayChange = false;
		passengerJoinsQueue = false;
	}
	
	public boolean IsQueueEmpty() {
		return queue.isEmpty();
	}
	
	public synchronized void PassengerJoiningQueue(Passenger newPassenger) {
		queue.add(newPassenger);
		passengerJoinsQueue = true;
		
		// If adding to an empty or small queue, notify new display details for GUI.
		if(queue.size() < 11) {
			queueDisplayChange = true;
		}
		
		setChanged();
		notifyObservers();
	}
	
	public boolean HasAPassengerJoinedTheQueue() {
		return passengerJoinsQueue;
	}
	
	public int SizeOfQueue() {
		return queue.size();
	}
	
	public void ResetPassengerJoinedIndicator() {
		passengerJoinsQueue = false;
	}
	
	public synchronized Passenger HeadToKiosk() {
		queueDisplayChange = true;

		setChanged();
		notifyObservers();
		
		return queue.remove(0);
	}
	
	public boolean HasChangeToQueueDisplayInfoBeenMade() {
		return queueDisplayChange;
	}
	
	public Object[][] HeadOfTheQueue() {
		int numberOfPassngers = queue.size();
		
		// We only want to display up to first 10 in queue.
		if(numberOfPassngers > 10) {
			numberOfPassngers = 10;
		}
		
		Object[][] headOfQueue = new Object[numberOfPassngers][3];
		Passenger gettingInfoFrom;
		Object[] info;
		
		// Go through getting information from passengers.
		for(int i = 0; i < numberOfPassngers; i++) {
			gettingInfoFrom = queue.get(i);
			info = gettingInfoFrom.QueueDisplayInformation();
			
			headOfQueue[i][0] = info[0];
			headOfQueue[i][1] = info[1];
			headOfQueue[i][2] = info[2];
		}
		
		return headOfQueue;
	}
	
	public void ResetChangeToQueueDisplayIndicator() {
		queueDisplayChange = false;
	}
}
