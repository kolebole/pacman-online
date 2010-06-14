import java.awt.*;
import java.awt.event.*;
public class TestTicTacToe extends Frame implements WindowListener, ActionListener
{
// 啟動棋盤, 等待使用者點按
    public void init( )
    {
        setLayout( new GridLayout( 3, 3 ) );
        for( int i = 0; i < 3; i++ )
           for( int j = 0; j < 3; j++ )
           {
               BookKeepArray[ i ][ j ] = new Button( );
               add( BookKeepArray[ i ][ j ] );
               BookKeepArray[ i ][ j ].addActionListener( this );
	       BookKeepArray[ i ][ j ].setFont(new Font("Serif", Font.BOLD, 72));
           }
        addWindowListener( this );
        newBoard( );
    }
    public TestTicTacToe( )
    {
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
    // 為電腦選一個棋步
    // 若是電腦先下，利用KeepGameInteresting隨機選擇棋步，增加遊戲的變化
    // 否則就要呼叫TryOneMove來選個好棋步
    public void doComputerMove( boolean predictToWin )
    {
        BestMove ComputerMove;

        if( predictToWin )
            ComputerMove = t.TryOneMove( TicTacToe.COMPUTER );
        else
        {
            ComputerMove = new BestMove( 0, KeepGameInteresting % 3, KeepGameInteresting / 3 );
            KeepGameInteresting = ( KeepGameInteresting + 1 ) % 9;
        }

        System.out.println( " 電腦選的列的位置 = " + ComputerMove.row +
                            " 電腦選的行的位置 = " + ComputerMove.column );
BookKeepArray[ ComputerMove.row ][ ComputerMove.column ].setLabel( computerYouKnowMe );
BookKeepArray[ ComputerMove.row ][ ComputerMove.column ].setEnabled( false );
        t.recordMoveInArray( TicTacToe.COMPUTER, ComputerMove.row, ComputerMove.column );
    }
// 遊戲結束時(注意結束的條件), 開始另一盤, 圈叉對調
    public boolean GameOverRest( boolean condition, String message, boolean ComputerMoves )
    {
        if( condition )
        {
            System.out.println( message );
            System.out.println( "重新開始新的一盤遊戲!" );
            newBoard( );
            if( ComputerMoves )
            {
                System.out.println( "電腦先下囉!" );
                computerYouKnowMe = "X";
                HUMANOPPYouKnowMe = "O";
                doComputerMove( false );
            }
            else
            {
                HUMANOPPYouKnowMe = "X";
                computerYouKnowMe = "O";
                System.out.println( "您先下吧!" );
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
            ( (Button)evt.getSource( ) ).setLabel( HUMANOPPYouKnowMe ); 
// 外觀改變
            ( (Button)evt.getSource( ) ).setEnabled( false ); // 已下棋步，不能再按
            for( int i = 0; i < 3; i++ )
                for( int j = 0; j < 3; j++ )
                    if( evt.getSource( ) == BookKeepArray[ i ][ j ] )
			// 在陣列資料結構上記載棋步
                        t.recordMoveInArray( TicTacToe.HUMANOPP, i, j );
	    // 在棋手下完後，檢查勝負，棋盤滿而無勝負就是平手(Draw)
            if( GameOverRest( t.boardIsFull( ), "DRAW", true ) )
                return;
	    // 換電腦下
            doComputerMove( true );
	    // 檢查勝負           
            GameOverRest( t.ResultIsWin( TicTacToe.COMPUTER ), 
"嘿!我贏了", true );
	    // 檢查是否平手(Draw)
            GameOverRest( t.boardIsFull( ), "DRAW", false );
            return; // 等棋手下棋
        }
    }
	// 主程式， 建立新棋盤，開始玩!
    public static void main( String [ ] args )
    {
        Frame f = new TestTicTacToe( );
        f.pack( );
        f.show( );
	// 啟始之後，等棋手下棋
    }
    private Button [ ][ ] BookKeepArray = new Button[ 3 ][ 3 ];//開始時棋盤是空的
    private TicTacToe t;
    private String computerYouKnowMe = "O";
    private String HUMANOPPYouKnowMe    = "X";
}
