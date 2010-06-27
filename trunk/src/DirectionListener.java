import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/* File: DirectionListener.java
 * Start: 2010/06/25
 * Modification: 2010/06/25
 * Description: Active when arrow key is pressed to change direction of controlled player.
 */
public class DirectionListener implements KeyListener {
	PacFrame frame = PacmanOnline.inst;
	Player[] playerList = PacmanOnline.map.playerList;
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		char team = TeamManager.vector.get(0).team;
		int number = TeamManager.vector.get(0).picIndex;
		Player player = playerList[team*4+number];
		switch( keyCode ){
		case KeyEvent.VK_UP:
			player.newDirect = KeyEvent.VK_UP;
			break;
		case KeyEvent.VK_DOWN:
			player.newDirect = KeyEvent.VK_DOWN;
			break;
		case KeyEvent.VK_LEFT:
			player.newDirect = KeyEvent.VK_LEFT;
			break;
		case KeyEvent.VK_RIGHT:
			player.newDirect = KeyEvent.VK_RIGHT;
			break;

		default:
			System.out.println("KeyCode is " + keyCode );
		}
		System.out.println("Player move to: " + player.x + ", " + player.y);
		System.out.println("Player move to: " + player.gridx + ", " + player.gridy);
		//frame.movePlayer(playerList);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
