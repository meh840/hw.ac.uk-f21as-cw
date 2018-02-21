package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core;

/**
 * A class that can be called to check a booking reference is in the correct format or not.
 * When system is running this class can not be altered, thus no tampering with check method.
 * Also has the benefit of having one location which requires updating if the booking reference 
 * format were to change. 
 * @author Alan Spence (as294)
 *
 */
public final class BookingReferenceChecker {
	/**
	 * Used to check if a booking reference is in the correct format or not.
	 * @param reference The booking reference which is to be checked.
	 * @return True: In correct format. False: Has an incorrect format.
	 */
	public static boolean CheckABookingReference(String reference) {
		if(reference.length() == 7) {
			if(Character.isLetter(reference.charAt(0))) 
				if(Character.isLetter(reference.charAt(1)))
					if(Character.isDigit(reference.charAt(2)))
						if(Character.isDigit(reference.charAt(3)))
							if(Character.isLetter(reference.charAt(4)))
								if(Character.isLetter(reference.charAt(5)))
									if(Character.isDigit(reference.charAt(6)))
										return true;
		}
		
		return false;
	}
}
