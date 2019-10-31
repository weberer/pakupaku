package test;
import Model.*;
import Controller.GameController;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class StinkyTest {


    @Test
    public void resetLocation() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        stinky.getLoc().setxLoc(1);
        stinky.getLoc().setyLoc(1);
        stinky.resetLocation();
        assertEquals(14, stinky.getLoc().getxLoc());
        assertEquals(11, stinky.getLoc().getyLoc());
    }

    @Test
    public void move() {
    }

    @Test
    public void recordLocation() {
    }
}
