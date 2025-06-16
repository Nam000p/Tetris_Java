package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	final int FPS = 60;
	Thread gameThread;
	PlayManager playManager;
	public static Sound music = new Sound();
	public static Sound se = new Sound();

	public GamePanel() {
		// Panel setting
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		// Implement KeyListener
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true);
		
		playManager = new PlayManager();
	}

	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
		
		music.play(0, true);
		music.loop();
	}

	@Override
	public void run() {
		// Game loop
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {
			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}

	private void update() {
		if (KeyHandler.pausePressed == false && playManager.gameOver == false) {
			playManager.update();
		}
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Graphics2D graphics2D = (Graphics2D) graphics;
		playManager.draw(graphics2D);
	}

}
