package uk.ac.yorksj.year2.sem1.assignment1;

import processing.core.PApplet;

public class Pong extends PApplet {
	// x position of the ball
	private int ballX;
	// y position of the ball
	private int ballY;
	// Ball speed in the x direction
	private int ballSpeedX;
	// Ball speed in the y direction
	private int ballSpeedY;
	// Width of our window
	final private int width = 640;
	// height of our window
	final private int height = 480;
	// radius of the ball
	final private int radius = 10;
	// Ball direction
	private int ballDirectionX = 1;
	private int ballDirectionY = 1;
	// set win score here
	final private int winScore = 10;

	private boolean gameFlag = true;

	// Player paddles
	private paddle player1 = new paddle(20, 0, 10, 80, 255, "player 1");
	private paddle player2 = new paddle((width - 20), 0, 10, 80, 255, "player 2");

	public static void main(String[] args) {
		// Set up the processing library
		PApplet.main("uk.ac.yorksj.year2.sem1.assignment1.Pong");
	}

	public void settings() {
		// Set our window size
		size(width, height);
	}

	public void setup() {
		ballX = width / 2;
		ballY = height / 2;
		ballSpeedX = 4;
		ballSpeedY = 4;
	}

	public void draw() {

		// Clear the background of the window
		background(255, 255, 255);
		drawPaddles();
		movePaddles(player1);
		movePaddles(player2);
		fill(255);
		// moving the ball and bouncing the ball of boarder
		isHittingPlayer(ballX, ballY);
		moveBall();
		drawBall();
		displayScore();
		gameOver();

	}

	public void drawBall() {
		ellipse(ballX, ballY, radius * 2, radius * 2);
		fill(0);
	}

	// Move ball and bounce of walls
	public void moveBall() {
		ballX += (ballSpeedX * ballDirectionX);
		ballY += (ballSpeedY * ballDirectionY);

		if (ballX > 620) {
			player1.addScore();
			setup();
		} else if (ballX < radius + 4) {
			player2.addScore();
			setup();

		}
		if (ballY > height - radius + 4 || ballY < radius + 4) {
			ballDirectionY *= -1;
			// System.out.println("test Y " + ballDirectionY);
		}
	}

	public boolean isHittingPlayer(int ballPosX, int ballPosY) {
		// Checks to see whether the ball and paddle are at the same X co-ordinate
		if (ballPosX - radius + 4 < (player1.getWidth() + player1.getPosX())) {
			// TODO fix bounce of edges
			// Checks to see if the ball is in-line with any of the paddle
			if (((player1.getPosY() + player1.getHeight()) > ballPosY) && (player1.getPosY() <= (ballPosY))) {
				ballDirectionX *= -1;
				return true;
			}
		} else if (ballPosX - radius + 4 > 610) {
			// TODO fix bounce of edges
			if (((player2.getPosY() + player2.getHeight()) > ballPosY) && (player2.getPosY() <= (ballPosY))) {
				ballDirectionX *= -1;
				return true;
			}
		}
		return false;
	}

	public void drawPaddles() {
		rect(player1.getPosX(), player1.getPosY(), player1.getWidth(), player1.getHeight());
		rect(player2.getPosX(), player2.getPosY(), player2.getWidth(), player2.getHeight());
	}

	// Display scores on screen
	public void displayScore() {
		textSize(50);
		text(player1.getScore(), 100, 40);
		textSize(50);
		text(player2.getScore(), width - 100, 40);
	}

	// Checks to see if the player has won the game
	public void gameOver() {
		if (player1.getScore() >= 10) {
			endGame(player1);

		} else if (player2.getScore() >= 10) {
			endGame(player2);
		}
	}

	// Stops games movement, when either player wins
	public void endGame(paddle name) {
		textSize(26);
		text(name.getName() + " wins!", (width / 2) - 100, 40);
		ballSpeedX = 0;
		ballSpeedY = 0;
		gameFlag = false;

	}

	// Creates keyboard input for players to use
	public void keyPressed() {
		setMovePaddles(keyCode, key, true);
	}

	// false key interaction keyboard input
	public void keyReleased() {
		setMovePaddles(keyCode, key, false);
	}

	// Update position of paddles and restricts paddles going off the screen
	public void movePaddles(paddle name) {

		if (name.getIsUp() && name.getPosY() > 5)
			name.subPosY();
		if (name.getIsDown() && name.getPosY() < (height - name.getHeight()))
			name.addPosY();

	}

	// Allows the paddle(s) to move simultaneously
	public void setMovePaddles(int keyCode, char key, boolean flag) {
		if (gameFlag) {

			if (key == 'w' || key == 'W') {
				player1.setIsUp(flag);
			}
			if (key == 's' | key == 'S') {
				player1.setIsDown(flag);
			}
			if (keyCode == UP) {
				player2.setIsUp(flag);
				;
			}
			if (keyCode == DOWN) {
				player2.setIsDown(flag);
				;
			}

		}

	}

}
