package Tetris;

import java.awt.Graphics2D;
import java.awt.Image;

public class TetrisGrid {

	public static int width, height, widthOff, heightOff;
	// -1 means empty, 0-6 is draw a block
	public static int[][] grid;
	public static int[][] gridShape;
	public static int[][] gridRotation;
	
	private int SQUARE_SIZE = 25;
	private Image[] tetrisBlocks;	

	public TetrisGrid(int width, int height, int wOff, int hOff, Image[] blocks) {

		widthOff = wOff;
		heightOff = hOff;
		TetrisGrid.width = width / SQUARE_SIZE;
		TetrisGrid.height = height / SQUARE_SIZE;
		grid = new int[TetrisGrid.width][TetrisGrid.height];
		gridShape = new int[TetrisGrid.width][TetrisGrid.height];
		gridRotation = new int[TetrisGrid.width][TetrisGrid.height];
		for (int x = 0; x < TetrisGrid.width; x++) {
			for (int y = 0; y < TetrisGrid.height; y++) {
				grid[x][y] = -1;
				gridShape[x][y] = -1;
				gridRotation[x][y] = -1;
			}
		}
		
		Action.invocShape(gridShape);

		// int rowsRemoved = checkForRemoval();

		tetrisBlocks = blocks;
	}

	public static int checkForRemoval() {
		int rowsRemoved = 0;
		for (int y = 0; y < height; y++) {
			boolean isRowFull = true;
			for (int x = 0; x < width; x++) {
				if (grid[x][y] == -1) {
					isRowFull = false;
				}
			}
			if (isRowFull) {
				removeRow(y);
				rowsRemoved++;
			}
		}
		return rowsRemoved;
	}
	
	public static void checkForGameOver() {		
			TetrisMain.runnable = false;				
	}

	private static void removeRow(int row) {
		for (int x = 0; x < width; x++) {
			for (int y = row; y > 0; y--) {
				grid[x][y] = grid[x][y - 1];
			}
		}

		for (int x = 0; x < width; x++) {
			grid[x][0] = -1;
		}
	}

	public void drawGrid(Graphics2D g) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (grid[x][y] != -1) {
					g.drawImage(tetrisBlocks[grid[x][y]], widthOff + x * SQUARE_SIZE, heightOff + y * SQUARE_SIZE,
							SQUARE_SIZE, SQUARE_SIZE, null);					
				} else if (gridShape[x][y] != -1){
					g.drawImage(tetrisBlocks[gridShape[x][y]], widthOff + x * SQUARE_SIZE, heightOff + y * SQUARE_SIZE,
							SQUARE_SIZE, SQUARE_SIZE, null);
				}
			}
		}
	}

}
