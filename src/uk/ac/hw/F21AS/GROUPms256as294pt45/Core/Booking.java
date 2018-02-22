package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core;
/*
 * This class holds the booking information of passenger as well as methods 
 * to process the information.
 * @author Mehdi Seddiq (ms256)  
 */
public class Booking implements Comparable<Booking>{
	private String bookingReference, flightCode; //bookingReference and flightCode follow specified formats
	private String firstName, surname;
	private boolean checkedIn;  // shows whether the passenger has successfully checked-in.
	private BaggageDetails baggageInfo;
	
	/**
	 * Constructor of Booking class.
	 * @param bookingReference: Unique code for a certain booking
	 * @param flightCode: Unique code for a certain flight
	 * @param firstName: First Name of passenger
	 * @param surname: Last Name of passenger
	 * @param checkedIn: 
	 * @param baggageInfo
	*/
	public Booking(String bookingReference, String flightCode, String firstName, String surname, boolean checkedIn) {
		this.bookingReference=bookingReference;
		this.flightCode=flightCode;
		this.firstName=firstName;
		this.surname=surname;
		this.checkedIn=checkedIn;
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
		return bookingReference.compareTo(anotherBooking.GetBookingReference());
	}
	
	@Override
	public String toString() {
		String strCheckedIn=String.valueOf(checkedIn);
		return ("" + bookingReference + "," + flightCode + "," + firstName + "," + surname + "," + strCheckedIn);
	}
	
	public String SummaryString() {
		String strCheckedIn=String.valueOf(checkedIn);
		return ("" + bookingReference + "," + flightCode + "," + firstName + "," + surname + "," + strCheckedIn + "," + baggageInfo.weight + "," + baggageInfo.length + "," + baggageInfo.width + "," + baggageInfo.height);
	}
}
	
	