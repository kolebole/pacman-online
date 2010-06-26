/* File: PlayerPanel.java
 * Start: 2010/06/07
 * Modification: 2010/06/26
 * Description: Panel for listing players of one team (Pacmans or Monsters).
 */
import java.awt.*;
import javax.swing.*;


public class PlayerPanel extends JPanel implements Constants {
	JButton[] picList;
	JLabel[] nickList;
	int listLen;
	char panelType; // false: pacmans, true: monsters
	String[] titles = {
			"Pacman List",
			"Monster List"
	};
	public PlayerPanel( char type, int num ){
		super(new GridBagLayout());
		picList = new JButton[num];
		nickList = new JLabel[num];
		listLen = num;
		panelType = type;
		GUI();
	}
	
	private void GUI(){
		int i;
		GridBagConstraints gbc = new GridBagConstraints();
		for ( i = 0 ; i < listLen ; i++ ){
			if (panelType == PACMANS) {
				picList[i] = new JButton(new ImageIcon(pacs[i][1]));
			}
			else {
				picList[i] = new JButton(new ImageIcon(mons[i][3]));
			}
			nickList[i] = new JLabel("Player #"+i, SwingConstants.RIGHT);//+", your nickname will show here.");
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.weightx = 0.5;
			this.add(picList[i], gbc );
			
			gbc.gridx = 2;
			gbc.gridwidth = 2;
			this.add(nickList[i], gbc );
		}
		setBorder(BorderFactory.createTitledBorder(titles[panelType]));
	}
}
