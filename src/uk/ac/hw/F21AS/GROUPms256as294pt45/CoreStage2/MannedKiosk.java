/**
 * 
 */
package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.util.Observable;

/**
 * @author mehdi seddiq (ms256)
 *
 */
public class MannedKiosk extends Observable implements Runnable {
	private int pauseInMannedKiosk;
	private Passenger passenger;
	private KioskStatus kioskStatus;

	/**
	 * 
	 */
	public MannedKiosk() {
		// TODO Auto-generated constructor stub
		pauseInMannedKiosk=0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		

	}

	public void SetPuseInMannedKiosk(int givenPauseInMannedKiosk){
		pauseInMannedKiosk=givenPauseInMannedKiosk;
	}
	
	public void SetPassenger(Passenger givenPassenger){
		passenger=givenPassenger;
	}
}
