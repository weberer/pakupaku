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
        private const int NUMBER_OF_SHIELDS = 4;
        private const int NUMBER_OF_ALIEN_ROWS = 6;
        private const int NUMBER_OF_ALIENS_PER_ROW = 11;


        private SpaceShip player;
        private Alien[,] alienGroup = new Alien[NUMBER_OF_ALIEN_ROWS, NUMBER_OF_ALIENS_PER_ROW];
        private List<Bullet> bullets = new List<Bullet>();
        private List<Bullet> alienBullets = new List<Bullet>();
        private List<Shield> Shields = new List<Shield>();

        private GameLogic logic;
        private GameView gameForm;
        public CollisionHandler(GameLogic logic, GameView view)
        {
            this.logic = logic;
            gameForm = view;
        }

        public void SetUpObjects(List<Bullet> bullets, List<Bullet> alienBullets, SpaceShip Player,
            Alien[,] alienList, List<Shield> shields)
        {
            this.bullets = bullets;
            this.alienBullets = alienBullets;
            this.player = Player;
            this.alienGroup = alienList;
            this.Shields = shields;
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
            {
                if (Shields.Count > 0) //Shield Check
                {
                    if (bullets[i].Position.Y < Shields[0].Position.Y)
                    {
                        bool delete = false;
                        int shieldIndexHit = -1;
                        for (int j = 0; j < Shields.Count; j++)
                            if (Shields[j].Position.X < bullets[i].Position.X && (Shields[j].Position.X + Resources.shield.Width - 20) > bullets[i].Position.X)
                            {
                                delete = true;
                                shieldIndexHit = j;
                            }
                        if (delete && shieldIndexHit != -1)
                        {
                            bullets.RemoveAt(i);
                            Shields[shieldIndexHit].healthHit();
                            gameForm.UpdateShield(Shields[shieldIndexHit].getHealth(), shieldIndexHit);
                            if (Shields[shieldIndexHit].getHealth() <= 0)
                            {
                                 Shields.RemoveAt(shieldIndexHit);
                                gameForm.RemoveShield(shieldIndexHit);
                            }
                        }
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
            {
                if (Shields.Count > 0) //Shield Check
                {
                    if (alienBullets[i].Position.Y > Shields[0].Position.Y)
                    {
                        bool delete = false;
                        int shieldIndexHit = -1;
                        for (int j = 0; j < Shields.Count; j++)
                            if (Shields[j].Position.X < alienBullets[i].Position.X && (Shields[j].Position.X + Resources.shield.Width - 20) > alienBullets[i].Position.X)
                            {
                                delete = true;
                                shieldIndexHit = j;
                            }
                        if (delete && shieldIndexHit != -1)
                        {
                            alienBullets.RemoveAt(i);
                            Shields[shieldIndexHit].alienHealthHit();
                            gameForm.UpdateShield(Shields[shieldIndexHit].getHealth(), shieldIndexHit);
                             if (Shields[shieldIndexHit].getHealth() <= 0)
                            {
                                Shields.RemoveAt(shieldIndexHit);
                                gameForm.RemoveShield(shieldIndexHit);
                            }
                        }
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
            {
                if (checkBulletHit(bullets[i]))
                {
                    bullets.RemoveAt(i);
                    gameForm.UpdateBullets(bullets);
                }
            }
        }

        /**
         * Helper method for AlienCheck
         * checkBulletHit checks if a player's Bullet object overlaps with an Alien object that has
         * a beenHit response of false. If there is an overlap with an alive Alien object, the Alien's
         * beenHit value is set to true and the score is increased by 10 points, then returns true. If the 
         * Bullet does not overlap an Alien or the Alien's beenHit value is true this method returns false.
         */
        private bool checkBulletHit(Bullet b)
        {
            for (int r = 0; r < NUMBER_OF_ALIEN_ROWS; r++)
            {
                for (int c = 0; c < NUMBER_OF_ALIENS_PER_ROW; c++)
                {
                    if ((alienGroup[r, c].beenHit == false) && alienGroup[r, c].GetBounds().IntersectsWith(b.GetBounds()))
                    {
                        alienGroup[r, c].beenHit = true;
                        logic.KillAlien();
                        return true;
                    }
                }
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
            for (int r = 0; r < NUMBER_OF_ALIEN_ROWS; r++) //person hit  by alien check
            {
                for (int c = 0; c < NUMBER_OF_ALIENS_PER_ROW; c++)
                {
                    if ((alienGroup[r, c].beenHit == false) && alienGroup[r, c].GetBounds().IntersectsWith(player.GetBounds()))
                    {
                        //hit
                        alienGroup[r, c].beenHit = true;
                        player.kill();
                        logic.GameOver();
                       
                    }
                }
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
            {
                if (player.GetBounds().IntersectsWith(alienBullets[i].GetBounds()))
                {
                    if (!player.hitAndIsAlive())
                    {
                        player.kill();
                        logic.GameOver();
                    }
                    gameForm.setLivesLabel(player.getLifes().ToString());
                    alienBullets.RemoveAt(i);
                }
            }
        }

        /**
           * Calls checkShieldHit to determine if an Alien object and Shield object overlap. 
           * If checkShieldHit returns true, the shield object is deleted and the gameForm is told to
           * remove its instance of the shield.
           */
        private void checkShieldHitByAlien()
        {
            for (int i = 0; i < Shields.Count; i++)
            {
                if (checkShieldHit(Shields[i]))
                {
                    Shields.RemoveAt(i);
                    gameForm.RemoveShield(i);
                }
            }
        }

        /**
           * Helper method for checkShieldHitByAlien. Checks if an alien object's location overlaps with a shield 
           * object's location on the GameView. returns true if there is an overlap, otherwise false.
           */
        private bool checkShieldHit(Shield s)
        {
            for (int r = 0; r < NUMBER_OF_ALIEN_ROWS; r++)
            {
                for (int c = 0; c < NUMBER_OF_ALIENS_PER_ROW; c++)
                {
                    if ((alienGroup[r, c].beenHit == false) && alienGroup[r, c].GetBounds().IntersectsWith(s.GetBounds()) && s.getHealth() > 0)
                    {
                        return true;
                    }
                }
            }
            return false;
        }
    } 
}
