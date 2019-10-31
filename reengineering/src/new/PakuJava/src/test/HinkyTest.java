package test;


import Controller.GameController;
import Model.Direction;
import Model.GameData;
import Model.Hinky;
import Model.Stinky;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


public class HinkyTest {


    @Test
    public void resetLocation() {
    }

    @Test
    public void move() {
    }

    @Test
    public void recordLocation() {
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
