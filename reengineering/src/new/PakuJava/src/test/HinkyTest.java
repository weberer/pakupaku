package test;


import Controller.GameController;
import Model.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class HinkyTest {


    @Test
    public void resetLocation() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        Hinky hinky = new Hinky(stinky, gameData.getMap());
        stinky.setupTimers();
        stinky.startTimer();
        hinky.getLoc().setxLoc(1);
        hinky.getLoc().setyLoc(1);
        hinky.resetLocation();
        assertEquals(11, hinky.getLoc().getxLoc());
        assertEquals(14, hinky.getLoc().getyLoc());
    }

    @Test
    public void chaseMove() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        Hinky hinky = new Hinky(stinky, gameData.getMap());
        stinky.setupTimers();
        hinky.startTimer();
        hinky.setState(GhostState.chase);
    }


    @Test
    public void  scatterMove() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky s = new Stinky(gameData.getMap());
        Hinky hinky = new Hinky(s,gameData.getMap());
        hinky.setupTimers();
        hinky.startTimer();
        hinky.getLoc().setxLoc(21);
        hinky.getLoc().setyLoc(23);
        hinky.setDirection(Direction.right);
        hinky.move();
        assertEquals(21, hinky.getLoc().getxLoc());
        assertEquals(24, hinky.getLoc().getyLoc());

        hinky.startTimer();
        hinky.getLoc().setxLoc(16);
        hinky.getLoc().setyLoc(29);
        hinky.setDirection(Direction.down);
        hinky.move();
        assertEquals(17, hinky.getLoc().getxLoc());
        assertEquals(29, hinky.getLoc().getyLoc());
    }
}
