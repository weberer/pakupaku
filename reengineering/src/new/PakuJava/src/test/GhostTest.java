package test;

import Controller.GameController;
import Model.*;
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
        stinky.setupTimers();
        stinky.startTimer();
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
/**
 * Tests all of the corners in the top left quadrant of the maze to see if the ghosts properly react.
 */
    @Test
    public void CheckTopLeftCorners()
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
        //Moving Left
        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(1, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(7, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Down
        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.down);
        gameData.getGhostList().get(0).move();
        assertEquals(2, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(8, gameData.getGhostList().get(0).getLoc().getyLoc());

        //(12, 1)
        //Moving Right
        gameData.getGhostList().get(0).getLoc().setxLoc(12);
        gameData.getGhostList().get(0).getLoc().setyLoc(1);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(12, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(2, gameData.getGhostList().get(0).getLoc().getyLoc());
        //From Up
        gameData.getGhostList().get(0).getLoc().setxLoc(12);
        gameData.getGhostList().get(0).getLoc().setyLoc(1);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(11, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(1, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(9,8)
        //Moving Left
        gameData.getGhostList().get(0).getLoc().setxLoc(9);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(9, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(7, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Down
        gameData.getGhostList().get(0).getLoc().setxLoc(9);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.down);
        gameData.getGhostList().get(0).move();
        assertEquals(10, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(8, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(12, 8)
        //Moving Right
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
        //Moving Left
        gameData.getGhostList().get(0).getLoc().setxLoc(9);
        gameData.getGhostList().get(0).getLoc().setyLoc(11);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(9, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(12, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Up
        gameData.getGhostList().get(0).getLoc().setxLoc(9);
        gameData.getGhostList().get(0).getLoc().setyLoc(11);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(10, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(11, gameData.getGhostList().get(0).getLoc().getyLoc());

    }
    @Test
    public void CheckTopRightCorners()
    {
        //(26, 1)
        //Moving Right
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        stinky.setupTimers();
        gameData.getGhostList().add(stinky);
        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(1);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(26, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(2, gameData.getGhostList().get(0).getLoc().getyLoc());

        //Moving Up
        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(1);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(25, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(1, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(26,8)
        //Moving Right
        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(26, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(7, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Down
        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.down);
        gameData.getGhostList().get(0).move();
        assertEquals(25, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(8, gameData.getGhostList().get(0).getLoc().getyLoc());

        //(15, 1)
        //Moving Left
        gameData.getGhostList().get(0).getLoc().setxLoc(15);
        gameData.getGhostList().get(0).getLoc().setyLoc(1);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(15, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(2, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Up
        gameData.getGhostList().get(0).getLoc().setxLoc(15);
        gameData.getGhostList().get(0).getLoc().setyLoc(1);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(16, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(1, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(18,8)
        //Moving Right
        gameData.getGhostList().get(0).getLoc().setxLoc(18);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(18, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(7, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Down
        gameData.getGhostList().get(0).getLoc().setxLoc(18);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.down);
        gameData.getGhostList().get(0).move();
        assertEquals(17, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(8, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(15, 8)
        //Moving Left
        gameData.getGhostList().get(0).getLoc().setxLoc(15);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(15, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(9, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Up
        gameData.getGhostList().get(0).getLoc().setxLoc(15);
        gameData.getGhostList().get(0).getLoc().setyLoc(8);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(16, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(8, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(18, 11)
        //Moving Right
        gameData.getGhostList().get(0).getLoc().setxLoc(18);
        gameData.getGhostList().get(0).getLoc().setyLoc(11);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(18, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(12, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Up
        gameData.getGhostList().get(0).getLoc().setxLoc(18);
        gameData.getGhostList().get(0).getLoc().setyLoc(11);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(17, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(11, gameData.getGhostList().get(0).getLoc().getyLoc());

    }

    @Test
    public void CheckBottomLeftCorners()
    {
        //(1,29)
        //Moving Left
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        stinky.setupTimers();
        gameData.getGhostList().add(stinky);
        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).getLoc().setyLoc(29);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(1, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(28, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Down
        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).getLoc().setyLoc(29);
        gameData.getGhostList().get(0).setDirection(Direction.down);
        gameData.getGhostList().get(0).move();
        assertEquals(2, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(29, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(1,26)
        //Moving Left
        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(1, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(27, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Up
        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(2, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(26, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(1,23)
        //Moving Left
        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).getLoc().setyLoc(23);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(1, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(22, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Down
        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).getLoc().setyLoc(23);
        gameData.getGhostList().get(0).setDirection(Direction.down);
        gameData.getGhostList().get(0).move();
        assertEquals(2, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(23, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(1,20)
        //Moving Left
        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).getLoc().setyLoc(20);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(1, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(21, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Up
        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).getLoc().setyLoc(20);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(2, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(20, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(6,26)
        //Moving Right
        gameData.getGhostList().get(0).getLoc().setxLoc(6);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(6, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(25, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Down
        gameData.getGhostList().get(0).getLoc().setxLoc(6);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.down);
        gameData.getGhostList().get(0).move();
        assertEquals(5, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(26, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(9, 26)
        //Moving Left
        gameData.getGhostList().get(0).getLoc().setxLoc(9);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(9, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(25, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Down
        gameData.getGhostList().get(0).getLoc().setxLoc(9);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.down);
        gameData.getGhostList().get(0).move();
        assertEquals(10, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(26, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(12, 26)
        //Moving Right
        gameData.getGhostList().get(0).getLoc().setxLoc(12);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(12, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(27, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Up
        gameData.getGhostList().get(0).getLoc().setxLoc(12);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(11, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(26, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(12, 20)
        //Moving Right
        gameData.getGhostList().get(0).getLoc().setxLoc(12);
        gameData.getGhostList().get(0).getLoc().setyLoc(20);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(12, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(21, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Up
        gameData.getGhostList().get(0).getLoc().setxLoc(12);
        gameData.getGhostList().get(0).getLoc().setyLoc(20);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(11, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(20, gameData.getGhostList().get(0).getLoc().getyLoc());

    }

    @Test
    public void CheckBottomRightCorners()
    {
        //(26,29)
        //Moving Right
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        stinky.setupTimers();
        gameData.getGhostList().add(stinky);
        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(29);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(26, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(28, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Down
        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(29);
        gameData.getGhostList().get(0).setDirection(Direction.down);
        gameData.getGhostList().get(0).move();
        assertEquals(25, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(29, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(26,26)
        //Moving Right
        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(26, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(27, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Up
        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(25, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(26, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(26,23)
        //Moving Right
        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(23);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(26, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(22, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Down
        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(23);
        gameData.getGhostList().get(0).setDirection(Direction.down);
        gameData.getGhostList().get(0).move();
        assertEquals(25, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(23, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(26,20)
        //Moving Right
        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(20);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(26, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(21, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Up
        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(20);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(25, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(20, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(21,26)
        //Moving Left
        gameData.getGhostList().get(0).getLoc().setxLoc(21);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(21, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(25, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Down
        gameData.getGhostList().get(0).getLoc().setxLoc(27);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.down);
        gameData.getGhostList().get(0).move();
        assertEquals(27, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(26, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(18, 26)
        //Moving Right
        gameData.getGhostList().get(0).getLoc().setxLoc(18);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(18, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(25, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Down
        gameData.getGhostList().get(0).getLoc().setxLoc(18);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.down);
        gameData.getGhostList().get(0).move();
        assertEquals(17, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(26, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(15, 26)
        //Moving Left
        gameData.getGhostList().get(0).getLoc().setxLoc(15);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(15, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(27, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Up
        gameData.getGhostList().get(0).getLoc().setxLoc(15);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(16, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(26, gameData.getGhostList().get(0).getLoc().getyLoc());
        //(15, 20)
        //Moving Left
        gameData.getGhostList().get(0).getLoc().setxLoc(15);
        gameData.getGhostList().get(0).getLoc().setyLoc(20);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(15, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(21, gameData.getGhostList().get(0).getLoc().getyLoc());
        //Moving Up
        gameData.getGhostList().get(0).getLoc().setxLoc(15);
        gameData.getGhostList().get(0).getLoc().setyLoc(20);
        gameData.getGhostList().get(0).setDirection(Direction.up);
        gameData.getGhostList().get(0).move();
        assertEquals(16, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(20, gameData.getGhostList().get(0).getLoc().getyLoc());

    }
    @Test
    public void jailExit()
    {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        stinky.setupTimers();
        gameData.getGhostList().add(stinky);
        gameData.getGhostList().get(0).getLoc().setxLoc(14);
        gameData.getGhostList().get(0).getLoc().setyLoc(13);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();

        assertEquals(14, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(12, gameData.getGhostList().get(0).getLoc().getyLoc());
        assertFalse(gameData.getGhostList().get(0).inJail());
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
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        stinky.setupTimers();
        stinky.setState(GhostState.eaten);
        assertEquals(GhostState.eaten, stinky.getState());

    }


    @Test
    public void  getState() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        stinky.setupTimers();
        stinky.startTimer();
        assertEquals(GhostState.scatter, stinky.getState());
    }

    /**
     * Tests that addscore properly updates the score for one ghost, capping at 6400 points.
     */
    @Test
    public void  addScoreOneGhost() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        stinky.setupTimers();
        stinky.startTimer();
        int test = stinky.addScore(new Score());
        assertEquals(200, test);
        test = stinky.addScore(new Score());
        assertEquals(400, test);
        test = stinky.addScore(new Score());
        assertEquals(800, test);
        test = stinky.addScore(new Score());
        assertEquals(1600, test);
        test = stinky.addScore(new Score());
        assertEquals(3200, test);
        test = stinky.addScore(new Score());
        assertEquals(6400, test);
        test = stinky.addScore(new Score());
        assertEquals(6400, test);

    }

    /**
     * Same test as above but with multiple ghosts. If it works with two it works with all four.
     */
    @Test
    public void  addScoreMultipleGhosts() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        Kinky kinky = new Kinky(gameData.getMap());
        stinky.setupTimers();
        stinky.startTimer();
        kinky.startTimer();
        int test = stinky.addScore(new Score());
        assertEquals(200, test);
        test = stinky.addScore(new Score());
        assertEquals(400, test);
        test = kinky.addScore(new Score());
        assertEquals(800, test);
        test = stinky.addScore(new Score());
        assertEquals(1600, test);
        test = kinky.addScore(new Score());
        assertEquals(3200, test);
        test = stinky.addScore(new Score());
        assertEquals(6400, test);
        test = stinky.addScore(new Score());
        assertEquals(6400, test);

    }
    /**
     * Tests that reset mutiplier sets multiplier to 1
     */
    @Test
    public void  resetMultiplier() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        stinky.resetMultiplier();
        assertEquals(1, stinky.getMultiplier());
        int test = stinky.addScore(new Score());
        test = stinky.addScore(new Score());
        test = stinky.addScore(new Score());
        test = stinky.addScore(new Score());
        stinky.resetMultiplier();
        assertEquals(1, stinky.getMultiplier());
    }

    @Test
    public void resetLocation() {

    }
}