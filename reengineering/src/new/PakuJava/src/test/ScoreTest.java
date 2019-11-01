package test;


import Model.Score;
import org.junit.Test;
import org.junit.runner.RunWith;

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
}
