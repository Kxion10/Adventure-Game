package journeygame;

import java.util.Locale;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class JourneyGame {
	
	  // in this game you are suppose to escape the dungeon.
	// you start in the starting room and you make choices to escape.
	// if you make the wrong choices you lose health. When your HP hits 0 the game ends.
	// i used HP for my variable that influence the success and I plan to use an item array to store items.
	// game saves floor counter and starts at the room you saved.
	
	//Attributes------------------------------------------------------------------------------------------
	
	//floorcount
	static int floorcounter=0;
	
	//default floor
	static int defaultfloorcounter = 0;
	
	//Items array
	String [] items;
	
	String [] myitems = new String[8];
	
	//initialize HP
	int HP;
	
	//Default HP on start
	int defaultHP=100;
	
	//---------------------------------------------------------------------------------------------------------------------------------
	
	//Accessor methods
	
	//getter for defaultfloorcounter
		public int getdefaultFloorcounter()
		{
			return defaultfloorcounter;
		}
		
		public void setdeafaultFloorcounter(int defaultfloorcounter)
		{
			this.defaultfloorcounter = defaultfloorcounter;
		}
	
	//getter for floorcounter
	public int getFloorcounter()
	{
		return floorcounter;
	}
	
	public void setFloorcounter(int floorcounter)
	{
		this.floorcounter = floorcounter;
	}
	//getter method for  HP
	public int getHP()
	{
		return HP;
	}
	
	//setter method
	public void setHP(int HP)
	{
	this.HP = HP;	
	}
	
	//getter for items
	public String[] getItems()
	{
		return items;
	}
	
	//setter for items
	public void setItems(String[] items)
	{
		this.items = items;
	}
	
	//getter for default HP
	public int getDefaultHP()
	{
		return defaultHP;
	}
	public void setDefaultHP(int defaultHP)
	{
		this.defaultHP= defaultHP;
	}

//-------------------------------------------------------------------------------------
	
	 //Starts the game
    private void startGame(JourneyGame currentGame){
        //Let the user know the game is started
        System.out.println("\n------------------------------------------------------------\nWelcome to the Journey Dungeon!\n------------------------------------------------------------\nYour goal is to reach an exit " +
                "\nfor an epic journey of escape.\n" +
                "You have " + currentGame.getHP() + " HP.\n" +
                "You are in in room " +floorcounter+ ". \n------------------------------------------------------------\n");

    } //end start game method-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //Ends the game---------------------------------------------------------------------------------------------------------------------------------------------------
    private static void endGame(){
        //Let the user know the game is over
        System.out.println("\n--------------------\nGame Over\n--------------------\n");
       

        //Exit the program (0 means the program exited normally)
        System.exit(0);
    }


    //THis method saves the user's progress---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void saveGame (JourneyGame currentGame) {
        //create a new saved game file

    	
        File savedGame = new File("savedgame.txt");


        //If the file already exists on the hard drive, delete it so we can start with a fresh file.
        if(savedGame.exists()) {
            savedGame.delete();
        }
        
        //if hp = 0 delete the game
        if (HP==0) 
        {
        	savedGame.delete();
        }

       
        
        //Use a try catch block to handle IOExceptions thrown when accessing the file
        try {
            //Create a new writer to write the information to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter("savedgame.txt"));

            //Write the user's number of lives to the file (convert the int to a String)
            writer.write(Integer.toString(currentGame.getHP())+"\n");
            
            writer.write(Integer.toString(floorcounter));
            

            //Close the file
            writer.close();                             
            //Tell the user the game was saved
            System.out.println("\n --- Game saved! ---- \n");

        }catch(IOException e){
            //Display an error message and the stack trace
            System.out.println("An error occurred while saving the game.\n");
            e.printStackTrace();
        }//end try catch

    }//end save game method----------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //THis method loads the user's progress (amount of HP they have left)--------------------------------------------------------------------------------------------
    private JourneyGame loadGame(JourneyGame currentGame) {

        //Read in the saved game file
        try { //Use a try catch block to handle IOExceptions thrown when accessing the file

            //Check to see if a file with a saved game exists
            File savedGame = new File("savedgame.txt");
            if(savedGame.exists()) {

                //Create a new scanner to read information from the file
               Scanner scanner = new Scanner(new File("savedgame.txt"));

                //Read in the amount of HP as an integer
               int savedHP = scanner.nextInt();
               int savedFloorcounter = scanner.nextInt();
             
               if (floorcounter==7) {
            	   currentGame.setFloorcounter(currentGame.getdefaultFloorcounter()); 
               }
              
             if(savedHP == 0) {
            	 currentGame.setFloorcounter(currentGame.getdefaultFloorcounter());
             }else {
              currentGame.setFloorcounter(savedFloorcounter);}
               
               //If the saved HP is zero, reset to the default amount of 100
                //Else set the HP to the saved HP
                if(savedHP == 0) {
                    currentGame.setHP(currentGame.getDefaultHP());
                }else{
                    currentGame.setHP(savedHP);
                }//end if
            }else{
                //There is no saved game, so user starts with default 100 HP
                currentGame.setHP(currentGame.getDefaultHP());
                currentGame.setFloorcounter(currentGame.getdefaultFloorcounter());
            }//end if

        }catch (IOException e) {
            //Display an error message and the stack trace
            System.out.println("An error occurred while loading the game.\n");
            e.printStackTrace();

        }//end try catch

        return currentGame;
    }//end load game method---------------------------------------------------------------------------------------------------------------
	
	
	//parameter for what moving rooms mean
	private JourneyGame movetoaroom(String roomName, String roomDescription, String resultY, String resultN, Boolean loseHP,Boolean items, JourneyGame currentGame, Boolean floorcount)
	{
		 System.out.println("\n=========================================================\n" +
                 roomName +
                 "\n=========================================================\n");

 //Print the description of the room
 System.out.println(roomDescription);

 //Create a new scanner and Get input from the user
 Scanner scanner = new Scanner((System.in));
 String userInput = scanner.next();
 if (userInput.equals("Y")) { // if input is equal to Y then this happens

     System.out.println(resultY);

     if(loseHP) {
         //If the user still has HP left, subtract some
         int HP = currentGame.getHP();
         if(HP > 0) {
             HP = HP-25;
             currentGame.setHP(HP);

             //Show HP
             System.out.println("\nYou have " + currentGame.getHP() + " HP left." +
                     "\n=========================================================\n");

             if(currentGame.getHP()==0) { //If the user ran out of HP, save and end game
                 //Save the game
                 saveGame(currentGame);

                 //If HP equals 0 game over
                 endGame();
             }
             
       
         }//end if
     }//end if

 }else if(userInput.equals("N")){ //If the user enters N, show the N result
     System.out.println("\n=========================================================\n" +
             resultN +
             "\n=========================================================\n");
 } else if(userInput.equals("Q")){  //If the user entered Q to quit
     //Save the game
     saveGame(currentGame);

     //End the game
     endGame();

 } 
 else 
 { //If the user doesn't enter Y, then show a message
     System.out.println("You continue on through the dungeon.");
 }//end if
 
 //items adding items into an array
 if (items)
 	{
	myitems[0]="torch";
	myitems[1]="nothing";
	myitems[2]="nothing";
	myitems[3]="nothing";
	myitems[4]="nothing";
	myitems[5]="berries";
	myitems[6]="nothing";
	myitems[7]="Good ending";
	

	System.out.println("You picked up the "+myitems[floorcounter]+".");
	
 	
 	}
 if (floorcount) {
	 floorcounter++;
 }
 
 return currentGame;
	}//end movetoaroom method--------------------------------------------------------------------------------------------------------------------------------------------

//End of class setup---------------------------------------------------------------------------------------------------------------------

public static void main(String[] args)throws IOException {
    //Create a new instance of the journeygame game
    JourneyGame currentGame = new JourneyGame();

    //Set the starting HP to 100
    currentGame.setDefaultHP(100);
    
    //Set the starting floor counter
    currentGame.setdeafaultFloorcounter(0);

    //Load the saved game
    currentGame = currentGame.loadGame(currentGame);

    //Start the game
    currentGame.startGame(currentGame);
    
    if(floorcounter == 0 ) {
    
					    //Show the first stop to the user
					    currentGame = currentGame.movetoaroom("Unknown Room",
					            "The Room is dark, but you see a Single torch on the wall. " +
					                    "Do you leave the room? Y/N or enter Q to quit.",
					            "You leave the room, but it's too dark to see. You Hit your head on the door somehow. Anyways, go grab the torch.",
					            "You try to stay but leave anyways because you don't know how long you should stay. You recogize the need for light.",
					            true,
					            true,
					            currentGame,
					            true);
					    
    						}
    if (floorcounter == 1) {
					    //Show the second stop to the user
					    currentGame = currentGame.movetoaroom("HallWay",
					            "You enter the hallway. It's spooky. You come to a forked road. There are 2 doors. " +
					                    "Do you want to go to the Left room or Right room? Y is left / N is right or enter Q to quit.",
					            "You decide to go to the left, but the door handle falls off as you grab it. The door handle slips and hits your foot. You take the right door anyways.",
					            "The door laughs as you open it. Too bad the door doesn't know you can't hear it through this text.",
					            true,
					            false,
					            currentGame,
					            true);
    }
    
    if(floorcounter == 2) {
					    //Show the third stop to the user
					    currentGame = currentGame.movetoaroom("Armory",
					            "Empty husks of armor stand around. The weapons are dull and not worth picking up. You come up to a set of doors. The right door has an empty set of armor standing infront of it." +
					                    "The left door hangs on only it's top hinges. Do you go try the right or left door? Y-right/ N-left or enter Q to quit.",
					            "You pick the door on the right. The armor that was suppose to be empty throws you through the left door. Of course, you got hurt. ",
					            "You pick the door on the left. The door falls as you approach, but you dodge with easy as you expected it to fall. You procceed as normal.",
					            true,
					            false,
					            currentGame,
					            true);
    }
    if(floorcounter == 3) {
					    //Show the fourth stop to the user
					    currentGame = currentGame.movetoaroom("Barracks",
					            "You enter the barracks. You notice that there is broken bed frames all over the ground. " +
					                    "Do sneak through the debris or traverse the edge of the room?  Y-Sneak/ N-Traverse edge or enter Q to quit.",
					            "You make the poor decision to go through the debris. You get a spliniter from the debris on the ground.",
					            "Of course you walk around the debris. You pass unharmed and go to the next room.",
					            true,
					            false,
					            currentGame,
					            true);
    }
					
if (floorcounter == 4) {
					    //Show the fifth stop to the user
					    currentGame = currentGame.movetoaroom("Outside the dungeon",
					            "You have made it outside, but are surrounded by forest and there is only a striaght path to go. " +
					                    "You notice 2 guards and the are both empty suits of armor. You could probably take them on." +
					                    " Do you brawl them or sneak past them. Y-brawl / N-Sneak or enter Q to quit.",
					            "You decide to brawl them while unarmed. I mean you win the brawl, but at what cost. Your HP of course.",
					            "You decide to sneak past them like a worm. It worked like a charm.",
					            true,
					            false,
					            currentGame,
					            true);
						}
					
if (floorcounter == 5) {
					  //Show the sixth stop to the user
					    currentGame = currentGame.movetoaroom("A medow infront of a Lake",
					            "The path takes you to a medow with a lake at the edge. " +
					                    "You are starving and see berries by the lake. You have never seen such beauty in food." +
					                    "Do you settle for drinking lake water or eat the barries? Y-lake water/N-berries or enter Q to quit.",
					            "You decide that water is best and you leave the berries and only drink water. The water upsets your tummy. " +
					                    "\nYou wonder if you can go on.",
					            "The berries taste great and you almost feel like youhave super powers. You follow the edge of the lake to a path.",
					            true,
					            true,
					            currentGame,
					            true);
						}
					
if(floorcounter == 6) {
					    //Show the final destination to the user
					    currentGame = currentGame.movetoaroom("The Exit",
					            "You made it to the end and honestly thats good enough for this run. " +
					                    "The gate opens for you. Do you leave? Y/N or enter Q to quit.",
					            "You walk out and this is the end. You have escaped." +
					                    "\nYou win and this is over. Good job.\n" +
					                    "After a long walk you hitch hike and a carriage picks you up." +
					                    "\nThe carriage people feed you good food and let you sleep on the way back to your house. ",
					            "Jokes on you, a hero saves you as you decide to stay and you get to leave anyways.\n" +
					                    "After that, you find a nice village that takes care of you.",
					            false,
					            false,
					            currentGame,
					            true);
					    
					    
}
					
					//end the game
					endGame();
					
	}
					}//end main method

					    



