package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core;
import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

public class SummaryGUI extends JFrame{

	private static final long serialVersionUID = 1L;
	JTextArea infoArea = new JTextArea(6, 20);
	
	//constructor	
    public SummaryGUI(TreeMap<String, Flight> flights) {
    	String titleRow,FlightInfo="";
    	titleRow="FlightCode, DestinationAirport, Carrier, maxPassengerCapacity, maxWeight, maxVolume";
    	//Set text scrolling and border
    	infoArea.setText(titleRow);
    	//"" + flightCode + "," + destinationAirport + "," + carrier + "," + maxPassengerCapacity + "," + maxWeight + "," + maxVolume;
    	for (Map.Entry<String, Flight> entry: flights.entrySet()) {
    		FlightInfo=entry.getValue().SummaryString()+"\n";
    		infoArea.setText(FlightInfo);
    	}
        JScrollPane scrollingArea = new JScrollPane(infoArea);
        
        //Get the content pane, set layout and add to centre
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(scrollingArea, BorderLayout.CENTER);
        
        //Set window characteristics
        this.setContentPane(content);
        this.setTitle("Summary of the Flights");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }
}
