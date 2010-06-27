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
	JTextField msgField;
	
	MessageThread(Socket socket, TeamManager tm, JPanel panel) {
		this.socket = socket;
		this.tm = tm;
		this.panel = panel;
		msgField = PacFrame.msgField;
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
			nickname = bf.readLine();
			tm.insertGuest(nickname, socket);						
		}
		catch (Exception e) {
			Utility.error(e);
		}
		
		/* Constantly receive messages from the client */
		
		while (true) {
			try {
				System.out.println("Server: read() 1.");
				int msg1 = cin.read();
				switch (msg1) {
					case START_COMMAND:
						System.out.println("Server: read() 2.");
						int msg2 = cin.read();
						switch (msg2) {
							case IM_READY:
								System.out.println("Server: " + nickname + " is ready.");
								msgField.setText("[Notice] " + nickname + " is ready.");
								ServerThread.numReady += 1;
								System.out.println("Server: numReady = " + ServerThread.numReady);
								checkAllReady();
								break;
							case IM_NOT_READY:
								System.out.println("Server: " + nickname + " is not ready.");
								msgField.setText("[Notice] " + nickname + " is not ready.");
								ServerThread.numReady -= 1;
								System.out.println("Server: numReady = " + ServerThread.numReady);
								checkAllReady();
								break;
							default: 
								System.out.println("Server: Other command.");
						}
						break;
						
					case START_MESSAGE:
						System.out.println("Server: START_MESSAGE. Not yet implemented.");
						break;
						
					default:
						Utility.unknown(panel);
				}
				
				
				
				
			}
			catch (Exception e) {
				Utility.error(e);
			}
			
		}
		
		/*** call ketCodeHandler() (infinite) once by Rex and Vincent ***/
		//keyCodeHandler();
		
	}
	
	public void keyCodeHandler() {
		////////////////////////////
		while(true){
			try {
				str = bf.readLine();
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
				// Testing sending back to client
				cout.println(keyCode);
				cout.flush();
			}
			catch (Exception e) {
				Utility.error(e);
			}
		}		
	}

	/* Check if all clients are ready */
	public void checkAllReady() {
		if (ServerThread.numReady == ServerThread.numPlayers - 1) {
			System.out.println("All clients are ready.");
			/* Enable the "Start" button */
			ConnectPanel.finalButton.setEnabled(true);
		}
		else {
			ConnectPanel.finalButton.setEnabled(false);
		}
		
	}

}
