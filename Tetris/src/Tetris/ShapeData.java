package Tetris;

import java.util.Scanner;

public class ShapeData {

	// Shape informations.
	boolean[][][] rotationStates = new boolean[4][4][4];
	String name;
	int defaultState;
	int colorIndex;
	
	// Instantiate the 7 different shapes
	public static final ShapeData line = new ShapeData("/line.txt", "line", 1, 0);
	public static final ShapeData square = new ShapeData("/square.txt", "square", 0, 1);
	public static final ShapeData lshape = new ShapeData("/lshape.txt", "lshape", 1, 2);
	public static final ShapeData jshape = new ShapeData("/jshape.txt", "jshape", 3, 3);
	public static final ShapeData tee = new ShapeData("/tee.txt", "tee", 0, 4);
	public static final ShapeData zshape = new ShapeData("/zshape.txt", "zshape", 1, 5);
	public static final ShapeData sshape = new ShapeData("/sshape.txt", "sshape", 1, 6);
	
	/**
	 * Constructor of a shape.
	 * 
	 * @param filename
	 * 		File name of the shape.
	 * @param name
	 * 		Name of the shape.
	 * @param defaultState
	 * 		Default state.
	 * @param colorIndex
	 * 		Color index of the shape.
	 */
	public ShapeData(String filename, String name, int defaultState, int colorIndex) {
		this.name = name;
		this.defaultState = defaultState;
		this.colorIndex = colorIndex;
		
		Scanner s = new Scanner(ShapeData.class.getResourceAsStream(filename));
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				String line = s.nextLine();
				for (int k = 0; k < 4; k++) {
					rotationStates[i][k][j] = line.charAt(k) == '1';
				}
			}
		}
		s.close();
	}

}
