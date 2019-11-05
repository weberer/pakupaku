package Model;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.*;


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
    private final int FRUIT_CODE = 5;  // put a five to indicate location of a Fruit

    private Paku paku;
    private int gamelevel;
    private GameStatus gameStatus;
    private int currentFrame;

    private final double ghostSpeed = 10;

    private Score score;

    private int frame;  //the number of the current frame
    private Direction inputDirection;
    private Fruit fruit = null;
    private int bonus;
    private int[] fruitArray;
    //private JSONObject jo = new JSONObject();  //rethinking this, replacing it with GameData object class --Evan

    private ArrayList<ArrayList> map;
    private ArrayList<Integer> eachRow;

    // used for checking the score based extra lives.
    private int extraLives;


    ///DO NOT DO NOT DO NOT MODIFY
    //private final String SAMPLE_CSV_FILE_PATH = "../../../PakuJava/src/asset/map.csv";///DO NOT DO NOT DO NOT MODIFY
    // private final String SAMPLE_CSV_FILE_PATH = "src\\asset\\map.csv";///DO NOT DO NOT DO NOT MODIFY
    private final String SAMPLE_CSV_FILE_PATH = "c:\\users\\weber\\desktop\\firebreathingrubberduckies\\reengineering\\src\\new\\pakujava\\src\\asset\\map.csv";
    //private final String SAMPLE_CSV_FILE_PATH = "J:\\CSSE\\se\\se3860\\firebreathingrubberduckies\\reengineering\\src\\new\\PakuJava\\src\\asset\\map.csv";


    private static GameData data = new GameData();  //to make this class a Singleton

    private Location pakuLoc; //x, y location of the paku

    private boolean blaineBlink;
    private boolean stinkyBlink;
    private boolean hinkyBlink;
    private boolean kinkyBlink;

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

        currentFrame = -1;
    }

    public boolean isBlaineBlink() {
        return blaineBlink;
    }

    public boolean isStinkyBlink() {
        return stinkyBlink;
    }

    public boolean isHinkyBlink() {
        return hinkyBlink;
    }

    public boolean isKinkyBlink() {
        return kinkyBlink;
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
            //this.correctMap();
            ArrayList<ArrayList> mapToSend = new ArrayList<>();
            for(int i = 0; i < map.size(); i++)
            {
                ArrayList<Integer> row = new ArrayList<>();
                for(int j = 0; j < map.get(i).size(); j++)
                {
                    row.add((int)map.get(i).get(j));
                }
                mapToSend.add(row);
            }
            //mapToSend = (ArrayList<ArrayList>) map.clone();
            mapToSend.remove(mapToSend.size()-1);
            mapToSend.remove(0);


//2 to 0 3 to 1.
            mapToSend.forEach((eachrowAL) -> {
                eachrowAL.remove(eachrowAL.size()-1);
                eachrowAL.remove(0);
                eachrowAL.forEach((eachLoc)->{
                    if((int)eachLoc == 2)
                        eachrowAL.set(eachrowAL.indexOf(eachLoc), 0);
                    if((int)eachLoc == 3)
                        eachrowAL.set(eachrowAL.indexOf(eachLoc), 1);
                });
            });

            JSONArray mapJS = new JSONArray();
            mapToSend.forEach((eachrowAL) -> {
                mapJS.put(new JSONArray(Arrays.asList(eachrowAL)));
            });

//TODO: (Board is the map) mao is a 2d arraylist, put the 2d arraylist into 2D Json Array
            //todo this, convert arraylist to 2D collection, put collection into 2dJson Aray


            //Collection board = ;

            dataToSend.put("board", mapJS);

            JSONArray fruitJS = new JSONArray();
            for(int i : fruitArray)
            {
                fruitJS.put(i);
            }
            dataToSend.put("fruitList", fruitJS);

            dataToSend.put("bonus", bonus);
            bonus = 0;

            JSONObject pakuLocationToSend = new JSONObject();
            pakuLocationToSend.put("x", getPakuLoc().getxLoc());
            pakuLocationToSend.put("y", getPakuLoc().getyLoc());
            pakuLocationToSend.put("direction", paku.getFacingDirection().toString());


            JSONObject pakuToSend = new JSONObject();
            pakuToSend.put("location", pakuLocationToSend);
            //pakuToSend.put("direction", pakuDir());

            dataToSend.put("paku", pakuToSend);




            JSONObject stinkyLocationToSend = new JSONObject();
            stinkyLocationToSend.put("x", getStinky().getLoc().getxLoc());
            stinkyLocationToSend.put("y", getStinky().getLoc().getyLoc());
            stinkyLocationToSend.put("direction", getStinky().getFacingDirection().toString());

            JSONObject stinkyToSend = new JSONObject();
            stinkyToSend.put("location", stinkyLocationToSend);
            stinkyToSend.put("ghost_state", GhostState.castState(getStinky().getState()));
            stinkyToSend.put("blinking", isStinkyBlink());

            JSONObject hinkyLocationToSend = new JSONObject();
            hinkyLocationToSend.put("x", getHinky().getLoc().getxLoc());
            hinkyLocationToSend.put("y", getHinky().getLoc().getyLoc());
            hinkyLocationToSend.put("direction", getHinky().getFacingDirection().toString());

            JSONObject hinkyToSend = new JSONObject();
            hinkyToSend.put("location", hinkyLocationToSend);
            hinkyToSend.put("ghost_state", GhostState.castState(getHinky().getState()));
            hinkyToSend.put("blinking", isHinkyBlink());

            JSONObject kinkyLocationToSend = new JSONObject();
            kinkyLocationToSend.put("x", getKinky().getLoc().getxLoc());
            kinkyLocationToSend.put("y", getKinky().getLoc().getyLoc());
            kinkyLocationToSend.put("direction", getKinky().getFacingDirection().toString());

            JSONObject kinkyToSend = new JSONObject();
            kinkyToSend.put("location", kinkyLocationToSend);
            kinkyToSend.put("ghost_state", GhostState.castState(getKinky().getState()));
            kinkyToSend.put("blinking", isKinkyBlink());

            JSONObject blaineLocationToSend = new JSONObject();
            blaineLocationToSend.put("x", getBlaine().getLoc().getxLoc());
            blaineLocationToSend.put("y", getBlaine().getLoc().getyLoc());
            blaineLocationToSend.put("direction", getBlaine().getFacingDirection().toString());

            JSONObject blaineToSend = new JSONObject();
            blaineToSend.put("location", blaineLocationToSend);
            blaineToSend.put("ghost_state", GhostState.castState(getBlaine().getState()));
            blaineToSend.put("blinking", isBlaineBlink());

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

    public Ghost getBlaine() {
        return ghostList.get(0);
    }

    public Ghost getStinky() {
        return ghostList.get(2);
    }

    public Ghost getHinky() {
        return ghostList.get(1);
    }

    public Ghost getKinky() {
        return ghostList.get(3);
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
        return score.getHighScore().getValue();
    }

    /**
     * Returns the high score with its corresponding player intitials
     * @return
     */
    public Map.Entry<String, Integer> getPlayerHighScore() {
        return score.getHighScore();
    }

    public Score getScore() {
        return score;
    }

    /*
    public List<Integer> getScoreList() {
        return score.getScoreList();
    }
    */

    public HashMap<String ,Integer> getScoreList()
    {
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

    public void correctMap(){
        try {
            for (int i = 31; i < 62; i++)
                map.remove(i);
        }
        catch(Exception e){

        }
    }

    public ArrayList<Integer> getEachRow() {
        return eachRow;
    }
    public void  resetEachRow()
    {
        eachRow = new ArrayList<>();
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

    public int getFRUIT_CODE() {
        return FRUIT_CODE;
    }


    public String getSAMPLE_CSV_FILE_PATH() {
        return SAMPLE_CSV_FILE_PATH;
    }

    public void setBlaineBlink(boolean blaineBlink) {
        this.blaineBlink = blaineBlink;
    }

    public void setStinkyBlink(boolean stinkyBlink) {
        this.stinkyBlink = stinkyBlink;
    }

    public void setHinkyBlink(boolean hinkyBlink) {
        this.hinkyBlink = hinkyBlink;
    }

    public void setKinkyBlink(boolean kinkyBlink) {
        this.kinkyBlink = kinkyBlink;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
    public int[] getFruitArray() {
        return fruitArray;
    }

    public void setFruitArray(int[] fruitArray) {
        this.fruitArray = fruitArray;
    }

    public boolean checkForDot() {
        ArrayList<Integer> eachrow = new ArrayList<Integer>();
        return eachrow.contains(1);
    }

    public boolean checkForSuperDot() {
        ArrayList<Integer> eachrow = new ArrayList<Integer>();
        return eachrow.contains(3);
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }
}
