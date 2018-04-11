package Tetris;

public class Bot implements Runnable {

	@Override
	public void run() {
		while (TetrisMain.runnable) {
			if (TetrisMain.running) {
				Action.moveShape(Config.down);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
