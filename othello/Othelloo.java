//import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Container;
import java.lang.Math;
//mobilityyy masih ngacoooo
//stable klo dikelilingin lawan
public class Othelloo extends JFrame implements MouseListener,ActionListener
{
	private final int ROWS = 8;
	private final int COLS = 8;
	private final int GAP1 = 3;
	private final int GAP2 = 3;
	private JButton [][] panel=new JButton[ROWS][COLS];
	private Container container = getContentPane();
	private JPanel screen=new JPanel();
	private JPanel board = new JPanel
     (new GridLayout (ROWS, COLS, GAP1, GAP2));
	int col,row;
	ImageIcon hitam=new ImageIcon("hitam.gif");
	ImageIcon putih=new ImageIcon("putih.gif");
	private Color color1 = Color.BLACK;
	private Color color2 = Color.ORANGE;
	private Color color3 = new Color(0,102,51);
	private Color color4 = Color.WHITE;
	private Color color5 = Color.BLUE;
	JMenuBar mainBar= new JMenuBar();
	JMenu menu1=new JMenu("Menu");
	JMenuItem main=new JMenuItem("Play");
	int colrow[][]=new int[ROWS][COLS];//0 kosong, 1 hitam 2 putih 3 g bisa dklik
	int value=0;
	int pemain;
	boolean flag[][]=new boolean[ROWS][COLS];//1 udah diisi, 2 bisa diklik, 3 g bisa dklik kosong
	int sp1,sp2=0;
	JLabel scoring=new JLabel("<html><center><p>Let's play Othello!!</p><br>Score <br>Black Player <br><html>"+sp1+"<html><p>White player <br><html>"+sp2+"<html><br><br><br></center><html>");
	Font huruf= new Font("Comic Sans MS",Font.BOLD, 18);
	JButton starts= new JButton("START");
	JButton pass= new JButton("PASS");
	int[] legalmove=new int[2];
	JLabel winner=new JLabel("<html><p></p><html>");
	int endgame,thewinner;
	int old;
	int[][] valueBoard=new int[][]{
	
	{99,-8,8,6,6,8,-8,99},
	{-8,-24,-4,-3,-3,-4,-24,-8},
	{8,-4,7,4,4,7,-4,8},
	{6,-3,4,0,0,4,-3,6},
	{6,-3,4,0,0,4,-3,6},
	{8,-4,7,4,4,7,-4,8},
	{-8,-24,-4,-3,-3,-4,-24,-8},
	{99,-8,8,6,6,8,-8,99}
	};
	int[][] bboard=new int[][]{
	{0,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,0},
	{0,0,0,0,1,1,1,0},
	{0,0,0,1,1,0,0,0},
	{0,0,0,2,1,0,0,0},
	{0,0,0,0,1,0,0,0},
	{0,0,0,0,1,0,0,0},
	{0,0,0,0,0,0,0,0},
	};
	int scoreBoard[][]=new int[ROWS][COLS];
	boolean endGame=false;
	int n=0;
	int option=0;
	boolean dangerOption=false;
	public Othelloo()
	{
		super("Othello");
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		container.setLayout(null);
		container.add(screen);
		container.add(board);
		//screen.setLayout(null);
		screen.setBounds(490,50,180,450);
		board.setBounds(20,50,450,450);
		container.setBackground(color4);
		board.setBackground(color1);
		screen.setBackground(color2);
		scoring.setFont(huruf);
		winner.setFont(huruf);
		screen.add(scoring);
		screen.add(starts);
		screen.add(pass);
		screen.add(winner);
		
		for(row=0;row<ROWS;row++)
      {
          for (col = 0;col< COLS; col++)
          {
            panel [row][col] = new JButton ();
            board.add (panel [row][col]);
			panel [row][col].setBackground(color3);
			colrow[row][col]=0;
			flag[row][col]=false;
			if(row==3&&col==3)
			{
			panel [row][col].setIcon(hitam);
			colrow[row][col]=1;
			//mobilityBoard[row][col]=1;
			flag[row][col]=false;
			}
			if(row==3&&col==4)
			{
			panel [row][col].setIcon(putih);
			colrow[row][col]=2;
			//mobilityBoard[row][col]=2;
			flag[row][col]=false;
			}
			if(row==4&&col==3)
			{
			panel [row][col].setIcon(putih);
			colrow[row][col]=2;
			//mobilityBoard[row][col]=2;
			flag[row][col]=false;
			}
			if(row==4&&col==4)
			{
			panel [row][col].setIcon(hitam);
			colrow[row][col]=1;
			//mobilityBoard[row][col]=1;
			flag[row][col]=false;
			}
           }
      }
	  pemain=1;
	  legalmove[0]=-1;
	  legalmove[1]=-1;
	  setJMenuBar(mainBar);
	  mainBar.add(menu1);
	  menu1.add(main);
	  starts.addActionListener(this);
	  main.addActionListener(this);
	}
	int getEnemy(int player)
	{
	int enemy=0;
		if(player==1)
		{
		enemy= 2;
		}
		if(player==2)
		{
		enemy= 1;
		}
		return enemy;
	}
	public void menang(int pemain)
   {
	if(pemain==1)
	{
     winner.setText("<html><center><p><br>The Winner is <p><p>Black Player<p><p>Congrats<p><center><html>");
	 }
	 if(pemain==2)
	{
     winner.setText("<html><center><p><br>The Winner is <p><p>White Player<p><p>Congrats<p><center><html>");
	 }
	 if(pemain==3)
	{
     winner.setText("<html><center><p>Draw<p><center><html>");
	 }
   }
	public int getColum(JButton click)//mendapat kolom
   {
   int kolom=0;
      for( int row=0;row<ROWS;row++)
      {
        for(int col=0;col<COLS;col++)
        {
          if(click==panel[row][col])
          {
		  kolom=col;
          }
        }
      }
      return kolom;
   }
   public int getRow(JButton click)//mendapatkan baris
    {
	int baris=0;
        for( int row=0;row<ROWS;row++)
      {
        for(int col=0;col<COLS;col++)
        {
          if(click==panel[row][col])
          {
		  baris=row;
          }
        }
      }
       return baris;
    }
	public void showScore(int pemain)
	{
		if(pemain==1)
		{
		scoring.setText("<html><center><p>White Player</p><br>Score <br>Black Player <br><html>"+sp1+"<html><p>White player <br><html>"+sp2+"<html><br><br><br></center><html>");
		}
		if(pemain==2)
		{
		scoring.setText("<html><center><p>Black Player</p><br>Score <br>Black Player <br><html>"+sp1+"<html><p>White player <br><html>"+sp2+"<html><br><br><br></center><html>");
		}
	}
	public void checkScore(int pemain)
	{
	int score1=0;
	int score2=0;
	int empty=0;
		 for( int row=0;row<ROWS;row++)
		{
			for(int col=0;col<COLS;col++)
			{
				if(colrow[row][col]==1)
				{
				score1++;
				}
				if(colrow[row][col]==2)
				{
				score2++;
				}
				if(colrow[row][col]==0)
				{
				empty++;
				}
			}
		}
		sp1=score1;
		sp2=score2;
		if(sp1>sp2)
		{
		thewinner=1;
		}
		if(sp2>sp1)
		{
		thewinner=2;
		}
		if(sp2==sp1)
		{
		thewinner=3;
		}
		showScore(pemain);
	}
public int checkCorner(int pemain, int[][]changeBoard)//check corner
{
int corner=0;
if(changeBoard[0][0]==pemain)
{
corner++;
}
if(changeBoard[0][7]==pemain)
{
corner++;
}
if(changeBoard[7][0]==pemain)
{
corner++;
}
if(changeBoard[7][7]==pemain)
{
corner++;
}
return corner;
}	

public boolean checkFrontier(int row1,int col1, int[][] changeBoard)//check corner
{
int frontier=0; 
if(row1<7)
{
if(changeBoard[row1+1][col1]==0)
{
frontier++;
}
}
if(row1>0)
{
if(changeBoard[row1-1][col1]==0)
{
frontier++;
}
}
if(col1<7)
{
if(changeBoard[row1][col1+1]==0)
{
frontier++;
}
}
if(col1>0){
if(changeBoard[row1][col1-1]==0)
{
frontier++;
}}
if(row1<7&&col1<7)
{
if(changeBoard[row1+1][col1+1]==0)
{
frontier++;
}
}
if(row1>0&&col1<7)
{
if(changeBoard[row1-1][col1+1]==0)
{
frontier++;
}
}
if(row1<7&&col1>0)
{
if(changeBoard[row1+1][col1-1]==0)
{
frontier++;
}
}
if(row1>0&&col1>0)
{
if(changeBoard[row1-1][col1-1]==0)
{
frontier++;
}
}
if(frontier>0)
{
return true;
}
else
{
return false;
}
}	

public int frontierDiscs(int pemain, int[][] changeBoard)//check frontier
{
int count=0;
 for( int row=0;row<ROWS;row++)
		{
			for(int col=0;col<COLS;col++)
			{
				if(changeBoard[row][col]==pemain)
				{
						if(checkFrontier(row,col,changeBoard)==true)
						{
							count++;
						}
				}
			}
		}
return count;
}

public boolean checkStable(int row, int col, int pemain, int[][] changeBoard)//check corner
{
int row1=0;
int col1=0;
int lawan=0;
boolean isedge=false;
if(pemain==1)
{lawan=2;}
if(pemain==2)
{lawan=1;}
Boolean[] flag1 = new Boolean[8];//penanda legal moves
Boolean[] flag2 = new Boolean[8];//ketemu item
for(int i=0;i<8;i++)
	{
	flag1[i]=true;
	flag2[i]=false;
	}
if(row+1<8)
{
for( row1=row+1;row1<8;row1++)//vertikal bawah
{
	if(changeBoard[row1][col]==lawan)
	{
		flag2[0]=true;
	}
	if(changeBoard[row1][col]==0)
	{
		flag1[0]=false;
	}
}
}
if(row-1>=0)
{
for(row1=row-1;row1>=0;row1--)//vertikal atas
{
	if(changeBoard[row1][col]==lawan)
	{
		flag2[1]=true;
	}
	if(changeBoard[row1][col]==0)
	{
		flag1[1]=false;
	}
}
}
if(col+1<8)
{
for(col1=col+1;col1<8;col1++)//horizontal kanan
{
	if(changeBoard[row][col1]==lawan)
	{
		flag2[2]=true;
	}
	if(changeBoard[row][col1]==0)
	{
		flag1[2]=false;
	}
}
}
if(col-1>=0)
{
for( col1=col-1;col1>=0;col1--)//horizontal kiri 
{
	if(changeBoard[row][col1]==lawan)
	{
		flag2[3]=true;
	}
	if(changeBoard[row][col1]==0)
	{
		flag1[3]=false;
	}
}
}
row1=row;
col1=col;
if(row-1>0&&col-1>0){//serong kiri atas 
			do{
			--row1;
			--col1;
			if(changeBoard[row1][col1]==lawan)
			{
				flag2[4]=true;
			}
			if(changeBoard[row1][col1]==0)
				{
					flag1[4]=false;
				}
			}
			while(row1>0&&col1>0);
}
row1=row;
col1=col;
if(row+1<8&&col-1>0){//serong kiri bawah
			do{
			++row1;
			--col1;
			if(changeBoard[row1][col1]==lawan)
			{
				flag2[7]=true;
			}
			if(changeBoard[row1][col1]==0)
				{
					flag1[7]=false;
				}
			}
			while(row1<7&&col1>0);
}
row1=row;
col1=col;
if(row-1>0&&col+1<8){
			do{//serong kanan atas 
			--row1;
			++col1;
			if(changeBoard[row1][col1]==lawan)
			{
				flag2[6]=true;
			}
			if(changeBoard[row1][col1]==0)
				{
					flag1[6]=false;
				}
			}
			while(row1>0&&col1<7);
}
row1=row;
col1=col;
if(row+1<8&&col+1<8){//serong kanan bawah
			do{
			++row1;
			++col1;
			if(changeBoard[row1][col1]==lawan)
			{
				flag2[5]=true;
			}
			if(changeBoard[row1][col1]==0)
				{
					flag1[5]=false;
				}
			}
			while(row1<7&&col1<7);
}	

int countStable=0;			
for(int a=0;a<8;a++)
{
		if((flag2[a]==false && flag1[a]==true)||(flag2[a+1]==false&&flag1[a+1]==true))
		{
		countStable++;
		}
	a++;
}
if(countStable==4)
{	
return true;	
}
else
{
return false;
}
}

public int stableDiscs(int pemain, int[][] changeBoard)//check corner
{
int count=0;
 for( int row=0;row<ROWS;row++)
		{
			for(int col=0;col<COLS;col++)
			{
				if(changeBoard[row][col]==pemain)
				{
						if(checkStable(row,col,pemain,changeBoard)==true)
						{
							count++;
						}
				}
			}
		}
return count;
}
public int countDiscs(int pemain, int[][] changeBoard)//check corner
{
int count=0;
 for( int row=0;row<ROWS;row++)
		{
			for(int col=0;col<COLS;col++)
			{
				if(changeBoard[row][col]==pemain)
				{
							count++;
				}
			}
		}
return count;
}


public void checkMove()
{
int pemainlain=0;
	if(pemain==1)
	{
		pemainlain=2;
	}
	if(pemain==2)
	{
		pemainlain=1;
	}
	int countLegal=0;
	for(row=0;row<ROWS;row++)
		{
			for (col = 0;col< COLS; col++)
			{
				if(flag[row][col]==true)
				{
				countLegal++;
				}
			}
		}
	if(countLegal==0)
	{
	legalmove[pemain-1]=0;
	}
	if(countLegal>0)
	{
	legalmove[pemain-1]=pemain;
	}
	if(legalmove[pemain-1]==0)
	{
		checkScore(pemain);
		pemain=pemainlain;
		System.out.println("legal move : "+pemain);
		pass.addActionListener(this);
	}
	
	if(legalmove[0]==0&&legalmove[1]==0)
	{
		System.out.println("stuck");
		checkScore(pemain);
		menang(thewinner);
		endGame=true;
		clearCell();
	}
}
	public boolean checkCell(int row,int col, int pemain, int color, int[][] colrow1)
	{
	int row1=0;
	int col1=0;
	int count=0;
	boolean mobil=false;
	Boolean[] flag1 = new Boolean[8];//penanda legal moves
	Boolean[] repeat = new Boolean[8];//penanda item udah ketemu
	int [] legal=new int[16];
	int pemainlain=0;
	for(int i=0;i<8;i++)
	{
	flag1[i]=true;
	repeat[i]=true;
	}
	for(int i=0;i<16;i++)
	{
	legal[i]=-1;
	}
	//System.out.println(row+" "+col+" "+pemain);
	if(pemain==1)
	{
	pemainlain=2;
	}
	if(pemain==2)
	{
	pemainlain=1;
	}//20
		if(row>1)
		{//10
		if(colrow1[row-1][col]==pemainlain)
		{
		for(row1=row-2;row1>=0;row1--)//VERTIKAL up
		{
			if(colrow1[row1][col]==0)//empty
			{
			flag1[0]=false;//illegal posible
			}
			if(colrow1[row1][col]==pemain)//beda
			{
			if(flag1[0]==true&&repeat[0]==true)
			{
			legal[0]=row1;
			legal[1]=col;
			repeat[0]=false;
			}
			}
		}
		}
		}
		if(row<6)
		{//7
		if(colrow1[row+1][col]==pemainlain)
		{
		for(row1=row+2;row1<=7;row1++)//VERTIKAL down
		{
			if(colrow1[row1][col]==0)//empty
			{
			flag1[1]=false;//illegal posible
			}
			if(colrow1[row1][col]==pemain)//beda
			{
			if(flag1[1]==true&&repeat[1]==true)
			{
			//available(row,col);//legal move
			legal[2]=row1;
			legal[3]=col;
			repeat[1]=false;
			}
			}
		}
		}
		}
		if(col<6){
		if(colrow1[row][col+1]==pemainlain)
		{
			for(col1=col+2;col1<=7;col1++)//HORIZONTAL kanan
			{
			
				if(colrow1[row][col1]==0)
				{
				flag1[2]=false;//illegal posible
				}
				if(colrow1[row][col1]==pemain)
				{
					if(flag1[2]==true && repeat[2]==true)
					{
					//available(row,col);//legal move
					legal[4]=row;
					legal[5]=col1;
					repeat[2]=false;
					}
				}
			}
		}
		}
		if(col>1){
		if(colrow1[row][col-1]==pemainlain)
		{//35
			for(col1=col-2;col1>=0;col1--)//HORIZONTAL kiri
			{
				if(colrow1[row][col1]==0)
				{
				flag1[3]=false;//illegal posible
				}
				if(colrow1[row][col1]==pemain)
				{
					if(flag1[3]==true&&repeat[3]==true)
					{
					legal[6]=row;
					legal[7]=col1;
					repeat[3]=false;
					}
				}
			}
		}
		}
		if(row>1&&col<6)
		{
		if(colrow1[row-1][col+1]==pemainlain)//serong kanan atas
		{
		row1=row-1;
		col1=col+1;
		if(row1>0&&col1<7){
			do{
			--row1;
			++col1;
				if(colrow1[row1][col1]==0)
				{
				flag1[4]=false;//illegal posible
				}
				if(colrow1[row1][col1]==pemain)
				{
					if(flag1[4]==true&&repeat[4]==true)
					{
					legal[8]=row1;
					legal[9]=col1;
					repeat[4]=false;
					}
				}
			}
			while(row1>0&&col1<7);
		}
		}
		}
		if(row<6&&col<6)
		{//70
		if(colrow1[row+1][col+1]==pemainlain)//serong kanan bawah
		{
		row1=row+1;
		col1=col+1;
		if(row1<7&&col1<7)
		{
			do{
			++row1;
			++col1;
				if(colrow1[row1][col1]==0)
				{
				flag1[5]=false;//illegal posible
				}
				if(colrow1[row1][col1]==pemain)
				{
					if(flag1[5]==true&&repeat[5]==true)
					{
					legal[10]=row1;
					legal[11]=col1;
					repeat[5]=false;
					}
				}
			}
			while(row1<7&&col1<7);
		}
		}
		}
		if(row>1&&col>1)
		{
		if(colrow1[row-1][col-1]==pemainlain)//serong kiri atas
		{
		row1=row-1;
		col1=col-1;
		if(row1>0&&col1>0){
			do{
			--row1;
			--col1;
				if(colrow1[row1][col1]==0)
				{
				flag1[6]=false;//illegal posible
				}
				if(colrow1[row1][col1]==pemain)
				{
					if(flag1[6]==true&&repeat[6]==true)
					{
					legal[12]=row1;
					legal[13]=col1;
					repeat[6]=false;
					}
				}
			}
			while(row1>0&&col1>0);
		}
		}
		}
		if(row<6&&col>1)
		{
		if(colrow1[row+1][col-1]==pemainlain)//serong kiri bawah
		{
		row1=row+1;
		col1=col-1;
		if(row1<7&&col1>0){
			do{
			++row1;
			--col1;
				if(colrow1[row1][col1]==0)
				{
				flag1[7]=false;//illegal posible
				}
				if(colrow1[row1][col1]==pemain)
				{
					if(flag1[7]==true&&repeat[7]==true)
					{
					legal[14]=row1;
					legal[15]=col1;
					repeat[7]=false;
					}
				}
			}
			while(row1<7&&col1>0);
		}
		}
		}
	if(color==0)
	{
	for(int a=0;a<16;a++)
		{
			if(legal[a]!=-1)
			{
			panel [row][col].setBackground(color5);
			panel[row][col].addMouseListener(this);
			flag[row][col]=true;
			}
		}
	}
	if(color==1)
	{
	changeColor(row,col,legal,pemain,0,colrow1);//ganti warna
	}
	if(color==2)
	{
	changeColor(row,col,legal,pemain,1,colrow1);//ganti changeBoard
	}
	if(color==3)
	{
		for(int a=0;a<16;a++)
		{
			if(legal[a]!=-1)
			{
			mobil=true;
			}
		}
	}
	return mobil;
}
public void changeColor(int row, int col, int a[], int pemain, int mobil, int[][] colrow1)
	{
	int row1=0;
	int col1=0;
		if(a[0]!=-1)
		{
			for(row1=row-1;row1>a[0];row1--)//VERTIKAL up
			{
				if(mobil==0)
				{
					if(pemain==1)
					{
					panel[row1][col].setIcon(hitam);
					}
					if(pemain==2)
					{
					panel[row1][col].setIcon(putih);
					}
				}
				colrow1[row1][col]=pemain;
			}
		}
		if(a[2]!=-1)
		{
			for(row1=row+1;row1<a[2];row1++)//VERTIKAL down
			{
				if(mobil==0)
				{
				if(pemain==1)
				{
				panel[row1][col].setIcon(hitam);
				}
				if(pemain==2)
				{
				panel[row1][col].setIcon(putih);
				}
				}
				colrow1[row1][col]=pemain;
			}
		}
		if(a[4]!=-1)
		{
			for(col1=col+1;col1<a[5];col1++)//HORIZONTAL kanan
			{
				if(mobil==0)
				{
				if(pemain==1)
				{
				panel[row][col1].setIcon(hitam);
				}
				if(pemain==2)
				{
				panel[row][col1].setIcon(putih);
				}
				}
				colrow1[row][col1]=pemain;
			}
		}
		if(a[6]!=-1)
		{
			for(col1=col-1;col1>a[7];col1--)//HORIZONTAL kiri
			{
				if(mobil==0)
				{
				if(pemain==1)
				{
				panel[row][col1].setIcon(hitam);
				}
				if(pemain==2)
				{
				panel[row][col1].setIcon(putih);
				}
				}
				colrow1[row][col1]=pemain;
			}
		}
		if(a[8]!=-1)
		{
			row1=row;
			col1=col;
		if(row1>a[8]&&col1<a[9]){
			do{
			--row1;
			++col1;
				if(mobil==0)
				{
				if(pemain==1)
				{
				panel[row1][col1].setIcon(hitam);
				}
				if(pemain==2)
				{
				panel[row1][col1].setIcon(putih);
				}
				}
				colrow1[row1][col1]=pemain;
			}
			while(row1>a[8]&&col1<a[9]);
			}
		}
		if(a[10]!=-1)
		{
			row1=row;
			col1=col;
			if(row1<a[10]&&col1<a[11])
			{
			do{
			++row1;
			++col1;
				if(mobil==0)
				{
				if(pemain==1)
				{
				panel[row1][col1].setIcon(hitam);
				}
				if(pemain==2)
				{
				panel[row1][col1].setIcon(putih);
				}
				}
				colrow1[row1][col1]=pemain;
			}
			while(row1<a[10]&&col1<a[11]);
			}
		}
		if(a[12]!=-1)
		{
			row1=row;
			col1=col;
		if(row1>a[12]&&col1>a[13]){
			do{
			--row1;
			--col1;
				if(mobil==0)
				{
				if(pemain==1)
				{
				panel[row1][col1].setIcon(hitam);
				}
				if(pemain==2)
				{
				panel[row1][col1].setIcon(putih);
				}
				}
				colrow1[row1][col1]=pemain;
			}
			while(row1>a[12]&&col1>a[13]);
			}
		}
		if(a[14]!=-1)
		{
			row1=row;
			col1=col;
		if(row1<a[14]&&col1>a[15]){
			do{
			++row1;
			--col1;
				if(mobil==0)
				{
				if(pemain==1)
				{
				panel[row1][col1].setIcon(hitam);
				}
				if(pemain==2)
				{
				panel[row1][col1].setIcon(putih);
				}
				}
				colrow1[row1][col1]=pemain;
			}
			while(row1<a[14]&&col1>a[15]);
			}
		}
	}
	public void clearCell()
	{
		for(row=0;row<ROWS;row++)
				{
					for (col = 0;col< COLS; col++)
					{
					panel [row][col].setBackground(color3);
					panel[row][col].addMouseListener(this);
					flag[row][col]=false;
					}
				}
	}
	public void availableCell(int pemain)
	{
			for(row=0;row<ROWS;row++)
				{
					for (col = 0;col< COLS; col++)
					{
					if(colrow[row][col]==0)
					{
					boolean not=checkCell(row,col,pemain,0,colrow);
					}
					}
				}
	}
	public int countBoard(int[][] changeBoard, int player)
	{
	int value=0;
		for(row=0;row<ROWS;row++)
				{
					for (col = 0;col< COLS; col++)
					{
					if(changeBoard[row][col]==player)
						{
						value+=valueBoard[row][col];
						}
					}
				}
				return value;
	}
	public int checkMobility(int player, int[][] changeBoard)
	{
		int mobility =0;
		int changeBoard1[][]=new int[ROWS][COLS];
		for(row=0;row<ROWS;row++)
				{
					for (col = 0;col< COLS; col++)
					{
						if(changeBoard[row][col]==0)
						{
							if(checkCell(row,col,player,3,changeBoard))
							{
							mobility++;
							}
						}
					}
				}
		return mobility;
	}
	public int evaluateFunction(int[][] colrow1,int player)
	{
	int lawan=0;
	if(player==1)
	{
		lawan=2;
	}
	if(player==2)
	{
		lawan=1;
	}
	int evaluateValue=0;
	int coinParity=0;
	int mobility=0;
	int corner=0;
	int stability=0;
	int utility=0;
	int frontiery=0;
	if((checkMobility(lawan,colrow1)+checkMobility(player,colrow1))!=0)
	{
	mobility=100*(checkMobility(player,colrow1)-checkMobility(lawan,colrow1))/(checkMobility(lawan,colrow1)+checkMobility(player,colrow1));
	}
	else 
	{
	mobility=0;
	}
	if((checkCorner(player,colrow1)+checkCorner(lawan,colrow1))!=0)
	{
	corner=100*(checkCorner(player,colrow1)-checkCorner(lawan,colrow1))/(checkCorner(player,colrow1)+checkCorner(lawan,colrow1));
	}
	else 
	{
	corner=0;
	}
	if((stableDiscs(player,colrow1)+stableDiscs(lawan,colrow1))!=0)
	{
	stability=100*(stableDiscs(player,colrow1)-stableDiscs(lawan,colrow1))/(stableDiscs(player,colrow1)+stableDiscs(lawan,colrow1));
	}
	else 
	{
	stability=0;
	}
	if((frontierDiscs(player,colrow1)+frontierDiscs(lawan,colrow1))!=0)
	{
	frontiery=100*(frontierDiscs(lawan,colrow1)-frontierDiscs(player,colrow1))/(frontierDiscs(player,colrow1)+frontierDiscs(lawan,colrow1));
	}
	else 
	{
	frontiery=0;
	}
	utility=countBoard(colrow1,player)-countBoard(colrow1,lawan);
	evaluateValue=stability+corner+utility+frontiery+mobility;
	return evaluateValue;
	}
	public void printNumber()
	{
		for(int a=0;a<ROWS;a++)
		{
			for(int b=0;b<COLS;b++)
			{
			System.out.print(colrow[a][b]+" ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	public void checkTheWinner()
	{
		for(int a=0;a<ROWS;a++)
		{
			for(int b=0;b<COLS;b++)
			{
			if(colrow[a][b]==0)
			{
			 endgame++;
			}
			}
		}
		availableCell(pemain);
		checkMove();
		if(endgame==0)
		{
		menang(thewinner);
		endGame=true;
		clearCell();
		}
	}
	public List<int[]> generateMoves(int[][] colrow1,int player) 
   {
      List<int[]> nextMoves = new ArrayList<int[]>(); // allocate List
 
      if ( endGame == true ) {
         return nextMoves;   
      }
      for (int row1 = 0; row1 < 8; ++row1) {
         for (int col1 = 0; col1 < 8; ++col1) 
		 {
		 if(colrow1[row1][col1]==0){
            if (checkCell(row1,col1,player,3,colrow1)) {
               nextMoves.add(new int[] {row1, col1});
            }
			}
         }
      }
      return nextMoves;
   }
	private int[] minimax2(int[][] colrow1,boolean isMaximizing, int depth, int pemain, int alpha, int beta) {
      // Generate possible next moves in a list of int[2] of {row, col}.
	  List<int[]> nextMoves= new ArrayList<int[]>();
		boolean playerSkipsMove = false;
		int player = isMaximizing ? 2 : 1 ;
      // mySeed is maximizing; while oppSeed is minimizing
      int score;
      int bestRow = -1;
      int bestCol = -1; 
	  boolean isFinalMove = depth <= 0;
	  if (!isFinalMove) 
	  {
	 // System.out.println("!finalmove");
		 nextMoves = generateMoves(colrow1,player);
	   if (nextMoves.isEmpty())
          {
			playerSkipsMove = true;
			nextMoves = generateMoves(colrow1,getEnemy(player));
		  }
		  isFinalMove = (nextMoves.isEmpty());
	  }
     if (isFinalMove) 
	 {
	// System.out.println("finalmove");
         // Gameover or depth reached, evaluate score
		 score = evaluateFunction(colrow1,2); 
         return new int[] {score, bestRow, bestCol};
      }
	  else {
         for (int[] move : nextMoves) {
		 int changeBoard[][]=new int[ROWS][COLS];
		 for(row=0;row<ROWS;row++)
			{
				for (col = 0;col< COLS; col++)
				{
					changeBoard[row][col]=colrow1[row][col];
				}
			}
			changeBoard[move[0]][move[1]]=player;
			boolean not=checkCell(move[0],move[1],player,2,changeBoard);
			System.out.println("row : "+move[0]+" col: "+move[1]);
			boolean nextIsMaximizing = playerSkipsMove ? isMaximizing : !isMaximizing;
            if (isMaximizing) {  // mySeed (computer) is maximizing player
			//System.out.println("minimaxComputer");
               score = minimax2(changeBoard, nextIsMaximizing, depth - 1, 1, alpha, beta)[0];
               if (score > alpha) {
                  alpha = score;
                  bestRow = move[0];
                  bestCol = move[1];
               }
            } else {  // oppSeed is minimizing player
               score = minimax2(changeBoard, nextIsMaximizing, depth - 1, 2, alpha, beta)[0];
               if (score < beta) {
                  beta = score;
                  bestRow = move[0];
                  bestCol = move[1];
               }
            }
            if (alpha >= beta) {break;}
         }
         return new int[] {(player == 2) ? alpha : beta, bestRow, bestCol};
      }
   }
	public void computerTurn()
	{
		availableCell(pemain);
		checkMove();
		if(pemain==2)
		{
		int[] result = minimax2(colrow, true, 1, 2, Integer.MIN_VALUE, Integer.MAX_VALUE);
		colrow[result[1]][result[2]]=2;
		clearCell();
		panel [result[1]][result[2]].setIcon(putih);
		boolean not=checkCell(result[1],result[2],pemain,1,colrow);
		System.out.println("Heuristic Score : " + result[0] + " Coordinate : " + result[1] + " , " + result[2]);
		System.out.println("Putih piliiiiih :"+result[1]+" "+result[2]);
		if(colrow[0][0]==2)
		{
		valueBoard[0][1]=50;
		valueBoard[1][0]=50;
		valueBoard[1][1]=50;//
		}
		if(colrow[0][7]==2)
		{
		valueBoard[0][6]=50;
		valueBoard[1][7]=50;
		valueBoard[1][6]=50;//
		}
		if(colrow[7][0]==2)
		{
		valueBoard[6][0]=50;
		valueBoard[7][1]=50;
		valueBoard[6][1]=50;
		}
		if(colrow[7][7]==2)
		{
		valueBoard[7][6]=50;
		valueBoard[6][7]=50;
		valueBoard[6][6]=50;
		}
		//checkBestMove();
		checkScore(pemain);
		pemain=1;
		}
		printNumber();
	}
	
	public void actionPerformed(ActionEvent a)
	{
		Object e = a.getSource();
		if(starts==e)
		{
			availableCell(pemain);
			//computerTurn();
			//checkTheWinner();
			scoring.setText("<html><center><p>Black Player</p><br>Score <br>Black Player <br><html>"+sp1+"<html><p>White player <br><html>"+sp2+"<html><br><br><br></center><html>");
		}
		if(pass==e)
		{
			if(pemain==1)
			{
			availableCell(pemain);
			}
			if(pemain==2)
			{
				computerTurn();
				checkTheWinner();
			}
			pass.removeActionListener(this);
		}
		if(main==e)
		{
			for(row=0;row<ROWS;row++)
		{
          for (col = 0;col< COLS; col++)
          {
			panel [row][col].setBackground(color3);
			panel [row][col].setIcon(null);
			colrow[row][col]=0;
			flag[row][col]=false;
			if(row==3&&col==3)
			{
			panel [row][col].setIcon(hitam);
			colrow[row][col]=1;
			}
			if(row==3&&col==4)
			{
			panel [row][col].setIcon(putih);
			colrow[row][col]=2;
			}
			if(row==4&&col==3)
			{
			panel [row][col].setIcon(putih);
			colrow[row][col]=2;
			flag[row][col]=false;
			}
			if(row==4&&col==4)
			{
			panel [row][col].setIcon(hitam);
			colrow[row][col]=1;
			}
           }
      }
	  pemain=1;
	  legalmove[0]=-1;
	  legalmove[1]=-1;
	  endGame=false;
	  sp1=0;
	  sp2=0;
	  scoring.setText("<html><center><p>Let's play Othello!!</p></center><br>Score <br>Black Player <br><html>"+sp1+"<html><p>White player <br><html>"+sp2+"<html><br><br><br><html>");
	  winner.setText("<html><p></p><html>");
		}
	}
	
	public void mouseClicked(MouseEvent e)
	{
		JButton click=(JButton)e.getSource();//getComponent
        int colum=getColum(click);
        int row=getRow(click);
		endgame=0;
		if(flag[row][colum]==true)
		{
		if(pemain==1)
		{
		colrow[row][colum]=1;
		clearCell();
		panel [row][colum].setIcon(hitam);
		boolean not=checkCell(row,colum,pemain,1,colrow);
		checkScore(pemain);
		printNumber();
		pemain=2;
		computerTurn();
		}
		checkTheWinner();
		}
	}
	public void mousePressed(MouseEvent event) 
	{
	}
	public void mouseReleased(MouseEvent event) 
	{
	}
	public void mouseEntered(MouseEvent event) 
	{
	}
	public void mouseExited(MouseEvent event) 
	{
	}
	public static void main(String args[])
	{
		Othelloo display = new Othelloo();
		final int SIZE1 = 700 ;
		final int SIZE2 = 570 ;
		display.setSize (SIZE1, SIZE2);
		display.setVisible (true);
		display.setResizable(false);
		display.setLocation(450,50);
	}
}