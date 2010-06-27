/* File: MessageThread.java
 * Start: 2010/06/27
 * Modification: 2010/06/XX
 * Description: Server's thread for message passing with ONE client.
 *              Created when each connection is accepted.
 */
import java.awt.event.*;
import java.net.*;
import java.util.Date;
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
	/* Message to be sent to client. Default: (START_COMMAND, IM_ALIVE) */
	public char msgHeader, command;
	public String message;
	
	
	
	MessageThread(Socket socket, TeamManager tm, JPanel panel) {
		this.socket = socket;
		this.tm = tm;
		this.panel = panel;
		msgField = PacFrame.msgField;
		/* default message combination */
		msgHeader = START_COMMAND;
		command = IM_ALIVE;
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
			Utility.checkStartMessage(cin.read(), panel);
			nickname = bf.readLine();
			tm.insertGuest(nickname, socket);
			
			/* Tell the client its playIndex */
			char clientIndex = (char)(TeamManager.vector.size() - 1);
			cout.print("" + START_COMMAND + (YOU_ARE_ZERO + clientIndex));
			
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
							/* control moving message */
							case MOVEUP:
								PacmanOnline.map.playerList[4].newDirect = KeyEvent.VK_UP;
								cout.print(""+START_COMMAND+MOVEUP);
								cout.flush();
								break;
							case MOVEDOWN:
								PacmanOnline.map.playerList[4].newDirect = KeyEvent.VK_DOWN;
								cout.print(""+START_COMMAND+MOVEDOWN);
								cout.flush();
								break;
							case MOVELEFT:
								PacmanOnline.map.playerList[4].newDirect = KeyEvent.VK_LEFT;
								cout.print(""+START_COMMAND+MOVELEFT);
								cout.flush();
								break;
							case MOVERIGHT:
								PacmanOnline.map.playerList[4].newDirect = KeyEvent.VK_RIGHT;
								cout.print(""+START_COMMAND+MOVERIGHT);
								cout.flush();
								break;
							/* control moving message end */
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
		char msg;
		while(true){
			try {
				msg = (char)cin.read();
				if ( msg != START_COMMAND )
					Utility.unknown(panel);
				msg = (char)cin.read();
				switch( msg ){
					case MOVEUP:
						PacmanOnline.map.playerList[4].newDirect=(KeyEvent.VK_UP);
						cout.print(""+START_COMMAND+MOVEUP);
						cout.flush();
						break;
					case MOVEDOWN:
						PacmanOnline.map.playerList[4].newDirect=(KeyEvent.VK_DOWN);
						cout.print(""+START_COMMAND+MOVEDOWN);
						cout.flush();
						break;
					case MOVELEFT:
						PacmanOnline.map.playerList[4].newDirect=(KeyEvent.VK_LEFT);
						cout.print(""+START_COMMAND+MOVELEFT);
						cout.flush();
						break;
					case MOVERIGHT:
						PacmanOnline.map.playerList[4].newDirect=(KeyEvent.VK_RIGHT);
						cout.print(""+START_COMMAND+MOVERIGHT);
						cout.flush();
						break;	
					default:
						System.out.println("KeyCode is " + msg );
				}
				
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

	/* Send a message to the client through socket */
	public void send() {
		if (msgHeader == START_COMMAND) {
			cout.print("" + msgHeader + command);
		}
		else if (msgHeader == START_MESSAGE) {
			cout.println("" + msgHeader + message);
		}
		else {
			Utility.unknown(panel);
		}		
	}
	
	/* Receive and respond to a command/message from the client through socket */
	public void recvAndRespond() {
		try {
			char header = (char)cin.read();
			if (header == START_COMMAND) {
				/* Respond to command */
				respondCommand((char)cin.read());
			}
			else if (header == START_MESSAGE) {
				// fill in the blank
				
			}
			else {
				Utility.unknown(panel);
			}			
		}
		catch (Exception e) {
			Utility.error(e);
		}
	}
	
	/* Respond to a command */
	public void respondCommand(char command) {
		switch (command) {
			case IM_ALIVE:
				System.out.println(nickname + " is alive @ " + new Date().toString());
				break;
			default:
				System.out.println("Some other command.");
		}
		send();
	}

}
