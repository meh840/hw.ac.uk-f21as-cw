package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Junit;

import java.util.TreeMap;
import java.io.IOException;
import java.util.Map.Entry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Flight;
import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.FlightLoader;

public class FlightLoaderTester {
	FlightLoader loader;
	TreeMap<String, Flight> flights;
	
	@Test
	public void testRadingAnEmptyFile() {
		loader = new FlightLoader("/EmptyFile.txt");
		
		try {
			flights = loader.LoadFlight();
		} catch (NullPointerException ex) {
			fail("NullPointerException on flight file loading");
		} catch (IOException e) {
			fail("IOException on flight file loading");
		}
		assertEquals("Collection should be empty", true, flights.isEmpty());
	}
	
	@Test
	public void testReadingAFileWithOneEntry() {
		loader = new FlightLoader("/FlightTestFileE1.txt");
		
		try {
			flights = loader.LoadFlight();
		} catch (NullPointerException ex) {
			fail("NullPointerException on flight file loading");
		} catch (IOException e) {
			fail("IOException on flight file loading");
		}
		for(Entry<String, Flight> entry : flights.entrySet()) {
			assertEquals("Flight Loader should have one entry", "FLN001,Heathrow,Heriot Airlines,35,10.0,10.0", entry.getValue().toString());
		}
	}
	
	@Test
	public void testReadingAFileWithMoreThanOneEntry() {
		loader = new FlightLoader("/FlightTestFileE2.txt");
		
		try {
			flights = loader.LoadFlight();
		} catch (NullPointerException ex) {
			fail("NullPointerException on flight file loading");
		} catch (IOException e) {
			fail("IOException on flight file loading");
		}
		Flight flight = flights.get("FLN001");
		assertEquals("Should be first of two entries", "FLN001,Heathrow,Heriot Airlines,35,10.0,10.0", flight.toString());

		flight = flights.get("FLN004");
		assertEquals("Should be second of two entries", "FLN004,Heathrow,Heriot Airlines,100,150.0,150.0", flight.toString());
	}
}
