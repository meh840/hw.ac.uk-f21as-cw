package uk.ac.hw.F21AS.GROUPms256as294pt45.Junit;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Flight;

class FlightTester {
	private Flight flight;
	
	@BeforeEach
	public void setUp() {
		flight = new Flight("FLN001", "Heathrow", "Heriot Airlines", 35, 10.0, 10.0);
	}

	@Test
	public void testCreationOfFlightClass() {
		assertEquals("Flight constructor check", "FLN001,Heathrow,Heriot Airlines,35,10.0,10.0", flight.toString());
	}

	@Test 
	public void testTotalWeightStartsAtZero() {
		assertEquals(0.0, flight.TotalWeight(), "Total weight starts at 0.0");
	}

	@Test 
	public void testTotalWeightChanges() {
		flight.AddToWeight(2.0);
		assertEquals(2.0, flight.TotalWeight(), "Total weight changes to 2.0");
	}

	@Test 
	public void testTotalVolumeStartsAtZero() {
		assertEquals(0.0, flight.TotalVolume(), "Total volume starts at 0.0");
	}

	@Test 
	public void testTotalVolumeChanges() {
		flight.AddToVolume(3.0);
		assertEquals(3.0, flight.TotalVolume(), "Total volume changes to 3.0");
	}

	@Test 
	public void testFeesPaidStartsAtZero() {
		assertEquals(0.0, flight.TotalFeesPaid(), "Fees starts at 0.0");
	}

	@Test 
	public void testFeesPaidChanges() {
		flight.AddToFees(4.0);
		assertEquals(4.0, flight.TotalFeesPaid(), "Fees changes to 4.0");
	}
	
	@Test
	public void testVolumeAcceptableWorksCorrectlyOnCreation() {
		assertEquals(true, flight.VolumeAcceptable(), "On creation, volume is acceptable");
	}
	
	@Test
	public void testVolumeAcceptableWorksCorrectlyAtLimit() {
		flight.AddToVolume(10.0);
		assertEquals(true, flight.VolumeAcceptable(), "At limit, volume is acceptable");
	}
	
	@Test
	public void testVolumeAcceptableWorksCorrectlyOverLimit() {
		flight.AddToVolume(10.1);
		assertEquals(false, flight.VolumeAcceptable(), "Over limit, volume is not acceptable");
	}
	
	@Test
	public void testWeightAcceptableWorksCorrectlyOnCreation() {
		assertEquals(true, flight.WeightAcceptable(), "On creation, weight is acceptable");
	}
	
	@Test
	public void testWeightAcceptableWorksCorrectlyAtLimit() {
		flight.AddToWeight(10.0);
		assertEquals(true, flight.WeightAcceptable(), "At limit, weight is acceptable");
	}
	
	@Test
	public void testWeightAcceptableWorksCorrectlyOverLimit() {
		flight.AddToWeight(10.1);
		assertEquals(false, flight.WeightAcceptable(), "Over limit, weight is not acceptable");
	}

	@Test
	public void testSummaryStringFormat() {
		flight.AddToFees(5.0);
		flight.AddToVolume(4.0);
		flight.AddToWeight(2.5);
		
		assertEquals("Flight summary check", "FLN001,Heathrow,Heriot Airlines,35,10.0,2.5,10.0,4.0,5.0", flight.SummaryString());
	}
	
	@Test
	public void testCompareFlightWithLowerNumber() {
		Flight preFlight = new Flight("FLN000", "Heathrow", "Heriot Airlines", 50, 50.0, 50.0);
		
		assertEquals(1, flight.compareTo(preFlight), "FLN000 is before FLN001");
	}
	
	@Test
	public void testCompareFlightWithSameFlight() {
		assertEquals(0, flight.compareTo(flight), "Knows FLN001 = FLN001");
	}
	
	@Test
	public void testCompareFlightWithHigherNumber() {
		Flight followingFlight = new Flight("FLN004", "Heathrow", "Heriot Airlines", 100, 150.0, 150.0);
		
		assertEquals(-3, flight.compareTo(followingFlight), "FLN004 is after FLN001");
	}
}
