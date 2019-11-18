using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using SpaceInvadersButBetter.core;
using SpaceInvadersButBetter.Model;

namespace SpaceInvadersButBetter.Controller
{
    //---------------------------------------------------------------------
    // The GameLogic class handles game logic and communicates it back to the view
    //---------------------------------------------------------------------
    public class GameLogic
    {

        private bool gameover;



        //increments with higher levels
        private int alien_speed = 6;
        private int alien_speed_factor = 1;
        private int alien_count = 66;


        private int TimerCounter = 0;
        private int MenuCount = 0;
        private int blinkCount = 0;
        private int scoreScrollCount = 0;


        private const int NUMBER_OF_SHIELDS = 4;
        private const int NUMBER_OF_ALIEN_ROWS = 6;
        private const int NUMBER_OF_ALIENS_PER_ROW = 11;
        private const int CREDIT_BLINK_COUNT = 8;
        private const double SPEEP_INCREASE_FACTOR = 1.25;

        private int credits;
        private List<Shield> Shields = new List<Shield>();

        private SpaceShip player;
        private Alien[,] alienGroup = new Alien[NUMBER_OF_ALIEN_ROWS, NUMBER_OF_ALIENS_PER_ROW];
        private List<Bullet> bullets = new List<Bullet>();
        private List<Bullet> alienbullets = new List<Bullet>();


        private GameView gameForm;
        private GameBoxForm gameBox;
        private GameData data;
        public GameLogic(GameBoxForm boxForm)
        {
            credits = 0;
            gameBox = boxForm;
        }
        public void SetGameView(GameView view)
        {
            this.gameForm = view;
        }
        /**
         * Creates Aliens for board
         */

       
        private bool StartScreenActive = true;
        

       

        public void InitializeAliens(int level)
        {
            for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
            {
                for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
                {
                    alienGroup[i, j] = new Alien(alien_speed_factor, Resources.invader_open, Resources.invader_closed, (2 + i), j);
                }
            }
        }

        /**
         * Method called when space bar is hit to shoot a bullet or reset game in over
         */
        public void ShootButton(Keys keydown_shoot)
        {
            if (keydown_shoot == Keys.Space)
            {
                if (!gameover)
                {
                    int startX = player.Position.X + (Resources.space_ship.Width / 2) - 10;
                    int startY = player.Position.Y - (Resources.space_ship.Height / 2) + 10;
                    Bullet bullet = new Bullet(startX, startY, true);
                    bullets.Add(bullet);
                }
                else
                {

                    ResetGameStates();
                    ResetPlayer();
                    ResetAliens();
                    ResetShields();
                    ResetBullets();
                }
            }
        }

        /**
         * Resets game states for logic
         */
        private void ResetGameStates()
        {
            gameover = false;
            alien_speed = 6;
            alien_speed_factor = 1;
            alien_count = 66;
            data.resetLevelScore();
            gameForm.setLevelLabel(data.getLevel().ToString());
            gameForm.setScoreLabel(data.getScore().ToString());
        }

        /**
        * Resets player and lifes
        */
        private void ResetPlayer()
        {
            player.reset();
            gameForm.setLivesLabel(player.getLifes().ToString());
        }

        /**
         * Clears aliens and resets them
         */
        public void ResetAliens()
        {
            Array.Clear(alienGroup, 0, alienGroup.Length);
            for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
            {
                for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
                {
                    alienGroup[i, j] = new Alien(alien_speed_factor, Resources.invader_open, Resources.invader_closed, (2 + i + 0), j);
                }
            }
        }

        /**
         * Clears sheilds and resets them
         */
        private void ResetShields()
        {
            Shields.Clear();
            gameForm.InitializeObject_Shields();
        }

        /**
        * Clears bullet lists
        */
        private void ResetBullets()
        {
            alienbullets.Clear();
            bullets.Clear();
        }

        /**
         * Updates score with given points and displays
         */
        public void UpdateScore(int points)
        {
            int score = data.getScore();
            score += 10;
            data.setScore(score);
            gameForm.setScoreLabel(score.ToString());       
        }

        /**
         * Increments level, speed factor, and resets aliens
         */
        public void NextLevel()
        {
            int level = data.getLevel();
            data.setLevel(++level); //level++
            gameForm.setLevelLabel(level.ToString()); //update level on screen
          
     
            alien_speed_factor = Convert.ToInt32(alien_speed_factor * SPEEP_INCREASE_FACTOR);
            ResetAliens();
            alien_count = 66;
        }

        /**
         * Insert coin method, updates label and count
         */
        public void CoinInsert()
        {
            int coinCount = data.getCoinCount();
            if (coinCount < 1)
            {
                coinCount++;
                data.setCoinCount(coinCount);
                gameForm.setCoinCountLabel(coinCount.ToString());

                gameForm.setHitSpaceVisibility(true);
     
            }
        }

        /**
        * Decrements coint when used for game play
        */
        public void CoinDecrement()
        {
            int coinCount = data.getCoinCount();
            if (coinCount > 0)
            {
                data.setCoinCount(--coinCount);
                gameForm.setCoinCountLabel(coinCount.ToString());
      
            }
        }


        /**
         * Creates 4 sheilds
         */
        /*
       private void InitializeObject_Shields()
       {
           for (int i = 0; i < NUMBER_OF_SHIELDS; i++)
           {
               Shield shield = new Shield();
               Shields.Add(shield);
               int shieldX = Shields[i].GetBounds().Width + 30 + (i * 135);
               int shieldY = ClientRectangle.Bottom - (Shields[i].GetBounds().Height + 100);
               Shields[i].Position.X = shieldX;
               Shields[i].Position.Y = shieldY;

               Label label = new Label();
               ShieldHealth.Add(label);
               ShieldHealth[i].Text = Shields[i].getHealth().ToString();
               ShieldHealth[i].Location = new Point(shieldX - 17, shieldY + 8);
               ShieldHealth[i].BackColor = Color.Transparent;
               ShieldHealth[i].TextAlign = ContentAlignment.MiddleCenter;
               this.Controls.Add(ShieldHealth[i]);
           }
       }
       */

                    //ResetGameStates();
                    //ResetPlayer();
                    //ResetAliens();
                    //ResetSheilds();
                    //ResetBullets();
         //       }
        //    }
        
        public void addCredit()
        {
            if (credits < 9)
                credits++;
            gameForm.UpdateCredits(credits);
           
        }
        public void DecrementCredits()
        {
            if (credits > 0)
            {
                credits--;
                gameForm.UpdateCredits(credits);
            }
        }
        public bool IsStartScreenActive()
        {
            return StartScreenActive;
        }
        public void StartGame()
        {
            if (credits > 0)
            {
                if (StartScreenActive)
                {
                    gameForm.EraseStartScreen();
                    DecrementCredits();
                    StartScreenActive = false;
                }
            }
        }
        public void EndGame()
        {
            //High Score Handling Code here.
            StartScreenActive = true;
            gameForm.ShowStartScreen();
           
        }

        /**
         * Testing method to fetch the credits int for the test method.
         */
        public int GetCredits()
        {
            return credits;
        }

        /**
         * Creates player spaceship object
         */
        public void InitializeSpaceShip()
        {
            player = new SpaceShip();
            gameForm.setLivesLabel(player.getLifes().ToString());
           
 
        }

        /**
         * Checks for bullet collision with sheild
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
                            ShieldHealth[shieldIndexHit].Text = Shields[shieldIndexHit].getHealth().ToString();
                            if (Shields[shieldIndexHit].getHealth() <= 0)
                            {
                                Shields.RemoveAt(shieldIndexHit);
                                this.Controls.Remove(ShieldHealth[shieldIndexHit]);
                                ShieldHealth.RemoveAt(shieldIndexHit);
                            }
                        }
                    }
                }
            }
        }


    }
}
