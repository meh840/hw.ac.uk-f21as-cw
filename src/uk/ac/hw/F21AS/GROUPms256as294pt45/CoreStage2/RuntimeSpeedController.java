package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

/**
 * Used to keep track of the running speed rate of the simulation. 
 * This can give a random wait time or convert a given wait time into 
 * milliseconds and factored into the simulation speed.
 * @author Alan Spence (as294)
 *
 */
public final class RuntimeSpeedController {
	private static int speedSetting = 1;
	private static int maxTimeToEnterDetails = 10;
	
	/**
	 * Change the speed the simulation is going at.
	 * @param speed Rate of simulation. 2: double speed, 4: four times the speed and 
	 * all other entries set the speed to normal rate.
	 */
	public static void SetRuntimeSpeed(int speed) {
		if(speed == 2 || speed == 4) {
			speedSetting = speed;
		} else {
			speedSetting = 1;
		}
	}
	
	/**
	 * Get a random wait time between 0 and maxTime.
	 * @param maxTime Maximum value the wait time can be.
	 * @return A wait time in milliseconds.
	 */
	public static int RandomWaitTime(int maxTime) {
		return (int) (Math.random() * maxTime / speedSetting) * 1000;
	}
	
	/**
	 * Makes a wait time match the speed of the simulation.
	 * @param waitTime Time to be converted.
	 * @return Wait time now in milliseconds.
	 */
	public static int MatchSimulationSpeed(int waitTime) {
		if(speedSetting != 1) {
			waitTime /= speedSetting;
		}
		
		return waitTime * 1000;
	}
	
	/**
	 * Gives a random wait time that can cover a passenger typing in details. 
	 * @return A wait time in milliseconds.
	 */
	public static int TimeToEnterDetails() {
		return (int) (Math.random() * maxTimeToEnterDetails / speedSetting) * 1000; 
	}
}
