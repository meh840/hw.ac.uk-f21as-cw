package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2.RuntimeSpeedController;

public class RuntimeSpeedControllerTester {
	private RuntimeSpeedController rsc;
	private int maxTimeToEnterDetails = 10000; // 10seconds in milliseconds.
	
	@Before
	public void setup() {
		rsc = new RuntimeSpeedController();
	}

	@Test
	public void testCheckRuntimeSpeedControllerStartWithSpeedSetToNormal() {
		assertEquals("Waiting time given should be same as entered in milliseconds", 1000, rsc.MatchSimulationSpeed(1));
	}

	@Test
	public void testCheckRuntimeSpeedControllerChangeSpeedSetToDouble() {
		rsc.SetRuntimeSpeed(2);
		assertEquals("Waiting time given should be half of entered in milliseconds", 500, rsc.MatchSimulationSpeed(1));
	}

	@Test
	public void testCheckRuntimeSpeedControllerChangeSpeedSetToFourTimesFaster() {
		rsc.SetRuntimeSpeed(4);
		assertEquals("Waiting time given should be quarter of entered in milliseconds", 250, rsc.MatchSimulationSpeed(1));
	}
	
	@Test
	public void testGettingValidRandomTimeToEnterDetails() {
		boolean passesTest = true;
		
		boolean firstTestLot = CheckFiveValuesFromTimeToEnterDetailsAreValidAndIfTheyMatch();
		boolean secondTestLot = CheckFiveValuesFromTimeToEnterDetailsAreValidAndIfTheyMatch();
		boolean thirdTestLot = CheckFiveValuesFromTimeToEnterDetailsAreValidAndIfTheyMatch();
		
		// Should fail if 2 or more fail.
		if(!firstTestLot && !secondTestLot && !thirdTestLot) {
			passesTest = false;
		}
		if(!firstTestLot && !secondTestLot) {
			passesTest = false;
		}
		if(!firstTestLot && !thirdTestLot) {
			passesTest = false;
		}
		if(!secondTestLot && !thirdTestLot) {
			passesTest = false;
		}
		
		assertEquals("Should not match enough to fail.", true, passesTest);		
	}
	
	@Test
	public void testGettingRandomWaitTime() {
		int maxTime = 1;
		int maxTimeInMilliseconds = maxTime * 1000;
		boolean passesTest = true;
		
		boolean firstTestLot = CheckFiveValuesFromRandomWaitTimeAreValidAndIfTheyMatch(maxTime, maxTimeInMilliseconds);
		boolean secondTestLot = CheckFiveValuesFromRandomWaitTimeAreValidAndIfTheyMatch(maxTime, maxTimeInMilliseconds);
		boolean thirdTestLot = CheckFiveValuesFromRandomWaitTimeAreValidAndIfTheyMatch(maxTime, maxTimeInMilliseconds);
		
		// Should fail if 2 or more fail.
		if(!firstTestLot && !secondTestLot && !thirdTestLot) {
			passesTest = false;
		}
		if(!firstTestLot && !secondTestLot) {
			passesTest = false;
		}
		if(!firstTestLot && !thirdTestLot) {
			passesTest = false;
		}
		if(!secondTestLot && !thirdTestLot) {
			passesTest = false;
		}
		
		assertEquals("Should not match enough to fail.", true, passesTest);
	}
	
	private boolean CheckValueIsUnderMax(int testValue, int maxValue) {
		if(testValue <= maxValue) {
			return true;
		}
		
		return false;
	}
	
	private boolean CheckFiveValuesFromTimeToEnterDetailsAreValidAndIfTheyMatch() {
		boolean passesTest = true;
		
		int test1 = rsc.TimeToEnterDetails();
		int test2 = rsc.TimeToEnterDetails();
		int test3 = rsc.TimeToEnterDetails();
		int test4 = rsc.TimeToEnterDetails();
		int test5 = rsc.TimeToEnterDetails();
		
		passesTest = CheckValueIsUnderMax(test1, maxTimeToEnterDetails);
		passesTest = CheckValueIsUnderMax(test2, maxTimeToEnterDetails);
		passesTest = CheckValueIsUnderMax(test3, maxTimeToEnterDetails);
		passesTest = CheckValueIsUnderMax(test4, maxTimeToEnterDetails);
		passesTest = CheckValueIsUnderMax(test5, maxTimeToEnterDetails);
		
		if(test1 == test2 && test2 == test3 && test3 == test4 && test4 == test5) {
			passesTest = false;
		}
		
		return passesTest;
	}
	
	private boolean CheckFiveValuesFromRandomWaitTimeAreValidAndIfTheyMatch(int testValue, int maxTime) {
		boolean passesTest = true;
		
		int test1 = rsc.RandomWaitTime(testValue);
		int test2 = rsc.RandomWaitTime(testValue);
		int test3 = rsc.RandomWaitTime(testValue);
		int test4 = rsc.RandomWaitTime(testValue);
		int test5 = rsc.RandomWaitTime(testValue);
		
		passesTest = CheckValueIsUnderMax(test1, maxTime);
		passesTest = CheckValueIsUnderMax(test2, maxTime);
		passesTest = CheckValueIsUnderMax(test3, maxTime);
		passesTest = CheckValueIsUnderMax(test4, maxTime);
		passesTest = CheckValueIsUnderMax(test5, maxTime);
		
		if(test1 == test2 && test2 == test3 && test3 == test4 && test4 == test5) {
			passesTest = false;
		}
		
		return passesTest;
	}
}
