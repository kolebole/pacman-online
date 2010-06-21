import java.io.*;


public class GameMap {
	public static int WIDTH = 35;
	public static int HEIGHT = 25;
	int size = 20;
	Grid[][] map;
	PacFrame frame = PacmanOnline.inst;
	GameMap () throws IOException{
		int i, j;
		BufferedReader buffer = new BufferedReader( new FileReader( "resouce/test.map" ) );
		char[] line;
		map = new Grid[HEIGHT][WIDTH];
		for ( i = 0 ; i < HEIGHT ; i++ ){
			line = buffer.readLine().toCharArray();
			for ( j = 0 ; j < WIDTH ; j++ ){
				map[i][j] = new Grid( line[j]-'0', j*size, i*size );
			}
		}
		frame.initGameMap(map);
	}
}
