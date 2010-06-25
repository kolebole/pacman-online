import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

/* File: PacmanOnline.java
 * Start: 2010/06/07
 * Modification: 2010/06/XX
 * Description: Sample code for coding style.
 */
public class PacmanOnline {
	public static PacFrame inst;
	public static GameMap map;
	public static Timer movingTimer;
	
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
				
				try {
					map = new GameMap();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				inst.gamePanel.addKeyListener(new DirectionListener());
				KeyListener[] kls = inst.getKeyListeners();
				System.out.println(kls);
				
				movingTimer = new Timer( 20, new MovingListener() );
				movingTimer.start();
				
			}
		});
		
	}

}
