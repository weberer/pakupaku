package test;


import Controller.GameController;
import Model.GameData;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    public void respawn() {
    }

    @Test
    public void resetGame()
    {

    }

    @Test
    public void update() {
    }

    @Test
    public void receivedUserInput() {
    }

    @Test
    public void uiInput() {
    }

    @Test
    public void getDataToSend() {
    }


}
