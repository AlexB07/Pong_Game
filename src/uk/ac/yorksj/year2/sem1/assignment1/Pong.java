package uk.ac.yorksj.year2.sem1.assignment1;

import processing.core.PApplet;
import java.util.Random;

import javax.swing.event.SwingPropertyChangeSupport;

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
	final private int radius = 10;
	// Ball direction
	private int ballDirectionX = 1;
	private int ballDirectionY = 1;

	// Player paddles
	private paddle player1 = new paddle(20, 0, 10, 80, 255);
	private paddle player2 = new paddle((width - 20), 0, 10, 80, 255);

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
		ballSpeedX = -4;
		ballSpeedY = 0;
	}

	public void draw() {

		// Clear the background of the window
		background(255, 255, 255);
		// Draw the player rectangle
		// rect(20, mouseY, 10, 40);
		drawPaddles();
		movePaddles(player1);
		movePaddles(player2);
		fill(255);
		// moving the ball and bouncing the ball of boarder
		isHittingPlayer(ballX, ballY);
		moveBall();
		drawBall();
		displayScore();

	}

	public void drawBall() {
		ellipse(ballX, ballY, radius * 2, radius * 2);
		fill(0);
	}

	// Move ball and bounce of walls
	public void moveBall() {
		ballX += (ballSpeedX * ballDirectionX);
		ballY += (ballSpeedY * ballDirectionY);

		if (ballX > width - radius + 4) {
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
			if (((player1.getPoxY() + player1.getHeight()) > ballPosY) && (player1.getPoxY() <= (ballPosY))) {
				ballDirectionX *= -1;
				return true;
			}
		} else if (ballPosX - radius + 4 > 610) {
			// TODO fix bounce of edges
			if (((player2.getPoxY() + player2.getHeight()) > ballPosY) && (player2.getPoxY() <= (ballPosY))) {
				ballDirectionX *= -1;
				return true;
			}
		}
		return false;
	}

	public void drawPaddles() {
		rect(player1.getPosX(), player1.getPoxY(), player1.getWidth(), player1.getHeight());
		rect(player2.getPosX(), player2.getPoxY(), player2.getWidth(), player2.getHeight());
	}

	// Display scores on screen
	public void displayScore() {
		textSize(50);
		text(player1.getScore(), 100, 40);
		textSize(50);
		text(player2.getScore(), width - 100, 40);
	}

	// Creates keyboard input for players to use
	public void keyPressed() {
		setMovePaddles(keyCode, key, true);
	}

	// false key interaction keyboard input
	public void keyReleased() {
		setMovePaddles(keyCode, key, false);
	}

	// Update position of paddles
	public void movePaddles(paddle name) {
		if (name.getIsUp())
			name.subPosY();
		if (name.getIsDown())
			name.addPosY();
	}

	// Allows the paddle(s) to move simultaneously
	public void setMovePaddles(int keyCode, char key, boolean flag) {
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
