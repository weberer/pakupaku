package test;

import Controller.GameController;
import Model.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;

public class KinkyTest {


    @Test
    public void resetLocation() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Kinky kinky = new Kinky(gameData.getMap());
        kinky.getLoc().setxLoc(1);
        kinky.getLoc().setyLoc(1);
        kinky.resetLocation();
        assertEquals(14, kinky.getLoc().getxLoc());
        assertEquals(14, kinky.getLoc().getyLoc());
    }

    /**
     * This test ensures that Kinky favors moving towards the top left corner when scattering.
     */
    @Test
    public void  scatterMove() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Kinky kinky = new Kinky(gameData.getMap());
        kinky.setupTimers();
        kinky.startTimer();
        kinky.getLoc().setxLoc(1);
        kinky.getLoc().setyLoc(6);
        kinky.setDirection(Direction.right);
        kinky.move();
        assertEquals(1, kinky.getLoc().getxLoc());
        assertEquals(5, kinky.getLoc().getyLoc());

        kinky.startTimer();
        kinky.getLoc().setxLoc(6);
        kinky.getLoc().setyLoc(1);
        kinky.setDirection(Direction.up);
        kinky.move();
        assertEquals(5, kinky.getLoc().getxLoc());
        assertEquals(1, kinky.getLoc().getyLoc());
    }

    /**
     * Tests that Kinky's behaviors are functioning properly: to chase Paku by targeting the tile four spaces away
     * from which Paku is facing.
     */
    @Test
    public void chaseMove() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Kinky kinky = new Kinky(gameData.getMap());
        kinky.setupTimers();
        kinky.startTimer();
        kinky.setState(GhostState.chase);
        Paku p = gameData.getPaku();
        p.setDir(Direction.up);
        p.getLoc().setxLoc(6);
        p.getLoc().setyLoc(5);
        kinky.getLoc().setxLoc(6);
        kinky.getLoc().setyLoc(8);
        kinky.setDirection(Direction.right);
        kinky.move();
        assertEquals(7, kinky.getLoc().getyLoc());

        p.setDir(Direction.left);
        p.getLoc().setxLoc(6);
        p.getLoc().setyLoc(8);
        kinky.getLoc().setxLoc(6);
        kinky.getLoc().setyLoc(5);
        kinky.setDirection(Direction.down);
        kinky.move();
        assertEquals(5, kinky.getLoc().getxLoc());

        p.setDir(Direction.right);
        p.getLoc().setxLoc(6);
        p.getLoc().setyLoc(5);
        kinky.getLoc().setxLoc(6);
        kinky.getLoc().setyLoc(1);
        kinky.setDirection(Direction.up);
        kinky.move();
        assertEquals(7, kinky.getLoc().getxLoc());

        p.setDir(Direction.down);
        p.getLoc().setxLoc(6);
        p.getLoc().setyLoc(14);
        kinky.getLoc().setxLoc(6);
        kinky.getLoc().setyLoc(1);
        kinky.setDirection(Direction.left);
        kinky.move();
        assertEquals(2, kinky.getLoc().getyLoc());

    }
}
