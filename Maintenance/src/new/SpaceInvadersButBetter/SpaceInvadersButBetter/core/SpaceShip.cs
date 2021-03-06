﻿using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpaceInvadersButBetter.core
{
    //---------------------------------------------------------------------
    // Players SpaceShip class that creates and specifies the players
    // spaceship
    //---------------------------------------------------------------------
    public class SpaceShip : GameObject
    {
        private const int START_POSITION_X = 245;
        private const int START_POSITION_Y = 380;
        private const int MOVE_INTERVAL = 5;

        private int lifes;
        private bool isDead;

        /**
         * Constructor, start in middle of screen, 3 lifes, not dead
         */
        public SpaceShip() : base(Resources.space_ship)
        {
            X = START_POSITION_X;
            Y = START_POSITION_Y;
            isDead = false;
            lifes = 3;
        }

        /**
         * Shows the ship has been hit
         */
        public void SpaceShipCrash()
        {
            SetMainImage(Resources.space_ship_dead);
           
        }

        /**
         * Puts the ship back into the starting position
         */ 
        public void ResetPosition()
        {
            X = START_POSITION_X;
            Y = START_POSITION_Y;
        }

        /**
         * Resets the ship image to the original
         */
        public void ResetImage()
        {
            SetMainImage(Resources.space_ship);
        }


        /**
         * Return lifes left
         */
        public int getLifes()
        {
            return lifes;
        }

        /**
         * Player hit,
         * return true - still alive (lifes > 0)
         * return false - dead (lifes = 0)
         */
        public bool hitAndIsAlive()
        {
            lifes -= 1;
            if (lifes == 0)
                kill();
            return !isDead;
            
        }

        /**
         * Return if is alive
         */
        public bool isAlive()
        {
            return !isDead;
        }

        /**
         * Set dead to false
         */
        public void kill()
        {
            isDead = true;
        }

        /**
         * Move player left by interval
         */
        public void MoveLeft()
        {
            X -= MOVE_INTERVAL;
            if (X < 0)
                X = 0;
        }

        /**
         * Move player right my interval up to screen limit
         */
        public void MoveRight(int screenLimit)
        {
            X += MOVE_INTERVAL;
            if (X > screenLimit - ImageBounds.Width + 10)
                X = screenLimit - ImageBounds.Width + 10;
        }

        /**
         * Reset  player states
         */
        public void reset()
        {
            lifes = 3;
            isDead = false;
            X = 245;
            Y = 380;
        }

        /**
         * Draw player if not dead
         */
        public void draw(Graphics g)
        {
            if(!isDead)
                base.Draw(g);
        }
    }
}
