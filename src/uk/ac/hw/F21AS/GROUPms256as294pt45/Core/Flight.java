package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core;

/**
 * The class which contains flight information and capacity details for a single flight.
 * Used by kiosk system to total up baggage and paid fee.
 * @author Alan Spence (as294)
 *
 */
public class Flight implements Comparable<Flight>{
	private String flightCode, destinationAirport, carrier;
	private int maxPassengerCapacity;
	private double totalWeight, maxWeight, totalVolume, maxVolume, totalFeeAccumulated;
	
	/**
	 * Constructor of Flight class.
	 * @param flightCode Unique code for a certain flight.
	 * @param destinationAirport Where the flight goes to.
	 * @param carrier Name of company that own plane. 
	 * @param maxPassengerCapacity How many passengers the flight can hold.
	 * @param maxWeight How much weight the baggage can be.
	 * @param maxVolume How much volume the baggage can take up.
	 */
	public Flight(String flightCode, String destinationAirport, String carrier, int maxPassengerCapacity, double maxWeight, double maxVolume) {
		this.flightCode = flightCode;
		this.destinationAirport = destinationAirport;
		this.carrier = carrier;
		this.maxPassengerCapacity = maxPassengerCapacity;
		this.maxWeight = maxWeight;
		this.maxVolume = maxVolume;
		totalWeight = 0.0;
		totalVolume = 0.0;
		totalFeeAccumulated = 0.0;
	}
	
	/**
	 * Requests the flight code for this flight.
	 * @return String which represents this flight.
	 */
	public String FlightCode() {
		return flightCode;
	}
	
	/**
	 * Requests the destination airport for this flight.
	 * @return String which states the flight's destination.
	 */
	public String Destination() {
		return destinationAirport;
	}
	
	/**
	 * Requests the carrier for this flight.
	 * @return String which states the carrier.
	 */
	public String Carrier() {
		return carrier;
	}
	
	/**
	 * How many passengers the flight can accommodate.
	 * @return Maximum number of passengers.
	 */
	public int PassengerCapacity() {
		return maxPassengerCapacity;
	}
	
	/**
	 * Update baggage weight with a passenger's bag weight.
	 * @param weight Weight of passenger's baggage.
	 */
	public void AddToWeight(double weight) {
		totalWeight += weight;
	}
	
	/**
	 * Total weight of passenger baggage.
	 * @return Weight of all passenger's baggage.
	 */
	public double TotalWeight() {
		return totalWeight;
	}
	
	/**
	 * Update baggage volume with the volume of a passenger's baggage.
	 * @param volume Volume of passenger's baggage.
	 */
	public void AddToVolume(double volume) {
		totalVolume += volume;
	}
	
	/**
	 * Total volume taken up by passenger baggage.
	 * @return Volume of all passenger's baggage.
	 */
	public double TotalVolume() {
		return totalVolume;
	}
	
	/**
	 * Update how much additional fees a customer has paid at the kiosk.
	 * @param paidFee Amount of fee paid.
	 */
	public void AddToFees(double paidFee) {
		totalFeeAccumulated += paidFee;
	}
	
	/**
	 * Accumulated fees for this flight at the kiosk.
	 * @return Total amount paid at kiosk.
	 */
	public double TotalFeesPaid() {
		return totalFeeAccumulated;
	}
	
	/**
	 * Check if total weight of passenger baggage is acceptable.
	 * @return true -> Total weight acceptable. false -> Weight over maximum.
	 */
	public boolean WeightAcceptable() {
		return totalWeight <= maxWeight;
	}
	
	/**
	 * Check if total volume of passenger baggage is acceptable.
	 * @return true -> Total volume acceptable. false -> Volume over maximum.
	 */
	public boolean VolumeAcceptable() {
		return totalVolume <= maxVolume;
	}
	
	@Override
	public int compareTo(Flight anotherFlight) {
		return flightCode.compareTo(anotherFlight.FlightCode());
	}

	@Override
	/**
	 * Overrides toString to give original information. 
	 */
	public String toString() {
		return ("" + flightCode + "," + destinationAirport + "," + carrier + "," + maxPassengerCapacity + "," + maxWeight + "," + maxVolume);
	}
	
	/**
	 * Will generate a summary string which can be used to save flight information from check in.
	 * @return Summary information.
	 */
	public String SummaryString() {
		return ("" + flightCode + "," + destinationAirport + "," + carrier + "," + maxPassengerCapacity + "," + maxWeight + "," + totalWeight + "," + maxVolume + "," + totalVolume + "," + totalFeeAccumulated);
	}
}
