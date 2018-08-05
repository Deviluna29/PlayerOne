package Tetris;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

public class TetrisGrid {

	// Parameters to initialize the grids.
	public static int width, height, widthOff, heightOff;
	private int SQUARE_SIZE = 25;
	private Image[] tetrisBlocks;
	
	/*
	 * The following grid are superposed like layers sheet, to draw the final grid.
	 *   
	 *  
	 *  -1 means empty, 0-6 is draw a block
	 */
	
	// Principal grid
	public static int[][] grid;
	
	// Grid used for the actual moving shape.
	public static int[][] gridShape;
	
	// Grid used to see the next shape to come.
	public static int[][] gridView;
	
	// Grid used to rotate (if possible) the actual moving shape.
	public static int[][] gridRotation;	

	/**
	 * Constructor of the grids.
	 * 
	 * @param width
	 * 		Width of the game area.
	 * @param height
	 * 		Height of the game area.
	 * @param wOff
	 * 
	 * @param hOff
	 * 
	 * @param blocks
	 * 		Array of subImages, that represent blocks.
	 * @throws IOException 
	 */
	public TetrisGrid(int width, int height, int wOff, int hOff, Image[] blocks) throws IOException {

		// Initialize parameters.
		widthOff = wOff;
		heightOff = hOff;
		TetrisGrid.width = width / SQUARE_SIZE;
		TetrisGrid.height = height / SQUARE_SIZE;
		tetrisBlocks = blocks;
		
		// Initialize the different grids size.
		grid = new int[TetrisGrid.width][TetrisGrid.height];
		gridShape = new int[TetrisGrid.width][TetrisGrid.height];
		gridView = new int[4][4];
		gridRotation = new int[TetrisGrid.width][TetrisGrid.height];
		
		// Fill the grids with empty blocks everywhere.
		for (int x = 0; x < TetrisGrid.width; x++) {
			for (int y = 0; y < TetrisGrid.height; y++) {
				grid[x][y] = -1;
				gridShape[x][y] = -1;
				gridRotation[x][y] = -1;
			}
		}

		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				gridView[x][y] = -1;
			}
		}

		// Invoke the first shape.
		Action.invocShape(gridShape);		
	}		

	/**
	 * Draw the principal Grid.
	 * 
	 * @param g
	 */
	public void drawGrid(Graphics2D g) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (grid[x][y] != -1) {
					g.drawImage(tetrisBlocks[grid[x][y]], widthOff + x * SQUARE_SIZE, heightOff + y * SQUARE_SIZE,
							SQUARE_SIZE, SQUARE_SIZE, null);
				} else if (gridShape[x][y] != -1) {
					g.drawImage(tetrisBlocks[gridShape[x][y]], widthOff + x * SQUARE_SIZE, heightOff + y * SQUARE_SIZE,
							SQUARE_SIZE, SQUARE_SIZE, null);
				}
			}
		}
	}

	/**
	 * Draw the Grid view used to see the next shape to come.
	 * 
	 * @param g
	 */
	public void drawGridView(Graphics2D g) {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (gridView[x][y] != -1) {
					g.drawImage(tetrisBlocks[gridView[x][y]], 275 + x * SQUARE_SIZE, 50 + y * SQUARE_SIZE,
							SQUARE_SIZE, SQUARE_SIZE, null);
				}
			}
		}
	}

}
