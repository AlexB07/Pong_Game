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
	private int ballDirectionX = 1;
	private int ballDirectionY = 1;

	private paddle player1 = new paddle(20, 0, 10, 50);
	private paddle player2 = new paddle((width - 20), 0, 10, 50);

	public static void main(String[] args) {
		// Set up the processing library
		PApplet.main("uk.ac.yorksj.year2.sem1.assignment1.Pong");
	}

	public void settings() {
		// Set our window size
		size(width, height);
	}

	public void setup() {
		// Create a random initial position and speed for the ball
		Random r = new Random();
		ballX = r.nextInt(width);
		ballY = r.nextInt(height);
		ballSpeedX = 4;
		ballSpeedY = 0;
	}

	public void draw() {

		// Clear the background of the window
		background(255, 255, 255);
		// Draw the player rectangle
		// rect(20, mouseY, 10, 40);
		drawPaddles();
		fill(255);
		// moving the ball and bouncing the ball of boarder
		isHittingPlayer(ballX, ballY);
		ballX += (ballSpeedX * ballDirectionX);
		ballY += (ballSpeedY * ballDirectionY);

		if (ballX > width - radius + 4 || ballX < radius + 4) {
			System.out.println("MOUSE X: " + mouseX + " Y: " + mouseY + "");
			System.out.println("BALL X:  " + ballX + " Y: " + ballY + "");
			ballDirectionX *= -1;
		}
		if (ballY > height - radius + 4 || ballY < radius + 4) {
			ballDirectionY *= -1;
			// System.out.println("test Y " + ballDirectionY);
		}

		// Draw the ball

		ellipse(ballX, ballY, radius * 2, radius * 2);
		fill(0);
	}

	public boolean isHittingPlayer(int ballPosX, int ballPosY) {
		if (ballPosX - radius + 4 < 30) {
			// TODO fix bounce of edges
			if (((player1.getPoxY() + 40) > ballPosY) && (player1.getPoxY() < (ballPosY))) {
				System.out.println("test");
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

	public void keyPressed() {
		if (key == 'w' || key == 'W') {
			player1.subPosY();
		}
		if (key == 's' | key == 'S') {
			player1.addPosY();
		}
		if (keyCode == UP) {
			player2.subPosY();
		}
		if (keyCode == DOWN) {
			player2.addPosY();
		}
	}
}
