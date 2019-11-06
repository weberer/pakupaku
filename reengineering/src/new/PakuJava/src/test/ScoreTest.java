package test;


import Model.Score;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static org.junit.Assert.*;

public class ScoreTest {



    @Test
    public void addScore() {
        Score score = new Score();
        score.addScore(1);
        assertEquals(1, score.getCurrentScore());
    }

    @Test
    public void reset() {

    }

    @Test
    public void detemineHighScore()
    {
        Score score = new Score();
        HashMap<String, Integer> highScores = score.getScoreList();
        score.setInitials("asd");
        score.addScore(81000);
        score.reset();
        highScores = score.getScoreList();
        assertEquals(81000, (int)highScores.get("asd"));
    }
}
