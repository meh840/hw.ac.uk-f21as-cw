/**
 * 
 */
package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.util.Observable;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.BaggageDetails;


/**Simulates the AutoKiosks
 * @author mehdi seddiq (ms256)
 *
 */
public class AutoKiosk extends Observable implements Runnable {
	private KioskStatus kioskStatus;
	private boolean checkinRunning;
	private boolean queueEmpty;
	private boolean entryIsValid; //showing the validity of booking reference and surname
	//private boolean useManned;
	private int pauseForPayment;
	private int pauseForEntryCheck;	
	private double fee;
	private RuntimeSpeedController speedController;
	private final int maxPauseForPayment=150;
	private Passenger passenger;
	private int kioskNumber;
	private Attempt attempt;
	private String bookingRef, surname;
	private BaggageDetails baggageInfo;
 
	/**
	 * Constructor for AutoKiosk
	 * @param
	 */
	public AutoKiosk(){
		checkinRunning=true;
		queueEmpty=true;
		entryIsValid=false;
		//useManned=true;
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
			StayAvailable();
			CheckEntries();
			if (attempt.UseMannedKiosk()){
				kioskStatus=KioskStatus.SEND_TO_MANNED_KIOSK;
				setChanged();
				notifyObservers();
			}
			if (entryIsValid)
				ReceiveBaggage();
				kioskStatus=KioskStatus.GET_BAGGAGE;
				setChanged();
				notifyObservers();
				if (fee!=0){
					Payment();
					kioskStatus=KioskStatus.DO_PAYMENT;
					setChanged();
					notifyObservers();
				}
				kioskStatus=KioskStatus.SEND_TO_PLANE;
				setChanged();
				notifyObservers();
		
		}//while (checkinRunning)
	}// run

	private void StayAvailable(){
		while (queueEmpty) {
			kioskStatus=KioskStatus.WAIT_FOR_PASSENGER;
			try { wait(); }
			catch (InterruptedException e) {}
		}
	}
	
	private void CheckEntries(){
		do{
			pauseForEntryCheck=speedController.TimeToEnterDetails();
			try {
				Thread.sleep(pauseForEntryCheck);
			} catch (InterruptedException e) {
				//System.out.println(e.getMessage());
				e.printStackTrace();
			}
			attempt=passenger.CheckInDetails();
			setChanged();
			notifyObservers();
		}while (!attempt.UseMannedKiosk() && !entryIsValid);
	}
	
	private void ReceiveBaggage(){
		baggageInfo=passenger.EnterBaggageDetails();
		fee=baggageInfo.Fee();
	}
	
	private void Payment(){
		pauseForPayment=speedController.RandomWaitTime(maxPauseForPayment);
		try {
			Thread.sleep(pauseForPayment);//pause due to payment process 
		} catch (InterruptedException e) {
			//System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public BaggageDetails GetBaggageInfo(){
		return baggageInfo;
	}
	

	public void SetPassenger(Passenger givenPassenger){
		passenger=givenPassenger; 
	}

	public Passenger GetPassenger(){
		return passenger; 
	}
	
	public void SetCheckinRunning(boolean givenCheckinRunning){
		this.checkinRunning=givenCheckinRunning;
	}
	
	public void SetBookingRefIsValid(boolean givenEntryIsValid){
		entryIsValid=givenEntryIsValid;
	}
	
	public void SetFee (double givenFee){
		fee=givenFee;
	}
	
	public void SetQueueEmpty(boolean givenQueueEmpty){
		queueEmpty=givenQueueEmpty;
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
	
/*	*//**
	 * verifies if the given string matches the predefined format of booking references	
	 * @param refString is a string which is supposed to be a booking reference
	 * @returns true if the string matches the format, false otherwise
	 *//*
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

	*//**
	 * verifies the given string to be potentially a surname 
	 * @param refString which is supposed to be a surname
	 * @return true if the input is deduced as a surname, false otherwise
	 *//*
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

	private boolean VerifyBookingRef (String refString) {
		if (!CheckBookingRefFormat(refString)){
			kioskEvent= "The given string does not match the format of booking reference";
			return false;
		}
		if (!CheckSurnameFormat(surString)){
			kioskEvent= "The given string could not be a surname";
			return false;			
		}
		if (bookings.get(refString)==null){
			kioskEvent= "The given code is not found in the booking records";
			return false;			
		}
		if (!surString.equals(bookings.get(refString).GetSurname())){
			kioskEvent= "The given surname does not correspond to the recorded booking refernce";
			return false;			
		}
		kioskEvent= "The booking was found in the records successfully";
		return true;
	}	
	*/	
}


