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
        private int alien_speed = 6; // 6
        private double alien_speed_factor = 1;
        private double UFO_speed_factor = 2;
        private int alien_count = 66;
        private const int MAX_ALIENS = 66;

        private const int NUMBER_OF_SHIELDS = 4;
        private const int NUMBER_OF_ALIEN_ROWS = 6;
        private const int NUMBER_OF_ALIENS_PER_ROW = 11;
        private const int CREDIT_BLINK_COUNT = 8;
        private const double SPEEP_INCREASE_FACTOR = 1.5;

        private int credits;
        private List<Shield> Shields = new List<Shield>();

        private SpaceShip player;
        private Alien[,] alienGroup = new Alien[NUMBER_OF_ALIEN_ROWS, NUMBER_OF_ALIENS_PER_ROW];
        private List<Bullet> bullets = new List<Bullet>();
        private List<Bullet> alienbullets = new List<Bullet>();




        private GameView gameForm;
        private GameBoxForm gameBox;
        private GameData data;
        private CollisionHandler collisionHandler;
        public GameLogic(GameBoxForm boxForm)
        {
            credits = 0;
            gameBox = boxForm;
            data = new GameData();
        }
        public void SetGameView(GameView view)
        {
            this.gameForm = view;
        }
        
        public void SetupCollisionHandler()
        {
            collisionHandler = new CollisionHandler(this, gameForm);
            collisionHandler.SetUpObjects(bullets, alienbullets, player, alienGroup, Shields);
        }

        private bool StartScreenActive = true;



        /**
         * Creates Aliens for board
         */
        public Alien[,] InitializeAliens(int level)//parameter is never used.
        {
            for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
            {
                for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
                {
                    alienGroup[i, j] = new Alien(alien_speed_factor, Resources.invader_open, Resources.invader_closed, (2 + i), j);
                }
            }
            return alienGroup;
        }

        /**
        * Creates UFO for board
        */
        public UFO InitializeUFO(int level)
        {
            return new UFO(UFO_speed_factor, Resources.UFO);
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
                    if (!StartScreenActive)
                    {
                        int startX = player.Position.X + (Resources.space_ship.Width / 2) - 10;
                        int startY = player.Position.Y - (Resources.space_ship.Height / 2) + 10;
                        Bullet bullet = new Bullet(startX, startY, true);
                        if(bullets.Count==0)
                            bullets.Add(bullet);
                        gameForm.UpdateBullets(bullets);
                    }
                    else
                    {
                        StartGame();
                    }
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
            alien_count = MAX_ALIENS;
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
        * Reset UFO
        */
        public void ResetUFO()
        {

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
            if (alien_count == 0)
            {
                int level = data.getLevel();
                data.setLevel(++level); //level++
                gameForm.setLevelLabel(level.ToString()); //update level on screen
                

                alien_speed_factor = Convert.ToInt32(alien_speed_factor * SPEEP_INCREASE_FACTOR);
                ResetAliens();
                ResetBullets();
                alien_count = MAX_ALIENS;
            }
        }

        /**
         * Creates 4 sheilds
         */

        public List<Shield> InitializeObject_Shields()
        {
            Shields.Clear();
            for (int i = 0; i < NUMBER_OF_SHIELDS; i++)
            {
                Shield shield = new Shield();
                Shields.Add(shield);
                int shieldX = Shields[i].GetBounds().Width + 30 + (i * 135);
                int shieldY = gameForm.GetShieldBottom(shield);
                Shields[i].Position.X = shieldX;
                Shields[i].Position.Y = shieldY;

                gameForm.SetupShieldLabel(shield, shieldX, shieldY);
            }
            return Shields;
        }

        public void addCredit()
        {
            if (data.GetCredits() < 9)
                data.AddCredit();
            gameForm.UpdateCredits(data.GetCredits());

        }
        public void DecrementCredits()
        {
            if (credits >= 0)
            {
                data.DecrementCredits();
                gameForm.UpdateCredits(data.GetCredits());
            }
        }
        public bool IsStartScreenActive()
        {
            return StartScreenActive;
        }
        public void StartGame()
        {
            if (data.GetCredits() > 0)
            {
                if (StartScreenActive)
                {
                    
                    data.DecrementCredits();
                    gameForm.UpdateCredits(data.GetCredits());
                    gameForm.EraseStartScreen();
                    StartScreenActive = false;
                    ResetBullets();

                }
            }
        }
        public void EndGame()
        {
            //High Score Handling Code here.
            gameForm.ResetGameObjects();
            ResetGameStates();
            player.reset();
            gameForm.setLivesLabel(player.getLifes().ToString());
            ResetBullets();
            StartScreenActive = true;
            gameover = false;
            gameForm.ShowStartScreen();

        }

        /**
         * Testing method to fetch the credits int for the test method.
         */
        public int GetCredits()
        {
            return data.GetCredits();
        }

        /**
         * Creates player spaceship object
         */
        public SpaceShip InitializeSpaceShip()
        {
            player = new SpaceShip();
            gameForm.setLivesLabel(player.getLifes().ToString());
            return player;

        }

        /**
         * Calls the CollisionHandler class.
         */
        public void CollisionCheck()
        {
            collisionHandler.CheckCollisions();
        }

        /*
         * GenerateAlienBullets selects the closest alive alien to the player and makes it fire a
         * new Bullet object. This object is added to the alienbullets list and is passed to the GameView
         * object.
         */ 
        public List<Bullet> GenerateAlienBullet()
        {
            List<Tuple<Alien, int>> bottomAliens = new List<Tuple<Alien, int>>();
            Alien nearest = null;
            for (int i = 0; i < 11; i++)
            {
                for (int k = 5; k > -1; k--)
                    if (!alienGroup[k, i].beenHit)
                    {
                        int posDiff = Math.Abs(alienGroup[k, i].Position.X - player.Position.X);
                        Tuple<Alien, int> pair = new Tuple<Alien, int>(alienGroup[k, i], posDiff);
                        bottomAliens.Add(pair);
                        break;
                    }
            }
            if (bottomAliens.Count != 0)
            {
                int finalDiff = bottomAliens[0].Item2;
                nearest = bottomAliens[0].Item1;
                foreach (Tuple<Alien, int> pair in bottomAliens)
                {
                    if (finalDiff > pair.Item2)
                    {
                        finalDiff = pair.Item2;
                        nearest = pair.Item1;
                    }
                }
            }
            if (nearest != null)
            {

                int startX = nearest.Position.X + 10;
                int startY = nearest.Position.Y + 30;
                Bullet bullet = new Bullet(startX, startY, false);
                alienbullets.Add(bullet);
            }
            return alienbullets;
        }
        /**
         * MovementHandlerPlayer handles the left and right movement of the SpaceShip object.
         * It will call the corresponding movement method in the SpaceShip class.
         */
        public void MovementHandlerPlayer(Keys joystick, int right)
        {
            if (joystick == Keys.Left)
            {
                player.MoveLeft();
            }
            else if (joystick == Keys.Right)
            {
                player.MoveRight(right);
            }
        }

        public void MovementHandlerBullets()
        {
            for (int i = 0; i < bullets.Count; i++)
                bullets[i].Move();
            gameForm.UpdateBullets(bullets);
            for (int i = 0; i < alienbullets.Count; i++)
                alienbullets[i].Move();
        }

        /**
         * The AnimateAliens method animates each alien object in the alienGroup
         * array and returns it to the main GameView object to redraw.
         */
        public Alien[,] AnimateAliens(int timerCounter)
        {
            if (timerCounter % 6 == 0)
            {
                for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
                {
                    for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
                    {
                        alienGroup[i, j].MoveInPlace();
                    }
                }
            }
            return alienGroup;
        }

        /**
         * Tests to see if the aliens that are alive have reached the bottom of the GameView object.
         * If this is true, this is a game over state that will end the game. Else nothing happens.
         */
        public void CheckForLanding(int bottom)
        {
            for (int r = 0; r < NUMBER_OF_ALIEN_ROWS; r++)
            {
                for (int c = 0; c < NUMBER_OF_ALIENS_PER_ROW; c++)
                {
                    if ((alienGroup[r, c].beenHit == false) && alienGroup[r, c].GetBounds().Bottom >= bottom)
                    {
                        alienGroup[r, c].beenHit = true;
                        gameForm.WriteScore(data.getScore());
                        gameover = true;
                    }
                }
            }
        }

        public void MoveAlienByFactorAndDirection(int width, int timerCounter)
        {
            if (timerCounter % alien_speed == 0)
            {
                for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
                {
                    for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
                    {
                        alienGroup[i, j].Move();
                    }
                }

                //get alien furthest to the right
                if (GetFarRightAlien() > width - alienGroup[4, 0].GetWidth())
                {
                    SetAllDirections(false);
                    for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
                    {
                        for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
                        {
                            alienGroup[i, j].MoveDown();
                        }
                    }
                }

                //get alien furthest to the left
                if (GetFarLeftAlien() < alienGroup[4, 0].GetWidth() / 3)
                {
                    SetAllDirections(true);
                    for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
                    {
                        for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
                        {
                            alienGroup[i, j].MoveDown();
                        }
                    }
                }
            }
        }

        /**
         * Get furthest alien to the right
         */
        private int GetFarRightAlien()
        {
            int max = 0;
            for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
            {
                for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
                {
                    int lastPos = alienGroup[i, j].Position.X;
                    if (lastPos > max)
                        max = lastPos;
                }
            }
            return max;
        }


        /**
        * Get furthest alien to the left
        */
        private int GetFarLeftAlien()
        {
            int min = int.MaxValue;
            for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
            {
                for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
                {
                    int firstPos = alienGroup[i, j].Position.X;
                    if (firstPos < min)
                        min = firstPos;
                }
            }
            return min;
        }

        /**
         * Set direction of all aliens (if moving right = true) (if moving left = false)
         */
        private void SetAllDirections(bool movingRight)
        {
            for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
            {
                for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
                {
                    alienGroup[i, j].movingRight = movingRight;
                }
            }
        }
        public bool IsGameOver()
        {
            return gameover;
        }

        public void GameOver()
        {
            gameover = true;
            gameForm.WriteScore(data.getScore());
        }

        public void KillAlien()
        {
            alien_count--;
            UpdateScore(10);
        }
    }
}

