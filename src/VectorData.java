/* File: VectorData.java
 * Start: 2010/06/26
 * Modification: 2010/06/XX
 * Description: The actual data in the a vector element 
 */

import java.net.*;

public class VectorData implements Constants {
	String nickname; // nickname
	Socket socket;  // connected socket
	char team;  // the constant PACMANS or MONSTERS 
	int picIndex; // 0~3
	
	VectorData(String nickname, Socket socket, char team, int picIndex) {
		this.nickname = nickname;
		this.socket = socket;
		this.team = team;
		this.picIndex = picIndex;
	}

}
