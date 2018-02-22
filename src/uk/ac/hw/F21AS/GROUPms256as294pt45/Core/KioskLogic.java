package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class KioskLogic {
	private String bookingPath, flightPath;
	private BookingLoader bookingLoader;
	private FlightLoader flightLoader;
	private TreeMap<String, Booking> bookings;
	private TreeMap<String, Flight> flights;
	private ArrayList<String> invalidFormatErrors;
	private ErrorLogger errorLogger;
	private Booking currentlyCheckingIn;
	private Flight currentFlightDetails;
	private BaggageDetails currentBagDetails;
	
	public KioskLogic() {
		// File locations.
		bookingPath = "/BookingFile.txt";
		flightPath = "/FlightFile.txt";
		
		// Loaders
		bookingLoader = new BookingLoader(bookingPath);
		flightLoader = new FlightLoader(flightPath);
		
		// Data collections.
		bookings = new TreeMap<String, Booking>();
		flights = new TreeMap<String, Flight>();
		invalidFormatErrors = new ArrayList<String>();
		
		errorLogger = new ErrorLogger();
	}
	
	public void ReadyKiosk() {
		try {
			bookings = bookingLoader.LoadBookings();
			invalidFormatErrors = bookingLoader.GetErrors();
		} catch (NullPointerException e) {
			errorLogger.addUnexpectedError("Booking file triggered an NullPointerException");
		} catch (IOException e) {
			errorLogger.addUnexpectedError("Booking file triggered an IOException");
		}
		
		try {
			flights = flightLoader.LoadFlight();
		} catch (NullPointerException e) {
			errorLogger.addUnexpectedError("Flight file triggered an NullPointerException");
		} catch (IOException e) {
			errorLogger.addUnexpectedError("Flight file triggered an IOException");
		}
	}
	
	public void StartUpKiosk() {
		if(!invalidFormatErrors.isEmpty()) {
			for(String error : invalidFormatErrors) {
				errorLogger.addEntry(error);
			}
		}
		
		KioskSearch kiosk = new KioskSearch(this);
	}
	
	/**
	 * Adds failed check in details to error logger.
	 * @param errorDetails A string with the information that failed to match.
	 */
	public void AddCheckInError(String errorDetails) {
		errorLogger.addError(errorDetails);
	}
	
	/**
	 * Gets booking that matches collected details.
	 * @param reference Booking code for passenger.
	 * @param surname Surname used in booking.
	 * @return True: Match found. False: No booking found.
	 */
	public boolean LocateBooking(String reference, String surname) {
		currentlyCheckingIn = bookings.get(reference);
		if(currentlyCheckingIn != null) {
			if(surname.equals(currentlyCheckingIn.GetSurname())) {
				currentFlightDetails = flights.get(currentlyCheckingIn.GetFlightCode());
				return true;
			}
		}
		
		currentFlightDetails = null;
		return false;
	}
	
	/**
	 * If a booking has been matched to details given, this will give you the booking.
	 * @return Booking for current check in process.
	 */
	public Booking CurrentCheckInBooking() {
		return currentlyCheckingIn;
	}
	
	/**
	 * If a booking has been matched to given details, this will give you the flight related to the booking.
	 * @return Flight for current check in process.
	 */
	public Flight CurrentFlightDetails() {
		return currentFlightDetails;
	}
	
	/**
	 * Gives all flights on request.
	 * @return Collection of flights.
	 */
	public TreeMap<String, Flight> GetAllFlights() {
		return flights;
	}
	
	/**
	 * With the baggage information add to booking & flight, update collections of each with updated infromation.
	 * @param booking Updated booking
	 * @param flight Updated flight
	 */
	public void UpdateDetails(Booking booking, Flight flight) {
		bookings.replace(booking.GetBookingReference(), booking);
		flights.replace(flight.FlightCode(), flight);
	}
	
	/**
	 * Holds baggage details to update flight and booking information once payment is received.
	 * @param bag 
	 */
	public void AwaitingPayment(BaggageDetails bag) {
		currentBagDetails = bag;
	}
	
	/**
	 * Amount to be paid according to current baggage.
	 * @return Fee amount to be paid.
	 */
	public double PaymentAmount() {
		return currentBagDetails.Fee();
	}
	
	/**
	 * Checks in a passenger after they have paid baggage fees.
	 */
	public void PaymentRecieved() {
		currentlyCheckingIn.SetBaggageInfo(currentBagDetails);
		currentlyCheckingIn.CheckIn();
		
		currentFlightDetails.AddToWeight(currentBagDetails.Weight());
		currentFlightDetails.AddToVolume(currentBagDetails.Volume());
		currentFlightDetails.AddToFees(currentBagDetails.Fee());
		
		UpdateDetails(currentlyCheckingIn, currentFlightDetails);
		currentBagDetails = null;
	}
	
	/**
	 * Passenger has cancelled payment, maybe to reduce it by removing weight.
	 */
	public void PaymentCancelled() {
		currentBagDetails = null;
	}
}
