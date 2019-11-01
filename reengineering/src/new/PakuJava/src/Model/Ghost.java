/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Ghost handles the shared functions of the ghost subclasses, holding the main move method, shared movement calculations,
 * and jail procedures. Also stores the timers used to change state for each ghost.
 * @author kruge
 */

//Todo: Every frame sent is on 40 milliseconds, use this information to set up the internal timers
public abstract class Ghost extends MovingGameObject {

    //constants from original code
    protected final int FAR_RIGHT = 26;
    private final int JAIL_BOTTOM = 16;
    private final int JAIL_TOP = 12;
    private final int JAIL_LEFT = 11;
    private final int JAIL_RIGHT = 16;
    private final int JAIL_DOOR = 14;
    private final int WARP_LEVEL = 14;
    private final int EATEN_Y = 10;
    private final int EATEN_X = 13;

    protected final static int SCORE = 200;
    protected boolean jailSkip;
    protected boolean allowTurn = false;
    protected boolean alternate = false;
    protected int changeX, changeY;
    protected int absoluteX, absoluteY;
    protected Random random;
    protected int testAmount;
    protected int resetExitCounter;
    protected int exitCounter;
    protected int howFar = 1;
    protected int howFarIncrement = 0;
    protected int fleeTotal;
    protected static int multiplier = 1;
    protected GhostState state;
    protected GhostState storedState;

    protected static ArrayList<Integer> frightTimers;
    protected static ArrayList<Integer> blinkTimers;
    protected static ArrayList<Integer> level1behaviors;
    protected static ArrayList<Integer> level2to5Behaviors;
    protected static ArrayList<Integer> level5PlusBehaviors;
    protected static ArrayList<GhostState> levelStates;

    protected int timer;
    protected int timerIndex = 0;

    protected ArrayList<ArrayList> map;

    public Ghost(States ss, Direction dir) {
        super(null, Direction.up);

        random = new Random();
        state = GhostState.chase;
        facingDirection = Direction.up;
    }

    /**
     * Tests if the ghost is in the jail area
     * @return true if it is, otherwise false;
     */
    public boolean inJail() {
        if (loc.getxLoc() >= JAIL_LEFT && loc.getxLoc() <= JAIL_RIGHT) {
            if (loc.getyLoc() < JAIL_BOTTOM && loc.getyLoc() > JAIL_TOP)
                return true;
            else
                return false;
        } else
            return false;
    }

    /**
     * Logic on how the ghost moves in jail. The ghost is forced out when exitCounter is 0, otherwise they move
     * in the jail.
     */
    protected void jailMove() {
        if (state.equals(GhostState.eaten) && loc.getyLoc() > JAIL_TOP) {
            loc.setyLoc(loc.getyLoc() + 1);
            if (loc.getyLoc() == JAIL_BOTTOM) {
                exitCounter = resetExitCounter;
                state = GhostState.scatter;
                timerIndex = 0;
                if(GameData.getInstance().getGamelevel() == 1)
                {
                    timer = level1behaviors.get(timerIndex);
                }
                else if(GameData.getInstance().getGamelevel() >= 2 && GameData.getInstance().getGamelevel() < 5)
                {
                    timer = level2to5Behaviors.get(timerIndex);
                }
                else
                {
                    timer = level5PlusBehaviors.get(timerIndex);
                }
            }

        } else {
            if (exitCounter > 0) {
                if (loc.getyLoc() == JAIL_TOP  ) {
                    facingDirection = Direction.down;
                    loc.setyLoc(loc.getyLoc() + 1);
                } else if (loc.getyLoc() == JAIL_BOTTOM ) {
                    facingDirection = Direction.up;
                    loc.setyLoc(loc.getyLoc() - 1);
                }
            } else {
                if(loc.getxLoc() >= JAIL_LEFT && loc.getxLoc() <= 13)
                {
                    facingDirection = Direction.right;
                }
                else if(loc.getxLoc() >= 15 && loc.getxLoc() <= JAIL_RIGHT)
                {
                    facingDirection = Direction.left;

                }
                else if(loc.getxLoc() == 14)
                {
                    facingDirection = Direction.up;
                }
            }
            switch (facingDirection) {
                case up:
                    loc.setyLoc(loc.getyLoc() - 1);
                    break;
                case down:
                    loc.setyLoc(loc.getyLoc() + 1);
                    break;
                case left:
                    loc.setxLoc(loc.getxLoc() - 1);
                    break;
                case right:
                    loc.setxLoc(loc.getxLoc() + 1);
                    break;
            }
        }
    }

    /**
     * Checks to see is the ghost is about to warp to the other side of the map, and if it is, move it there.
     */
    protected void checkWarp() {
        if (facingDirection.equals(Direction.right)) {
            if (loc.getxLoc() == FAR_RIGHT && loc.getyLoc() == WARP_LEVEL) {
                loc.setxLoc(1);
            }
        } else if (facingDirection.equals(Direction.left)) {
            if (loc.getxLoc() == 1 && loc.getyLoc() == WARP_LEVEL) {
                loc.setxLoc(FAR_RIGHT);
            }
        }
    }

    /**
     * The basic movement style besides the chase movement. Each ghost will move to a corner
     * @param scatterX the corner's x position.
     * @param scatterY the corner's y position.
     */
    protected void scatterMove(int scatterX, int scatterY) {
        changeX = scatterX - loc.getxLoc();
        changeY = scatterY - loc.getyLoc();
    }

    /**
     * How ghost moves in the flee state, will avoid the current Paku location, if possible.
     */
    protected void fleeMove() {
        changeX = loc.getxLoc() - Paku.getInstance().getLoc().getxLoc();
        changeY = loc.getyLoc() - Paku.getInstance().getLoc().getyLoc();
    }

    /**
     * How a ghost moves when eaten. will try to get to the jail door as soon as possible
     */
    protected void eatenMove() {
        if (loc.getxLoc() == JAIL_DOOR) {
            if (loc.getyLoc() == (JAIL_TOP - 1) && loc.getyLoc() < JAIL_BOTTOM) {
                facingDirection = Direction.down;
                jailSkip = true;
            }
        } else {
            changeX = JAIL_DOOR - loc.getxLoc();
            changeY = JAIL_TOP - loc.getyLoc();
            if(howFarIncrement == 3)
            {
                howFar++;
                howFarIncrement = 1;
            }
        }
    }

    /**
     * Main logic of movement for ghosts outside of jail. Will move the ghost, and maybe turn them based on logic.
     */
    protected void calculateMove() {
        int randomInt = random.nextInt(10);
        if (!jailSkip) {

            absoluteX = Math.abs(changeX);
            absoluteY = Math.abs(changeY);
            if (facingDirection.equals(Direction.up) || facingDirection.equals(Direction.left)) {
                testAmount = -1;
            } else {
                testAmount = 1;
            }
            if (facingDirection.equals(Direction.up) || facingDirection.equals(Direction.down)) {
                if (randomInt > 1) {
                    if (loc.getxLoc() == 9 || loc.getxLoc() == 18)
                        if (loc.getyLoc() > 9 && loc.getyLoc() < 27)
                            allowTurn = true;
                        else
                            allowTurn = false;
                    else
                        allowTurn = false;

                } else
                    allowTurn = false;


                if (absoluteX > absoluteY || changeY > 0) {
                    turnUpDown();
                } else if ((int) map.get(loc.getyLoc() + testAmount).get(loc.getxLoc()) == 0 || allowTurn) {
                    turnUpDown();
                } else if (randomInt > 8) {
                    turnUpDown();
                }
            } else if (facingDirection.equals(Direction.left) || facingDirection.equals(Direction.right)) {
                if (randomInt > 1) {
                    if ((loc.getxLoc() > 9) && (loc.getxLoc() < 18))
                        if ((loc.getyLoc() > 9) || (loc.getyLoc() < 22))
                            allowTurn = false;
                        else
                            allowTurn = true;
                    else
                        allowTurn = true;

                } else
                    allowTurn = true;
                if (absoluteX > absoluteY || changeY > 0) {
                    turnLeftRight();
                }
                if ((int) map.get(loc.getyLoc()).get(loc.getxLoc() + testAmount) == 0 || randomInt > 8) {
                    turnLeftRight();
                }
            }
            // left/right check
            // jailskip check
        }
        if (!state.equals(GhostState.flee)) {
            moveNotTurn();
        } else if (!(loc.getyLoc() == 14))
            if (!(loc.getxLoc() < 6 && loc.getxLoc() > 21))
                if (!alternate) {
                    moveNotTurn();
                }

        if(!state.equals(GhostState.flee) && !state.equals(GhostState.eaten))
        {
            timer--;
        }
        if(timer <= 0 && !state.equals(GhostState.flee))
        {
            if(!state.equals(GhostState.eaten)) {
                if (GameData.getInstance().getGamelevel() == 0) {
                    if (timerIndex == 6)
                        timerIndex = 0;
                    else
                        timerIndex++;
                    timer = level1behaviors.get(timerIndex);
                    state = levelStates.get(timerIndex);
                } else if (GameData.getInstance().getGamelevel() >= 1 && GameData.getInstance().getGamelevel() < 4) {
                    if (timerIndex == 6)
                        timerIndex = 0;
                    else
                        timerIndex++;
                    timer = level2to5Behaviors.get(timerIndex);
                    state = levelStates.get(timerIndex);
                } else {
                    if (timerIndex == 5)
                        timerIndex = 0;
                    else
                        timerIndex++;
                    timer = level5PlusBehaviors.get(timerIndex);
                    state = levelStates.get(timerIndex);
                }
            }
        }
        if(state.equals(GhostState.flee))
        {
            if(fleeTotal <= 0)
            {
                state = storedState;

            }
            else
            {
                fleeTotal--;
            }
        }

    }

    /**
     * Used after all the turning is completed, the ghosts move in their corresponding facingDirection.
     */
    private void moveNotTurn()
    {
        ArrayList row = map.get(loc.getyLoc());
        ArrayList rowUp = map.get(loc.getyLoc() - 1);
        ArrayList rowDown = map.get(loc.getyLoc() + 1);
        if (facingDirection.equals(Direction.up)) {
            if ((int)rowUp.get(loc.getxLoc())> 0)
                loc.setyLoc(loc.getyLoc() - howFar);
            else
                loc.setyLoc(loc.getyLoc() - 1);
        } else if (facingDirection.equals(Direction.right)) {
            if ((int)row.get(loc.getxLoc() + 1) > 0)
                loc.setxLoc(loc.getxLoc() + howFar);
            else
                loc.setxLoc(loc.getxLoc() + 1);
        } else if (facingDirection.equals(Direction.down)) {
            if ((int)rowDown.get(loc.getxLoc()) > 0 || jailSkip)
                loc.setyLoc(loc.getyLoc() + howFar);
            else
                loc.setyLoc(loc.getyLoc() + 1);
        } else if (facingDirection.equals(Direction.left)) {
            if ((int)row.get(loc.getxLoc() - 1) > 0)
                loc.setxLoc(loc.getxLoc() - howFar);
            else
                loc.setxLoc(loc.getxLoc() - 1);
        }
    }

    /**
     * Used when a ghost moving up or down needs to turn.
     */
    private void turnUpDown() {
        if (changeX > 0) {
            if ((int)map.get(loc.getyLoc()).get(loc.getxLoc() + 1) > 0) {
                facingDirection = Direction.right;
            } else if ((int)map.get(loc.getyLoc()).get(loc.getxLoc() - 1) > 0) {
                if ((int)map.get(loc.getyLoc() + testAmount).get(loc.getxLoc()) == 0) {
                    facingDirection = Direction.left;
                } else if (allowTurn && loc.getxLoc() == 7) {
                    facingDirection = Direction.left;
                }
            }
        } else {
            if ((int)map.get(loc.getyLoc()).get(loc.getxLoc() - 1) > 0) {
                facingDirection = Direction.left;
            } else if ((int)map.get(loc.getyLoc()).get(loc.getxLoc() + 1) > 0) {
                if ((int)map.get(loc.getyLoc() + testAmount).get(loc.getxLoc()) == 0) {
                    facingDirection = Direction.right;
                } else if (allowTurn && loc.getxLoc() == 7) {
                    facingDirection = Direction.right;
                }
            }
        }
    }

    /**
     * Used when a ghost moving left or right needs to turn up or down.
     */
    private void turnLeftRight() {
        if(changeY > 0)
        {
            if((int)map.get(loc.getyLoc() + 1).get(loc.getxLoc()) > 0)
            {
                facingDirection = Direction.down;
            }
            else if((int)map.get(loc.getyLoc() - 1).get(loc.getxLoc()) > 0)
            {
                if(allowTurn || (int)map.get(loc.getyLoc()).get(loc.getxLoc() + 1) == 0)
                {
                    facingDirection = Direction.up;
                }
            }
        }
        else
        {
            if((int)map.get(loc.getyLoc() - 1).get(loc.getxLoc()) > 0 && allowTurn)
            {
                facingDirection = Direction.up;
            }
            else if((int)map.get(loc.getyLoc() + 1).get(loc.getxLoc()) > 0 &&
                    (int)map.get(loc.getyLoc()).get(loc.getxLoc() + testAmount) == 0)
            {
                facingDirection = Direction.down;
            }
        }
    }

    public void setState(GhostState state) {
        this.state = state;
    }



    public GhostState getState()
    {
        return this.state;
    }

    /**
     * Scores the ghost and sets it's state to eaten.
     * @param score score to add to
     * @return score added for gameData
     */
    public int addScore(Score score)
    {
        score.addScore(SCORE * multiplier);
        int ret = SCORE * multiplier;
        if(multiplier != 32 )
            multiplier = multiplier * 2;
        this.setState(GhostState.eaten);
        return ret;
    }
    public void resetMultiplier()
    {
        multiplier = 1;
    }

    /**
     * Method to set the ghost to a fleeing state. will set the fright timers to the corresponding level's timer.
     */
    public void makeFlee()
    {
        if(state != GhostState.flee) {
            if (GameData.getInstance().getGamelevel() <= 20) {
                fleeTotal = frightTimers.get(GameData.getInstance().getGamelevel());
                storedState = state;
                state = GhostState.flee;
            } else {
                fleeTotal = frightTimers.get(20);
                storedState = state;
                state = GhostState.flee;
            }
            if (facingDirection.equals(Direction.left))
                facingDirection = Direction.right;
            else if (facingDirection.equals(Direction.right))
                facingDirection = Direction.left;
            else if (facingDirection.equals(Direction.up))
                facingDirection = Direction.down;
            else if (facingDirection.equals(Direction.down))
                facingDirection = Direction.up;
        }
        else
        {
            if (GameData.getInstance().getGamelevel() <= 20) {
                fleeTotal = frightTimers.get(GameData.getInstance().getGamelevel());
            } else {
                fleeTotal = frightTimers.get(20);
            }
        }

    }
    public abstract void resetLocation();

    //Used to populate level based timers.
    public void setupTimers()
    {
        level1behaviors = new ArrayList<>();
        level2to5Behaviors = new ArrayList<>();
        level5PlusBehaviors = new ArrayList<>();
        levelStates = new ArrayList<>();
        frightTimers = new ArrayList<>();
        blinkTimers = new ArrayList<>();

        level1behaviors.add(7);
        level1behaviors.add(20);
        level1behaviors.add(7);
        level1behaviors.add(20);
        level1behaviors.add(5);
        level1behaviors.add(20);
        level1behaviors.add(5);

        level2to5Behaviors.add(7);
        level2to5Behaviors.add(20);
        level2to5Behaviors.add(7);
        level2to5Behaviors.add(20);
        level2to5Behaviors.add(5);
        level2to5Behaviors.add(1092);
        level2to5Behaviors.add(7);

        level5PlusBehaviors.add(5);
        level5PlusBehaviors.add(20);
        level5PlusBehaviors.add(5);
        level5PlusBehaviors.add(20);
        level5PlusBehaviors.add(5);
        level5PlusBehaviors.add(1092);

        levelStates.add(GhostState.scatter);
        levelStates.add(GhostState.chase);
        levelStates.add(GhostState.scatter);
        levelStates.add(GhostState.chase);
        levelStates.add(GhostState.scatter);
        levelStates.add(GhostState.chase);
        levelStates.add(GhostState.scatter);

        frightTimers.add(22);
        frightTimers.add(20);
        frightTimers.add(18);
        frightTimers.add(16);

        frightTimers.add(14);
        frightTimers.add(22);
        frightTimers.add(14);
        frightTimers.add(14);

        frightTimers.add(8);
        frightTimers.add(20);
        frightTimers.add(14);
        frightTimers.add(8);

        frightTimers.add(8);
        frightTimers.add(16);
        frightTimers.add(8);
        frightTimers.add(8);

        frightTimers.add(0);
        frightTimers.add(8);
        frightTimers.add(0);
        frightTimers.add(0);

        frightTimers.add(0);

        blinkTimers.add(10);
        blinkTimers.add(10);
        blinkTimers.add(10);
        blinkTimers.add(10);

        blinkTimers.add(10);
        blinkTimers.add(10);
        blinkTimers.add(10);
        blinkTimers.add(10);

        blinkTimers.add(6);
        blinkTimers.add(10);
        blinkTimers.add(10);
        blinkTimers.add(6);

        blinkTimers.add(6);
        blinkTimers.add(10);
        blinkTimers.add(6);
        blinkTimers.add(6);

        blinkTimers.add(0);
        blinkTimers.add(6);
        blinkTimers.add(0);
        blinkTimers.add(0);

        blinkTimers.add(0);
    }
    //Starts the state time on each ghost based on the level.
    public void startTimer()
    {
        if(gameData.getGamelevel() == 0)
        {
            timerIndex = 0;
            timer = level1behaviors.get(timerIndex);
            state = levelStates.get(timerIndex);
        }
        else if(gameData.getGamelevel() >= 1 && gameData.getGamelevel() < 4)
        {
            timerIndex = 0;
            timer = level2to5Behaviors.get(timerIndex);
            state = levelStates.get(timerIndex);
        }
        else
        {
            timerIndex = 0;
            timer = level5PlusBehaviors.get(timerIndex);
            state = levelStates.get(timerIndex);
        }

    }
    //implement as true if fleeTotal is less than the level's blinkTimer.
    public abstract void isBlinking();
    //These methods are used for testing.
    public void setDirection(Direction dir)
    {
        facingDirection = dir;
    }
    public int getMultiplier()
    {
        return multiplier;
    }
    public void setFleeTimer(int i)
    {
        fleeTotal = i;
    }
}