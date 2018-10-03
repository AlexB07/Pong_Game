package uk.ac.yorksj.year2.sem1.assignment1;

public class highScore {

	private String name;
	private int score;

	public highScore(String name_, int score_) {
		this.name = name_;
		this.score = score_;
	}

	public String getName() {
		return this.name;
	}

	public int getScore() {
		return this.score;
	}

}
