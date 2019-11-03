package test;

import Controller.GameController;
import Model.*;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * JUnitTesting for the main Ghost methods is performed here. One thing to note is to NOT perform all tests at once, as
 * they will usually give two errors on perfectly good tests for some reason.
 */
public class GhostTest {

    /**
     * Tests that the ghosts spawn properly and the inJail check works.
     */
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

    /**
     * Checks that the warp areas of (1, 14) and (26,14) work as intended.
     */
    @Test
    public void  checkWarp() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        Ghost.setupTimers();
        stinky.startTimer();
        gameData.getGhostList().add(stinky);
        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(14);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(2, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(14, gameData.getGhostList().get(0).getLoc().getyLoc());

        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(14);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(25, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(14, gameData.getGhostList().get(0).getLoc().getyLoc());

        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();
        assertEquals(25, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(14, gameData.getGhostList().get(0).getLoc().getyLoc());

        gameData.getGhostList().get(0).getLoc().setxLoc(1);
        gameData.getGhostList().get(0).setDirection(Direction.right);
        gameData.getGhostList().get(0).move();
        assertEquals(2, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(14, gameData.getGhostList().get(0).getLoc().getyLoc());
    }
/**
 * These tests are the most important tests for the Ghost class to work. the next four tests test the corresponding
 * quadrant of the map, using the coordinates (12, 14) and (13,15) as the center of the map. Each test tests all corners
 * of the quadrant twice. The code is formatted as follows:
 * Comment of the coordinates being tested
 * Comment of what Direction value is being used
 * Test of that Direction
 * Comment of the other Direction being used
 * Test of that direction
 *
 * Please note that these only need to be tested from the Directions going directly into a wall, as the other directions
 * will not be possible to achieve in the game
 */
    @Test
    public void CheckTopLeftCorners()
    {
        //(1,1)
        //Moving Left
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        Ghost.setupTimers();
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
        Ghost.setupTimers();
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
        Ghost.setupTimers();
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
        Ghost.setupTimers();
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
        gameData.getGhostList().get(0).getLoc().setxLoc(26);
        gameData.getGhostList().get(0).getLoc().setyLoc(26);
        gameData.getGhostList().get(0).setDirection(Direction.down);
        gameData.getGhostList().get(0).move();
        assertEquals(25, gameData.getGhostList().get(0).getLoc().getxLoc());
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

    /**
     * Ensures that the jailMove method allows the ghost to leave jail when their exitCounter is 0. Stinky is the best
     * to test this with as its counter is always 0.
     */
    @Test
    public void jailExit()
    {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        Ghost.setupTimers();
        gameData.getGhostList().add(stinky);
        gameData.getGhostList().get(0).getLoc().setxLoc(14);
        gameData.getGhostList().get(0).getLoc().setyLoc(13);
        gameData.getGhostList().get(0).setDirection(Direction.left);
        gameData.getGhostList().get(0).move();

        assertEquals(14, gameData.getGhostList().get(0).getLoc().getxLoc());
        assertEquals(12, gameData.getGhostList().get(0).getLoc().getyLoc());
        assertFalse(gameData.getGhostList().get(0).inJail());
    }

    /**
     * A test of the fleeMove method in Ghost. Due to fleeMove being a private method accessable only by move, move
     * must be used to test this. This is a bit of an annoying one to test as the ghost variance may move towards Paku.
     */
    @Test
    public void  fleeMove() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        Ghost.setupTimers();
        stinky.setState(GhostState.flee);
        gameData.setPakuLoc(new Location(26, 29));
        stinky.getLoc().setyLoc(29);
        stinky.getLoc().setxLoc(15);
        stinky.setDirection(Direction.down);
        stinky.setFleeTimer(1000000);
        stinky.move();
        stinky.move();
        assertEquals(16, stinky.getLoc().getxLoc());
    }

    /**
     * Tests that the eaten ghost can go into the jail and reset its mode to scatter mode.
     */
    @Test
    public void eatenMove() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        Ghost.setupTimers();
        stinky.setState(GhostState.eaten);
        stinky.move();
        assertEquals(12, stinky.getLoc().getyLoc());
        assertEquals(14, stinky.getLoc().getxLoc());
        stinky.move();
        stinky.move();
        stinky.move();
        stinky.move();
        stinky.move();
        assertEquals(GhostState.scatter, stinky.getState());
    }

    /**
     * Tests the setter setState to ensure it doesn't send bad data back.
     */
    @Test
    public void  setState() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        Ghost.setupTimers();
        stinky.setState(GhostState.eaten);
        assertEquals(GhostState.eaten, stinky.getState());

    }

    /**
     * Tests the getter getState to ensure it saves the states.
     */
    @Test
    public void  getState() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        Ghost.setupTimers();
        stinky.startTimer();
        assertEquals(GhostState.scatter, stinky.getState());
    }

    /**
     * Tests that addScore properly updates the score for one ghost, capping at 6400 points.
     */
    @Test
    public void  addScoreOneGhost() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        Ghost.setupTimers();
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
        Ghost.setupTimers();
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
     * Tests that resetMultiplier sets multiplier to 1
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

    /**
     * testFlee tests the flee based methods in Ghost. It starts the flee timer first to make sure that it works,
     * then it tests the isBlink method. Afterwards, an instance of Stinky is used to model the ghosts, as these methods
     * are all shared by the ghosts. makeFlee is tested to make sure that the state is set to flee and that the storedState
     * in Ghost stores the state before it was fleeing. blink is tested next, comparing it to isStinkyBlink in GameData.
     * Lastly, endingFleeProtocol is tested to make sure that A) the state of the ghost is restored using the value of
     * storedState, B) storedState is set to null, and C) the ghost's blink value is false.
     */
    @Test
    public void testFlee()
    {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky s = new Stinky(gameData.getMap());
        Ghost.setupTimers();
        s.startTimer();
        Ghost.startGlobalFleeCounter();
        assertFalse(Ghost.getGlobalFleeCounter() == 0);
        Ghost.setGlobalFleeCounter(50);
        assertTrue(Ghost.isBlinking());
        GhostState state = s.getState();
        s.makeFlee();
        assertTrue(s.getState().equals(GhostState.flee));
        assertTrue(s.getStoredState().equals(state));
        s.blink();
        assertTrue(gameData.isStinkyBlink());
        s.blink();
        assertFalse(gameData.isStinkyBlink());
        s.blink();
        assertTrue(gameData.isStinkyBlink());
        s.endingFleeProtocol();

        assertTrue(s.getState().equals(state));
        assertTrue(s.getStoredState() == null);
        assertFalse(gameData.isStinkyBlink());
    }
}