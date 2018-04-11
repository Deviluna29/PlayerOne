package Tetris;

public class Bot implements Runnable {

	@Override
	public void run() {
		boolean runnable = true;
		while (runnable) {
				Action.moveShape(Config.down);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

		}
	}

}
