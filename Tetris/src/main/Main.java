package main;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame window = new JFrame("Simple Tetris");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		// Add GamePanel to the window
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();
		
		gamePanel.launchGame();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
