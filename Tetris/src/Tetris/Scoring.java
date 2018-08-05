package Tetris;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Scoring {

	public static int level = 1;

	/**
	 * Calculates the score attributed after one or more row is removed.
	 * 
	 * @param rowsRemoved
	 * 		Number of row removed.
	 * @return
	 * 		The score.
	 */
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

	/**
	 * Calculates the level.
	 * 
	 * @param totalRowsRemoved
	 * 		Total number of row removed.
	 */
	public static void calculLevel(int totalRowsRemoved) {
		if (totalRowsRemoved >= 10 && totalRowsRemoved < 200) {
			double level = totalRowsRemoved / 10 + 1;
			Scoring.level = (int) level;
		} else if (totalRowsRemoved >= 200) {
			Scoring.level = 20;
		}
	}
	
	/**
	 * Get the high score value from the file.
	 * 
	 * @return
	 * 		The high score value.
	 * @throws IOException
	 */
	public static int getHighScore() throws IOException {
		int highScore = 0;		
		File highScoreFile = Config.getHighScoreFile();
		
		Scanner s = new Scanner(highScoreFile);
		while(s.hasNextLine()) {
			String[] entry = s.nextLine().split(":");
			highScore = Integer.parseInt(entry[1]);
		}
		
		
		return highScore;
	}
	
	/**
	 * Save the new high score in the file.
	 * 
	 * @param highScore
	 * 		The high score value.
	 * @throws IOException
	 */
	public static void saveNewHighScore(int highScore) throws IOException {		
		File highScoreFile = Config.getHighScoreFile();
		
		PrintWriter pw = new PrintWriter(highScoreFile);
		pw.println("Highscore is:" + highScore);
		pw.close();
		
	}
}
