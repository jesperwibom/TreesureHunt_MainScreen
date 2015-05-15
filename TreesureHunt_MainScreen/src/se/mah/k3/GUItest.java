package se.mah.k3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GUItest extends JFrame implements KeyEventDispatcher{
	
	
	//////////////////////////////////////////////////
	// Class variables
	//
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel background_panel;
	private JPanel side_panel;
	private JPanel map_panel;
	private JLabel instruction_label;
	private JLabel marker_label;
	
	//Screen variables
	private boolean fullScreen = false;
	private int[] screenSize = {640,480};
	private int[] screenPlacement = {100,100};
	
	//Resource variables
	private BufferedImage backgroundImg;
	private BufferedImage logoImg;
	private BufferedImage mapImg;
	private BufferedImage markerImg;

	
	//////////////////////////////////////////////////
	// Main
	//
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUItest frame = new GUItest();
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
	
	public GUItest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(screenPlacement[0], screenPlacement[1], screenSize[0]+screenPlacement[0], screenSize[1]+screenPlacement[1]);
		
		loadResources();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		background_panel = new BackgroundPanel(backgroundImg);
		contentPane.add(background_panel, BorderLayout.CENTER);
		background_panel.setLayout(new BorderLayout(0, 0));
		background_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		side_panel = new JPanel();
		background_panel.add(side_panel, BorderLayout.EAST);
		side_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		side_panel.setBackground(new Color(0,0,255));
		
		instruction_label = new JLabel();
		instruction_label.setBorder(new EmptyBorder(5, 5, 5, 5));

		instruction_label.setText("DOWNLOAD THIS GAME!!!!!");
		side_panel.add(instruction_label);
		
		map_panel = new BackgroundPanel(logoImg);
		background_panel.add(map_panel, BorderLayout.CENTER);
		map_panel.setBackground(new Color(255,0,0));
		map_panel.setLayout(null);
		
		
		
		ImageIcon icon = new ImageIcon(markerImg);
		marker_label = new JLabel("lblMarker");
		marker_label.setIcon(icon);
		marker_label.setBounds(110, 110, 250, 250);
		map_panel.add(marker_label);
		
		
		
		
		//Create the logic object that in turn create FireSupport and TreasureSupport objects
		Logic logic = new Logic();
		drawFrame(); //??
		
		//Creates a thread that will update the markers(?)
		Thread updateThread = new Thread("UpdateThread"){
			public void run(){
				
				if(Constants.DEBUG){System.out.println("UpdateThread runs!!!!!!!!!!");}
			}
		};
		updateThread.start();
		
	}
	
	
	//////////////////////////////////////////////////
	// Setup methods
	//
	
	public void loadResources(){
		try {
			backgroundImg = ImageIO.read(new File("res/background.jpg"));
			if(Constants.DEBUG){System.out.println("backgroundImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("backgroundImg not found");}
		}
		
		try {
			logoImg = ImageIO.read(new File("res/logo.png"));
			if(Constants.DEBUG){System.out.println("logoImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("logoImg not found");}
		}
		
		try {
			markerImg = ImageIO.read(new File("res/marker.png"));
			if(Constants.DEBUG){System.out.println("markerImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("markerImg not found");}
		}
	}
	
	
	//////////////////////////////////////////////////
	// Draw methods
	//
	
	public static void drawFrame(){
		
		//Draw background
		
		//repaint(); //??
		
		drawMarkers();
		
		//Draw frame
		
	}
	
	public static void drawMarkers(){
		for(int i = 0; i < TreasureSupport.getTreasureLocationsSize(); i++){
			Treasure treasure = TreasureSupport.getTreasureLocation(i);
			if(treasure.getType() > 0){
				drawMarker(treasure.getPosX(),treasure.getPosY());
			}
		}
		if(Constants.DEBUG){System.out.println("Redraw all markers");}
	}
	
	private static void drawMarker(int posX, int posY){
		
		//
		// WRITE HOW TO PLACE MARKER HERE
		// 
		// ALSO CHANGE THE POSITIONS IN THE TREASURE VECTOR
		// EVERY TREASURE OBJECT SHOULD HAVE AN INT BETWEEN 1-1000
		// THIS INT WILL NOT DIRECTLY REPRESENT THE POSITION
		// IT WILL BE COMPARED TO THE FULLSCREEN WIDTH AND HEIGHT
		// SO AS TO MAKE THE MAP INTERFACE DYNAMIC AND SCALABLE
		//
		
		if(Constants.DEBUG){System.out.println("Marker placed at: X"+posX+" Y"+posY);}
	}
	
	
	//////////////////////////////////////////////////
	// Keypress methods
	//

	public boolean dispatchKeyEvent(KeyEvent e){
		if (e.getID() == KeyEvent.KEY_TYPED) {
			if(e.getKeyChar()=='f'){     		 
             	setFullscreen(!fullScreen);	
     		}
        }
		return false;
	}
	
	public void setFullscreen(boolean fullscreen) {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = environment.getScreenDevices();    
		if(fullscreen){
		
		} else {
			
		}
	}
}
