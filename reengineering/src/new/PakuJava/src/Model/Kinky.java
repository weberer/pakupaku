package Model;


import java.util.ArrayList;

public class Kinky extends Ghost {

    private final int STARTING_X = 14;  //starting x and y coordinates of Paku; subject to change
    private final int STARTING_Y = 13;
    private final int SCATTER_X = 1;
    private final int SCATTER_Y = 1;
    private final int EXITCOUNTER = 0;

    public Kinky(ArrayList<ArrayList> map) {

        loc = new Location(STARTING_X, STARTING_Y);
        this.map = map;
        facingDirection = Direction.up;
        exitCounter = EXITCOUNTER;
    }

    @Override
    public void resetLocation() {
        loc.setxLoc(STARTING_X);
        loc.setyLoc(STARTING_Y);
    }

    @Override
    public void move() {
        Location paku = Paku.getInstance().getLoc();
        if (inJail()) {
            jailMove();
            exitCounter--;
        } else {
            if (state.equals(GhostState.scatter)) {
                scatterMove(SCATTER_X, SCATTER_Y);
            } else if (state.equals(GhostState.chase)) {

            } else if (state.equals(GhostState.flee)) {
                fleeMove();
            } else if (state.equals(GhostState.eaten)) {
                eatenMove();
            }
            calculateMove();
        }
    }

    public void recordLocation() {
        gameData.setKinkyLocation(loc);

    }
}