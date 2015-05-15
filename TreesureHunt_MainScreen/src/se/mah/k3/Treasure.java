package se.mah.k3;

//This class is used to store one QR code position each and activity/type info
public class Treasure {
	
	//Variables
	private String id;
	private int type;
	private int posX;
	private int posY; 
	
	//Constructor
	public Treasure(String id, int posX, int posY){	
		this.id = id;
		this.type = 0;
		this.posX = posX;
		this.posY = posY;
	}
	
	//Special methods
	public boolean checkActive(){
		if(this.type == 0){
			return false;
		} else {
			return true;
		}
	}
	
	//Setters and Getters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	
}
