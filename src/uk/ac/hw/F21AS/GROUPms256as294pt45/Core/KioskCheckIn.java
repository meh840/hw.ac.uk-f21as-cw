package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The class is a Kioks GUI. It is a frame which display booking information.
 * It is used to do the checking in.
 * @author Panagiotis Tsamoutalis (pt45)
 *
 */
public class KioskCheckIn extends JFrame  implements ActionListener {
	JButton cancel, submit;
	JLabel refDisplay, firstDisplay,surnameDisplay,flightcodeDisplay , destinationDisplay,heighterrorDisplay,
	widtherrorDisplay,lengtherrorDisplay,weighterrorDisplay;
	JLabel title, refLabel, firstLabel,surnameLabel, flightcodeLabel, destinationLabel, LweightLabel, 
	dimensionsLabel, lengthLabel, widthLabel, heightLabel,dimensionsinfLabel;
	JTextField Lweightinput, lengthinput, widthinput, heightinput;
	double maxWeight=100.00;
	double limitWeight=32.00;
	int maxDimension=200;
	int limitDimension=110;
	private Booking booking;
	private Flight flight;
	private KioskLogic logic;
	
	/**
	 * Constructor of the Kiosk GUI Check in class.
	 * @param logic The kiosk logic which has the booking, flight & error logger.
	 */
	public KioskCheckIn(KioskLogic logic) {
		this.logic = logic;
		booking = logic.CurrentCheckInBooking();
		flight = logic.CurrentFlightDetails();
		
    	setTitle("KIOSK");
    	setSize(800,800);
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
	 * @return A Submit button for cheking in.
	 * @return A Cancel button to dispose the GUI.
	 */
	private void setupSouthPanel() {
		// TODO Auto-generated method stub
		JPanel southPanel = new JPanel();
		 submit = new JButton("Submit");
		 submit.addActionListener(this);
	        
	     cancel = new JButton("Cancel");
	    // close.setSize(400,60);
	     cancel.addActionListener(this);
	      
	     southPanel.setLayout(new FlowLayout(FlowLayout.CENTER,100,20));
	     southPanel.add (submit);
	     southPanel.add(cancel);
	     this.add(southPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * It handles the north panel of the GUI.
	 * @return A label as the title of the GUI
	 */
	private void setupNorthPanel() {
		JPanel titlePanel = new JPanel();
		title = new JLabel("CHECK IN");
		title.setFont( new Font(Font.MONOSPACED, Font.BOLD,30));
        titlePanel.add(title);   
        
        //set up north panel containing the title and add it to the frame
        JPanel northPanel = new JPanel();
        northPanel.add(titlePanel);
        this.add(northPanel, BorderLayout.NORTH);   	
	}
	
	/**
	 * It handles the center panel of the GUI
	 * It displays all the information about a booking.
	 * Can be add information about the baggage.
	 */
	private void setupCenterPanel() {
		JPanel mainPanel1 = new JPanel();
		mainPanel1.setLayout(new BoxLayout(mainPanel1, BoxLayout.Y_AXIS));
		
		// Create the reference number panel
		JPanel refPanel = new JPanel();
		refPanel.setLayout(new FlowLayout(FlowLayout.LEADING,100,10));
		refLabel= new JLabel("Reference Number");
		refLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		refDisplay= new JLabel(booking.GetBookingReference());
		refPanel.add(refLabel);
		refPanel.add(refDisplay);
		mainPanel1.add(refPanel);
		
		// Create the firstname panel
		JPanel firstPanel = new JPanel();
		firstPanel.setLayout(new FlowLayout(FlowLayout.LEADING,100,10));
		firstLabel= new JLabel("Firstname");
		firstLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		JPanel firPanel = new JPanel();
		firPanel.setLayout(new FlowLayout(FlowLayout.LEADING,78,10));
		firstDisplay= new JLabel(booking.GetFirstName());
		firPanel.add(firstDisplay);
		firstPanel.add(firstLabel);
		firstPanel.add(firPanel);
		mainPanel1.add(firstPanel);
		
		// Create the surname panel
		JPanel surnamePanel = new JPanel();
		surnamePanel.setLayout(new FlowLayout(FlowLayout.LEADING,100,10));
		surnameLabel= new JLabel("Surname");
		surnameLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		JPanel surPanel = new JPanel();
		surPanel.setLayout(new FlowLayout(FlowLayout.LEADING,100,10));
		surnameDisplay= new JLabel(booking.GetSurname());
		surPanel.add(surnameDisplay);
		surnamePanel.add(surnameLabel);
		surnamePanel.add(surPanel);
		mainPanel1.add(surnamePanel);
		
		// Create the flight panel
		JPanel flightcodePanel = new JPanel();
		flightcodePanel.setLayout(new FlowLayout(FlowLayout.LEADING,100,10));
		flightcodeLabel= new JLabel("Flight");
		flightcodeLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		JPanel flightPanel = new JPanel();
		flightPanel.setLayout(new FlowLayout(FlowLayout.LEADING,110,10));
		flightcodeDisplay= new JLabel(booking.GetFlightCode());
		flightPanel.add(flightcodeDisplay);
		flightcodePanel.add(flightcodeLabel);
		flightcodePanel.add(flightPanel);
		mainPanel1.add(flightcodePanel);
		
		// Create the destination panel
		JPanel destinationPanel = new JPanel();
		destinationPanel.setLayout(new FlowLayout(FlowLayout.LEADING,100,10));
		destinationLabel= new JLabel("Destination");
		destinationLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		JPanel destPanel = new JPanel();
		destPanel.setLayout(new FlowLayout(FlowLayout.LEADING,60,10));
		destinationDisplay= new JLabel(flight.Destination());
		destPanel.add(destinationDisplay);
		destinationPanel.add(destinationLabel);
		destinationPanel.add(destPanel);
		mainPanel1.add(destinationPanel);
		
		// Create the baggage weight panel
		JPanel weightPanel = new JPanel();
		weightPanel.setLayout(new FlowLayout(FlowLayout.LEADING,100,10));
		LweightLabel= new JLabel("Baggage weight");
		LweightLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		JPanel weiPanel = new JPanel();
		weiPanel.setLayout(new FlowLayout(FlowLayout.LEADING,30,10));
		Lweightinput= new JTextField(9);
		JPanel weighterrorPanel = new JPanel();
		weighterrorPanel.setLayout(new FlowLayout(FlowLayout.LEADING,20,10));
		weighterrorDisplay= new JLabel("Baggage weight");
		weighterrorPanel.add(weighterrorDisplay);
		weiPanel.add(Lweightinput);
		weightPanel.add(LweightLabel);
		weightPanel.add(weiPanel);
		weightPanel.add(weighterrorPanel);
		mainPanel1.add(weightPanel);
		
		// Create the dimension panel
		JPanel dimensionsPanel = new JPanel();
		dimensionsPanel.setLayout(new BoxLayout(dimensionsPanel, BoxLayout.Y_AXIS));
		
		//dimensions title
		JPanel dimenLab =new JPanel();
		dimenLab.setLayout(new FlowLayout(FlowLayout.LEADING,100,5));
		dimensionsLabel= new JLabel("Baggage dimensions:");
		dimensionsLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		dimenLab.add(dimensionsLabel);
		
		// informations about the dimensions
		JPanel diminfLab =new JPanel();
		diminfLab.setLayout(new FlowLayout(FlowLayout.LEADING,100,5));
		dimensionsinfLabel= new JLabel("Please, add the dimensions of your suitcase in cm!!");
		dimensionsinfLabel.setFont( new Font(Font.MONOSPACED, Font.ITALIC,13));
		diminfLab.add(dimensionsinfLabel);
		
		//the three dimensions panel
		JPanel lwhPanel =new JPanel();
		lwhPanel.setLayout(new GridLayout(3,1));
		
		//make the length domain
		JPanel lengthPanel = new JPanel();
		lengthPanel.setLayout(new FlowLayout(FlowLayout.LEADING,100,10));
		lengthLabel= new JLabel("Length");
		lengthLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		JPanel lenPanel = new JPanel();
		lenPanel.setLayout(new FlowLayout(FlowLayout.LEADING,120,10));
		lengthinput= new JTextField(9);
		JPanel lengtherrorPanel = new JPanel();
		lengtherrorPanel.setLayout(new FlowLayout(FlowLayout.LEADING,20,10));
		lengtherrorDisplay= new JLabel("Length");
		lengtherrorPanel.add(lengtherrorDisplay);
		lenPanel.add(lengthinput);
		lengthPanel.add(lengthLabel);
		lengthPanel.add(lenPanel);
		lengthPanel.add(lengtherrorPanel);
		
		//make the  width domain
		JPanel widthPanel = new JPanel();
		widthPanel.setLayout(new FlowLayout(FlowLayout.LEADING,100,10));
		widthLabel= new JLabel("Width");
		widthLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		JPanel widPanel = new JPanel();
		widPanel.setLayout(new FlowLayout(FlowLayout.LEADING,130,10));
		widthinput= new JTextField(9);
		JPanel widtherrorPanel = new JPanel();
		widtherrorPanel.setLayout(new FlowLayout(FlowLayout.LEADING,20,10));
		widtherrorDisplay= new JLabel("Width");
		widtherrorPanel.add(widtherrorDisplay);
		widPanel.add(widthinput);
		widthPanel.add(widthLabel);
		widthPanel.add(widPanel);
		widthPanel.add(widtherrorPanel);
		
		//make the height domain
		JPanel heightPanel = new JPanel();
		heightPanel.setLayout(new FlowLayout(FlowLayout.LEADING,100,10));
		heightLabel= new JLabel("Height");
		heightLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		JPanel heigPanel = new JPanel();
		heigPanel.setLayout(new FlowLayout(FlowLayout.LEADING,120,10));
		heightinput= new JTextField(9);
		JPanel heiErrorPanel = new JPanel();
		heiErrorPanel.setLayout(new FlowLayout(FlowLayout.LEADING,20,10));
		heighterrorDisplay= new JLabel("dfdg");
		heiErrorPanel.add(heighterrorDisplay);
		heigPanel.add(heightinput);
		heightPanel.add(heightLabel);
		heightPanel.add(heigPanel);
		heightPanel.add(heiErrorPanel);
		
		lwhPanel.add(lengthPanel);
		lwhPanel.add(widthPanel);
		lwhPanel.add(heightPanel);
		dimensionsPanel.add(dimenLab);
		dimensionsPanel.add(diminfLab);
		dimensionsPanel.add(lwhPanel);
		mainPanel1.add(dimensionsPanel);
			
		JPanel centerPanel = new JPanel();
        centerPanel.add(mainPanel1);
        this.add(centerPanel,BorderLayout.CENTER);
	}
	
	/**
	 * it handles the buttons Submit and Cancel.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
    		checkGUI();
    	}
		else if (e.getSource() == cancel) {
			KioskSearch abc = new KioskSearch(logic);
    		this.dispose();
    	}
	}

	/**
	 * Checking all the inputs of the GUI.
	 * Displaying error messages if we have errors
	 */
	private void checkGUI() {
		String weightstr=Lweightinput.getText().trim();
		String lengthstr=lengthinput.getText().trim();
		String widthstr=widthinput.getText().trim();
		String heighttstr=heightinput.getText().trim();
		boolean wei=CheckWeight(weightstr);
		boolean len=CheckLength(lengthstr);
		boolean wid=CheckWidth(widthstr);
		boolean hei=CheckHeight(heighttstr);
		
		if (wei && len && wid && hei) {
			double weight = Double.parseDouble(weightstr);
			double length = Double.parseDouble(lengthstr);
			double width = Double.parseDouble(widthstr);
			double height = Double.parseDouble(heighttstr);
			
			BaggageDetails bag = new  BaggageDetails(weight, length, width, height);
			
			if(bag.Fee() > 0.0) {
				logic.AwaitingPayment(bag);
				KioskPayment abc = new KioskPayment(logic);
			} else {
				booking.SetBaggageInfo(bag);
				booking.CheckIn();
				
				flight.AddToWeight(bag.Weight());
				flight.AddToVolume(bag.Volume());
				
				
				JOptionPane.showMessageDialog(null," Tkank you for checking in. Have a nice Flight " , 
						" Information ",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	/**
	 * Checking the  weight input.
	 * @param weightstr
	 * @return True or False
	 */
	private boolean CheckWeight(String weightstr){
		if (weightstr == null || weightstr.isEmpty()) {
			weighterrorDisplay.setText("is empty!");
			return false;
		}
		Double val;
		try {
	         val=Double.parseDouble(weightstr);
		}catch (NumberFormatException e){
		   weighterrorDisplay.setText("not a number!");
		   return false;
		} 
		if(val>maxWeight) {
			weighterrorDisplay.setText("too heavy!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checking the  length input.
	 * @param lengthstr
	 * @return  True or False
	 */
	private boolean CheckLength(String lengthstr){
		if (lengthstr == null || lengthstr.isEmpty()) {
			lengtherrorDisplay.setText("is empty!");
			return false;
		}
		int val;
		try {
	         val=Integer.parseInt(lengthstr);
		}catch (NumberFormatException e){
		   lengtherrorDisplay.setText("not a number!");
		   return false;
		} 
		if(val>maxDimension) {
			lengtherrorDisplay.setText("too large!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checking the  width input.
	 * @param widhtstr
	 * @return True or False
	 */
	private boolean CheckWidth(String widhtstr){
		if (widhtstr == null || widhtstr.isEmpty()) {
			widtherrorDisplay.setText("is empty!");
			return false;
		}
		int val;
		try {
			val=Integer.parseInt(widhtstr);
		}catch (NumberFormatException e){
		   widtherrorDisplay.setText("not a number!");
		   return false;
		} 
		if(val>maxDimension) {
			widtherrorDisplay.setText("too large!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checking the  height input.
	 * @param heighttstr
	 * @return
	 */
	private boolean CheckHeight(String heighttstr){
		if (heighttstr == null || heighttstr.isEmpty()) {
			heighterrorDisplay.setText("is empty!");
			return false;
		}
		int val;
		try {
			val=Integer.parseInt(heighttstr);
	   }catch (NumberFormatException e){
		   heighterrorDisplay.setText("not a number!");
		   return false;
	   } 
		if(val>maxDimension) {
			heighterrorDisplay.setText("too large!");
			return false;
		}
		
		return true;
	}
}
