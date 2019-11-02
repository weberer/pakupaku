package Model;

import java.util.ArrayList;

import static java.lang.Math.sqrt;

/**
 * Blaine is the brown ghost, he moves erratically and will ususally veer off course
 * if Paku is too far away.
 * Blaine's Scatter is to the bottom left corner of the map
 * Blaine's Chase is similar to Stinky's but less accurate.
 * Blaine is the last ghost to leave the jail.
 */
public class Blaine extends Ghost
{
    private final int STARTING_X = 15;  //starting x and y coordinates of Paku; subject to change
    private final int STARTING_Y = 14;
    private final int SCATTER_X = 1;
    private final int SCATTER_Y = 30;
    private final int EXITCOUNTER = 90;

    public Blaine(ArrayList<ArrayList> map)
    {
        super(null, Direction.up);
        loc = new Location(STARTING_X, STARTING_Y);
        this.map = map;
        facingDirection = Direction.up;
        exitCounter = EXITCOUNTER;
        resetExitCounter = EXITCOUNTER;
    }
    @Override
    public void resetLocation()
    {
        loc.setxLoc(STARTING_X);
        loc.setyLoc(STARTING_Y);
    }

    /**
     * Blaine's Scatter move is to move to the bottom right corner of the map.
     * Blaine's Chase movement functions similar to Stinky's, and afterwards the distance is used to calculate a qualifier.
     * This is calculated by the square root of (xDistance^2 + yDistance^2). if that qualifier is less than seven,
     * Blaine follows his Scatter move instead. Blaine is more effective at a distance rather than up close.
     */
    @Override
    public void move() {
        Location paku = Paku.getInstance().getLoc();
        alternate = !alternate;
        if (inJail()) {
            jailMove();
            exitCounter--;
        } else {
            checkWarp();
            if (state.equals(GhostState.scatter)) {
                scatterMove(SCATTER_X, SCATTER_Y);
            } else if (state.equals(GhostState.chase)) {
                changeX = paku.getxLoc() - loc.getxLoc();
                changeY = paku.getyLoc() - loc.getyLoc();
                testAmount = (int) sqrt(changeX * changeX + changeY * changeY);
                if (testAmount < 7) {
                    scatterMove(SCATTER_X, SCATTER_Y);
                }
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
     * blinkTimer ArrayList's value for the level, it will alternate the current blink boolean in gameData for Blaine.
     * If the fleeTimer is greater than the blinkTimers value, or the current level is after 21 (20 due to how gameData
     * stores it), it will set the boolean to false.
     */
    public void isBlinking()
    {
        if(gameData.getGamelevel() < 21)
        {
            if(fleeTotal <= blinkTimers.get(gameData.getGamelevel()))
            {
                gameData.setBlaineBlink(!gameData.isBlaineBlink());
            }
            else
                gameData.setBlaineBlink(false);
        }
        gameData.setBlaineBlink(false);
    }
}