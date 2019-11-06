package test;


import Controller.Controls;
import Controller.GameController;
import Model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class GameControllerTest {


    @Test
    public void LoadMap()
    {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        gc.LoadMap();
        Assert.assertNotNull(gameData.getMap());

    }

    @Test
    public void startGame() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        gc.LoadMap();
        gc.startGame();

    }

    @Test
    public void nextLevel(){
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        gc.LoadMap();
        gc.startGame();
        gc.update();
        gc.nextLevel();
    }

    @Test
    public void spawnGhosts() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        gc.spawnGhosts();
        assertEquals(4, gameData.getGhostList().size());
    }

    @Test
    public void respawn()
    {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        gc.startGame();
        gc.respawn();

    }

    @Test
    public void resetGame()
    {
        GameController controller = new GameController();
        GameData gameData = GameData.getInstance();
        controller.spawnGhosts();

        Assert.assertEquals(0 ,gameData.getGamelevel() );
        controller.resetGame();
        Assert.assertEquals(0 ,gameData.getGamelevel() );

        gameData.setGamelevel(1);
        Assert.assertEquals(1 ,gameData.getGamelevel() );
        controller.resetGame();
        Assert.assertEquals(0 ,gameData.getGamelevel() );

        gameData.setExtraLives(4);
        Assert.assertEquals(4 ,gameData.getExtraLives() );
        controller.resetGame();
        Assert.assertEquals(1 ,gameData.getExtraLives() );

        gameData.getScore().addScore(5);
        Assert.assertEquals(5 ,gameData.getScore().getCurrentScore() );
        controller.resetGame();
        Assert.assertEquals(0 ,gameData.getScore().getCurrentScore() );

    }



    @Test
    public void update()
    {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        gc.LoadMap();
        gc.startGame();
        gc.update();
        Assert.assertNotNull(gc.getUserInput());

    }

    @Test
    public void receivedUserInput() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        gc.receivedUserInput("KeyW");

        Controls keyW = gc.getUserInput();
        Assert.assertEquals("keyW", keyW.toString());
    }


    @Test
    public void pakuEatsDots() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        int initialGameDots = gameData.getDots();
        Location inedibleDotLoc = new Location(19, 1);
        gc.pakuEatsDots(inedibleDotLoc);
        int finalGameDots = gameData.getDots();
        Assert.assertEquals(initialGameDots - 1, finalGameDots);

        ArrayList<ArrayList> map = gameData.getMap(); //get copy of the map array
        ArrayList row = map.get(inedibleDotLoc.getyLoc());
        int newTile = (int)row.get(inedibleDotLoc.getxLoc());
        Assert.assertEquals(2, newTile);

        int leftTile = (int)row.get(inedibleDotLoc.getxLoc() - 1);
        Assert.assertEquals(1, leftTile);

    }

    /**
     * Tests that the fruitArray properly builds based on the level.
     */
    @Test
    public void setUpFruitArray() {
        GameData gd = GameData.getInstance();
        GameController gc = new GameController();
        gc.testFruitArray();
        assertEquals(4, gd.getFruitArray()[7]);
    }

    @Test
    public void pakuUpdate() {

        GameData gd = GameData.getInstance();
        GameController gc = new GameController();
    }

    @Test
    public void collideWithGhostProtocol()
    {

        GameData gd = GameData.getInstance();
        GameController gc = new GameController();
        List<Ghost> ghostList = gameData.getGhostList();


    }

    @Test
    public void ghostsMove() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        gc.spawnGhosts();
        List<Ghost> ghostList = gameData.getGhostList();
        Location ghost1Loc = ghostList.get(0).getLoc();
        Location ghost2Loc = ghostList.get(1).getLoc();
        Location ghost3Loc = ghostList.get(2).getLoc();
        Location ghost4Loc = ghostList.get(3).getLoc();
        gc.ghostsMove();
        Assert.assertNotSame(ghost1Loc, ghostList.get(0).getLoc());
        Assert.assertNotSame(ghost1Loc, ghostList.get(1).getLoc());
        Assert.assertNotSame(ghost1Loc, ghostList.get(2).getLoc());
        Assert.assertNotSame(ghost1Loc, ghostList.get(3).getLoc());
    }
    @Test
    public void checkFlee()
    {
        GameData gd = GameData.getInstance();
        GameController gc = new GameController();
        List<Ghost> ghostList = gameData.getGhostList();

    }

    @Test
    public void collideWithGhost() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Paku paku = gameData.getPaku();
        List<Ghost> ghostList = gameData.getGhostList();
        gc.spawnGhosts();
        Location pakuLoc = paku.getLoc();
        ghostList.get(0).setState(GhostState.chase);
        ghostList.get(0).setLoc(pakuLoc); //set ghost location equal to Paku's location

        Assert.assertTrue(gc.collideWithGhost()); //check if the method detects colllison properly
    }

    @Test
    public void pakuMove() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Paku paku = Paku.getInstance();
        paku.setGameData(gameData);
        paku.setMap(gameData.getMap());
        gc.pakuMove(Direction.left);

        ; //move unit in default left direction
        int currentX = paku.getLoc().getxLoc();
        Assert.assertEquals(currentX, paku.getSTARTING_X() - 1);
        paku.resetLocation();
    }


    @Test
    public void spawnFruit()
    {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        gameData.setGamelevel(1);
        gc.spawnFruit();
        int objCode = (int)gameData.getMap().get(17).get(14); //code value at the coordinates where fruit should spawn

        //Test whether fruit is inserted into the map at the proper location
        Assert.assertEquals(gameData.getFRUIT_CODE(), objCode);


    }



    /**
     * testFruit tests to make sure that the gameData method fruitCheck works, and then tests the GameController's
     * spawnFruit method using testFruit as an accessor. If everything runs as intended, the fruitCheck should return
     * false at first, then true once dots < fruitCounter1, in this case, 100. The fruit should also properly spawn in
     * the map coordinates (14, 17), and has an integer code of 5.
     */
    @Test
    public void testFruit()
{
    GameData gameData = GameData.getInstance();
    GameController gc = new GameController();
    assertFalse(gameData.fruitCheck());
    gameData.setDots(100);
    assertTrue(gameData.fruitCheck());
    gc.testFruit();
    assertFalse(gameData.fruitCheck());
    assertTrue((int)gameData.getMap().get(17).get(14) == 5);
}



}
