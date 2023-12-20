import java.util.Scanner;
import java.util.*;

class Player{

	private String playerName = "";
	private String password="";
	private int totalGamesPlayed=0;
	private int wins=0;
	private int loss=0;
	
	String getPlayerName(){
		return this.playerName;
	}
	void setPlayerName(String s){
		this.playerName = s;
	}
	String getPassword(){
		return this.password;
	}
	void setPassword(String s){
		this.password = s;
	}
	int getTotalgamesPlayed(){
		return this.totalGamesPlayed;
	} 
	void setTotalgamesPlayed(int n){
		this.totalGamesPlayed = n;
	}
	int getWins(){
		return this.wins;
	}
	void setWins(int n){
		this.wins = n;
	}
	int getLoss(){
		return this.loss;
	}
	void setLoss(int n){
		this.loss = n;
	}
}

interface Game
{
	void showBoard(char [][] board);
	void initialise(char [][] board);
	int declareWinner(int whoseTurn);
	boolean gameOver(char [][] board);
	int playTicTacToe(int whoseTurn);
}

class BoardGame implements Game{
	
	static char COMPUTERMOVE = 'O';
	static char HUMANMOVE = 'X';
	
	static int SIDE = 3;

	static int COMPUTER = 1;
	static int HUMAN = 2;
	static String green="\u001B[32m";
	static String red="\u001B[31m";
	static String yellow="\u001B[33m";

	public void showBoard(char [][] board){
		
		System.out.println();
		
		int count=1;
		for(int i=0;i<SIDE;i++){
			System.out.print("                                                                             ");
			for(int j=0;j<SIDE;j++){
				if(board[i][j]!='X' && board[i][j] !='O'){
					if(j!=0){
						System.out.print(" | ");
					}
					System.out.print(count);
				}
				else{
					if(j!=0){
						System.out.print(" | ");
					}
					if(board[i][j]=='X')
						System.out.print(green+board[i][j]+yellow);
					if(board[i][j]=='O')
						System.out.print(red+board[i][j]+yellow);
				}
				count++;
			}
			if(i!=2){
				System.out.println();
				System.out.println("                                                                             ---------");
			}
			
		}
		
		System.out.println();
		System.out.println();
	}
	public void initialise(char [][] board){
		board[0][0] = '*';
		board[0][1] = '*';
		board[0][2] = '*';
		board[1][0] = '*';
		board[1][1] = '*';
		board[1][2] = '*';
		board[2][0] = '*';
		board[2][1] = '*';
		board[2][2] = '*';
		
	}
	boolean rowCrossed(char board[][]){

		for (int i=0; i<SIDE; i++){
		if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '*')
			return true;
		}
		return false;
	}

	boolean columnCrossed(char board[][]){

		for (int i=0;i<SIDE;i++){
			if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '*')
				return true;
		}
		return false;
	}

	boolean diagonalCrossed(char board[][]){

		if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[1][1] != '*')
			return true;
		if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[1][1] != '*')
			return true;
		return false;
	}
	public int declareWinner(int whoseTurn){
		if (whoseTurn == COMPUTER){
			System.out.println();
			System.out.println("					-------->  You Lose !");
			return 0;
		}	
		else{
			System.out.println();
			System.out.println("					-------->  You won !");
			return 1;
		}
	}
	public boolean gameOver(char [][] board){
		return(rowCrossed(board) || columnCrossed(board) || diagonalCrossed(board) );
	}
	int minimax(char board[][], int depth, boolean isAI){
		int score = 0;
		int bestScore = 0;
		if (gameOver(board) == true){
			if (isAI == true)
				return -10;
			if (isAI == false)
				return +10;
		}
		else{
			if(depth < 9){
				if(isAI == true){
					bestScore = -999;
					for(int i=0; i<SIDE; i++){
						for(int j=0; j<SIDE; j++){
							if (board[i][j] == '*'){
								board[i][j] = COMPUTERMOVE;
								score = minimax(board, depth + 1, false);
								board[i][j] = '*';
								if(score > bestScore){
									bestScore = score;
								}
							}
						}
					}
					return bestScore;
				}
				else{
					bestScore = 999;
					for (int i = 0; i < SIDE; i++){
						for (int j = 0; j < SIDE; j++){
							if (board[i][j] == '*'){
								board[i][j] = HUMANMOVE;
								score = minimax(board, depth + 1, true);
								board[i][j] = '*';
								if (score < bestScore){
									bestScore = score;
								}
							}
						}
					}
					return bestScore;
				}
			}
			else{
				return 0;
			}
		}
		return 0;
	}

	int bestMove(char board[][], int moveIndex){
		int x = -1;
		int y = -1;
		int score = 0;
		int bestScore = -999;
		for (int i = 0; i < SIDE; i++){
			for (int j = 0; j < SIDE; j++) {
		
				if (board[i][j] =='*') {
					board[i][j] = COMPUTERMOVE;
					score = minimax(board, moveIndex+1, false);
					board[i][j] = '*';
					if(score > bestScore){
						bestScore = score;
						x = i;
						y = j;
					}
				}
			}
		}
		return x*3+y;
	}
	public int playTicTacToe(int whoseTurn){
		Scanner input = new Scanner(System.in);
		char board [][] = new char[SIDE][SIDE];
		int moveIndex = 0, x = 0, y = 0;
		initialise(board);
		showBoard(board);
		while((gameOver(board)==false) && (moveIndex!=SIDE*SIDE)){
			int n;
			if (whoseTurn == COMPUTER){
				n = bestMove(board, moveIndex);
				x = n / SIDE;
				y = n % SIDE;
				board[x][y] = COMPUTERMOVE;
				System.out.printf("\n\n COMPUTER has put a %c in cell %d\n\n", COMPUTERMOVE,n+1);
				showBoard(board);
				moveIndex ++;
				whoseTurn = HUMAN;
			}
			else if (whoseTurn == HUMAN){
				System.out.println("					------->  Your Turn ! Select a number from the available cells ");
				System.out.println();
				/*for(int i=0; i<SIDE; i++)
					for (int j = 0; j < SIDE; j++)
						if (board[i][j] == '*')
							printf("%d ", (i * 3 + j) + 1);*/
				System.out.print("					------->  Enter the position = ");
				String temp = "";
				boolean loop = true;
				do{
					temp = input.next();
					if( temp.equals("1") || temp.equals("2") || temp.equals("3") || temp.equals("4") || temp.equals("5") || temp.equals("6") || temp.equals("7") || temp.equals("8") || temp.equals("9")){
						n=(int)(temp.charAt(0)-'0');
						loop = false;
					}
					else{
						System.out.println("		             Please Enter Valid Integer Value");
					}
				}while(loop);
				n = (int)(temp.charAt(0)-'0');
				n--;
				x = n / SIDE;
				y = n % SIDE;
				if(board[x][y] == '*' && n<9 && n>=0){
					board[x][y] = HUMANMOVE;
					moveIndex ++;
					whoseTurn = COMPUTER;
				}
				else if(board[x][y] != '*' && n<9 && n>=0){
					System.out.printf("\n \t\t\t\t\t ------->  Position is occupied, select any one place from the available places\n\n");
				}
				else if(n<0 || n>8){
					System.out.printf("\n \t\t\t\t\t ------->  Invalid position\n");
				}
			}
		}
		if (gameOver(board) == false && moveIndex == SIDE * SIDE){
			System.out.printf("\n					------>  It's a draw\n\n");
			return -1;
		}
		else{
			if (whoseTurn == COMPUTER)
				whoseTurn = COMPUTER;	
			else if (whoseTurn == HUMAN)
				whoseTurn = COMPUTER;
			return declareWinner(whoseTurn);
		}	
		
	}
	
	
}

class StartGame{
	static List<Player> players = new ArrayList<>();
	static StartGame object = new StartGame();
	static Scanner input = new Scanner(System.in);
	static String redbri = "\033[1;91m"; 
	static String reset="\u001B[0m";
	static String bold="\u001B[1m";
	static String blink="\u001B[5m";
	static String red="\u001B[31m";
	static String green="\u001B[32m";
	static String yellow="\u001B[33m";
	static String blue="\u001B[34m";
	static String purple="\u001B[35m";
	static String cyan="\u001B[36m";
	static String white="\u001B[37m";
	static String whitebg="\u001B[47m";
	static String black ="\u001B[30m";
	static String magent = "\u001B[95m";

	static final String ital = "\u001B[3m";
	//bright colors;

	static  String redbr = "\033[0;91m";  
    	static  String greenbr = "\033[0;92m"; 
    	static  String whitebr = "\033[0;97m";
	static String ul = "\u001B[4m";

	void display(){
		Scanner ab=new Scanner(System.in);
		System.out.println("                							                		  	                 ");
		System.out.println("             							                     			 		 ");
		System.out.println("             							                      					 ");
		System.out.println("                							                		  	 		 ");
		System.out.println("             							                     					 ");
		System.out.println("             							                      					 ");
		System.out.println("           			         						                		         ");
		System.out.println("                							 		     		  			 ");
		System.out.println(white+greenbr+blink+magent+"            				           				"+"LETS PLAY"+"  ");
		System.out.println("             							                  			 		 ");
		System.out.println(reset+white+blink+bold+"                			"+"  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	 ");
		System.out.println("					"+"  +	"+"_____         ____	_____	 ______	  ____	  ______   ____    ____ "+"   +  ");
		System.out.println("					"+"  +	"+"  |	 |   |	          |    	 |    |  |          |     |    |  |     "+"   +  ");
		System.out.println("					"+"  +	"+"  |	 |   |            | 	 |____|	 |          |     |    |  |---- "+"   +  "); 
		System.out.println("					"+"  +	"+"  |	 |   |____        |	 |    |  |____      |	  |____|  |____ "+"   +  ");
		System.out.println("        	  			"+"  +	"+"			                     			        "+"   +	 ");
		System.out.println("					"+"  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	 ");		
		System.out.println("             							                     					 ");
		System.out.println("          							                      			           		 ");
		System.out.println("               							                				  	 ");
		System.out.println("             							                     					 ");
		System.out.println("             							                      					 ");

	}
	Player checkPassword(String User,String password){
		for(Player player : players){
			String playerName = player.getPlayerName();
			String playerPassword = player.getPassword();
			if(User.equals(playerName) && password.equals(playerPassword)){
				return player;
			}
		}
		return null;
	}
	void showPlayerStats(Player obj){
		System.out.println(reset+green+" 					-------> Player Name : " + obj.getPlayerName());
		System.out.println("					-------> Total Games Played : " + obj.getTotalgamesPlayed());
		System.out.println("					-------> Total Wins : " + obj.getWins());
		System.out.println("					-------> Total Losses : " + obj.getLoss());
		
		
	}
	void playGame(Player obj){
		
		System.out.println(reset+yellow+"					------->  Do you want to start first ?");
		System.out.println("					------->  Press 1 for 'YES' ");
		System.out.println("					------->  Press 2 for 'NO'");

		String n = "";
		int k = 0;
		boolean loop = true;
		do{
			n=input.next();
			if(n.equals("1")){
				k=1;
				loop = false;
			}
			else if(n.equals("2")){
				k=2;
				loop = false;
			}
			else{
				System.out.println("                                  Please Enter Valid Integer Value !");
			}
			
		}while(loop);
		BoardGame temp = new BoardGame();
		obj.setTotalgamesPlayed(obj.getTotalgamesPlayed()+1);
		if(k==1){
			int f = temp.playTicTacToe(2);
			if(f == 1){
				obj.setWins(obj.getWins()+1);
			}
			else if(f==0){
				obj.setLoss(obj.getLoss()+1);
			}
		}
		else if(k==2){
			int f = temp.playTicTacToe(1);
			if(f == 1){
				obj.setWins(obj.getWins()+1);
			}
			else if(f==0){
				obj.setLoss(obj.getLoss()+1);
			}
		}
		
			
	}
	void gamePage(Player obj){
		
		
		String n = "";
		do{
			System.out.println();
			System.out.println(reset+cyan+"					------>  Select from the options given below ");
			System.out.println("					------>  1 ) Start Game ");
			System.out.println("					------>  2 ) Player Stats ");
			System.out.println("					------>  3 ) Logout ");
			System.out.println("------>  Enter Your Choice : ");
			n = input.next();
			if(n.equals("1")){
				object.playGame(obj);
			}
			else if(n.equals("2")){
				object.showPlayerStats(obj);
			}
		}while(!n.equals("3"));
		
		
	}
	void checkLogin(){
			
			Player obj =null;

			while(obj==null){
				System.out.print(reset+cyan+"					------> Enter UserName : ");
				String user = input.next();
				System.out.println();
				System.out.print("			 		------> Enter Password : ");
				
				String pass = input.next();
				obj = object.checkPassword(user,pass);
				if(obj!=null){
					object.gamePage(obj);
				}
				else{
					System.out.println(reset+white+"					-------> Username or Password is Incorrect");
				}
			
			}
		
			
	}
	void addPlayers(){
	
		Player obj1=new Player();
		obj1.setPlayerName("Navya");
		obj1.setPassword("@1234");
		obj1.setTotalgamesPlayed(0);
		obj1.setWins(0);
		obj1.setLoss(0);

		Player obj2=new Player();
		obj2.setPlayerName("Abhinav");
		obj2.setPassword("#1234");
		obj2.setTotalgamesPlayed(0);
		obj2.setWins(0);
		obj2.setLoss(0);

		Player obj3=new Player();
		obj3.setPlayerName("Sasi");
		obj3.setPassword("$1234");
		obj3.setTotalgamesPlayed(0);
		obj3.setWins(0);
		obj3.setLoss(0);

		Player obj4=new Player();
		obj4.setPlayerName("Prasanna");
		obj4.setPassword("%1234");
		obj4.setTotalgamesPlayed(0);
		obj4.setWins(0);
		obj4.setLoss(0);

		Player obj5=new Player();
		obj5.setPlayerName("Vamsi");
		obj5.setPassword("^1234");
		obj5.setTotalgamesPlayed(0);
		obj5.setWins(0);
		obj5.setLoss(0);
	
		
		players.add(obj1);
		players.add(obj2);
		players.add(obj3);
		players.add(obj4);
	}
	void login(){
		
		String n = "";
		do{
			System.out.println(reset+red+"					------->  Select from the options given below");
			System.out.println("					------->  1 ) Login ");
			System.out.println("					------->  2 ) Quit Game");

			System.out.println("------->  Enter Your Choice : ");
			n = input.next();
			if(n.equals("1")){
				object.checkLogin();
			}
			else if(!n.equals("2")){
				System.out.println("------->  Enter Correct Option ");
			}
		}while(!n.equals("2"));
		
	}
	public static void main(String [] args){
		object.display();
		object.addPlayers();
		object.login();
			
	}	
	
}