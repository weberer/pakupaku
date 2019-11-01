package Model;

import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class Blaine extends Ghost
{
    private final int STARTING_X = 15;  //starting x and y coordinates of Paku; subject to change
    private final int STARTING_Y = 14;
    private final int SCATTER_X = 1;
    private final int SCATTER_Y = 30;
    private final int EXITCOUNTER = 0;

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
    public void isBlinking()
    {
        if(gameData.getGamelevel() < 21)
        {
            if(fleeTimer <= blinkTimers.get(gameData.getGamelevel()))
            {
                gameData.setBlaineBlink(!gameData.isBlaineBlink());
            }
            else
                gameData.setBlaineBlink(false);
        }
        gameData.setBlaineBlink(false);
    }

}