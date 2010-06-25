/* File: Constants.java
 * Start: 2010/06/25
 * Modification: 2010/06/XX
 * Description: Constants used in this game.
 */

public interface Constants {
	/*** Game-related ***/
	/* maximum allowable number of players */
	public static final int MAX_PLAYERS = 2;
	
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
	

}
