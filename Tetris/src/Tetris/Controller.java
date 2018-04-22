package Tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {
	
	TetrisMain game;
	public boolean left, right, down, rotate, pause;
	
	public Controller(TetrisMain game){
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.left)){
			left = true;
			if (TetrisMain.running && !TetrisMain.pause){
				Action.moveShape(Config.left);
			}			
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.right)){
			right = true;
			if (TetrisMain.running && !TetrisMain.pause){
				Action.moveShape(Config.right);
			}
			
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.rotate)){
			rotate = true;
			if (TetrisMain.running && !TetrisMain.pause){
				Action.moveShape(Config.rotate);
			}
			
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.down)){
			down = true;
			if (TetrisMain.running && !TetrisMain.pause){
				Action.moveShape(Config.down);
			}
			
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.pause)){
			pause = true;
			Action.pauseGame();
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
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
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()).equals(Config.pause)){			
			pause = false;
		}
	}
	
}
