using SpaceInvadersButBetter.Controller;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpaceInvadersButBetter.core
{
    class CollisionHandler
    {
        private const int NUMBER_OF_ALIEN_ROWS = 6;
        private const int NUMBER_OF_ALIENS_PER_ROW = 11;
        private const int SHIELD_WIDTH_ADJUST = 10;
        
        private Alien[,] alienGroup;
        private GameLogic logic;
        private GameView gameForm;
        private List<Bullet> bullets = new List<Bullet>();
        private List<Bullet> alienBullets = new List<Bullet>();
        private List<Shield> shields = new List<Shield>();
        private SpaceShip player;

        public CollisionHandler(GameLogic logic, GameView view)
        {
            alienGroup = new Alien[NUMBER_OF_ALIEN_ROWS, NUMBER_OF_ALIENS_PER_ROW];
            this.logic = logic;
            gameForm = view;
        }

        public void SetUpObjects(List<Bullet> bullets, List<Bullet> alienBullets, SpaceShip player,
            Alien[,] alienGroup, List<Shield> shields)
        {
            this.bullets = bullets;
            this.alienBullets = alienBullets;
            this.player = player;
            this.alienGroup = alienGroup;
            this.shields = shields;
        }
        /*
         * CheckCollisions is the main handler for CollisionHandler. When called, it calls all of
         * the collision checkers in the handler class. These are:
         * ShieldCheck
         * AlienCheck
         * AlienHitShield
         * AlienHitPersonCheck
         * AlienBulletsCheck
         * and checkShieldHitByAlien
         */
        public void CheckCollisions()
        {
            ShieldCheck();
            AlienCheck();
            AlienHitSheild();
            AlienHitPersonCheck();
            AlienBulletsCheck();
            checkShieldHitByAlien();
        }

        /**
         * AlienHitShield tests if a Bullet object spawned by an SpaceShip object intersects with a Shield object.
         * If there is an intersect, the Bullet is erased and the Shield is dealt 10 points of damage.
         * If the Shield's HP is 0 or less after the hit, the shield is erased from the game.
         */
        private void ShieldCheck()
        {
            for (int i = 0; i < bullets.Count; i++)
                if ((shields.Count > 0) && bullets[i].Y < shields[0].Y)
                {
                    bool delete = false;
                    int shieldIndexHit = -1;

                    for (int j = 0; j < shields.Count; j++)
                        if (shields[j].X < bullets[i].X && (shields[j].X + Resources.shield.Width - SHIELD_WIDTH_ADJUST) > bullets[i].X)
                        {
                            delete = true;
                            shieldIndexHit = j;
                        }

                    if (delete && shieldIndexHit != -1)
                    {
                        bullets.RemoveAt(i);
                        shields[shieldIndexHit].healthHit();
                        gameForm.UpdateShield(shields[shieldIndexHit].getHealth(), shieldIndexHit);

                        if (shields[shieldIndexHit].getHealth() <= 0)
                        {
                            shields.RemoveAt(shieldIndexHit);
                            gameForm.RemoveShield(shieldIndexHit);
                        }
                    }
                }
        }

        /**
         * AlienHitShield tests if a Bullet object spawned by an Alien object intersects with a Shield object.
         * If there is an intersect, the Bullet is erased and the Shield is dealt 5 points of damage.
         * If the Shield's HP is 0 or less after the hit, the shield is erased from the game.
         */
        private void AlienHitSheild()
        {
            for (int i = 0; i < alienBullets.Count; i++) // alien shot hit sheild
                if ((shields.Count > 0) && alienBullets[i].Y > shields[0].Y)
                {
                    bool delete = false;
                    int shieldIndexHit = -1;

                    for (int j = 0; j < shields.Count; j++)
                        if (shields[j].X < alienBullets[i].X && (shields[j].X + Resources.shield.Width - SHIELD_WIDTH_ADJUST) > alienBullets[i].X)
                        {
                            delete = true;
                            shieldIndexHit = j;
                        }

                    if (delete && shieldIndexHit != -1)
                    {
                        alienBullets.RemoveAt(i);
                        shields[shieldIndexHit].alienHealthHit();
                        gameForm.UpdateShield(shields[shieldIndexHit].getHealth(), shieldIndexHit);

                        if (shields[shieldIndexHit].getHealth() <= 0)
                        {
                            shields.RemoveAt(shieldIndexHit);
                            gameForm.RemoveShield(shieldIndexHit);
                        }
                    }
                }
        }
        /**
         * AlienCheck tests to see if an alien has been hit by a player's Bullet object, using
         * the checkBulletHit method. if the bullet does hit, the bullet is removed from the bullets
         * list and the GameView object's bullet list is updated.
         */
        public void AlienCheck()
        {
            for (int i = 0; i < bullets.Count; i++) //Alien Check
                if (checkBulletHit(bullets[i]))
                {
                    bullets.RemoveAt(i);
                    gameForm.UpdateBullets(bullets);
                }
        }

        /**
         * Helper method for AlienCheck
         * checkBulletHit checks if a player's Bullet object overlaps with an Alien object that has
         * a beenHit response of false. If there is an overlap with an alive Alien object, the Alien's
         * beenHit value is set to true and the score is increased by 10 points, then returns true. If the 
         * Bullet does not overlap an Alien or the Alien's beenHit value is true this method returns false.
         */
        private bool checkBulletHit(Bullet bullet)
        {
            for (int row = 0; row < NUMBER_OF_ALIEN_ROWS; row++)
                for (int column = 0; column < NUMBER_OF_ALIENS_PER_ROW; column++)
                    if ((alienGroup[row, column].beenHit == false) && alienGroup[row, column].GetBounds().IntersectsWith(bullet.GetBounds()))
                    {
                        alienGroup[row, column].beenHit = true;
                        logic.KillAlien();
                        return true;
                    }

            return false;
        }

        /**
         * Tests to see if an Alien object makes contact with the player's SpaceShip object. If
         * an Alien does make contact, the player is killed and the GameView object is set to
         * display the game over state.
         */
        private void AlienHitPersonCheck()
        {
            for (int row = 0; row < NUMBER_OF_ALIEN_ROWS; row++) //person hit  by alien check
                for (int column = 0; column < NUMBER_OF_ALIENS_PER_ROW; column++)
                    if ((alienGroup[row, column].beenHit == false) && alienGroup[row, column].GetBounds().IntersectsWith(player.GetBounds()))
                    {
                        //hit
             
                        alienGroup[row, column].beenHit = true;
                        player.kill();
                        logic.GameOver();
                       
                    }
        }
        /**
         * AlienBulletsCheck tests if a bullet fired by an Alien object overlaps with the player's
         * SpaceShip object's location. If there is an overlap, the bullet is erased and the player
         * loses a life. If the player is out of lifes, the game over screen will be displayed on
         * the GameView object.
         */
        private void AlienBulletsCheck()
        {
            for (int i = 0; i < alienBullets.Count; i++)
                if (player.GetBounds().IntersectsWith(alienBullets[i].GetBounds()))
                {
                    player.SpaceShipCrash(); //shows ship crash
                    if (!player.hitAndIsAlive())
                    {
                        player.kill();
                        logic.GameOver();
                    }
                    gameForm.SetLivesLabel(player.getLifes().ToString());
                    alienBullets.RemoveAt(i);
                }
        }

        /**
           * Calls checkShieldHit to determine if an Alien object and Shield object overlap. 
           * If checkShieldHit returns true, the shield object is deleted and the gameForm is told to
           * remove its instance of the shield.
           */
        private void checkShieldHitByAlien()
        {
            for (int i = 0; i < shields.Count; i++)
                if (checkShieldHit(shields[i]))
                {
                    shields.RemoveAt(i);
                    gameForm.RemoveShield(i);
                }
        }

        /**
           * Helper method for checkShieldHitByAlien. Checks if an alien object's location overlaps with a shield 
           * object's location on the GameView. returns true if there is an overlap, otherwise false.
           */
        private bool checkShieldHit(Shield shield)
        {
            for (int row = 0; row < NUMBER_OF_ALIEN_ROWS; row++)
                for (int column = 0; column < NUMBER_OF_ALIENS_PER_ROW; column++)
                    if ((alienGroup[row, column].beenHit == false) && alienGroup[row, column].GetBounds().IntersectsWith(shield.GetBounds()) && shield.getHealth() > 0)
                        return true;

            return false;
        }
    } 
}
