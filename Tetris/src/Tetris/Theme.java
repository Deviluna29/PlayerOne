package Tetris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Theme {
	
	
	public static void openTheme(JFrame frame){	
	// Construction of the theme windows.
			final JFrame options = new JFrame("Options");
			options.setSize(200, 400);
			options.setResizable(false);
			options.setLocationRelativeTo(frame);
			options.setLayout(null);
			
			// Set themes button to change the theme.
			JButton theme1 = new JButton("Theme 1");
			theme1.setBounds(50, 50, 100, 30);
theme1.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					try {
						TetrisMain.fond = ImageLoader.loadImageFond("/fondMenuDroite.png");
						TetrisMain.tetrisBlocks = ImageLoader.loadImage("/tetris.png", 25);
						TetrisGrid.tetrisBlocks = ImageLoader.loadImage("/tetris.png", 25);
					} catch (IOException e) {
						System.out.println("Error loading in fondMenuDroite.png");
						System.exit(1);
					}					
				}
			});
			
			JButton theme2 = new JButton("Theme 2");
			theme2.setBounds(50, 130, 100, 30);
			theme2.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					try {
						TetrisMain.fond = ImageLoader.loadImageFond("/fondMenuDroite2.png");
						TetrisMain.tetrisBlocks = ImageLoader.loadImage("/tetris2.png", 25);
						TetrisGrid.tetrisBlocks = ImageLoader.loadImage("/tetris2.png", 25);
					} catch (IOException e) {
						System.out.println("Error loading in fondMenuDroite2.png");
						System.exit(1);
					}					
				}
			});
			
			JButton theme3 = new JButton("Theme 3");
			theme3.setBounds(50, 210, 100, 30);
			
			// Set done button to close the theme window.
			JButton done = new JButton("Done");
			done.setBounds(50, 290, 100, 30);
			done.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					options.dispose();		
				}
			});
			
			options.add(theme1);
			options.add(theme2);
			options.add(theme3);
			options.add(done);
			options.setVisible(true);
	}
	
	private static void changeTheme() {
		
	}
}
