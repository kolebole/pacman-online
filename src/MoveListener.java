import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class MoveListener implements KeyListener {
	PacFrame frame = PacmanOnline.inst;
	Player[] playerList = PacmanOnline.map.playerList;
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch( keyCode ){
		case KeyEvent.VK_UP:
			playerList[0].direction = KeyEvent.VK_UP;
			playerList[0].y--;
			break;
		case KeyEvent.VK_DOWN:
			playerList[0].direction = KeyEvent.VK_DOWN;
			playerList[0].y++;
			break;
		case KeyEvent.VK_LEFT:
			playerList[0].direction = KeyEvent.VK_LEFT;
			playerList[0].x--;
			break;
		case KeyEvent.VK_RIGHT:
			playerList[0].direction = KeyEvent.VK_RIGHT;
			playerList[0].x++;
			break;
		default:
			// error
		}
		System.out.println("Player move to: " + playerList[0].x + ", " + playerList[0].y);
		frame.placePlayer(playerList);
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
