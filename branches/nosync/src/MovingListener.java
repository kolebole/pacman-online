import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/* File: MovingListener.java
 * Start: 2010/06/21
 * Modification: 2010/06/25
 * Description: Used by movingTimer in PacmanOnline.java.
 */
public class MovingListener implements ActionListener {
	PacFrame frame = PacmanOnline.inst;
	GameMap map = PacmanOnline.map;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for ( int i = 0 ; i < map.playerList.length ; i++ ){
			map.playerList[i].move();
			map.playerList[i].checkTouch();
		}
		
		frame.movePlayer(map.playerList);

	}
	

}
