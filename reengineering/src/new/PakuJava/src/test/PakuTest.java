package test;


import Model.Paku;
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
    public void addLife()
    {
        Paku paku = Paku.getInstance();
        int lives = paku.getRemainingLife();
        paku.addLife();
        int newLives = paku.getRemainingLife();
        Assert.assertEquals(lives + 1, newLives);
    }

    @Test
    public void resetPaku()
    {
        Paku paku = Paku.getInstance();
        paku.move();
        paku.resetPaku();
        //paku.getLoc().getxLoc(), paku.get
    }

    @Test
    public void resetLocation() {
    }
}
