public class Ship{
	public int size; //Size of the ship
	public int [] rows; //Array of the rows that the ship is in
	public int [] columns; //Array of the column that the ship is in
	//(columns[i],rows[i]) is the ordered pair of the ith spot that the ship is in
	
	/*
	Ship constructor
	*/
	public Ship(int s){
		size = s;
		rows = new int [s];
		columns = new int [s];
	}
}
