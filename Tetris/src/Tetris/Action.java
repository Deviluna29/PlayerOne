package Tetris;

import java.io.IOException;
import java.util.Random;

public class Action {

	public static Random random = new Random();

	public static int index_weight_shape;
	public static int index_height_shape;
	// Actual shape index.
	public static int shapeNumber;
	// Next shape index.
	public static int shapeNumberAfter = random.nextInt(6);

	public static int defaultState;
	public static int colorIndex;
	public static int defaultStateAfter;
	public static int colorIndexAfter;

	public static int score = 0;
	public static int totalRowsRemoved = 0;
	public static int tetrisMade = 0;

	
	// Array composed of the 7 different shapes.
	public static ShapeData[] shapes = new ShapeData[7];

	
	/**
	 * Invoke a new shape on the grid.
	 * 
	 * @param grid
	 * 		The grid used to add the shape.
	 * @throws IOException 
	 */
	public static void invocShape(int[][] grid) throws IOException {

		resetGridView();

		// Parameters used for the placement of the new shape.
		index_weight_shape = 3;
		index_height_shape = 0;

		// Check if rows are needed to be removed first.
		int rowsRemoved = checkForRemoval();
		
		// Avoid the user to move down rapidly the new shape if the down keyboard key is pressed.
		if (Controller.down) {
			Controller.keyDownReleasedOneTime = false;
		}
		
		// Update of the different informations to the user.
		if (rowsRemoved == 4){
			tetrisMade++;
		}
		
		totalRowsRemoved += rowsRemoved;
		score += Scoring.calculScore(rowsRemoved);
		Scoring.calculLevel(totalRowsRemoved);
		
		shapeNumber = shapeNumberAfter;
		shapeNumberAfter = random.nextInt(6);

		// Fill the array composed of shapes.
		shapes[0] = ShapeData.line;
		shapes[1] = ShapeData.square;
		shapes[2] = ShapeData.lshape;
		shapes[3] = ShapeData.jshape;
		shapes[4] = ShapeData.tee;
		shapes[5] = ShapeData.zshape;
		shapes[6] = ShapeData.sshape;

		// Define the new shape to invoke.
		ShapeData newShape = shapes[shapeNumber];

		defaultState = newShape.defaultState;
		colorIndex = newShape.colorIndex;

		// Define the next shape to come.
		ShapeData shapeAfter = shapes[shapeNumberAfter];

		defaultStateAfter = shapeAfter.defaultState;
		colorIndexAfter = shapeAfter.colorIndex;

		// Fill the gridShape with the new shape invoked.
		boolean rowEmpty = true;
		int delta = 0;
		boolean gameOver = false;
		
		for (int y = 0; y < 4; y++) {
			if (rowEmpty && y != 0){
				delta++;
			} else {
				rowEmpty = true;
			}
			for (int x = 0; x < 4; x++) {
				if (newShape.rotationStates[defaultState][x][y] == true) {
					if (TetrisGrid.grid[3 + x][y - delta] != -1){
						gameOver = true;
					} else {
						grid[3 + x][y - delta] = colorIndex;
						rowEmpty = false;
					}
					
				}
			}
		}

		// Fill the Grid view with the next shape to come.
		rowEmpty = true;
		delta = 0;		
		
		for (int y = 0; y < 4; y++) {
			if (rowEmpty && y != 0){
				delta++;
			} else {
				rowEmpty = true;
			}
			for (int x = 0; x < 4; x++) {
				if (shapeAfter.rotationStates[defaultStateAfter][x][y] == true) {
					TetrisGrid.gridView[x][y - delta] = colorIndexAfter;
					rowEmpty = false;
				}
			}
		}
		
		// Stop the game if gameOver is true.
		if (gameOver){
			gameOver();
		}
	}
	
	/**
	 * Check if one or more row are needed to be removed.
	 * 
	 * @return
	 * 		The number of rows removed.
	 */
	private static int checkForRemoval() {
		int rowsRemoved = 0;
		for (int y = 0; y < TetrisGrid.height; y++) {
			boolean isRowFull = true;
			for (int x = 0; x < TetrisGrid.width; x++) {
				if (TetrisGrid.grid[x][y] == -1) {
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

	/**
	 * Removes the full rows from the grid.
	 * 
	 * @param row
	 */
	private static void removeRow(int row) {
		for (int x = 0; x < TetrisGrid.width; x++) {
			for (int y = row; y > 0; y--) {
				TetrisGrid.grid[x][y] = TetrisGrid.grid[x][y - 1];
			}
		}

		for (int x = 0; x < TetrisGrid.width; x++) {
			TetrisGrid.grid[x][0] = -1;
		}
	}

	/**
	 * Rotates the shape if possible.
	 */
	private static void rotateShape() {
		defaultState++;
		if (defaultState == 4) {
			defaultState = 0;
		}

		ShapeData newShape = shapes[shapeNumber];
		if (0 <= index_weight_shape && index_weight_shape <= 6 && 0 <= index_height_shape && index_height_shape <= 17) {

			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					if (newShape.rotationStates[defaultState][x][y] == true) {
						TetrisGrid.gridRotation[index_weight_shape + x][index_height_shape + y] = colorIndex;
					}
				}
			}

			boolean rotationPossible = true;

			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 22; y++) {
					if (TetrisGrid.gridRotation[x][y] != -1) {
						if (TetrisGrid.grid[x][y] != -1) {
							rotationPossible = false;
						}
					}
				}
			}

			if (rotationPossible) {
				for (int x = 0; x < 10; x++) {
					for (int y = 0; y < 22; y++) {
						TetrisGrid.gridShape[x][y] = TetrisGrid.gridRotation[x][y];
					}
				}
			}

			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 22; y++) {
					TetrisGrid.gridRotation[x][y] = -1;
				}
			}
		}
	}

	/**
	 * Move the shape to the direction wanted if possible.
	 * 
	 * @param key
	 * 		The keyboard key pressed.
	 * @throws IOException 
	 */
	public static void moveShape(String key) throws IOException {

		boolean detectEdgeLeft = detectEdgeLeft(TetrisGrid.grid, TetrisGrid.gridShape);
		boolean detectEdgeRight = detectEdgeRight(TetrisGrid.grid, TetrisGrid.gridShape);
		boolean detectCollision = detectCollision(TetrisGrid.grid, TetrisGrid.gridShape);

		if (key.equals(Config.down) && detectCollision && index_weight_shape == 3
				&& index_height_shape == 0) {
			gameOver();
		}

		if (key.equals(Config.down)) {
			if (detectCollision) {
				resetGridShape();
				// Invoke the next shape to come.
				invocShape(TetrisGrid.gridShape);
			} else {
				for (int x = 0; x < 10; x++) {
					for (int y = 21; y >= 0; y--) {
						if (y == 0) {
							TetrisGrid.gridShape[x][y] = -1;
						} else {
							TetrisGrid.gridShape[x][y] = TetrisGrid.gridShape[x][y - 1];
						}
					}
				}
				index_height_shape++;
			}

		} else if (key.equals(Config.left) && !detectEdgeLeft) {
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 22; y++) {
					if (x == 9) {
						TetrisGrid.gridShape[x][y] = -1;
					} else {
						TetrisGrid.gridShape[x][y] = TetrisGrid.gridShape[x + 1][y];
					}

				}
			}
			index_weight_shape--;
		} else if (key.equals(Config.right) && !detectEdgeRight) {
			for (int x = 9; x >= 0; x--) {
				for (int y = 0; y < 22; y++) {
					if (x == 0) {
						TetrisGrid.gridShape[x][y] = -1;
					} else {
						TetrisGrid.gridShape[x][y] = TetrisGrid.gridShape[x - 1][y];
					}

				}
			}
			index_weight_shape++;
		} else if (key.equals(Config.rotate)) {
			rotateShape();
		}
	}

	/**
	 * Detect if there is an obstacle to the left of the shape in movement.
	 * 
	 * @param grid
	 * 		The principal grid.
	 * @param gridShape
	 * 		The grid with the shape in movement.
	 * @return
	 * 		A boolean.
	 */
	private static boolean detectEdgeLeft(int[][] grid, int[][] gridShape) {
		boolean edgeLeft = false;
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 22; y++) {
				if (gridShape[x][y] != -1 && x != 0) {
					if (grid[x - 1][y] != -1) {
						edgeLeft = true;
					}
				} else if (gridShape[x][y] != -1 && x == 0) {
					edgeLeft = true;
				}

			}
		}
		return edgeLeft;
	}

	/**
	 * Detect if there is an obstacle to the right of the shape in movement.
	 * 
	 * @param grid
	 * 		The principal grid.
	 * @param gridShape
	 * 		The grid with the shape in movement.
	 * @return
	 * 		A boolean.
	 */
	private static boolean detectEdgeRight(int[][] grid, int[][] gridShape) {
		boolean edgeRight = false;
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 22; y++) {
				if (gridShape[x][y] != -1 && x != 9) {
					if (grid[x + 1][y] != -1) {
						edgeRight = true;
					}
				} else if (gridShape[x][y] != -1 && x == 9) {
					edgeRight = true;
				}

			}
		}
		return edgeRight;
	}

	/**
	 * Detect if there is an obstacle to the bottom of the shape in movement.
	 * 
	 * @param grid
	 * 		The principal grid.
	 * @param gridShape
	 * 		The grid with the shape in movement.
	 * @return
	 * 		A boolean.
	 */
	private static boolean detectCollision(int[][] grid, int[][] gridShape) {
		boolean collision = false;
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 22; y++) {
				if (gridShape[x][y] != -1 && y != 21) {
					if (grid[x][y + 1] != -1) {
						collision = true;
					}
				} else if (gridShape[x][y] != -1 && y == 21) {
					collision = true;
				}
			}
		}
		return collision;
	}

	/**
	 * Rest the Grid shape, but fusion the principal grid with it before.
	 */
	private static void resetGridShape() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 22; y++) {
				if (TetrisGrid.gridShape[x][y] != -1) {
					TetrisGrid.grid[x][y] = TetrisGrid.gridShape[x][y];
					TetrisGrid.gridShape[x][y] = -1;
				}
			}
		}		
	}

	/**
	 * Reset the Grid view.
	 */
	private static void resetGridView() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				TetrisGrid.gridView[x][y] = -1;
			}
		}
	}
	
	/**
	 * Compare the actual score with high score and change it if necessary.
	 *  
	 * @param score
	 * 		The actual score.
	 * @throws IOException
	 */
	public static void compareScores(int score) throws IOException {
		int highScore = Scoring.getHighScore();
		if (score > highScore) {
			Scoring.saveNewHighScore(score);
		}
	}

	/**
	 * Put the game in pause.
	 */
	public static void pauseGame() {
		if (TetrisMain.pause) {
			TetrisMain.pause = false;
		} else {
			TetrisMain.pause = true;
		}
	}
	
	/**
	 * Game over, stop the game running.
	 * @throws IOException 
	 */
	private static void gameOver() throws IOException {
		TetrisMain.running = false;
		compareScores(score);
	}
}
