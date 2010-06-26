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
		switch( keyCode ){
		case KeyEvent.VK_UP:
			playerList[0].newDirect = KeyEvent.VK_UP;
/*			playerList[1].direction = KeyEvent.VK_UP;
			playerList[1].changeDir(KeyEvent.VK_UP);
			playerList[2].direction = KeyEvent.VK_UP;
			playerList[2].changeDir(KeyEvent.VK_UP);
			playerList[3].direction = KeyEvent.VK_UP;
			playerList[3].changeDir(KeyEvent.VK_UP);*/
			break;
		case KeyEvent.VK_DOWN:
			playerList[0].newDirect = KeyEvent.VK_DOWN;
/*			playerList[1].direction = KeyEvent.VK_DOWN;
			playerList[1].changeDir(KeyEvent.VK_DOWN);
			playerList[2].direction = KeyEvent.VK_DOWN;
			playerList[2].changeDir(KeyEvent.VK_DOWN);
			playerList[3].direction = KeyEvent.VK_DOWN;
			playerList[3].changeDir(KeyEvent.VK_DOWN);*/
			break;
		case KeyEvent.VK_LEFT:
			playerList[0].newDirect = KeyEvent.VK_LEFT;
/*			playerList[1].direction = KeyEvent.VK_LEFT;
			playerList[1].changeDir(KeyEvent.VK_LEFT);
			playerList[2].direction = KeyEvent.VK_LEFT;
			playerList[2].changeDir(KeyEvent.VK_LEFT);
			playerList[3].direction = KeyEvent.VK_LEFT;
			playerList[3].changeDir(KeyEvent.VK_LEFT);*/
			break;
		case KeyEvent.VK_RIGHT:
			playerList[0].newDirect = KeyEvent.VK_RIGHT;
/*			playerList[1].direction = KeyEvent.VK_RIGHT;
			playerList[1].changeDir(KeyEvent.VK_RIGHT);
			playerList[2].direction = KeyEvent.VK_RIGHT;
			playerList[2].changeDir(KeyEvent.VK_RIGHT);
			playerList[3].direction = KeyEvent.VK_RIGHT;
			playerList[3].changeDir(KeyEvent.VK_RIGHT);*/
			break;
			///////////////////////////////////////////
		case KeyEvent.VK_W:
			playerList[4].newDirect = KeyEvent.VK_UP;
/*			playerList[5].direction = KeyEvent.VK_UP;
			playerList[5].changeDir(KeyEvent.VK_W);
			playerList[6].direction = KeyEvent.VK_UP;
			playerList[6].changeDir(KeyEvent.VK_W);
			playerList[7].direction = KeyEvent.VK_UP;
			playerList[7].changeDir(KeyEvent.VK_W);*/
			break;
		case KeyEvent.VK_S:
			playerList[4].newDirect = KeyEvent.VK_DOWN;
/*			playerList[5].direction = KeyEvent.VK_DOWN;
			playerList[5].changeDir(KeyEvent.VK_S);
			playerList[6].direction = KeyEvent.VK_DOWN;
			playerList[6].changeDir(KeyEvent.VK_S);
			playerList[7].direction = KeyEvent.VK_DOWN;
			playerList[7].changeDir(KeyEvent.VK_S);*/
			break;
		case KeyEvent.VK_A:
			playerList[4].newDirect = KeyEvent.VK_LEFT;
/*			playerList[5].direction = KeyEvent.VK_LEFT;
			playerList[5].changeDir(KeyEvent.VK_A);
			playerList[6].direction = KeyEvent.VK_LEFT;
			playerList[6].changeDir(KeyEvent.VK_A);
			playerList[7].direction = KeyEvent.VK_LEFT;
			playerList[7].changeDir(KeyEvent.VK_A);*/
			break;
		case KeyEvent.VK_D:
			playerList[4].newDirect = KeyEvent.VK_RIGHT;
/*			playerList[5].direction = KeyEvent.VK_RIGHT;
			playerList[5].changeDir(KeyEvent.VK_D);
			playerList[6].direction = KeyEvent.VK_RIGHT;
			playerList[6].changeDir(KeyEvent.VK_D);
			playerList[7].direction = KeyEvent.VK_RIGHT;
			playerList[7].changeDir(KeyEvent.VK_D);*/
			break;
			///////////////////////////////////////////
		default:
			System.out.println("KeyCode is " + keyCode );
		}
		System.out.println("Player move to: " + playerList[0].x + ", " + playerList[0].y);
		System.out.println("Player move to: " + playerList[0].gridx + ", " + playerList[0].gridy);
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
