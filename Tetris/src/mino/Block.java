package mino;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block extends Rectangle{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x, y;
	public static final int SIZE = 30; // 30x30 block
	public Color color;
	
	public Block(Color color) {
		this.color = color;
	}
	  
	public void draw(Graphics2D graphics2D) {
		int margine = 2;
		graphics2D.setColor(color);
		graphics2D.fillRect(x + margine, y + margine, SIZE - (margine * 2), SIZE - (margine * 2));
	}

}
