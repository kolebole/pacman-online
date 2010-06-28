import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/* File: DirectionListener.java
 * Start: 2010/06/25
 * Modification: 2010/06/25
 * Description: Active when arrow key is pressed to change direction of controlled player.
 */
public class DirectionListener implements KeyListener, Messages {
	PacFrame frame = PacmanOnline.inst;
	Player[] playerList = PacmanOnline.map.playerList;
	InputStream cin = null;
	PrintWriter cout = null;
	
	DirectionListener(){
		try {
			cin = PacmanOnline.sTsocket.getInputStream();
			cout = new PrintWriter(PacmanOnline.sTsocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		char team = TeamManager.vector.get(0).team;
		int number = TeamManager.vector.get(0).picIndex;
		Player player = playerList[team*4+number];
		switch( keyCode ){
		case KeyEvent.VK_UP:
			player.newDirect = KeyEvent.VK_UP;
			//cout.println(""+START_COMMAND+SET_PLAYER_POSITION+(Utility.pInt(0, player.x, player.y)));
			cout.print(""+START_COMMAND+SET_PLAYER_POSITION+MOVEUP);
			cout.flush();
			break;
		case KeyEvent.VK_DOWN:
			player.newDirect = KeyEvent.VK_DOWN;
			//cout.println(""+START_COMMAND+SET_PLAYER_POSITION+(Utility.pInt(0, player.x, player.y)));
			cout.print(""+START_COMMAND+SET_PLAYER_POSITION+MOVEDOWN);
			cout.flush();
			break;
		case KeyEvent.VK_LEFT:
			player.newDirect = KeyEvent.VK_LEFT;
			//cout.println(""+START_COMMAND+SET_PLAYER_POSITION+(Utility.pInt(0, player.x, player.y)));
			cout.print(""+START_COMMAND+SET_PLAYER_POSITION+MOVELEFT);
			cout.flush();
			break;
		case KeyEvent.VK_RIGHT:
			player.newDirect = KeyEvent.VK_RIGHT;
			//cout.println(""+START_COMMAND+SET_PLAYER_POSITION+(Utility.pInt(0, player.x, player.y)));
			cout.print(""+START_COMMAND+SET_PLAYER_POSITION+MOVERIGHT);
			cout.flush();
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
