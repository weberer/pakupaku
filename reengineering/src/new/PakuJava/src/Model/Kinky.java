package Model;


import java.util.ArrayList;

/**
 * Kinky is the pink ghost who will follow where paku is about to move to based on its direction.
 * The unique chase function of kinky is to predict where paku is going by targeting a tile in front of where Paku is
 * facing.
 * Kinky's Scatter moves it to the top left corner of the map.
 * Kinky is the first ghost to leave the jail
 */
public class Kinky extends Ghost {

    private final int STARTING_X = 14;  //starting x and y coordinates of Paku; subject to change
    private final int STARTING_Y = 14;
    private final int SCATTER_X = 1;
    private final int SCATTER_Y = 1;
    private final int EXITCOUNTER = 30;
    private final int KINKY_VARIANCE = 4;
    public Kinky(ArrayList<ArrayList> map) {
        super(null, Direction.up);
        loc = new Location(STARTING_X, STARTING_Y);
        this.map = map;
        facingDirection = Direction.up;
        exitCounter = EXITCOUNTER;
        resetExitCounter = EXITCOUNTER;
    }

    @Override
    public void resetLocation() {
        loc.setxLoc(STARTING_X);
        loc.setyLoc(STARTING_Y);
    }

    /**
     * Kinky's Scatter location is to the top left corner of the map.
     * Kinky's unique chase targets the tile four tiles away from where paku is currently facing. The exception to this
     * is when Paku is facing up, Kinky will target the tile that is four tiles up and four to the left. This number can
     * be negative. Then Kinky's current position is subtracted from that result to get the difference in position. This number
     * can also be negative.
     */
    @Override
    public void move() {
        Location paku = Paku.getInstance().getLoc();
        Direction pakuDir = Paku.getInstance().getFacingDirection();
        if (inJail()) {
            jailMove();
            exitCounter--;
        } else {
            if (state.equals(GhostState.scatter)) {
                scatterMove(SCATTER_X, SCATTER_Y);
            } else if (state.equals(GhostState.chase)) {
                if(pakuDir.equals(Direction.up)) {
                    changeX = paku.getxLoc() - KINKY_VARIANCE;
                    changeY = paku.getyLoc() - KINKY_VARIANCE;
                }
                else if (pakuDir.equals(Direction.down))
                {
                    changeX = paku.getxLoc();
                    changeY = paku.getyLoc() + KINKY_VARIANCE;
                }
                else if (pakuDir.equals(Direction.left))
                {
                    changeX = paku.getxLoc() - KINKY_VARIANCE;
                    changeY = paku.getyLoc();
                }
                else if(pakuDir.equals(Direction.right))
                {
                    changeX = paku.getxLoc() + KINKY_VARIANCE;
                    changeY = paku.getyLoc();
                }
                changeX = changeX - loc.getxLoc();
                changeY = changeY - loc.getyLoc();
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
     * blinkTimer ArrayList's value for the level, it will alternate the current blink boolean in gameData for Kinky.
     * If the fleeTimer is greater than the blinkTimers value, or the current level is after 21 (20 due to how gameData
     * stores it), it will set the boolean to false.
     */
    public void isBlinking() {
        if (gameData.getGamelevel() < 21) {
            if (fleeTimer <= blinkTimers.get(gameData.getGamelevel())) {
                gameData.setKinkyBlink(!gameData.isKinkyBlink());
            } else
                gameData.setKinkyBlink(false);
        }
        gameData.setKinkyBlink(false);
    }
}