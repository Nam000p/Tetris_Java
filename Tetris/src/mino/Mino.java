package mino;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.KeyHandler;
import main.PlayManager;

public class Mino {
	public Block block[] = new Block[4];
	public Block tempB[] = new Block[4];
	int autoDropCounter = 0;
	public int direction = 1; // There are 4 direction (1/2/3/4)
	boolean leftCollision, rightCollision, bottomCollision;
	public boolean active = true;
	public boolean deactivating;
	int deactivateCounter = 0;

	public void create(Color color) {
		block[0] = new Block(color);
		block[1] = new Block(color);
		block[2] = new Block(color);
		block[3] = new Block(color);
		tempB[0] = new Block(color);
		tempB[1] = new Block(color);
		tempB[2] = new Block(color);
		tempB[3] = new Block(color);
	}

	public void setXY(int x, int y) {
	}

	public void updateXY(int direction) {
		checkRotationCollision();
		
		if (leftCollision == false && rightCollision == false && bottomCollision == false) {
			this.direction = direction;
			block[0].x = tempB[0].x;
			block[0].y = tempB[0].y;
			block[1].x = tempB[1].x;
			block[1].y = tempB[1].y;
			block[2].x = tempB[2].x;
			block[2].y = tempB[2].y;
			block[3].x = tempB[3].x;
			block[3].y = tempB[3].y;
		}
	}

	public void getDirection1() {
	}

	public void getDirection2() {
	}

	public void getDirection3() {
	}

	public void getDirection4() {
	}

	public void checkMovementCollision() {	
		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;
		
		// Check static block collision
		checkStaticBlockCollision();
		
		// Check frame collision
		// Right wall
		for (int i = 0; i < block.length; i++) {
			if (block[i].x == PlayManager.left_x) {
				leftCollision = true;
			}
		}
		// Right wall
		for (int i = 0; i < block.length; i++) {
			if (block[i].x + Block.SIZE == PlayManager.right_x) {
				rightCollision = true;
			}
		}
		// Bottom floor
		for (int i = 0; i < block.length; i++) {
			if (block[i].y + Block.SIZE == PlayManager.bottom_y) {
				bottomCollision = true;
			}
		}
	}
	
	public void checkRotationCollision() {
		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;
		
		// Check frame collision
		// Right wall
		for (int i = 0; i < block.length; i++) {
			if (tempB[i].x < PlayManager.left_x) {
				leftCollision = true;
			}
		}
		// Right wall
		for (int i = 0; i < block.length; i++) {
			if (tempB[i].x + Block.SIZE > PlayManager.right_x) {
				rightCollision = true;
			}
		}
		// Bottom floor
		for (int i = 0; i < block.length; i++) {
			if (tempB[i].y + Block.SIZE > PlayManager.bottom_y) {
				bottomCollision = true;
			}
		}
	}
	
	private void checkStaticBlockCollision() {
		for (int i = 0; i < PlayManager.staticBlocks.size(); i++) {
			int targetX = PlayManager.staticBlocks.get(i).x;
			int targetY = PlayManager.staticBlocks.get(i).y;
			
			// Check down
			for (int ii = 0; ii < block.length; ii++) {
				if (block[ii].y + Block.SIZE == targetY && block[ii].x == targetX) {
					bottomCollision = true;
				}
			}
			// Check left
			for (int ii = 0; ii < block.length; ii++) {
				if (block[ii].x - Block.SIZE == targetX && block[ii].y == targetY) {
					leftCollision = true;
				}
			}
			// Check right
			for (int ii = 0; ii < block.length; ii++) {
				if (block[ii].x + Block.SIZE == targetX && block[ii].y == targetY) {
					rightCollision = true;
				}
			}
		}
	}
	
	public void update() {
		if (deactivating) {
			deactivating();
		}
		// Move the mino
		if (KeyHandler.upPressed) {
			switch (direction) {
			case 1: getDirection2(); break;
			case 2: getDirection3(); break;
			case 3: getDirection4(); break;
			case 4: getDirection1(); break;
			}
			KeyHandler.upPressed = false;
			GamePanel.se.play(3, false);
		}
		
		checkMovementCollision();
		
		if (KeyHandler.downPressed) {
			// if the mino's bottom is not hitting, it can go down
			if (bottomCollision == false) {

				block[0].y += Block.SIZE;
				block[1].y += Block.SIZE;
				block[2].y += Block.SIZE;
				block[3].y += Block.SIZE;

				// When moved down, reset the autoDropCounter
				autoDropCounter = 0;
			}
			KeyHandler.downPressed = false;
		}
		
		if (KeyHandler.leftPressed) {
			if (leftCollision == false) {
				block[0].x -= Block.SIZE;
				block[1].x -= Block.SIZE;
				block[2].x -= Block.SIZE;
				block[3].x -= Block.SIZE;
			}
				KeyHandler.leftPressed = false;
		}
		
		if (KeyHandler.rightPressed) {
			if (rightCollision == false) {
				block[0].x += Block.SIZE;
				block[1].x += Block.SIZE;
				block[2].x += Block.SIZE;
				block[3].x += Block.SIZE;
			}
				KeyHandler.rightPressed = false;
		}
		
		if (bottomCollision) {
			if (deactivating == false) {
				GamePanel.se.play(4, false);
			}
			deactivating = true;
		} else {
			autoDropCounter++; // the counter increase in every frame
			if (autoDropCounter == PlayManager.dropInterval) {
				// the mino goes down
				block[0].y += Block.SIZE;
				block[1].y += Block.SIZE;
				block[2].y += Block.SIZE;
				block[3].y += Block.SIZE;
				autoDropCounter = 0;
			}
		}
	}

	private void deactivating() {
		deactivateCounter++;
		
		// Wait 45 frame until deactivate
		if (deactivateCounter == 45) {
			deactivateCounter = 0;
			checkMovementCollision(); // Check if the bottom is still hitting
			
			// if the bottom is still hitting after 45 frames, deactivate the mino
			if (bottomCollision) {
				active = false;
			}
		}
	}

	public void draw(Graphics2D graphics2D) {
		int margin = 2;
		graphics2D.setColor(block[0].color);
		graphics2D.fillRect(block[0].x, block[0].y, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
		graphics2D.fillRect(block[1].x, block[1].y, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
		graphics2D.fillRect(block[2].x, block[2].y, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
		graphics2D.fillRect(block[3].x, block[3].y, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
	}
}
