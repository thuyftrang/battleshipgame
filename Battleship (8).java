import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.lang.*; 

/* 
Authors: Eric Huber, Trang Do, Ilana Aronson
Date: 12/2/2019
Course name: COM212 Data Structures
*/

public class Battleship{
	/* 
	2D arrays for the player's board, cpu's board, and guess board that holds the player's guesses
	Arrays that hold Ship objects for the player's and cpu's ships 
	*/
	char [][] playerBoard;
	char [][] cpuBoard;
	char [][] guessBoard;
	Ship [] cpuShipArray;
	Ship [] playerShipArray;
	
	//Constructor 
	public Battleship(){
		playerBoard = new char [10][10]; //Boards are 10x10
		cpuBoard = new char [10][10];
		guessBoard = new char [10][10];
		cpuShipArray = new Ship [5]; //Ship arrays are length 5 since there are 5 ships to hold
		playerShipArray = new Ship [5];
	}
	
	/*
	Method to display the player's board and guess board to the user by printing 
	*/
	public void showBoard(){
		//Top of the board with titles and column lables
		System.out.println("");
		System.out.println("                          Your Board                                                          CPU Board");
		System.out.println("");
		System.out.println("      A     B     C     D     E     F     G     H     I     J            A     B     C     D     E     F     G     H     I     J   ");
		System.out.println("    ___________________________________________________________        ___________________________________________________________");
		
		//Each row follows the same structure but with different row values each time through the for loop (from 1 to 9)
		for(int i=0;i<10;i++){
			System.out.println("   |     |     |     |     |     |     |     |     |     |     |      |     |     |     |     |     |     |     |     |     |     |");
			System.out.println(i+"  |  "+playerBoard[i][0]+"  |  "+playerBoard[i][1]+"  |  "+playerBoard[i][2]+"  |  "+playerBoard[i][3]+"  |  "+playerBoard[i][4]+
			"  |  "+playerBoard[i][5]+"  |  "+playerBoard[i][6]+"  |  "+playerBoard[i][7]+"  |  "+playerBoard[i][8]+"  |  "+playerBoard[i][9]+"  |   "+i+"  |  "+guessBoard[i][0]+
			"  |  "+guessBoard[i][1]+"  |  "+guessBoard[i][2]+"  |  "+guessBoard[i][3]+"  |  "+guessBoard[i][4]+"  |  "+guessBoard[i][5]+"  |  "+guessBoard[i][6]+"  |  "+guessBoard[i][7]+
			"  |  "+guessBoard[i][8]+"  |  "+guessBoard[i][9]+"  |");
			System.out.println("   |_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|      |_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|");
		}
		System.out.println("");
	}
	
	/*
	Method to check if the user's entry for ship placement is valid
	Takes a string of the ship's placement that the user entered (ex: "f 6 d"), the length of the ship, and the board that the ship is being placed on (player or cpu)
	*/
	public boolean validPlacement(String placement,int shipLength, char [][] board){
		String[] shipArray; //Array to hold the row value, column value, and direction of the player's ship placement
		int row=0;
		String letter="";
		char character=' ';
		int column=0;
		String direction="";
		
		//Placememnt string must be of length 5, including a letter for the column, number for the row, letter for the direction and spaces between each
		if (placement.length() < 5){return false;}
		
		//Valid rows are integers 0-9, valid columns are letters a-j, and valid directions are u,d,l,r
		boolean validRow = false;
		boolean validColumn = false;
		boolean validDirection = false;
		
		//Arrays of the valid columns, rows, and drections
		char [] validColumns = {'a','b','c','d','e','f','g','h','i','j'};
		char [] validRows = {'0','1','2','3','4','5','6','7','8','9'};
		String [] validDirections = {"u","d","l","r"};
		
		char columnChar=placement.charAt(0); //Column character is the character at index 0 in the user's entry string
		char rowChar=placement.charAt(2); //Row is the character at index 2
		String directionString=String.valueOf(placement.charAt(4)); //Direction is the character at index 4

		//If the column entered by the user is in the validColumns array, it is a valid column
		for (int i=0;i<validColumns.length;i++){
			if (columnChar==validColumns[i]){validColumn = true;}
		}
		//If the row entered by the user is in the validRows array, it is a valid row
		for (int i=0;i<validRows.length;i++){
			if (rowChar==validRows[i]){validRow = true;}
		}
		//If the direction entered by the user is in the validDirection array, it is a valid direction
		for (int i=0;i<validDirections.length;i++){
			if (directionString.compareTo(validDirections[i])==0){validDirection = true;}
		}
		//If the user entry has an invalid column, invalid row, invalid direction, or doesn't have spaces between them, the entry is invalid
		if(validColumn==false || validRow==false || validDirection==false || placement.charAt(1)!= ' ' || placement.charAt(3)!= ' '){return false;}
		
		//At this point, the user's placement is guaranteed to be corrctly specified 
		shipArray = placement.split(" "); //Split the entry into an array that stores the column at index 0, row at index 1, and direction at index 2
		row = Integer.parseInt(shipArray[1]); //Row is at index 1
		letter = shipArray[0]; //Letter (or column) is at index 0
		character = letter.charAt(0);
		column = (int)character - 97; //Convert letter a-j into integers 0-9 by using the character's ascii value
		direction = shipArray[2]; //Direction is at index 2

		//Case where the user is placing a ship with the direction up
		if (direction.compareTo("u")==0){
			//Ship would go off the board, invalid placement
			if (row-shipLength<-1){return false;}
			else{
				//Ship intersects with another ship, invalid placement
				//Ships on the board are represented by '+'
				for (int i=0;i<shipLength;i++){
					if (board[row-i][column] == '+'){return false;}
				}
			}
			//If the ship doesn't go off the board or intersect another ship, placememnt is valid
			return true;
		}
		
		//Direction is down
		else if (direction.compareTo("d")==0){
			if (row+shipLength>10){return false;}
			else{
				for (int i=0;i<shipLength;i++){
					if (board[row+i][column] == '+'){return false;}
				}
			}
			return true;
		}
		
		//Direction is left
		else if (direction.compareTo("l")==0){
			if (column-shipLength<-1){return false;}
			else{
				for (int i=0;i<shipLength;i++){
					if (board[row][column-i] == '+'){return false;}
				}
			}
			return true;
		}
		
		//Direction is right
		else if (direction.compareTo("r")==0){
			if (column+shipLength>10){return false;}
			else{
				for (int i=0;i<shipLength;i++){
					if (board[row][column+i] == '+'){return false;}
				}
			}
			return true;
		}
		else{return false;}
	}
		
	/*
	Method to place a ship on the specified board (either player or cpu)
	Takes the palcement string that the user entered, the ship length, and the board (player or cpu)
	*/
	public Ship placeShip(String placement,int shipLength,char [][] board){
		String[] placeShipArray = placement.split(" "); //Array containing the column, row, and direction
		int placeRow=Integer.parseInt(placeShipArray[1]); //Row is at index 1
		String placeLetter=placeShipArray[0]; //Letter (or column) is at index 0
		char placeCharacter=placeLetter.charAt(0);
		int placeColumn=(int)placeCharacter - 97; //Column letter is converted to an integer by using ascii values
		String placeDirection=placeShipArray[2]; //Direction is at index 2
		Ship currentShip = new Ship(shipLength); //Create new ship object

		//Direction of the ship is up
		if (placeDirection.compareTo("u")==0){
			//For the length of the ship, set spaces to be '+' in decreasing rows
			for (int i=0;i<shipLength;i++){
				board[placeRow-i][placeColumn] = '+';
				//Add the row and column for each spot to the arrays for rows and columns in the ship object
				currentShip.rows[i]=placeRow-i;
				currentShip.columns[i]=placeColumn;
			}
		}
		//Direction of the ship is down
		else if (placeDirection.compareTo("d")==0){
			for (int i=0;i<shipLength;i++){
				board[placeRow+i][placeColumn] = '+';
				currentShip.rows[i]=placeRow+i;
				currentShip.columns[i]=placeColumn;
			}
		}
		//Direction of the ship is left
		else if (placeDirection.compareTo("l")==0){
			for (int i=0;i<shipLength;i++){
				board[placeRow][placeColumn-i] = '+';
				currentShip.rows[i]=placeRow;
				currentShip.columns[i]=placeColumn-i;
			}
		}
		//Direction of the ship is right
		else if (placeDirection.compareTo("r")==0){
			for (int i=0;i<shipLength;i++){
				board[placeRow][placeColumn+i] = '+';
				currentShip.rows[i]=placeRow;
				currentShip.columns[i]=placeColumn+i;
			}
		}
		return currentShip; //Return the ship object
	}
	
	/*
	Method to place a single ship
	Used in the playShips method
	*/
	public int playOneShip(int shipSize, int shipNum){
		Scanner scanner = new Scanner(System.in);
		Ship ship = new Ship(0);
		boolean valid=false;
		String entry="";
		while (valid==false){
			entry = scanner.nextLine().toLowerCase();
			if (entry.compareTo("quit")==0){return 0;} //Return 0 immediately if the user enters quit
			//Check if entry is valid
			valid = validPlacement(entry, shipSize, playerBoard);
			if (valid == false){System.out.print("Invalid placement! Place your ship again > ");}
		}
		//Use placeShip method to place the ship on the player's board
		ship = placeShip(entry, shipSize, playerBoard);
		//Add the ship to the player's array containg his/her 5 ships, at index 0, then display the board
		playerShipArray[shipNum] = ship;
		showBoard();
		return 1;
	}
	
	/*
	Method to allow the user to place all 5 of his/her ships
	*/
	public int playerShips(){
		System.out.println("To place your ships, enter the starting spot and direction (U, D, L, R)");
		System.out.println("For example, entering \"B 1 D\" for your carrier would place the carrier in B1, B2, B3, B4, and B5");
		System.out.println("");
		
		//Place carrier (length 5 ship)
		System.out.print("Place your Carrier (5 spots) > ");
		int quit=playOneShip(5,0);
		if (quit==0){return 0;} //If quit was entered, return 0 immediately
		//Place battleship (length 4 ship)
		System.out.print("Place your Battleship (4 spots) > ");
		quit=playOneShip(4,1);
		if (quit==0){return 0;}
		//Place cruiser (length 3 ship)
		System.out.print("Place your Cruiser (3 spots) > ");
		quit=playOneShip(3,2);
		if (quit==0){return 0;}
		//Place submarine (length 3 ship)
		System.out.print("Place your Submarine (3 spots) > ");
		quit=playOneShip(3,3);
		if (quit==0){return 0;}
		//Place destroyer (length 2 ship)
		System.out.print("Place your Destroyer (2 spots) > ");
		quit=playOneShip(2,4);
		if (quit==0){return 0;}
		
		return 1;
	}
	
	/*
	Method to check if a given hit sinks a ship or not
	Takes the integer for the row, integer for the column, the array of ships (cpu's or player's), and the board (cpu or player)
	*/
	public boolean checkSink(int row, int column, Ship [] shipArray, char [][] board){
		int hitShipIndex=0;
		//Loop through each ship in the array of ships (cpu or player)
		//If the row and column match any space of any of the 5 ships, that index in the array of ships is set to be the hitShipIndex
		//This is the index of the ship that has been hit
		for (int i=0;i<shipArray.length;i++){
			for (int j=0;j<shipArray[i].size;j++){
				if (shipArray[i].rows[j]==row && shipArray[i].columns[j]==column){hitShipIndex=i;}
			}
		}
		//Loop through each pair of rows and columns in the hit ship
		for (int k=0;k<shipArray[hitShipIndex].size;k++){
			if (board[shipArray[hitShipIndex].rows[k]][shipArray[hitShipIndex].columns[k]] == '+'){return false;} //If there is still a '+' character on the board at any of the pairs of rows/columns, the ship has not been sunken
		}
		//If false was not returned, the ship is completely sunk
		return true;
	}
	
	public int cpuDirection(int rowDifference, int columnDifference, int currRow, int currColumn){
		boolean rev=false; //Boolean representing whether the cpu needs to turn around when guessing
		int startRow=currRow; //Initial row is stored
		int startColumn=currColumn; //Initial column is stored
		int returnRow=0;
		int returnColumn=0;
		
		//While the current spot is already a hit, continue iterating based on rowDifference and columnDifference
		while (playerBoard[currRow][currColumn] == 'X'){
			currColumn-=columnDifference;
			currRow-=rowDifference;
		}
		//If the column and row are within the boundaries of the board
		if (currColumn>=0 && currColumn<=9 && currRow>=0 && currRow<=9){
			//If the spot is not already a miss
			if (playerBoard[currRow][currColumn] != 'O'){
				//This is the spot that the cpu will guess
				returnRow=currRow;
				returnColumn=currColumn;
			}
			//If the spot is a miss, reverse
			else if (playerBoard[currRow][currColumn] == 'O'){rev=true;}
		}
		//If the spot is off the board, reverse
		else{rev=true;}
		//If either of the 2 possibilites above cause the cpu to reverse directions
		if (rev==true){
			currRow=startRow; //Set currRow back to the starting row
			currColumn=startColumn; //Set currColumn back toi the starting column
			
			 //Iterate in the opposite direction until a spot is not a hit (an empty spot)
			while (playerBoard[currRow][currColumn] == 'X'){
				currColumn+=columnDifference;
				currRow+=rowDifference;
			}
			returnRow=currRow;
			returnColumn=currColumn;
		}
		if (rowDifference==0){return returnColumn;} //If the row difference is 0, return the current column
		else{return returnRow;} //If the row difference is not 0 (ie, the column difference is 0), return the current row
	}
				
	/*
	Method to implement the computer's strategy in guessing spots
	Takes a boolean called tracking which is true if there is currently a ship that has been hit but hasn't been sunken yet
	*/
	public boolean cpuTurn(boolean tracking, String difficulty){
		if (difficulty.compareTo("easy")==0){tracking=false;} //If difficulty is set to easy, tracking is set to false every cpu turn
		Random random = new Random();
		boolean filled=true; //Initialize boolean to check if the spot that the cpu is attempting to guess at is already filled
		int tryRow=0; //Initialize the row and column that the cpu will guess
		int tryColumn=0;
		Ship target = new Ship(0); //New ship, which is the target ship that the cpu will attempt to sink
		
		/*
		If the cpu is currently tracking a ship, must determine which of the player's 5 ships the cpu is trying to sink
		A target ship must have at least one hit (or else the cpu would not know where it is) and at least one spot that has not been hit (or else the ship would be sunken already)
		*/
		for (int i=0;i<playerShipArray.length;i++){ //Loop through array of player's ships
			for (int j=0;j<playerShipArray[i].size;j++){ //Loop through each pair of coordinates in the ship's parallel row/column arrays
				if (playerBoard[playerShipArray[i].rows[j]][playerShipArray[i].columns[j]]=='X'){ //If there is a 'X' (hit) on the player's board at these coordinates 
					for (int k=0;k<playerShipArray[i].size;k++){ //Loop through the pairs of coordinates again
						if (playerBoard[playerShipArray[i].rows[k]][playerShipArray[i].columns[k]]=='+'){ //If there is an '+' (ship still up) on the player's board
							//Set this ship to be the target ship and tracking to be true
							tracking=true;
							target = playerShipArray[i];
						}
					}
				}
			}
		}
			
		//If the cpu is not currently tracking (either no ships have been hit yet or all ships that have been hit have been sunken)
		if (!tracking){
			//While the spot that the cpu is trying to guess is filled
			while (filled==true){
				//Generate random integers from 0-9 for the row and column
				tryRow = random.nextInt(10);
				tryColumn = random.nextInt(10);
				//If there is an 'X' (hit) or 'O' (miss), filled is true
				if (playerBoard[tryRow][tryColumn]=='X' || playerBoard[tryRow][tryColumn]=='O'){filled=true;}
				//If not, filled is false
				else{filled=false;}
			}
		}
		
		else{
			int hitCount=0; //Integer to count the number of hits that a ship currently has
			int firstHitIndex=-1; //Initalize an integer which will represent the index of the row/column arrays of the first hit 
			int secondHitIndex=-1;
			//Loop through each set of coordinates in the parallel arrays
			for (int l=0;l<target.size;l++){
				//If that spot on the board is 'X', add 1 to the hit count for the ship
				if (playerBoard[target.rows[l]][target.columns[l]] == 'X'){
					hitCount+=1;
					//If this is the first hit located, set the index to be the first hit index
					if (hitCount==1){firstHitIndex=l;}
					//If this is the second hit located, set the index to be the second hit index
					else if (hitCount==2){secondHitIndex=l;}
				}
			}
			
			//If a ship has been hit once
			if (hitCount==1){
				Random randomAdjacent = new Random();
				
				int hitRow=target.rows[firstHitIndex]; //Integer of the row that has the hit 
				int hitColumn=target.columns[firstHitIndex]; //Integer of the column that has the hit
				
				int adjacent=-1; //Initialize an integer which will determine which of the 4 adjacent spots the cpu will guess at
				boolean invalid=true; //Initialize invalid to determine if a spot the cpu guesses is valid
				int newHitRow=-1; //Initialize integer which will be the row that the cpu attemots to guess at
				int newHitColumn=-1;//Initialize integer which will be the column that the cpu attemots to guess at
				
				//While the spot is invalidd
				while (invalid==true){
					adjacent=random.nextInt(4); //Generate random integer (0,1,2, or 3)
					
					//If adjacent is 0, guess the spot 1 row down (visually)
					if (adjacent==0){
						newHitRow=hitRow+1; //Row is the row that was hit plus 1
						//If the row is within the boundaries of the board
						if (newHitRow>=0 && newHitRow<=9){
							//If the spot is not already 'X' or 'O'
							if (playerBoard[newHitRow][hitColumn] != 'X' && playerBoard[newHitRow][hitColumn] != 'O'){
								tryRow=newHitRow; //This is the row the cpu will guess at 
								tryColumn=hitColumn; //This is the column the cpu will guess at 
								invalid=false;
							}
						}
					}
					//If adjacent is 1, guess the spot 1 row up (visually)
					else if (adjacent==1){
						newHitRow=hitRow-1; //Row is the row that was hit minus 1
						if (newHitRow>=0 && newHitRow<=9){
							if (playerBoard[newHitRow][hitColumn] != 'X' && playerBoard[newHitRow][hitColumn] != 'O'){
								tryRow=newHitRow; //This is the row the cpu will guess at 
								tryColumn=hitColumn; //This is the column the cpu will guess at 
								invalid=false;
							}
						}
					}
					//If adjacent is 2, gues the spot 1 column right (visually)
					else if (adjacent==2){
						newHitColumn=hitColumn+1; //Column is the column that was hit plus 1
						if (newHitColumn>=0 && newHitColumn<=9){
							if (playerBoard[hitRow][newHitColumn] != 'X' && playerBoard[hitRow][newHitColumn] != 'O'){
								tryRow=hitRow; //This is the row the cpu will guess at 
								tryColumn=newHitColumn; //This is the column the cpu will guess at 
								invalid=false;
							}
						}
					}
					//If adjacent is 3, guess the spot 1 column left (visually)
					else{
						newHitColumn=hitColumn-1; //Column is the column that was hit minus 1
						if (newHitColumn>=0 && newHitColumn<=9){
							if (playerBoard[hitRow][newHitColumn] != 'X' && playerBoard[hitRow][newHitColumn] != 'O'){
								tryRow=hitRow; //This is the row the cpu will guess at 
								tryColumn=newHitColumn; //This is the column the cpu will guess at 
								invalid=false;
							}
						}
						else{invalid=true;}
					}

				}
			}
			
			//Decision making of the cpu if a ship has been hit more than once
			else{
				//Integers for the first hit row/column and the second hit row/column (used to determine which direction to guess in)
				int firstHitRow=target.rows[firstHitIndex];
				int firstHitColumn=target.columns[firstHitIndex];
				
				int secondHitRow=target.rows[secondHitIndex];
				int secondHitColumn=target.columns[secondHitIndex];
				
				//Integers of the difference between the first row and second row as well as first column and second column
				int rowDiff = firstHitRow - secondHitRow;
				int columnDiff = firstHitColumn - secondHitColumn;
				
				//Initialize the current row to be the second row and current column to be the second column
				int currentRow=secondHitRow;
				int currentColumn=secondHitColumn;
				
				//If the two hits are in the same row
				if (rowDiff==0){
					tryColumn=cpuDirection(rowDiff,columnDiff,currentRow,currentColumn);
					tryRow=currentRow; //Row is unchanged
				}
				//If the two hits are in the same column
				else{
					tryRow=cpuDirection(rowDiff,columnDiff,currentRow,currentColumn);
					tryColumn=currentColumn; //Column is unchanged
				}
			}
		}
		//If the player's board at the spot that the cpu is guessing is a ship spot
		if (playerBoard[tryRow][tryColumn]=='+'){
			//Make that spot an 'X'
			playerBoard[tryRow][tryColumn]='X';
			//Display the board
			showBoard();
			System.out.println("");
			//If that hit sunk a ship
			if (checkSink(tryRow,tryColumn,playerShipArray,playerBoard)){
				System.out.println("Your ship has sunk!");
				//Return false (no longer tracking a ship)
				return false;
			}
			//If it did not sink the ship
			else{
				System.out.println("Your ship has been hit!");
				//Return true (still tracking the ship)
				return true;
			}
		}
		//If it is a miss
		else{
			playerBoard[tryRow][tryColumn]='O';
			showBoard();
			System.out.println("The computer missed!");
			//Return the same value for tracking (if it was not tracking and missed it is still not tracking; if it was tracking and missed it still is tracking)
			return tracking;
		}
	}
	
	/*
	Method to decide where to place cpu ships
	*/
	public void cpuShips(){
		int ar [] = {2,3,3,4,5}; //Array of the 5 ship lengths
		String [] dirArray = {"u","d","l","r"}; //Array of the 4 possible directions
		int cpuRow=0; //Initalize integer of the row and column that the cpu will place a ship at
		int cpuColumn=0;
		String cpuDirection=""; //Initialize direction string that the cpu will use 
		int cpuLength = 0; //Initialize length of the ship that cpu will place
		boolean cpuValid=false; //Boolean of whether a placement is valid or not
		Random random = new Random();
		String cpuPlacementString = ""; //Initialize a string of the entire placement (ex: "f 6 d")
		
		//New ship object
		Ship ship = new Ship(0);
		
		//Loop through each of the lengths in the array above
		for (int i=0;i<ar.length;i++) { 
			cpuValid=false; //Valid is intially set to be false to enter the while loop
			cpuLength=ar[i];
			
			//While a placement is invalid
			while (cpuValid==false){
				cpuRow=random.nextInt(10); //The row is a random integer from 0-9
				cpuColumn=random.nextInt(10); //The column is a random integer from 0-9
				cpuDirection=dirArray[random.nextInt(4)]; //The direction is the element in the direction array at a random index 0-3
				
				String [] columnTitles = {"a","b","c","d","e","f","g","h","i","j"}; //Array of the column labels
				String cpuColumnString = columnTitles[cpuColumn]; //The random column is the element in the array above at the random index
				
				cpuPlacementString = cpuColumnString + " " + Integer.toString(cpuRow) + " " + cpuDirection; //The placement string is the colum, row, and direction separated by spaces (ex: b 2 d)
				
				cpuValid = validPlacement(cpuPlacementString, cpuLength, cpuBoard); //Check if this placement is valid
			}
			//Placement is valid
			ship = placeShip(cpuPlacementString, cpuLength, cpuBoard); //Place the ship on the cpu's bpard
			
			cpuShipArray[i] = ship; //Add the ship object to the cpu's ship array
		}
	}
	
	/*
	Method to check if the game is over
	*/
	public boolean gameOver(){
		//Booleans for if the player has lost or the cpu has lost
		boolean playerLost = true;
		boolean cpuLost = true;
		//Loop through each spot in the player's board
		for (int i=0;i<10;i++){
			for (int j=0;j<10;j++){
				//If there is still a spot with a '+' (unhit ship spot)
				if (playerBoard[i][j] == '+'){playerLost = false;} //Player did not lose
			}
		}
		//Loop through each spot in the cpu's board
		for (int i=0;i<10;i++){
			for (int j=0;j<10;j++){
				//If there is still a spot with a '+' (unhit ship spot)
				if (cpuBoard[i][j] == '+'){cpuLost = false;} //Cpu did not lose
			}
		}
		//If player lost or cpu lost, game is over
		if (playerLost==true || cpuLost==true){return true;}
		//If not, game is not over
		else{return false;}
	}
	
	/*
	Method to check if a user's guess is valid
	Takes a string of the user's guess (ex: j 4)
	*/
	public boolean checkValidGuess(String guess){
		boolean validFirst = false; //Boolean to check if the column is valid
		boolean validSecond = false; //Boolean to check if the row is valid
		char [] validFirstChars = {'a','b','c','d','e','f','g','h','i','j'}; //Valid columns
		char [] validSeconedChars = {'0','1','2','3','4','5','6','7','8','9'}; //Valid rows
		//If length of the guess is less than 3 (column + a space + row), return false
		if (guess.length() != 3){return false;}
		//Check if the user's column entry is in the valid columns array
		for (int i=0;i<validFirstChars.length;i++){
			if (guess.charAt(0)==validFirstChars[i]){validFirst = true;} //Column is valid
		}
		//Check if the user's row entry is in the valid rows array
		for (int i=0;i<validSeconedChars.length;i++){
			if (guess.charAt(2)==validSeconedChars[i]){validSecond = true;} //Row is valid
		}
		//If column is valid, row is valid, and there is a space between the two, the guess is valid
		if(validFirst==true && validSecond==true && guess.charAt(1)==' '){return true;}
		else{return false;}
	}
			
	/*
	Method to play out the player's turn
	*/
	public int playerTurn(){
		Scanner guessScanner = new Scanner(System.in);
		String [] guess; //Array containing the player's guess (column and row)
		int guessRow = 0; //Initialize integer of the guessed row
		String guessLetter = ""; //Initialize string of the guessed column
		char guessCharacter = ' '; //Initialize charcter of the guessed column
		int guessColumn = 0; //Initialize integer of the guessed column
			
		System.out.print("Guess a spot with the letter and number separated by a space (for example \"C 6\") > ");
		String guessStr = guessScanner.nextLine().toLowerCase();
		if (guessStr.compareTo("quit")==0){return 0;}
		
		//While the guess is not valid, get a new guess
		while(checkValidGuess(guessStr) == false){
			System.out.print("Invalid, guess a spot with the letter and number separated by a space (for example \"C 6\") > ");
			guessStr = guessScanner.nextLine().toLowerCase();
			if (guessStr.compareTo("quit")==0){return 0;}
		}
		//Guess is valid, split the string into an array 
		guess = guessStr.split(" ");
	
		guessRow = Integer.parseInt(guess[1]); //Row is the integer at index 1
	
		guessLetter = guess[0]; //Column letter is the string at index 0
		guessCharacter = guessLetter.charAt(0); //Convert string to character
		guessColumn = (int)guessCharacter - 97; //Convert character to integer using ascii values
		
		//If the cpu's board at that spot is a ship
		if (cpuBoard[guessRow][guessColumn] == '+'){
			//Make that spot on the guess board and cpu board an 'X' (hit)
			guessBoard[guessRow][guessColumn] = 'X';
			cpuBoard[guessRow][guessColumn] = 'X';
			showBoard(); //Show the board
			//If this hit sunk a ship
			if (checkSink(guessRow,guessColumn,cpuShipArray,cpuBoard)){System.out.println("Hit and sunk!");}
			//If not a sink
			else{System.out.println("Hit!");}
		}
		//If thaat spot is already an 'X' (hit) on the guess board
		else if (cpuBoard[guessRow][guessColumn] == 'X'){System.out.println("That spot is already a hit!");}
		//If that spot is already an 'O' (miss) on the guess board
		else if (cpuBoard[guessRow][guessColumn] == 'O'){System.out.println("That spot is already a miss!");}
		//The spot is a miss
		else{
			guessBoard[guessRow][guessColumn] = 'O';
			cpuBoard[guessRow][guessColumn] = 'O';
			showBoard();
			System.out.println("Miss!");
		}
		return 1;
	}
	
	/*
	Method to check who won (player or cpu)
	Used only when the game is over and there is guaranteed to be a winner
	*/
	public void checkWinner(){
		boolean playerWon=false;
		//Loop through each spot on the player's board
		for (int i=0;i<10;i++){
			for (int j=0;j<10;j++){
				//If any spot is still '+'(a ship) on the player's board
				if (playerBoard[i][j] == '+'){playerWon = true;} //The player won
			}
		}
		if (playerWon){System.out.println("You win!");}
		else{System.out.println("You lose!");}
	}
}
