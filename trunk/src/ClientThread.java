import java.io.*;
import java.net.*;
import javax.net.*;
import javax.swing.*;

/* File: ClientThread.java
 * Start: 2010/06/25
 * Modification: 2010/06/XX
 * Description: Client thread for receiving/sending messages from/to the server.
 *              This thread is to avoid I/O blocking.
 */

public class ClientThread implements Constants, Messages, Runnable {
	JPanel panel;
	String addr;  // server address
	Socket cs;
	
	/* constructor */
	ClientThread(JPanel panel, String addr) {
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
			cs = new Socket(addr, PORT);
			System.out.println("Create socket"); 

			/* Check for connection success */
			InputStream cin = cs.getInputStream();
			PrintStream cout = new PrintStream(cs.getOutputStream());
			int msg = -1;
			/*** IMPORTANT: set the timeout before read() ***/
			try {
				cs.setSoTimeout(CONN_TIMEOUT);
				msg = cin.read();
			}
			catch (SocketTimeoutException e) {
				JOptionPane.showMessageDialog(panel.getParent(), "Connection failed.", 
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (msg == IM_ALIVE) {
				System.out.println("Connection succeeded");
			}
			else {
				Utility.unknown(panel.getParent().getParent());
			}
			
			ConnectPanel.nickField.setEnabled(false);
			ConnectPanel.addrField.setEnabled(false);
			ConnectPanel.clientButton.setEnabled(false);
			ConnectPanel.serverButton.setEnabled(false);
		
		} 
		catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(panel.getParent(), "Unknown host", 
					"Error", JOptionPane.INFORMATION_MESSAGE);			
			return;
		}		
		catch (ConnectException e) {
			JOptionPane.showMessageDialog(panel.getParent(), "Connection refused.", 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		catch (Exception e) {
			Utility.error(e);
		}
	}
		

}
