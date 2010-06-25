import java.io.*;

/* File: GameMap.java
 * Start: 2010/06/21
 * Modification: 2010/06/25
 * Description: 載入資料地圖
 */
public class GameMap {
	public final static int WIDTH = 35;
	public final static int HEIGHT = 25;
	Grid[][] map;
	Player[] playerList;
	//Because of next line, we can't put GameMap before PacFrame.
	PacFrame frame = PacmanOnline.inst;
	GameMap () throws IOException{
		int i, j;
		BufferedReader buffer = new BufferedReader( new FileReader( "resouce/test.map" ) );
		char[] line;
		map = new Grid[WIDTH][HEIGHT];
		for ( i = 0 ; i < HEIGHT ; i++ ){
			line = buffer.readLine().toCharArray();
			for ( j = 0 ; j < WIDTH ; j++ ){
				map[j][i] = new Grid( line[j]-'0', j*Player.SIZE, i*Player.SIZE );
			}
		}
		
		playerList = new Player[2];
		playerList[0] = new Player( 30, 20, Player.PACMAN );
		playerList[1] = new Player( 30, 40, Player.MONSTER );
		
		// draw picture
		frame.initGameMap(map);
		frame.placePlayer(playerList);
	}
	
	public void checkTouch(){
		int i, j, gridx, gridy;
		for ( i = 0 ; i < playerList.length ; i++ ){
			gridx = playerList[i].gridx;
			gridy = playerList[i].gridy;
			switch ( map[gridx][gridy].content ){
			case Grid.NONE:
				// do nothing
				break;
			case Grid.CREDIT:
				if ( playerList[i].type == Player.PACMAN ){
					// TODO pacman get the credit
					map[gridx][gridy].content = Grid.NONE;
					map[gridx][gridy].image.setVisible(false);
				}else if ( playerList[i].type == Player.MONSTER ){
					// do nothing
				}else {
					// error
				}
				break;
			case Grid.WALL:
				// TODO can't move forward
				playerList[i].move(true);
				playerList[i].updateGrid();
				break;
			default:
				// error
			}
			if ( playerList[i].type == Player.MONSTER ) {
				for ( j = 0 ; j < playerList.length ; j++ ){
					if ( gridx == playerList[j].x && 
						 gridy == playerList[j].y && 
						 playerList[j].type == Player.PACMAN ){
						// TODO Monster eat Pacman
					}
				}
			}
		}
	}
}
