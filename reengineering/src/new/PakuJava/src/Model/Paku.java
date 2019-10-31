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
    private Location loc;
    private final int STARTING_X = 14;  //starting x and y coordinates of Paku; subject to change
    private final int STARTING_Y = 24;

    private final int MOVE_DIST_PER_TICK = 1;

    private Paku()
    {
        System.out.println("Paku has been constructed");
        remainingLife = STARTINGLIFES;
        loc = new Location(STARTING_X, STARTING_Y);
    }


    /*
    returns the single Paku instance
     */
    public static Paku getInstance()
    {
        return paku;
    }


    //JSONObject jo = new JSONObject();
    @Override
    public void move()
    {
        //jo.put(dir.toString());

        switch(this.facingDirection)
        {
            case left:   //move Paku left a unit
                loc.setxLoc(loc.getxLoc() - MOVE_DIST_PER_TICK);
            case right: //move Paku right a unit
                loc.setxLoc(loc.getxLoc() + MOVE_DIST_PER_TICK);
            case up:  //move Paku up a unit
                loc.setyLoc(loc.getyLoc() + MOVE_DIST_PER_TICK);
            case down:  //move Paku down a unit
                loc.setyLoc(loc.getyLoc() - MOVE_DIST_PER_TICK);

        }

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
        return loc;
    }


    public void setLoc(Location loc) {
        this.loc = loc;
    }
    public void setDir(Direction dir)
    {
        this.facingDirection = dir;
    }


    /**
     * This resets the game; Puts Paku back in its initial position and resets its number of lives
     */
    public void resetPaku()
    {
        loc.setyLoc(STARTING_Y);
        loc.setxLoc(STARTING_X);
        remainingLife = STARTINGLIFES;
    }


    /**
     * This resets a round; Paku gets put back to its original position
     */
    public void resetLocation()
    {
        loc.setyLoc(STARTING_Y);
        loc.setxLoc(STARTING_X);
    }

    public void setMap(ArrayList<ArrayList> map) {
        this.map = map;
    }
}
