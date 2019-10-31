package Model;


import java.util.ArrayList;
import java.util.Random;

public class Stinky extends Ghost
{
    private final int STARTING_X = 14;  //starting x and y coordinates of Paku; subject to change
    private final int STARTING_Y = 11;
    private final int SCATTER_X = FAR_RIGHT;
    private final int SCATTER_Y = 1;


    public Stinky(ArrayList<ArrayList> map)
    {
        super( null, Direction.up);
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
    public void isBlinking()
    {
        if(gameData.getGamelevel() < 21)
        {
            if(fleeTimer <= blinkTimers.get(gameData.getGamelevel()))
            {
                gameData.setStinkyBlink(!gameData.isStinkyBlink());
            }
            else
                gameData.setStinkyBlink(false);
        }
        gameData.setStinkyBlink(false);
    }
    public void recordLocation() {
        gameData.setStinkyLocation(loc);
    }
}

