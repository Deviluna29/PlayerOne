package Tetris;

public class Scoring {

	public static int level = 1;

	public static int calculScore(int rowsRemoved) {
		int score = 0;

		switch (rowsRemoved) {
		case 0:
			break;
		case 1:
			score = 40 * level;
			break;
		case 2:
			score = 100 * level;
			break;
		case 3:
			score = 300 * level;
			break;
		case 4:
			score = 1200 * level;
			break;
		}

		return score;
	}

	public static void calculLevel(int totalRowsRemoved) {
		if (totalRowsRemoved >= 10 && totalRowsRemoved < 200) {
			double level = totalRowsRemoved / 10 + 1;
			Scoring.level = (int) level;
		} else if (totalRowsRemoved >= 200) {
			Scoring.level = 20;
		}
	}

}
