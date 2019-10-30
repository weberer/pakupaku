package Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;


/**
 * This class holds all data to be sent to UI at any given time
 */

public class GameData
{

    private static GameData data = new GameData();  //to make this class a Singleton
    private GameStatus gameStatus;
    private Location pakuLoc; //x, y location of the paku

    // The ghost locations
    private Location blaineLoc;
    private Location stinkyLoc;
    private Location hinkyLoc;
    private Location kinkyLoc;

    private Score score;

    JSONObject dataToSend;

    private GameData()
    {
        dataToSend = new JSONObject();

    }

    public void createObject()
    {
        try {
            String gameStatusToSend = GameStatus.getStatusUI(this.gameStatus);
            dataToSend.put("game_state", gameStatusToSend);

            //int highScoreToSend = getHighScore();
           // dataToSend.put("score", highScoreToSend);

            int scoreToSend = getScore().getCurrentScore();
            dataToSend.put("score", scoreToSend);

            dataToSend.put("sound", true);


            //Collection board = ;
            //dataToSend.put("board", board);

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


        }

        catch (JSONException ex)
        {
            System.out.println(ex);
        }

    }

    public JSONObject getData()
    {

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
        return pakuLoc;
    }

    public Location getBlaineLoc() {
        return blaineLoc;
    }

    public Location getStinkyLoc() {
        return stinkyLoc;
    }

    public Location getHinkyLoc() {
        return hinkyLoc;
    }

    public Location getKinkyLoc() {
        return kinkyLoc;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
