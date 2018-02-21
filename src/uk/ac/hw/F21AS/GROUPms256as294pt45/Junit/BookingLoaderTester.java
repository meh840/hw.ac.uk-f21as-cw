package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Junit;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.junit.Test;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Booking;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.BookingLoader;

public class BookingLoaderTester {
	BookingLoader loader;
	TreeMap<String, Booking> bookings;
	
	@Test
	public void testRadingAnEmptyFile() {
		loader = new BookingLoader("/EmptyFile.txt");
		
		try {
			bookings = loader.LoadBookings();
		} catch (NullPointerException ex) {
			fail("NullPointerException on booking file loading");
		} catch (IOException e) {
			fail("IOException on booking file loading");
		}
		assertEquals("Collection should be empty", true, bookings.isEmpty());
		
		String errorExpected = ErrorString(loader.GetErrors());
		assertEquals("There should be no errors", "", errorExpected);
	}
	
	@Test
	public void testReadingAFileWithOneEntry() {
		loader = new BookingLoader("/BookingTestFileE1.txt");
		Booking toCheck;
		String checking;
		
		try {
			bookings = loader.LoadBookings();
		} catch (NullPointerException ex) {
			fail("NullPointerException on booking file loading");
		} catch (IOException e) {
			fail("IOException on booking file loading");
		}
		
		for(Entry<String, Booking> entry : bookings.entrySet()) {
			toCheck = entry.getValue();
			checking = "" + toCheck.GetBookingReference() + "," + toCheck.GetFlightCode() + "," + toCheck.GetFirstName() + "," + toCheck.GetSurname() + "," + toCheck.GetCheckInStatus();
			
			assertEquals("Booking Loader should have one entry", "AA00AA0,FLN001,Alan,Spence,false", checking);
		}
		
		String errorExpected = ErrorString(loader.GetErrors());
		assertEquals("There should be no errors", "", errorExpected);
	}
	
	@Test
	public void testReadingAFileWithMoreThanOneEntry() {
		loader = new BookingLoader("/BookingTestFileE2.txt");
		String checking;
		
		try {
			bookings = loader.LoadBookings();
		} catch (NullPointerException ex) {
			fail("NullPointerException on booking file loading");
		} catch (IOException e) {
			fail("IOException on booking file loading");
		}
		Booking booking = bookings.get("AA00AA0");
		checking = "" + booking.GetBookingReference() + "," + booking.GetFlightCode() + "," + booking.GetFirstName() + "," + booking.GetSurname() + "," + booking.GetCheckInStatus();
		
		assertEquals("Should be first of two entries", "AA00AA0,FLN001,Alan,Spence,false", checking);

		booking = bookings.get("AA00AA1");
		checking = "" + booking.GetBookingReference() + "," + booking.GetFlightCode() + "," + booking.GetFirstName() + "," + booking.GetSurname() + "," + booking.GetCheckInStatus();
		
		assertEquals("Should be second of two entries", "AA00AA1,FLN001,John,Smith,false", checking);
		
		String errorExpected = ErrorString(loader.GetErrors());
		assertEquals("There should be no errors", "", errorExpected);
	}
	
	@Test
	public void testReadingAFileWithAnIncorrectFormatEntry() {
		loader = new BookingLoader("/BookingTestFileE3.txt");
		String checking;
		
		try {
			bookings = loader.LoadBookings();
		} catch (NullPointerException ex) {
			fail("NullPointerException on booking file loading");
		} catch (IOException e) {
			fail("IOException on booking file loading");
		}
		Booking booking = bookings.remove("AA00AA0");
		checking = "" + booking.GetBookingReference() + "," + booking.GetFlightCode() + "," + booking.GetFirstName() + "," + booking.GetSurname() + "," + booking.GetCheckInStatus();
		
		assertEquals("Should be first of two entries", "AA00AA0,FLN001,Alan,Spence,false", checking);

		booking = bookings.remove("AA00AA1");
		checking = "" + booking.GetBookingReference() + "," + booking.GetFlightCode() + "," + booking.GetFirstName() + "," + booking.GetSurname() + "," + booking.GetCheckInStatus();
		
		assertEquals("Should be second of two entries", "AA00AA1,FLN001,John,Smith,false", checking);
		
		assertEquals("Collection should now be empty", true, bookings.isEmpty());
		
		String errorExpected = ErrorString(loader.GetErrors());
		assertEquals("Should have an error entry", "A142AA1,FLN001,Jane,Smith,false", errorExpected);
	}
	
	private String ErrorString(ArrayList<String> errorList) {
		if(!errorList.isEmpty()) {
			String errorString = "";
			
			for(String error : errorList) {
				errorString += error;
			}
			
			return errorString;
		}
		
		return "";
	}
}
