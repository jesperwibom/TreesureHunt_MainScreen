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

public class GUIlarge extends JFrame {
	
	
	//////////////////////////////////////////////////
	// Class variables
	// 
	
	private static final long serialVersionUID = 1L;
	
	private Logic logic;
	
	//Frame elements
	private JPanel contentPane;
	private JPanel background_panel;

	private JLabel logo_label;
	private JLabel logotext_label;
	private JLabel map_label;
	private JLabel sidebar_label;
	private JLabel compass_label;
	private JLabel star_label;
	private JLabel animation_label;
	private JLabel download_label;
	
	private ArrayList<JLabel> markerLbl;
	
	//Screen variables
	private int[] screenSize = {1900,1060};
	private int[] screenPlacement = {100,100};
	private int[] mapPosition = {8, 8}; //sets where the map is placed in relation to the top left corner
	private int[] mapSize = {1500, 1020}; //control both the scaling of the mapImg and the size of the JLabel
	private int[] sidebarPosition = {1520,6};
	private int[] sidebarSize = {370, 1040}; // control both the scaling of the sidebarImg and the size of the JLabel
	
	//Resource variables
	private BufferedImage logoImg;
	private BufferedImage logotextImg;
	private BufferedImage mapImg;
	private BufferedImage sidebarImg;
	private BufferedImage compassImg;
	private BufferedImage starImg;
	private BufferedImage googleImg;

	private ImageIcon helpAni;
	private ImageIcon markerAni;

	private Color backColor;
	
	//////////////////////////////////////////////////
	// Main
	// 
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() { 
			public void run() {
				try {
					
					GUIlarge frame = new GUIlarge();
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
	
	public GUIlarge() {
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
			logoImg = ImageIO.read(new File("res/icon_logo.png"));
			if(Constants.DEBUG){System.out.println("logoImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("logoImg not found");}
		}
		
		try {
			mapImg = ImageIO.read(new File("res/map_b1051080.png"));
			if(Constants.DEBUG){System.out.println("mapImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("mapImg not found");}
		}

		try {
			sidebarImg = ImageIO.read(new File("res/sidebar.png"));
			if(Constants.DEBUG){System.out.println("sidebarImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("sidebarImg not found");}
		}
		
		try {
			compassImg = ImageIO.read(new File("res/icon_compass.png"));
			if(Constants.DEBUG){System.out.println("compassImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("compassImg not found");}
		}
		
		try {
			starImg = ImageIO.read(new File("res/icon_star.png"));
			if(Constants.DEBUG){System.out.println("starImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("starImg not found");}
		}
		
		try {
			logotextImg = ImageIO.read(new File("res/text_logo.png"));
			if(Constants.DEBUG){System.out.println("logotextImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("logotextImg not found");}
		}
		
		try {
			googleImg = ImageIO.read(new File("res/googleplay.png"));
			if(Constants.DEBUG){System.out.println("googleImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("googleImg not found");}
		}
		
		helpAni = new ImageIcon("res/ani_help330.gif");
		markerAni = new ImageIcon("res/ani_marker200.gif");
		backColor = new Color(199,210,235);
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
		background_panel.setBackground(backColor);
		
		//LOGO
		logo_label = new JLabel();
		logo_label.setBounds(sidebarPosition[0]+35, 50, sidebarSize[0]-70, sidebarSize[0]-70 );
		logo_label.setIcon(new ImageIcon(logoImg.getScaledInstance(sidebarSize[0]-70, sidebarSize[0]-70, Image.SCALE_SMOOTH)));
		background_panel.add(logo_label);
		
		//LOGOTEXT
		logotext_label = new JLabel();
		logotext_label.setBounds(sidebarPosition[0]+40, 385, sidebarSize[0]-80, sidebarSize[0]-200 );
		logotext_label.setIcon(new ImageIcon(logotextImg.getScaledInstance(sidebarSize[0]-80, sidebarSize[0]-200, Image.SCALE_SMOOTH)));
		background_panel.add(logotext_label);
		
		//ANIMATION
		animation_label = new JLabel();
		animation_label.setBounds(sidebarPosition[0]+20, 580, 330, 330 );
		animation_label.setIcon(helpAni);
		background_panel.add(animation_label);
		
		//GOOGLE
		download_label = new JLabel();
		download_label.setBounds(sidebarPosition[0]+160, 930, 180, 60 );
		download_label.setIcon(new ImageIcon(googleImg.getScaledInstance(180, 60, Image.SCALE_SMOOTH)));
		background_panel.add(download_label);
		
		//SIDEBAR BACKGROUND
		sidebar_label = new JLabel();
		sidebar_label.setBounds(sidebarPosition[0], sidebarPosition[1], sidebarSize[0], sidebarSize[1] );
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
		/*
		//COMPASS
		compass_label = new JLabel();
		compass_label.setBounds(18, 450, 80, 80 );
		compass_label.setIcon(new ImageIcon(compassImg.getScaledInstance (80, 80, Image.SCALE_SMOOTH)));
		background_panel.add(compass_label);
		*/
		/*
		//STAR
		star_label = new JLabel();
		star_label.setBounds(620, 420, 70, 70 );
		star_label.setIcon(new ImageIcon(starImg.getScaledInstance(70, 70, Image.SCALE_SMOOTH)));
		background_panel.add(star_label);
		*/
		//MAP
		map_label = new JLabel();
		map_label.setBounds(mapPosition[0], mapPosition[1], mapSize[0], mapSize[1]);
		map_label.setIcon(new ImageIcon(mapImg.getScaledInstance(mapSize[0], mapSize[1], Image.SCALE_REPLICATE)));
		background_panel.add(map_label);
		
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