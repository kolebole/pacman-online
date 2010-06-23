

import java.awt.*;

import javax.swing.*;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class PacFrame extends JFrame {
	public JTextField msgField;
	public GamePanel gamePanel;
	
	/* Define some size constants */
	private final int FRAME_WIDTH = 1000;
	private final int FRAME_HEIGHT = 650;
	public static final int GAME_WIDTH = 700;
	public static final int GAME_HEIGHT = 500;
		
	public PacFrame() {
		super("Pacman Online");
		initGUI();
	}
	
	public void initGameMap( Grid[][] map ) {
		gamePanel.initMap( map );
	}
	
	public void placePlayer( Player[] playerList ){
		gamePanel.placePlayer( playerList );
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			pack();
			setSize(FRAME_WIDTH, FRAME_HEIGHT);
			setResizable(false);
			createGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createGUI(){
		Container pane = this.getContentPane();
		pane.setLayout(new FlowLayout());
		/* Frame adds those big panels */
		JPanel LPanel = new JPanel(new BorderLayout()), 
			   RPanel = new JPanel(new BorderLayout());
		pane.add(LPanel);
		pane.add(new JSeparator(SwingConstants.VERTICAL));
		pane.add(RPanel);

		/*** New the 4 panel objects and add them to the big panels ***/
		gamePanel = new GamePanel();
		PlayerPanel pacmanPanel = new PlayerPanel( 0, 4 ),
					monsterPanel = new PlayerPanel( 1, 4 );
		ConnectPanel RSPanel = new ConnectPanel();


		/* left part */
		LPanel.add(gamePanel, "North");
		LPanel.add(new JSeparator(SwingConstants.HORIZONTAL), "Center");		
		JPanel LSPanel = new JPanel(new BorderLayout());
		LPanel.add(LSPanel,"South");
		LSPanel.add(new JLabel("Message:"), "North");
		msgField = new JTextField();
		msgField.setEditable(false);
		msgField.setPreferredSize(new Dimension(PacFrame.GAME_WIDTH,40));
		msgField.setFont(new Font("Arial", Font.PLAIN, 16));
		LSPanel.add(msgField,"South");

		/* right part */

		RPanel.add(pacmanPanel, "North");
		RPanel.add(monsterPanel, "Center");
		RPanel.add(RSPanel, "South");
	}
}
