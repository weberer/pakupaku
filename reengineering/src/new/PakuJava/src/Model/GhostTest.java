package Model;

import Controller.GameController;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class GhostTest {

    @Test
    public void  inJail() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();

        Stinky stinky = new Stinky(gameData.getMap());
        gameData.getGhostList().add(stinky);
        gameData.getGhostList().add(new Kinky(gameData.getMap()));
        gameData.getGhostList().add(new Hinky(stinky, gameData.getMap()));
        gameData.getGhostList().add(new Blaine(gameData.getMap()));

        assertFalse(gameData.getGhostList().get(0).inJail());
        assertTrue(gameData.getGhostList().get(1).inJail());
        assertTrue(gameData.getGhostList().get(2).inJail());
        assertTrue(gameData.getGhostList().get(3).inJail());
    }

    @Test
    public void  jailMove() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        gameData.getGhostList().add(new Kinky(gameData.getMap()));
        gameData.getGhostList().get(0).getLoc().setxLoc(11);
        gameData.getGhostList().get(0).getLoc().setyLoc(15);
        gameData.getGhostList().get(0).move();
        System.out.println(gameData.getGhostList().get(0).getLoc().getxLoc());
        System.out.println(gameData.getGhostList().get(0).getLoc().getyLoc());

    }

    @Test
    public void  checkWarp() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        gameData.getGhostList().add(stinky);
        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(14);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(2, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(14, gameData.getGhostList().get(0).getLoc().getyLoc());

        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(25, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(14, gameData.getGhostList().get(0).getLoc().getyLoc());
    }

    @Test
    public void  scatterMove() {
    }

    @Test
    public void  fleeMove() {
    }

    @Test
    public void eatenMove() {
    }

    @Test
    public void  calculateMove() {
    }

    @Test
    public void  setState() {
    }

    @Test
    public void  getState() {
    }

    @Test
    public void  addScore() {
    }

    @Test
    public void  resetMultiplier() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        stinky.resetMultiplier();
        assertEquals(1, stinky.getMultiplier());
    }

    @Test
    public void resetLocation() {

    }
}