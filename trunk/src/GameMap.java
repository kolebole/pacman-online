import java.io.*;


public class GameMap {
	int HEIGHT = 35;
	int WIDTH = 25;
	int size = 20;
	Grid[][] map;
	GameMap () throws IOException{
		int i, j;
		BufferedReader buffer = new BufferedReader( new FileReader( "/resouce/test.map" ) );
		String line;
		map = new Grid[WIDTH][HEIGHT];
		for ( i = 0 ; i < HEIGHT ; i++ ){
			line = buffer.readLine();
			for ( j = 0 ; j < WIDTH ; j++ ){
				map[i][j] = new Grid( line.indexOf(j)-'0', i*size, j*size );
			}
		}
	}
}
