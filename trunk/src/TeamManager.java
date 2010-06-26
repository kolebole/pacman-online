/* File: template.java
 * Start: 2010/06/06
 * Modification: 2010/06/XX
 * Description: Team manager for team selection before start the game.
 */
import java.net.*;
import java.util.*;

public class TeamManager implements Constants {
	Vector<VectorData> vector;
	
	TeamManager() {
		vector = new Vector<VectorData>();
	}
	
	public void insertHost(String nickname) {
		/* Use an empty socket to represent server (host) */
		Socket socket = new Socket();
		vector.add(new VectorData(nickname, socket, MONSTERS, 2));
	}
	
	/* Notify the team selection status */	
	public void notifyTeamSelection() {
		//System.out.println("vector size = " + vector.size());
		/* Notify server */
		for (int i = 0; i < vector.size(); i++) {
			PacFrame.playerPanel[vector.get(i).team].nickList[vector.get(i).picIndex].setText(vector.get(i).nickname);
			
		}
		
		/* Notify each clients */
	}
}
