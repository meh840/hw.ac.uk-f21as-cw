package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class kiosk2 extends JFrame  implements ActionListener {
	
	
	JButton cancel, submit;
	JLabel refDisplay, firstDisplay,surnameDisplay,flightcodeDisplay , destinationDisplay;
	JLabel title, refLabel, firstLabel,surnameLabel, flightcodeLabel, destinationLabel, LweightLabel, 
	diamensionsLabel, lengthLabel, widthLabel, heightLabel;
	JTextField Lweightinput, lengthinput, widthinput, heightinput;
	
	
	
	public kiosk2() {
    	
    	setTitle("KIOSK");
    	setSize(800,500);
    	setResizable(false);
		setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
 
		setupSouthPanel();
		setupNorthPanel();
		setupCenterPanel();
		//setupLeftPanel();
		//setupRightPanel();
		
		

        //pack and set visible
        //pack();
        setVisible(true);
        
    	
    }
	
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
	
	private void setupNorthPanel() {
		// TODO Auto-generated method stub
		JPanel titlePanel = new JPanel();
		title = new JLabel("CHECK IN");
		title.setFont( new Font(Font.MONOSPACED, Font.BOLD,30));
        titlePanel.add(title);   
        
        //set up north panel containing the title and add it to the frame
        JPanel northPanel = new JPanel();
        northPanel.add(titlePanel);
        this.add(northPanel, BorderLayout.NORTH);   	
	}
	
	private void setupCenterPanel() {
		// TODO Auto-generated method stub
		
		JPanel mainPanel1 = new JPanel();
		mainPanel1.setLayout(new BoxLayout(mainPanel1, BoxLayout.Y_AXIS));
		
		JPanel refPanel = new JPanel();
		refPanel.setLayout(new FlowLayout());
		refLabel= new JLabel("REFERENCE NUMBER");
		refLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		refDisplay= new JLabel(" sdfjsdjkhgkds ");
		refPanel.add(refLabel);
		refPanel.add(refDisplay);
		mainPanel1.add(refPanel);
		
		JPanel firstPanel = new JPanel();
		firstPanel.setLayout(new FlowLayout());
		firstLabel= new JLabel("Firstname");
		firstLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		firstDisplay= new JLabel(" ");
		firstPanel.add(firstLabel);
		firstPanel.add(firstDisplay);
		mainPanel1.add(firstPanel);
		
		JPanel surnamePanel = new JPanel();
		surnamePanel.setLayout(new FlowLayout());
		surnameLabel= new JLabel("Surname");
		surnameLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		surnameDisplay= new JLabel(" ");
		surnamePanel.add(surnameLabel);
		surnamePanel.add(surnameDisplay);
		mainPanel1.add(surnamePanel);
		
		JPanel flightcodePanel = new JPanel();
		flightcodePanel.setLayout(new FlowLayout());
		flightcodeLabel= new JLabel("Flight");
		flightcodeLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		flightcodeDisplay= new JLabel(" ");
		flightcodePanel.add(flightcodeLabel);
		flightcodePanel.add(flightcodeDisplay);
		mainPanel1.add(flightcodePanel);
		
		JPanel destinationPanel = new JPanel();
		destinationPanel.setLayout(new FlowLayout());
		destinationLabel= new JLabel("Destination");
		destinationLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		destinationDisplay= new JLabel(" ");
		destinationPanel.add(destinationLabel);
		destinationPanel.add(destinationDisplay);
		mainPanel1.add(destinationPanel);
		
		
		JPanel weightPanel = new JPanel();
		weightPanel.setLayout(new FlowLayout());
		LweightLabel= new JLabel("Baggage weight");
		LweightLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		Lweightinput= new JTextField(9);
		weightPanel.add(LweightLabel);
		weightPanel.add(Lweightinput);
		mainPanel1.add(weightPanel);
		
		JPanel dimensionsPanel = new JPanel();
		//dimensionsPanel.setLayout(new FlowLayout());
		diamensionsLabel= new JLabel("Baggage dimensions:");
		diamensionsLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		//make the length domain
		JPanel lengthPanel = new JPanel();
		lengthPanel.setLayout(new FlowLayout());
		lengthLabel= new JLabel("Length");
		lengthLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		lengthinput= new JTextField(9);
		lengthPanel.add(lengthLabel);
		lengthPanel.add(lengthinput);
		//make the  domain
		JPanel widthPanel = new JPanel();
		widthPanel.setLayout(new FlowLayout());
		widthLabel= new JLabel("Width");
		widthLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		widthinput= new JTextField(9);
		widthPanel.add(widthLabel);
		widthPanel.add(widthinput);
		//make the length domain
		JPanel heightPanel = new JPanel();
		heightPanel.setLayout(new FlowLayout());
		heightLabel= new JLabel("Height");
		heightLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		heightinput= new JTextField(9);
		
		
		
		dimensionsPanel.add(LweightLabel);
		dimensionsPanel.add(Lweightinput);
		mainPanel1.add(dimensionsPanel);
		
		
		
		
		JPanel centerPanel = new JPanel();
        centerPanel.add(mainPanel1);
        this.add(centerPanel,BorderLayout.CENTER);
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
