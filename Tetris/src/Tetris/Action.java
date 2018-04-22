package Tetris;

import java.util.Random;

public class Action {

	public static Random random = new Random();

	public static int index_weight_shape;
	public static int index_height_shape;
	public static int shapeNumber;
	public static int shapeNumberAfter = random.nextInt(6);

	public static int defaultState;
	public static int colorIndex;
	public static int defaultStateAfter;
	public static int colorIndexAfter;

	public static int score = 0;
	public static int totalRowsRemoved = 0;

	public static ShapeData[] shapes = new ShapeData[7];

	public static void invocShape(int[][] grid) {

		resetGridView();

		index_weight_shape = 3;
		index_height_shape = 0;

		int rowsRemoved = TetrisGrid.checkForRemoval();
		totalRowsRemoved += rowsRemoved;
		score += Scoring.calculScore(rowsRemoved);
		Scoring.calculLevel(totalRowsRemoved);

		shapeNumber = shapeNumberAfter;
		shapeNumberAfter = random.nextInt(6);

		shapes[0] = ShapeData.line;
		shapes[1] = ShapeData.square;
		shapes[2] = ShapeData.lshape;
		shapes[3] = ShapeData.jshape;
		shapes[4] = ShapeData.tee;
		shapes[5] = ShapeData.zshape;
		shapes[6] = ShapeData.sshape;

		ShapeData newShape = shapes[shapeNumber];

		defaultState = newShape.defaultState;
		colorIndex = newShape.colorIndex;

		ShapeData shapeAfter = shapes[shapeNumberAfter];

		defaultStateAfter = shapeAfter.defaultState;
		colorIndexAfter = shapeAfter.colorIndex;

		boolean rowEmpty = true;
		int delta = 0;
		boolean gameOver = false;
		
		for (int y = 0; y < 4; y++) {
			if (rowEmpty && y != 0){
				delta++;
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

		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (shapeAfter.rotationStates[defaultStateAfter][x][y] == true) {
					TetrisGrid.gridView[x][y] = colorIndexAfter;
				}
			}
		}
		
		if (gameOver){
			TetrisGrid.checkForGameOver();
		}
	}

	public static void rotateShape() {
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

	public static void moveShape(String key) {

		boolean detectEdgeLeft = detectEdgeLeft(TetrisGrid.grid, TetrisGrid.gridShape);
		boolean detectEdgeRight = detectEdgeRight(TetrisGrid.grid, TetrisGrid.gridShape);
		boolean detectCollision = detectCollision(TetrisGrid.grid, TetrisGrid.gridShape);

		if (key.equals(Config.down) && detectCollision && index_weight_shape == 3
				&& index_height_shape == 0) {
			TetrisGrid.checkForGameOver();
		}

		if (key.equals(Config.down)) {
			if (detectCollision) {
				resetGridShape();
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

	public static boolean detectEdgeLeft(int[][] grid, int[][] gridShape) {
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

	public static boolean detectEdgeRight(int[][] grid, int[][] gridShape) {
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

	public static boolean detectCollision(int[][] grid, int[][] gridShape) {
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

	public static void resetGridShape() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 22; y++) {
				if (TetrisGrid.gridShape[x][y] != -1) {
					TetrisGrid.grid[x][y] = TetrisGrid.gridShape[x][y];
					TetrisGrid.gridShape[x][y] = -1;
				}
			}
		}
		invocShape(TetrisGrid.gridShape);
	}

	public static void resetGridView() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				TetrisGrid.gridView[x][y] = -1;
			}
		}
	}

	public static void pauseGame() {
		if (TetrisMain.pause) {
			TetrisMain.pause = false;
		} else {
			TetrisMain.pause = true;
		}
	}
}
