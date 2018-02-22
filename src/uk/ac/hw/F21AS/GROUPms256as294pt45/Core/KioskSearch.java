package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core;

import java.awt.*;
import java.awt.event.*;
import java.util.TreeMap;

import javax.swing.*;

/**
 * The class is the Kiosk GUI. It is the first frame when you access in Kiosk GUI.
 * It is used as a search engine
 * @author Panagiotis Tsamoutalis (pt45)
 *
 */
public class KioskSearch extends JFrame  implements ActionListener{
	private KioskLogic logic;
	private int referenceSize=7;
	JButton close, search,SumGui;
	JTextField refinput, surnameinput, errordisplay1, errordisplay2;
	JLabel title, refLabel, surnameLabel;
	KioskCheckIn abc;
	
	/**
	 * Constructor of the Kiosk GUI search engine class.
	 * @param logic The kiosk logic which has the booking, flight & error logger.
	 */
	public KioskSearch(KioskLogic logic) {
		this.logic = logic;
    	
    	setTitle("KIOSK");
    	setSize(800,450);
    	setResizable(true);
		setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
 
		setupSouthPanel();
		setupNorthPanel();
		setupCenterPanel();

        //pack and set visible
        //pack();
        setVisible(true);
    }
	
	/**
	 * It sets the south panel of the GUI.
	 * @return A Search button for searching.
	 * @return A Close button to close the GUI.
	 * @return A Summary button to open the SummaryGUI.
	 */
	private void setupSouthPanel() {
		 JPanel southPanel = new JPanel();
		 search = new JButton("Search");
		 search.addActionListener(this);
	        
	     close = new JButton("Close");
	     close.addActionListener(this);
	     
	     SumGui = new JButton("Summary");
	     SumGui.addActionListener(this);
	     
	     southPanel.setLayout(new FlowLayout(FlowLayout.CENTER,100,20));
	     southPanel.add (search);
	     southPanel.add(close);
	     southPanel.add(SumGui);
	     this.add(southPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * It is  a method to handle the north panel of the GUI.
	 * @return A label as the title of the GUI
	 */
	private void setupNorthPanel() {
		JPanel titlePanel = new JPanel();
		title = new JLabel("WELCOME TO KIOSK");
		title.setFont( new Font(Font.MONOSPACED, Font.BOLD,30));
        titlePanel.add(title);   
        JPanel northPanel = new JPanel();
        northPanel.add(titlePanel);
        this.add(northPanel, BorderLayout.NORTH);   	
	}
	
	/**
	 * It handles the center panel of the GUI
	 * @return 2 labels. One for the Reference number and one for surname.
	 * @return 4 textfields. Two for the Reference number and Two for surname.
	 */
	private void setupCenterPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout( new GridLayout(2,1));
		JPanel mainPanel1 = new JPanel();
		mainPanel1.setLayout(new BoxLayout(mainPanel1, BoxLayout.Y_AXIS));
		JPanel refPanel = new JPanel();
		refPanel.setLayout(new FlowLayout(FlowLayout.LEADING,100,15));
		refLabel= new JLabel("REFERENCE NUMBER");
		refLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,20));
		refinput= new JTextField(25);
		JPanel refErrorPanel = new JPanel();
		refErrorPanel.setLayout(new FlowLayout(FlowLayout.LEADING,180,0));
		errordisplay1	= new JTextField(60);
		errordisplay1.setFont( new Font(Font.MONOSPACED, Font.ITALIC,13));
		errordisplay1.setEnabled(false);
		refErrorPanel.add(errordisplay1);
		
		refPanel.add(refLabel);
		refPanel.add(refinput);
		mainPanel1.add(refPanel);
		mainPanel1.add(refErrorPanel);
		
		JPanel mainPanel2 = new JPanel();
		mainPanel2.setLayout(new BoxLayout(mainPanel2, BoxLayout.Y_AXIS));
		
		JPanel surnamePanel = new JPanel();
		surnamePanel.setLayout(new FlowLayout(FlowLayout.LEADING,100,15));
		surnameLabel= new JLabel("SURNAME");
		surnameLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,20));
		
		JPanel surnPanel = new JPanel();
		surnPanel.setLayout(new FlowLayout(FlowLayout.LEADING,100,15));
		surnameinput= new JTextField(25);
		surnPanel.add(surnameinput);
		
		JPanel surErrorPanel = new JPanel();
		surErrorPanel.setLayout(new FlowLayout(FlowLayout.LEADING,180,0));
		errordisplay2	= new JTextField(60);
		errordisplay2.setFont( new Font(Font.MONOSPACED, Font.ITALIC,13));
		errordisplay2.setEnabled(false);
		surErrorPanel.add(errordisplay2);
		
		surnamePanel.add(surnameLabel);
		surnamePanel.add(surnPanel);
		mainPanel2.add(surnamePanel);
		mainPanel2.add(surErrorPanel);
		
		mainPanel.add(mainPanel1);
		mainPanel.add(mainPanel2);
		
		JPanel centerPanel = new JPanel();
        centerPanel.add(mainPanel);
        this.add(centerPanel,BorderLayout.CENTER);
	}
	
	/**
	 * It handles the buttons of the GUI
	 * @param Activation Event e. It is used to indicate which button was activated.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == search) {
    		search();
    	} else if (e.getSource() == close) {
    		JOptionPane.showMessageDialog(this, " thank you ");
    		System.exit(0);
    	} else if (e.getSource()==SumGui) {
    		SummaryGUI summary = new SummaryGUI(logic.GetAllFlights());
    	}
	}

	/**
	 * Search in the booking list to identify the user and calls the Check in GUI.
	 * 
	 */
	private void search() {
		String refString = refinput.getText().trim();
		String surString = surnameinput.getText().trim();
		
		boolean surname_checked = check_surname(surString);
		boolean reference_checked = check_reference(refString);
		
		if (surname_checked==true && reference_checked==true)  {
			if(logic.LocateBooking(refString, surString)) {
				abc=new KioskCheckIn(logic);
				System.exit(0);
			} else {
				errordisplay1.setText("No match for details given. Please try again or see our staffed check in desks.");
				String error = "Details do not match with a booking: " + refString + "," + surString;
				logic.AddCheckInError(error);
			}
		} else {
			logic.AddCheckInError(ErrorReport());
		}
	}
	
	/**
	 * checking the surname's input.
	 * @param surString
	 * @return True or False
	 */
	private Boolean check_surname(String surString) {
		if (surString == null || surString.isEmpty()) {
			errordisplay2.setText("Please add your Surname!");
			return false;
		}
		for(char c : surString.toCharArray()){
	        if(Character.isDigit(c)|| Character.isSpaceChar(c)){
	        	errordisplay2.setText("Your surname must not have any spaces or numbers!");
	        	return false;
	        }
		}        
		return true;
	}
	
	/**
	 * Checking the reference number input
	 * @param refString
	 * @return True or false
	 */
	private Boolean check_reference (String refString) {
		char[] refArray = new char[refString.toCharArray().length];
		int counter =0;
		
		if (refString == null || refString.isEmpty()) {
			errordisplay1.setText("Please add your Reference Number!");
			return false;
		}
		
		if(refString.toCharArray().length <referenceSize) {
			errordisplay1.setText("Your Reference number is too short!");
			return false;
		} else if(refString.toCharArray().length >referenceSize) {
			errordisplay1.setText("Your Reference number is too large!");
			return false;
		}
		
		for(char c : refString.toCharArray()){
	        if(Character.isSpaceChar(c)){
	        	errordisplay1.setText("Your surname must not have any spaces !");
	        	return false;
	        }
		}    
		
		for(char c : refString.toCharArray()) {
			refArray[counter]=c;
			counter++;
		}
		
		if(!BookingReferenceChecker.CheckABookingReference(refString)) {
			errordisplay1.setText("Your Reference Number is incorrect !");
        	return false;
		}
		
		return true;
	}
	
	/**
	 * It makes a report for the ErrorList.
	 * @return a String with all the information about an error.
	 */
	private String ErrorReport() {
		String RefNum= refinput.getText().trim();
		String SurName= surnameinput.getText().trim();
		String RefErr= errordisplay1.getText().trim();
		String SurErr= errordisplay2.getText().trim();
		String Results=RefNum+","+SurName+","+RefErr+","+SurErr;
		return Results;
	}

}
