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
 * and jail procedures. Also stores the timers used to change state for each ghost. The movement patterns shared
 * by each ghost is scatterMove, fleeMove, eatenMove, and jailMove.
 * Each Ghost handles their own Scatter movement direction, their starting location, their starting direction, and
 * how long they each stay in jail. Edit those values to change the movement.
 * @author luedtkemi
 */

//Todo: Every frame sent is on 40 milliseconds, use this information to set up the internal timers
public abstract class Ghost extends MovingGameObject {

    //constants from original code
    protected final int FAR_RIGHT = 26;
    //protected final int FAR_RIGHT = 27;
    private final int JAIL_BOTTOM = 16;
    private final int JAIL_TOP = 12;
    private final int JAIL_LEFT = 11;
    private final int JAIL_RIGHT = 16;
    //private final int JAIL_RIGHT = 17;
    private final int JAIL_DOOR = 14;
    private final int WARP_LEVEL = 14;

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
    protected static int globalFleeTimer;
    protected static int multiplier = 1;
    protected GhostState state;
    protected GhostState storedState;

    protected static ArrayList<Integer> frightTimers;
    protected static ArrayList<Integer> blinkTimers;
    protected static ArrayList<Integer> level1behaviors;
    protected static ArrayList<Integer> level2to4Behaviors;
    protected static ArrayList<Integer> level5PlusBehaviors;
    protected static ArrayList<GhostState> levelStates;

    protected int timer;
    protected int timerIndex = 0;

    protected ArrayList<ArrayList> map;

    public Ghost(States ss, Direction dir) {
        super(ss, dir);

        random = new Random();
        state = GhostState.scatter;
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
     * in the jail and decrement the exit counter.
     * When a ghost enters the jail for the first time after being eaten, the state variable is set to Scatter, the exitCounter
     * is set to the default of each ghost, and the timers for scatter are set to the corresponding level's first behavior.
     */
    protected void jailMove() {
        if (state.equals(GhostState.eaten) && loc.getyLoc() > JAIL_TOP) {
            loc.setyLoc(loc.getyLoc() + 1);
            if (loc.getyLoc() == JAIL_BOTTOM - 1) {
                exitCounter = resetExitCounter;
                state = GhostState.scatter;
                timerIndex = 0;
                if(GameData.getInstance().getGamelevel() == 1)
                {
                    timer = level1behaviors.get(timerIndex);
                }
                else if(GameData.getInstance().getGamelevel() >= 2 && GameData.getInstance().getGamelevel() < 5)
                {
                    timer = level2to4Behaviors.get(timerIndex);
                }
                else
                {
                    timer = level5PlusBehaviors.get(timerIndex);
                }
            }

        } else {
            if (exitCounter > 0) {
                if (loc.getyLoc() == JAIL_TOP + 1 ) {
                    facingDirection = Direction.down;
                } else if (loc.getyLoc() == JAIL_BOTTOM - 1) {
                    facingDirection = Direction.up;
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
     * Checks to see is the ghost is moving to the the tiles (0,14) or (27,14),
     * and if it is, set the ghost's location (26,14) when it is at (1,14), or (1,14) when it is at (26, 14).
     */
    protected void checkWarp() {
        if (facingDirection.equals(Direction.right)) {
            if (loc.getxLoc() == FAR_RIGHT && loc.getyLoc() == WARP_LEVEL) {
                loc.setxLoc(1);
                facingDirection = Direction.right;
            }
        } else if (facingDirection.equals(Direction.left)) {
            if (loc.getxLoc() == 1 && loc.getyLoc() == WARP_LEVEL) {
                loc.setxLoc(FAR_RIGHT);
                facingDirection = Direction.left;
            }
        }
    }

    /**
     * The basic movement style besides the chase movement. Each ghost will move towards a different corner.
     * Stinky will go to the top right (1,26)
     * Kinky will go to the top left (1,1)
     * Hinky will go to the bottom right (26,30)
     * Blaine will go to the bottom left (1,30)
     * @param scatterX the corner's x position.
     * @param scatterY the corner's y position.
     */
    protected void scatterMove(int scatterX, int scatterY) {
        changeX = scatterX - loc.getxLoc();
        changeY = scatterY - loc.getyLoc();
    }

    /**
     * Ghosts in the flee state move away from paku's current distance, but may not always succeed with that movement.
     */
    protected void fleeMove() {
        changeX = loc.getxLoc() - Paku.getInstance().getLoc().getxLoc();
        changeY = loc.getyLoc() - Paku.getInstance().getLoc().getyLoc();
    }

    /**
     * How a ghost moves when eaten. will try to get to the jail door (14,11). The ghost will speed up every three
     * times this method is called to assist in this process.
     */
    protected void eatenMove() {
        if (loc.getxLoc() == JAIL_DOOR) {
            if (loc.getyLoc() == (JAIL_TOP - 1) && loc.getyLoc() < JAIL_BOTTOM) {
                facingDirection = Direction.down;
                jailSkip = true;
            }
        } else {
            changeX = JAIL_DOOR - loc.getxLoc();
            changeY = (JAIL_TOP + 1) - loc.getyLoc();
        }
    }

    /**
     * Main logic of movement for ghosts outside of jail. Will move the ghost, and maybe turn them based on logic.
     * See comments in the method for more details.
     */
    protected void calculateMove() {
        int randomInt = random.nextInt(10);
        if (!jailSkip) {//jailSkip is a value only true when a ghost is entering or leaving the jail.

            absoluteX = Math.abs(changeX);
            absoluteY = Math.abs(changeY);
            //testAmount is used as an offset for testing the turns
            if (facingDirection.equals(Direction.up) || facingDirection.equals(Direction.left)) {
                testAmount = -1;
            } else {
                testAmount = 1;
            }
            // Up and Down movement testing for turns
            if (facingDirection.equals(Direction.up) || facingDirection.equals(Direction.down)) {
                if (randomInt > 1) {
                    if (loc.getxLoc() == 9 || loc.getxLoc() == 18)//loc.getxLoc() == 19
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
                }
                //This is one of the original author's things he added. It's a random chance for the ghosts
                //to turn where they don't in the Pacman game.
                else if (randomInt > 8) {
                    turnUpDown();
                }
            }
            // Left and Right movement testing for turns
            else if (facingDirection.equals(Direction.left) || facingDirection.equals(Direction.right)) {
                if (randomInt > 1) {
                    if ((loc.getxLoc() > 9) && (loc.getxLoc() < 18))//loc.getxLoc() == 19
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

        }
        if (!state.equals(GhostState.flee)) {
            moveNotTurn();
        }
        //Fleeing ghosts can only move every other movement call
        else if (!(loc.getyLoc() == 14))
            if (!(loc.getxLoc() < 6 && loc.getxLoc() > 21))//loc.getxLoc() == 22
                if (!alternate) {
                    moveNotTurn();
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
                loc.setyLoc(loc.getyLoc() - 1);
        } else if (facingDirection.equals(Direction.right)) {
            if ((int)row.get(loc.getxLoc() + 1) > 0)
                loc.setxLoc(loc.getxLoc() + 1);
        } else if (facingDirection.equals(Direction.down)) {
            if ((int)rowDown.get(loc.getxLoc()) > 0 || jailSkip)
                loc.setyLoc(loc.getyLoc() + 1);
        } else if (facingDirection.equals(Direction.left)) {
            if ((int)row.get(loc.getxLoc() - 1) > 0)
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
     * @param score the score to be incremented.
     * @return score added for gameData's bonus value
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
    public static void resetMultiplier()
    {
        multiplier = 1;
    }

    /**
     * Method to set the ghost to a fleeing state. If the ghost is not fleeing or eaten the ghost will have their current
     * state saved in storedState, and the ghost has its direction flipped. If the ghost is already fleeing nothing will happen.
     */
    public void makeFlee()
    {
        if(!state.equals(GhostState.flee) && !state.equals(GhostState.eaten)) {
            storedState = state;
            state = GhostState.flee;
            if (facingDirection.equals(Direction.left))
                facingDirection = Direction.right;
            else if (facingDirection.equals(Direction.right))
                facingDirection = Direction.left;
            else if (facingDirection.equals(Direction.up))
                facingDirection = Direction.down;
            else if (facingDirection.equals(Direction.down))
                facingDirection = Direction.up;
        }
    }

    /**
     * getter for globalFleeCounter
     * @return globalFleeCounter
     */
    public static int getGlobalFleeCounter()
    {
        return globalFleeTimer;
    }

    /**
     * Starts the flee timer for all ghosts. Timer is determined by the current level's frightTimer variable, until level
     * 21 (20 in the code), where the game stops increasing difficulty.
     */
    public static void startGlobalFleeCounter()
    {
        if (GameData.getInstance().getGamelevel() <= 20) {
            globalFleeTimer = frightTimers.get(GameData.getInstance().getGamelevel());
        } else {
            globalFleeTimer = frightTimers.get(20);
        }
    }
    public static void decrementGlobalFleeCounter()
    {
        globalFleeTimer--;
    }
    /**
     * endingFleeProtocol is used to reset the ghost to the state before they were fleeing, unless they were eaten.
     * Then the storedState variable is reset to null, and the ghosts will set their blinking values to false.
     */
    public abstract void endingFleeProtocol();

    /**
     * Alternates the current blink value of the ghost that calls it.
     */
    public abstract void blink();
    public abstract void resetLocation();

    /**
     * setupTimers is used when the ghosts are first built to set the preset timers for each behavior level.
     * These were derived by taking the pascal code's values, dividing them by 60 to get seconds, converting that value
     * into milliseconds, and then dividing it by 40. 40 milliseconds is the speed of which each new frame is sent to
     * the game from the Tomcat server. The last two of each one are there to match the pascal code.
     */
    public static void setupTimers()
    {
        level1behaviors = new ArrayList<>();
        level2to4Behaviors = new ArrayList<>();
        level5PlusBehaviors = new ArrayList<>();
        levelStates = new ArrayList<>();
        frightTimers = new ArrayList<>();
        blinkTimers = new ArrayList<>();

        level1behaviors.add(175);
        level1behaviors.add(500);
        level1behaviors.add(175);
        level1behaviors.add(500);
        level1behaviors.add(125);
        level1behaviors.add(500);
        level1behaviors.add(125);
        level1behaviors.add(1);
        level1behaviors.add(1);

        level2to4Behaviors.add(175);
        level2to4Behaviors.add(500);
        level2to4Behaviors.add(175);
        level2to4Behaviors.add(500);
        level2to4Behaviors.add(125);
        level2to4Behaviors.add(21300);
        level2to4Behaviors.add(1);
        level2to4Behaviors.add(1);

        level5PlusBehaviors.add(125);
        level5PlusBehaviors.add(500);
        level5PlusBehaviors.add(125);
        level5PlusBehaviors.add(500);
        level5PlusBehaviors.add(125);
        level5PlusBehaviors.add(21300);
        level5PlusBehaviors.add(1);
        level5PlusBehaviors.add(1);

        levelStates.add(GhostState.scatter);
        levelStates.add(GhostState.chase);
        levelStates.add(GhostState.scatter);
        levelStates.add(GhostState.chase);
        levelStates.add(GhostState.scatter);
        levelStates.add(GhostState.chase);
        levelStates.add(GhostState.scatter);
        levelStates.add(GhostState.chase);

        frightTimers.add(550);
        frightTimers.add(500);
        frightTimers.add(450);
        frightTimers.add(400);

        frightTimers.add(350);
        frightTimers.add(550);
        frightTimers.add(350);
        frightTimers.add(350);

        frightTimers.add(200);
        frightTimers.add(500);
        frightTimers.add(350);
        frightTimers.add(200);

        frightTimers.add(200);
        frightTimers.add(400);
        frightTimers.add(200);
        frightTimers.add(200);

        frightTimers.add(0);
        frightTimers.add(200);
        frightTimers.add(0);
        frightTimers.add(0);

        frightTimers.add(0);

        blinkTimers.add(250);
        blinkTimers.add(250);
        blinkTimers.add(250);
        blinkTimers.add(250);

        blinkTimers.add(250);
        blinkTimers.add(250);
        blinkTimers.add(250);
        blinkTimers.add(250);

        blinkTimers.add(150);
        blinkTimers.add(250);
        blinkTimers.add(250);
        blinkTimers.add(150);

        blinkTimers.add(150);
        blinkTimers.add(250);
        blinkTimers.add(150);
        blinkTimers.add(150);

        blinkTimers.add(0);
        blinkTimers.add(150);
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
            timer = level2to4Behaviors.get(timerIndex);
            state = levelStates.get(timerIndex);
        }
        else
        {
            timerIndex = 0;
            timer = level5PlusBehaviors.get(timerIndex);
            state = levelStates.get(timerIndex);
        }

    }

    /**
     * isBlinking is used when the ghosts are Fleeing to determine when to change their sprite to white instead of blue.
     * @return true if the globalFleeTimer integer is less than the current level's blinkTimer. Returns false if the
     * globalFleeTimer is greater than or equal to the current level's blink timer, equal to to avoid any possible issues
     * with the 0 count blink timers, or when the game's level is above 21 (20 in the code), as that's where the
     * game switches to the fixed values of level 21, which is 0 for the count.
     */
    public static boolean isBlinking(){
        if(GameData.getInstance().getGamelevel() < 21)
        {
            if(globalFleeTimer < blinkTimers.get(GameData.getInstance().getGamelevel()))
            {
                return true;
            }
            else
                return false;
        }
        else
        {
            return false;
        }
    }


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
        globalFleeTimer = i;
    }
    public static void setGlobalFleeCounter(int counter)
    {
        globalFleeTimer = counter;
    }
    public GhostState getStoredState()
    {
        return storedState;
    }
}