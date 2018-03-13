/**
 * 
 */
package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.util.ArrayList;

/** main class
 * @author mehdi seddiq (ms256)
 *
 */
public class AirportCheckin {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StageSelectionFrame GU1 = new StageSelectionFrame(); 
		ArrayList<Passenger> passengerQueue=null; //temporarily
		Kiosk kiosk1, kiosk2;
		kiosk1=null; kiosk2=null;//temporarily
		DataModel model = new DataModel(passengerQueue, kiosk1, kiosk2);
		AutomatedFrame view = new AutomatedFrame(model);
		CheckinController controller = new CheckinController(model, view);
		controller.LoadFiles();
		controller.StartCheckin();
	}

}
