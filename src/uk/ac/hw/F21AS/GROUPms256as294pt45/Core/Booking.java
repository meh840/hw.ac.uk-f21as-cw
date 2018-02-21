package uk.ac.hw.F21AS.GROUPms256as294pt45.Core;

public class Booking implements Comparable<Booking>{
	private String bookingReference, flightCode, firstName, surname;
	private boolean checkedIn;
	private BaggageDetails baggageInfo;
	/**
	 Constructor of Booking class.
	*/
	public Booking(String bookingReference, String flightCode, String firstName, String surname) {
		this.bookingReference=bookingReference;
		this.flightCode=flightCode;
		this.firstName=firstName;
		this.surname=surname;
		checkedIn=false;
		baggageInfo=null;
	}
	
	public String GetBookingReference() {
		return bookingReference;
	}
	
	public String GetFlightCode() {
		return flightCode;
	}
	
	public String GetFirstName(){
		return firstName;
	}
	
	public String GetSurname(){
		return surname;
	}
	
	public boolean GetCheckInStatus(){
		return checkedIn;
	}
	
	public void SetBaggageInfo(BaggageDetails baggageInfoNew){
		baggageInfo=baggageInfoNew;
	}
	
	public BaggageDetails GetBaggageInfo(){
		return baggageInfo;
	}
	
	public void CheckIn(){
		checkedIn=true;
	}

	@Override
	/* 
	 * compares the Booking object given in argument with this Bookings based on their BookingReference
	 */
	public int compareTo(Booking anotherBooking) {
		return bookingReference.compareTo(anotherBooking.bookingReference);
	}
	
	@Override
	public String toString() {
		String strCheckedIn=String.valueOf(checkedIn);
		return ("" + bookingReference + "," + flightCode + "," + firstName + "," + surname + "," + strCheckedIn + "," + baggageInfo.weight + "," + baggageInfo.length + "," + baggageInfo.width + "," + baggageInfo.height);
	}
}
	
	