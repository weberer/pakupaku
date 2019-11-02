package Model;


import java.util.ArrayList;

/**
 * Hinky is the blue ghost on the playfield. It spwans in the jail with Kinky and Blaine. Hinky's unique chase function
 * is to target a tile on the map based off of the direction Paku is facing like Kinky, but two tiles away instead of four.
 * The catch to this isthat before the directional values are generated, the location of Stinky compared to Paku is
 * calculated, and the values are derived from this. Hinky's Scatter direction is the bottom right orner of the map. Hinky is the second ghost
 * to leave the jail. Due to how Hinky's Chase works, an instance of Stinky is required for this class to function.
 */
public class Hinky extends Ghost
{
    private final int STARTING_X = 11;  //starting x and y coordinates of Paku; subject to change
    private final int STARTING_Y = 14;
    private final int SCATTER_X = 26;
    private final int SCATTER_Y = 30;
    private final int HINKY_VARIANCE = 2;
    private final int EXITCOUNTER = 60;
    private Ghost stinky;
    public Hinky(Ghost stinky, ArrayList<ArrayList> map)
    {
        super( null, Direction.up);
        this.map = map;
        this.stinky = stinky;
        loc = new Location(STARTING_X, STARTING_Y);
        facingDirection = Direction.down;
        resetExitCounter = EXITCOUNTER;
        exitCounter = EXITCOUNTER;
    }
    @Override
    public void resetLocation() {
        loc.setxLoc(STARTING_X);
        loc.setyLoc(STARTING_Y);
    }

    /**
     * Hinky's unique Scatter function is to move to the lower left corner of the map.
     * Hinky calculates his distance calcuations based on the tile that is two away from paku's current location,
     * based off of the direction Paku faces. Up is unique as it points to the tile two tiles up and two to the left.
     * This number can be negative. Afterwards, Stinky's location is added to these values, and then is divided by two.
     * This can cause Hinky to go off course when Stinky is far away. Then the current location of Hinky is subtracted
     * from the dividend. This value can be negative.
     */
    @ Override
    public void move()   {
        Location paku = Paku.getInstance().getLoc();
        Direction pakuDir = Paku.getInstance().getFacingDirection();
        alternate = !alternate;
        if (inJail()) {
            jailMove();
            exitCounter--;
        } else {
            checkWarp();
            if (state.equals(GhostState.scatter)) {
                scatterMove(SCATTER_X, SCATTER_Y);

            } else if (state.equals(GhostState.chase)) {
                if(pakuDir.equals(Direction.up)) {
                    changeX = paku.getxLoc() - HINKY_VARIANCE;
                    changeY = paku.getyLoc() - HINKY_VARIANCE;
                }
                else if (pakuDir.equals(Direction.down))
                {
                    changeX = paku.getxLoc();
                    changeY = paku.getyLoc() + HINKY_VARIANCE;
                }
                else if (pakuDir.equals(Direction.left))
                {
                    changeX = paku.getxLoc() - HINKY_VARIANCE;
                    changeY = paku.getyLoc();
                }
                else if(pakuDir.equals(Direction.right))
                {
                    changeX = paku.getxLoc() + HINKY_VARIANCE;
                    changeY = paku.getyLoc();
                }
                changeX = ((changeX + stinky.loc.getxLoc()) / 2) - loc.getxLoc();
                changeY = ((changeY + stinky.loc.getyLoc()) / 2) - loc.getyLoc();
            } else if (state.equals(GhostState.flee)) {
                fleeMove();
            } else if (state.equals(GhostState.eaten)) {
                eatenMove();
            }
            calculateMove();
        }

    }
    /**
     * Compares the fleeTimer variables to the level's blinkTimer values, if the fleeTimer is less than or equal to the
     * blinkTimer ArrayList's value for the level, it will alternate the current blink boolean in gameData for Hinky.
     * If the fleeTimer is greater than the blinkTimers value, or the current level is after 21 (20 due to how gameData
     * stores it), it will set the boolean to false.
     */
    public void isBlinking()
    {
        if(gameData.getGamelevel() < 21)
        {
            if(fleeTotal <= blinkTimers.get(gameData.getGamelevel()))
            {
                gameData.setHinkyBlink(!gameData.isHinkyBlink());
            }
            else
                gameData.setHinkyBlink(false);
        }
        gameData.setHinkyBlink(false);
    }
}