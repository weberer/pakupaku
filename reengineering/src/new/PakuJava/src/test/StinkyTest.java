package test;

import Controller.GameController;
import Model.GameData;
import Model.Stinky;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

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
