package test;
import Model.*;
import Controller.GameController;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;


public class StinkyTest {


    @Test
    public void resetLocation() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        stinky.setupTimers();
        stinky.startTimer();
        stinky.getLoc().setxLoc(1);
        stinky.getLoc().setyLoc(1);
        stinky.resetLocation();
        assertEquals(14, stinky.getLoc().getxLoc());
        assertEquals(11, stinky.getLoc().getyLoc());
    }

    @Test
    public void  scatterMove() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        stinky.setupTimers();
        stinky.startTimer();
        stinky.getLoc().setxLoc(26);
        stinky.getLoc().setyLoc(6);
        stinky.setDirection(Direction.right);
        stinky.move();
        assertEquals(26, stinky.getLoc().getxLoc());
        assertEquals(5, stinky.getLoc().getyLoc());

        stinky.startTimer();
        stinky.getLoc().setxLoc(21);
        stinky.getLoc().setyLoc(1);
        stinky.setDirection(Direction.up);
        stinky.move();
        assertEquals(22, stinky.getLoc().getxLoc());
        assertEquals(1, stinky.getLoc().getyLoc());
    }
    @Test
    public void chaseMove() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        stinky.setupTimers();
        stinky.startTimer();
        stinky.setState(GhostState.chase);
        gameData.getPaku().getLoc().setxLoc(12);
        gameData.getPaku().getLoc().setyLoc(20);
        stinky.getLoc().setyLoc(23);
        stinky.getLoc().setxLoc(12);
        stinky.move();
        assertEquals(22, stinky.getLoc().getyLoc());
    }
}