

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

public class PacFrame extends javax.swing.JFrame {

		
	public PacFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
