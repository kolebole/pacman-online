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
    // 決定下一步該圈(O)或叉(X)
    public BestMove TryOneMove( int YouKnowMe )
    {
        int opp;              // 敵方
        BestMove GiveItATry;           // 敵方的最佳對策
        int CurrentResult;       
        int bestRow = 0;
        int bestColumn = 0;
        int value;
	// 呼叫CheckResult()看目前的盤勢，假如還未知勝負，才繼續往下執行
        if( ( CurrentResult = CheckResult( ) ) != UNKNOWN )
            return new BestMove( CurrentResult );

        if( YouKnowMe == COMPUTER )
        {   // 以下棋的人來說，我們會假設他會設法贏
            opp = HUMANOPP; value = HUMANOPP_WIN;
        }
        else
        {   // 以電腦來說，當然也是會設法贏
            opp = COMPUTER; value = COMPUTER_WIN;
        }
	// 每一步可能下的棋步都要考慮到
        for( int row = 0; row < 3; row++ )
            for( int column = 0; column < 3; column++ )
                if( squareIsNOLABEL( row, column ) ) // 只有空棋才能下
                {   // 目前呼叫TryOneMove的那一邊先下一步
                    place( row, column, YouKnowMe );
		    // 對手呼叫TryOneMove選擇一步好棋步
                    GiveItATry = TryOneMove( opp );
		    // 因為只是預測，假設情況下的棋步要還原
                    place( row, column, NOLABEL );
		    // 不管是那一邊，只要呼叫TryOneMove，就要選最佳的棋步
                    if( YouKnowMe == COMPUTER && GiveItATry.val > value ||
                        YouKnowMe == HUMANOPP && GiveItATry.val < value )
                    {
                        value = GiveItATry.val;
                        bestRow = row; bestColumn = column;
                    }
                }
        return new BestMove( value, bestRow, bestColumn );
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
