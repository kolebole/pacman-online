import java.io.*;

/* File: GameMap.java
 * Start: 2010/06/21
 * Modification: 2010/06/25
 * Description: 載入資料地圖
 */
public class GameMap implements Constants {
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
		
		playerList = new Player[8];
		playerList[0] = new Player( 30, 20, Player.PACMAN, map , pacs[0] );
		playerList[1] = new Player( 100, 150, Player.PACMAN, map , pacs[1] );
		playerList[2] = new Player( 200, 170, Player.PACMAN, map , pacs[2] );
		playerList[3] = new Player( 300, 180, Player.PACMAN, map , pacs[3] );
		playerList[4] = new Player( 30, 50, Player.MONSTER, map , mons[0] );
		playerList[5] = new Player( 40, 180, Player.MONSTER, map, mons[1] );
		playerList[6] = new Player( 50, 190, Player.MONSTER, map, mons[2] );
		playerList[7] = new Player( 60, 180, Player.MONSTER, map, mons[3] );
		for ( i = 0 ; i < playerList.length ; i++ ){
			playerList[i].addRef(playerList);
		}
		
		
		// draw picture
		frame.initGameMap(map);
		frame.placePlayer(playerList);
	}
	
}
