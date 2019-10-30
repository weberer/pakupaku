package Model;


import java.util.ArrayList;

public class Hinky extends Ghost
{
    //private Location loc;  //already a location object in MovingGameObject --Evan
    private final int STARTING_X = 14;  //starting x and y coordinates of Paku; subject to change
    private final int STARTING_Y = 11;
    private final int SCATTER_X = 26;
    private final int SCATTER_Y = 30;
    private final int HINKY_VARIANCE = 2;
    private Ghost stinky;
    public Hinky(Ghost stinky, ArrayList<ArrayList> map)
    {
        this.map = map;
        this.stinky = stinky;
        loc = new Location(STARTING_X, STARTING_Y);
    }
    @Override
    public void resetLocation() {
        loc.setxLoc(STARTING_X);
        loc.setyLoc(STARTING_Y);
    }
    @ Override
    public void move()   {
        Location paku = Paku.getInstance().getLoc();
        Direction pakuDir = Paku.getInstance().getFacingDirection();
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
}