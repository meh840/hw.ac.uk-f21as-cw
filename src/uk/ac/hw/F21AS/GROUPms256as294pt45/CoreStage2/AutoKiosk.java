/**
 * 
 */
package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.util.Observable;


/**Simulates the AutoKiosks
 * @author mehdi seddiq (ms256)
 *
 */
public class AutoKiosk extends Observable implements Runnable {
	private KioskStatus kioskStatus;
	private boolean checkinRunning;
	private boolean queueEmpty;
	private boolean entryIsValid; //showing the validity of booking reference and surname
	private boolean useManned;
	private int pauseForPayment;
	private int pauseForEntryCheck;	
	private double fee;
	RuntimeSpeedController speedController;
	private final int maxPauseForPayment=150;
	private Passenger passenger;
	private int kioskNumber;
 
	/**
	 * Constructor for AutoKiosk
	 * @param
	 */
	public AutoKiosk(){
		checkinRunning=true;
		queueEmpty=true;
		entryIsValid=false;
		useManned=true;
		kioskStatus=KioskStatus.WAIT_FOR_PASSENGER;
		pauseForPayment = 0;
		pauseForEntryCheck=0;	
		fee=0;
		kioskNumber=0;
		
	}
	
	/** (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run(){
		while (checkinRunning){
			while (queueEmpty) {
				kioskStatus=KioskStatus.WAIT_FOR_PASSENGER;
				try { wait(); }
				catch (InterruptedException e) {}
			}
			kioskStatus=KioskStatus.GET_PASSENGER;
			setChanged();
			notifyObservers();
			entryIsValid=false;
			do{ 
				pauseForEntryCheck=speedController.TimeToEnterDetails();
				try {
					Thread.sleep(pauseForEntryCheck);
				} catch (InterruptedException e) {
					//System.out.println(e.getMessage());
					e.printStackTrace();
				}
				kioskStatus=KioskStatus.CHECK_ENTRIES;
				setChanged();
				notifyObservers();
			}while (!useManned && !entryIsValid);
			if (useManned){
				kioskStatus=KioskStatus.SEND_TO_MANNED_KIOSK;
				setChanged();
				notifyObservers();
			}
			else{ //entryIsValid
				kioskStatus=KioskStatus.GET_BAGGAGE;
				setChanged();
				notifyObservers();
				if (fee!=0){
					pauseForPayment=speedController.RandomWaitTime(maxPauseForPayment);
					try {
						Thread.sleep(pauseForPayment);//pause due to payment process 
					} catch (InterruptedException e) {
						//System.out.println(e.getMessage());
						e.printStackTrace();
					}
				}
				kioskStatus=KioskStatus.SEND_TO_PLANE;
				setChanged();
				notifyObservers();
			}
				
		}//while (checkinRunning)
	}// run

	public void SetPassenger(Passenger givenPassenger){
		passenger=givenPassenger; 
	}

	public Passenger GetPassenger(){
		return passenger; 
	}
	
	public void SetCheckinRunning(boolean givenCheckinRunning){
		this.checkinRunning=givenCheckinRunning;
	}
	
	public void SetEntryIsValid(boolean givenEntryIsValid){
		entryIsValid=givenEntryIsValid;
	}
	
	public void SetUseManned(boolean givenUseManned){
		useManned=givenUseManned;
	}
	
	public void SetFee (double givenFee){
		fee=givenFee;
	}
	
	public KioskStatus GetKioskStatus(){
		return kioskStatus;
	}
	
	public int GetKioskNumber(){
		return kioskNumber;
	}
	
	public void SetKioskNumber(int givenKioskNumber){
		kioskNumber=givenKioskNumber;
	}
	
}


