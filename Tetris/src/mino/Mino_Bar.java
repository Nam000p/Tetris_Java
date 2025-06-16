package mino;

import java.awt.Color;

public class Mino_Bar extends Mino{
	public Mino_Bar() {
		create(Color.CYAN);
	}
	
	@Override
	public void setXY(int x, int y) {
		// o o o o
		block[0].x = x;
		block[0].y = y;
		block[1].x = block[0].x - Block.SIZE;
		block[1].y = block[0].y;
		block[2].x = block[0].x + Block.SIZE;
		block[2].y = block[0].y;
		block[3].x = block[0].x + Block.SIZE * 2;
		block[3].y = block[0].y;
	}

	@Override
	public void getDirection1() {
		// o o o o
		tempB[0].x = block[0].x;
		tempB[0].y = block[0].y;
		tempB[1].x = block[0].x - Block.SIZE;
		tempB[1].y = block[0].y;
		tempB[2].x = block[0].x + Block.SIZE;
		tempB[2].y = block[0].y;
		tempB[3].x = block[0].x + Block.SIZE * 2;
		tempB[3].y = block[0].y;	
		
		updateXY(1);
	}

	@Override
	public void getDirection2() {
		// o 
		// o 
		// o
		// o
		tempB[0].x = block[0].x;
		tempB[0].y = block[0].y;
		tempB[1].x = block[0].x;
		tempB[1].y = block[0].y - Block.SIZE;
		tempB[2].x = block[0].x;
		tempB[2].y = block[0].y + Block.SIZE;
		tempB[3].x = block[0].x;
		tempB[3].y = block[0].y + Block.SIZE * 2;	
		
		updateXY(2);		
	}

	@Override
	public void getDirection3() {
		getDirection1();				
	}

	@Override
	public void getDirection4() {
		getDirection4();
	}
}
