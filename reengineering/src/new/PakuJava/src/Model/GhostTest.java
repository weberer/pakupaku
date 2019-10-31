package Model;

import Controller.GameController;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        assertEquals(false, gameData.getGhostList().get(0).inJail());
        assertEquals(true, gameData.getGhostList().get(1).inJail());
        assertEquals(true, gameData.getGhostList().get(2).inJail());
        assertEquals(true, gameData.getGhostList().get(3).inJail());
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
    public void CheckTopLeftCorner()
    {
        //(1,1)
        //Moving Left
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        stinky.setupTimers();
        gameData.getGhostList().add(stinky);
        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).getLoc().setyLoc(1);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(1, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(2, gameData.getGhostList().get(0).getLoc().getyLoc());

        //Moving Up
        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).getLoc().setyLoc(1);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(2, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(1, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(1,8)
        //From Left
        /*gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(1, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(7, gameData.getGhostList().get(0).getLoc().getyLoc());*/
        //From Down
        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.down);
        gameData.getGhostList().get(0).move();
        assertEquals(2, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(8, gameData.getGhostList().get(0).getLoc().getyLoc());

        //(12, 1)
        //From Left
        gameData.getGhostList().get(0).getLoc().setxLoc(12);
        gameData.getGhostList().get(0).getLoc().setyLoc(1);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(12, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(2, gameData.getGhostList().get(0).getLoc().getyLoc());
        //From Down
        /*gameData.getGhostList().get(0).getLoc().setxLoc(12);
        gameData.getGhostList().get(0).getLoc().setyLoc(2);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(11, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(1, gameData.getGhostList().get(0).getLoc().getyLoc());*/
        //(9,8)
        //From Right
        gameData.getGhostList().get(0).getLoc().setxLoc(9);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(9, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(7, gameData.getGhostList().get(0).getLoc().getyLoc());
        //From Top
        gameData.getGhostList().get(0).getLoc().setxLoc(9);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.down);
        gameData.getGhostList().get(0).move();
        assertEquals(10, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(8, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(12, 8)
        //From Left
        gameData.getGhostList().get(0).getLoc().setxLoc(12);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(12, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(9, gameData.getGhostList().get(0).getLoc().getyLoc());
        //From Down
        gameData.getGhostList().get(0).getLoc().setxLoc(12);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(11, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(8, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(9, 11)
        //From Right
        gameData.getGhostList().get(0).getLoc().setxLoc(9);
        gameData.getGhostList().get(0).getLoc().setyLoc(11);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(9, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(12, gameData.getGhostList().get(0).getLoc().getyLoc());
        //From Down
        gameData.getGhostList().get(0).getLoc().setxLoc(9);
        gameData.getGhostList().get(0).getLoc().setyLoc(11);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(9, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(12, gameData.getGhostList().get(0).getLoc().getyLoc());

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