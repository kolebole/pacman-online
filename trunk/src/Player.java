import java.awt.event.KeyEvent;

import javax.swing.JLabel;


public class Player {
	public static int PACMAN = 0,
					  MONSTER = 1;
	
	int x, y;
	String iconSrc;
	int type;
	int direction;
	JLabel image;
	
	Player( int x, int y, int type ) {
		this.x = x;
		this.y = y;
		this.type = type;
		direction = KeyEvent.VK_LEFT;
	}
}
