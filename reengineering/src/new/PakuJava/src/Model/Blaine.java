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
    private final int STARTING_X = 16;  //starting x and y coordinates of Paku; subject to change
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
        gameData = GameData.getInstance();
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
        if (inJail()) {
            jailMove();
            exitCounter--;
        } else {
            warp = false;
            alternate = !alternate;
            jailSkip = false;
            checkWarp();
            setWarpFlag();
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
     * See Ghost for details (Line 451)
     */
    @Override
    public void endingFleeProtocol() {
        if(!state.equals(GhostState.eaten)) {
            state = storedState;
        }
        storedState = null;
        gameData.setBlaineBlink(false);
    }
    /**
     * See Ghost for details (Line 457)
     */
    @Override
    public void blink() {
        if(!gameData.isBlaineBlink() && isBlinking())
            gameData.setBlaineBlink(true);
        else if(gameData.isBlaineBlink() && !isBlinking())
            gameData.setBlaineBlink(false);
    }
    private void setWarpFlag()
    {
        gameData.setBlaineWarp(warp);
    }
}