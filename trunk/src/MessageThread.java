/* File: MessageThread.java
 * Start: 2010/06/27
 * Modification: 2010/06/XX
 * Description: Server's thread for message passing with ONE client.
 *              Created when each connection is accepted.
 */
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;

public class MessageThread implements Runnable, Messages {
	Socket socket;
	TeamManager tm;
	JPanel panel;
	String str, nickname;
	InputStream cin = null;
	BufferedReader bf = null;
	PrintStream cout = null;
	
	MessageThread(Socket socket, TeamManager tm, JPanel panel) {
		this.socket = socket;
		this.tm = tm;
		this.panel = panel;
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		System.out.println("One MessageThread created.");
		
		/* Initialize cin, cout, bf using the socket */
		try {
			cin = socket.getInputStream();
			bf = new BufferedReader(new InputStreamReader(cin));
			cout = new PrintStream(socket.getOutputStream());
		
			/* Send an IM_ALIVE message to the client */
			cout.print("" + START_COMMAND + IM_ALIVE);
			cout.flush();
	
			/* Receive the client's nickname and randomly select an icon for them */
			int msg = cin.read();
			if (msg != START_MESSAGE) {
				Utility.unknown(panel);
			}
			String nickname = bf.readLine();
			tm.insertGuest(nickname, socket);						
		}
		catch (Exception e) {
			Utility.error(e);
		}
		
		/*** call ketCodeHandler() (infinite) once by Rex and Vincent ***/
		keyCodeHandler();
		
	}
	
	public void keyCodeHandler() {
		////////////////////////////
		while(true){
			try {
				String str = bf.readLine();
				int keyCode = Integer.parseInt(str);
				switch( keyCode ){
					case KeyEvent.VK_UP:
						PacmanOnline.map.playerList[4].newDirect=(KeyEvent.VK_UP);
						break;
					case KeyEvent.VK_DOWN:
						PacmanOnline.map.playerList[4].newDirect=(KeyEvent.VK_DOWN);
						break;
					case KeyEvent.VK_LEFT:
						PacmanOnline.map.playerList[4].newDirect=(KeyEvent.VK_LEFT);
						break;
					case KeyEvent.VK_RIGHT:
						PacmanOnline.map.playerList[4].newDirect=(KeyEvent.VK_RIGHT);
						break;	
					default:
						System.out.println("KeyCode is " + keyCode );
				}
			}
			catch (Exception e) {
				Utility.error(e);
			}
		}		
	}

}
