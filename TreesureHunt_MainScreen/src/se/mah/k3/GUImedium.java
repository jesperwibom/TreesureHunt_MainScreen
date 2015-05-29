package se.mah.k3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GUImedium extends JFrame {
	
	
	//////////////////////////////////////////////////
	// Class variables
	// 
	
	private static final long serialVersionUID = 1L;
	
	private Logic logic;
	
	//Frame elements
	private JPanel contentPane;
	private JPanel background_panel;

	private JLabel background_label;
	private JLabel logo_label;
	private JLabel logotext_label;
	private JLabel map_label;
	private JLabel sidebar_label;
	private JLabel compass_label;
	private JLabel star_label;
	private JLabel animation_label;
	
	private ArrayList<JLabel> markerLbl;
	
	//Screen variables
	private int[] screenSize = {1024,576};
	private int[] screenPlacement = {100,100};
	private int[] mapPosition = {7, 7}; //sets where the map is placed in relation to the top left corner
	private int[] mapSize = {840, 550}; //control both the scaling of the mapImg and the size of the JLabel
	private int[] sidebarSize = {150, 549}; // control both the scaling of the sidebarImg and the size of the JLabel
	
	//Resource variables
	private BufferedImage backgroundImg;
	private BufferedImage logoImg;
	private BufferedImage logotextImg;
	private BufferedImage mapImg;
	private BufferedImage sidebarImg;
	private BufferedImage compassImg;
	private BufferedImage starImg;

	private BufferedImage animationImg;
	private ImageIcon markerAni;

	
	//////////////////////////////////////////////////
	// Main
	// 
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() { 
			public void run() {
				try {
					
					GUImedium frame = new GUImedium();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	//////////////////////////////////////////////////
	// Constructor
	// 
	
	public GUImedium() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(screenPlacement[0], screenPlacement[1], screenSize[0], screenSize[1]);
		setResizable(false);
		
		loadResources();
		setupFrameElements();
		setupUpdateThread();
		
		logic = new Logic();	
	}
	
		
	//////////////////////////////////////////////////
	// Methods
	// 
	
	//Load all resources
	public void loadResources(){
		try {
			backgroundImg = ImageIO.read(new File("res/backgroundscreen.png"));
			if(Constants.DEBUG){System.out.println("backgroundImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("backgroundImg not found");}
		}
		
		try {
			logoImg = ImageIO.read(new File("res/logoscreen.png"));
			if(Constants.DEBUG){System.out.println("logoImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("logoImg not found");}
		}
		
		try {
			mapImg = ImageIO.read(new File("res/map.png"));
			if(Constants.DEBUG){System.out.println("mapImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("mapImg not found");}
		}

		try {
			sidebarImg = ImageIO.read(new File("res/sidebarscreen.png"));
			if(Constants.DEBUG){System.out.println("sidebarImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("sidebarImg not found");}
		}
		
		try {
			compassImg = ImageIO.read(new File("res/compass_icon.png"));
			if(Constants.DEBUG){System.out.println("compassImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("compassImg not found");}
		}
		
		try {
			starImg = ImageIO.read(new File("res/star_icon.png"));
			if(Constants.DEBUG){System.out.println("starImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("starImg not found");}
		}
		
		try {
			animationImg = ImageIO.read(new File("res/animationscreen.png"));
			if(Constants.DEBUG){System.out.println("animationImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("animationImg not found");}
		}
		try {
			logotextImg = ImageIO.read(new File("res/logotextscreen.png"));
			if(Constants.DEBUG){System.out.println("logotextImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("logotextImg not found");}
		}
		
		markerAni = new ImageIcon("res/waveRadar.gif");
		
	}
	
	//Set up all frame elements in the correct order and location
	public void setupFrameElements(){
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		
		//CONTAIN ALL LABELS
		background_panel = new JPanel();
		contentPane.add(background_panel, BorderLayout.CENTER);
		background_panel.setLayout(null);
		background_panel.setBackground(new Color(195,205,210));
		
		//LOGO
		logo_label = new JLabel();
		logo_label.setBounds(875, 295, 130, 130 );
		logo_label.setIcon(new ImageIcon(logoImg.getScaledInstance(130, 130, Image.SCALE_SMOOTH)));
		background_panel.add(logo_label);
		
		//LOGOTEXT
		logotext_label = new JLabel();
		logotext_label.setBounds(870, 440, 142, 98 );
		logotext_label.setIcon(new ImageIcon(logotextImg.getScaledInstance(142, 98, Image.SCALE_SMOOTH)));
		background_panel.add(logotext_label);
		
		//ANIMATION
		animation_label = new JLabel();
		animation_label.setBounds(875, 28, 130, 130 );
		animation_label.setIcon(new ImageIcon(animationImg.getScaledInstance(130, 130, Image.SCALE_SMOOTH)));
		background_panel.add(animation_label);
		
		//SIDEBAR BACKGROUND
		sidebar_label = new JLabel();
		sidebar_label.setBounds(865, 7, sidebarSize[0], sidebarSize[1] );
		sidebar_label.setIcon(new ImageIcon(sidebarImg.getScaledInstance(sidebarSize[0], sidebarSize[1], Image.SCALE_SMOOTH)));
		background_panel.add(sidebar_label);
		
		//MARKERS
		markerLbl = new ArrayList<JLabel>();
		for(int i = 0; i < Constants.MAX_ACTIVE; i++){
			markerLbl.add(new JLabel());
			markerLbl.get(i).setBounds(0,0,200,200);
			markerLbl.get(i).setIcon(markerAni);
			background_panel.add(markerLbl.get(i));
		}
		
		//COMPASS
		compass_label = new JLabel();
		compass_label.setBounds(18, 450, 80, 80 );
		compass_label.setIcon(new ImageIcon(compassImg.getScaledInstance (80, 80, Image.SCALE_SMOOTH)));
		background_panel.add(compass_label);
		
		//STAR
		star_label = new JLabel();
		star_label.setBounds(300, 300, 50, 50 );
		star_label.setIcon(new ImageIcon(starImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		background_panel.add(star_label);
		
		//MAP
		map_label = new JLabel();
		map_label.setBounds(mapPosition[0], mapPosition[1], mapSize[0], mapSize[1]);
		map_label.setIcon(new ImageIcon(mapImg.getScaledInstance(mapSize[0], mapSize[1], Image.SCALE_SMOOTH)));
		background_panel.add(map_label);
		
		
		/*
		//BACKGROUND
		background_label = new JLabel();
		background_label.setBounds(0,0,screenSize[0],screenSize[1]);
		background_label.setIcon(new ImageIcon(backgroundImg.getScaledInstance(screenSize[0], screenSize[1], Image.SCALE_SMOOTH)));
		background_panel.add(background_label);
		*/
	}
	
	//Checks firebase continuously and update marker locations 
	public void setupUpdateThread(){
		Thread updateThread = new Thread("UpdateThread"){
			public void run(){
				while(true){
					if(Constants.DEBUG){System.out.println("UpdateThread runs!!!!!!!!!!");}
					
					Vector<Treasure> tempLocations = TreasureSupport.treasureLocations;
					int markerCount = 0;
					for(Treasure t : tempLocations){
						if(t.checkActive()){
							markerLbl.get(markerCount).setLocation(mapMarker(t.getPosX(),0)-100,mapMarker(t.getPosY(),1)-100);

							if(Constants.DEBUG){System.out.println(t.getId()+":");}
							if(Constants.DEBUG){System.out.println(markerLbl.get(markerCount).getLocation());}

							markerCount++;
						}
					}
					try {
						sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		updateThread.start();
	}
	
	//Maps a value to the correct position on the map. when calling method, first value is position, second is 0 for x values, 1 for y values
	public int mapMarker(int position, int xy){
		int inMin = 0;
		int inMax	= 1000;
		int outMin = mapPosition[xy];
		int outMax = mapSize[xy] + mapPosition[xy];
		
		int mappedPosition = (position - inMin) * (outMax - outMin)  / (inMax - inMin)  + outMin;
		
		return mappedPosition;
	}
	
}

