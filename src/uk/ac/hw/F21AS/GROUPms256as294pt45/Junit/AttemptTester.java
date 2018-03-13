package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2.Attempt;

public class AttemptTester {
	private Attempt attempt;

	@Before
	public void setUp() {
		attempt = new Attempt("Ref", "Spence", false);
	}
	
	@Test
	public void testCanGetTheBookingReferenceFromAttempt() {
		assertEquals("Booking reference should be 'Ref'", "Ref", attempt.Reference());
	}
	
	@Test
	public void testCanGetTheSurnameFromAttempt() {
		assertEquals("Surname should be 'Spence'", "Spence", attempt.Surname());
	}
	
	@Test
	public void testCheckAttemptForMannedKioskRequest() {
		assertEquals("Attempt should not request a manned kiosk", false, attempt.UseMannedKiosk());
	}
}
