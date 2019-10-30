package Model;

import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class Blaine extends Ghost
{
    private final int STARTING_X = 14;  //starting x and y coordinates of Paku; subject to change
    private final int STARTING_Y = 11;
    private final int SCATTER_X = 1;
    private final int SCATTER_Y = 30;


    public Blaine(ArrayList<ArrayList> map)
    {
        loc = new Location(STARTING_X, STARTING_Y);
        this.map = map;
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
        modX = loc.getxLoc() % 3;
        modY = (loc.getyLoc() + 1) % 3;
        if (inJail()) {
            jailMove();
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
}