import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

/* File: ClientThread.java
 * Start: 2010/06/25
 * Modification: 2010/06/26
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
	char lastKeyCode = MOVERIGHT;
	
	/* constructor */
	ClientThread(JPanel panel, String addr) {
		killThread = false;
		this.panel = panel;
		this.addr = addr;
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
		/* Send your nickname to the server and let the server randomly select an icon */
		try {
			nickname = ConnectPanel.nickField.getText();
			cout.println("" + START_MESSAGE + nickname);
			cout.flush();
			
		}
		catch (Exception e) {
			Utility.error(e);
		}
		/* Enable the "Ready" button and add an ActionListener */
		finalButton = ConnectPanel.finalButton;
		finalButton.setEnabled(true);
		// Now the ActionListener becomes "this" instead of a TeamManager
		//tm = new TeamManager();
		finalButton.addActionListener(this);
		
		
		/* For Rex and Vincent to fill in */
		while(!PacmanOnline.isReady){
			
		}
		
		PacmanOnline.inst.gamePanel.addKeyListener(this);
		// try receiving server's messages
		// set the controlled character
		char msg;
		while(true){
			try {
				msg = (char)cin.read();
				if ( msg != START_COMMAND )
					Utility.unknown(panel);
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
					default:
						System.out.println("KeyCode is " + msg );
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		
	}
		
	public void checkConnection() {
		try {
			int msg = -1;
			/*** IMPORTANT: set the timeout before read() ***/
			cs.setSoTimeout(CONN_TIMEOUT);
			msg = cin.read();
			/* First message should be START_COMMAND */
			if (msg != START_COMMAND) {
				Utility.unknown(panel.getParent().getParent());
				cs.close();
				System.out.println("Client: close socket.");
				killThread = true;
				return;
			}
			System.out.println("step 2");				

			/* Read second message */
			msg = cin.read();
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
	
	@Override
	/* Listen on the "Ready" button */
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
