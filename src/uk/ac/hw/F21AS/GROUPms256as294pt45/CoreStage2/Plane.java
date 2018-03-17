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
	private SimpleDateFormat departureTime;
	private ArrayList<String> boarded;
	
	public Plane(String flightCode, int planeCapacity) {
		this.flightCode = flightCode;
		capacity = planeCapacity;
		// TODO: Set up departure time.
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
	public int TotalOfPassengersBoarded() {
		return boarded.size();
	}
	
	/**
	 * The plane is departing, give it the final details.
	 * @param flightDetails Details accumulated from Kiosks. 
	 */
	public void PlaneDeparts(Flight flightDetails) {
		this.flightDetails = flightDetails;
	}
}
