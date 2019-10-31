package Model;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

import static org.junit.Assert.*;


public class PakuTest
{


    @Test
    public void move()
    {

    }

    @Test
    public void substractLife()
    {
        Paku paku = Paku.getInstance();
        int lives = paku.getRemainingLife();
        paku.substractLife();
        int newLives = paku.getRemainingLife();
        Assert.assertEquals(lives - 1, newLives);
    }

    @Test
    public void addLife() {
    }

    @Test
    public void resetPaku() {
    }

    @Test
    public void resetLocation() {
    }
}
