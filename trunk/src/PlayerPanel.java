import java.awt.*;

import javax.swing.*;


public class PlayerPanel extends JPanel {
	JLabel[] pictureList;
	JLabel[] nickList;
	int listLen;
	public PlayerPanel( int num ){
		super(new GridBagLayout());
		pictureList = new JLabel[num];
		nickList = new JLabel[num];
		listLen = num;
		GUI();
	}
	
	private void GUI(){
		int i;
		GridBagConstraints gbc = new GridBagConstraints();
		for ( i = 0 ; i < listLen ; i++ ){
			pictureList[i] = new JLabel("pic"+i);
			nickList[i] = new JLabel("Player #"+i+", your nickname will show here.");
			gbc.gridx = 0;
			gbc.gridy = i;
			this.add(new JButton("pic"+i), gbc );
			gbc.gridx = 1;
			gbc.gridwidth = 2;
			this.add(new JButton("Player #"+i+", your nickname will show here."), gbc );
		}
	}
}
