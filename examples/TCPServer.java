/* Problem 5: Simple Java TCP server program */
import java.net.*;
import java.io.*;
import java.util.*;

class TCPServer {
	
	public static void main( String args[] ) {
		try {
			ServerSocket sskt = new ServerSocket( 5566 );
			Socket cskt;
			// loop forever
			while ( true ) {
				cskt = sskt.accept();
				userThread chatRoom = new userThread( cskt );
				chatRoom.start();
			}
		}
		catch ( Exception e ) {}
	}
}

class userThread extends Thread {
	Socket cskt;
	BufferedReader cin;
	PrintStream cout;
	String name;
	String str;
	
	userThread( Socket cskt ) {
		this.cskt = cskt;
	}
	
	public void run() {
		try {
			cin = new BufferedReader( new InputStreamReader( 
				cskt.getInputStream() ) );
			cout = new PrintStream( cskt.getOutputStream() );
		
			cout.println( "Hello. What is your name?" );
			name = cin.readLine();
			cout.println( "Hi, " + name + ". You logged in at " +
				new Date( System.currentTimeMillis() ).toString() );
			
			while ( true ) {
				str = cin.readLine();
				if ( str.equals( "quit" ) ) {
					cout.println( "bye bye" );
					break;
				}

				cout.print( name + "@" + 
						new Date( System.currentTimeMillis() ).toString() + ": " );
				for ( int i = str.length() - 1; i >= 0; i-- ) {
					cout.print( Character.toString( str.charAt( i ) ) );
				}
				cout.println( "" );
			}
			cskt.close();
		}
		catch ( Exception e) {}

	}

	
}