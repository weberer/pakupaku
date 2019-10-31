/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;/*
import Model.MovingGameObject;
import org.json.simple.JSONObject;
import Controller.Controls;*/

import Controller.Controls;

import java.util.ArrayList;

/**
 *
 * @author kruge
 */
public class Paku extends MovingGameObject{

    private static Paku paku = new Paku();  //to make this class a Singleton
    private int remainingLife;
    private final int STARTINGLIFES = 3;
    //private Location loc;
    private final int STARTING_X = 14;  //starting x and y coordinates of Paku; subject to change
    private final int STARTING_Y = 24;

    private final int MOVE_DIST_PER_TICK = 1;

    private Paku()
    {
        super(14, 24, null, Direction.left);
        System.out.println("Paku has been constructed");

        remainingLife = STARTINGLIFES;
        //loc = new Location(STARTING_X, STARTING_Y);

    }


    /**
     * returns the single Paku instance
     */
    public static Paku getInstance()
    {
        return paku;
    }


    /**
     * Moves Paku a single unit in the direction it is facing unless it is facng a wall, in which case it does not move
     */
    @Override
    public void move() {
        switch(this.facingDirection) {
            case left:   //move Paku left a unit
                if(!checkWall(loc.getxLoc() - MOVE_DIST_PER_TICK, loc.getyLoc()))
                    loc.setxLoc(loc.getxLoc() - MOVE_DIST_PER_TICK); //move left if not running into wall
            case right: //move Paku right a unit unless there's a wall there
                if(!checkWall(loc.getxLoc() + MOVE_DIST_PER_TICK, loc.getyLoc()))
                    loc.setxLoc(loc.getxLoc() + MOVE_DIST_PER_TICK);
            case up:  //move Paku up a unit unless theres a wall there
                if(!checkWall(loc.getxLoc(), loc.getyLoc() + MOVE_DIST_PER_TICK))
                    loc.setyLoc(loc.getyLoc() + MOVE_DIST_PER_TICK);
            case down:  //move Paku down a unit unless there's a wall
                if(!checkWall(loc.getxLoc(), loc.getyLoc() - MOVE_DIST_PER_TICK))
                    loc.setyLoc(loc.getyLoc() - MOVE_DIST_PER_TICK);
        }
    }

    /**
     * Returns true if Paku has run into a wall, false otherwise
     * @param xLoc
     * @param yLoc
     * @return
     */
    private boolean checkWall(int xLoc, int yLoc)
    {
        ArrayList row = map.get(loc.getyLoc());
        int tile = (int)row.get(loc.getxLoc()); //get tile that Paku is trying to get to
        if(tile == gameData.getWALL_CODE())
            return true;
        return false;
    }

    /**
     * decrements paku life number
     */
    public void substractLife(){
        remainingLife--;
    }

    /**
     * adds a life for paku; called after certains score acheived??
     */
    public void addLife(){
        remainingLife++;
    }

    /**
     * Checks whether Paku is out of lives; if so, the game is over and must be reset
     * @return
     */
    public boolean isGameOver(){
        return remainingLife > 0;
    }

    /**
     *returns the number of remaining lives for paku
     */
    public int getRemainingLife()
    {
        return remainingLife;
    }

    @Override
    public Location getLoc() {
        return super.loc;
    }


    public void setLoc(Location loc) {
        super.loc = loc;
    }
    public void setDir(Direction dir)
    {
        super.facingDirection = dir;
    }


    /**
     * This resets the game; Puts Paku back in its initial position and resets its number of lives
     */
    public void resetPaku()
    {
        super.loc.setyLoc(STARTING_Y);
        super.loc.setxLoc(STARTING_X);
        remainingLife = STARTINGLIFES;
    }


    /**
     * This resets a round; Paku gets put back to its original position
     */
    public void resetLocation()
    {
        super.loc.setyLoc(STARTING_Y);
        super.loc.setxLoc(STARTING_X);
    }

    public void setMap(ArrayList<ArrayList> map) {
        this.map = map;
    }
}
