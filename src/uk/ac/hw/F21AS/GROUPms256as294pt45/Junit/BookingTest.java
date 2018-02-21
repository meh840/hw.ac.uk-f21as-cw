package uk.ac.hw.F21AS.GROUPms256as294pt45.JUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.hw.F21AS.GROUPms256as294pt45.Core.BaggageDetails;
import uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Booking;
import uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Flight;

public class BookingTest {

	@BeforeEach
	public void setUp() {
		baggageInfo1= new BaggageDetails(10.0, 1.0, 0.8, 0.4); 
		baggageInfo2= new BaggageDetails(0.0, 0.0, 0.0, 0.0);
		// bookingReference, flightCode, firstName, surname, checkedIn, bagInfo 
		booking1 = new Booking("VT48QB7", "FLU999", "Dieter", "Muller", false, baggageInfo1);
		Booking booking2=booking1;
		booking2.SetBaggageInfo(baggageInfo2);
		booking3 = new Booking("VT48QB0", "FLU999", "Dieter", "Muller", false, baggageInfo1);
	}
	
	@Test
	/**
	 * Tests Construction of the inputs
	 */
	public void testBooking() {
		String error1 = "Failed \n";
		//assertEquals(error1, );		
	}

	@Test
	public void testGetBookingReference() {
		String error1 = "GetBookingReference Failed \n";
		String expected="VT48QB7";
		String actual=booking1.GetBookingReference();
		assertEquals(error1, expected, actual);
	}

	@Test
	public void testGetFlightCode() {
		String error1 = "GetFlightCode Failed \n";
		String expected="FLU999";
		String actual=booking1.GetFlightCode();
		assertEquals(error1, expected, actual);
	}

	@Test
	public void testGetFirstName() {
		String error1 = "GetFirstName Failed \n";
		String expected="Dieter";
		String actual=booking1.GetFirstName();
		assertEquals(error1, expected, actual);
	}

	@Test
	public void testGetSurname() {
		String error1 = "GetSurname Failed \n";
		String expected="Muller";
		String actual=booking1.GetSurname();
		assertEquals(error1, expected, actual);
	}

	@Test
	public void testGetCheckInStatus() {
		String error1 = "GetCheckInStatus Failed \n";
		String expected=false;
		String actual=booking1.GetCheckInStatus();
		assertEquals(error1, expected, actual);
	}

	@Test
	public void testGetBaggageInfo() {
		String error1 = "GetBaggageInfo Failed \n";
		String expected=baggageInfo1;
		String actual=booking1.GetBaggageInfo();
		assertEquals(error1, expected, actual);
	}

	@Test
	public void testCompareTo() {
		String error1 = "compareTo Failed to distinguish different BookingReferences\n";
		String BookRef1="VT48QB7";
		expected1=1;
		String actual1=booking1.compareTo(booking3);
		assertEquals(error1, expected1, actual1);
		
		String error2 = "compareTo Failed to acknowledge similar BookingReferences\n";
		String expected2=0;
		String actual2=booking1.compareTo(booking2);
		assertEquals(error2, expected2, actual2);
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}