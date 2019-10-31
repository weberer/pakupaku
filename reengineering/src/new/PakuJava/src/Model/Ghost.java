/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;/*
import Model.movingGameObject;
import org.json.simple.JSONObject;
import Controller.Controls;*/

import java.util.ArrayList;
import java.util.Random;

/**
 * @author kruge
 */

public abstract class Ghost extends MovingGameObject {

    //constants from original code
    protected final int FAR_RIGHT = 26;
    private final int JAIL_BOTTOM = 17;
    private final int JAIL_TOP = 12;
    private final int JAIL_LEFT = 10;
    private final int JAIL_RIGHT = 17;
    private final int JAIL_DOOR = 13;
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
    protected int exitCounter;
    protected int howFar;
    protected int fleeTotal;
    protected static int multiplier = 1;
    GhostState state;

    public Ghost() {
        random = new Random();
    }

    protected boolean inJail() {
        if (loc.getxLoc() >= JAIL_LEFT && loc.getxLoc() <= JAIL_RIGHT) {
            if (loc.getyLoc() <= JAIL_BOTTOM && loc.getyLoc() >= JAIL_TOP)
                return true;
            else
                return false;
        } else
            return false;
    }

    protected void jailMove() {
        if (state.equals(GhostState.eaten) && loc.getyLoc() < JAIL_TOP) {
            loc.setyLoc(loc.getyLoc() + 1);
            if (loc.getyLoc() == JAIL_BOTTOM)
                state = GhostState.scatter;

        } else {
            if (exitCounter < 0) {
                if (loc.getyLoc() == JAIL_TOP) {
                    facingDirection = Direction.down;
                } else if (loc.getyLoc() == JAIL_BOTTOM) {
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
                    loc.setyLoc(loc.getyLoc() + 1);
                    break;
                case down:
                    loc.setyLoc(loc.getyLoc() - 1);
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

    protected void scatterMove(int scatterX, int scatterY) {
        changeX = scatterX - loc.getxLoc();
        changeY = scatterY - loc.getyLoc();
    }

    protected void fleeMove() {
        changeX = loc.getxLoc() - Paku.getInstance().getLoc().getxLoc();
        changeY = loc.getyLoc() - Paku.getInstance().getLoc().getyLoc();
    }

    protected void eatenMove() {
        if (loc.getyLoc() == JAIL_DOOR) {
            if (loc.getyLoc() >= (JAIL_BOTTOM + 4) && loc.getyLoc() > JAIL_BOTTOM) {
                facingDirection = Direction.down;
                jailSkip = true;
            }
        } else {
            changeX = EATEN_X - loc.getxLoc();
            changeY = EATEN_Y - loc.getyLoc();
        }
    }

    protected void calculateMove() {
        int randomInt = random.nextInt(10);
        ArrayList row = map.get(loc.getyLoc());
        ArrayList rowUp = map.get(loc.getyLoc() - 1);
        ArrayList rowDown = map.get(loc.getyLoc() + 1);
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
                } else if ((int)map.get(loc.getyLoc() + testAmount).get(loc.getxLoc()) == 0 || allowTurn) {
                    turnUpDown();
                } else if (randomInt > 8) {
                    turnUpDown();
                }
            } else if (facingDirection.equals(Direction.left) || facingDirection.equals(Direction.right)) {
                if (randomInt > 1) {
                    if (loc.getxLoc() > 9 && loc.getxLoc() < 18)
                        if (loc.getyLoc() > 9 || loc.getyLoc() < 22)
                            allowTurn = true;
                        else
                            allowTurn = false;
                    else
                        allowTurn = false;

                } else
                    allowTurn = false;
                if (absoluteX > absoluteY || changeY > 0) {
                    turnLeftRight();
                }
                if ((int)map.get(loc.getyLoc()).get(loc.getxLoc() + testAmount) == 0 || randomInt > 8) {
                    turnLeftRight();
                }
            }
            // left/right check
            // jailskip check
        }
        if (!state.equals(GhostState.flee)) {
            if (!(loc.getyLoc() == 14))
                if (!(loc.getxLoc() < 6 && loc.getxLoc() > 21))
                    if (!alternate) {

                        if (facingDirection.equals(Direction.up)) {
                            if ((int)rowUp.get(loc.getxLoc())> 0)
                                loc.setyLoc(loc.getyLoc() - 1);
                            else
                                loc.setyLoc(loc.getyLoc() - 1);
                        } else if (facingDirection.equals(Direction.right)) {
                            if ((int)row.get(loc.getxLoc() + 1) > 0)
                                loc.setxLoc(loc.getxLoc() + 1);
                            else
                                loc.setxLoc(loc.getxLoc() + 1);
                        } else if (facingDirection.equals(Direction.down)) {
                            if ((int)rowDown.get(loc.getxLoc()) > 0 || jailSkip)
                                loc.setyLoc(loc.getyLoc() + 1);
                            else
                                loc.setyLoc(loc.getyLoc() + 1);
                        } else if (facingDirection.equals(Direction.left)) {
                            if ((int)row.get(loc.getxLoc() - 1) > 0)
                                loc.setxLoc(loc.getxLoc() - 1);
                            else
                                loc.setxLoc(loc.getxLoc() - 1);
                        }
                    }
        }



    }
    private void turnUpDown() {
        if (changeX > 0) {
            if ((int)map.get(loc.getyLoc()).get(loc.getxLoc() + 1) > 0) {
                facingDirection = Direction.right;
            } else if ((int)map.get(loc.getyLoc()).get(loc.getxLoc() - 1) > 0) {
                if ((int)map.get(loc.getyLoc() - testAmount).get(loc.getxLoc()) == 0) {
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

    private void turnLeftRight() {
        if(changeY > 0)
        {
            if((int)map.get(loc.getyLoc() + 1).get(loc.getxLoc()) > 0)
            {
                facingDirection = Direction.down;
            }
            else if((int)map.get(loc.getyLoc() - 1).get(loc.getxLoc()) > 0)
            {
                if(allowTurn && (int)map.get(loc.getyLoc()).get(loc.getxLoc() + 1) == 0)
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
            else if((int)map.get(loc.getyLoc() + 1).get(loc.getxLoc()) == 0 &&
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
    public void addScore(Score score)
    {
        score.addScore(SCORE * multiplier);
        if(multiplier != 32 )
            multiplier = multiplier * 2;
        this.setState(GhostState.eaten);
    }
    public void resetMultiplier()
    {
        multiplier = 1;

    }


    public abstract void resetLocation();
    public abstract void recordLocation();

}
