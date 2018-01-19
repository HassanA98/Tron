//Hassan Arshad
//Tron LightCycles game
//Main Game

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;

public class Tron extends JFrame implements ActionListener{
	Timer myTimer;   
	GamePanel game;
	
	private static boolean gameStart = false;
	//the constructor of the class starts the timer, sets the frame setting and adds the Panel
    public Tron() {
		super("TRON");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1022,1022);

		myTimer = new Timer(10, this);	 // causes an trigger for actionPerformed every 10ms
		

		game = new GamePanel(this);
		add(game);

		setResizable(false);
		setVisible(true);
    }
	
	//The following two methods stop and start the timer
	public void start(){
		myTimer.start();
	}

	public void stop(){
		myTimer.stop();
	}
	
	//Whenever the timer hits 10ms or a key is pressed this method is called
	public void actionPerformed(ActionEvent evt){
		game.move();
		game.checkRestart();
		game.repaint();
	}
	
	
    public static void main(String[] arguments) {
    	TronMenu menu = new TronMenu();
    	while(gameStart != true){                //While loop ensure that the menu stays up until the game has started
    		gameStart = menu.checkGame();
 			System.out.println("");
    	}
    	
	    Tron frame = new Tron();
    	
    	
				
    }
}


//The GamePanel class draws the graphics of the game
class GamePanel extends JPanel implements KeyListener{
	private int boxx,boxy,xstep,ystep,boxx2,boxy2,xstep2,ystep2,speed,score1,score2;
	private boolean []keys;
	Point boxp = new Point();
	Point boxp2 = new Point();
	private Image back,logo;
	private boolean drawimage;
	private static ArrayList<Point>trail = new ArrayList<Point>();
	private static ArrayList<Point>trail2 = new ArrayList<Point>();
	private Tron mainFrame;
	private boolean flag = true;
	private String winner = "";
	
	public GamePanel(Tron m){
		mainFrame = m;
		back = new ImageIcon("tron.jpg").getImage();     //Grid image
		logo = new ImageIcon("game1.png").getImage();	 //Logo image
		initialize();
		//keeps track of the players score
		score1 = 0;
		score2 = 0;
		setSize(1022,1022);
		setVisible(true);
        addKeyListener(this);
	}
	
	//Resets all variables to their initial position
	public void initialize(){
		trail = new ArrayList<Point>();
		trail2 = new ArrayList<Point>();
		keys = new boolean[KeyEvent.KEY_LAST+1];
		drawimage = true;
		speed = 5;
	    
        boxx = 150;
        boxy = 550;
        xstep = speed;
        ystep = 0;
        
        boxx2 = 850; 
        boxy2 = 550;
        xstep2 = -speed;
        ystep2 = 0;

        Point boxp = new Point(boxx,boxy);
		Point boxp2 = new Point(boxx2,boxy2);
		trail.add(boxp);
		trail2.add(boxp2);
	}
	
    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }
    
    
    //The move method increases the trails position by the set value
    //and keeps adding the new positions into an ArrayList
	public void move(){
	
		boxx += xstep;
		boxy += ystep;
		boxx2 += xstep2;
		boxy2 += ystep2;

		Point boxp = new Point(boxx,boxy);
		Point boxp2 = new Point(boxx2,boxy2);
		trail.add(boxp);
		trail2.add(boxp2);	
	}
	
	//This method keeps checking if the game ended or not
	//if it did then it calls on the restart method
	public void checkRestart(){
		if(checkStatus() == false){
			mainFrame.stop();
			if(restart()){
				initialize();
			}				
			mainFrame.start();
		}
	}
	
	//Shows a YES/NO box and either closes the game or restarts it
	public boolean restart(){
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(this, "Restart?", "Game Ended! " + winner + " Won!", dialogButton);  //shows which player won the game
		if(dialogResult == 0){
		  return true;
		}
		else{
		  System.exit(1);
		  return false;
		} 
		
    }

	//This method checks for any of the trails colliding with each other, themselves or the boundries
	//it then returns true or false for resume game or end game
	//also increases the score of the players
	public boolean checkStatus(){
		int s =trail.size()-1; // gives you the leading point on the trail as it was added last to the Arraylist
		
		//Checks collision for Player 1 with boundary
		if(trail.get(s).getX() > 895 || trail.get(s).getX() < 95 || trail.get(s).getY() > 945 || trail.get(s).getY() < 145){
			winner = "Player 2";
			score2++;
			return false;
			}
			
		//Checks collision for Player 2 with boundary
		if(trail2.get(s).getX() > 895 || trail2.get(s).getX() < 95 || trail2.get(s).getY() > 945 || trail2.get(s).getY() < 145){
			winner = "Player 1";
			score1++;
			return false;
		}
		
		//Checks collision for Player 1 with Player 2
		for (int i = 0; i < trail2.size()-1;i++){
			if(trail.get(s).equals(trail2.get(i))){
				winner = "Player 2";
				score2++;
				return false;
			}
			
			//Checks collision for Player 2 with itself
			if(trail2.get(s).equals(trail2.get(i))){
				winner = "Player 1";
				score1++;
				return false;
			}
		}
		
		//Checks collision for Player 2 with Player 1
		for (int i = 0; i < trail.size()-1;i++){
			if(trail2.get(s).equals(trail.get(i))){
				winner = "Player 1";
				score1++;
				return false;
			}
			
			//Checks collision for Player 1 with itself
			if(trail.get(s).equals(trail.get(i))){
				winner = "Player 2";
				score2++;
				return false;
				
			}
		}
		return true;		
	}
	
	
    public void keyTyped(KeyEvent e) {}

	//This method sets the direction in which the trail will move in
	//It restricts the user from moving in the direction directly opposite to its current direction
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        
        //For Player 1
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			xstep2 = (xstep2 == -speed)?-speed:speed;  //For example here the direction can't be changed to right if the trail was already going left
        	ystep2 = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			xstep2 = (xstep2 == speed)?speed:-speed;
        	ystep2 = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP){
			xstep2 = 0;
        	ystep2 = (ystep2 == speed)?speed:-speed;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			xstep2 = 0;
        	ystep2 = (ystep2 == -speed)?-speed:speed;
		}
		
		
		//For Player 2
		if(e.getKeyCode() == KeyEvent.VK_D){
        	xstep = (xstep == -speed)?-speed:speed;
        	ystep = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
        	xstep = (xstep == speed)?speed:-speed;
        	ystep = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_W){
        	xstep = 0;
        	ystep = (ystep == speed)?speed:-speed;
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
        	xstep = 0;
        	ystep = (ystep == -speed)?-speed:speed;
		}
    }
    
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
     
    //The paintComponent creates the graphics of the game
    public void paintComponent(Graphics g){ 
    	
    	//Draws the grid image and logo image only one time	
    	if(drawimage){
			g.setColor(Color.decode("#000000"));
    		g.fillRect(0,0,1022,1022);
    		g.drawImage(back,100,150,this);
			g.drawImage(logo,350,20,this);
    		drawimage = false;
    	}
    	
    	g.setFont(new Font("TR2N", Font.PLAIN, 30)); 
    	
    	//Draw the trails
		g.setColor(Color.decode("#FFFF00"));
		g.fillRect(boxx,boxy,5,5);
		g.setColor(Color.decode("#F8F8F8"));
		g.fillRect(boxx2,boxy2,5,5);
		g.setColor(Color.decode("#FFFF"));
		
		//Outline box
		for(int i = 0; i < 5; i++){
			g.drawRect(96+i,146+i,800,800);
		}
		
		//Player 1 Score box
        g.setColor(Color.decode("#FFFF00"));
        g.drawString("Player 1", 110, 50);
        g.setColor(Color.decode("#FFFF"));
        g.drawRect(130,60,100,70);
        
       	//Player 2 score box
        g.setColor(Color.decode("#F8F8F8"));
        g.drawString("Player 2", 720, 50);
        g.setColor(Color.decode("#FFFF"));
        g.drawRect(740,60,100,70);
        
        
        //Write the scores for Player 1 nad 2
        g.setFont(new Font("TR2N", Font.PLAIN, 50));
        g.setColor(Color.decode("#FFFF00"));
        g.drawString(Integer.toString(score1), 160, 110);
        g.setColor(Color.decode("#F8F8F8"));
        g.drawString(Integer.toString(score2), 770, 110);
        
		
    }
}