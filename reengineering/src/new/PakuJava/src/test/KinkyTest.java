package test;

import Controller.GameController;
import Model.GameData;
import Model.Kinky;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
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
        assertEquals(11, kinky.getLoc().getyLoc());
    }

    @Test
    public void move() {
    }

    @Test
    public void recordLocation() {
    }
}
