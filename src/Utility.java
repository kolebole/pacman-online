/* File: Utility.java
 * Start: 2010/06/14
 * Modification: 2010/06/XX
 * Description: class with utility functions
 */

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;

class Utility implements Messages {
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

	/* Play an .wav audio file 
	 * Example: http://tinyurl.com/2cqs44x
	 * */
	public static void playSound(String path) {
		try {
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File(path));
			DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
			Clip clip = (Clip) AudioSystem.getLine(info);
			System.out.println("audio 1");
			// load into memory
			clip.open(sound);
			System.out.println("audio 2");
			// play the sound
			clip.start();			
			System.out.println("audio 3");
		}
		catch (Exception e) {
			Utility.error(e);
		}
	}
	
	/* Check whether the input is START_COMMAND */
	public static boolean checkStartCommand(int msg, Container c) {
		if (msg != START_COMMAND) {
			JOptionPane.showMessageDialog(c, "Expecting \"START_COMMAND\"",
					"Fatal Error", JOptionPane.ERROR_MESSAGE);			
			return false;
		}
		else {
			return true;
		}
	}
	
	/* Check whether the input is START_MESSAGE */
	public static boolean checkStartMessage(int msg, Container c) {
		if (msg != START_MESSAGE) {
			JOptionPane.showMessageDialog(c, "Expecting \"START_MESSAGE\"",
					"Fatal Error", JOptionPane.ERROR_MESSAGE);			
			return false;
		}
		else {
			return true;
		}
	}
	

}
