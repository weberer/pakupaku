/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author kruge
 */
public class Fruit
{
    private final int CHERRY = 1;
    private final int STRAWBERRY = 2;
    private final int ORANGE = 3;
    private final int APPLE = 5;
    private final int MELON = 7;
    private final int GALAXIAN = 9;
    private final int BELL = 11;
    private final int KEY = 13;
    private final int CHERRY_SCORE = 100;
    private final int STRAWBERRY_SCORE = 300;
    private final int ORANGE_SCORE = 500;
    private final int APPLE_SCORE = 700;
    private final int MELON_SCORE = 1000;
    private final int GALAXIAN_SCORE = 2000;
    private final int BELL_SCORE = 3000;
    private final int KEY_SCORE = 5000;
    enum Fruits{
        none,
        cherry,
        strawberry,
        orange,
        apple,
        melon,
        galaxianBoss,
        bell,
        key;
    }
    private Fruits fruit;
    private int score;
    public Fruit(int level)
    {
        if(level == CHERRY)
        {
            fruit = Fruits.cherry;
            score = CHERRY_SCORE;
        }
        else if(level == STRAWBERRY)
        {
            fruit = Fruits.strawberry;
            score = STRAWBERRY_SCORE;
        }
        else if(level == ORANGE || level == ORANGE + 1)
        {
            fruit = Fruits.orange;
            score = ORANGE_SCORE;
        }
        else if(level == APPLE || level == APPLE + 1)
        {
            fruit = Fruits.apple;
            score = APPLE_SCORE;
        }
        else if(level == MELON || level == MELON + 1)
        {
            fruit = Fruits.melon;
            score = MELON_SCORE;
        }
        else if(level == GALAXIAN || level == GALAXIAN + 1)
        {
            fruit = Fruits.galaxianBoss;
            score = GALAXIAN_SCORE;
        }
        else if(level == BELL || level == BELL + 1)
        {
            fruit = Fruits.bell;
            score = BELL_SCORE;
        }
        else if(level >= KEY)
        {
            fruit = Fruits.key;
            score = KEY_SCORE;
        }
    }

    public int getScore() {
        return score;
    }

}