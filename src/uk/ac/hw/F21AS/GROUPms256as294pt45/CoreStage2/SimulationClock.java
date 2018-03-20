package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.util.ArrayList;
import java.util.Observable;

/**
 * As the simulation can run at different speeds and we have departure times for planes. 
 * This clock will start at 08:00 and keep time according to the RuntimeSpeedController 
 * speed setting. Additionally, can add departure times and will notify you of a flight 
 * is due to depart.
 * @author Alan Spence (as294)
 *
 */
public final class SimulationClock extends Observable implements Runnable {
	private int hours = 8;
	private int minutes = 0;
	private int seconds = 0;
	private RuntimeSpeedController simulationSpeed = RuntimeSpeedController.getInstance();
	private static SimulationClock clock = null; 
	private boolean keepTicking = true;
	private ArrayList<String> departureTimes = new ArrayList<String>();
	
	private SimulationClock() {}
	
	public static SimulationClock GetInstance() {
		if(clock == null) {
			clock = new SimulationClock();
			clock.run();
		}
		
		return clock;
	}
	
	public String CurrentTime() {
		return GiveTimeAsString(hours, minutes);
	}
	
	private String GiveTimeAsString(int hoursValue, int minutesValue) {
		String time = "";
		
		if(hoursValue < 10) {
			time += "0";
		}
		
		time += hoursValue + ":";
		
		if(minutesValue < 10) {
			time += "0";
		}
		
		time += minutesValue;
		
		return time;
	}
	
	public void StopTheClock() {
		keepTicking = false;
	}
	
	public String AddDepartureTime(int minutesTillDeparture) {	
		int departureMinutes = minutes % 60;
		int departureHours = (hours + (minutes - departureMinutes)) % 24;	
		
		String departureTime = GiveTimeAsString(departureHours, departureMinutes);
		departureTimes.add(departureTime);
		return departureTime;
	}
	
	private void DepartureDue() {
		String currentTime = CurrentTime();
		
		if(departureTimes.contains(currentTime)) {
			departureTimes.remove(currentTime);
			
			setChanged();
			notifyObservers();
		}
	}
	
	private void IncreaseSeconds(int numberOfIncreaments) {
		for(int c = 0; c < numberOfIncreaments; c++) {
			seconds++;
			if(seconds == 60) {
				seconds = 0;
				IncreaseMinutes(1);
			}
		}
	}
	
	private void IncreaseMinutes(int numberOfIncreaments) {
		for(int c = 0; c < numberOfIncreaments; c++) {
			minutes++;
			if(minutes == 60) {
				minutes = 0;
				IncreaseHours(1);
			}
		}
	}
	
	private void IncreaseHours(int numberOfIncreaments) {
		for(int c = 0; c < numberOfIncreaments; c++) {
			hours++;
			if(hours == 24) {
				hours = 0;
			}
		}
	}

	@Override
	public void notifyObservers(Object arg) {
		// TODO Auto-generated method stub
		super.notifyObservers(arg);
	}

	@Override
	public void run() {
		while(keepTicking) {
			try {
				Thread.sleep(1000/simulationSpeed.CurrentSpeedSetting());
			} catch (InterruptedException e) {
				// Restore the interrupted status
			    Thread.currentThread().interrupt(); 
			    // TODO: Not sure if this is how I want this handled.
			}
			IncreaseSeconds(1);
			DepartureDue();
		}
	}
}