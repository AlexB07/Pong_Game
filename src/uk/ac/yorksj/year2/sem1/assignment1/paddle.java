package uk.ac.yorksj.year2.sem1.assignment1;

public class paddle{
	
	private int width;
	private int height;
	private int posX;
	private int posY;
	
	
	public paddle() {
		
	}
	
	public paddle(int x, int y, int w, int h) {
		this.posX = x;
		this.posY = y;
		this.width = w;
		this.height = h;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int subPosY() {
		this.posY-=5;
		return (this.posY);
	}
	
	public int addPosY() {
		this.posY+= 5;
		return (this.posY);
	}
	
	public int getPosX() {
		return this.posX;
	}
	
	public int getPoxY() {
		return this.posY;
	}
	

}
