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
			map.playerList[i].move(false);
			map.playerList[i].updateGrid();
		}
		map.checkTouch();
		frame.movePlayer(map.playerList);

	}
	
	private void moving( Player player ){
		switch( player.direction ){
		case KeyEvent.VK_UP:
			player.y = player.y - Player.SPEED[player.type];
			if ( player.y < 0)
				player.y = 0;
			break;
		case KeyEvent.VK_DOWN:
			player.y = player.y + Player.SPEED[player.type];
			if ( player.y > PacFrame.GAME_HEIGHT - Player.SIZE )
				player.y = PacFrame.GAME_HEIGHT - Player.SIZE;
			break;
		case KeyEvent.VK_LEFT:
			player.x = player.x - Player.SPEED[player.type];
			if ( player.x < 0)
				player.x = 0;
			break;
		case KeyEvent.VK_RIGHT:
			player.x = player.x + Player.SPEED[player.type];
			if ( player.x > PacFrame.GAME_WIDTH - Player.SIZE )
				player.x = PacFrame.GAME_WIDTH - Player.SIZE;
			break;
		default:
			System.out.println("Direction is " + player.direction );
		}
		
	}

}
