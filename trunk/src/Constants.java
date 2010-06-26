/* File: Constants.java
 * Start: 2010/06/25
 * Modification: 2010/06/26
 * Description: Constants used in this game.
 */

public interface Constants {
	/*** Game-related ***/
	/* maximum allowable number of players */
	public static final int MAX_PLAYERS = 2;
	/* time to wait before showing room full */
	public static final int TIMER_ROOMFULL = 3000;
	
	/*** GUI-related ***/
	/* The whole frame (window) */
	public final int FRAME_WIDTH = 1000;
	public final int FRAME_HEIGHT = 650;
	/* The game panel */
	public static final int GAME_WIDTH = 700;
	public static final int GAME_HEIGHT = 500;
	
	/*** Network-related ***/
	/* This game uses the constant port port 4233 */
	public static final int PORT = 4233;
	/* Timeout for client connection */
	public static final int CONN_TIMEOUT = 3000;
	/* Timeout in a game play */
	public static final int GAME_TIMEOUT = 2000;
	
	/*** Picture Sources ***/
	/* For pac/mon UP/DOWN/LEFT/RIGHT pics */
	String[][] pacs = {
		{
			"pics/pac_LEFT.png",
			"pics/pac_RIGHT.png",
			"pics/pac_UP.png",
			"pics/pac_DOWN.png"
		},
		{},
		{},
		{}
	};
	String[][] mons = {
		{
			"pics/mon_LEFT.png",
			"pics/mon_RIGHT.png",
			"pics/mon_UP.png",
			"pics/mon_DOWN.png"
		},
		{},
		{},
		{}
	};
}