package se.mah.k3;

import java.util.Random;
import java.util.Vector;

public class TreasureSupport {
	
	
	//////////////////////////////////////////////////
	// Class variables
	//
	
	public static Vector<Treasure> treasureLocations = new Vector<Treasure>(); //Stores all Treasure objects	
	private static int activeTreasureCounter = 0;
	
	public String lastChangedTreasureId = "";
	public int lastChangedTreasureType = 0;
	
	
	//////////////////////////////////////////////////
	// Constructor
	//
	
	public TreasureSupport(){}
	
	
	//////////////////////////////////////////////////
	// Methods for treasureLocations
	//
	
	
	//Run in the start of the application. Every added new TreasureLocation is initialized with id, x position and y position
	//Set the positions with values between 1-1000, the will be scaled in relation to the mapPosition[] and mapSize[] in GUI
	
	//FOR X POSITION
	// 30 is a realistic minimum, depending on the size of the markers
	// 972 is a good maximum value
	
	//FOR Y POSITION
	// min = 29  max = 921
	
	public void setupTreasureLocations(){	
		treasureLocations.add(new Treasure("TL01",100,921));
		treasureLocations.add(new Treasure("TL02",300,200));
		treasureLocations.add(new Treasure("TL03",500,450));
		treasureLocations.add(new Treasure("TL04",805,720));
		treasureLocations.add(new Treasure("TL05",720,300));
		treasureLocations.add(new Treasure("TL06",972,921));
		treasureLocations.add(new Treasure("TL07",30,500));
		treasureLocations.add(new Treasure("TL08",972,780));
		treasureLocations.add(new Treasure("TL09",708,380));
		treasureLocations.add(new Treasure("TL10",972,29));
		treasureLocations.add(new Treasure("TL11",800,921));
		treasureLocations.add(new Treasure("TL12",235,180));
		treasureLocations.add(new Treasure("TL13",324,459));
		treasureLocations.add(new Treasure("TL14",608,29));
		treasureLocations.add(new Treasure("TL15",972,29));
		treasureLocations.add(new Treasure("TL16",60,565));
		treasureLocations.add(new Treasure("TL17",30,29));
		treasureLocations.add(new Treasure("TL18",400,29));
		treasureLocations.add(new Treasure("TL19",370,740));
		treasureLocations.add(new Treasure("TL20",972,650));
	}
	
	//Generates ONE active treasure
	public void generateTreasureLocation(){
		Random rand = new Random();
		int rTreasure = rand.nextInt(treasureLocations.size());
		int rType = rand.nextInt(Constants.NBR_TYPES) + 1;
			
		while(treasureLocations.get(rTreasure).checkActive()) {
			rTreasure = rand.nextInt(treasureLocations.size());
		}
		treasureLocations.get(rTreasure).setType(rType);
		increaseActiveTreasureCounter(1);
		
		lastChangedTreasureId = treasureLocations.get(rTreasure).getId();
		lastChangedTreasureType = treasureLocations.get(rTreasure).getType();
		
		if(Constants.DEBUG){System.out.println("New treasure generated: "+treasureLocations.get(rTreasure).getId()+" "+treasureLocations.get(rTreasure).getType());}
	}
	
	//Generates treasure until the max active limit is reached
	public void generateTreasureLocations(){
		Random rand = new Random();
		int rTreasure = 0;
		int rType = 0;
		while(getActiveTreasureCounter() < Constants.MAX_ACTIVE){
			rTreasure = rand.nextInt(treasureLocations.size());
			rType = rand.nextInt(Constants.NBR_TYPES) + 1;
			
			if(treasureLocations.get(rTreasure).checkActive()) {
				//Already active. Looks for new random
			} else {
				treasureLocations.get(rTreasure).setType(rType);
				increaseActiveTreasureCounter(1);
				if(Constants.DEBUG){System.out.println("New treasure generated: "+treasureLocations.get(rTreasure).getId()+" "+treasureLocations.get(rTreasure).getType());}
			}
		}
	}
	
	//Get specific treasure object from the treasureLocations vector. Used by logic, fireSupport and GUI
	public static Treasure getTreasureLocation(int i){
		return treasureLocations.get(i);
	}
	
	//Gets the size of treasureLocations. Used by logic, fireSupport and GUI
	public static int getTreasureLocationsSize(){
		return treasureLocations.size();
	}
	
	//Sets the type of one treasure object
	public void updateTreasureLocationType(int nbr, int type){
		treasureLocations.get(nbr).setType(type);
	}
	
	//Prints treasureLocations data to console
	public void printTreasureLocationsInConsole(){
		for (Treasure tl : treasureLocations){
			System.out.println(tl.getId()+"\t:\t"+tl.getType());
		}
	}
	
	
	//////////////////////////////////////////////////
	// Methods for activeTreasureCounter
	//
	
	//Goes through treasureLocation array(Vector) and counts all active Treasure
	public void updateActiveTreasureCounter(){
		setActiveTreasureCounter(0);	
		for (Treasure tl : treasureLocations){
			if(tl.getType() > 0){
				increaseActiveTreasureCounter(1);
			}
		}	
		if(Constants.DEBUG){System.out.println("activeTreasureCount: "+getActiveTreasureCounter());}
	}

	public void increaseActiveTreasureCounter(int i){
		activeTreasureCounter += i;
	}
	
	public void setActiveTreasureCounter(int i){
		activeTreasureCounter = i;
	}
	
	public int getActiveTreasureCounter(){
		return activeTreasureCounter;
	}
	 
}
