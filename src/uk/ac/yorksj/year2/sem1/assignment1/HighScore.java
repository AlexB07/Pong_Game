package uk.ac.yorksj.year2.sem1.assignment1;

public class HighScore {

    private String name;
    private int score;

    public HighScore(String n, int s) {
        this.name = n;
        this.score = s;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

}
