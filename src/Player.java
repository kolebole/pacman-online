import java.awt.event.KeyEvent;

import javax.swing.JLabel;

/* File: Player.java
 * Start: 2010/06/21
 * Modification: 2010/06/25
 * Description: All data that a player have. Include offset, image icon, and current direction
 */
public class Player {
	public final static int PACMAN = 0,
					  		MONSTER = 1;
	public final static int SPEED[] = { 2, 3 };
	public final static int SIZE = 20;
	
	int x, y;
	int gridx, gridy;
	String iconSrc;
	int type;
	int direction;
	JLabel image;
	
	Player( int x, int y, int type ) {
		this.x = x;
		this.y = y;
		this.type = type;
		direction = KeyEvent.VK_RIGHT;
		updateGrid();
	}
	
	public void move( boolean back ){
		int speed = back ? -Player.SPEED[type] : Player.SPEED[type] ;
		switch( direction ){
		case KeyEvent.VK_UP:
			y = y - speed;
			if ( y < 0)
				y = 0;
			break;
		case KeyEvent.VK_DOWN:
			y = y + speed;
			if ( y > PacFrame.GAME_HEIGHT - Player.SIZE )
				y = PacFrame.GAME_HEIGHT - Player.SIZE;
			break;
		case KeyEvent.VK_LEFT:
			x = x - speed;
			if ( x < 0)
				x = 0;
			break;
		case KeyEvent.VK_RIGHT:
			x = x + speed;
			if ( x > PacFrame.GAME_WIDTH - Player.SIZE )
				x = PacFrame.GAME_WIDTH - Player.SIZE;
			break;
		default:
			System.out.println("Direction is " + direction );
		}
		
	}
	
	public void updateGrid(){
		int offsetx	= x % SIZE,
			offsety = y % SIZE;
		gridx = x / SIZE;
		gridy = y / SIZE;
		if ( offsetx > SIZE * 0.5 )
			gridx++;
		if ( offsety > SIZE * 0.5 )
			gridy++;
	}
}
