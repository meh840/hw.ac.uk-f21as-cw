package check_in;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The class is a Kioks GUI frame. It is a frame which displays the amount of 
 * money the user is called to pay extra.
 * It is used for payments.
 * @author Panagiotis Tsamoutalis (pt45)
 *
 */
public class KioskPayment extends JFrame  implements ActionListener{
	
	JButton cancel, pay;
	JLabel title, amountLabel, payLabel,paymentLabel;
	JCheckBox cashbox, cardbox;
	/**
	 * Constructor of the Kiosk GUI payment class.
	 * @param flightlist from FlightLoader.
	 * @param ErrosList from ErrorLogger.
	 */
	
	public KioskPayment() {
    	
    	setTitle("KIOSK");
    	setSize(800,500);
    	setResizable(false);
		setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
 
		setupSouthPanel();
		setupNorthPanel();
		setupCenterPanel();
		
        //pack and set visible
        //pack();
        setVisible(true);	
    }
	/**
	 * It handles the center panel of the GUI
	 * It displays all the information about the payment.
	 * You can choose to pay by cash or card.
	 */

	private void setupCenterPanel() {
		// TODO Auto-generated method stub

		JPanel mainPanel1 = new JPanel();
		mainPanel1.setLayout(new GridLayout(3,1));
		// Creates the pay label.
		JPanel payPanel = new JPanel();
		payPanel.setLayout(new FlowLayout());
		payLabel= new JLabel("REFERENCE NUMBER");
		payLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		payPanel.add(payLabel);
		// Creates the amount label.
		JPanel amountPanel = new JPanel();
		amountPanel.setLayout(new FlowLayout());
		amountLabel= new JLabel("REFERENCE NUMBER");
		amountLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		amountPanel.add(amountLabel);
		// Creates the panel which you choose how you want to pay.
		JPanel paymentPanel = new JPanel();
		paymentPanel.setLayout(new GridLayout(2,1));
		paymentLabel= new JLabel("Pay by :");
		paymentLabel.setFont( new Font(Font.MONOSPACED, Font.BOLD,18));
		JPanel ccPanel=new JPanel();
		ccPanel.setLayout(new FlowLayout(FlowLayout.LEADING,50,10));
		cashbox= new JCheckBox("Cash");
		cardbox= new JCheckBox("Credit Card");
		ButtonGroup ccgroup=new ButtonGroup();
		ccgroup.add(cashbox);
		ccgroup.add(cardbox);
		ccPanel.add(cashbox);
		ccPanel.add(cardbox);
		paymentPanel.add(paymentLabel);
		paymentPanel.add(ccPanel);
		
		
		mainPanel1.add(payPanel);
		mainPanel1.add(amountPanel);
		mainPanel1.add(paymentPanel);
		
		JPanel centerPanel = new JPanel();
        centerPanel.add(mainPanel1);
        this.add(centerPanel,BorderLayout.CENTER);
		
	}
	/**
	 * It handles the north panel of the GUI.
	 * @return A label as the title of the GUI
	 */
	private void setupNorthPanel() {
		// TODO Auto-generated method stub
		JPanel titlePanel = new JPanel();
		title = new JLabel("MAKE YOUR PAYMENT");
		title.setFont( new Font(Font.MONOSPACED, Font.BOLD,30));
        titlePanel.add(title); 
        JPanel northPanel = new JPanel();
        northPanel.add(titlePanel);
        this.add(northPanel, BorderLayout.NORTH);
	}
	/**
	 * It handles the south panel of the GUI.
	 * @return A Pay button for the payment.
	 * @return A Cancel button to dispose the GUI.
	 */
	private void setupSouthPanel() {
		// TODO Auto-generated method stub
		JPanel southPanel = new JPanel();
		 pay = new JButton("Pay");
		 pay.addActionListener(this);
	        
	     cancel = new JButton("Cancel");
	     cancel.addActionListener(this);
	      
	     southPanel.setLayout(new FlowLayout(FlowLayout.CENTER,100,20));
	     southPanel.add (pay);
	     southPanel.add(cancel);
	     this.add(southPanel, BorderLayout.SOUTH);
		
	}
	/**
	 * it handles the buttons Pay and Cancel.
	 * Pay button send you to the search engine GUI by checking in.
	 * Cancel button send to the search engine GUI without checking in.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == pay) {
			if (cashbox.isSelected() || cardbox.isSelected()) {
				JOptionPane.showMessageDialog(this, 
   				 " The payment has complited,thank you. Have a nice flight");
				this.setVisible(false);
				KioskSearch abc=new KioskSearch();
			}else {
				JOptionPane.showMessageDialog(this, 
		   				 "Please pick the way you want to pay");
			}
			
    	}
		else if (e.getSource() == cancel) {
			int  yes_noButton = JOptionPane.YES_NO_OPTION;
    		int Buttonresult=JOptionPane.showConfirmDialog(this, 
    				 " Do you want to cancel the payment?", "Choose",yes_noButton);
    		if(Buttonresult==0) {
    			JOptionPane.showMessageDialog(this, 
       				 "Cheking in has faild");
    			KioskSearch abc=new KioskSearch();
    			this.dispose();
    		}
    		
    	}
	}
	

}
