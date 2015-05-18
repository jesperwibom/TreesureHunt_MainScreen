package se.mah.k3;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class FireSupport {
	
	
	//////////////////////////////////////////////////
	// Class variables
	//
	
	private Firebase firebase; //Stores the Firebase referens. See Constants for the address.
	private TreasureSupport treasureSupport;
	
	
	//////////////////////////////////////////////////
	// Constructor
	//
	
	public FireSupport(String url){
		firebase = new Firebase(url);
		treasureSupport = new TreasureSupport();
	}
	
	
	//////////////////////////////////////////////////
	// Methods
	//
	
	//Removes all values
	public void clearFirebase(){			
		firebase.removeValue();
	}
	
	//Create a child and a sets the value. 
	public void setData(String name, int value){
		firebase.child(name).setValue(value);
	}
	
	//Create a valueEventListener
	public void setListener(String name){
		firebase.child(name).addValueEventListener(new ValueEventListener() {
		
			
			//This method is executed every time the value is changed
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				
				//Create a int value from string value
				String value = snapshot.getValue().toString();
				int valueInt = Integer.parseInt(value);
				
				//Always update the changed values treasureLocation referens
				treasureSupport.updateTreasureLocationType(calculateTreasureLocationPlaceInArray(snapshot.getKey()), valueInt);
				
				//Debug message
				if(Constants.DEBUG){System.out.println(snapshot.getKey()+" "+snapshot.getValue().toString());}
				
				//IF value is changed to zero (ie an app user has "deleted" a value:
				if(valueInt == 0){
					
					// Updates the current active treasures counter [maybe REDUNDANT]
					treasureSupport.updateActiveTreasureCounter();
					
					//IF maximum active treasures is NOT reached, generate new treasure
					if(treasureSupport.getActiveTreasureCounter() < Constants.MAX_ACTIVE){
						//Generates ONE new treasure location and automatically updates treasureLocations array
						treasureSupport.generateTreasureLocation();
						//Debug message
						if(Constants.DEBUG){System.out.println("New activeTreasureCount: "+treasureSupport.getActiveTreasureCounter());}
						//Updates firebase with new data
						setData(treasureSupport.lastChangedTreasureId, treasureSupport.lastChangedTreasureType);
						//Draw markers
						//GUItest.drawFrame();
						GUIsmall.drawFrame();
					}
					
					//Debug message
					if(Constants.DEBUG){System.out.println(snapshot.getKey()+" is ZERO");}
				}
			}
			
			//Not used
			@Override
			public void onCancelled(FirebaseError error) {
				// TODO Auto-generated method stub	
			}
		});
	} 
	
	
	//////////////////////////////////////////////////
	// Help methods
	//
	
	public int calculateTreasureLocationPlaceInArray(String id){
		int place = 0;
		for(int i = 0; i < treasureSupport.getTreasureLocationsSize(); i++){
			if(treasureSupport.getTreasureLocation(i).getId() == id){
				place = i;
			}
		}	
		return place;
	}
	
}
