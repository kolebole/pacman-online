import java.net.*;
import javax.swing.*;
import java.io.*;
/* File: ServerThread.java
 * Start: 2010/06/25
 * Modification: 2010/06/XX
 * Description: Server thread for receiving/sending messages from/to clients.
 *              This thread is to avoid I/O blocking.
 */

public class ServerThread implements Constants, Messages, Runnable {
	ServerSocket ss;
	Socket cs;
	public int numPlayers;
	InputStream cin = null;
	PrintWriter cout = null;
	
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
			/* disable other textfields or buttons */
			ConnectPanel.nickField.setEnabled(false);
			ConnectPanel.addrField.setEnabled(false);
			ConnectPanel.clientButton.setEnabled(false);
			ConnectPanel.serverButton.setEnabled(false);

			System.out.println("Server: Listen on port " + PORT + " ...");
			numPlayers = 1;
			System.out.println("Number of players: " + numPlayers);

			/* Always wait for clients when ... # of players < 8 ?? */
			while (numPlayers < MAX_PLAYERS) {
				cs = ss.accept();
				numPlayers++;
				System.out.println("Server: Client from " + cs.getInetAddress() + ":" + cs.getPort());
				System.out.println("Number of players: " + numPlayers);
				
				/* getInputStream() and getOutputStream() */
				
				cin = cs.getInputStream();
				cout = new PrintWriter(cs.getOutputStream());
				
				/* Send a message to the client */
				cout.print(IM_ALIVE);
				cout.flush();
			}
			System.out.println("The room is full !");

		}
		catch (Exception e) {
			Utility.error(e.toString());
		}
		
		
	}

}
