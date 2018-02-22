package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core;
/*
 * Appears at when all passengers have checked-in. 
 * It shows a summary of all flights information. 
 * @author Mehdi Seddiq (ms256)
 */
import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

public class SummaryGUI extends JFrame{

	private static final long serialVersionUID = 1L;
	JTextArea infoArea = new JTextArea(40, 40);
	
	//constructor	
    public SummaryGUI(TreeMap<String, Flight> flights) {
    	String titleRow="";
    	StringBuffer FlightInfo = new StringBuffer();
    	titleRow="Summary of the Flights"+ '\n'+'\n';
    	FlightInfo.append(titleRow);
    	
    	for (Map.Entry<String, Flight> entry: flights.entrySet()) {
    		FlightInfo.append(entry.getValue().SummaryString());
    	}
    	String Final=FlightInfo.toString();
    	infoArea.setFont(new Font (Font.MONOSPACED, Font.PLAIN,12));
    	infoArea.setEditable(false);
    	infoArea.setText(Final);
    	//Set text scrolling and border
        JScrollPane scrollingArea = new JScrollPane(infoArea);
        
        //Get the content pane, set layout and add to centre
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(scrollingArea, BorderLayout.CENTER);
        
        //Set window characteristics
        this.setContentPane(content);
        this.setTitle("Summary of the Flights");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
