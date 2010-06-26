/* File: HashData.java
 * Start: 2010/06/07
 * Modification: 2010/06/26
 * Description: The "data" field in the hash table 
 */

import java.net.*;

public class HashData implements Constants {
	Socket socket;  // connected socket
	boolean isPacman;  // team
	int picIndex; // 0~3
	
	HashData(Socket socket, boolean isPacman, int picIndex) {
		this.socket = socket;
		this.isPacman = isPacman;
		this.picIndex = picIndex;
	}

}
