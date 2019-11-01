package test;


import Controller.GameController;
import Model.GameData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

import static org.junit.Assert.*;


public class GameControllerTest {


    @Test
    public void startGame() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        gc.startGame();

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

    }

    @Test
    public void resetGame()
    {
        GameData gameData = GameData.getInstance();

        Assert.assertEquals(gameData.getGamelevel(), 1);



    }

    @Test
    public void update() {
    }

    @Test
    public void receivedUserInput() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        String keyW = gc.receiveUserInput("KeyW")
        assert("keyW", keyW);
    }


    @Test
    public void getDataToSend() {
    }


}
