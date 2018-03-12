/**
 * 
 */
package src.uk.ac.hw.F21AS.GROUPms256as294pt45.CoreStage2;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

/**
 * @author 
 *this is the main frame of stage 2 which shows the events online
 */
public class AutomatedFrame extends JFrame {
	private DataModel model; 
	/**
	 * @throws HeadlessException
	 */
	public AutomatedFrame(DataModel givenModel) throws HeadlessException {
		// TODO Auto-generated constructor stub
		model=givenModel;
	}

	/**
	 * @param gc
	 */
	public AutomatedFrame(GraphicsConfiguration gc) {
		super(gc);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public AutomatedFrame(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param title
	 * @param gc
	 */
	public AutomatedFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}

}
