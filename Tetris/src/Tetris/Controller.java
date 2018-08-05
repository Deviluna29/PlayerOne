package Tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Controller implements KeyListener {
	
	TetrisMain game;
	public static boolean left, right, down, rotate, pause;
	
	// Used to avoid the shape from continuing to go down rapidly when a new shape comes.
	public static boolean keyDownReleasedOneTime = true;
	
	public Controller(TetrisMain game){
		this.game = game;
	}

	/**
	 * Detects a keyboard key pressed.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.left)){
			left = true;
			if (TetrisMain.running && !TetrisMain.pause){
				try {
					Action.moveShape(Config.left);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}			
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.right)){
			right = true;
			if (TetrisMain.running && !TetrisMain.pause){
				try {
					Action.moveShape(Config.right);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.rotate)){
			rotate = true;
			if (TetrisMain.running && !TetrisMain.pause){
				try {
					Action.moveShape(Config.rotate);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.down)){
			down = true;
			if (TetrisMain.running && !TetrisMain.pause && keyDownReleasedOneTime){
				try {
					Action.moveShape(Config.down);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.pause)){
			pause = true;
			Action.pauseGame();
		}
	}

	/**
	 * Detects a keyboard key released.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.left)){
			left = false;
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.right)){
			right = false;
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.rotate)){
			rotate = false;
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.down)){
			down = false;
			keyDownReleasedOneTime = true;
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.pause)){			
			pause = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}
