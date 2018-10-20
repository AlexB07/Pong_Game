package uk.ac.yorksj.year2.sem1.assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import processing.core.PApplet;
import processing.core.PImage;
import processing.sound.*;

public class Pong extends PApplet {
	// x position of the ball
    private int ballX;
	// y position of the ball
    private int ballY;
	// Ball speed in the x direction
    private int ballSpeedX = 4;
	// Ball speed in the y direction
    private int ballSpeedY = 4;
	// Ball direction
    private int ballDirectionX = 1;
    private int ballDirectionY = 1;
	// Width of our window
    final private int width = 640;
	// height of our window
    final private int height = 480;
	// radius of the ball
    final private int radius = 10;
	// flag for the game over
    private boolean gameFlag = true;
	// flag used for checking highScores
    private int highScore = -1;
	// keeps track of how many time the players have hit the ball between them
    private int rally = 0;
	// Stores background image
    private PImage bg;
	// file path and name
    final private File file = new File("highScores.txt");
	// Stores the file high score when program starts
    private ArrayList<HighScore> scores = new ArrayList<HighScore>();
	// Stores the highest score out of the game
    private int highestRally;

    private int paddleMovement = 7;

    private String userInput = "";

	// Player paddles
    private Paddle player1 = new Paddle(20, 0, 10, 80, "player 1");
    private Paddle player2 = new Paddle((width - 20), 0, 10, 80, "player 2");

	// Sounds variables initiate later
    private SoundFile batHit;
    private SoundFile edgeHit;
    private SoundFile miss;
	// set win score here
    final private int winScore = 10;

    public static void main(String[] args) {
		// Set up the processing library
        PApplet.main("uk.ac.yorksj.year2.sem1.assignment1.Pong");
    }

    public void settings() {
		// Set our window size
        size(width, height);
		// Initialising sound files
        batHit = new SoundFile(this, "hit.wav");
        edgeHit = new SoundFile(this, "bounce.wav");
        miss = new SoundFile(this, "miss.wav");

    }

    public void setup() {
		//smoother animation
        frameRate(60);
        smooth();
		//resets values
        reset();
        highestRally = 0;
        bg = loadImage("bg.png");
        ballSpeedX = -4;
        ballSpeedY = 4;
		// prevents error if file doesn't exist
        if (file.exists()) {
            readFile();
        } else {
			// If file doesn't exist, initiate array to write file and create one
            for (int i = 0; i < 5; i++) {
                scores.add(new HighScore("init", 0));
            }
        }

    }

	// reset variables when player loses
    public void reset() {
        ballX = width / 2;
        ballY = height / 2;
        rally = 0;
        paddleMovement = 7;
    }

    public void readFile() {

        try {
            Scanner fileSc = new Scanner(file);

            while (fileSc.hasNextLine()) {
                String temp = fileSc.nextLine();
                String[] parts = temp.split(" ");
                scores.add(new HighScore(parts[0], Integer.parseInt(parts[1])));
            }
            fileSc.close();

        } catch (FileNotFoundException e) {
            System.err.println("[ERROR] File does not found");
        }

    }

	// write high score file
    public void writeFile() {
        textSize(26);
        try {
            PrintWriter pw = new PrintWriter(file);
            for (int i = 0; i < 5; i++) {
                pw.println(scores.get(i).getName() + " " + scores.get(i).getScore());
            }
            pw.close();

        } catch (FileNotFoundException e) {
            System.err.println("[ERROR] File not found");
        }
        displayHighScores();
    }

	// Write high scores, and change background
    public void displayHighScores() {

        if (scores.size() > 0) {
            PImage bgFinish = loadImage("bgFinish.png");
            background(bgFinish);

            for (int i = 0; i < 5; i++) {
                fill(255);
                text("TOP 5 Rallies", ((width / 2) - 70), (215 - 25));
                text((i + 1) + ". " + scores.get(i).getName() + " " + scores.get(i).getScore(), ((width / 2) - 70),215 + (30 * i));
                fill(0);
            }
        }

    }

    public void draw() {
		// Clear the background of the window
        if (gameFlag) {			
            gameOver();
            background(bg);
            drawPaddles();
            movePaddles(player1);
            movePaddles(player2);
            moveBall();
            drawBall();
            hittingPaddles();
            displayScore();
            displayRally();
        } else if (!gameFlag && highScore == -1) {
            findHighscorePosition();
        } else if (!gameFlag && highScore >= 0) {
            updateHighScore();
        }
        if (!gameFlag && highScore == -1) {
            writeFile();
        }
    }

	// display ball on screen
    public void drawBall() {
        fill(255);
        ellipseMode(CENTER);
        ellipse(ballX, ballY, radius * 2, radius * 2);
        fill(0);
    }

	// Move ball and bounce of walls
    public void moveBall() {
        ballX += (ballSpeedX * ballDirectionX);
        ballY += (ballSpeedY * ballDirectionY);

        if (ballX > width - radius) {
            player1.addScore();
            miss.play();
            reset();
        } else if (ballX < radius) {
            player2.addScore();
            miss.play();
            reset();
        }
        if (ballY > height - radius || ballY < radius) {
            ballDirectionY *= -1;
            edgeHit.play();
        }
    }

	// checks to see if its hitting the paddle, and reacts
    public void hittingPaddles() {
		// Checks to see whether the ball and paddle are at the same X co-ordinate
        if (ballX - radius < (player1.getWidth() + player1.getPosX())
		        && ballX - radius > player1.getPosX() && player1.getPosY() + player1.getHeight() > ballY - radius && player1.getPosY() < ballY) {
            updatePaddleObjects();
        }
        else if (ballX > player2.getPosX() - player2.getWidth() && ballX - radius < player2.getPosX() && player2.getPosY() + player2.getHeight() > ballY - radius && player2.getPosY() < ballY) {
            updatePaddleObjects();
        }
		
    }
	
	// events and objects updated when ball hits paddle
    public void updatePaddleObjects() {
        ballDirectionX *= -1;
        rally += 1;
        batHit.play();
		// Makes game harder after time, slows bat speed down
        if (rally > 0 && paddleMovement > 2) {
            if (rally % 10 == 0) {
                paddleMovement--;
            }

            if (rally > highestRally) {
                highestRally = rally;
            }
        }
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
    public boolean gameOver() {
    	if (player1.getScore() >= winScore) {
            endGame(player1);
            return true;
        } else if (player2.getScore() >= winScore) {
            endGame(player2);
            return true;
        }

        return false;

    }

	// Stops games movement, when either player wins
    public void endGame(Paddle name) {
        fill(255);
        textSize(26);
        text(name.getName() + " wins!", (width / 2) - 100, 40);
        fill(0);
        ballSpeedX = 0;
        ballSpeedY = 0;
        gameFlag = false;
    }

	// Creates keyboard input for players to use
    public void keyPressed() {
        if (gameFlag) {
            setMovePaddles(keyCode, key, true);
        } else if (highScore >= 0) {
			// highScore name input
            if (keyCode == ENTER || key == '\n') {
            	//this is so it doesn't add an extra line into the string below
            } else {
                userInput += key;
            }
        }
    }

	// false key interaction keyboard input
    public void keyReleased() {
        if (gameFlag) {
            setMovePaddles(keyCode, key, false);
        }

    }

    public void findHighscorePosition() {
        for (int i = 0; i < scores.size(); i++) {
            if (highestRally > scores.get(i).getScore()) {
                highScore = i;
                break;
            }

        }
    }

	// adds newest highScore to table
    public void updateHighScore() {
        background(0);
        fill(255);
        textSize(12);
        text("You have acheived a high score in the top 5 rallies \n Please enter you team name down below: \n (no bigger than 7 characters)",
				width / 2, height / 2);
        text("\n \n \nName:" + userInput, (width / 2), (height / 2) + 30);
        fill(0);
        textSize(20);
        if (key == '\n' || keyCode == ENTER) {
            if (userInput.length() > 7) {
                userInput = userInput.substring(0, 7);
            }
            scores.add(highScore, new HighScore(userInput, highestRally));
            highScore = -10;
            userInput = "";

            writeFile();
        }

    }

	// Update position of paddles and restricts paddles going off the screen
    public void movePaddles(Paddle name) {

        if (name.getIsUp() && name.getPosY() > 0) {
            name.subPosY(paddleMovement);
        }
        if (name.getIsDown() && name.getPosY() < (height - name.getHeight())) {
            name.addPosY(paddleMovement);
        }
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
            }
            if (keyCode == DOWN) {
                player2.setIsDown(flag);
            }

        }

    }

	// display rally when rally >= 10
    public void displayRally() {
        if (rally >= 10) {
            fill(169, 169, 169); // changes colour of text
            textSize(20);
            text("Rally:     " + rally, (width / 2) - 70, height - 50);
            fill(0); // reset colour
        }

    }

}
