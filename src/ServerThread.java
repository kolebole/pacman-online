import java.net.*;
import javax.swing.*;
/* File: template.java
 * Start: 2010/06/25
 * Modification: 2010/06/XX
 * Description: Server thread for receiving/sending messages from/to clients.
 *              This thread is to avoid I/O blocking.
 */

public class ServerThread implements Constants, Runnable {
	ServerSocket ss;
	Socket cs;
	private static final int port = ConnectPanel.port;
	JTextField nickField;
	JTextField addrField;
	JButton serverButton;
	public int numPlayers;
	
	/* constructor */
	ServerThread() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	/* Entry point when a new thread is created */
	public void run() {
		System.out.println("Server: create a thread.");
		try {
			ss = new ServerSocket(port);
			System.out.println("Server: Listen on port " + port + " ...");
			numPlayers = 1;
			System.out.println("Number of players: " + numPlayers);

			/* Always wait for clients when ... # of players < 8 ?? */
			while (numPlayers < MAX_PLAYERS) {
				cs = ss.accept();
				numPlayers++;
				System.out.println("Server: Client from " + cs.getInetAddress() + ":" + cs.getPort());
				System.out.println("Number of players: " + numPlayers);
			}
			System.out.println("The room is full !");

		}
		catch (Exception e) {
			Utility.error(e.toString());
		}
		
		
	}

}
