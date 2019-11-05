package Controller;
import Model.Ghost;
import Model.Paku;
import Model.GameStatus;
import Model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.IOException;
import com.opencsv.CSVReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.json.JSONArray;
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
    private int currentFrame;
    private final int BLINK = 50; //used to determine when the ghosts blink, they blink two to four times currently.
    private int blinkCounter;

    /**
     * GameController constructor
     */
    public GameController()
    {
        gameData = GameData.getInstance();  //INSTANTIATION OF GAMEDATA OBJECT

        if(gameData.getMap().isEmpty())
            LoadMap();
        gameData.setGameStatus(GameStatus.mainMenu);
      //  startGame();   //this method is already called from the Program class --Evan
        currentFrame = -1;
    }

    /**
     * Loads the PakuPaku map, in csv format
     */
    public void LoadMap()
    {
        // For showing the dictionary. do not remove.
        //File file = new File(".");
        //for(String fileNames : file.list()) System.out.println(fileNames);

        //why are parentheses used here instead of braces??? --Evan 10/30
        try (
                Reader reader = Files.newBufferedReader(Paths.get(gameData.getSAMPLE_CSV_FILE_PATH()));
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
                gameData.resetEachRow();
                //map.add(eachrow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * TODO: Utilize
     * @param frameNumber
     * @return
     */
   public void receivedFrame(int frameNumber) {
        gameData.setCurrentFrame(frameNumber);
    }




    /**
     * Responsible for setting up the game
     * TODO: The method for calling update: there is a frame variable in gameData that needs to be updated; the
     *
     * TODO: we need a way so that every time frame is updated, update is called, but we cannot use a while loop
     *
     * TODO: event listener, so that every time frame changes (the one in game controller), we are going to call update()
     * frame in gameData needs to be updated (AKA it needs to match the frame value in gameController)
     *
     * ToDO:
     *
     *
     * once game has started, keep calling
     *
     * TODO:
     */
    public void startGame() {
        Paku paku = gameData.getPaku(); //retrieve singleton Paku Object

        blinkCounter = 0;

        paku.setGameData(gameData); //giving Paku a reference to gameData
        spawnGhosts();

        paku.setMap(gameData.getMap());  //give Paku a reference to the game map --Evan 10/30

       // score = new Score();  //new score object created each game UPDATE 10/29 no longer creating new score object each game --Evan
        gameData.setGameStatus(GameStatus.staring);  //update gameStatus
        //update();
    }



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
        gameData.getGhostList().get(0).setupTimers(); // Was Ghost.setupTimers() --Eric 11/4
        setGhostGameDataReference();  //probably isn't needed --Evan 10/29
        int[] fruits = new int[8];
        fruits[7] = 1;
        gameData.setFruitArray(fruits);
        for(Ghost ghost : ghostList)
        {
            ghost.startTimer();
        }

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

    /**
     * Puts Paku and ghosts back in their starting positions after Paku has been eaten
     */
    public void respawn()
    {
        List<Ghost> ghostList = gameData.getGhostList();
        Paku paku = gameData.getPaku();
        paku.resetLocation();
        resetGhosts(ghostList);
        blinkCounter = BLINK;
    }

    private void resetGhosts(List<Ghost> ghostList) {
        for(Ghost ghost : ghostList){
            ghost.resetLocation();
            if(ghost.getState().equals(GhostState.flee))
                ghost.endingFleeProtocol();
            ghost.startTimer();
        }
        ghostList.get(0).resetMultiplier(); // was Ghost.resetMultiplier(); --Eric 11/4
    }


    /**
     * Resets game
     */
    public void resetGame()
    {
        List<Ghost> ghostList = gameData.getGhostList();

        if(ghostList.size() > 0) { // if ghostList is of size 0, then the game has not yet started.
            gameData.getPaku().resetPaku();

            resetGhosts(ghostList); // New

            for(Ghost ghost : ghostList){
                ghost.resetLocation();
                ghost.startTimer();
            }
            ghostList.get(0).resetMultiplier();
        }
        int gamelevel = 0;
        gameData.setGamelevel(gamelevel);

        int extraLives = 1;
        gameData.setExtraLives(extraLives);

        //tells the Score class to store the current score int the score list for high score tracking purposes, then resets current score to 0
        gameData.getScore().reset();

        blinkCounter = BLINK;
    }

    /**
     * Handles setting up the game for the next level
     */
    public void nextLevel() {
        List<Ghost> ghostList = gameData.getGhostList();
        Paku paku = gameData.getPaku();
        paku.resetLocation();
        resetGhosts(ghostList);
        blinkCounter = BLINK;
        int gameLevel = gameData.getGamelevel() + 1;
        LoadMap();
        gameData.setGamelevel(gameLevel);
        int[] fruitArray = new int[8];
        if(gameLevel < 3)
        {
            int test = gameLevel + 1;
            //int i = fruitArray.length;
            int i = fruitArray.length - 1;
            while (test != 0)
            {
                fruitArray[i] = test;
                test--;
                i--;
            }
        }
        else if(gameLevel < 4)
        {
            int test = gameLevel;
            int i = fruitArray.length;
            while (test != 0)
            {
                fruitArray[i] = test;
                test--;
                i--;
            }
        }
        else if(gameLevel < 6)
        {
            int test = 4;
            int i = fruitArray.length;
            while (test != 0)
            {
                fruitArray[i] = test;
                test--;
                i--;
            }
        }
        else if(gameLevel < 8)
        {
            int test = 5;
            int i = fruitArray.length;
            while (test != 0)
            {
                fruitArray[i] = test;
                test--;
                i--;
            }
        }
        else if(gameLevel < 10)
        {
            int test = 6;
            int i = fruitArray.length;
            while (test != 0)
            {
                fruitArray[i] = test;
                test--;
                i--;
            }
        }

        else if(gameLevel < 12)
        {
            int test = 7;
            int i = fruitArray.length;
            while (test != 0)
            {
                fruitArray[i] = test;
                test--;
                i--;
            }
        }
        else
        {
            int test = 8;
            int i = fruitArray.length;
            while (test != 0)
            {
                fruitArray[i] = test;
                test--;
                i--;
            }
        }
        update();
    }


    /**
     * Called every frame(or whenever timer ticks)
     */
    public void update(){

        Controls input = getUserInput();
        dataToSend = gameData.getData();
        if(input != Controls.escape && input != Controls.O && input
                != Controls.enter && !gameData.getGameStatus().equals(GameStatus.mainMenu))
        {
            pakuUpdate((Controls.castToDir(input)));
        }
        dataToSend = gameData.getData();
    }

    //talks to frontend, return input enum
    public void receivedUserInput(String userInput) {
        this.userInput = Controls.getControl(userInput);
    }

    public Controls getUserInput() {
        if(this.userInput == null) {
            return Controls.none;
        }
        return this.userInput;
    }

    /**
     * Updates Paku: checks if it has collided with a ghost, if not, Paku moves
     * @param input
     */
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
        if(gameData.checkForDot() && gameData.checkForSuperDot())
          {
              gameData.setGameStatus(GameStatus.nextLevel); //update gameStatus to next level
              nextLevel();
          }
    }

    /**
     * Handles scoring and map modificaiton for the eating of dots and fruit
     * @param location
     */
    private void pakuEatsDots(Location location)
    {
        ArrayList<ArrayList> map = gameData.getMap();

        ArrayList row = map.get(location.getyLoc());

        List<Ghost> ghostList = gameData.getGhostList();
        int tile = (int)row.get(location.getxLoc());
        if(tile == gameData.getDOT_CODE())  //regular dot
        {
            gameData.getScore().addScore(POINTS_PER_DOT); //add score for eating dot to current score
            row.set(location.getxLoc(), 2);
            map.set(location.getyLoc(), row);
            gameData.setMap(map);
        }
        if(tile == gameData.getLARGEDOT_CODE())  //super (large) dot
        {
            for(Ghost ghost : ghostList)
            {
                if(!ghost.getState().equals(GhostState.eaten))
                {
                    ghost.makeFlee();
                }

            }
            Ghost.startGlobalFleeCounter();
            gameData.getScore().addScore(POINTS_PER_SUPER_DOT);
            row.set(location.getxLoc(), 2);
            map.set(location.getyLoc(), row);
            gameData.setMap(map);
        }
        if(tile == gameData.getFRUIT_CODE())  //fruit
        {
            gameData.getScore().addScore(gameData.getFruit().getScore());
            gameData.setBonus(gameData.getFruit().getScore());
            row.set(location.getxLoc(), 2);
            map.set(location.getyLoc(), row);
            gameData.setMap(map);
            gameData.setFruit(null);
        }
    }

    /**
     *
     */
    private void collideWithGhostProtocol() {
        boolean death = false;
        List<Ghost> ghostList = gameData.getGhostList();
        Paku paku = gameData.getPaku();
        Score score = gameData.getScore();
        GameStatus gameStatus;// = gameData.getGameStatus();
        for(Ghost ghost : ghostList){
            if(!ghost.getState().equals(GhostState.flee) || !ghost.getState().equals(GhostState.eaten)) {
                if(paku.getLoc().getxLoc() == ghost.getLoc().getxLoc() && paku.getLoc().getyLoc() == ghost.getLoc().getyLoc()) {
                    paku.substractLife();
                    death = true;
                }
            }
            if(ghost.getState().equals(GhostState.flee)) {
                gameData.setBonus(ghost.addScore(score));
                ghost.endingFleeProtocol();

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
     * Calls each ghost's move method, which updates the ghost's position.
     *
     */
    private void ghostsMove()
    {
        List<Ghost> ghostList = gameData.getGhostList();
        for (Ghost ghost: ghostList) {
            ghost.move();
        }
        checkFlee(ghostList);

    }

    /**
     * Handles the Flee state counters on the ghosts, and will set them to blink when .
     * @param ghostList the list of ghosts from gameData
     */
    private void checkFlee(List<Ghost> ghostList)
    {
        if(Ghost.getGlobalFleeCounter() > 0)
        {
            Ghost.decrementGlobalFleeCounter();
            if(Ghost.isBlinking())
            {
                for(Ghost ghost: ghostList)
                {
                    if(ghost.getState().equals(GhostState.flee))
                    {
                        ghost.blink();
                    }
                }
            }

        }
        else if(Ghost.getGlobalFleeCounter() == 0)
        {
            Ghost.resetMultiplier();
            for(Ghost ghost: ghostList)
            {
                if(ghost.getState().equals(GhostState.flee))
                {
                    ghost.endingFleeProtocol();
                }
            }
            Ghost.decrementGlobalFleeCounter();

        }
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


    public JSONObject getDataToSend() {
        return dataToSend;
    }

    public JSONObject getHighScore() throws Exception {
        int score = gameData.getHighScore();
        JSONObject obj = new JSONObject();
        obj.put("highscore", score);
        return obj;
    }

    public JSONObject getHighScoreList() throws Exception {
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
      //  List<Integer> list = gameData.getScoreList();
        HashMap<String, Integer> scoreMap = gameData.getScoreList();


        //put map entries, which consist of scores with their corresponding player initials, into the JSONArray
        for(Map.Entry<String, Integer> entry : scoreMap.entrySet())
            arr.put(entry);
        obj.put("highscore_list", arr);

        /*
        for(Integer i : list)
            arr.put(i);
        obj.put("highscore_list", arr);
        */

        return obj;
    }

    public String getGameStatus() {
        return GameStatus.getStatusUI(gameData.getGameStatus());
    }
}
