package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Stinky is the red ghost on the playfield. Its unique chase function is to chase after the current location of Paku.
 * Stinky's scatter location is the top right corner of the map (See ScatterMove in Ghost for more information on this)
 * Stinky is the only ghost who starts outside of the jail in the center, and starts facing left.
 */
public class Stinky extends Ghost
{
    private final int STARTING_X = 14;  //starting x and y coordinates of Paku; subject to change
    private final int STARTING_Y = 11;
    private final int SCATTER_X = FAR_RIGHT;
    private final int SCATTER_Y = 1;

    public Stinky(ArrayList<ArrayList> map)
    {
        super( null, Direction.left);
        this.map =  map;
        loc = new Location(STARTING_X, STARTING_Y);
        exitCounter = 0;
        resetExitCounter = 0;
        gameData = GameData.getInstance();
    }

    @Override
    public void resetLocation() {
        loc.setxLoc(STARTING_X);
        loc.setyLoc(STARTING_Y);
    }

    /**
     * Stinky's unique Scatter movement is to go to the top right corner.
     * Stinky has the simplist Chase movement, which takes the current location of paku and subtract's Stinky's location
     * to determine distance. This value can be negative.
     */
    @ Override
    public void move()   {
        Location paku = Paku.getInstance().getLoc();
        if(inJail())
        {
            jailMove();
        }
        else {
            warp = false;
            alternate = !alternate;
            checkWarp();
            setWarpFlag();
                jailSkip = false;
            if (state.equals(GhostState.scatter)) {
                scatterMove(SCATTER_X, SCATTER_Y);
            } else if (state.equals(GhostState.chase)) {
                changeX = paku.getxLoc() - loc.getxLoc();
                changeY = paku.getyLoc() - loc.getyLoc();
            } else if (state.equals(GhostState.flee)) {
                fleeMove();
            } else if (state.equals(GhostState.eaten)) {
                eatenMove();
            }
            this.calculateMove();
        }
    }

    private void setWarpFlag() {
        gameData.setStinkyWarp(warp);
    }

    /**
     * See Ghost for details (Line 451)
     */
    @Override
    public void endingFleeProtocol() {
        if(!state.equals(GhostState.eaten)) {
            state = storedState;
        }
        storedState = null;
        gameData.setStinkyBlink(false);
    }
    /**
     * See Ghost for details (Line 45)
     */
    @Override
    public void blink() {
        if(!gameData.isStinkyBlink() && isBlinking())
            gameData.setStinkyBlink(true);
        else if(gameData.isStinkyBlink() && !isBlinking())
            gameData.setStinkyBlink(false);
    }
}

