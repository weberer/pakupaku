package Model;
import java.util.ArrayList;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;


public abstract class MovingGameObject {

	public enum States{

	}

	protected States state;
	protected Direction facingDirection;
	protected Location loc;

	protected ArrayList<ArrayList> map; //moved this declaration from Ghost to MovingGameObject so that it can be used by Paku -- Evan 10/30

	protected GameData gameData;  //used to centralize the locations of all MovingGameObjects

	protected int modX, modY;

	public MovingGameObject(States state, Direction dir){
		this.state = state;
		this.facingDirection = dir;
	}

	public MovingGameObject(){

	}

	public void setGameData(GameData data)
	{
		this.gameData = data;
	}

	public abstract void move();

	public Direction getFacingDirection() {
		return facingDirection;
	}
	public Location getLoc() {
		return loc;
	}

}