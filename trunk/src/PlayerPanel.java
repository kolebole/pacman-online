import java.awt.*;

import javax.swing.*;


public class PlayerPanel extends JPanel {
	JLabel[] pictureList;
	JLabel[] nickList;
	int listLen;
	int panelType;
	String[] titles = {
			"Pacman List",
			"Monster List"
	};
	public PlayerPanel( int type, int num ){
		super(new GridBagLayout());
		pictureList = new JLabel[num];
		nickList = new JLabel[num];
		listLen = num;
		panelType = type;
		GUI();
	}
	
	private void GUI(){
		int i;
		GridBagConstraints gbc = new GridBagConstraints();
		for ( i = 0 ; i < listLen ; i++ ){
			pictureList[i] = new JLabel("pic"+i);
			nickList[i] = new JLabel("Player #"+i, SwingConstants.RIGHT);//+", your nickname will show here.");
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.weightx = 0.5;
			this.add(new JButton("pic"+i), gbc );
			
			gbc.gridx = 2;
			gbc.gridwidth = 2;
			this.add(nickList[i], gbc );
		}
		setBorder(BorderFactory.createTitledBorder(titles[panelType]));
	}
}
