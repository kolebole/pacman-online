/* File: SyncThread.java
 * Start: 2010/06/27
 * Modification: 2010/06/XX
 * Description: Server's unique timer thread for message synchronization.
 */
import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;

public class SyncThread implements Constants, Runnable, ActionListener {
	public Timer syncTimer;

	SyncThread() {
		Thread thread = new Thread(this);
		thread.start();
		syncTimer = new Timer(SYNC_TIMER_PERIOD, this);
		syncTimer.start();
	}
	
	@Override
	public void run() {
		

	}
	
	public void actionPerformed(ActionEvent evt) {
		System.out.println("sync: " + ServerThread.mtv.size() + " clients.");
		/* Send an IM_ALIVE message to each MessageThread's socket */
		for (MessageThread mt : ServerThread.mtv) {
			mt.send();
			//mt.recvAndRespond();
		}
		//System.out.println("sync: MessageThread.send() to all clients.");
	}

}
