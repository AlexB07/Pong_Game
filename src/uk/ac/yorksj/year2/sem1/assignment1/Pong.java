package uk.ac.yorksj.year2.sem1.assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import processing.core.PApplet;
import processing.core.PImage;

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
	final private int winScore = 2;

	// flag for the game over
	private boolean gameFlag = true;
	// keeps track of how many time the players have hit the ball between them
	private int rally = 0;

	// Stores background image
	private PImage bg;

	// file path and name
	private File file = new File("highScores.txt");
	// Stores the file high score when program starts
	private ArrayList<highScore> scores = new ArrayList<highScore>();
	// Stores the highest score out of the game
	private int highestRally;

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
		frameRate(60);
		smooth();
		reset();
		highestRally = 0;
		bg = loadImage("bg.png");

		if (file.exists()) {
			readFile();
		}

	}

	public void reset() {
		ballX = width / 2;
		ballY = height / 2;
		ballSpeedX = 4;
		ballSpeedY = 0;

		rally = 0;
	}

	public void readFile() {

		try {
			Scanner fileSc = new Scanner(file);

			while (fileSc.hasNextLine()) {
				String temp = fileSc.nextLine();
				String[] parts = temp.split(" ");
				scores.add(new highScore(parts[0], Integer.parseInt(parts[1])));
			}
			fileSc.close();

		} catch (FileNotFoundException e) {
			System.err.println("[ERROR] File does not found");
		}

	}

	public void writeFile() {
		try {
			PrintWriter pw = new PrintWriter(file);
			for (highScore a : scores) {
				pw.println(a.getName() + " " + a.getScore());
			}
			pw.close();

		} catch (FileNotFoundException e) {
			System.err.println("[ERROR] File not found");
		}
	}

	public void displayHighScores() {

		if (scores.size() > 0) {
			PImage bgFinish = loadImage("bgFinish.png");
			background(bgFinish);
			for (int i = 0; i < scores.size(); i++) {
				fill(255);
				text("TOP 5 Rallies", ((width / 2) - 70), (215 - 25));
				text((i + 1) + ". " + scores.get(i).getName() + " " + scores.get(i).getScore(), ((width / 2) - 70),
						215 + (30 * i));
				fill(0);
			}
		}

	}

	public void draw() {
		// Clear the background of the window
		if (gameFlag == true) {
			background(bg);
			drawPaddles();
			movePaddles(player1);
			movePaddles(player2);
			// moving the ball and bouncing the ball of boarder
			isHittingPaddles(ballX, ballY);
			moveBall();
			drawBall();
			displayScore();
			displayRally();
			gameOver();
		}

	}

	// display ball on screen
	public void drawBall() {
		fill(255);
		ellipse(ballX, ballY, radius * 2, radius * 2);
		fill(0);
	}

	// Move ball and bounce of walls
	public void moveBall() {
		ballX += (ballSpeedX * ballDirectionX);
		ballY += (ballSpeedY * ballDirectionY);

		if (ballX > width) {
			player1.addScore();
			reset();
		} else if (ballX < radius) {
			player2.addScore();
			reset();

		}
		if (ballY > height - radius + 5 || ballY < radius + 5) {
			ballDirectionY *= -1;
			// System.out.println("test Y " + ballDirectionY);
		}
	}

	// checks to see if its hitting the paddle, and reacts
	public boolean isHittingPaddles(int ballPosX, int ballPosY) {
		// Checks to see whether the ball and paddle are at the same X co-ordinate
		if (ballPosX - radius + 5 < (player1.getWidth() + player1.getPosX())
				&& ballPosX - radius + 5 > (player1.getPosX() + 5)) {
			// Checks to see if the ball is in-line with any of the paddle
			if (((player1.getPosY() + player1.getHeight()) > ballPosY) && (player1.getPosY() <= (ballPosY))) {
				ballDirectionX *= -1;
				rally += 1;
				return true;
			}
		} else if (ballPosX - radius + 5 >= 610 && ballPosX - radius + 5 < 615) {
			if (((player2.getPosY() + player2.getHeight()) > ballPosY) && (player2.getPosY() <= (ballPosY))) {
				ballDirectionX *= -1;
				rally += 1;
				return true;
			}
		}
		return false;
	}

	// draws the paddles on the screen
	public void drawPaddles() {
		fill(255);
		rect(player1.getPosX(), player1.getPosY(), player1.getWidth(), player1.getHeight());
		rect(player2.getPosX(), player2.getPosY(), player2.getWidth(), player2.getHeight());
		fill(0);
	}

	// Display scores on screen
	public void displayScore() {
		fill(169, 169, 169);
		textSize(50);
		text(player1.getScore(), 100, 40);
		textSize(50);
		text(player2.getScore(), width - 100, 40);
		fill(0);
	}

	// Checks to see if the player has won the game
	public void gameOver() {
		if (player1.getScore() >= winScore) {
			endGame(player1);

		} else if (player2.getScore() >= winScore) {
			endGame(player2);
		}

	}

	// Stops games movement, when either player wins
	public void endGame(paddle name) {
		fill(255);
		textSize(26);
		text(name.getName() + " wins!", (width / 2) - 100, 40);
		fill(0);
		ballSpeedX = 0;
		ballSpeedY = 0;
		gameFlag = false;
		// Update high scores
		displayHighScores();
		for (int i = 0; i < scores.size(); i++) {
			if (highestRally > scores.get(i).getScore()) {
				scores.set(i, new highScore("test1", highestRally));
				break;
			}
		}
		writeFile();
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

		if (name.getIsUp() && name.getPosY() > 0)
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
			if (key == 's' || key == 'S') {
				player1.setIsDown(flag);
			}
			if (keyCode == UP) {
				player2.setIsUp(flag);
				;
			}
			if (keyCode == DOWN) {
				player2.setIsDown(flag);
			}

		}

	}

	public void displayRally() {
		if (rally >= 10) {
			fill(169, 169, 169); // changes colour of text
			textSize(20);
			text("Rally:     " + rally, (width / 2) - 70, height - 50);
			fill(0); // reset colour

		}
		if (rally > highestRally) {
			highestRally = rally;
		}

	}
}
