/**
 * 
 */
package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.util.ArrayList;

/**
 * contains objects which are controlled or viewed
 * @author mehdi seddiq (ms256)
 */
public class DataModel {
	private ArrayList<Passenger> passengerQueue;
	private Kiosk kiosk1, kiosk2;
	/**
	 * 
	 */
	public DataModel(ArrayList<Passenger> givenPassengerQueue, Kiosk givenKiosk1, Kiosk givenKiosk2) {
		// TODO Auto-generated constructor stub
		passengerQueue=givenPassengerQueue;
		kiosk1=givenKiosk1;
		kiosk2=givenKiosk2;
	}
	
	 public ArrayList<Passenger> getPassengerQueue() {
		 return passengerQueue;
	 }
	
	 public Kiosk getKiosk1() {
		 return kiosk1;
	 }

	 public Kiosk getKiosk2() {
		 return kiosk2;
	 }

}
