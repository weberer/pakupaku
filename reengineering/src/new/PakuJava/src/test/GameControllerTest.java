package test;


import Controller.Controls;
import Controller.GameController;
import Model.GameData;
import Model.Paku;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

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





}
