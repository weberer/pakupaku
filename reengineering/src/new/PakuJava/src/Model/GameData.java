package Model;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class holds all data to be sent to UI at any given time
 */

public class GameData
{

    private List<Ghost> ghostList;
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

    //map tile numbers
    private final int WALL_CODE = 0;
    private final int DOT_CODE = 1;
    private final int HALL_CODE = 2;
    private final int LARGEDOT_CODE = 3;

    private Paku paku;
    private int gamelevel;
    private GameStatus gameStatus;

    private final double ghostSpeed = 10;

    private Score score;

    private int frame;  //the number of the current frame
    private Direction inputDirection;
    private Fruit fruit = null;

    //private JSONObject jo = new JSONObject();  //rethinking this, replacing it with GameData object class --Evan

    private ArrayList<ArrayList> map;
    private ArrayList<Integer> eachRow;

    // used for checking the score based extra lives.
    private int extraLives;

    private final String SAMPLE_CSV_FILE_PATH = "../../../PakuJava/src/asset/map.csv";
    private static GameData data = new GameData();  //to make this class a Singleton

    private Location pakuLoc; //x, y location of the paku

    // The ghost locations
    private Location blaineLoc;
    private Location stinkyLoc;
    private Location hinkyLoc;
    private Location kinkyLoc;



    JSONObject dataToSend;

    private GameData()
    {
        paku = Paku.getInstance();
        inputDirection = Direction.stay;
        dataToSend = new JSONObject();

        score = new Score();


        ghostList = new ArrayList<Ghost>();
        gamelevel = 0;
        map = new ArrayList<ArrayList>();
        eachRow = new ArrayList<Integer>();


    }

    public void createObject()
    {
        try {
            String gameStatusToSend = GameStatus.getStatusUI(this.gameStatus);
            dataToSend.put("game_state", gameStatusToSend);

            //int highScoreToSend = getHighScore();
           // dataToSend.put("score", highScoreToSend);

            int scoreToSend = getCurrentScore();
            dataToSend.put("score", scoreToSend);

            dataToSend.put("sound", true);

            JSONArray mapJS = new JSONArray();
            map.forEach((eachrowAL) -> {
                mapJS.put(new JSONArray(Arrays.asList(eachrowAL)));
            });

//TODO: (Board is the map) mao is a 2d arraylist, put the 2d arraylist into 2D Json Array
            //todo this, convert arraylist to 2D collection, put collection into 2dJson Aray


            //Collection board = ;

            dataToSend.put("board", mapJS);



            JSONObject locationToSend = new JSONObject();
            locationToSend.put("x", getPakuLoc().getxLoc());
            locationToSend.put("y", getPakuLoc().getyLoc());

            JSONObject pakuToSend = new JSONObject();
            pakuToSend.put("location", locationToSend);
            //pakuToSend.put("direction", pakuDir());

            dataToSend.put("paku", pakuToSend);

            JSONObject stinkyToSend = new JSONObject();
            stinkyToSend.put("location", getStinkyLoc());
           // stinkyToSend.put("state", getStinkyState());

            JSONObject hinkyToSend = new JSONObject();
            hinkyToSend.put("location", getHinkyLoc());
           // hinkyToSend.put("state", getHinkyState());

            JSONObject kinkyToSend = new JSONObject();
            kinkyToSend.put("location", getKinkyLoc());
            //kinkyToSend.put("state", getKinkyState());

            JSONObject blaineToSend = new JSONObject();
            blaineToSend.put("location", getBlaineLoc());
            //blaineToSend.put("state", getBlaineState());

            JSONObject ghostsToSend = new JSONObject();
            ghostsToSend.put("stinky", stinkyToSend);
            ghostsToSend.put("hinky", hinkyToSend);
            ghostsToSend.put("kinky", kinkyToSend);
            ghostsToSend.put("blaine", blaineToSend);

            dataToSend.put("ghosts", ghostsToSend);
        }

        catch (JSONException ex)
        {
            System.out.println(ex);
        }

    }

    public JSONObject getData()
    {
        createObject();
        return dataToSend;
    }

    public static GameData getInstance()
    {
        return data;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void setPakuLoc(Location pakuLoc) {
        this.pakuLoc = pakuLoc;
    }

    public void setHinkyLocation(Location loc)
    {
        this.hinkyLoc = loc;
    }

    public void setStinkyLocation(Location loc)
    {
        this.stinkyLoc = loc;
    }

    public void setKinkyLocation(Location loc)
    {
        this.kinkyLoc = loc;
    }

    public void setBlaineLocation(Location loc)
    {
        this.blaineLoc = loc;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Location getPakuLoc() {
        return paku.getLoc();
    }

    public Location getBlaineLoc() {
        return ghostList.get(0).getLoc();
    }

    public Location getStinkyLoc() {
        return ghostList.get(2).getLoc();
    }

    public Location getHinkyLoc() {
        return ghostList.get(1).getLoc();
    }

    public Location getKinkyLoc() {
        return ghostList.get(3).getLoc();
    }

    public int getCurrentScore() {
        return score.getCurrentScore();
    }



    public Paku getPaku() {
        return paku;
    }

    public void setPaku(Paku paku) {
        this.paku = paku;
    }

    public int getGamelevel() {
        return gamelevel;
    }

    public void setGamelevel(int gamelevel) {
        this.gamelevel = gamelevel;
    }

    public double getGhostSpeed() {
        return ghostSpeed;
    }

    public int getHighScore() {
        return score.getHighScore();
    }

    public Score getScore() {
        return score;
    }

    public List<Integer> getScoreList() {
        return score.getScoreList();
    }



    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public Direction getInputDirection() {
        return inputDirection;
    }

    public void setInputDirection(Direction inputDirection) {
        this.inputDirection = inputDirection;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public ArrayList<ArrayList> getMap() {
        return map;
    }

    public void setMap(ArrayList<ArrayList> map) {
        this.map = map;
    }

    public ArrayList<Integer> getEachRow() {
        return eachRow;
    }

    public void setEachRow(ArrayList<Integer> eachRow) {
        this.eachRow = eachRow;
    }


    public List<Ghost> getGhostList() {
        return ghostList;
    }

    public void setGhostList(List<Ghost> ghostList) {
        this.ghostList = ghostList;
    }

    public int getPointsPerLife() {
        return pointsPerLife;
    }

    public int getExtraLives() {
        return extraLives;
    }

    public void setExtraLives(int extraLives) {
        this.extraLives = extraLives;
    }

    public int getWALL_CODE() {
        return WALL_CODE;
    }

    public int getDOT_CODE() {
        return DOT_CODE;
    }

    public int getHALL_CODE() {
        return HALL_CODE;
    }

    public int getLARGEDOT_CODE() {
        return LARGEDOT_CODE;
    }
}
