import java.net.*;
import javax.swing.*;

import java.awt.event.*;
import java.io.*;
/* File: ServerThread.java
 * Start: 2010/06/25
 * Modification: 2010/06/26
 * Description: Server thread for receiving/sending messages from/to clients.
 *              This thread is to avoid I/O blocking.
 */

public class ServerThread implements Constants, Messages, Runnable, ActionListener {
	ServerSocket ss;
	Socket cs;
	/* current number of players */
	public int numPlayers;
	/* allow other players to join */
	public boolean allowJoin;
	InputStream cin = null;
	PrintWriter cout = null;
	public String roomLock = "I'll lock the room.";
	
	/* constructor */
	ServerThread() {
		Thread thread = new Thread(this);
		thread.start();
	}

	/* Entry point when a new thread is created */
	@Override
	public void run() {
		System.out.println("Server: create a thread.");
		try {
			ss = new ServerSocket(PORT);
			/* Disable other textfields or buttons */
			ConnectPanel.nickField.setEnabled(false);
			ConnectPanel.addrField.setEnabled(false);
			ConnectPanel.clientButton.setEnabled(false);
			ConnectPanel.serverButton.setEnabled(false);
			/* Enable "Lock Room" and add ActionListener */
			ConnectPanel.lockButton.setEnabled(true);
			ConnectPanel.lockButton.addActionListener(this);
			PacFrame.msgField.setText("[Notice] You have created a room.");

			System.out.println("Server: Listen on port " + PORT + " ...");
			allowJoin = true;
			ConnectPanel.lockButton.setEnabled(true);
			numPlayers = 1;
			System.out.println("Number of players: " + numPlayers);
			/* wait for client to join */
			if (allowJoin && numPlayers < MAX_PLAYERS) {
				waitForClients();
			}
		}
		catch (Exception e) {
			Utility.error(e);
		}

	}
	
	public void waitForClients() {	
		/* Always wait for clients to join */
		try {
			while (true) {
				cs = ss.accept();
				/* getInputStream() and getOutputStream() */				
				cin = cs.getInputStream();
				cout = new PrintWriter(cs.getOutputStream());
				
				/*** critical section ***/
				synchronized (roomLock) {
					/* Check for allowJoin */
					if (!allowJoin) {
						System.out.println("The room is now allowed for join.");
						cout.print(DISALLOW_JOIN);
						cout.flush();
						cs.close();
						return;
					}
					/* Check for room full */
					if (numPlayers == MAX_PLAYERS) {
						System.out.println("The room is full. (" + MAX_PLAYERS + " people");
						cout.print(ROOM_FULL);
						cout.flush();
						cs.close();
						return;
					}
					
					numPlayers++;
					PacFrame.msgField.setText("[Notice] Player " + numPlayers + " from " + cs.getInetAddress() + ":" + cs.getPort());
					/* If the room is now full, notify after 2 seconds */
					if (numPlayers == MAX_PLAYERS) {
						Timer timer = new Timer(TIMER_ROOMFULL, new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								PacFrame.msgField.setText("[Notice] The room is now full (" + MAX_PLAYERS + " people)");	
							}
						});
						timer.start();
					}	
					
					/* If join OK => Send a message to the client */
					cout.print(IM_ALIVE);
					cout.flush();
					
				} // end of synchronized()
			}
			
		}
		catch (Exception e) {
			Utility.error(e.toString());
		}
	}
	
	public void actionPerformed(ActionEvent evt) {
		JButton src = (JButton)evt.getSource();
		
		/* Lock / Unlock the room */
		if (src == ConnectPanel.lockButton) {
			/*** critical section ***/
			synchronized (roomLock) {
				if (allowJoin) {
					allowJoin = false;
					PacFrame.msgField.setText("[Notice] The host has locked the room.");
					ConnectPanel.lockButton.setText("Unlock Room");
				}
				else {
					allowJoin = true;
					PacFrame.msgField.setText("[Notice] The host has unlocked the room.");
					ConnectPanel.lockButton.setText(" Lock Room ");
				}
			}
		}
	}
}
