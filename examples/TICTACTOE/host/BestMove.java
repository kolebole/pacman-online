// 記錄最佳的下棋位置，包括對應的欄(column)與列(row)
// 
final class BestMove
{
    int row;
    int column;
    int val;
    // val代表目前的盤勢
    public BestMove( int v )
      { this( v, 0, 0 ); }
    public BestMove( int v, int r, int c )
      { val = v; row = r; column = c; }
}
