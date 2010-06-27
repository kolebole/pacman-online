import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;
import javax.swing.*;

/* File: ClientThread.java
 * Start: 2010/06/25
 * Modification: 2010/06/26
 * Description: Client thread for receiving/sending messages from/to the server.
 *              This thread is to avoid I/O blocking.
 */

public class ClientThread implements Constants, Messages, Runnable, KeyListener {
	JPanel panel;
	String addr;  // server address
	Socket cs;
	boolean killThread; // safer way to terminate a thread
	InputStream cin;
	PrintStream cout;
	String nickname;
	TeamManager tm;
	
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
		/*** Check for connection success ***/
		checkConnection();
		if (killThread == true) {
			return; // terminate the ClientThread
		}
		/* Send your nickname to the server and let the server randomly select an icon */
		try {
			nickname = ConnectPanel.nickField.getText();
			cout = new PrintStream(cs.getOutputStream());
			cout.println("" + START_MESSAGE + nickname);
			cout.flush();
			
		}
		catch (Exception e) {
			Utility.error(e);
		}
		/* Enable the "Ready" button and add an ActionListener */
		ConnectPanel.finalButton.setEnabled(true);
		tm = new TeamManager();
		ConnectPanel.finalButton.addActionListener(tm);
		
		
		/* For Rex and Vincent to fill in */
		while(!PacmanOnline.isReady){
			
		}
		
		PacmanOnline.inst.gamePanel.addKeyListener(this);
		
	}
		
	public void checkConnection() {
		try {
			System.out.println("Client: Connect to " + addr + " ...");
			cs = new Socket(addr, PORT);
			System.out.println("step 1");			
			System.out.println("Socket created (Server connected)"); 
			
			cs.setSoTimeout(CONN_TIMEOUT);
			cin = cs.getInputStream();
			System.out.println("step 2");
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
			System.out.println("step 3");				

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
		switch( keyCode ){
		case KeyEvent.VK_UP:
			cout.println(KeyEvent.VK_UP);
			cout.flush();
			break;
		case KeyEvent.VK_DOWN:
			cout.println(KeyEvent.VK_DOWN);
			cout.flush();
			break;
		case KeyEvent.VK_LEFT:
			cout.println(KeyEvent.VK_LEFT);
			cout.flush();
			break;
		case KeyEvent.VK_RIGHT:
			cout.println(KeyEvent.VK_RIGHT);
			cout.flush();
			break;
		default:
			System.out.println("KeyCode is " + keyCode );
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
