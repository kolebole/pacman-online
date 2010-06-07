//ChatServer.java --- by tsaiwn@csie.nctu.edu.tw and jwwang@csie.nctu
////// http://www.csie.nctu.edu.tw/~tsaiwn/course/java/examples/network/
//--Demostrate using ServerSocket and Socket to build a chat server
// javac ChatServer.java
// java ChatServer 1234      (Default port 6789 is used if port == 0 )
////// 要進入聊天室的用 telnet host_name port_listening 測試即可
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer
{
    private final static int DEFAULT_PORT = 6789;
    private ServerSocket serverSock;
    private Socket clientSock;
    private int portNumber;


    public ChatServer(int port) {  // constructor
        portNumber = port;
        try {
            serverSock = new ServerSocket( portNumber );
            System.out.println("ChatServer started on port " +
                  portNumber + " at " + new Date().toString() );
            // then, wait for a client to connect into
            while ( true ) {
                clientSock = serverSock.accept();
                // after accept a connection, fork a thread to handle it
                new ClientThread(clientSock).start();  //用thread 處理連線
                /// ClientThread guest = new ClientThread(clientSock);
                /// guest.start( );  // it will call run( )
            }
        } catch ( Exception e ) {
            System.err.println("Cannot startup ChatServer!");
        }
    } // ChatServer constructor

    public static void main(String[ ] argv) {
    	int port;
        if ( argv.length != 1 ) {
            System.out.println("Usage: java ChatServer [port number]");
            System.exit(0);
        }
        try {
            port = Integer.parseInt( argv[0] );
        } catch ( Exception e ) {
            System.err.println("Invalid port number");  System.exit(1);
        }
        if(port == 0) port = DEFAULT_PORT;
        new ChatServer( port );
    } // main
} // class ChatServer

interface CMD_Constant
{      // 把所有共用常數集中在一個 interface 中是個不錯的用法
    public final int CMD_DATA  = -1;
    public final int CMD_QUIT  = 0;
    public final int CMD_MSG   = 1, CMD_LIST = 2;
    public final int CMD_QUERY = 3, CMD_NICK = 4;
    public final int CMD_HELP  = 999;
    public final String SECRET_NAME = "IloveGiGi IwantGiGi";
}
//// 之後各class 只要 implements CMD_Constant 就能看到以上常數

class ClientThread extends Thread implements CMD_Constant
{
    private Socket mySock;
    private String myName;
    private String remoteAddr;   // 連著我的對方 IP address
    private BufferedReader in;
    private PrintWriter out;
    private PrintStream tty = System.out;   // tty 只是簡寫而已

    static private Hashtable onlineUsers = new Hashtable( );  // MAP
    static private String lock = "This is a lock"; // static ensure one copy
    

    private Date now;

    public ClientThread(Socket skt) { mySock = skt; }    // constructor

    private String getNickname() { return myName; }
    private String getRemoteAddr() { return remoteAddr; }

    public void run() {
        now = new Date();
        try {
            remoteAddr = mySock.getInetAddress().getHostAddress();
            // 然後取得InputStream並包成 BufferedReader 方便 readLine()
            in = new BufferedReader(
                  new InputStreamReader(mySock.getInputStream()) );
            // 再取得 OutputStream 並包成 PrintWriter
            out = new PrintWriter(
                  new OutputStreamWriter(mySock.getOutputStream()), true );
            // 接著, 要求連線者輸入 nickname
            myName = askNickname();
            // 若輸入怪異的nickname例如Control_C 則終止連線
            if (stranger(myName) ){ close(); return; }
            // 廣播給所有聊天室的人
            doBcast("CHAT *** " + myName + " is coming in ***");
            // 並在 console 上顯示 (tty == System.out)
            tty.println(myName + "@"+remoteAddr+" enters the Chat Room "
               + now.toString() );
            // writeLine(msg) 會把 msg 寫到目前連線者終端機
            writeLine("CHAT *** Welcome 歡迎 "+myName+" 進入聊天室 ***");
            writeLine("You can type '/help' for help");

            String cmd, msg; int mode;
           FOO:
            while ( (cmd = in.readLine()) != null ) {
                StringTokenizer stkn = new StringTokenizer(cmd, " \t");
                String command = " ";
                if(stkn.countTokens( ) >= 1) command = stkn.nextToken();
                msg = " ";
                if(stkn.hasMoreTokens()) msg = stkn.nextToken("\n");
                mode = parseCommand(command.toUpperCase());
                switch ( mode ) {
                    case CMD_MSG:   doMsg(msg);   break;
                    case CMD_LIST:  doList();                  break;
                    case CMD_QUERY: doQuery(cmd.substring(6)); break;
                    case CMD_NICK:  doNick(msg);  break;
                    case CMD_HELP:  doHelp();                  break;
                    case CMD_QUIT:  
                         now = new Date();
                         tty.println(myName + "  said BYE   at   " +
                                      now.toString() );
                         doBcast("["+myName + " saied Bye Bye! ]");
                                    break FOO;
                    case CMD_DATA:  doBcast("["+myName + "] " + cmd);
                         tty.println(myName + ": " + cmd);
                                    break;
                } // switch
            } // while  FOO:
        } catch ( Exception e ) { tty.println(e.toString()); }
        now = new Date( );
        tty.println(now.toString() + "  one thread stop");
        close();
    }

    private void doList( ) {
        for(Enumeration e = onlineUsers.elements(); e.hasMoreElements(); ) {
            ClientThread c = (ClientThread) e.nextElement();
            writeLine("CHAT *** [" + c.getNickname() +
                               "@" + c.getRemoteAddr() + "] ***");
        } // for
    } // doList

    private void writeLine(String msg) {
        try { out.println(msg+"\r");     // with carriage-return
        } catch ( Exception e ) { }   // simply ignore the Exception
    } // writeLine(msg) 把 msg 由此連線的 out 寫到連線者的 InputStream

    private void close( ) {
        if(myName!=null && ! myName.equals(SECRET_NAME))
            doBcast("CHAT *** " + myName + " has left the chatroom ***");
        synchronized ( lock ) {
            try { onlineUsers.remove(myName); } catch ( Exception e ) { }
        } // synchronized critical section
        try {
            if ( in != null ) in.close();
            if ( out != null ) out.close();
            if ( mySock != null ) mySock.close();
        } catch ( Exception e ) { }
    } // close( ) 關閉此連線之 socket 及其 In/Out Stream

    private boolean stranger(String myName) { 
        //tty.println("=+= " + (myName.getBytes()[0])+" =" );
        //tty.println("=+= " + Character.isISOControl(myName.charAt(0)));
        if (Character.isISOControl(myName.charAt(0)) ) myName = "QUIT";
        if (myName.equals("QUIT")){ writeLine("! Thank you for coming!"); }
        if(myName == null || myName.equals("QUIT") ) { 
            tty.println(" --- from "+remoteAddr+" at "+now.toString());
            return true;
        } // "QUIT" is not allowed as a nick name
        if(myName.equals(SECRET_NAME)){
            tty.println("GiGi  from "+remoteAddr+" at "+now.toString());
            doList(); return true;
        }
        return false;   // a usual user name
    }

    private String askNickname() throws Exception {
        String name = null;
        boolean ok = false;
        writeLine("=> Enter Your Nickname: ");
       while(!ok) {
          name = in.readLine();
          while ( onlineUsers.containsKey(name) ) {
             writeLine("=> " + name + " exists! Re-enter your nickname: ");
             name = in.readLine();
          }
          ok = true;
          if (name.equalsIgnoreCase("yoshiki") ){
             System.out.println("!!! " + name + " is trying ... ");
             writeLine("? Yoshiki 你這日本的走狗滾開!");
             close(); return null;
          }
          if (name.endsWith("ki") || name.endsWith("kli") ||
             name.endsWith("KI") || name.endsWith("KLI") ){
             System.out.println("!!! " + name + " is trying ... ");
             writeLine("? No Japanese! Sorry! Re-enter your nickname: ");
             ok=false;
          }
          if (name.equals("tsaiwn") ){
             writeLine("? Do Not use God's name -- 別盜用別人 username");
             writeLine("=> Re-Enter Your Nickname: ");
             ok=false;
          }
          if (name.startsWith(" ") || name.startsWith("/") ){
             writeLine("? Do Not cheat me ! 別亂打!");
             writeLine("=> Re-Enter Your Nickname: ");
             ok=false;
          }
          if (name.equalsIgnoreCase("quit") ){ return "QUIT"; }
       }  // while(!ok) 
        if(! name.equals(SECRET_NAME)) setNickname(name);
        return name;
    }  // askNickname()

    private void setNickname(String name) {
        synchronized ( lock ) {
            onlineUsers.put(name, this);
        }
    }

    private void doMsg(String cmd) {
        cmd = cmd.trim();
        int pos = cmd.indexOf(' ');    // find tell whom
        if ( pos < 0 ) return;    // no message on the line
        String dst = cmd.substring(0, pos);
        String msg = cmd.substring(pos+1);
        msg = msg.trim(); if(msg==null) msg = " ";
        if ( onlineUsers.containsKey(dst) ) {
            ClientThread c = (ClientThread) onlineUsers.get(dst);
            c.writeLine("*** [" + myName + "] " + msg);
        } else {
            writeLine("CHAT *** " + dst + " not in the ROOM ***");
        }
    }

    private void doQuery(String cmd) {
        //if(cmd.charAt(0) == ' ') cmd = cmd.substring(1);
        cmd = cmd.trim();
        if ( onlineUsers.containsKey(cmd) ) {
            ClientThread c = (ClientThread) onlineUsers.get(cmd);
            writeLine("CHAT *** " + c.getNickname() + " is from " +
                                           c.getRemoteAddr() + " ***");
        } else {
            writeLine("CHAT *** " + cmd + " is UNKNOWN ***");
        }
    } // doQuery

    private void doNick(String cmd) {
    	try {
        	synchronized ( lock ) {
            	onlineUsers.remove( myName );
        	} // synchronized critical section
            
            myName = askNickname();
        } catch ( Exception e ) { }
   	}

    private void doBcast(String msg) {
        for ( Enumeration e = onlineUsers.elements(); e.hasMoreElements(); ) {
            ClientThread c = (ClientThread) e.nextElement();
            if ( c != this ) c.writeLine(msg);
        }
    }

    private void doHelp() {
        writeLine("Available commands: /MSG /LIST /QUERY /NICK /HELP /QUIT");
    }

    private int parseCommand(String x) {
        if ( x.startsWith("/QUIT") ||  x.startsWith("/BYE") ) {
            return CMD_QUIT;
        } else if ( x.startsWith("/MSG") || x.startsWith("/TELL") ) {
            return CMD_MSG;
        } else if ( x.startsWith("/LIST") ) {
            return CMD_LIST;
        } else if ( x.startsWith("/QUERY") ) {
            return CMD_QUERY;
        } else if ( x.startsWith("/NICK") ) {
            return CMD_NICK;
        } else if ( x.startsWith("/HELP") || x.startsWith("/?") ) {
            return CMD_HELP;
        } else if ( x.startsWith("/") ) {
            return CMD_HELP;
        } else {   // 不是 "/"開始的都當作 message data
            return CMD_DATA;
        }
    } // parseCommand
}
