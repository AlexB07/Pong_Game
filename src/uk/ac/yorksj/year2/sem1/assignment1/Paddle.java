package uk.ac.yorksj.year2.sem1.assignment1;

public class Paddle {

    private int width;
    private int height;
    private int posX;
    private int posY;
    private String name;
    private int score;
    private boolean isUp, isDown;

    public Paddle() {

    }

    public Paddle(int x, int y, int w, int h, String s) {
        this.posX = x;
        this.posY = y;
        this.width = w;
        this.height = h;
        this.score = 0;
        this.name = s;
    }

    public int getScore() {
        return this.score;
    }

    public int addScore() {
        this.score += 1;
        return this.score;

    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void subPosY(int num) {
        this.posY -= num;
    }

    public void addPosY(int num) {
        this.posY += num;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
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

    public String getName() {
        return this.name;
    }

}
