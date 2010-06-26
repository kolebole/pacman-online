import javax.swing.JLabel;


/* File: Grid.java
 * Start: 2010/06/21
 * Modification: 2010/06/21
 * Description: The map grid used only by GameMap.java.
 */
public class Grid {
	static final int NONE = 0,
			         WALL = 1,
			         BEAN = 2;
	int content;
	int x;
	int y;
	JLabel image;
	
	Grid( int content, int x, int y ){
		this.content = content;
		this.x = x;
		this.y = y;
	}
	
	public void setImage( JLabel img ){
		image = img;
	}
}
