import java.awt.event.KeyEvent;

/* File: Messages.java
 * Start: 2010/06/25
 * Modification: 2010/06/27
 * Description: Define messages used in client-server communication.
 */

public interface Messages {
	/* Header for a normal command. */
	public static final char START_COMMAND = '/';  // This is 47 in ASCII code
	/* Header for a message (String) command. It is terminated by '\n'. */
	public static final char START_MESSAGE = '\\';
	/* I'm alive, initially sent by server, but periodically sent later by clients. */
	public static final char IM_ALIVE = 0;
	/* Disallow join of the game (set by server) */
	public static final char DISALLOW_JOIN = 1;
	/* The room is already full */
	public static final char ROOM_FULL = 2;
	/* I'm ready to start the game, sent by clients */
	public static final char IM_READY = 3;
	/* I regret being ready to start the game, sent by clients */
	public static final char IM_NOT_READY = 4;
	/* I'll start a game now, sent by the server */
	public static final char START_GAME = 5;
	/* Control character moving */
	public static final char MOVEUP = 6;
	public static final char MOVEDOWN = 7;
	public static final char MOVELEFT = 8;
	public static final char MOVERIGHT = 9;
	/* Tell the client its playIndex (1~7). Server is 0. */
	public static final char YOU_ARE_ZERO = 10;  // never really sent?
	public static final char YOU_ARE_ONE = 11;
	public static final char YOU_ARE_TWO = 12;
	public static final char YOU_ARE_THREE = 13;
	public static final char YOU_ARE_FOUR = 14;
	public static final char YOU_ARE_FIVE = 15;
	public static final char YOU_ARE_SIX = 16;
	public static final char YOU_ARE_SEVEN = 17;
	
	
}
