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
	JPanel panel;
	ServerSocket ss;
	Socket cs;
	String nickname; // server's (host's) nickname
	/* current number of players */
	public int numPlayers;
	/* allow other players to join */
	public boolean allowJoin;
	InputStream cin = null;
	PrintWriter cout = null;
	/* synchronization lock */
	public String roomLock = "I'll lock the room.";
	/* Team manager before starting the game */
	public TeamManager tm;
	
	/* constructor */
	ServerThread(JPanel panel) {
		this.panel = panel;
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
			allowJoin = true;
			ConnectPanel.lockButton.setEnabled(true);
			ConnectPanel.lockButton.addActionListener(this);
			PacFrame.msgField.setText("[Notice] You have created a room.");
			System.out.println("Server: Listen on port " + PORT + " ...");
			
			/* Create a TeamManager with an empty Hashtable */
			numPlayers = 1;
			tm = new TeamManager();
			/* Insert a record of the server (host) */
			nickname = ConnectPanel.nickField.getText();
			tm.insertHost(nickname);
			tm.notifyTeamSelection();
			
			/* wait for client to join */
			if (allowJoin && numPlayers < MAX_TOTAL_PLAYERS) {
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
						continue; // wait for a new client
					}
					/* Check for room full */
					if (numPlayers == MAX_TOTAL_PLAYERS) {
						System.out.println("The room is full. (" + MAX_TOTAL_PLAYERS + " people)");
						cout.print(ROOM_FULL);
						cout.flush();
						cs.close();
						continue; // wait for a new client
					}
					
					numPlayers++;
					PacFrame.msgField.setText("[Notice] Player " + numPlayers + " from " + cs.getInetAddress() + ":" + cs.getPort());
					/* If the room is now full, notify after 3 seconds */
					if (numPlayers == MAX_TOTAL_PLAYERS) {
						Timer timer = new Timer(TIMER_ROOMFULL, new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								PacFrame.msgField.setText("[Notice] The room is now full. (" + MAX_TOTAL_PLAYERS + " people)");	
							}
						});
						timer.start();
					}	
				} // end of synchronized()
					
				/* Now the join is OK => Send a message to the client */
				cout.print("" + START_COMMAND + IM_ALIVE);
				cout.flush();
				
				/* Receive the client's nickname and randomly select an icon for them */
				int msg = cin.read();
				if (msg != START_MESSAGE) {
					Utility.unknown(panel);
				}
				BufferedReader bf = new BufferedReader(new InputStreamReader(cin));
				String str = bf.readLine();
				
				
				
				////////////////////////////
				while(true){
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
				}
				/*str = bf.readLine();
				String tempStr="";
				while(!str.isEmpty()){
					tempStr+=str;
					str=bf.readLine();
				}
				System.out.println(tempStr);*/
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
