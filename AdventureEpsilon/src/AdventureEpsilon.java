import javax.swing.SingleSelectionModel;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Scanner;
//NOTA: Any class or interface you will create must be into the 
//public class AdventureEpsilon 
public class AdventureEpsilon {
	
	public static GameRoom theRoom = new GameRoom();
	public static Box theBox = new Box();
	public static Sign theSign = new Sign();
	public static Book theBook = new Book();
	public static Apple theApple = new Apple();
	

	public interface GameThing
	{
		String getName();
		String getDescription();
	}
	
	public interface Readable 
	{
        void read();
	} 
	
	public interface Openable 
	{
        void open();

        void close();
	} 
	public interface Edible 
	{
        void eat();
	}

	public static class GameRoom
	{
		private ArrayList<GameThing> roomContents = new ArrayList<GameThing>();

		/** Add something to the room */
		public void addThing(GameThing thing)
		{
			//Since ArrayList objects allow us to add null values,
			//we have to make sure that we act only if we received
			//a non-null reference
			if (thing != null)
			{
				//Since ArrayList objects allow us to add the same value
				//multiple times, we have to make sure that we act only
				//if the thing is not already in the contents
				if (!roomContents.contains(thing))
				{
					//OK, let's add the thing then!
					roomContents.add(thing);					
				}
			}
		}

		/** Remove something from the room */
		public void removeThing(GameThing thing)
		{
			//Fortunately, if we try to remove something from an
			//ArrayList object that is not there, Java will not
			//generate an error. Thus, we don't need to check
			//to see if the thing is already in the contents
			roomContents.remove(thing);
		}

		/** Display the list of things in the room */
		public void listContents()
		{
			if (roomContents.isEmpty())
			{
				System.out.println("There is nothing here!");
			}
			else
			{
				System.out.println("You see the following:");
				int counter = 1;
				for (GameThing thing: roomContents)
				{
					System.out.println(counter + ". " + thing.getName());
					counter++;
				}
			}
		}

		/** Check to see whether a particular thing is currently in the room */
		public boolean hasThing(GameThing thing)
		{
			return roomContents.contains(thing);
			//Note: The above statement is the same as the following code,
			//except that it is shorter. It's just a matter of preference.
			//    if (roomContents.contains(thing))
			//    {
			//	      return true;
			//    }
			//    else
			//    {
			//	      return false;
			//    }
		}

		/** Get the amount of things in the room */
		public int getThingCount()
		{
			return roomContents.size();
		}

		/** Get the thing with the indicated index number */
		public GameThing getThingByIndex(int thingChoice)
		{
			if (thingChoice >= 1 && thingChoice <= roomContents.size())
			{
				//ArrayLists begin counting things at 0
				//But we present the list of things to the user starting at 1
				//Therefore, we need to subtract one from the user's thing choice
				return roomContents.get(thingChoice - 1);
			}
			else
			{
				return null;
			}
		}
	}
	

	public static class Sign implements GameThing, Readable, Edible
	{
		public String getName()
		{
			return "A chocolate sign...?";
		}
		public String getDescription()
		{
		      return "This is a small sign, that strangely seems to be made out of chocolate (yet has some wood splinters in the corners). There seems to be some partially faded writing in it.";
		}
		public void read() {

			System.out.println("You can see the following message: \"EAT ME (Test #69; Success!)\"");

		}
		@Override
		public void eat() {
			System.out.println("You decide to munch a bit on the sign, \"Charlie and the Chocolate Factory\" style. It tastes like expired milk chocolate, but it's surprisingly sweet! You eventually keep munching until the sign \"dissappears\". (And no, you don't eat the splinters. You're smarter than that, I hope.)");
			AdventureEpsilon.theRoom.removeThing(AdventureEpsilon.theSign);			
		}

	}
	
	public static class Box implements GameThing
	{
		private ArrayList<GameThing> boxContents = new ArrayList<GameThing>();

		public String getName()
		{
			return "A cardboard box";
		}
		public String getDescription()
		{
			if(AdventureEpsilon.theApple.isEaten)
				return "This is a large cardboard box decorated in a grid pattern. It's empty, aside from a bitten, rotten and worm-filled red apple.";
			else
				return "This is a large cardboard box decorated in a grid pattern.";
		}

		/** Put something inside the box */
		public void putIn(GameThing thing)
		{
			if (thing != null)
			{
				if (thing == AdventureEpsilon.theBox)
				{
					System.out.println("You can't put the box inside itself!");
				}
				else
				{
					if (boxContents.contains(thing))
					{
						System.out.println("That's already in the box!");
					}
					else
					{
						//Now, the thing should not be directly in the room
						AdventureEpsilon.theRoom.removeThing(thing);
						boxContents.add(thing);					
						System.out.println("Done.");
					}
				}
			}
		}

		/** Pull something out of the box */
		public void pullOut(GameThing thing)
		{
			if (thing != null)
			{
				if (thing == AdventureEpsilon.theBox)
				{
					System.out.println("You can't pull out the box from within itself, that doesn't make sense!");
				}
				else
				{
					if (boxContents.contains(thing))
					{
						boxContents.remove(thing);
						//Now, the thing should be put back in the room
						AdventureEpsilon.theRoom.addThing(thing);
						System.out.println("Done.");
					}
					else
					{
						System.out.println("That's not in the box!");
					}
				}
			}
		}

		/** Look into the box */
		public void lookInto()
		{
			if (boxContents.isEmpty())
			{
				System.out.println("The box is empty.");
			}
			else
			{
				System.out.println("The box contains:");
				int counter = 1;
				for (GameThing thing: boxContents)
				{
					System.out.println(counter + ". " + thing.getName());
					counter++;
				}
			}
		}

		/** Check to see whether a particular thing is currently in the box */
		public boolean hasThing(GameThing thing)
		{
			return boxContents.contains(thing);
		}

		/** Get the amount of things in the box */
		public int getThingCount()
		{
			return boxContents.size();
		}

		/** Get the thing with the indicated index number */
		public GameThing getThingByIndex(int thingChoice)
		{
			if (thingChoice >= 1 && thingChoice <= boxContents.size())
			{
				return boxContents.get(thingChoice - 1);
			}
			else
			{
				return null;
			}
		}
	}
	
	public static class Book implements GameThing, Readable, Openable
	{	
		public boolean isOpen = false;          //Start out closed
		
		public String getName() {

			return "A dusty old book";

			}

			public String getDescription() {
				String openOrClosed;
				if(isOpen){
					openOrClosed = "The book is currently open.";
				}
				else{
					openOrClosed = "The book is currently closed.";
				}

				return "This book appears to be old and dusty. It's also quite heavy. " + openOrClosed;

			 }
			
			public void read() {
				if(isOpen){
					System.out.println("The book seems difficult to read. You start reading the following phrase: \"Exorcizamus te, omnis immundus spiritus, omnis satanica potestas...\". You close the book; you've read enough.");
				}
				else{
					System.out.println("I can't read closed books!");
				}

			}

			@Override
			public void open() {
				if(isOpen){
					System.out.println("The book is already open!");
				}
				else{
					System.out.println("The book opens with a creaky sound.");
					isOpen = true;
				}
				
			}

			@Override
			public void close() {
				if(isOpen){
					System.out.println("As the book closes, it makes a loud 'thud' sound.");
					isOpen = false;
				}
				else{
					System.out.println("The book is already closed!");
				}
				
			}

	}
	
	public static class Apple implements GameThing, Edible
	{
		public boolean isEaten = false; //My own addition
		
		public String getName()
		{
			return "A sexy, shiny red apple";
		}
		public String getDescription()
		{
		      return "This is a juicy-looking red apple. It seems to have been here for some time, yet it looks fresh. Curious, isn't it?";
		}
		@Override
		public void eat() {
			System.out.println("You decide to eat the apple. One bite in, you have a strange feeling. You look at the apple and you see small worms coming out of it! Disgusted, you spit out the apple piece and throw the now rotten apple into some box nearby.");
			isEaten = true;
			AdventureEpsilon.theRoom.removeThing(AdventureEpsilon.theApple);
		}

	}
	
// Main class, The student must complite the swith cases.
	public static void main(String[] args)
	{
		//Populate the room
		theRoom.addThing(theBox);
		theRoom.addThing(theSign);
		theRoom.addThing(theBook);
		theRoom.addThing(theApple);

		//Create additional useful objects
		Scanner keyboard = new Scanner(System.in); 

		//Display a greeting
		System.out.println("Welcome to Adventure Epsilon!");
		System.out.println();

		//Main loop
		boolean hasQuitGame = false;
		while (!hasQuitGame)
		{
			System.out.println();
			System.out.println();
			System.out.println();
			theRoom.listContents();
			System.out.println();
			System.out.println("What do you wish to do?");
			System.out.println("1. Examine something");
			System.out.println("2. Look into the box");
			System.out.println("3. Put something into the box");
			System.out.println("4. Pull out something out of the box");
			System.out.println("5. Read something");
			System.out.println("6. Open something");
			System.out.println("7. Close something");
			System.out.println("8. Eat something");
			System.out.println("99. Quit this game");
			System.out.print("Your choice? >>>");
			int mainMenuChoice = keyboard.nextInt();
			System.out.println();
			int thingChoice;
			
			switch (mainMenuChoice)
			{
				case 1:
					System.out.println("Which thing do you want to examine?");
					theRoom.listContents();
					System.out.print("Your choice? (Enter an unlisted number to go back to the main menu) >>>");
					thingChoice = keyboard.nextInt();
					System.out.println();
					if (thingChoice >= 1 && thingChoice <= theRoom.getThingCount())
					{
						GameThing thing = theRoom.getThingByIndex(thingChoice);
						System.out.println(thing.getDescription());
						System.out.println();
					}
					break;
				case 2:
					theBox.lookInto();
					System.out.println();
					break;
				case 3:
					System.out.println("Which thing do you want to put into the box?");
					theRoom.listContents();
					System.out.print("Your choice? (Enter an unlisted number to go back to the main menu) >>>");
					thingChoice = keyboard.nextInt();
					System.out.println();
					if (thingChoice >= 1 && thingChoice <= theRoom.getThingCount())
					{
						GameThing thing = theRoom.getThingByIndex(thingChoice);
						theBox.putIn(thing);
						System.out.println();
					}
					break;
				case 4:
					if (theBox.getThingCount() == 0)
					{
						System.out.println("The box is empty. Nothing can be pulled out of it.");
						System.out.println();
					}
					else
					{
						System.out.println("Which thing do you want to pull out of the box?");
						theBox.lookInto();
						System.out.print("Your choice? (Enter an unlisted number to go back to the main menu) >>>");
						thingChoice = keyboard.nextInt();
						System.out.println();
						if (thingChoice >= 1 && thingChoice <= theBox.getThingCount())
						{
							GameThing thing = theBox.getThingByIndex(thingChoice);
							theBox.pullOut(thing);
							System.out.println();
						}
					}
					break;
				case 5:
					System.out.println("Which thing do you want to read?");
					theRoom.listContents();	

					System.out.print("Your choice? (Enter an unlisted number to go back to the main menu) >>>");

					thingChoice = keyboard.nextInt();
					System.out.println();

					if (thingChoice >= 1 && thingChoice <= theRoom.getThingCount()) {
						GameThing thing = theRoom.getThingByIndex(thingChoice);
						if (thing instanceof Readable) {
							Readable readableThing = (Readable) thing;
							readableThing.read();
						} else {
							System.out.println("That's not something you can read!");
						}
						System.out.println();
					}
				break;
				case 6:
					System.out.println("Which thing do you want to open?");
					theRoom.listContents();	

					System.out.print("Your choice? (Enter an unlisted number to go back to the main menu) >>>");

					thingChoice = keyboard.nextInt();
					System.out.println();

					if (thingChoice >= 1 && thingChoice <= theRoom.getThingCount()) {
						GameThing thing = theRoom.getThingByIndex(thingChoice);
						if (thing instanceof Openable) {
							Openable openableThing = (Openable) thing;
							openableThing.open();
						} else {
							System.out.println("That's not something you can open!");
						}
						System.out.println();
					}
				break;
				case 7:
					System.out.println("Which thing do you want to open?");
					theRoom.listContents();	

					System.out.print("Your choice? (Enter an unlisted number to go back to the main menu) >>>");

					thingChoice = keyboard.nextInt();
					System.out.println();

					if (thingChoice >= 1 && thingChoice <= theRoom.getThingCount()) {
						GameThing thing = theRoom.getThingByIndex(thingChoice);
						if (thing instanceof Openable) {
							Openable openableThing = (Openable) thing;
							openableThing.close();
						} else {
							System.out.println("That's not something you can close!");
						}
						System.out.println();
					}
				break;
				case 8:
					System.out.println("Which thing do you want to eat?");
					theRoom.listContents();	

					System.out.print("Your choice? (Enter an unlisted number to go back to the main menu) >>>");

					thingChoice = keyboard.nextInt();
					System.out.println();

					if (thingChoice >= 1 && thingChoice <= theRoom.getThingCount()) {
						GameThing thing = theRoom.getThingByIndex(thingChoice);
						if (thing instanceof Edible) {
							Edible edibleThing = (Edible) thing;
							edibleThing.eat();
						} else {
							System.out.println("That's not something you can eat!");
						}
						System.out.println();
					}
				break;
					
				case 99:
					hasQuitGame = true;
					System.out.println("Thank you for playing!");
					break;
		            
				default:
					System.out.println("Please use one of the choices from the menu!");
					System.out.println();
			    break;	
			}
		}

		//The following line of code is used so that
		//this program runs on computers with Java 7
		//without a warning message from Eclipse.
		//Java 6 can use it, but does not need it.
		keyboard.close();
	}

}