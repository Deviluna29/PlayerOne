package Tetris;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TetrisMain extends Canvas implements Runnable, KeyListener {

	// Used to set the Width and Height of the game's screen
	public static final int WIDTH = 400, HEIGHT = 565;
	
	public static void main(String[] args) {
		// Set the game's screen
		final JFrame frame = new JFrame("Tetris");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setLayout(null);
		
		KeyGetter.loadKeys();
		try {
			Config.loadConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Menu
		JMenuBar bar = new JMenuBar();		
		bar.setBounds(0, 0, WIDTH, 25);
		
		JMenu file = new JMenu("File");
		file.setBounds(0, 0, 45, 24);
		
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Code for new game
				System.out.println("Starting New Game...");
			}
		});
		
		JMenuItem highScore = new JMenuItem("Highscore");
		highScore.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int highscore = 0; //replace this with getHighscoreMethod later
				JFrame alert = new JFrame("High Score");
				alert.setSize(250, 150);
				alert.setLayout(null);
				alert.setLocationRelativeTo(null);
				alert.setAlwaysOnTop(true);
				
				JLabel score = new JLabel("The highscore is: " + highscore);
				score.setBounds(0, 0, 200, 50);
				
				JButton okayButton = new JButton("Okay");
				okayButton.setBounds(75, 80, 100, 30);
				okayButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						alert.dispose();
					}
				});
				alert.add(score);
				alert.add(okayButton);				
				alert.setResizable(false);
				alert.setVisible(true);				
			}
		});
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Closing...");
				System.exit(0);
			}
		});
		
		JMenuItem options = new JMenuItem("Options");
		options.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Config.openConfig(frame);
			}
		});
		
		// Initialize the game's screen
		TetrisMain tm = new TetrisMain();
		tm.setBounds(0, 25, WIDTH, HEIGHT - 25);
		
		frame.add(tm);
		file.add(newGame);
		file.add(highScore);
		file.add(options);
		file.add(exit);
		bar.add(file);
		frame.add(bar);
		frame.setVisible(true);
		tm.start();
	}
	
	public void start() {
		// Start a new Thread
		Thread t = new Thread(this);
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	}

	@Override
	public void run() {		
		boolean running = true;
		while (running){
			update();
			BufferStrategy buf = getBufferStrategy();
			if (buf == null){
				createBufferStrategy(3);
				continue;
			}
			Graphics2D g = (Graphics2D) buf.getDrawGraphics();
			render(g);
			buf.show();
		}
		
	}	
	
	public void init() {
		
	}

	private void update() {
		
		
	}
	
	private void render(Graphics2D g) {
		// Set the background color and other stuff like a Title
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Tetris", 170, 50);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
