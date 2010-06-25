import java.net.*;
import javax.swing.*;
/* File: template.java
 * Start: 2010/06/25
 * Modification: 2010/06/XX
 * Description: Server thread for receiving/sending messages from/to clients.
 *              This thread is to avoid I/O blocking.
 */

public class ServerThread implements Runnable {
	ServerSocket ss;
	Socket cs;
	private static final int port = ConnectPanel.port;
	JTextField addrField;
	JButton serverButton;
	
	/* constructor */
	ServerThread(JTextField addrField, JButton serverButton) {
		this.addrField = addrField;
		this.serverButton = serverButton;
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		System.out.println("Server: create a thread.");
		try {
			ss = new ServerSocket(port);
			System.out.println("Server: Listen on port " + port + " ...");
			/* disable other buttons */
			addrField.setEnabled(false);
			serverButton.setEnabled(false);
			
			while (true) {
				cs = ss.accept();
				System.out.println("Server: Socket accepted.");
				System.out.println("Server: Client from " + cs.getInetAddress());
			}

		}
		catch (Exception e) {
			Utility.error(e.toString());
		}
		
		
	}

}
