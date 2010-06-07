

import java.awt.*;

import javax.swing.*;

public class PacFrame extends JFrame {

		
	public PacFrame() {
		super("Pacman Online");
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			pack();
			setSize(800, 600);
			//setResizable(false);
			createGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createGUI(){
		Container pane = this.getContentPane();
		pane.setLayout(new FlowLayout());
		JPanel LPanel = new JPanel(), 
			   RPanel = new JPanel(new BorderLayout());
		PlayerPanel pacmanPanel = new PlayerPanel( 0, 4 ),
					monsterPanel = new PlayerPanel( 1, 4 );
		ConnectPanel RSPanel = new ConnectPanel();
		//LPanel.setLayout(null);
		//LPanel.add(new JLabel("Game Area"));
		LPanel.add(new JTextArea(30,30));
		// Basic Layout
		RPanel.add(pacmanPanel, "North");
		RPanel.add(monsterPanel, "Center");
		RPanel.add(RSPanel, "South");
		pane.add(LPanel);
		pane.add(RPanel);
		
	}

}
