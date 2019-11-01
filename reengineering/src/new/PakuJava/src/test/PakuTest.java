package test;

import Model.Direction;
import Model.GameData;
import Controller.GameController;
import Model.Location;
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
        new GameController();  //calls the loadMap() method
        Paku paku = Paku.getInstance();
        paku.setGameData(gameData);
        paku.setMap(gameData.getMap());

        paku.move(); //move unit in default left direction
        int currentX = paku.getLoc().getxLoc();
        Assert.assertEquals(currentX, paku.getSTARTING_X() - 1);
        paku.resetLocation();

        paku.setDir(Direction.right);
        paku.move(); //moves paku one unit in the right direction
        Assert.assertEquals(paku.getLoc().getxLoc(), paku.getSTARTING_X() + 1);
        paku.resetLocation();

        Location newLoc = new Location(15, 23);
        paku.setLoc(newLoc);

        paku.setDir(Direction.up);
        paku.move(); //moves paku one unit in the up direction
        Assert.assertEquals(paku.getLoc().getyLoc(), paku.getSTARTING_Y() - 1);

        paku.setDir(Direction.down);
        paku.move(); //moves paku one unit in the down direction
        Assert.assertEquals(paku.getLoc().getyLoc(), paku.getSTARTING_Y());

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

        Assert.assertEquals(paku.getRemainingLife(), paku.getSTARTINGLIFES());
    }

    @Test
    public void resetLocation()
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
}
