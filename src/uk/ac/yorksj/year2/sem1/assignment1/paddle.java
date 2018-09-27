package uk.ac.yorksj.year2.sem1.assignment1;

public class paddle{
	
	private int width;
	private int height;
	private int posX;
	private int posY;
	private int colour;

	private int score;
	private boolean isUp, isDown;

	
	
	public paddle() {
		
	}
	
	public paddle(int x, int y, int w, int h, int col) {
		this.posX = x;
		this.posY = y;
		this.width = w;
		this.height = h;
		this.colour = col;
		this.score = 0;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int addScore() {
		this.score+=1;
		return this.score;

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
	
	public int getColour() {
		return this.getColour();
	}
	
	public boolean getIsUp() {
		return this.isUp;
	}
	
	public void setIsUp(boolean flag) {
		this.isUp = flag;
	}
	
	public boolean getIsDown() {
		return this.isDown;
	}
	
	public void setIsDown(boolean flag) {
		this.isDown = flag;
	}
	


}
