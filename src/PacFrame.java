

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
			setSize(1000, 1000);
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
		PlayerPanel RNPanel = new PlayerPanel(),
			RCPanel = new PlayerPanel();
		ConnectPanel RSPanel = new ConnectPanel();
		//LPanel.setLayout(null);
		//LPanel.add(new JLabel("Game Area"));
		LPanel.add(new JTextArea(30,30));
		RPanel.add(RNPanel, "North");
		RPanel.add(RCPanel, "Center");
		RPanel.add(RSPanel, "South");
		pane.add(LPanel);
		pane.add(RPanel);
		
	}

}
