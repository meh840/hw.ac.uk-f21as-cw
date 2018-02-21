package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Junit;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Flight;

public class FlightTester {
	private Flight flight;
	
	@Before
	public void setUp() {
		flight = new Flight("FLN001", "Heathrow", "Heriot Airlines", 35, 10.0, 10.0);
	}

	@Test
	public void testCreationOfFlightClass() {
		assertEquals("Flight constructor check", "FLN001,Heathrow,Heriot Airlines,35,10.0,10.0", flight.toString());
	}

	@Test
	public void testGettingFlightCode() {
		assertEquals("Flight code should be FLN001", "FLN001", flight.FlightCode());
	}

	@Test
	public void testGettingTheDestinationAirport() {
		assertEquals("Destination should be Heathrow", "Heathrow", flight.Destination());
	}

	@Test
	public void testGettingTheCarrier() {
		assertEquals("Carrier should be Heriot Airlines", "Heriot Airlines", flight.Carrier());
	}

	@Test 
	public void testTotalWeightStartsAtZero() {
		assertEquals("Total weight should be 0.0 after creation.", 0.0, flight.TotalWeight(), 0);
	}

	@Test 
	public void testTotalWeightChanges() {
		flight.AddToWeight(2.0);
		assertEquals("Total weight should change to 2.0", 2.0, flight.TotalWeight(), 0);
	}

	@Test 
	public void testTotalVolumeStartsAtZero() {
		assertEquals("Total volume should start at 0.0", 0.0, flight.TotalVolume(), 0);
	}

	@Test 
	public void testTotalVolumeChanges() {
		flight.AddToVolume(3.0);
		assertEquals("Total volume should change to 3.0", 3.0, flight.TotalVolume(), 0);
	}

	@Test 
	public void testFeesPaidStartsAtZero() {
		assertEquals("Fees should start at 0.0", 0.0, flight.TotalFeesPaid(), 0);
	}

	@Test 
	public void testFeesPaidChanges() {
		flight.AddToFees(4.0);
		assertEquals("Fees should change to 4.0", 4.0, flight.TotalFeesPaid(), 0);
	}
	
	@Test
	public void testVolumeAcceptableWorksCorrectlyOnCreation() {
		assertEquals("On creation, volume is acceptable", true, flight.VolumeAcceptable());
	}
	
	@Test
	public void testVolumeAcceptableWorksCorrectlyAtLimit() {
		flight.AddToVolume(10.0);
		assertEquals("At limit, volume is acceptable", true, flight.VolumeAcceptable());
	}
	
	@Test
	public void testVolumeAcceptableWorksCorrectlyOverLimit() {
		flight.AddToVolume(10.1);
		assertEquals("Over limit, volume is not acceptable", false, flight.VolumeAcceptable());
	}
	
	@Test
	public void testWeightAcceptableWorksCorrectlyOnCreation() {
		assertEquals("On creation, weight is acceptable", true, flight.WeightAcceptable());
	}
	
	@Test
	public void testWeightAcceptableWorksCorrectlyAtLimit() {
		flight.AddToWeight(10.0);
		assertEquals("At limit, weight is acceptable", true, flight.WeightAcceptable());
	}
	
	@Test
	public void testWeightAcceptableWorksCorrectlyOverLimit() {
		flight.AddToWeight(10.1);
		assertEquals("Over limit, weight is not acceptable", false, flight.WeightAcceptable());
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
		
		assertEquals("FLN000 is before FLN001", 1, flight.compareTo(preFlight));
	}
	
	@Test
	public void testCompareFlightWithSameFlight() {
		assertEquals("Knows FLN001 = FLN001", 0, flight.compareTo(flight));
	}
	
	@Test
	public void testCompareFlightWithHigherNumber() {
		Flight followingFlight = new Flight("FLN004", "Heathrow", "Heriot Airlines", 100, 150.0, 150.0);
		
		assertEquals("FLN004 is after FLN001", -3, flight.compareTo(followingFlight));
	}
}
