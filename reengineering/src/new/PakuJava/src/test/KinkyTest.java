package test;

import Controller.GameController;
import Model.Direction;
import Model.GameData;
import Model.GhostState;
import Model.Kinky;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @Test
    public void chaseMove() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Kinky kinky = new Kinky(gameData.getMap());
        kinky.setupTimers();
        kinky.startTimer();
        kinky.setState(GhostState.chase);
    }
}
