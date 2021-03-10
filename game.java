import java.util.Scanner;

class Board
{
    protected int range=0;
    protected Player board[][];
    public Board(int range)
    {
        this.range = range;
        board = new Player[this.range][this.range];
    }

}
//----------------------------------------------------------------------------
class ChessPiece
{
    protected int x, y;                                  //piece x y
    private boolean empty = true;                        //as empty bool true,  as piece black true
    ChessPiece(){}
    public void setCoordinate(int x, int y)              //設定棋子的座標
    {
        this.x = x;
        this.y = y;
        this.empty = false;
    }
}
//----------------------------------------------------------------------------
class Player
{
    private String name;
    private ChessPiece cp;
    private boolean black;
    public Player(String n, boolean black)          //as chesspiece color as black true,otherwise
    {
        this.name = n;
        this.black = black;
    }
    public Player addChessPiece(int x, int y)          //user add piece x,y   return self
    {
        
        cp = new ChessPiece();
        cp.setCoordinate(x, y);
        ChessBoard.recordBoard[x][y] = 1;
        ChessBoard.num++; 
        
        return this;
    }
    public boolean getColor()                      
    {
        return black;
    }
    public ChessPiece get_cp()                     //取得棋子物件
    {
        return cp;
    }
}
//----------------------------------------------------------------------------
class ChessBoard extends Board
{
    protected static int recordBoard[][], num=0;                     //紀錄棋盤有沒有被下過, 目前下棋總個數   
    public ChessBoard(int range)
    {
        super(range);
        recordBoard = new int[range][range];
    }
    public void add(Player p)                                        //棋盤存放每個使用者輸入的狀況
    {    
        board[ p.get_cp().x ][p.get_cp().y] = p;
    }
    public void display()
    {
        System.out.print("   ");
        for(int index=0; index<this.range;index++)                   //顯示最上面欄位的index值
        {
            System.out.print(index + "  ");
        }
        System.out.println();
        //----------------------------------------------------------------------------
        for(int row=0; row<this.range; row++)
        {
            System.out.print(row + " ");

            for(int column=0; column<this.range; column++)
            {
                
                if(board[row][column] != null)                      //判斷目前位置是否為空值
                {
                    if(board[row][column].getColor() == true)       //true為black, false為white
                    {
                        System.out.print(" ● ");
                    }
                    else
                    {
                        System.out.print(" ○ ");
                    }
                }
                else
                    System.out.print("   ");
            }
            System.out.println();
        }
    }
    public int check()
    {
        return judge();
    }
    
    protected int judge()
    {
        for(int col=0; col<this.range; col++)                               //直線判斷
        {
            for(int x=0; x<= (this.range%5); x++)
            {
                int lineB = 0, lineW=0;
                for(int row=0+x; row<(this.range-(this.range%5)+x); row++)
                {
                    
                    if(board[row][col] != null)                            //判斷此欄位是否為空
                    {
                        if(board[row][col].getColor() == true)
                        {
                            lineB++;
                        }
                        else if(board[row][col].getColor() == false)
                        {
                            lineW++;
                        }
                            
                    }
                    
                }
                if(lineB == 5)                     //black line
                {
                    return 1;
                }
                else if(lineW == 5)               //white line
                {
                    return -1;
                }
                
                System.out.println();
            }
        }
        //------------------------------------------------------------------
        for(int row=0; row<this.range; row++)                                   //橫線判斷
        {
            for(int y=0; y<= (this.range%5); y++)
            {
                int lineB = 0, lineW=0;
                for(int col=0+y; col<(this.range-(this.range%5)+y); col++)
                {
                    if(board[row][col] != null)            //if black ++
                    {
                        if(board[row][col].getColor() == true)
                        {
                            lineB++;
                        }
                        else if(board[row][col].getColor() == false)
                        {
                            lineW++;
                        }
                            
                    }
                }
                if(lineB == 5)                     //black line
                {
                    return 1;
                }
                else if(lineW == 5)               //white line
                {
                    return -1;
                }
               
                System.out.println();
            }
        }
        //------------------------------------------------------------------
        for(int row=0;row<this.range;row++)                                   //斜線判斷
        {
            for(int col=0;col<this.range;col++)
            {
                int lineB=0, lineW=0, lineB2=0, lineW2=0;                    //lineB是左斜線, lineB2是右斜線
                for(int index=0;index<this.range;index++)
                {
                    
                    if(row+index < this.range && col+index < this.range)                 //左斜線尋找
                    {
                        if(board[row+index][col+index] != null)
                        {
                            if(board[row+index][col+index].getColor() == true)
                            {
                                lineB++;
                            }
                            else
                            {
                                lineW++;
                            }
                        }                  
                    }
                    //----------------------------------------------------------------------
                    if(row+index < this.range && col-index >= 0)                        //右斜線尋找
                    {
                        if(board[row+index][col-index] != null)
                        {
                            if(board[row+index][col-index].getColor() == true)
                            {
                                lineB2++;
                            }
                            else
                            {
                                lineW2++;
                            }
                        }                  
                    }    
                
                }
                //---------------------------------------------------------------------
                if(lineB == 5 || lineB2 == 5)
                {
                    return 1;
                }
                else if(lineW == 5 || lineW2 == 5)
                {

                    return -1;
                }
                System.out.println();
            }
        }
        return 0;
    }
}
//----------------------------------------------------------------------------

public class game {
    public static void main(String args[])
    {
        Scanner scn = new Scanner(System.in);
        Player p[] = new Player[2];                                 //建立兩位使用者       
        String s[] = new String[2];                                 //存放使用者id

        ChessBoard cb;                                              //棋盤  
        boolean color;                                              //棋子顏色
        int flat = 0, n=0;                                          
        //----------------------------------------------------------------------------
        for(int user=0;user<2;user++)
        {
            System.out.print("輸入第" + (user+1) + "位使用者名稱:");
            s[user] = scn.next();
            System.out.print("輸入黑白棋顏色,黑為true 白為false:");
            color = scn.nextBoolean();
            
            p[user] = new Player(s[user],color);
        }
        //----------------------------------------------------------------------------
        System.out.print("設定n*n的棋盤,請輸入單一個n值:");
        n = scn.nextInt();
        cb = new ChessBoard(n);
        //----------------------------------------------------------------------------
        while(true)
        {
            cb.display();
            System.out.print(s[flat] + "請輸入第x,y的棋子位置:");
            int x = scn.nextInt();
            int y = scn.nextInt();
            if(ChessBoard.recordBoard[x][y] == 1)
            {
                System.out.println("此位置已被下過,請重新選擇!!");
                continue;
            }
            //----------------------------------------------------------------------------
            if(flat == 0)                                          //輪流輸入
            {
                
                cb.add( p[flat].addChessPiece(x, y));
                 
            }
            else
            {
                cb.add( p[flat].addChessPiece(x, y));
            }
            //----------------------------------------------------------------------------
            if(cb.check() == 1)                            //user 1 win
            {
                System.out.println(s[flat] + "Win!!!");
                break;
            }
            else if(cb.check() == -1)                      //user 2 win
            {
                System.out.println(s[flat] + "Win!!!");
                break;
            }
            if(cb.num == n*n)
            {
                System.out.println("Equal!!!");
                break;
            }
            //----------------------------------------------------------------------------
            if(flat == 1)                                 //切換
                flat=0;
            else
                flat=1;
        }
        cb.display();
        System.out.println("Game over!!!");
    }
    
}
