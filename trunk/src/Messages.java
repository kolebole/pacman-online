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
	/* I'll start a game now, sent by the server */
	public static final char START_GAME = 4;
	
	
	
}
