/**
 * 
 */
package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.util.ArrayList;
import java.util.Observable;

/**
 * @author mehdi seddiq (ms256)
 *
 */
public class PassengerQueue extends Observable {
	public boolean isEmpty;
	public int NumPassengersInQueque;
	public ArrayList<Passenger> list;
	/**
	 * 
	 */
	public PassengerQueue() {
		// TODO Auto-generated constructor stub
		isEmpty=true;
		NumPassengersInQueque=0;
		list=null;
	}

	public void setChanges(){
		NumPassengersInQueque= list.size();
		if (NumPassengersInQueque==0){
			isEmpty=true;
		}
	}
}
