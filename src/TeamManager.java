/* File: template.java
 * Start: 2010/06/06
 * Modification: 2010/06/XX
 * Description: Team manager for team selection before start the game.
 */
import java.net.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class TeamManager implements Constants, ActionListener {
	public static Vector<VectorData> vector;
	//JButton picList[];
	Random random; /* For random initial selection */
	String nickname;
	public static boolean selected[][]; /* a 2 * MAX_TEAM_PLAYERS array indicating character selection */
	/**** only supports for host ****/
	int playerIndex = 0;
	
	TeamManager() {
		vector = new Vector<VectorData>();
		selected = new boolean[2][MAX_TEAM_PLAYERS];
		/* clear selected[][] */
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < MAX_TEAM_PLAYERS; j++) {
				selected[i][j] = false;
			}
		}
		random = new Random();
	}
	
	public void insertHost(String nickname) {
		/* Use an empty socket to represent server (host) */
		Socket socket = new Socket();
		/* Random selection */
		char randTeam = (char)random.nextInt(2);
		int randPic = random.nextInt(MAX_TEAM_PLAYERS);
		vector.add(new VectorData(nickname, socket, randTeam, randPic));
		selected[randTeam][randPic] = true;
	}
	
	/* Notify the team selection status */	
	public void notifyTeamSelection() {
		/* Clear playerPanel */
		clearPlayerPanel();
		/* Notify server */
		for (int i = 0; i < vector.size(); i++) {
			VectorData element = vector.get(i);
			PacFrame.playerPanel[element.team].nickList[element.picIndex].setText(element.nickname);
			
		}
		
		/* Notify each clients */
		/* 哭哭 */
	}
	
	public void clearPlayerPanel() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < MAX_TEAM_PLAYERS; j++) {
				PacFrame.playerPanel[i].nickList[j].setText(NICKNAME_SPACE);				
			}
		}		
	}
	
	/* Listen on the (2 * MAX_TEAM_PLAYERS) icon JButtons */
	public void actionPerformed(ActionEvent evt) {
		nickname = ConnectPanel.nickField.getText();

		for (char i = PACMANS; i < 2; i++) {
			for (int j = 0; j < MAX_TEAM_PLAYERS; j++) {
				if (evt.getSource() == PacFrame.playerPanel[i].picList[j]) {
					/* Check for repetition */
					if (!selected[i][j]) {
						System.out.println("Not repeated");
						
						/**** For now, only supports for the host ****/
						VectorData element = vector.get(playerIndex);
						/* Clear previous selection */
						selected[element.team][element.picIndex] = false;
						/* Switch selection */
						vector.set(playerIndex, new VectorData(element.nickname, element.socket, i, j));
						selected[i][j] = true;
						notifyTeamSelection();
						
					}
					else {
						return;
					}
				}
			}
		}
	}
}
