import java.awt.*;
import java.awt.event.*;
import java.io.*; 	// new
import java.net.*;	// new
import java.util.StringTokenizer;  // new
// 我們先把TestTicTacToe1當成Computer這一邊的
public class TestTicTacToe1 extends Frame implements WindowListener, ActionListener
{
int portNo;
InetAddress addr;
DatagramSocket ssocket; 
DatagramSocket rsocket;
// 啟動棋盤, 等待使用者點按
    public void init( )
    {
	this.portNo =5556;
	System.out.print("Please input the IP address of destination :");
	BufferedReader uip= new BufferedReader(new
		InputStreamReader(System.in)); 
            try{
	String ServerIP=uip.readLine();
	this.addr=InetAddress.getByName(ServerIP);
	this.ssocket=new DatagramSocket(1500);
	this.rsocket=new DatagramSocket(1600);
            } catch (SocketException e) {
              e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();
        }
        setLayout( new GridLayout( 3, 3 ) );
        for( int i = 0; i < 3; i++ )
           for( int j = 0; j < 3; j++ )
           {
               BookKeepArray[ i ][ j ] = new Button( );
               add( BookKeepArray[ i ][ j ] );
               BookKeepArray[ i ][ j ].addActionListener( this );
	       BookKeepArray[ i ][ j ].setFont(new Font("Serif", Font.BOLD, 100));
           }
        addWindowListener( this );
        newBoard( );
//_____________________________________________________
// Computer這一邊一開始先等human下!
	byte buffer1[]=new byte[100];   
	try {
	DatagramPacket packet1=new DatagramPacket(buffer1,100,this.addr,1600);
	System.out.println("wait on receiving ...等對方下!");
	this.rsocket.receive(packet1); 
	String msg1=new String(buffer1,0,packet1.getLength());
	System.out.println("收到下面的訊息 : " + msg1); 
	StringTokenizer stok1 = new StringTokenizer(msg1,",");
	int dontcare = Integer.parseInt(stok1.nextToken());
	int x1=Integer.parseInt(stok1.nextToken());
	int y1=Integer.parseInt(stok1.nextToken());
	BookKeepArray[x1][y1].setLabel(HUMANOPPYouKnowMe);
	BookKeepArray[x1][y1].setEnabled(false);
	t.recordMoveInArray( TicTacToe.HUMANOPP, x1, y1 );
	}catch (SocketException e) {
            		e.printStackTrace();
        	} catch (IOException e) {
            		e.printStackTrace();
}
//_____________________________________________________
    }
    public TestTicTacToe1( String whoami)
    {
        super("TestTicTacToe1 (was computer: " +whoami);
        init( );
    }
// 重玩 ! 新建一個TicTacToe的案例, 清除按鈕上的標籤
    public void newBoard( )
    {
        t = new TicTacToe( );
        for( int i = 0; i < 3; i++ )
           for( int j = 0; j < 3; j++ )
           {
               BookKeepArray[ i ][ j ].setLabel( "" );	// 無標籤
               BookKeepArray[ i ][ j ].setEnabled( true );	// 可點按下棋步
           }
    }
    private int KeepGameInteresting = 0;
// 遊戲結束時(注意結束的條件), 開始另一盤, 圈叉不對調
// 平手時由human先下, 否則由贏的先下
    public boolean GameOverRest( boolean condition, String message, boolean ComputerMoves )
    {
        if( condition )
        {
            System.out.println( message );
            System.out.println( "重新開始新的一盤遊戲!" );
            newBoard( );
            if( ComputerMoves )
            {
                  System.out.println( "先下囉!" );
            }
            else
            {
                  System.out.println( "等對方先下吧!" );
              	byte buffer2[]=new byte[100];   
	try {
	DatagramPacket packet2=new DatagramPacket(buffer2,100,this.addr,1600);
	System.out.println("wait on receiving ...等對方下!");
	this.rsocket.receive(packet2); 
	String msg2=new String(buffer2,0,packet2.getLength());
	System.out.println("收到下面的訊息 : " + msg2); 
	StringTokenizer stok2 = new StringTokenizer(msg2,",");
	int dontcare2 = Integer.parseInt(stok2.nextToken());
	int x2=Integer.parseInt(stok2.nextToken());
	int y2=Integer.parseInt(stok2.nextToken());
	BookKeepArray[x2][y2].setLabel(HUMANOPPYouKnowMe);
	BookKeepArray[x2][y2].setEnabled(false);
	t.recordMoveInArray( TicTacToe.HUMANOPP, x2, y2 );
	}catch (SocketException ex) {
            		ex.printStackTrace();
        	} catch (IOException ex) {
           		 ex.printStackTrace();
        	}
            }
        }
        return condition;
    }
    // WindowListener介面的實作
    public void windowClosing( WindowEvent event ) 
    {
        System.exit( 0 );
    }          
    public void windowClosed( WindowEvent event ) { }
    public void windowDeiconified( WindowEvent event ) { }
    public void windowIconified( WindowEvent event ) { }
    public void windowActivated( WindowEvent event ) { }
    public void windowDeactivated( WindowEvent event ) { }
    public void windowOpened( WindowEvent event ) { }
// 處理按鈕點選(button click)事件，注意按錯時的處理方式
    public void actionPerformed( ActionEvent evt )
    {
        if( evt.getSource( ) instanceof Button )
        {   // 棋手每按一下棋盤，就要進行處理
            ( (Button)evt.getSource( ) ).setLabel( computerYouKnowMe ); 
// 外觀改變
            ( (Button)evt.getSource( ) ).setEnabled( false ); // 已下棋步，不能再按
            int i=0; int  j=0; int ii=0, jj=0;
            for( i = 0; i < 3; i++ )
                for( j = 0; j < 3; j++ )
                    if( evt.getSource( ) == BookKeepArray[ i ][ j ] )
	// 在陣列資料結構上記載棋步
                       { t.recordMoveInArray( TicTacToe.COMPUTER, i, j );
				ii=i; jj=j;};
           System.out.println("TestTicTacToe1 : (i,j) "+ii+" "+jj);
           Integer oi = new Integer(ii);  
           Integer oj = new Integer(jj);
           Integer oid = new Integer(TicTacToe.COMPUTER);
           String dataOut = oid.toString()+","+oi.toString()+","+oj.toString();
           int oLength=dataOut.length();   
           byte buffer[]=new byte[oLength];     
           System.out.println(dataOut);
           buffer=dataOut.getBytes();
           try {
	DatagramPacket packet=new DatagramPacket(buffer,oLength,this.addr,1800); 
	System.out.println(this.portNo);
	this.ssocket.send(packet);     
	System.out.println("sent ..., check!");     
	    // 在棋手下完後，檢查勝負，棋盤滿而無勝負就是平手(Draw)
          	 if (GameOverRest( t.ResultIsWin( TicTacToe.COMPUTER ), 
		"嘿!我贏了", true )) return;
 	if( GameOverRest( t.boardIsFull( ), "DRAW", false ) )
                	return;
	// 換Human下
	DatagramPacket packet1=new DatagramPacket(buffer,oLength,this.addr,1600); 
	System.out.println("comp wait receiving ...");
	this.rsocket.receive(packet1); 
	String msg=new String(buffer,0,packet1.getLength());
	System.out.println("收到下面的訊息 : " + msg); 
	StringTokenizer stok = new StringTokenizer(msg,",");
	int dontcare = Integer.parseInt(stok.nextToken());
	int x=Integer.parseInt(stok.nextToken());
	int y=Integer.parseInt(stok.nextToken());
	BookKeepArray[x][y].setLabel(HUMANOPPYouKnowMe);
	BookKeepArray[x][y].setEnabled(false);
	t.recordMoveInArray( TicTacToe.HUMANOPP, x, y );
	} catch (SocketException e) {
            		e.printStackTrace();
        	} catch (IOException e) {
            		e.printStackTrace();
        }
	    // 檢查勝負           
            GameOverRest( t.ResultIsWin( TicTacToe.HUMANOPP ), 
           		"嘿!我輸了", false );
	    // 檢查是否平手(Draw)
            GameOverRest( t.boardIsFull( ), "DRAW", false );
            return; // 等棋手下棋
        }
    }
	// 主程式， 建立新棋盤，開始玩!
    public static void main( String [ ] args )
    {
        Frame f = new TestTicTacToe1("使用O" );
        f.pack( );
        f.show( );
	// 啟始之後，等棋手下棋
    }
    private Button [ ][ ] BookKeepArray = new Button[ 3 ][ 3 ];//開始時棋盤是空的
    private TicTacToe t;
    private String computerYouKnowMe = "O";
    private String HUMANOPPYouKnowMe    = "X";
}
