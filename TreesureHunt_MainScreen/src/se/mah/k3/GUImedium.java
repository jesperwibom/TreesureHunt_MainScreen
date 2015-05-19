package se.mah.k3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
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
	
	private JLabel logo_label;
	private JLabel map_label;
	private JLabel instruction_label;
	private JLabel marker_label;
	
	private ArrayList<JLabel> markerLbl;
	
	//Screen variables
	private int[] screenSize = {853,480};
	private int[] screenPlacement = {100,100};
	
	//Resource variables
	private BufferedImage backgroundImg;
	private BufferedImage logoImg;
	private BufferedImage mapImg;
	private BufferedImage markerImg;
	private ImageIcon loaderAni;

	
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
	
	public void loadResources(){
		try {
			backgroundImg = ImageIO.read(new File("res/backgroundscreen.png"));
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
		
		try {
			mapImg = ImageIO.read(new File("res/map.png"));
			if(Constants.DEBUG){System.out.println("mapImg loaded");}
		} catch (IOException e) {
			if(Constants.DEBUG){System.out.println("mapImg not found");}
		}
		
		loaderAni = new ImageIcon("res/loader.gif");
		
	}
	
	public void setupFrameElements(){
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		
		background_panel = new BackgroundPanel(backgroundImg);
		contentPane.add(background_panel, BorderLayout.CENTER);
		background_panel.setLayout(null);
		
		markerLbl = new ArrayList<JLabel>();
		for(int i = 0; i < Constants.MAX_ACTIVE; i++){
			markerLbl.add(new JLabel());
			markerLbl.get(i).setBounds(0,0,100,100);
			markerLbl.get(i).setIcon(new ImageIcon(markerImg.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			background_panel.add(markerLbl.get(i));
		}
		
		marker_label = new JLabel();
		marker_label.setBounds(780,360,100,100);
		marker_label.setIcon(loaderAni);
		background_panel.add(marker_label);
		
		map_label = new JLabel();
		map_label.setBounds(0, 0, (int) (screenSize[0]*0.75), screenSize[1]);
		map_label.setIcon(new ImageIcon(mapImg.getScaledInstance((int) (screenSize[0]*0.75), screenSize[1], Image.SCALE_SMOOTH)));
		background_panel.add(map_label);
		
		logo_label = new JLabel();
		logo_label.setBounds(730, 40, 100, 130);
		logo_label.setIcon(new ImageIcon(logoImg.getScaledInstance(100, 130, Image.SCALE_SMOOTH)));
		background_panel.add(logo_label);
	}
	
	public void setupUpdateThread(){
		Thread updateThread = new Thread("UpdateThread"){
			public void run(){
				while(true){
					if(Constants.DEBUG){System.out.println("UpdateThread runs!!!!!!!!!!");}
					
					Vector<Treasure> tempLocations = TreasureSupport.treasureLocations;
					int markerCount = 0;
					for(Treasure t : tempLocations){
						if(t.checkActive()){
							markerLbl.get(markerCount).setLocation(t.getPosX(),t.getPosY());
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

}
