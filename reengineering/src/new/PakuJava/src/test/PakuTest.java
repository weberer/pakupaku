package test;


import Model.Direction;
import Model.GameData;
import Controller.GameController;
import Model.Paku;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class PakuTest
{

    @Test
    public void move()
    {
        GameData gameData = GameData.getInstance();
        // ArrayList row = map.get(loc.getyLoc());
        Paku paku = Paku.getInstance();
        paku.move();
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
        GameData gameData = GameData.getInstance();
        new GameController();  //calls the loadMap() method
        paku.setGameData(gameData);
        paku.setMap(gameData.getMap());
        paku.move();  //moves paku one unit in the default left direction
        paku.resetPaku();  //reset

        //test whether Paku has returned to starting Location
        Assert.assertEquals(paku.getLoc().getxLoc(), paku.getSTARTING_X());
        Assert.assertEquals(paku.getLoc().getyLoc(), paku.getSTARTING_Y());
        paku.move();
        paku.move();
        paku.move();
        paku.resetPaku();
        //test whether Paku has returned to starting Location after starting moves
        Assert.assertEquals(paku.getLoc().getxLoc(), paku.getSTARTING_X());
        Assert.assertEquals(paku.getLoc().getyLoc(), paku.getSTARTING_Y());

        paku.setDir(Direction.up);
        paku.move(); //moves paku one unit in the up direction
        paku.resetPaku();
        Assert.assertEquals(paku.getLoc().getxLoc(), paku.getSTARTING_X());
        Assert.assertEquals(paku.getLoc().getyLoc(), paku.getSTARTING_Y());
        paku.move();
        paku.move();
        paku.move();
        paku.resetPaku();
        Assert.assertEquals(paku.getLoc().getxLoc(), paku.getSTARTING_X());
        Assert.assertEquals(paku.getLoc().getyLoc(), paku.getSTARTING_Y());

        paku.setDir(Direction.down);
        paku.move(); //moves paku one unit in the down direction
        paku.resetPaku();
        Assert.assertEquals(paku.getLoc().getxLoc(), paku.getSTARTING_X());
        Assert.assertEquals(paku.getLoc().getyLoc(), paku.getSTARTING_Y());
        paku.move();
        paku.move();
        paku.resetPaku();
        Assert.assertEquals(paku.getLoc().getxLoc(), paku.getSTARTING_X());
        Assert.assertEquals(paku.getLoc().getyLoc(), paku.getSTARTING_Y());


        paku.setDir(Direction.right);
        paku.move(); //moves paku one unit in the right direction
        paku.resetPaku();
        Assert.assertEquals(paku.getLoc().getxLoc(), paku.getSTARTING_X());
        Assert.assertEquals(paku.getLoc().getyLoc(), paku.getSTARTING_Y());
        paku.move();
        paku.move();
        paku.resetPaku();
        Assert.assertEquals(paku.getLoc().getxLoc(), paku.getSTARTING_X());
        Assert.assertEquals(paku.getLoc().getyLoc(), paku.getSTARTING_Y());
    }

    @Test
    public void resetLocation()
    {

    }
}
