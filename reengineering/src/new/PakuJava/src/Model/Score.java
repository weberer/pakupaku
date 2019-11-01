/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.List;
import java.util.ArrayList;

/**
 * This class defines the score object for the game. It holds the current score as well as a list of
 * previos scores. It is responsible for keeping track of the high score
 * @author kruge
 */
public class Score 
{
    private int currentScore;
    private int highScore;
    private List<Integer> scoreList;
    public Score() {
        scoreList = new ArrayList<>();
        currentScore = 0;
    }

    public void addScore(int score)
    {
        this.currentScore += score;
    }
    public int getCurrentScore() {
        return currentScore;
    }

    /**
     * Resets currentscore to 0 and puts the previous score into the score list
     */
    public void reset()
    {
        if(currentScore > 0)
            archiveScore();
        currentScore = 0;
    }

    /**
     * Adds currentScore to the list of scores (used to keep track of high scores)
     */
    private void archiveScore()
    {
        scoreList.add(currentScore);
    }

    /**
     * Finds the highest currentScore in the list and updates highSore
     */
    private void determineHighScore()
    {
        for(int i = 0; i < scoreList.size(); i++)
        {
            if(scoreList.get(i) > highScore)
                highScore = scoreList.get(i);
        }
    }

    public int getHighScore()
    {
        determineHighScore();
        return highScore;
    }

    public List<Integer> getScoreList() {
        return scoreList;
    }
}
