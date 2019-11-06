package Controller;
import Model.Ghost;
import Model.Paku;
import Model.GameStatus;
import Model.*;

import java.nio.file.Path;
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
        java.nio.file.Path relpath = java.nio.file.Paths.get(gameData.getSAMPLE_CSV_FILE_PATH());
        String path = relpath.toAbsolutePath().toString();

        if(!gameData.getMap().isEmpty())
            gameData.getMap().clear();

        try (
                Reader reader = Files.newBufferedReader(relpath.toAbsolutePath());
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
     * Responsible for setting up the game, including spawning ghosts, seting game status, and
     * ininializing fruit array
     */
    public void startGame() {
        Paku paku = gameData.getPaku(); //retrieve singleton Paku Object

        paku.setGameData(gameData); //giving Paku a reference to gameData
        spawnGhosts();

        paku.setMap(gameData.getMap());  //give Paku a reference to the game map --Evan 10/30

       // score = new Score();  //new score object created each game UPDATE 10/29 no longer creating new score object each game --Evan
        gameData.setGameStatus(GameStatus.staring);  //update gameStatus
        //update();
        if(gameData.getDots() != gameData.getStartingDots())
            gameData.resetDots();
        int[] fruits = new int[8];
        fruits[7] = 1;
        gameData.setFruitArray(fruits);
    }



    /**
     * creates the four ghosts for gameplay
     */
    public void spawnGhosts()
    {
        List<Ghost> ghostList = gameData.getGhostList();
        if(ghostList.size() > 0)
        {
            ghostList.clear();
        }
        ghostList.add(new Blaine(gameData.getMap())); //orange
        ghostList.add(new Kinky(gameData.getMap())); //Pink
        Stinky stinky = new Stinky(gameData.getMap());
        ghostList.add(stinky); //red
        ghostList.add(new Hinky(stinky, gameData.getMap())); //Blue, Hinky needs Stinky's info to move. please do not modify

        gameData.setGhostList(ghostList);
        gameData.getGhostList().get(0).setupTimers(); // Was Ghost.setupTimers() --Eric 11/4

        for(Ghost ghost : ghostList)
        {
            ghost.startTimer();
        }

    }


    /**
     * Puts Paku and ghosts back in their starting positions after Paku has been eaten
     */
    public void respawn()
    {
        List<Ghost> ghostList = gameData.getGhostList();
        Paku paku = gameData.getPaku();
        gameData.setFruit(null);
        paku.resetLocation();
        paku.setDir(Direction.left);
        resetGhosts(ghostList);
    }

    private void resetGhosts(List<Ghost> ghostList) {
        spawnGhosts();
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
            ghostList.get(0).resetMultiplier();
        }
        int gamelevel = 0;
        gameData.setGamelevel(gamelevel);
        LoadMap();
        int extraLives = 1;
        gameData.setExtraLives(extraLives);
        gameData.getPaku().setDir(Direction.left);
        //tells the Score class to store the current score int the score list for high score tracking purposes, then resets current score to 0
        gameData.getScore().reset();

        gameData.resetDots();
        LoadMap();
    }

    /**
     * Handles setting up the game for the next level
     */
    public void nextLevel() {
        List<Ghost> ghostList = gameData.getGhostList();
        Paku paku = gameData.getPaku();
        paku.resetLocation();
        resetGhosts(ghostList);
        int gameLevel = gameData.getGamelevel() + 1;
        LoadMap();
        paku.setDir(Direction.left);
        gameData.setGamelevel(gameLevel);
        gameData.resetDots();
        setUpFruitArray(gameLevel);
    }

    /**
     * Accessor to test the setupFruitArray method. used to testing
     */
    public void testFruitArray()
    {
        setUpFruitArray(5);
    }
    /**
     * Sets the fruit array based on the game level so that the screen displays the correct fruits
     * @param gameLevel
     */
    private void setUpFruitArray(int gameLevel)
    {
        int test;
        int[] fruitArray = new int[8];
        if(gameLevel < 3)
        {
            test = gameLevel + 1;
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
            test = gameLevel;
            int i = fruitArray.length - 1;
            while (test != 0)
            {
                fruitArray[i] = test;
                test--;
                i--;
            }
        }
        else if(gameLevel < 6)
        {
            test = 4;
            int i = fruitArray.length - 1;
            while (test > 0)
            {
                fruitArray[i] = test;
                test--;
                i--;
            }
        }
        else if(gameLevel < 8)
        {
            test = 5;
            int i = fruitArray.length - 1;
            while (test > 0)
            {
                fruitArray[i] = test;
                test--;
                i--;
            }
        }
        else if(gameLevel < 10)
        {
            test = 6;
            int i = fruitArray.length - 1;
            while (test != 0)
            {
                fruitArray[i] = test;
                test--;
                i--;
            }
        }

        else if(gameLevel < 12)
        {
            test = 7;
            int i = fruitArray.length - 1;
            while (test != 0)
            {
                fruitArray[i] = test;
                test--;
                i--;
            }
        }
        else
        {
            test = 8;
            int i = fruitArray.length - 1;
            while (test != 0)
            {
                fruitArray[i] = test;
                test--;
                i--;
            }
        }
        gameData.setFruitArray(fruitArray);
    }


    /**
     * Called every frame(or whenever timer ticks)
     */
    public void update(){

        Controls input = getUserInput();
        dataToSend = gameData.getData();
        if(gameData.getGameStatus().equals(GameStatus.pakuDiedButStillHasLifeLeft)  || gameData.getGameStatus().equals(GameStatus.nextLevel))
        {
            gameData.setGameStatus(GameStatus.play);
        }
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
            gameData.setExtraLives(gameData.getExtraLives() + 1);
            paku.addLife();
        }
        if(!gameData.dotsRemain()) //if map contains no more dots, go to next level
          {
              gameData.setGameStatus(GameStatus.nextLevel); //update gameStatus to next level
              nextLevel();
          }
    }

    /**
     * Handles scoring and map modification for the eating of dots and fruit
     * @param location
     */
    public void pakuEatsDots(Location location)
    {
        ArrayList<ArrayList> map = gameData.getMap(); //get copy of the map array

        ArrayList row = map.get(location.getyLoc()); //get the row based on Paku's Y location

        List<Ghost> ghostList = gameData.getGhostList(); //retrieve copy of ghost list
        int tile = (int)row.get(location.getxLoc());
        if(tile == gameData.getDOT_CODE())  //regular dot
        {
            gameData.getScore().addScore(POINTS_PER_DOT); //add score for eating dot to current score
            row.set(location.getxLoc(), gameData.getHALL_CODE()); //replace the dot in the map with a blank space
            map.set(location.getyLoc(), row); //put the updated row back into the map
            gameData.setMap(map); //update the map with the missing pellet
            gameData.setDots(gameData.getDots() - 1); //decrement number of dots
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
            row.set(location.getxLoc(), gameData.getHALL_CODE());
            map.set(location.getyLoc(), row);
            gameData.setMap(map);
            gameData.setDots(gameData.getDots() - 1);
        }
        if(tile == gameData.getFRUIT_CODE())  //fruit
        {
            if(gameData.getFruit() != null) {
                gameData.getScore().addScore(gameData.getFruit().getScore());
                gameData.setBonus(gameData.getFruit().getScore());
            }
            row.set(location.getxLoc(), 2);
            map.set(location.getyLoc(), row);
            gameData.setMap(map);
            gameData.setFruit(null);
        }
        if(gameData.fruitCheck())
        {
            spawnFruit();
        }
    }

    /**
     * Checks whether Paku has collided with a ghost and handles the event according to the ghost state
     */
    private void collideWithGhostProtocol() {
        boolean death = false;
        List<Ghost> ghostList = gameData.getGhostList();
        Paku paku = gameData.getPaku();
        Score score = gameData.getScore();
        GameStatus gameStatus;// = gameData.getGameStatus();

        //if ghosts are in their normal chase state, paku loses life
        for(Ghost ghost : ghostList){
            if(!ghost.getState().equals(GhostState.flee) && !ghost.getState().equals(GhostState.eaten)) {
                if(paku.getLoc().getxLoc() == ghost.getLoc().getxLoc() && paku.getLoc().getyLoc() == ghost.getLoc().getyLoc()) {
                    if(!death) {
                        paku.substractLife();
                        death = true;
                    }
                }
            }
            //paku eats ghost
            else if(ghost.getState().equals(GhostState.flee)) {
                if(paku.getLoc().getxLoc() == ghost.getLoc().getxLoc() && paku.getLoc().getyLoc() == ghost.getLoc().getyLoc()) {
                    gameData.setBonus(ghost.addScore(score));
                    ghost.endingFleeProtocol();
                }
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
    public boolean collideWithGhost()
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
    public void pakuMove(Direction input)
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

    /**
     * Creates the fruit object, the type of which depends on the game level. The fruit code is then inserted into
     * the map at the proper location
     */
    public void spawnFruit()
    {
        Fruit fruit = gameData.getFruit();
        int gamelevel = gameData.getGamelevel();
        ArrayList<ArrayList> map = gameData.getMap();

        if(fruit == null)
        {
            fruit = new Fruit(gamelevel);
            ArrayList row = map.get(17); //row where fruit spawn is to occur
            row.set(14, gameData.getFRUIT_CODE()); //put fruit into the map
            map.set(17, row);
            gameData.setFruit(fruit);
            gameData.setMap(map);
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
        HashMap<String, Integer> scoreMap = gameData.getScoreList();


        //put map entries, which consist of scores with their corresponding player initials, into the JSONArray
        for(Map.Entry<String, Integer> entry : scoreMap.entrySet())
            arr.put(entry);
        obj.put("highscore_list", arr);

        return obj;
    }

    public String getGameStatus() {
        return GameStatus.getStatusUI(gameData.getGameStatus());
    }
    public void testFruit()
    {
        spawnFruit();
    }
}
