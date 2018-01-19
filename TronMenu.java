//Hassan Arshad
//Tron LightCycles game
//Menu

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;

public class TronMenu extends JFrame implements ActionListener{
	private boolean gameStart = false;
	MenuPanel menu;  
	Timer myTimer;  
	
	//Two buttons start game and quit
	JButton startBox = new JButton("START GAME");
    JButton quitBox = new JButton ("QUIT");
    JLabel display = new JLabel ("                  ");
	
	//Constructor of the class places the buttons in the appropriate place and adds the Panel
    public TronMenu() {
		super("TRON LIGHTCYCLE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1022,1022);
		
		myTimer = new Timer(10, this);
		
		menu = new MenuPanel(this);
		add(menu);
		
		JPanel pane = new JPanel ();
		pane.setLayout(null);
	
		startBox.addActionListener (this);
		quitBox.addActionListener (this);
		pane.add (startBox);
		pane.add (quitBox);
		pane.add (display);
		
		startBox.setLocation(389,600);
		startBox.setSize(200,50);
	
		quitBox.setLocation(389,700);
		quitBox.setSize(200,50);
	
	
		getContentPane().add(pane);
		
		
		setResizable(true);
		setVisible(true);
		
		
    }
    
	public void actionPerformed (ActionEvent evt){
		chooseOption(evt);		
    }
    
    public void start(){
		myTimer.start();
	}
    
    public boolean checkGame(){
    	return gameStart;
    }
    
    //
    public void chooseOption(ActionEvent evt){
    	Object source = evt.getSource();
    	
    	//Start Game button triggers the gameStart and hides the menu
		if (source == startBox){
		    gameStart = true;
		    setVisible(false);
		}
		
		//Quit shows a Yes/No box and quits the game if the user selects yes or does nothing if they click no
		else if (source == quitBox){
		    Object[] options = {"YES", "NO"};
		    int tmp = JOptionPane.showOptionDialog (null, "Are you sure?", "Quit Game",
			    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
			    null, options, options [0]);
		    if(tmp == 1){
			  JOptionPane.showMessageDialog (null, "Good Choice...");
			}
			else{
			  System.exit(1);
			}
		}
    }
}

//This class is used to draw the background Image
class	MenuPanel extends JPanel{
	private TronMenu mainFrame;
	private Image back;
	
	public MenuPanel(TronMenu m){
		mainFrame = m;
		back = new ImageIcon("TMenu.jpg").getImage();
		setSize(1022,1022);
		setVisible(true);

	}
	
	public void paintComponent(Graphics g){ 	
    	g.fillRect(0,0,1022,1022);
    	g.drawImage(back,0,0,this);	
		
    }
}