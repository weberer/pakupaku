using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using SpaceInvadersButBetter.core;
using SpaceInvadersButBetter.Model;
using SpaceInvadersButBetter.views;

namespace SpaceInvadersButBetter.Controller
{
    //---------------------------------------------------------------------
    // The GameLogic class handles game logic and communicates it back to the view
    //---------------------------------------------------------------------
    public class GameLogic
    {
        private const int NUMBER_OF_ALIEN_ROWS = 6;
        private const int NUMBER_OF_ALIENS_PER_ROW = 11;
        private const int NUMER_OF_SHIELDS = 4;

        private GameView gameForm;
        private GameBoxForm gameBox;
        private GameData data;
        private CreditSystem credit;
        private CollisionHandler collisionHandler;
        private bool gameOver;

        //increments with higher levels
        private int alien_speed = 6; // 6
        private int alien_speed_factor = 1;
        private int alien_count = 66;
        private const int MAX_ALIENS = 66;

        private const double SPEEP_INCREASE_FACTOR = 1.25;

        private List<Shield> Shields = new List<Shield>();

        private SpaceShip player;
        private Alien[,] alienGroup;
        //private List<Bullet> bullets;
        //private List<Bullet> alienbullets;

        private ScoreUtility scoreUtil;

  
        private HighScoreForm highScore;
        private InitalsForm initalsForm;


        bool isGame = true;

        public GameLogic(GameBoxForm boxForm)
        {
            gameBox = boxForm;
            alienGroup = new Alien[NUMBER_OF_ALIEN_ROWS, NUMBER_OF_ALIENS_PER_ROW];
        }
        public void SetGameView(GameView view)
        {
            this.gameForm = view;
        }


        public void SetGameData(GameData data)
        {
            this.data = data;
        }

        public void SetCreditSystem(CreditSystem credit)
        {
            this.credit = credit;
        }

        public void SetHighScoreView(HighScoreForm highScore)
        {
            this.highScore = highScore;
        }

        public void SetInitialsForm(InitalsForm initals)
        {
            initalsForm = initals;
        }
        

        public void SetupCollisionHandler()
        {
            collisionHandler = new CollisionHandler(this, gameForm);
            collisionHandler.SetUpObjects(gameForm.GetBullets(), gameForm.GetAlienBullets(), player, gameForm.GetAlienGroup(), Shields);
        }

        private bool StartScreenActive = true;



        /**
         * Creates Aliens for board
         */
        public Alien[,] InitializeAliens(int level)
        {
            for (int i = 0; i < gameForm.GetNumberOfAlienRows(); i++)
            {
                for (int j = 0; j < gameForm.GetNumberOfAliensPerRow(); j++)
                {
                    alienGroup[i, j] = new Alien(alien_speed_factor, Resources.invader_open, Resources.invader_closed, (2 + i), j);
                }
            }
            return alienGroup;
        }

        /**
         * Method called when space bar is hit to shoot a bullet or reset game in over
         */
        public void ShootButton(Keys keydown_shoot)
        {
            if (keydown_shoot == Keys.Space)
            {
                if (!gameOver)
                {
                    if (!StartScreenActive)
                    {
                        int startX = player.X + (Resources.space_ship.Width / 2) - 10;
                        int startY = player.Y - (Resources.space_ship.Height / 2) + 10;
                        Bullet bullet = new Bullet(startX, startY, true);
                        gameForm.GetBullets().Add(bullet);
                        gameForm.UpdateBullets(gameForm.GetBullets());
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
            GameReset();
            alien_speed = 6;
            alien_speed_factor = 1;
            alien_count = MAX_ALIENS;
            data.resetLevelScore();

            gameForm.setLevelLabel(data.Level.ToString());

        }

        /**
        * Resets player and lifes
        */
        private void ResetPlayer()
        {
            player.reset();
            gameForm.SetLivesLabel(player.getLifes().ToString());
        }

        /**
         * Clears aliens and resets them
         */
        public void ResetAliens()
        {
            Array.Clear(alienGroup, 0, alienGroup.Length);
            for (int i = 0; i < gameForm.GetNumberOfAlienRows(); i++)
            {
                for (int j = 0; j < gameForm.GetNumberOfAliensPerRow(); j++)
                {
                    alienGroup[i, j] = new Alien(alien_speed_factor, Resources.invader_open, Resources.invader_closed, (2 + i + 0), j);
                }
            }
        }

        /**
        * Clears bullet lists
        */
        private void ResetBullets()
        {
            gameForm.GetAlienBullets().Clear();
            gameForm.GetBullets().Clear();
        }

        /**
         * Updates score with given points and displays
         */
        public void UpdateScore(int points)
        {
            int score = data.Score;
            score += 10;

            data.Score = score;
            gameForm.SetScoreLabel(score.ToString());

        }


        /**
         * Increments level, speed factor, and resets aliens
         */
        public void NextLevel()
        {
            if (alien_count == 0)
            {

                int level = data.Level;
                data.Level++;

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
            for (int i = 0; i < NUMER_OF_SHIELDS; i++)
            {
                Shield shield = new Shield();
                Shields.Add(shield);
                int shieldX = Shields[i].GetBounds().Width + 30 + (i * 135);
                int shieldY = gameForm.GetShieldBottom(shield);
                Shields[i].X = shieldX;
                Shields[i].Y = shieldY;

                gameForm.SetupShieldLabel(shield, shieldX, shieldY);
            }
            return Shields;
        }


        //public void AddCredit()
        //{
        //    if (credit.GetCredits() < 9)
        //        credit.AddCredit();
        //    gameForm.UpdateCredits(credit.GetCredits());

        //}
        
        
        //public void DecrementCredits()
        //{
        //    if (credit.GetCredits() >= 0)
        //    {
        //        credit.DecrementCredits();
        //        gameForm.UpdateCredits(credit.GetCredits());
        //    }
        //}

       
        public bool IsStartScreenActive()
        {
            return StartScreenActive;
        }
        public void StartGame()
        {

            if (credit.Credits > 0)

            {
                if (StartScreenActive)
                {
                    

                    credit.DecrementCredits();
                    gameForm.UpdateCredits(credit.Credits);

                    highScore.UpdateCredits(credit.Credits);

                    gameForm.EraseStartScreen();
                    StartScreenActive = false;
                    ResetBullets();

                }
            }
        }
        public void EndGame()
        {
            gameForm.ResetGameObjects();
            player.reset();
            gameForm.SetLivesLabel(player.getLifes().ToString());
            ResetBullets();
            StartScreenActive = true;


            GameReset();
            if (scoreUtil.IsHighScore(data.Score))
            {
                StartInitialsForm();
            }       
            else
            {
                gameForm.ShowStartScreen();
                ResetGameStates();
            }
                

        }

        /**
         * Sets the gameOver boolean to false
         */
        public void GameReset()
        {
            gameOver = false;
        }

        /**
         * Testing method to fetch the credits int for the test method.
         */
        public int GetCredits()
        {

            return credit.Credits;

        }

        /**
         * Creates player spaceship object
         */
        public SpaceShip InitializeSpaceShip()
        {
            player = new SpaceShip();
            gameForm.SetLivesLabel(player.getLifes().ToString());
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
                        int posDiff = Math.Abs(alienGroup[k, i].X - player.X);
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

                int startX = nearest.X + 10;
                int startY = nearest.Y + 30;
                Bullet bullet = new Bullet(startX, startY, false);
                gameForm.GetAlienBullets().Add(bullet);
            }
            return gameForm.GetAlienBullets();
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
            for (int i = 0; i < gameForm.GetBullets().Count; i++)
                gameForm.GetBullets()[i].Move();
            gameForm.UpdateBullets(gameForm.GetBullets());
            for (int i = 0; i < gameForm.GetAlienBullets().Count; i++)
                gameForm.GetAlienBullets()[i].Move();
        }

        /**
         * The AnimateAliens method animates each alien object in the alienGroup
         * array and returns it to the main GameView object to redraw.
         */
        public Alien[,] AnimateAliens(int timerCounter)
        {
            if (timerCounter % 6 == 0)
            {
                for (int i = 0; i < gameForm.GetNumberOfAlienRows(); i++)
                {
                    for (int j = 0; j < gameForm.GetNumberOfAliensPerRow(); j++)
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
            for (int r = 0; r < gameForm.GetNumberOfAlienRows(); r++)
            {
                for (int c = 0; c < gameForm.GetNumberOfAliensPerRow(); c++)
                {
                    if ((alienGroup[r, c].beenHit == false) && alienGroup[r, c].GetBounds().Bottom >= bottom)
                    {
                        alienGroup[r, c].beenHit = true;

                        gameForm.WriteScore(data.Score);
                        GameOver();

                    }
                }
            }
        }

        public void MoveAlienByFactorAndDirection(int width, int timerCounter)
        {
            if (timerCounter % alien_speed == 0)
            {
                for (int i = 0; i < gameForm.GetNumberOfAlienRows(); i++)
                {
                    for (int j = 0; j < gameForm.GetNumberOfAliensPerRow(); j++)
                    {
                        alienGroup[i, j].Move();
                    }
                }

                //get alien furthest to the right
                if (GetFarRightAlien() > width - alienGroup[4, 0].Width)
                {
                    SetAllDirections(false);
                    for (int i = 0; i < gameForm.GetNumberOfAlienRows(); i++)
                    {
                        for (int j = 0; j < gameForm.GetNumberOfAliensPerRow(); j++)
                        {
                            alienGroup[i, j].MoveDown();
                        }
                    }
                }

                //get alien furthest to the left
                if (GetFarLeftAlien() < alienGroup[4, 0].Width / 3)
                {
                    SetAllDirections(true);
                    for (int i = 0; i < gameForm.GetNumberOfAlienRows(); i++)
                    {
                        for (int j = 0; j < gameForm.GetNumberOfAliensPerRow(); j++)
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
            for (int i = 0; i < gameForm.GetNumberOfAlienRows(); i++)
            {
                for (int j = 0; j < gameForm.GetNumberOfAliensPerRow(); j++)
                {
                    int lastPos = alienGroup[i, j].X;
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
            for (int i = 0; i < gameForm.GetNumberOfAlienRows(); i++)
            {
                for (int j = 0; j < gameForm.GetNumberOfAliensPerRow(); j++)
                {
                    int firstPos = alienGroup[i, j].X;
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
            for (int i = 0; i < gameForm.GetNumberOfAlienRows(); i++)
            {
                for (int j = 0; j < gameForm.GetNumberOfAliensPerRow(); j++)
                {
                    alienGroup[i, j].movingRight = movingRight;
                }
            }
        }

        /**
         * Returns the gameOver boolean
         */
        public bool IsGameOver()
        {
            return gameOver;
        }

        
        /**
         * Makes calls to set gameOver to true and write the final game score to the screen
         */
        public void GameOver()
        {
            SetGameOver();
            gameForm.WriteScore(data.Score);

        }

        /**
         * Sets gameover boolean to true
         */
        public void SetGameOver()
        {
            gameOver = true;
        }



        public void KillAlien()
        {
            alien_count--;
            UpdateScore(10);
        }


        public void BeginNewGame()
        {
            if(!isGame)
            {
                SwitchForms();
            }
            //StartGame();
        }

        public void SwitchForms()
        {
            if (isGame)
            {
                isGame = false;
                gameForm.Visible = false;
                highScore.Visible = true;
                highScore.Focus();
            }
            else
            {
                isGame = true;
                highScore.Visible = false;
                gameForm.Visible = true;
                gameForm.Focus();
            }
        }

        public void SetScoreUtil(ScoreUtility util)
        {
            this.scoreUtil = util;
            highScore.SetScoreUtil(util);
        }

        public void StartInitialsForm()
        {
            gameForm.Visible = false;
            initalsForm.SetupElements(data.Score.ToString(), scoreUtil.DeterminePosition(data.Score).ToString());
            initalsForm.Visible = true;
            initalsForm.Focus();
        }

        public void ExitInitialsForm(string initials)
        {
            scoreUtil.Write(data.Score, initials);
            ResetGameStates();
            initalsForm.Hide();
            gameForm.ShowStartScreen();
            gameForm.Show();
            isGame = true;
        }

        public int GetScore()
        {
            return data.Score;
        }

    }

    
}

