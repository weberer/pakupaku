package Controller;
import Model.Ghost;
import Model.Paku;
import Model.GameStatus;
import Model.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import com.opencsv.CSVReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *  This class controls the game logic
 * @author kruge
 */

public class GameController
{
    private final int POINTS_PER_DOT = 10;
    private final int POINTS_PER_SUPER_DOT = 50;
    private Controls userInput;
    private JSONObject dataToSend;


    private GameData gameData; //GAMEDATA OBJECT; THERE SHOULD BE ONLY ONE
    private final String SAMPLE_CSV_FILE_PATH = "../../../PakuJava/src/asset/map.csv";




    /**
     * GameController constructor
     */
    public GameController()
    {
        gameData = GameData.getInstance();  //INSTANTIATION OF GAMEDATA OBJECT
        LoadMap();
      //  startGame();   //this method is already called from the Program class --Evan
    }

    /**
     * Loads the PakuPaku map, in csv format
     */
    private void LoadMap()
    {
        // For showing the dictionary. do not remove.
        File file = new File(".");
        for(String fileNames : file.list()) System.out.println(fileNames);

        //why are parentheses used here instead of braces??? --Evan 10/30
        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVReader csvReader = new CSVReader(reader);
        )
        {
            // Reading Records One by One in a String array
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null)
            {
              //  eachrow = new ArrayList<Integer>();

                for(String number: nextRecord){
                    //System.out.println(number);

                    number = number.replaceAll("\\uFEFF", "");
                    int temp = Integer.parseInt(number);
                    gameData.getEachRow().add(temp);
                }
                gameData.getMap().add(gameData.getEachRow());
                //map.add(eachrow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Responsible for setting up the game
     */
    public void startGame() {
        Paku paku = gameData.getPaku(); //retrieve singleton Paku Object
        paku.setGameData(gameData); //giving Paku a reference to gameData
        spawnGhosts();

        paku.setMap(gameData.getMap());  //give Paku a reference to the game map --Evan 10/30

       // score = new Score();  //new score object created each game UPDATE 10/29 no longer creating new score object each game --Evan
        gameData.setGameStatus(GameStatus.staring);  //update gameStatus
    }

    /*
    public int increaseGhostSpeed(int ghostSpeed) {
        return (int) Math.pow(gamelevel, ghostSpeedToThePower);
    }
    */


    /**
     * creates the four ghosts for gameplay
     */
    public void spawnGhosts()
    {
        List<Ghost> ghostList = gameData.getGhostList();

        ghostList.add(new Blaine(gameData.getMap())); //orange
        ghostList.add(new Kinky(gameData.getMap())); //blue
        Stinky stinky = new Stinky(gameData.getMap());
        ghostList.add(stinky); //red
        ghostList.add(new Hinky(stinky, gameData.getMap())); //pink, Hinky needs Stinky's info to move. please do not modify

        gameData.setGhostList(ghostList);
        setGhostGameDataReference();  //probably isn't needed --Evan 10/29
    }


    /**
     * Passes the gameData object to the ghosts so that they each have a reference to it so that they can update it
     *
     * UPDATE 10/29 probably obsolete now --Evan
     */
    private void setGhostGameDataReference()
    {
        List<Ghost> ghostList = gameData.getGhostList();
        for(Ghost ghost : ghostList)
        {
            ghost.setGameData(gameData);
        }
    }

    public void respawn()
    {
        List<Ghost> ghostList = gameData.getGhostList();
        Paku paku = gameData.getPaku();
        paku.resetLocation();
        for(Ghost ghost : ghostList){
            ghost.resetLocation();
        }
        ghostList.get(0).resetMultiplier();
    }



    //TODO: need method to retrieve data at game startup

    /**
     * Resets game
     */
    public void resetGame()
    {
        List<Ghost> ghostList = gameData.getGhostList();
        gameData.getPaku().resetPaku();
        for(Ghost ghost : ghostList){
            ghost.resetLocation();
        }
        ghostList.get(0).resetMultiplier();
        int gamelevel = 1;
        gameData.setGamelevel(gamelevel);

        int extraLives = 1;
        gameData.setExtraLives(extraLives);

        //tells the Score class to store the current score int the score list for high score tracking purposes, then resets current score to 0
        gameData.getScore().reset();

    }

    private void nextLevel() {
        List<Ghost> ghostList = gameData.getGhostList();
        Paku paku = gameData.getPaku();
        paku.resetLocation();
        for(Ghost ghost : ghostList){
            ghost.resetLocation();
        }
        ghostList.get(0).resetMultiplier();
        int gameLevel = gameData.getGamelevel();
        gameLevel = gameLevel++;
        LoadMap();
        gameData.setGamelevel(gameLevel);
    }



    //Called every frame(or whenever timer ticks)
    public void update(){

        Controls input = getUserInput();
        if(input != Controls.escape && input != Controls.O && input != Controls.enter)
        {
            pakuUpdate((input.castToDir(input)));
        }
        dataToSend = gameData.getData();
    }

    //talks to frontend, return input enum
    public void receivedUserInput(String userInput) {
        this.userInput = Controls.getControl(userInput);
    }

    private Controls getUserInput(){
        if(this.userInput == null){
            return Controls.none;
        }
        return this.userInput;
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

    private void pakuUpdate(Direction input)
    {
        Paku paku = gameData.getPaku();
        int pointsPerLife = gameData.getPointsPerLife();

        pakuMove(input);
        if(collideWithGhost())
        {
            collideWithGhostProtocol();
            if(gameData.getGameStatus() == GameStatus.GameOver)
                return;
        }
        else{
            ghostsMove();
        }
        if(collideWithGhost())
        {
            collideWithGhostProtocol();
            if(gameData.getGameStatus() == GameStatus.GameOver)
                return;
            else
            {
                respawn();
            }
        }
        
        pakuEatsDots(paku.getLoc());
        if(gameData.getCurrentScore() > (pointsPerLife * gameData.getExtraLives()))
        {
            gameData.setExtraLives(2);
            paku.addLife();
        }
        if(!gameData.getMap().contains(1) && !gameData.getMap().contains(3))
          {
              gameData.setGameStatus(GameStatus.nextLevel); //update gameStatus to next level
              nextLevel();
          }
    }

    private void pakuEatsDots(Location location)
    {
        //map.get(location.getxLoc()).get(location.getyLoc());
        ArrayList<ArrayList> map = gameData.getMap();
        ArrayList row = map.get(location.getyLoc());
        List<Ghost> ghostList = gameData.getGhostList();
        int tile = (int)row.get(location.getxLoc());
        if(tile == 1)
        {
            gameData.getScore().addScore(POINTS_PER_DOT); //add score for eating dot to current score
            row.set(location.getxLoc(), 2);
            map.set(location.getyLoc(), row);
            gameData.setMap(map);
        }
        if(tile == 3)
        {
            for(Ghost ghost : ghostList)
            {
                if(!ghost.getState().equals(GhostState.eaten))
                {
                    ghost.setState(GhostState.flee);
                }
            }
            gameData.getScore().addScore(POINTS_PER_SUPER_DOT);
            row.set(location.getxLoc(), 2);
            map.set(location.getyLoc(), row);
            gameData.setMap(map);
        }
        if(tile == 5)
        {
            gameData.getScore().addScore(gameData.getFruit().eatFruit());
            row.set(location.getxLoc(), 2);
            map.set(location.getyLoc(), row);
            gameData.setMap(map);
            gameData.setFruit(null);
        }
    }

    /**
     * TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    private void collideWithGhostProtocol() {
        boolean death = false;
        List<Ghost> ghostList = gameData.getGhostList();
        Paku paku = gameData.getPaku();
        Score score = gameData.getScore();
        GameStatus gameStatus = gameData.getGameStatus();
        for(Ghost ghost : ghostList){

            if(!ghost.getState().equals(GhostState.flee) || !ghost.getState().equals(GhostState.eaten)) {
                if(paku.getLoc().getxLoc() == ghost.getLoc().getxLoc() && paku.getLoc().getyLoc() == ghost.getLoc().getyLoc()) {
                    paku.substractLife();
                    death = true;
                }
            }
            if(ghost.getState().equals(GhostState.flee)) {
                ghost.addScore(score);
            }
        }
        if(paku.isGameOver()){
            gameStatus = GameStatus.GameOver;
            gameData.setGameStatus(gameStatus);
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
    private void ghostsMove()
    {
        List<Ghost> ghostList = gameData.getGhostList();
        boolean fleeing = false;
        for (Ghost ghost: ghostList) {
            ghost.move();
            if(ghost.getState().equals(GhostState.flee) || ghost.getState().equals(GhostState.eaten))
                fleeing = true;
        }
        if(!fleeing)
            ghostList.get(0).resetMultiplier();
    }
    /**
     * checks whether paku collided with ghost
     * @return
     */
    private boolean collideWithGhost()
    {
        Paku paku = gameData.getPaku();
        List<Ghost> ghostList = gameData.getGhostList();
        for(Ghost ghost : ghostList){
           if(!ghost.getState().equals(GhostState.eaten)) {
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
        Direction inputDirection = gameData.getInputDirection(); //get latest direction inputted from keyboard
        Paku paku = gameData.getPaku();  //get singleton Paku reference
        if(!input.equals(Direction.stay))
            if(!inputDirection.equals(input) || !inputDirection.equals(Direction.stay)) {
                paku.setDir(input); //change paku's facingDirection if the given input is different than Paku's current facing direction
                gameData.setInputDirection(input);
                //inputDirection = input;
            }
        paku.move(); //tell paku to move in the given direction

    }
    private void spawnFruit()
    {
        Fruit fruit = gameData.getFruit();
        int gamelevel = gameData.getGamelevel();
        ArrayList<ArrayList> map = gameData.getMap();

        if(fruit == null)
        {
            fruit = new Fruit(gamelevel);
            ArrayList row = map.get(24);
            row.set(14, 5);
            map.set(24, row);
            gameData.setFruit(fruit);
            gameData.setMap(map); //to update the map in gameData ?? --Evan
        }
    }


    /**
     * Adds currentScore to the list of scores (used to keep track of high scores)
     */
    private void archiveScore()
    {
       // scoreList.add(currentScore);
    }


    /**
     * Finds the highest currentScore in the list and updates highSore
     */
    private void determineHighScore()
    {
        //for(int i = 0; i < scorelist.size(); i++)
        //{
        //    if(scorelist.get(i) > highScore)
        //        highScore = scorelist.get(i);
        //}

    }

    public JSONObject getDataToSend() {
        return dataToSend;

    }


}
