/* File: Utility.java
 * Start: 2010/06/14
 * Modification: 2010/06/XX
 * Description: class with utility functions
 */

import java.awt.*;
import javax.swing.*;

class Utility {
	/* Print an exception */ 
	public static void error(Exception e) {
		System.err.println(e.toString());
	}
	
	/* Print an error message */
	public static void error(String error) {
		System.err.println("Error: " + error);
	}
	
	/* Report an unknown state */
	public static void unknown(Container c) {
		JOptionPane.showMessageDialog(c, "Should NOT reach this state!!!",
				"Fatal Error", JOptionPane.ERROR_MESSAGE);
	}
}
