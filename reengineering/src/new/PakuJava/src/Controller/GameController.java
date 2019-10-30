package Controller;

import Model.Ghost;
import Model.Paku;
import Model.GameStatus;
import Model.*;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

import com.opencsv.CSVReader;

import java.io.Reader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author kruge
 */
public class GameController {
    private List<Ghost> ghostlist;
    private final int NUM_GHOSTS = 4;

    //values from PAS file lines 220-229
    private final int pointsPerLife = 10000;
    private final int startingLives = 3;
    private final int startingLevel = 0;
    private final int startingDots = 244;
    private final int fruitCounter1 = startingDots - 70;
    private final int fruitCounter2 = fruitCounter1 - 100;
    private final int elroy = 20;  //I believe this refers to the number of dots paku must eat before blinky speeds up. Source: https://paku.fandom.com/wiki/Blinky
    private final int superElroy = 10;
    private final int dotPoint = 10;

    private Paku paku;
    private int gamelevel;
    private GameStatus gameStatus;
    private Score score;
    private final double ghostSpeed = 10;


    private int highScore;
    private List<Integer> scoreList;

    private GameData gameData; //GAMEDATA OBJECT; THERE SHOULD BE ONLY ONE

    private int frame;  //the number of the current frame
    private Direction inputDirection = Direction.stay;
    private Fruit fruit = null;

    //private JSONObject jo = new JSONObject();  //rethinking this, replacing it with GameData object class --Evan

    private ArrayList<ArrayList> map;
    private ArrayList<Integer> eachrow;
    private final String SAMPLE_CSV_FILE_PATH = "../../../PakuJava/src/asset/map.csv";
    // used for checking the score based extra lives.
    private int extraLives;

    public GameController() {
        System.out.println("Game controller has been created");
        ghostlist = new ArrayList<>();
        gamelevel = 0;
        map = new ArrayList<ArrayList>();
        LoadMap();
        scoreList = new ArrayList<Integer>();
    }

    private void LoadMap() {
        // For showing the dictionary. do not remove.
        File file = new File(".");
        for(String fileNames : file.list()) System.out.println(fileNames);
        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVReader csvReader = new CSVReader(reader);
        ) {
            // Reading Records One by One in a String array
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {

                eachrow = new ArrayList<Integer>();

                for(String number: nextRecord){
                    System.out.println(number);

                    number = number.replaceAll("\\uFEFF", "");
                    int temp = Integer.parseInt(number);
                    eachrow.add(temp);
                }
                map.add(eachrow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Responsible for setting up the game
    public void startGame() {
        gameData = GameData.getInstance();  //INSTANTIATION OF GAMEDATA OBJECT
        paku = Paku.getInstance();
        paku.setGameData(gameData); //giving Paku a reference to gameData
        spawnGhosts();
        score = new Score();  //new score object created each game
        gameStatus = GameStatus.staring;
        gameData.setGameStatus(gameStatus);  //update gameStatus


    }



    /*
    public int increaseGhostSpeed(int ghostSpeed) {
        return (int) Math.pow(gamelevel, ghostSpeedToThePower);
    }
    */

    //creates the four ghosts for gameplay
    public void spawnGhosts() {
        ghostlist.add(new Blaine(map)); //orange
        ghostlist.add(new Kinky(map)); //blue
        Stinky stinky = new Stinky(map);
        ghostlist.add(stinky); //red
        ghostlist.add(new Hinky(stinky, map)); //pink, Hinky needs Stinky's info to move. please do not modify

        setGhostGameDataReference();

    }


    /**
     * Passes the gameData object to the ghosts so that they each have a reference to it so that they can update it
     */
    private void setGhostGameDataReference()
    {
        for(Ghost ghost : ghostlist)
        {
            ghost.setGameData(gameData);
        }
    }

    public void respawn()
    {
        paku.resetLocation();
        for(Ghost ghost : ghostlist){
            ghost.resetLocation();
        }
        ghostlist.get(0).resetMultiplier();
    }

    /**
     * Resets game
     */
    public void resetGame()
    {
        paku.resetPaku();
        for(Ghost ghost : ghostlist){
            ghost.resetLocation();
        }
        ghostlist.get(0).resetMultiplier();
        gamelevel = 1;
        extraLives = 1;

        archiveScore();  //add score to score list for high score tracking purposes
        determineHighScore();  //update the current high score
    }

    private void nextLevel() {
        paku.resetLocation();
        for(Ghost ghost : ghostlist){
            ghost.resetLocation();
        }
        ghostlist.get(0).resetMultiplier();
        gamelevel = gamelevel++;
        LoadMap();
    }
    //Called every frame(or whenever timer ticks)
    public void update(){
        Controls input = receivedUserInput();
        if(input != Controls.escape && input != Controls.O && input != Controls.enter)
        {
            pakuUpdate((input.castToDir(input)));
        }
    }


    //talks to frontend, return input enum
    private Controls receivedUserInput() {

        return null;//Controls.getControl(uiInput(keyBoardInput));
    }



    /*
    This method receives the data from the UI in the form of a JSON object
     */
    public void uiInput(JSONObject keyBoardInput) throws JSONException {
        //frame = input.frame;

        //Controls inputDir = input.input;
        //Direction dr = inputDir.castToDir();
        //pakuUpdate(dr);      //the idea here is to get Paku diretional input from UI to be processed by the game logic

        //String input = (String) keyBoardInput.get("input")[0];
    }

    private void pakuUpdate(Direction input){
        pakuMove(input);
        if(collideWithGhost())
        {
            collideWithGhostProtocol();
            if(gameStatus == GameStatus.GameOver)
                return;
        }
        else{
            ghostsMove();
        }
        if(collideWithGhost())
        {
            collideWithGhostProtocol();
            if(gameStatus == GameStatus.GameOver)
                return;
            else
            {
                respawn();
            }
        }
        
        pakuEatsDots(paku.getLoc());
        if(score.getCurrentScore() > (pointsPerLife * extraLives))
        {
            extraLives++;
            paku.addLife();
        }
        if(!map.contains(1) && !map.contains(3))
          {
             gameStatus = GameStatus.nextLevel;
             nextLevel();
          }
        gameData.setScore(score);
        gameData.setPakuLoc(paku.getLoc());
    }

    private void pakuEatsDots(Location location)
    {
        //map.get(location.getxLoc()).get(location.getyLoc());
        ArrayList column = map.get(location.getxLoc());
        int tile = (int)column.get(location.getyLoc());
        if(tile == 1)
        {
            score.addScore(10);

        }
        if(tile == 3)
        {
            score.addScore(50);
            for(Ghost ghost : ghostlist)
            {
                if(!ghost.getState().equals(GhostState.eaten))
                {
                    ghost.setState(GhostState.flee);
                }
            }
        }
    }

    private void collideWithGhostProtocol() {
        boolean death = false;
        for(Ghost ghost : ghostlist){

            if(!ghost.getState().equals(GhostState.flee) || !ghost.getState().equals(GhostState.eaten))
            {
                paku.substractLife();
                death = true;
            }
            if(ghost.getState().equals(GhostState.flee))
            {
                ghost.addScore(score);
            }
        }
        if(paku.isGameOver()){
            gameStatus = GameStatus.GameOver;
           // gameData.setGameStatus(gameStatus);
        }
        else if(death)
        {
            gameStatus = GameStatus.pakuDiedButStillHasLifeLeft;
            gameData.setGameStatus(gameStatus);
        }

    }

    /**
     * Calls each ghost's move method, which updates the ghost's position
     */
    private void ghostsMove() {
        boolean fleeing = false;
        for (Ghost ghost: ghostlist) {
            ghost.move();
            if(ghost.getState().equals(GhostState.flee) || ghost.getState().equals(GhostState.eaten))
                fleeing = true;
        }
        if(!fleeing)
            ghostlist.get(0).resetMultiplier();
        for (Ghost ghost: ghostlist){

        }
    }
    /**
     * checks whether paku collided with ghost
     * @return
     */
    private boolean collideWithGhost() {
        for(Ghost ghost : ghostlist){
           if(!ghost.getState().equals(GhostState.eaten))
           {
                if(paku.getLoc().getxLoc() == ghost.getLoc().getxLoc())
                    if(paku.getLoc().getyLoc() == ghost.getLoc().getyLoc())
                        return true;
           }
        }
        return false;
    }

    /**
     * Calls paku's move method, which updates the paku's position
     */
    private void pakuMove(Direction input)
    {
        if(!input.equals(Direction.stay))
            if(!inputDirection.equals(input) || !inputDirection.equals(Direction.stay)) {
                paku.setDir(input);
                inputDirection = input;
            }
        paku.move();

    }
    private void spawnFruit()
    {
        if(fruit == null)
        {
            fruit = new Fruit(gamelevel);
            ArrayList column = map.get(14);
            column.set(24, 4); //todo: Find a number for fruit on the map
            map.set(14, column);

        }
    }


    /**
     * Adds currentScore to the list of scores (used to keep track of high scores)
     */
    private void archiveScore()
    {
        scoreList.add(score.getCurrentScore());
    }


    /**
     * Finds the highest currentScore in the list and updates highSore
     */
    private void determineHighScore()
    {
        for(int i = 0; i < scoreList.size(); i++)
        {
            if(scoreList.get(i) > highScore)
                highScore = scoreList.get(i);
        }
    }
}
    

