import java.awt.*;
import javax.swing.*;

/* File: GamePanel.java
 * Start: 2010/06/15
 * Modification: 2010/06/XX
 * Description: The main game panel on the left.
 */

public class GamePanel extends JPanel {

	GamePanel() {
		GUI();
	}
	
	private void GUI() {
		/* Absolute layout */
		setLayout(null);
		setBackground(Color.black); // so we can "see" the JPanel
		/* setPreferredSize is VERY IMPORTANT. Otherwise the size will be (0,0) */
		setPreferredSize(new Dimension(PacFrame.GAME_WIDTH, PacFrame.GAME_HEIGHT));
		JButton bb = new JButton("Hello game");
		bb.setLocation(500,100);
		bb.setSize(100,50);
		add(bb);				
	}
}
