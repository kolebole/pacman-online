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
	
	public Grid[][] map;
	
	int x, y;
	int gridx, gridy;
	String iconSrc;
	int type;
	int direction;
	JLabel image;
	
	Player( int x, int y, int type, Grid[][] map ) {
		this.x = x;
		this.y = y;
		this.type = type;
		direction = KeyEvent.VK_RIGHT;
		this.map = map;
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
	
	public void updateGrid( ){
		final double FLU = 0.99, FRD = 1-FLU, FNW = 0.5;
		int offsetx	= x % SIZE,
			offsety = y % SIZE;
		int gt[][] = new int[2][2];
		double xfactor, yfactor;
		
		gridx = x / SIZE;
		gridy = y / SIZE;
		//System.out.println(map);
		gt[0][0] = map[gridx][gridy].content;
		gt[1][0] = map[gridx+1][gridy].content;
		gt[0][1] = map[gridx][gridy+1].content;
		gt[1][1] = map[gridx+1][gridy+1].content;
		
		if ( gt[0][0] == Grid.WALL && gt[0][1] == Grid.WALL )
			xfactor = FLU;
		else if ( gt[1][0] == Grid.WALL && gt[1][1] == Grid.WALL )
			xfactor = FRD;
		else
			xfactor = FNW;
		
		if ( gt[0][0] == Grid.WALL && gt[1][0] == Grid.WALL )
			yfactor = FLU;
		else if ( gt[0][1] == Grid.WALL && gt[1][1] == Grid.WALL )
			yfactor = FRD;
		else
			yfactor = FNW;
		
		if ( gt[0][0] == Grid.WALL && 
			 gt[1][0] != Grid.WALL && 
			 gt[0][1] != Grid.WALL &&
			 gt[1][1] != Grid.WALL ){
			if ( offsetx > SIZE * FLU )
				yfactor = FNW;
			else 
				yfactor = FLU;
			
			if ( offsety > SIZE * FLU )
				xfactor = FNW;
			else
				xfactor = FLU;
		}
		
		if ( gt[0][0] != Grid.WALL && 
			 gt[1][0] == Grid.WALL && 
			 gt[0][1] != Grid.WALL &&
			 gt[1][1] != Grid.WALL ){
			if ( offsetx > SIZE * FRD )
				yfactor = FLU;
			else 
				yfactor = FNW;
			
			if ( offsety > SIZE * FLU )
				xfactor = FNW;
			else
				xfactor = FRD;
		}
		
		if ( gt[0][0] != Grid.WALL && 
			 gt[1][0] != Grid.WALL && 
			 gt[0][1] == Grid.WALL &&
			 gt[1][1] != Grid.WALL ){
			if ( offsetx > SIZE * FLU )
				yfactor = FNW;
			else 
				yfactor = FRD;
			
			if ( offsety > SIZE * FRD )
				xfactor = FLU;
			else
				xfactor = FNW;
		}
		
		if ( gt[0][0] != Grid.WALL && 
			 gt[1][0] != Grid.WALL && 
			 gt[0][1] != Grid.WALL &&
			 gt[1][1] == Grid.WALL ){
			if ( offsetx > SIZE * FRD )
				yfactor = FRD;
			else 
				yfactor = FNW;
			
			if ( offsety > SIZE * FRD )
				xfactor = FRD;
			else
				xfactor = FNW;
		}
		
		if ( offsetx > SIZE * xfactor )
			gridx++;
		if ( offsety > SIZE * yfactor )
			gridy++;
	}
}
