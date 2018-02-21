package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class kiosk1 extends JFrame  implements ActionListener{
	
	JButton close, search;
	JTextField refinput, surnameinput, errordisplay1, errordisplay2;
	JLabel title, refLabel, surnameLabel;
	
	public kiosk1(/*PTCabinlist list*/) {
    	//this.pTCabinlist=list;
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
		 search = new JButton("Search");
		 search.addActionListener(this);
	        
	     close = new JButton("Close");
	    // close.setSize(400,60);
	     close.addActionListener(this);
	      
	     southPanel.setLayout(new FlowLayout(FlowLayout.CENTER,100,20));
	     southPanel.add (search);
	     southPanel.add(close);
	     this.add(southPanel, BorderLayout.SOUTH);
	}
	private void setupNorthPanel() {
		// TODO Auto-generated method stub
		JPanel titlePanel = new JPanel();
		title = new JLabel("WELCOME TO KIOSK");
		title.setFont( new Font(Font.MONOSPACED, Font.BOLD,30));
        titlePanel.add(title);   
        
        //Set up the area where the results will be displayed.
       // result= new JTextField(25);     
        //result.setEditable(false);
        
        
        
        //set up south panel containing 3 previous areas and add it to the frame
        JPanel northPanel = new JPanel();
        northPanel.add(titlePanel);
        this.add(northPanel, BorderLayout.NORTH);   	
	}
	
	private void setupCenterPanel() {
		// TODO Auto-generated method stub
		
		JPanel mainPanel1 = new JPanel();
		mainPanel1.setLayout(new BoxLayout(mainPanel1, BoxLayout.PAGE_AXIS));
		JPanel refPanel = new JPanel();
		refPanel.setLayout(new FlowLayout());
		refLabel= new JLabel("REFERENCE NUMBER");
		refLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		refinput= new JTextField(25);
		errordisplay1	= new JTextField(40);
		errordisplay1.setEnabled(false);
		refPanel.add(refLabel);
		refPanel.add(refinput);
		mainPanel1.add(refPanel);
		mainPanel1.add(errordisplay1);
		
		JPanel mainPanel2 = new JPanel();
		mainPanel2.setLayout(new BoxLayout(mainPanel2, BoxLayout.PAGE_AXIS));
		JPanel surnamePanel = new JPanel();
		surnamePanel.setLayout(new FlowLayout());
		surnameLabel= new JLabel("SURNAME");
		surnameLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		surnameinput= new JTextField(25);
		errordisplay2	= new JTextField(40);
		errordisplay2.setEnabled(false);
		surnamePanel.add(surnameLabel);
		surnamePanel.add(surnameinput);
		mainPanel2.add(surnamePanel);
		mainPanel2.add(errordisplay2);
		JPanel centerPanel = new JPanel();
        centerPanel.add(mainPanel1);
        centerPanel.add(mainPanel2);
        this.add(centerPanel,BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource() == search) {
    		search();
    	}
		else if (e.getSource() == close) {
    		JOptionPane.showMessageDialog(this, 
    				 " thank you ");
    		System.exit(0);
    	}
	}

	
	private void search() {
		String refString = refinput.getText().trim();
		String surString = surnameinput.getText().trim();
		if (surString == null || surString.isEmpty()) {
			errordisplay2.setText("Please add your Surname");
		}
		for(char c : surString.toCharArray()){
		        if(Character.isDigit(c)|| Character.isSpaceChar(c)){
		        	errordisplay2.setText("Your surname must not have any spaces or numbers");
		        }
		}        
	}
	

}
