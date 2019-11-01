package Model;


import java.util.ArrayList;

public class Kinky extends Ghost {

    private final int STARTING_X = 14;  //starting x and y coordinates of Paku; subject to change
    private final int STARTING_Y = 14;
    private final int SCATTER_X = 1;
    private final int SCATTER_Y = 1;
    private final int EXITCOUNTER = 0;
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
    public void isBlinking() {
        if (gameData.getGamelevel() < 21) {
            if (fleeTotal <= blinkTimers.get(gameData.getGamelevel())) {
                gameData.setKinkyBlink(!gameData.isKinkyBlink());
            } else
                gameData.setKinkyBlink(false);
        }
        gameData.setKinkyBlink(false);
    }
}