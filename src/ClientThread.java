import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.Date;

/* File: ClientThread.java
 * Start: 2010/06/25
 * Modification: 2010/06/28
 * Description: Client thread for receiving/sending messages from/to the server.
 *              This thread is to avoid I/O blocking.
 */

public class ClientThread implements Constants, Messages, Runnable, KeyListener, ActionListener {
	JPanel panel;
	String addr;  // server address
	Socket cs;
	boolean killThread; // safer way to terminate a thread
	InputStream cin = null;
	PrintStream cout = null;
	BufferedReader bf = null;
	String nickname;
	TeamManager tm;
	JButton finalButton;
	/* game-related variable */
	int playerIndex;
	/* Message to be sent to client. Default: (START_COMMAND, IM_ALIVE) */
	public char msgHeader, command;
	public String message;	
	char lastKeyCode = MOVERIGHT;
	
	/* constructor */
	ClientThread(JPanel panel, String addr) {
		killThread = false;
		this.panel = panel;
		this.addr = addr;
		/* default message combination */
		msgHeader = START_COMMAND;
		command = IM_ALIVE;
		Thread thread = new Thread(this);
		thread.start();
	}

	/* Entry point when a new thread is created */
	@Override
	public void run() {
		System.out.println("Client: Create a thread");
		System.out.println("Client: Connect to " + addr + " ...");
		try {
			/* Create a socket and connect at the same time.
			 * This may take long to respond if the user mistypes the address.
			 */
			cs = new Socket(addr, PORT);
			System.out.println("step 1");			
			/* Initialize cin, cout, bf using the socket */
			cin = cs.getInputStream();
			bf = new BufferedReader (new InputStreamReader(cin));
			cout = new PrintStream(cs.getOutputStream());			
		}
		catch (Exception e)  {
			Utility.error(e);
		}

		/*** Check for connection success ***/
		checkConnection();
		if (killThread == true) {
			return; // terminate the ClientThread
		}
		/* Enable the "Ready" button and add an ActionListener */
		finalButton = ConnectPanel.finalButton;
		finalButton.setEnabled(true);
		// Now the ActionListener becomes "this" instead of a TeamManager
		//tm = new TeamManager();
		finalButton.addActionListener(this);		
		
		
		PacmanOnline.inst.gamePanel.addKeyListener(this);		
		
		
		/*** Always do message receiving and sending ***/
		/* ryanlei: Should this be put to another Thread?? */
		while (true) {
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
		
		//// Put this in the synchronization 
//		try {
//			/* Send your nickname to the server and let the server randomly select an icon */
//			nickname = ConnectPanel.nickField.getText();
//			cout.println("" + START_MESSAGE + nickname);
//			cout.flush();			
//			
//			/* Receive your playerIndex */
//			Utility.checkStartCommand(cin.read(), panel);
//			playerIndex = cin.read() - YOU_ARE_ZERO;
//			
//		}
//		catch (Exception e) {
//			Utility.error(e);
//		}
		
		
		
		// try receiving server's messages
		// set the controlled character
		/*char msg;
		while(true){
			try {
				Utility.checkStartCommand(cin.read(), panel);
				msg = (char)cin.read();
				switch( msg ){
					case SET_PLAYER_POSITION:
						
						// Send last move after receiving server's messages
						cout.print(""+START_COMMAND+lastKeyCode);
						cout.flush();
						break;
					/*case MOVEUP:
						PacmanOnline.map.playerList[4].newDirect = KeyEvent.VK_UP;
						break;
					case MOVEDOWN:
						PacmanOnline.map.playerList[4].newDirect = KeyEvent.VK_DOWN;
						break;
					case MOVELEFT:
						PacmanOnline.map.playerList[4].newDirect = KeyEvent.VK_LEFT;
						break;
					case MOVERIGHT:
						PacmanOnline.map.playerList[4].newDirect = KeyEvent.VK_RIGHT;
						break;*/	
					/*default:
						System.out.println("KeyCode is " + msg );
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}*/
		
	}
		
	public void checkConnection() {
		try {
			/* First message should be START_COMMAND */
			/*** IMPORTANT: set the timeout before read() ***/
			cs.setSoTimeout(CONN_TIMEOUT);			
			if (Utility.checkStartCommand(cin.read(), panel) == false) {
				cs.close();
				killThread = true;
				return;
			}
			System.out.println("step 2");				

			/* Read second message */
			int msg = cin.read();
			switch (msg) {
				/* Join OK */
				case IM_ALIVE:
					PacFrame.msgField.setText("[Notice] You have joined the room.");
					ConnectPanel.nickField.setEnabled(false);
					ConnectPanel.addrField.setEnabled(false);
					ConnectPanel.clientButton.setEnabled(false);
					ConnectPanel.serverButton.setEnabled(false);
					ConnectPanel.finalButton.setEnabled(true);
					break;
				/* Host disallows join */
				case DISALLOW_JOIN:
					JOptionPane.showMessageDialog(panel.getParent(), "Host disllowed join.", 
							"Warning", JOptionPane.WARNING_MESSAGE);
					cs.close();
					System.out.println("Client: close socket.");
					killThread = true;
					break;
				/* The room is already full */
				case ROOM_FULL:
					JOptionPane.showMessageDialog(panel.getParent(), "Room already full.", 
							"Warning", JOptionPane.WARNING_MESSAGE);
					cs.close();
					System.out.println("Client: close socket.");
					killThread = true;
					break;
					
				/* unknown state */
				default:
					Utility.unknown(panel.getParent().getParent());
					cs.close();
					System.out.println("Client: close socket.");
					killThread = true;
			}
		} 
		catch (SocketTimeoutException e) {
			JOptionPane.showMessageDialog(panel.getParent(), "Connection timeout.", 
					"Error", JOptionPane.ERROR_MESSAGE);
			killThread = true;
		}
		catch (ConnectException e) {
			JOptionPane.showMessageDialog(panel.getParent(), "Connection refused.", 
					"Error", JOptionPane.ERROR_MESSAGE);
			killThread = true;
		}
		catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(panel.getParent(), "Unknown host", 
					"Error", JOptionPane.INFORMATION_MESSAGE);			
			killThread = true;
		}		
		catch (Exception e) {
			Utility.error(e);
		}
	}

	/* Respond to a command */
	public void respondCommand(char command) {
		switch (command) {
			case IM_ALIVE:
				PacFrame.msgField.setText("Server is alive @ " + new Date().toString());
				break;
			default:
				System.out.println("Some other command.");
		}
		send();
	}
	
	/* Send a message to the server through socket */
	public void send() {
		if (msgHeader == START_COMMAND) {
			cout.print("" + msgHeader + command);
			cout.flush();
		}
		else if (msgHeader == START_MESSAGE) {
			cout.println("" + msgHeader + message);
			cout.flush();
		}
		else {
			Utility.unknown(panel);
		}		
	}	
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		System.out.println("Client Action: "+ keyCode);
		switch( keyCode ){
		case KeyEvent.VK_UP:
			//cout.print(""+START_COMMAND+MOVEUP);
			lastKeyCode=MOVEUP;
			//cout.flush();
			break;
		case KeyEvent.VK_DOWN:
			//cout.print(""+START_COMMAND+MOVEDOWN);
			lastKeyCode=MOVEDOWN;
			//cout.flush();
			break;
		case KeyEvent.VK_LEFT:
			//cout.print(""+START_COMMAND+MOVELEFT);
			lastKeyCode=MOVELEFT;
			//cout.flush();
			break;
		case KeyEvent.VK_RIGHT:
			//cout.print(""+START_COMMAND+MOVERIGHT);
			lastKeyCode=MOVERIGHT;
			//cout.flush();
			break;
		default:
			System.out.println("KeyCode is " + keyCode );
		}
	}
	
	/* Listen on the "Ready" button */
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == finalButton) {
			if (finalButton.getText().equals("Ready")) {
				/* Send an "IM_READY" message to server */
				cout.print("" + START_COMMAND + IM_READY);
				cout.flush();
				finalButton.setText("Regret");
			}
			else if (finalButton.getText().equals("Regret")) {
				/* Send an "IM_NOT_READY" message to server */
				cout.print("" + START_COMMAND + IM_NOT_READY);
				cout.flush();
				finalButton.setText("Ready");				
			}
			else {
				Utility.unknown(panel);
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
