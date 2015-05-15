package se.mah.k3;

public class Logic {
	
	
	//////////////////////////////////////////////////
	// Class variables
	//
	
	private TreasureSupport treasureSupport;
	private FireSupport fireSupport;
	
	
	//////////////////////////////////////////////////
	// Constructor
	//
	
	public Logic(){
		treasureSupport = new TreasureSupport();
		treasureSupport.setupTreasureLocations();
		treasureSupport.generateTreasureLocations(); //generate treasures and updates treasure array
		treasureSupport.printTreasureLocationsInConsole();
		
		fireSupport = new FireSupport(Constants.FIREBASE_URL);
		setupFirebaseDatabase();
		updateFirebaseDatabase();
		setupFirebaseListeners();
	}
	
	
	//////////////////////////////////////////////////
	// Methods
	//
	
	//Loops through treasureLocations and sets key and value.
	public void setupFirebaseDatabase(){
		fireSupport.setData("LightCue", 0);
		for(int i = 0; i < treasureSupport.getTreasureLocationsSize(); i++){
			fireSupport.setData(treasureSupport.getTreasureLocation(i).getId(), 0);
		}
	}
	
	//Alternative method where a default value can be specified. Loops through treasureLocations and sets key and value.
	public void setupFirebaseDatabase(int defaultValue){
		for(int i = 0; i < treasureSupport.getTreasureLocationsSize(); i++){
			fireSupport.setData(treasureSupport.getTreasureLocation(i).getId(), 0);
		}
	}
	
	//Loops through treasureLocations and sets valueEventListeners.
	public void setupFirebaseListeners(){
		for(int i = 0; i < treasureSupport.getTreasureLocationsSize(); i++){
			fireSupport.setListener(treasureSupport.getTreasureLocation(i).getId());
		}
	}
	
	//Updates all firebase data
	public void updateFirebaseDatabase(){
		for(int i = 0; i < treasureSupport.getTreasureLocationsSize(); i++){
			fireSupport.setData(treasureSupport.getTreasureLocation(i).getId(), treasureSupport.getTreasureLocation(i).getType());
		}
	}
	
	//Updates specific data
	public void updateFirebaseData(String name, int value){
		fireSupport.setData(name, value);
	}
	
}
