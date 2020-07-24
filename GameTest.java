import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.lang.*; 

class GameTest{
	/*
	Main method to test the game
	*/
	public static void main (String args[]){
		Battleship game = new Battleship(); //Create new instance of the game
		
		System.out.println("  ____          _    _    _             _      _        ");
		System.out.println(" |  _ \\        | |  | |  | |           | |    (_)       ");
		System.out.println(" | |_) |  __ _ | |_ | |_ | |  ___  ___ | |__   _  _ __  ");
		System.out.println(" |  _ <  / _` || __|| __|| | / _ \\/ __|| '_ \\ | || '_ \\ ");
		System.out.println(" | |_) || (_| || |_ | |_ | ||  __/\\__ \\| | | || || |_) |");
		System.out.println(" |____/  \\__,_| \\__| \\__||_| \\___||___/|_| |_||_|| .__/ ");
		System.out.println("                                                 | |    ");
		System.out.println("                                                 |_|    ");
		
		Scanner scanner = new Scanner(System.in);
		boolean validDifficulty=false;
		String difficulty="";
		
		//Choice of easy or hard difficulty
		System.out.print("Please type \"easy\" or \"hard\" to choose your difficulty > ");
		while (validDifficulty==false){
			difficulty = scanner.nextLine().toLowerCase();
			if (difficulty.compareTo("easy")==0){
				validDifficulty=true;
				System.out.println("You chose easy difficulty");
			}
			else if (difficulty.compareTo("hard")==0){
				validDifficulty=true;
				System.out.println("You chose hard difficulty");
			}
			else{
				validDifficulty=false;
				System.out.print("Invalid difficulty, type easy or hard > ");
			}
		}
		
		game.showBoard(); //Show the boards
		int quit=game.playerShips(); //Player places ships
		if (quit==0){System.exit(0);} //If playerShips returned 0, the user entered quit at some point in the method
		game.cpuShips(); //Cpu places ships
		
		boolean over=false;
		boolean tracking=false; //Cpu is initially not tracking any ships
		int turn=1;
		//While game is not over
		while (!over){
			System.out.println("Turn: " + Integer.toString(turn));
			quit=game.playerTurn(); //Player plays turn
			if (quit==0){System.exit(0);}
			turn+=1;
			over = game.gameOver(); //Check is game is over
			//Brief delay for player to see the result of their turn; code from https://www.baeldung.com/java-delay-code-execution
			try{TimeUnit.SECONDS.sleep(2);}
			catch (InterruptedException ie){Thread.currentThread().interrupt();}
			tracking = game.cpuTurn(tracking, difficulty); //Cpu plays turn, taking the current tracking boolean and returning the new tracking boolean
			over = game.gameOver(); //Check is game is over
		}
		game.checkWinner(); //Check who won
	}
}