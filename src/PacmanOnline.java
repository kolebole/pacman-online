import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;

/* File: PacmanOnline.java
 * Start: 2010/06/07
 * Modification: 2010/06/25
 * Description: main entrance of this program.
 */
public class PacmanOnline implements Constants {
	public static PacFrame inst;
	public static GameMap map;
	public static Timer movingTimer;
	
	public static boolean isServer = false;
	public static boolean isReady = false;
	
	public static Socket sTsocket = null;
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Hello,Pacman-Online!");
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				inst = new PacFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.RSPanel.nickField.requestFocusInWindow();

				
				try {
					map = new GameMap();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
	}

}
