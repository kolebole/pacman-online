class TicTacToe
{   // 用一些常數值的名稱讓程式碼更容易閱讀
    public static final int HUMANOPP        = 0; 
    public static final int COMPUTER     = 1;
    public static final int NOLABEL        = 2; // 棋盤位置是空的，可以下
    public static final int HUMANOPP_WIN    = 0;
    public static final int DRAW         = 1; // 平手
    public static final int UNKNOWN      = 2; // 還未知勝負
    public static final int COMPUTER_WIN = 3;
    public TicTacToe( )
    {
        clearBoard( );
    }
    public int [ ] [ ] getBoard( )
    {
        return board;
    }
    // 下手, 同時檢查下的位置是否還是空位, 或是否有超出棋盤範圍
    public boolean recordMoveInArray( int YouKnowMe, int row, int column )
    {
        if( row < 0 || row >= 3 || column < 0 || column >= 3
                || board[ row ][ column ] != NOLABEL )
            return false;
        board[ row ][ column ] = YouKnowMe;
        return true;
    }
    // 其他相關的有用程序
    public void clearBoard( )
    {
        for( int i = 0; i < 3; i++ )
            for( int j = 0; j < 3; j++ )
                board[ i ][ j ] = NOLABEL;
    }
    public boolean boardIsFull( )
    {
        for( int row = 0; row < 3; row++ )
            for( int column = 0; column < 3; column++ )
                if( board[ row ][ column ] == NOLABEL )
                    return false;
        return true;
    }
    boolean ResultIsWin( int YouKnowMe )
    {
        int row, column;

        // 查看是否有整列排成的情況
	for( row = 0; row < 3; row++ )
	{
            for( column = 0; column < 3; column++ )
                if( board[ row ][ column ] != YouKnowMe )
                    break;
            if( column >= 3 )
                return true;
	}
	// 查看是否有整行排成的情況
	for( column = 0; column < 3; column++ )
	{
            for( row = 0; row < 3; row++ )
                if( board[ row ][ column ] != YouKnowMe )
                    break;
            if( row >= 3 )
                return true;
	}
	// 查看是否有對角線排成的情況
        if( board[ 1 ][ 1 ] == YouKnowMe && board[ 2 ][ 2 ] == YouKnowMe
			&& board[ 0 ][ 0 ] == YouKnowMe )
            return true;

        if( board[ 0 ][ 2 ] == YouKnowMe && board[ 1 ][ 1 ] == YouKnowMe
			&& board[ 2 ][ 0 ] == YouKnowMe )
            return true;

        return false;
    }
    private int [ ] [ ] board = new int[ 3 ][ 3 ];
	// 下手
    private void place( int row, int column, int piece )
    {
        board[ row ][ column ] = piece;
    }
    
    private boolean squareIsNOLABEL( int row, int column )
    {
        return board[ row ][ column ] == NOLABEL;
    }
// 確認目前的戰況
    private int CheckResult( )
    {
        return ResultIsWin( COMPUTER ) ? COMPUTER_WIN :
               ResultIsWin( HUMANOPP ) ? HUMANOPP_WIN :
               boardIsFull( )? DRAW : UNKNOWN;
    }
}
