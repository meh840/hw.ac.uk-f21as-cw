package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core.Flight;

public class SimulationGUI extends JFrame  implements ActionListener{
	
	JLabel title,flight1,flight2,flight3,desk1,desk2
		,queuelabel,speedlabel,flight1time,flight2time,flight3time,timelabel;
	JTextArea deskinfo2,deskinfo1,timedisplay;
	JScrollPane QueueList,flightList1,flightList2,flightList3;
	JTextField queueinfo,flightinfo1,flightinfo2,flightinfo3;
	JCheckBox spbox1,spbox2,spbox3;
	JButton stop;
	JTable queueList,flight1table,flight2table,flight3table;
	GridBagConstraints c;
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	private RuntimeSpeedController runtimeSpeed;
	private SimulationClock Clock;
	private PassengerQueue a;
	private String [] Qlistlabels = {"Reference Number","Name","Flight's Number"};
	private String [] WVflightlabels = {"Weight %","Volume %"};
	Object [][] b= {
			{},
			
	};//b, Qlistlabels
	Object [][] ab= {
			{"111","Alan","aedf"},
			{"111","Alan","aedf"},
			{"111","Alan","aedf"},
	};
	DefaultTableModel modelList = new DefaultTableModel(b, Qlistlabels); 
	DefaultTableModel modelList1 = new DefaultTableModel(b, WVflightlabels); 
	DefaultTableModel modelList2 = new DefaultTableModel(b, WVflightlabels); 
	DefaultTableModel modelList3 = new DefaultTableModel(b, WVflightlabels); 
	public SimulationGUI() {
		runtimeSpeed = RuntimeSpeedController.getInstance();
		
		//Clock= SimulationClock.GetInstance();
		setTitle("DISPLAY");
    	//setSize(800,800);
    	this.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
		tablequeue.setLayout(new GridBagLayout());
		// title for the Queue
		queuelabel=new JLabel("Passenger's Queue");
		queuelabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(50,0,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		tablequeue.add(queuelabel,c);
		
		
		Object [][] a= {
				{"111","Alan","aedf"},
				{"111","Alan","aedf"},
				{"111","Alan","aedf"},
		};
		
		//the queue list
		queueList=new JTable(modelList);
		queueList.setPreferredScrollableViewportSize(new Dimension(340,400));
		queueList.setFillsViewportHeight(true);
		queueList.setEnabled(false);
		centerRenderer.setVerticalAlignment(JLabel.CENTER);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		queueList.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		queueList.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		queueList.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		QueueList=new JScrollPane(queueList);
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.insets=new Insets(10,0,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		tablequeue.add(QueueList,c);
		
		//display the size of queue
		queueinfo=new JTextField("info",40);
		queueinfo.setFont( new Font(Font.MONOSPACED, Font.ITALIC,15));
		queueinfo.setEditable(false);
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=2;
		c.insets=new Insets(20,0,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		tablequeue.add(queueinfo,c);
		
		JPanel timePanel= new JPanel();
		timePanel.setLayout(new GridBagLayout());
		//the time label
		timelabel=new JLabel("Time :");
		timelabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(50,0,0,0);
		c.fill= GridBagConstraints.HORIZONTAL;
		timePanel.add(timelabel,c);
		
		//display the time
		timedisplay=new JTextArea();
		timedisplay.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		c = new GridBagConstraints();
		c.gridx=1;
		c.gridy=0;
		c.insets=new Insets(50,20,0,0);
		c.fill= GridBagConstraints.HORIZONTAL;
		timePanel.add(timedisplay,c);
		
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=3;
		c.insets=new Insets(20,0,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		tablequeue.add(timePanel,c);
		
		
		
		JPanel desk_flights=new JPanel();
		
		JPanel DesksPanel= new JPanel();
		DesksPanel.setLayout(new GridBagLayout());
		JPanel Desk1Panel= new JPanel();
		Desk1Panel.setLayout(new GridBagLayout());
		
		//kiosk1 label
		desk1=new JLabel("Kiosk 1:");
		desk1.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(0,0,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Desk1Panel.add(desk1,c);
		
		//display kiosk1 information
		deskinfo1=new JTextArea("information");
		deskinfo1.setFont( new Font(Font.MONOSPACED, Font.ITALIC,15));
		deskinfo1.setEditable(false);
		deskinfo1.setPreferredSize(new Dimension(120,50));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.ipady=50;
		c.ipadx=100;
		c.insets=new Insets(15,0,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Desk1Panel.add(deskinfo1,c);
		
		// kiosk2 label
		JPanel Desk2Panel= new JPanel();
		Desk2Panel.setLayout(new GridBagLayout());
		desk2=new JLabel("Kiosk 2:");
		desk2.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(0,0,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Desk2Panel.add(desk2,c);
		
		//String ap = "information  \r\n information  \r\n   oeeoo";
		//display  kiosk2 information
		deskinfo2=new JTextArea("information");
		deskinfo2.setEditable(false);
		deskinfo2.setFont( new Font(Font.MONOSPACED, Font.ITALIC,15));
		deskinfo2.setPreferredSize(new Dimension(120,50));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.ipady=50;
		c.ipadx=100;
		c.insets=new Insets(15,0,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Desk2Panel.add(deskinfo2,c);
		
		//add the kiosk1 and kiosk2 in the desk panel
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(0,0,80,0);
		c.fill= GridBagConstraints.HORIZONTAL;
		DesksPanel.add(Desk1Panel,c);
		c = new GridBagConstraints();
		c.gridx=1;
		c.gridy=0;
		c.insets=new Insets(0,100,80,0);
		c.fill= GridBagConstraints.HORIZONTAL;
		DesksPanel.add(Desk2Panel,c);
		
		JPanel FlightsPanel= new JPanel();
		FlightsPanel.setLayout(new GridBagLayout());
		
		//flight1 constructor
		JPanel Flight1Panel= new JPanel();
		Flight1Panel.setLayout(new GridBagLayout());
		//flight1 label
		flight1=new JLabel("Flight1");
		flight1.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(0,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight1Panel.add(flight1,c);
		//flight1 departure time
		flight1time=new JLabel("Flight1time");
		flight1time.setFont( new Font(Font.MONOSPACED,Font.PLAIN,15));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.insets=new Insets(5,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight1Panel.add(flight1time,c);
		//flight1 passengers on board
		flightinfo1=new JTextField("information",30);
		flightinfo1.setFont( new Font(Font.MONOSPACED, Font.ITALIC,15));
		flightinfo1.setEditable(false);
		//flightinfo1.setPreferredSize(new Dimension(120,50));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=2;
		//c.ipady=50;
		c.ipadx=100;
		c.insets=new Insets(5,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight1Panel.add(flightinfo1,c);
		
		Object [][] b= {
				{"111","Alan"},
				
		};
		//display flight1 table
		
		flight1table=new JTable(modelList1);
		flight1table.setPreferredScrollableViewportSize(new Dimension(30,20));
		flight1table.setEnabled(false);
		flight1table.setFillsViewportHeight(true);
		centerRenderer.setVerticalAlignment(JLabel.CENTER);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		flight1table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		flight1table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		flightList1=new JScrollPane(flight1table);
		flightList1.setPreferredSize(new Dimension(150,40));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=3;
		//c.ipady=50;
		//c.ipadx=100;
		c.insets=new Insets(25,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight1Panel.add(flightList1,c);
		
		JPanel Flight2Panel= new JPanel();
		Flight2Panel.setLayout(new GridBagLayout());
		flight2=new JLabel("Flight2");
		flight2.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(0,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight2Panel.add(flight2,c);
		
		flight2time=new JLabel("Flight2time");
		flight2time.setFont( new Font(Font.MONOSPACED,Font.PLAIN,15));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.insets=new Insets(5,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight2Panel.add(flight2time,c);
		
		flightinfo2=new JTextField("information",30);
		flightinfo2.setFont( new Font(Font.MONOSPACED, Font.ITALIC,15));
		flightinfo2.setEditable(false);
		//flightinfo2.setPreferredSize(new Dimension(120,50));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=2;
		//c.ipady=50;
		c.ipadx=100;
		c.insets=new Insets(5,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight2Panel.add(flightinfo2,c);
		
		flight2table=new JTable(modelList2);
		flight2table.setPreferredScrollableViewportSize(new Dimension(30,20));
		flight2table.setEnabled(false);
		flight2table.setFillsViewportHeight(true);
		centerRenderer.setVerticalAlignment(JLabel.CENTER);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		flight2table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		flight2table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		flightList2=new JScrollPane(flight2table);
		flightList2.setPreferredSize(new Dimension(150,40));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=3;
		//c.ipady=50;
		//c.ipadx=100;
		c.insets=new Insets(25,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight2Panel.add(flightList2,c);
		
		JPanel Flight3Panel= new JPanel();
		Flight3Panel.setLayout(new GridBagLayout());
		flight3=new JLabel("fly");
		flight3.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(0,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight3Panel.add(flight3,c);
		
		flight3time=new JLabel("Flight3time");
		flight3time.setFont( new Font(Font.MONOSPACED,Font.PLAIN,15));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.insets=new Insets(5,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight3Panel.add(flight3time,c);
		
		flightinfo3=new JTextField("20 passengers have been allready on board",30);
		flightinfo3.setFont( new Font(Font.MONOSPACED, Font.ITALIC,15));
		flightinfo3.setEditable(false);
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=2;
		//c.ipady=50;
		c.ipadx=100;
		c.insets=new Insets(5,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight3Panel.add(flightinfo3,c);
		
		flight3table=new JTable(modelList3);
		flight3table.setPreferredScrollableViewportSize(new Dimension(30,20));
		flight3table.setEnabled(false);
		flight3table.setFillsViewportHeight(true);
		centerRenderer.setVerticalAlignment(JLabel.CENTER);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		flight3table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		flight3table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		flightList3=new JScrollPane(flight3table);
		flightList3.setPreferredSize(new Dimension(150,40));
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=3;
		//c.ipady=50;
		//c.ipadx=100;
		c.insets=new Insets(25,20,0,0);
		c.fill= GridBagConstraints.VERTICAL;
		Flight3Panel.add(flightList3,c);
		
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(20,20,100,0);
		c.fill= GridBagConstraints.HORIZONTAL;
		FlightsPanel.add(Flight1Panel,c);
		c = new GridBagConstraints();
		c.gridx=1;
		c.gridy=0;
		c.insets=new Insets(20,20,100,0);
		c.fill= GridBagConstraints.HORIZONTAL;
		FlightsPanel.add(Flight2Panel,c);
		c = new GridBagConstraints();
		c.gridx=2;
		c.gridy=0;
		c.insets=new Insets(20,20,100,0);
		c.fill= GridBagConstraints.HORIZONTAL;
		FlightsPanel.add(Flight3Panel,c);
		
		desk_flights.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.fill= GridBagConstraints.VERTICAL;
		desk_flights.add(DesksPanel,c);
		c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=1;
		c.insets=new Insets(0,0,0,0);
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
		title = new JLabel("Airport Check In Simulator");
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
		spbox1=new JCheckBox("x1");
		spbox2=new JCheckBox("x2");
		spbox3=new JCheckBox("x4");
		ButtonGroup check = new ButtonGroup();
		check.add(spbox1);
		check.add(spbox2);
		check.add(spbox3);
		spbox1.setSelected(true);
		checkboxPanel.add(spbox1);
		checkboxPanel.add(spbox2);
		checkboxPanel.add(spbox3);
		spbox1.addActionListener(this);
		spbox2.addActionListener(this);
		spbox3.addActionListener(this);
		
		//JPanel closepanel=new JPanel();
		stop=new JButton("Stop");
		stop.addActionListener(this);
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
		SouthPanel.add(stop,c);
        this.add(SouthPanel, BorderLayout.SOUTH);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == stop) {
    		SummaryFrame testSummary= new SummaryFrame();
    		this.dispose();
		}	
		if (e.getSource() == spbox1) {
			runtimeSpeed.SetRuntimeSpeed(1);
		}
		if (e.getSource() == spbox2) {
			runtimeSpeed.SetRuntimeSpeed(2);
		}
		if (e.getSource() == spbox3) {
			runtimeSpeed.SetRuntimeSpeed(3);
		}
	}
	public void settheList(Object[][] headofqueue){
		modelList=new  DefaultTableModel(headofqueue, Qlistlabels);
	}
	public void setflightlist1(Object[][] headofqueue){
		modelList1=new  DefaultTableModel(headofqueue, Qlistlabels);
	}
	public void setflightlist2(Object[][] headofqueue){
		modelList2=new  DefaultTableModel(headofqueue, Qlistlabels);
	}
	public void setflightlist3(Object[][] headofqueue){
		modelList3=new  DefaultTableModel(headofqueue, Qlistlabels);
	}
	
	public void setkiosk1info(String kiosk1info) {
		deskinfo1.setText(kiosk1info);
	}
	public void setkiosk2info(String kiosk2info) {
		deskinfo2.setText(kiosk2info);
	}
	public void setflight1time(String flightdep) {
		flight1time.setText(flightdep);
	}
	public void setflight2time(String flightdep) {
		flight2time.setText(flightdep);
	}
	public void setf(String flightdep) {
		flight3time.setText(flightdep);
	}
	public void setpassengersonboard1(String numPassengers) {
		flightinfo1.setText(numPassengers);
	}
	public void setpassengersonboard2(String numPassengers) {
		flightinfo2.setText(numPassengers);
	}
	public void setpassengersonboard3(String numPassengers) {
		flightinfo3.setText(numPassengers);
	}
	public void setqueuestage(String queuestage) {
		queueinfo.setText(queuestage);
	}
	public void setclock(String time) {
		timedisplay.setText(time);
	}
	public void setflight1title(String flighttitle) {
		flight1.setText(flighttitle);
	}
	public void setflight2title(String flighttitle) {
		flight2.setText(flighttitle);
	}
	public void setflight3tilte(String flighttitle) {
		flight3.setText(flighttitle);
	}

}