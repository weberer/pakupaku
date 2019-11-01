package test;

import Controller.GameController;
import Model.Blaine;
import Model.Direction;
import Model.GameData;
import Model.GhostState;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;


public class BlaineTest {


    @Test
    public void resetLocation() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Blaine blaine = new Blaine(gameData.getMap());
        blaine.setupTimers();
        blaine.startTimer();
        blaine.getLoc().setxLoc(1);
        blaine.getLoc().setyLoc(1);
        blaine.resetLocation();
        assertEquals(15, blaine.getLoc().getxLoc());
        assertEquals(14, blaine.getLoc().getyLoc());
    }

    @Test
    public void chaseMove() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Blaine blaine = new Blaine(gameData.getMap());
        blaine.setupTimers();
        blaine.startTimer();
        blaine.setState(GhostState.chase);
        gameData.getPaku().getLoc().setyLoc(5);
        gameData.getPaku().getLoc().setxLoc(6);
        blaine.getLoc().setyLoc(6);
        blaine.getLoc().setxLoc(5);
        blaine.move();
        assertEquals(5, blaine.getLoc().getyLoc());
        //long left test
        gameData.getPaku().getLoc().setyLoc(5);
        gameData.getPaku().getLoc().setxLoc(1);
        blaine.getLoc().setyLoc(5);
        blaine.getLoc().setxLoc(12);
        blaine.setDirection(Direction.down);
        blaine.move();
        assertEquals(11, blaine.getLoc().getxLoc());
        //Long up test
        blaine.getLoc().setyLoc(21);
        blaine.getLoc().setxLoc(6);
        blaine.setDirection(Direction.left);
        blaine.move();
        assertEquals(20, blaine.getLoc().getyLoc());
    }

    @Test
    public void  scatterMove() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Blaine blaine = new Blaine(gameData.getMap());
        blaine.setupTimers();
        blaine.startTimer();
        blaine.getLoc().setxLoc(6);
        blaine.getLoc().setyLoc(23);
        blaine.setDirection(Direction.left);
        blaine.move();
        assertEquals(6, blaine.getLoc().getxLoc());
        assertEquals(24, blaine.getLoc().getyLoc());

        blaine.startTimer();
        blaine.getLoc().setxLoc(12);
        blaine.getLoc().setyLoc(29);
        blaine.setDirection(Direction.down);
        blaine.move();
        assertEquals(11, blaine.getLoc().getxLoc());
        assertEquals(29, blaine.getLoc().getyLoc());


    }
}
