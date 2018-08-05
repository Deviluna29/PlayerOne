package Tetris;

import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Config {
	
	// Value of the keyboard keys used to play.
	public static String rotate = "Haut", left = "Gauche", right = "Droite", down = "Bas", pause = "P";
	
	// List of choices used to compose the options window.
	public static ArrayList<Choice> choices;
	
	/**
	 * Open the options window, to set the keyboard keys used to play.
	 * 
	 * @param frame
	 * 		The frame from which the options window is related.
	 * 
	 */
	public static void openConfig(JFrame frame){
		choices = new ArrayList<Choice>();
		
		// Construction of the options windows.
		final JFrame options = new JFrame("Options");
		options.setSize(400, 300);
		options.setResizable(false);
		options.setLocationRelativeTo(frame);
		options.setLayout(null);
		
		// Add all the choices list for each game actions on the options window.
		Choice left = addChoice("Gauche", options, 30, 30);
		left.select(Config.left);
		Choice right = addChoice("Droite", options, 150, 30);
		right.select(Config.right);
		Choice rotate = addChoice("Rotation", options, 30, 80);
		rotate.select(Config.rotate);
		Choice down = addChoice("Descendre", options, 150, 80);
		down.select(Config.down);
		Choice pause = addChoice("Pause", options, 30, 130);
		pause.select(Config.pause);
		
		// Set done button to save and close the options window.
		JButton done = new JButton("Done");
		done.setBounds(75, 200, 100, 30);
		done.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				options.dispose();
				saveChanges();				
			}
		});
		
		// Set reinit button to use the keyNames by default.
		JButton reinit = new JButton("Default");
		reinit.setBounds(225, 200, 100, 30);
		reinit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){				
				try {
					setDefaultConfig();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				options.dispose();
				openConfig(frame);
			}
		});
		
		// Add the buttons on the options window.
		options.add(done);
		options.add(reinit);
		options.setVisible(true);
	}
	
	/**
	 * Set the keyboard keys by default.
	 * 
	 * @throws Exception
	 */
	private static void setDefaultConfig() throws Exception{
		Config.left = "Gauche";
		Config.right = "Droite";
		Config.rotate = "Haut";
		Config.down = "Bas";
		Config.pause = "P";
		saveConfig();
	}
	
	/**
	 * Save the changes made to the keyboard keys used to play.
	 */
	private static void saveChanges(){
		Choice left = choices.get(0);
		Choice right = choices.get(1);
		Choice rotate = choices.get(2);
		Choice down = choices.get(3);		
		Choice pause = choices.get(4);
		Config.left = left.getSelectedItem();
		Config.right = right.getSelectedItem();
		Config.rotate = rotate.getSelectedItem();
		Config.down = down.getSelectedItem();
		Config.pause = pause.getSelectedItem();
		try{
			saveConfig();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a choices list with its label on the options window.
	 * 
	 * @param name
	 * 		The label name.
	 * @param options
	 * 		The options window.
	 * @param x
	 * 		Position X of the choices list on the options window.
	 * @param y
	 * 		Position Y of the choices list on the options window.
	 * @return
	 */
	private static Choice addChoice(String name, JFrame options, int x, int y){
		// Set the label of the choices list
		JLabel label = new JLabel(name);
		label.setBounds(x, y - 20, 100, 20);
		
		// Set the choices list
		Choice key = new Choice();
		for(String s: getKeyNames()){
			key.add(s);
		}
		key.setBounds(x, y, 100, 20);
		
		// Add the label and the list on the options window
		options.add(key);
		options.add(label);
		choices.add(key);
		return key;
	}
	
	/**
	 * Get the array of key name.
	 * 
	 * @return
	 * 		The array.
	 */
	private static ArrayList<String> getKeyNames(){
		ArrayList<String> result = new ArrayList<String>();
		for(String s: KeyGetter.keyNames){
			result.add(s);
			if(s.equalsIgnoreCase("F24")){
				break;
			}
		}
		return result;
	}
	
	/**
	 * Load the config saved in the file.
	 * 
	 * @throws Exception
	 */
	public static void loadConfig() throws Exception{
		File directory = new File(getDefaultDirectory(), "/Tetris");
		if(!directory.exists()){
			directory.mkdirs();
		}
		File config = new File(directory, "/config.txt");
		if(!config.exists()){
			config.createNewFile();
			System.out.println("File not found, savings defaults");
			saveConfig();
			return;
		}
		Scanner s = new Scanner(config);
		HashMap<String, String> values = new HashMap<String, String>();
		while(s.hasNextLine()){
			String[] entry = s.nextLine().split(":");
			String key = entry[0];
			String value = entry[1];
			values.put(key, value);
		}
		s.close();
		if(values.size() != 5){
			System.out.println("Config is unnusable, saving defaults");
			saveConfig();
			return;
		}
		if(!values.containsKey("left") || !values.containsKey("right") || !values.containsKey("rotate") || !values.containsKey("down") || !values.containsKey("pause")){
			System.out.println("Invalid names in config, saving defaults");
			saveConfig();
			return;
		}
		String left = values.get("left");
		String right = values.get("right");
		String rotate = values.get("rotate");
		String down = values.get("down");
		String pause = values.get("pause");
		
		if(!(getKeyNames().contains(left) && getKeyNames().contains(right) && getKeyNames().contains(rotate) && getKeyNames().contains(down) && getKeyNames().contains(pause))){
			System.out.println("Invalid key in config, saving defaults");
			saveConfig();
			return;
		}
		Config.left = left;
		Config.right = right;
		Config.rotate = rotate;
		Config.down = down;
		Config.pause = pause;
	}
	
	/**
	 * Save the configuration in a file.
	 * 
	 * @throws Exception
	 */
	private static void saveConfig() throws Exception{
		File directory = new File(getDefaultDirectory(), "/Tetris");
		if(!directory.exists()){
			directory.mkdirs();
		}
		
		File config = new File(directory, "/config.txt");
		if(!config.exists()){
			config.createNewFile();
		}
		
		PrintWriter pw = new PrintWriter(config);
		pw.println("right:" + right);
		pw.println("left:" + left);
		pw.println("rotate:" + rotate);
		pw.println("down:" + down);
		pw.println("pause:" + pause);
		pw.close();		
	}
	
	/**
	 * Get the file where the highScore is saved.
	 * 
	 * @return
	 * 		The file.
	 * @throws IOException
	 */
	public static File getHighScoreFile() throws IOException {
		File directory = new File(getDefaultDirectory(), "/Tetris");
		if(!directory.exists()){
			directory.mkdirs();
		}
		
		File highscore = new File(directory, "/highscore.txt");
		if (!highscore.exists()){
			highscore.createNewFile();
			
			PrintWriter pw = new PrintWriter(highscore);
			pw.println("Highscore is:" + 0);
			pw.close();
		}
		
		return highscore;		
	}
	
	/**
	 * Get the directory where to save the files.
	 * 
	 * @return
	 * 		The root of the directory.
	 */
	private static String getDefaultDirectory(){
		String OS = System.getProperty("os.name").toUpperCase();
		if(OS.contains("WIN")){
			return System.getenv("APPDATA");
		}
		if(OS.contains("MAC")){
			return System.getProperty("user.home") + "Library/Application Support/";
		}
		return System.getProperty("user.home");
	}
}
