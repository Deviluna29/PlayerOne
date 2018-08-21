package Tetris;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TetrisMain extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	// Used to set the Width and Height of the game screen.
	public static final int WIDTH = 406, HEIGHT = 629;
	
	// Used to set the Width and Height of the game area.
	public static final int WIDTH_GRID = 250, HEIGHT_GRID = 550;

	public static Image[] tetrisBlocks;
	public static Image fond;
	private static Image pauseImage;

	Controller control;

	// Initialize a grid to play on it.
	private static TetrisGrid tetrisGrid;

	// Game is paused if true.
	public static boolean pause = false;
	// Keep the game running if true.
	public static boolean running = true;
	// Keep the entire application running.
	public static boolean runnable = true;

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Set the game's screen
		final JFrame frame = new JFrame("Tetris");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setLayout(null);
		
		// Load keyboard keys and the configuration.
		KeyGetter.loadKeys();
		try {
			Config.loadConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Set the Menu.
		JMenuBar bar = new JMenuBar();
		bar.setBounds(0, 0, WIDTH, 25);

		JMenu menuFile = new JMenu("File");
		menuFile.setBounds(0, 0, 45, 24);

		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Code for new game
				System.out.println("Starting New Game...");
				try {
					newGame();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JMenuItem highScore = new JMenuItem("Highscore");
		highScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int highscore = 0;
				try {
					highscore = Scoring.getHighScore();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JFrame alert = new JFrame("High Score");
				alert.setSize(250, 150);
				alert.setLayout(null);
				alert.setLocationRelativeTo(null);
				alert.setAlwaysOnTop(true);

				JLabel score = new JLabel("The highscore is: " + highscore);
				score.setBounds(0, 0, 200, 50);

				JButton okayButton = new JButton("Okay");
				okayButton.setBounds(75, 80, 100, 30);
				okayButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
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
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Action.compareScores(Action.score);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Closing...");
				System.exit(0);
			}
		});
		
		JMenuItem theme = new JMenuItem("Theme");
		theme.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {				
				Theme.openTheme(frame);						
			}
		});

		JMenuItem options = new JMenuItem("Options");
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.openConfig(frame);
			}
		});

		// Initialize the game's screen
		TetrisMain tm = new TetrisMain();
		tm.setBounds(0, 25, WIDTH, HEIGHT - 25);

		frame.add(tm);
		menuFile.add(newGame);
		menuFile.add(highScore);
		menuFile.add(options);
		menuFile.add(theme);
		menuFile.add(exit);
		bar.add(menuFile);
		frame.add(bar);
		frame.setVisible(true);
		tm.start();

	}

	/**
	 * Start a new Thread.
	 */
	private void start() {
		// Start a new Thread
		Thread t = new Thread(this);
		// t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	}

	/**
	 * Run the game.
	 */
	@Override
	public void run() {
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Start a new Thread for the "Bot".
		Bot bot = new Bot();
		Thread t = new Thread(bot);
		t.start();
		while (runnable) {
			BufferStrategy buf = getBufferStrategy();
			if (buf == null) {
				createBufferStrategy(3);
				continue;
			}
			Graphics2D g = (Graphics2D) buf.getDrawGraphics();
			render(g);
			buf.show();
		}

	}
	
	/**
	 * Initialize the Grid.
	 * @throws IOException 
	 */
	private void init() throws IOException {
		control = new Controller(this);
		this.addKeyListener(control);
		requestFocus();
		try {
			tetrisBlocks = ImageLoader.loadImage("g/tetris.png", 25);
		} catch (IOException e) {
			System.out.println("Error loading in tetris.png");
			System.exit(1);
		}
		
		try {
			fond = ImageLoader.loadImageFond("/fondMenuDroite.png");
		} catch (IOException e) {
			System.out.println("Error loading in fondMenuDroite.png");
			System.exit(1);
		}
		
		try {
			pauseImage = ImageLoader.loadImageFond("/pauseImage.png");
		} catch (Exception e) {
			System.out.println("Error loading in pauseImage.png");
			System.exit(1);
		}
		tetrisGrid = new TetrisGrid(WIDTH_GRID, HEIGHT_GRID, 0, 25, tetrisBlocks);
	}
	
	/**
	 * Start a new Game, and reset some parameters.
	 * @throws IOException 
	 */
	private static void newGame() throws IOException {
		TetrisMain.running = true;
		Action.shapeNumberAfter = Action.random.nextInt(6);
		tetrisGrid = new TetrisGrid(WIDTH_GRID, HEIGHT_GRID, 0, 25, tetrisBlocks);
		Action.compareScores(Action.score);
		Action.score = 0;
		Action.totalRowsRemoved = 0;
		Action.tetrisMade = 0;
		Scoring.level = 1;
		
	}

	/**
	 * Fill the grid with graphics stuff.
	 * 
	 * @param g
	 * 		Graphic element.
	 */
	private static void render(Graphics2D g) {
		// Set the background color and other stuff like a Title.
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, 25);		
		g.setColor(Color.BLACK);
		g.fillRect(0, 25, WIDTH_GRID, HEIGHT_GRID);
		
		//Draw the backScreen on the right.	
		g.drawImage(fond, WIDTH_GRID, 25, WIDTH - WIDTH_GRID - 6, HEIGHT_GRID, null);		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Tetris", 170, 20);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Calibri", Font.PLAIN, 22));
		g.drawString("Score: " + String.valueOf(Action.score), 275, 150);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Level: " + String.valueOf(Scoring.level), 275, 200);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Lignes : " + String.valueOf(Action.totalRowsRemoved), 275, 230);
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Tétris: " + String.valueOf(Action.tetrisMade), 275, 260);
		tetrisGrid.drawGrid(g);
		tetrisGrid.drawGridView(g);
				
		//Draw the pause Image.
		if (pause){
			g.drawImage(pauseImage, 75, 225, 100, 100, null);
		}
		
	}
}
