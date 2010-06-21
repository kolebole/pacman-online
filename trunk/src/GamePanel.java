import java.awt.*;
import javax.swing.*;

/* File: GamePanel.java
 * Start: 2010/06/15
 * Modification: 2010/06/XX
 * Description: The main game panel on the left.
 */

public class GamePanel extends JPanel {
	String[] srcs = {
		"pics/wall.jpeg",
		"pics/credit.jpeg",
		"pics/pac.jpeg"
	};

	GamePanel() {
		GUI();
		
	}
	
	public void initMap( Grid[][] map ) {
		int i, j;
		JLabel temp;
		for ( i = 0 ; i < GameMap.HEIGHT ; i++ ){
			for ( j = 0 ; j < GameMap.WIDTH ; j++ ){
				temp = new JLabel( new ImageIcon(srcs[map[i][j].content-1]) );
				temp.setLocation( map[i][j].x ,map[i][j].y );
				temp.setSize( 20, 20 );
				//System.out.print(map[i][j].content + " ");
				//add(temp);
			}
			//System.out.println();
		}
	}
	
	public void placePlayer( Player[] playerList ){
		int i;
		JLabel temp;
		for ( i = 0 ; i < playerList.length ; i++ ){
			temp = new JLabel( new ImageIcon(srcs[2]) );
			temp.setLocation(playerList[i].x, playerList[i].y);
			temp.setSize( 20, 20 );
			add( temp );
		}
	}
	
	private void GUI() {
		/* Absolute layout */
		setLayout(null);
		setBackground(Color.black); // so we can "see" the JPanel
		/* setPreferredSize is VERY IMPORTANT. Otherwise the size will be (0,0) */
		setPreferredSize(new Dimension(PacFrame.GAME_WIDTH, PacFrame.GAME_HEIGHT));
		/*JButton bb = new JButton("Hello game");
		bb.setLocation(500,100);
		bb.setSize(100,50);
		add(bb);*/				
	}
}
