/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.*;

/**
 * This class defines the score object for the game. It holds the current score as well as a list of
 * previos scores. It is responsible for keeping track of the high score
 * @author kruge
 */
public class Score 
{
    private int currentScore;
    //private int highScore;
    private Map.Entry<String, Integer> highScore;

    private String initials;
    private HashMap<String, Integer> scores;
    public Score() {
        //scoreList = new ArrayList<>();
        scores = new LinkedHashMap<>();
        currentScore = 0;


        //Adding initial scores to the map
        scores.put("PAL", 80000);
        scores.put("ADI", 40000);
        scores.put("N_S", 20000);
        scores.put("JMK", 14680);
        scores.put("YST", 10000);
    }

    /**
     * updates the current score
     * @param score
     */
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
        //scores.put(initials, currentScore);
        LinkedHashMap<String, Integer> temp = new LinkedHashMap<>();
        Set<String> keySet = scores.keySet();
        Iterator iterator = keySet.iterator();
        boolean placed = false;
        while (iterator.hasNext())
        {
            String key = (String) iterator.next();
            if(scores.get(key) > currentScore)
            {
                temp.put(key, scores.get(key));
            }
            else if(!placed)
            {
                temp.put(initials, currentScore);
                placed = true;
            }
            if(placed && temp.size() != scores.size())
            {
                temp.put(key, scores.get(key));
            }
            else if(scores.size() == temp.size())
                break;
        }
        scores = temp;
    }

    /**
     * Finds the highest currentScore in the list and updates highSore
     */
    private void determineHighScore()
    {
        Map.Entry<String, Integer> maxEntry = null;
        for(Map.Entry<String, Integer> entry : scores.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        highScore = maxEntry;
        /*
        for(int i = 0; i < scoreList.size(); i++)
        {
            if(scoreList.get(i) > highScore)
                highScore = scoreList.get(i);
        }
        */
    }


    public Map.Entry<String, Integer> getHighScore()
    {
        determineHighScore();
        return highScore;
    }


    public HashMap<String, Integer> getScoreList() {
        return scores;
    }

    /**
     * Gets the player initials for the current game score
     * @return
     */
    public String getInitials()
    {
        return this.initials;
    }

    /**
     * Sets the player initials for the current game score
     * @param initials
     */
    public void setInitials(String initials)
    {
        this.initials = initials;
    }
}
