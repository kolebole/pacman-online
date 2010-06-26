/* File: Constants.java
 * Start: 2010/06/25
 * Modification: 2010/06/26
 * Description: Constants and parameters used in this game.
 */

public interface Constants {
	/*** Game-related ***/
	/* maximum total number of players */
	public static final int MAX_TOTAL_PLAYERS = 2;
	/* maximum number of players on each team */
	public static final int MAX_TEAM_PLAYERS = 4;
	/* time to wait before showing room full */
	public static final int TIMER_ROOMFULL = 3000;
	/* Team id */
	public static final char PACMANS = 0;
	public static final char MONSTERS = 1;
	
	/*** GUI-related ***/
	/* The whole frame (window) */
	public final int FRAME_WIDTH = 1000;
	public final int FRAME_HEIGHT = 650;
	/* The game panel */
	public static final int GAME_WIDTH = 700;
	public static final int GAME_HEIGHT = 500;
	/* The playerPanel[] */
	String NICKNAME_SPACE = "                         ";
	
	/*** Network-related ***/
	/* This game uses the constant port port 4233 */
	public static final int PORT = 4233;
	/* Timeout for client connection */
	public static final int CONN_TIMEOUT = 3000;
	/* Timeout in a game play */
	public static final int GAME_TIMEOUT = 2000;
	
	/*** Picture Sources ***/
	public static final int MAX_PIC_INDEX = 3;
	/* For pac/mon UP/DOWN/LEFT/RIGHT pics */
	String[][] pacs = {
		{
			"pics/pac_LEFT_1.png",
			"pics/pac_RIGHT_1.png",
			"pics/pac_UP_1.png",
			"pics/pac_DOWN_1.png"
		},
		{
			"pics/pac_LEFT_2.png",
			"pics/pac_RIGHT_2.png",
			"pics/pac_UP_2.png",
			"pics/pac_DOWN_2.png"
		},
		{
			"pics/pac_LEFT_3.png",
			"pics/pac_RIGHT_3.png",
			"pics/pac_UP_3.png",
			"pics/pac_DOWN_3.png"
		},
		{
			"pics/pac_LEFT_4.png",
			"pics/pac_RIGHT_4.png",
			"pics/pac_UP_4.png",
			"pics/pac_DOWN_4.png"
		}
	};
	String[][] mons = {
		{
			"pics/mon_LEFT_1.png",
			"pics/mon_RIGHT_1.png",
			"pics/mon_UP_1.png",
			"pics/mon_DOWN_1.png"
		},
		{
			"pics/mon_LEFT_2.png",
			"pics/mon_RIGHT_2.png",
			"pics/mon_UP_2.png",
			"pics/mon_DOWN_2.png"
		},
		{
			"pics/mon_LEFT_3.png",
			"pics/mon_RIGHT_3.png",
			"pics/mon_UP_3.png",
			"pics/mon_DOWN_3.png"
		},
		{
			"pics/mon_LEFT_4.png",
			"pics/mon_RIGHT_4.png",
			"pics/mon_UP_4.png",
			"pics/mon_DOWN_4.png"
		}
	};
}