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

	protected GameData gameData;  //used to centralize the locations of all MovingGameObjects

	protected int modX, modY;

	public MovingGameObject(int x, int y, States state, Direction dir){
		loc = new Location(x, y);
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