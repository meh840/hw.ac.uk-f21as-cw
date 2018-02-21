package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Junit;

import static org.junit.Assert.*;

import org.junit.Test;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.BookingReferenceChecker;

public class BookingReferenceCheckerTester {
	private String correctFormat = "AA00AA0";
	private String incorrectFormat1 = "0A00AA0";
	private String incorrectFormat2 = "A000AA0";
	private String incorrectFormat3 = "AAA0AA0";
	private String incorrectFormat4 = "AA0AAA0";
	private String incorrectFormat5 = "AA000A0";
	private String incorrectFormat6 = "AA00A00";
	private String incorrectFormat7 = "AA00AAA";
	private String incorrectFormat8 = ">A00AA0";
	private String incorrectFormat9 = "A>00AA0";
	private String incorrectFormat10 = "AA>0AA0";
	private String incorrectFormat11 = "AA0>AA0";
	private String incorrectFormat12 = "AA00>A0";
	private String incorrectFormat13 = "AA00A>0";
	private String incorrectFormat14 = "AA00AA>";

	@Test
	public void testCanDetectCorrectFormat() {
		assertEquals("Should state AA00AA0 is in correct format", true, BookingReferenceChecker.CheckABookingReference(correctFormat));
	}

	@Test
	public void testCanDetectIncorrectFormatWithFirstCharWrong() {
		assertEquals("Should state 0A00AA0 is an incorrect format", false, BookingReferenceChecker.CheckABookingReference(incorrectFormat1));
	}

	@Test
	public void testCanDetectIncorrectFormatWithSecondCharWrong() {
		assertEquals("Should state A000AA0 is an incorrect format", false, BookingReferenceChecker.CheckABookingReference(incorrectFormat2));
	}

	@Test
	public void testCanDetectIncorrectFormatWithThirdCharWrong() {
		assertEquals("Should state AAA0AA0 is an incorrect format", false, BookingReferenceChecker.CheckABookingReference(incorrectFormat3));
	}

	@Test
	public void testCanDetectIncorrectFormatWithFourthCharWrong() {
		assertEquals("Should state AA0AAA0 is an incorrect format", false, BookingReferenceChecker.CheckABookingReference(incorrectFormat4));
	}

	@Test
	public void testCanDetectIncorrectFormatWithFifthCharWrong() {
		assertEquals("Should state AA000A0 is an incorrect format", false, BookingReferenceChecker.CheckABookingReference(incorrectFormat5));
	}

	@Test
	public void testCanDetectIncorrectFormatWithSixthCharWrong() {
		assertEquals("Should state AA00A00 is an incorrect format", false, BookingReferenceChecker.CheckABookingReference(incorrectFormat6));
	}

	@Test
	public void testCanDetectIncorrectFormatWithSeventhCharWrong() {
		assertEquals("Should state AA00AAA is an incorrect format", false, BookingReferenceChecker.CheckABookingReference(incorrectFormat7));
	}

	@Test
	public void testCanDetectIncorrectFormatWithFirstCharAsNonAlphanumeric() {
		assertEquals("Should state >A00AA0 is an incorrect format", false, BookingReferenceChecker.CheckABookingReference(incorrectFormat8));
	}

	@Test
	public void testCanDetectIncorrectFormatWithSecondCharAsNonAlphanumeric() {
		assertEquals("Should state A>00AA0 is an incorrect format", false, BookingReferenceChecker.CheckABookingReference(incorrectFormat9));
	}

	@Test
	public void testCanDetectIncorrectFormatWithhirdrstCharAsNonAlphanumeric() {
		assertEquals("Should state AA>0AA0 is an incorrect format", false, BookingReferenceChecker.CheckABookingReference(incorrectFormat10));
	}

	@Test
	public void testCanDetectIncorrectFormatWithFourthCharAsNonAlphanumeric() {
		assertEquals("Should state AA0>AA0 is an incorrect format", false, BookingReferenceChecker.CheckABookingReference(incorrectFormat11));
	}

	@Test
	public void testCanDetectIncorrectFormatWithFifthtCharAsNonAlphanumeric() {
		assertEquals("Should state AA00>A0 is an incorrect format", false, BookingReferenceChecker.CheckABookingReference(incorrectFormat12));
	}

	@Test
	public void testCanDetectIncorrectFormatWithSixthCharAsNonAlphanumeric() {
		assertEquals("Should state AA00A>0 is an incorrect format", false, BookingReferenceChecker.CheckABookingReference(incorrectFormat13));
	}

	@Test
	public void testCanDetectIncorrectFormatWithSeventhCharAsNonAlphanumeric() {
		assertEquals("Should state AA00AA> is an incorrect format", false, BookingReferenceChecker.CheckABookingReference(incorrectFormat14));
	}
}
