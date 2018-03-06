package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.BaggageDetails;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Booking;

/**
 * @author Alan Spence (as294)
 *
 */
public class Passenger {
	private Booking passengersDetails;
	private Boolean makeSecondAttempt, makeThirdAttempt, alterReference, alterSurname;
	private int attemptCount;
	
	/**
	 * Constructor for Passenger.
	 * @param passengerBooking Initial details to produce passenger with.
	 */
	public Passenger(Booking passengerBooking) {
		passengersDetails = passengerBooking;
		// Create random baggage details for passenger.
		double weight = Math.random() * 40 + 10;
		double length = Math.random() * 160 + 30;
		double width = Math.random() * 160 + 30;
		double height = Math.random() * 160 + 30;
		BaggageDetails baggageDetails = new BaggageDetails(weight, length, width, height);
		
		// Add baggage details to passenger's details.
		passengersDetails.SetBaggageInfo(baggageDetails);
		
		attemptCount = 1;
		
		// Have the passenger make multiple attempts at checking in.
		if(Math.random() > 0.8) {
			makeSecondAttempt = true;
			// 50/50 chance to make a third attempt.
			if(Math.random() > 0.5) {
				makeThirdAttempt = true;
			} 
			// 50/50 chance to alter booking reference.
			if(Math.random() > 0.5) {
				alterReference = true;
				// 50/50 chance to alter surname too.
				if(Math.random() > 0.5) {
					alterSurname = true;
				} 
			} else {
				// Make sure an alteration is made.
				alterSurname = true;
			}
		} else {
			makeSecondAttempt = false;
			makeThirdAttempt = false; 
			alterReference = false;
			alterSurname = false;
		}
	}
	
	/**
	 * Gives check in details for this passenger.
	 * For the simulation, this passenger may get the details wrong and have to retry.
	 * Note: Gets it wrong by missing out last char from reference or surname.
	 * @return An Attempt which holds the check in details for this passenger.
	 */
	public Attempt CheckInDetails() {
		String reference = passengersDetails.GetBookingReference();
		String name = passengersDetails.GetSurname();
		
		// If 3 attempts have been made, request manned kiosk.
		if(attemptCount > 3) {
			return new Attempt(reference, name, true);
		}
		
		// Check if a second attempt is to be made.
		if(makeSecondAttempt) {
			// Check this is detected and done on first attempt.
			if(attemptCount == 1) {
				reference = AlterReferenceCheck(reference);
				name = AlterSurnameCheck(name);
			// Check if a third attempt is to be made and this is the second attempt.
			} else if(makeThirdAttempt && attemptCount == 2) {
				reference = AlterReferenceCheck(reference);
				name = AlterSurnameCheck(name);
			}
		}
		
		attemptCount += 1;
		return new Attempt(reference, name, false);
	}
	
	/**
	 * Checks in booking reference is to be altered for attempt and alters if required.
	 * @param ref Reference to check.
	 * @return Reference entered for check (alter/not altered).
	 */
	private String AlterReferenceCheck(String ref) {
		if(alterReference) {
			ref.substring(0, (ref.length() - 1));
		}
		
		return ref;
	}
	
	/**
	 * Checks in surname is to be altered for attempt and alters if required.
	 * @param name Name to check.
	 * @return Name entered for check (alter/not altered).
	 */
	private String AlterSurnameCheck(String name) {
		if(alterSurname) {
			name.substring(0,(name.length() - 1));
		}
		
		return name;
	}
	
	/**
	 * Gets the passenger's baggage details for completing check in.
	 * @return Passenger's baggage details.
	 */
	public BaggageDetails EnterBaggageDetails() {
		return passengersDetails.GetBaggageInfo();
	}
 }
