package uk.ac.yorksj.year2.sem1.assignment1;

import processing.core.PApplet;
import java.util.Random;

public class Pong extends PApplet
{
    // x position of the ball
    private int ballX;
    // y position of the ball
    private int ballY;
    // Ball speed in the x directio
    private int ballSpeedX;
    // Ball speed in the y direction
    private int ballSpeedY;
    // Width of our window
    final private int width = 640;
    // height of our window
    final private int height = 480;
    
    public static void main(String[] args) {
        // Set up the processing library
        PApplet.main("Pong");
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
        ballSpeedX = r.nextInt(width / 100) + 1;
        ballSpeedY = r.nextInt(height / 100) + 1;
    }

    public void draw() {
        // Clear the background of the window
        background(255, 255, 255);
        // Draw the ball
        ellipse(ballX, ballY, 32, 32);
        // Move the ball
        ballX += ballSpeedX;
        ballY += ballSpeedY;
    }
}
