package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class stage2 extends JFrame  implements ActionListener{
	
	JLabel title,flight1,flight2,flight3,desk1,desk2
		,queuelabel,speedlabel;
	JTextArea deskinfo2,flightinfo1,flightinfo2,flightinfo3,deskinfo1;
	JScrollPane QueueList;
	JCheckBox spbox1,spbox2,spbox3;
	JButton close;
	JTable table;
	GridBagConstraints c;
	
	public stage2() {
		setTitle("DISPLAY");
    	setSize(800,800);
    	setResizable(true);
		setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
 
		setupSouthPanel();
		setupNorthPanel();
		setupCenterPanel();
	
        //pack and set visible
        pack();
        setVisible(true);
	}


	private void setupCenterPanel() {
		// TODO Auto-generated method stub
		JPanel tablequeue = new JPanel();
		tablequeue.setLayout(new BoxLayout(tablequeue,BoxLayout.Y_AXIS));
		String [] tablelabels = {"Reference Number","Name","Flight's Number"};
		Object [][] a= {
				{"111","Alan","aedf"},
				{"111","Alan","aedf"},
				{"111","Alan","aedf"},
		};
		queuelabel=new JLabel("Passenger's Queue");
		queuelabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		table=new JTable(a,tablelabels);
		table.setPreferredScrollableViewportSize(new Dimension(200,400));
		table.setFillsViewportHeight(true);
		QueueList=new JScrollPane(table);
		tablequeue.add(queuelabel);
		tablequeue.add(QueueList);
		
		JPanel desk_flights=new JPanel();
		
		JPanel DesksPanel= new JPanel();
		DesksPanel.setLayout(new BoxLayout(DesksPanel,BoxLayout.Y_AXIS));
		JPanel Desk1Panel= new JPanel();
		Desk1Panel.setLayout(new GridBagLayout());
		
		desk1=new JLabel("Desk1:");
		desk1.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(30,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Desk1Panel.add(desk1,c);
		
		deskinfo1=new JTextArea("information");
		deskinfo1.setFont( new Font(Font.MONOSPACED, Font.ITALIC,15));
		deskinfo1.setEditable(false);
		deskinfo1.setPreferredSize(new Dimension(120,50));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.ipady=50;
		c.ipadx=100;
		c.insets=new Insets(5,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Desk1Panel.add(deskinfo1,c);
		
		JPanel Desk2Panel= new JPanel();
		Desk2Panel.setLayout(new GridBagLayout());
		desk2=new JLabel("Desk2");
		desk2.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(10,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Desk2Panel.add(desk2,c);
		
		String ap = "information  \r\n information  \r\n   oeeoo";
		deskinfo2=new JTextArea("information");
		deskinfo2.setEditable(false);
		deskinfo2.setFont( new Font(Font.MONOSPACED, Font.ITALIC,15));
		deskinfo2.setPreferredSize(new Dimension(120,50));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.ipady=50;
		c.ipadx=100;
		c.insets=new Insets(5,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Desk2Panel.add(deskinfo2,c);
		
		DesksPanel.add(Desk1Panel);
		DesksPanel.add(Desk2Panel);
		
		JPanel FlightsPanel= new JPanel();
		FlightsPanel.setLayout(new BoxLayout(FlightsPanel,BoxLayout.Y_AXIS));
		JPanel Flight1Panel= new JPanel();
		Flight1Panel.setLayout(new GridBagLayout());
		flight1=new JLabel("Flight1");
		flight1.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(30,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight1Panel.add(flight1,c);
		
		flightinfo1=new JTextArea("information");
		flightinfo1.setFont( new Font(Font.MONOSPACED, Font.ITALIC,15));
		flightinfo1.setEditable(false);
		flightinfo1.setPreferredSize(new Dimension(120,50));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.ipady=50;
		c.ipadx=100;
		c.insets=new Insets(5,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight1Panel.add(flightinfo1,c);
		
		JPanel Flight2Panel= new JPanel();
		Flight2Panel.setLayout(new GridBagLayout());
		flight2=new JLabel("Flight2");
		flight2.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		flightinfo2=new JTextArea("informations");
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(10,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight2Panel.add(flight2,c);
		
		flightinfo2.setFont( new Font(Font.MONOSPACED, Font.ITALIC,15));
		flightinfo2.setEditable(false);
		flightinfo2.setPreferredSize(new Dimension(120,50));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.ipady=50;
		c.ipadx=100;
		c.insets=new Insets(5,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight2Panel.add(flightinfo2,c);
		
		JPanel Flight3Panel= new JPanel();
		Flight3Panel.setLayout(new GridBagLayout());
		flight3=new JLabel("Flight3");
		flight3.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		flightinfo3=new JTextArea("informations");
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(10,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight3Panel.add(flight3,c);
		
		flightinfo3.setFont( new Font(Font.MONOSPACED, Font.ITALIC,15));
		flightinfo3.setEditable(false);
		flightinfo3.setPreferredSize(new Dimension(120,50));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.ipady=50;
		c.ipadx=100;
		c.insets=new Insets(5,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight3Panel.add(flightinfo3,c);
		
		FlightsPanel.add(Flight1Panel);
		FlightsPanel.add(Flight2Panel);
		FlightsPanel.add(Flight3Panel);
		
		desk_flights.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.fill= GridBagConstraints.VERTICAL;
		desk_flights.add(DesksPanel,c);
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.insets=new Insets(10,0,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		desk_flights.add(FlightsPanel,c);
		
		
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
        centerPanel.add(tablequeue);
        centerPanel.add(desk_flights);
        this.add(centerPanel,BorderLayout.CENTER);
	}


	private void setupNorthPanel() {
		// TODO Auto-generated method stub
		JPanel titlePanel = new JPanel();
		title = new JLabel("hello");
		title.setFont( new Font(Font.MONOSPACED, Font.BOLD,30));
        titlePanel.add(title);   
        
        //set up north panel containing the title and add it to the frame
        JPanel northPanel = new JPanel();
        northPanel.add(titlePanel);
        this.add(northPanel, BorderLayout.NORTH);   	
	}


	private void setupSouthPanel() {
		// TODO Auto-generated method stub
		JPanel checkboxlabelPanel=new JPanel();
		speedlabel=new JLabel("Speed:");
		checkboxlabelPanel.add(speedlabel);
		
		JPanel checkboxPanel=new JPanel();
		checkboxPanel.setLayout(new GridLayout(1,3));
		spbox1=new JCheckBox("x0.5");
		spbox2=new JCheckBox("x1");
		spbox3=new JCheckBox("x2");
		ButtonGroup check = new ButtonGroup();
		check.add(spbox1);
		check.add(spbox2);
		check.add(spbox3);
		checkboxPanel.add(spbox1);
		checkboxPanel.add(spbox2);
		checkboxPanel.add(spbox3);
		
		//JPanel closepanel=new JPanel();
		close=new JButton("Close");
		close.addActionListener(this);
		//closepanel.add(close);
		
		JPanel SouthPanel = new JPanel();
		SouthPanel.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		//c.weighty=2;
		//c.ipady=40;
		c.fill= GridBagConstraints.HORIZONTAL;
		SouthPanel.add(checkboxlabelPanel,c);
		c = new GridBagConstraints();
		c.gridx=1;
		c.gridy=0;
		c.fill= GridBagConstraints.HORIZONTAL;
		SouthPanel.add(checkboxPanel,c);
		c = new GridBagConstraints();
		c.insets=new Insets(20,130,20,0);
		c.gridx=2;
		c.gridy=0;
		c.gridwidth=5;
		c.ipady=20;
		c.fill= GridBagConstraints.HORIZONTAL;
		SouthPanel.add(close,c);
        this.add(SouthPanel, BorderLayout.SOUTH);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == close) {
    		JOptionPane.showMessageDialog(this, 
    				 " thank you ");
    		System.exit(0);
		}	
	}

}
