/* File: template.java
 * Start: 2010/06/06
 * Modification: 2010/06/XX
 * Description: Team manager for team selection before start the game.
 */
import java.net.*;
import java.util.*;

public class TeamManager {
	Hashtable<String, HashData> ht;
	
	TeamManager() {
		ht = new Hashtable<String, HashData>();
	}
	
	public void insertHost(String nickname) {
		/* Use an empty socket to represent server (host) */
		Socket socket = new Socket();
		ht.put(nickname, new HashData(socket, true, 2));
	}
	
	/* Notify the team selection status */	
	public void notifyTeamSelection() {
		/* Notify server */
		
		/* Notify each clients */
	}
}
