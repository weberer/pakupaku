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
    }

    @Override
    public void resetLocation() {
        loc.setxLoc(STARTING_X);
        loc.setyLoc(STARTING_Y);
    }

    /**
     * Stinky's unique Scatter movement is to go to the top right corner.
     * Stinky has the simplist Chase movement, which takes the current location of paku and subtract's Stinky's location
     * to detemine distance. This value can be negative.
     */
    @ Override
    public void move()   {
        Location paku = Paku.getInstance().getLoc();

        alternate = !alternate;
        modX = loc.getxLoc() % 3;
        modY = (loc.getyLoc() + 1) % 3;
        if(inJail())
        {
            jailMove();
        }
        else {
            checkWarp();
                jailSkip = false;
                howFar = 1;
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
    /**
     * Compares the fleeTimer variables to the level's blinkTimer values, if the fleeTimer is less than or equal to the
     * blinkTimer ArrayList's value for the level, it will alternate the current blink boolean in gameData for Stinky.
     * If the fleeTimer is greater than the blinkTimers value, or the current level is after 21 (20 due to how gameData
     * stores it), it will set the boolean to false.
     */
    public void isBlinking()
    {
        if(gameData.getGamelevel() < 21)
        {
            if(fleeTotal <= blinkTimers.get(gameData.getGamelevel()))
            {
                gameData.setStinkyBlink(!gameData.isStinkyBlink());
            }
            else
                gameData.setStinkyBlink(false);
        }
        gameData.setStinkyBlink(false);
    }
}

