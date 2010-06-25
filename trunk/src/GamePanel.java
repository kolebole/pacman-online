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
		"pics/credit.gif",
		"pics/pac.jpeg"
	};

	GamePanel() {
		GUI();
		
	}
	
	public void initMap( Grid[][] map ) {
		int i, j;
		JLabel temp;
		for ( i = 0 ; i < GameMap.WIDTH ; i++ ){
			for ( j = 0 ; j < GameMap.HEIGHT ; j++ ){
				if ( map[i][j].content > 0 ) {
					temp = new JLabel( new ImageIcon(srcs[map[i][j].content-1]) );
					temp.setLocation( map[i][j].x ,map[i][j].y );
					temp.setSize( Player.SIZE, Player.SIZE );
					add(temp);
					map[i][j].setImage( temp );
				}
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
			temp.setSize( Player.SIZE, Player.SIZE );
			add( temp );
			playerList[i].image = temp;
		}
	}
	
	public void movePlayer( Player player ){
		player.image.setLocation( player.x, player.y );
	}
	
	private void GUI() {
		/* Absolute layout */
		setLayout(null);
		setBackground(Color.black); // so we can "see" the JPanel
		/* setPreferredSize is VERY IMPORTANT. Otherwise the size will be (0,0) */
		setPreferredSize(new Dimension(PacFrame.GAME_WIDTH, PacFrame.GAME_HEIGHT));
		setFocusable(true);
		/*JButton bb = new JButton("Hello game");
		bb.setLocation(500,100);
		bb.setSize(100,50);
		add(bb);*/				
	}
}
