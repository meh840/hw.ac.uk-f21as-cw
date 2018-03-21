package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Flight;

/**
 * @author Alan Spence (as294)
 *
 */
public class Plane {
	private Flight flightDetails;
	private String flightCode;
	private int capacity;
	private String departureTime;
	private SimulationClock clock;
	private ArrayList<String> boarded;
	
	public Plane(String flightCode, Flight flightDetails, int planeCapacity) {
		this.flightCode = flightCode;
		this.flightDetails = flightDetails;
		capacity = planeCapacity;

		// Get an instance of the simulation clock and use capacity to calculate departure time.
		clock = SimulationClock.GetInstance();
		departureTime = clock.AddDepartureTime(capacity);
	}
	
	/**
	 * Gives the plane's flight code.
	 * @return Flight code (String).
	 */
	public String FlightCode() {
		return flightCode;
	}
	
	/**
	 * A passenger has checked in, have them board flight.
	 * @param Passenger The passenger boarding (toString).
	 */
	public void BoardFlight(String Passenger) {
		boarded.add(Passenger);
	}
	
	/**
	 * Gives the total of passengers that have boarded the plane.
	 * @return Number of passengers currently on the plane.
	 */
	public int TotalOfPassengersThatHaveBoarded() {
		return boarded.size();
	}
	
	/**
	 * Checks if the departure time has been reached or even exceeded.
	 * @return True: Not yet departed. False: Departure has been.
	 */
	public boolean StillAvailableForBoarding() {
		return !clock.DepartureCheck(departureTime);
	}
	
	/**
	 * The plane is departing, give it the final details.
	 * @param flightDetails Details accumulated from Kiosks. 
	 */
	public void PlaneDeparts(Flight flightDetails) {
		this.flightDetails = flightDetails;
	}
	
	/**
	 * Generates the summary of the departed plane.
	 * @return String containing the flight details and the passengers that boarded.
	 */
	public String DepartureDetails() {
		String departure = "";
		
		// Summary string of flight.
		//TODO: Add to Flight class a summary method for recording information of departure.
		
		// Add each passenger.
		for (String passenger : boarded) {
			departure += passenger + "\n";
		}
		
		return departure;
	}
}
