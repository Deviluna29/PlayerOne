package Tetris;

import java.io.IOException;

public class Bot implements Runnable {

	/**
	 * This "Bot" is used to move the shapes automatically, the speed depends of the level.
	 */
	@Override
	public void run() {
		while (TetrisMain.runnable) {
			if (TetrisMain.running && !TetrisMain.pause) {
				try {
					Action.moveShape(Config.down);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				// The speed of the "Bot" is adjusted here.
				Thread.sleep(1000-(Scoring.level*45));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
