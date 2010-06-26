import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/* File: Player.java
 * Start: 2010/06/21
 * Modification: 2010/06/25
 * Description: All data that a player have. Include offset, image icon, and current direction
 */
public class Player {
	public final static int PACMAN = 0,
					  		MONSTER = 1;
	public final static int SPEED[] = { 2, 2 };
	public final static int SIZE = 20;
	
	public Player[] playerList;
	public Grid[][] map;
	
	int x, y;
	int gridx, gridy;
	String iconSrc;
	int type;
	int direction;
	int newDirect;

	JLabel LEFT;// 	= new JLabel( new ImageIcon( srcs[0]) );
	JLabel RIGHT;// 	= new JLabel( new ImageIcon( srcs[1]) );
	JLabel UP;// 		= new JLabel( new ImageIcon( srcs[2]) );
	JLabel DOWN;// 	= new JLabel( new ImageIcon( srcs[3]) );
	
	JLabel image;
	
	Player( int x, int y, int type, Grid[][] map, String[] srcs ) {
		LEFT 	= new JLabel( new ImageIcon ( srcs[0] ));
		LEFT.setSize( Player.SIZE, Player.SIZE );
		RIGHT 	= new JLabel( new ImageIcon ( srcs[1] ));
		RIGHT.setSize( Player.SIZE, Player.SIZE );
		UP 		= new JLabel( new ImageIcon ( srcs[2] ));
		UP.setSize( Player.SIZE, Player.SIZE );
		DOWN 	= new JLabel( new ImageIcon ( srcs[3] ));
		DOWN.setSize( Player.SIZE, Player.SIZE );
		this.x = x;
		this.y = y;
		this.type = type;
		direction = newDirect = KeyEvent.VK_RIGHT;
		this.map = map;
		updateGrid();
	}
	
	public void changeDir(int Direction){
		image.setVisible(false);
		switch(Direction){
			case KeyEvent.VK_UP:
				image = UP;
				break;
			case KeyEvent.VK_DOWN:
				image = DOWN;
				break;
			case KeyEvent.VK_LEFT:
				image = LEFT;
				break;
			case KeyEvent.VK_RIGHT:
				image = RIGHT;
				break;
				////////////////////
			case KeyEvent.VK_W:
				image = UP;
				break;
			case KeyEvent.VK_S:
				image = DOWN;
				break;
			case KeyEvent.VK_A:
				image = LEFT;
				break;
			case KeyEvent.VK_D:
				image = RIGHT;
				break;
				////////////////////
			default:
				image = null;
		}
		image.setVisible(true);
	}
	
	public void move(){
		testMove(true, false);
		if ( isTouchWall() ) {
			// Test move back
			testMove(true, true);
			// keep old direction
			testMove(false, false);
						
		}else if( direction != newDirect ){
			direction = newDirect;
			changeDir(direction);
		}
		
	}
	
	public void testMove( boolean test, boolean back ){
		int speed = back ? -Player.SPEED[type] : Player.SPEED[type] ;
		int d = test ? newDirect : direction;
		switch( d ){
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
		updateGrid();
		
	}
	
	public void updateGrid( ){
		final double FLU = 0.9, FRD = 1-FLU, FNW = 0.5;
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
	
	public void addRef( Player[] playerList ){
		this.playerList = playerList;
	}
	
	public boolean isTouchWall(){
		
		return map[gridx][gridy].content == Grid.WALL;
	}
	
	public void checkTouch () {
		switch ( map[gridx][gridy].content ){
		case Grid.NONE:
			// do nothing
			break;
		case Grid.BEAN:
			if ( type == PACMAN ){
				// TODO pacman get the credit
				map[gridx][gridy].content = Grid.NONE;
				map[gridx][gridy].image.setVisible(false);
			}else if ( type == MONSTER ){
				// do nothing
			}else {
				// error
			}
			break;
		case Grid.WALL:
			// TODO can't move forward
			testMove(false, true);
			break;
		default:
			// error
		}
		if ( type == MONSTER ) {
			for ( int j = 0 ; j < playerList.length ; j++ ){
				if ( gridx == playerList[j].gridx && 
					 gridy == playerList[j].gridy && 
					 playerList[j].type == Player.PACMAN ){
					// TODO Monster eat Pacman
						
				}
			}
		}
	}
	
	
}
