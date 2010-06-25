import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/* File: MovingListener.java
 * Start: 2010/06/21
 * Modification: 2010/06/25
 * Description: Used by movingTimer in PacmanOnline.java.
 */
public class MovingListener implements ActionListener {
	PacFrame frame = PacmanOnline.inst;
	GameMap map = PacmanOnline.map;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for ( int i = 0 ; i < map.playerList.length ; i++ ){
			moving( map.playerList[i] );
			map.playerList[i].updateGrid();
		}
		frame.movePlayer(map.playerList);

	}
	
	private void moving( Player player ){
		int x = player.x,
			y = player.y;
		switch( player.direction ){
		case KeyEvent.VK_UP:
			player.y--;
			break;
		case KeyEvent.VK_DOWN:
			player.y++;
			break;
		case KeyEvent.VK_LEFT:
			player.x--;
			break;
		case KeyEvent.VK_RIGHT:
			player.x++;
			break;
		default:
			System.out.println("Direction is " + player.direction );
		}
		
		if ( player.x < 0 || player.y < 0 || 
		     player.x > PacFrame.GAME_WIDTH - Player.SIZE || player.y > PacFrame.GAME_HEIGHT - Player.SIZE ){
			player.x = x;
			player.y = y;
		}
	}

}
