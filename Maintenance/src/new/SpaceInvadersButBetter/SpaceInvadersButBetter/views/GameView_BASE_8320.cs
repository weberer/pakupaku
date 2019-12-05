using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using SpaceInvadersButBetter.core;
using SpaceInvadersButBetter.Controller;
using SpaceInvadersButBetter.Model;
using System.IO;


namespace SpaceInvadersButBetter
{
    /**
     * GameView Class is the main game the would be run inside the arcade box
     */
    public partial class GameView : UserControl
    {
        private GameBoxForm _parent;
        private GameLogic logic; // Class that should handle all game logic
        private GameData data;


        private int credits = 0;
  

        private bool gameover;
        private ScoreUtility scoreUtil;
        private bool StartScreenActive = true;
        private bool creditFlash = false;
        private int gameOverFlash = 0;
        private const int MAX_GAME_OVER_FLASHES = 2;

        //increments with higher levels
        private int alien_speed = 6;
        private int alien_speed_factor = 1;
        private int alien_count = 66;

        private Timer fpsTimer;
  
        private int TimerCounter = 0;
        private int MenuCount = 0;
        private int blinkCount = 0;
        private int scoreScrollCount = 0;
        private Keys keydown_joystick;
        private Keys keydown_shoot;

        private const int NUMBER_OF_SHIELDS = 4;
        private const int NUMBER_OF_ALIEN_ROWS = 6;
        private const int NUMBER_OF_ALIENS_PER_ROW = 11;
        private const int CREDIT_BLINK_COUNT = 8;
        private const double SPEEP_INCREASE_FACTOR = 1.25;

        private List<Shield> Shields = new List<Shield>();
        private List<Label> ShieldHealth = new List<Label>();
        private SpaceShip player;
        private Alien[,] alienGroup = new Alien[NUMBER_OF_ALIEN_ROWS, NUMBER_OF_ALIENS_PER_ROW];
        private List<Bullet> bullets = new List<Bullet>();
        private List<Bullet> alienbullets = new List<Bullet>();

        /**
         * Constructor
         */
        public GameView(GameBoxForm parent, GameLogic logic, GameData data)
        {
            InitializeComponent();

            SetStyle(ControlStyles.UserPaint, true);
            SetStyle(ControlStyles.AllPaintingInWmPaint, true);

            //performance buff
            SetStyle(ControlStyles.OptimizedDoubleBuffer, true);

            _parent = parent;

            this.logic = logic;

            this.data = data;
            this.logic.SetGameView(this);

        
            scoreUtil = new ScoreUtility();

            lblHighScore.Text = scoreUtil.getTopScore().ToString();
            InitializeStartScreen();
            InitializeGameObjects();
            fpsTimer.Start();
        }

        /**
         * Displays start screen elements
         */
        private void InitializeStartScreen()
        {
            StartScreenActive = true;
            lblScore.Visible = false;
            lblLevel.Visible = false;
            lblLevelNumber.Visible = false;
            lblLifes.Visible = false;
            lblLifesLabel.Visible = false;
            lblHighScore.Visible = true;
            lblHighScoreTitle.Visible = true;
            lblScoreScroll.Visible = true;

        }

        /**
         * Initializes game essentials and calls methods to create game objects
         */
        private void InitializeGameObjects()
        {
            gameover = false;
            fpsTimer = new Timer();
            fpsTimer.Tick += fpsTimer_Tick;
            fpsTimer.Interval = 20;
            data.resetLevelScore(); //level = 1; score = 0
          
            lblScore.Text = data.getScore().ToString();
            lblLevelNumber.Text = data.getLevel().ToString();
            InitializeObject_Shields();
            logic.InitializeSpaceShip();

            logic.InitializeAliens(data.getLevel());


            InitializeCredits();
        }

        /**
         * Hides credit screen elements
         */
        private void InitializeCredits()
        {
            lblGameOver.Visible = false;
            lblYourScore.Visible = false;
            lblEndScore.Visible = false;
        }

        /**
         * Displays credit screen elements with top score
         */
        private void DisplayCredits()
        {
            lblGameOver.Visible = true;
            lblYourScore.Visible = true;
            lblEndScore.Visible = true;
            lblEndScore.Text = data.getScore().ToString();
            lblHighScore.Text = scoreUtil.getTopScore().ToString();
        }

        /**
         * Removes start screen elements
         */
        public void EraseStartScreen()
        {
            SpaceInvadersLabel.Visible = false;
            CoinCountLabel.Visible = false;
            InsertCoinLabel.Visible = false;
            StartScreenActive = false;
            lblScore.Visible = true;
            lblScoreTitle.Visible = true;
            lblLevel.Visible = true;
            lblLevelNumber.Visible = true;
            lblLifes.Visible = true;
            lblLifesLabel.Visible = true;
            lblScoreScroll.Visible = false;
            lblHitSpace.Visible = false;
            CreditFlashTimer.Enabled = false;
        }


        /**
         * Creates Aliens for board
         */
        //private void InitializeAliens(int level)
        //{
        //    for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
        //    {
        //        for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
        //        {
        //            alienGroup[i, j] = new Alien(alien_speed_factor, Resources.invader_open, Resources.invader_closed, (2 + i), j);
        //        }
        //    }
        //}

        /**
         * Insert coin method, updates label and count
         */
         /*
        public void CoinInsert()
        {
            int coinCount = data.getCoinCount();
            if (coinCount < 1)
            {
                coinCount++;
                data.setCoinCount(coinCount);
                CoinCountLabel.Text = coinCount.ToString();
                lblHitSpace.Visible = true; //Evan 11/12
            }
        }
        */


        public void ShowStartScreen()
        {
            SpaceInvadersLabel.Visible = true;
            InsertCoinLabel.Visible = true;
            StartScreenActive = true;
            lblScore.Visible = false;
            lblScoreTitle.Visible = true;
            lblLevel.Visible = false;
            lblLevelNumber.Visible = false;
            lblLifes.Visible = false;
            lblLifesLabel.Visible = false;
            lblScoreScroll.Visible = true;
            if (credits > 0)
                lblHitSpace.Visible = true;
            CreditFlashTimer.Enabled = true;
        }

        /**
         * Creates Aliens for board
         */

         /*
        public void CoinDecrement()
        {
            int coinCount = data.getCoinCount();
            if (coinCount > 0)
            {
                data.setCoinCount(--coinCount);
                CoinCountLabel.Text = coinCount.ToString();

        private void InitializeAliens(int level)
        {
            for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
            {
               for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
                {
                    alienGroup[i, j] = new Alien(alien_speed_factor, Resources.invader_open, Resources.invader_closed, (2 + i), j);
                }

            }
        }
        */

        

        /**
         * Creates player spaceship object
         */
         /*
        private void InitializeSpaceShip()
        {
            player = new SpaceShip();
            lblLifes.Text = player.getLifes().ToString();
    
        }
        */

        /**
         * Creates 4 sheilds
         */
         
        public void InitializeObject_Shields()
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
        

        /**
         * Draws sheilds
         */
        private void drawShields(Graphics g, List<Shield> Shields)
        {
            for (int i = 0; i < Shields.Count; i++)
            {
                Shields[i].Draw(g);
            }

            for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
            {
                for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
                {
                    alienGroup[i, j].Draw(g);
                }
            }
        }

        /**
         * Draws bullets
         */
        private void drawBullets(Graphics g)
        {
            for (int i = 0; i < bullets.Count; i++)
            {
                if (bullets[i].getY() < 0)
                    bullets.RemoveAt(i);
                else
                    bullets[i].Draw(g);
            }

            for (int i = 0; i < alienbullets.Count; i++)
            {
                if (alienbullets[i].getY() < 0)
                    alienbullets.RemoveAt(i);
                else
                    alienbullets[i].Draw(g);
            }
        }

        /**
         * Paint method for the form to draw elements
         */
        private void Form1_Paint(object sender, PaintEventArgs e)
        {
            Graphics g = e.Graphics;

            // draw player
             player.draw(g);

            // draw sheilds
            drawShields(g, Shields);

            // draw/remove bullets
            drawBullets(g);
        }

        /**
         * KeyDown event for keyboard input
         */
        private void GameControlObject_KeyDown(object sender, KeyEventArgs e)
        {
            determineOperation(e.KeyCode, false);
        }

        /**
         * KeyUp event for keyboard input
         */
        private void GameControlObject_KeyUp(object sender, KeyEventArgs e)
        {
            determineOperation(e.KeyCode, true);
        }

        /**
         * CMD key override to access arrow keys on keyboard
         */
        protected override bool ProcessCmdKey(ref Message msg, Keys keyData)
        {
            switch (keyData)
            {
                case Keys.Left: // left arrow key
                    determineOperation(Keys.Left, false);
                    break;

                case Keys.Right: // right arrow key
                    determineOperation(Keys.Right, false);
                    break;

                case Keys.Space:

                    if ((data.getCoinCount() == 1) && StartScreenActive == true)
                        EraseStartScreen();

                    if ((credits > 0) && logic.IsStartScreenActive())
                    {
                        logic.StartGame();
                    }  

                    break;
            }
            return base.ProcessCmdKey(ref msg, keyData);
        }

        /**
         * Determins what to do based on key parameter
         */
        private void determineOperation(Keys k, bool release)
        {
            if (k == Keys.Left)
            {
                if (!release)
                {
                    keydown_joystick = k;
                    this._parent.changeJoystickView(0);
                }
                else
                {
                    keydown_joystick = Keys.None;
                    this._parent.changeJoystickView(1);
                }
            }
            else if (k == Keys.Right)
            {
                if (!release)
                {
                    keydown_joystick = k;
                    this._parent.changeJoystickView(2);
                }
                else
                {
                    keydown_joystick = Keys.None;
                    this._parent.changeJoystickView(1);
                }
            }
            else if (k == Keys.Escape && release)
            {
                if (!logic.IsStartScreenActive())
                {
                    if (fpsTimer.Enabled == true)
                    {
                        lblEndScore.Visible = true;
                        lblEndScore.Text = "Paused";
                        fpsTimer.Enabled = false;
                    }
                    else
                    {
                        lblEndScore.Visible = false;
                        fpsTimer.Enabled = true;
                    }
                }
            }
            //Evan 11/12: changed !release to release so that space 
            else if (k == Keys.Space && release)
            {
                keydown_shoot = Keys.Space;

                logic.ShootButton(k);

                //ShootButton(keydown_shoot);

            }
            //else if (k == Keys.Space && !release)
            //{
            //    keydown_shoot = Keys.Space;
            //    ShootButton();
            //}
        }

        /**
         * Moves player based on key down and bullets
         */
        private void Movement()
        {
            if (keydown_joystick == Keys.Left)
            {
                player.MoveLeft();
                Invalidate(player.GetBounds());
            }
            else if (keydown_joystick == Keys.Right)
            {
                player.MoveRight(ClientRectangle.Right);
                Invalidate(player.GetBounds());
            }

            for (int i = 0; i < bullets.Count; i++)
            {
                bullets[i].Move();
                Invalidate(bullets[i].GetBounds());
            }

            for (int i = 0; i < alienbullets.Count; i++)
            {
                alienbullets[i].Move();
                Invalidate(alienbullets[i].GetBounds());
            }
        }

        /**
         * Method called when space bar is hit to shoot a bullet or reset game in over
         */

        //private void ShootButton()
        //{
        //    if (keydown_shoot == Keys.Space)
        //    {
        //        if(!gameover)
        //        {
        //            int startX = player.Position.X + (Resources.space_ship.Width / 2) - 10;
        //            int startY = player.Position.Y - (Resources.space_ship.Height / 2) + 10;
        //            Bullet bullet = new Bullet(startX, startY, true);
        //            bullets.Add(bullet);
        //        } 
        //        else
        //        {
        //            ResetGameStates();
        //            ResetPlayer();
        //            ResetAliens();
        //            ResetShields();
        //            ResetBullets();
        //        }
        //    }
        //}

        /*private void ShootButton()
        {
            if (keydown_shoot == Keys.Space)
            {
                if(!gameover)
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
                    ResetSheilds();
                    ResetBullets();
                }
            }
        }*/


        /**
         * Clears bullet lists
         */
         /*
        private void ResetBullets()
        {
            alienbullets.Clear();
            bullets.Clear();
        }
        */

        /**
         * Clears sheilds and resets them
         */
         /*
        private void ResetShields()
        {
            Shields.Clear();
            InitializeObject_Shields();
        }
        */

        /**
         * Clears aliens and resets them
         */
         /*
        private void ResetAliens()
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
        */

        /**
         * Resets player and lifes
         */
         /*
        private void ResetPlayer()
        {
            player.reset();
            lblLifes.Text = player.getLifes().ToString();
        }
        */

        /**
         * Resets game states for logic
         */
         /*
        private void ResetGameStates()
        {
            gameover = false;
            toggleCredit(false);
            alien_speed = 6;
            alien_speed_factor = 1;
            alien_count = 66;
            level = 0;
            score = 0;
            lblLevelNumber.Text = level.ToString();
            lblScore.Text = score.ToString();
        }
        */
        /**
         * Checks for bullet collision with sheild
         */
         /*
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
        */

        /**
         * Checks for bullet collision with alien
         */
        private void AlienCheck()
        {
            for (int i = 0; i < bullets.Count; i++) //Alien Check
            {
                if (checkBulletHit(bullets[i]))
                {
                    bullets.RemoveAt(i);
                }
            }
        }

        /**
         * Checks for alien bullet hiting shield
         */
        private void AlienHitSheild()
        {
            for (int i = 0; i < alienbullets.Count; i++) // alien shot hit sheild
            {
                if (Shields.Count > 0) //Shield Check
                {
                    if (alienbullets[i].Position.Y > Shields[0].Position.Y)
                    {
                        bool delete = false;
                        int shieldIndexHit = -1;
                        for (int j = 0; j < Shields.Count; j++)
                            if (Shields[j].Position.X < alienbullets[i].Position.X && (Shields[j].Position.X + Resources.shield.Width - 20) > alienbullets[i].Position.X)
                            {
                                delete = true;
                                shieldIndexHit = j;
                            }
                        if (delete && shieldIndexHit != -1)
                        {
                            alienbullets.RemoveAt(i);
                            Shields[shieldIndexHit].alienHealthHit();
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

        /**
         * Check alien hit person, this ends the game (ABDUCTION)
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
                        gameover = true;
                        WriteScore(data.getScore());
                    }
                }
            }
        }

        /**
         * Check Alien bullets hit player, this ends the game (KILLED)
         */
        private void AlienBulletsCheck()
        {
            for (int i = 0; i < alienbullets.Count; i++)
            {
                if (player.GetBounds().IntersectsWith(alienbullets[i].GetBounds()))
                {
                    if (!player.hitAndIsAlive())
                    {
                        player.kill();
                        gameover = true;
                        WriteScore(data.getScore());
                    }
                    lblLifes.Text = player.getLifes().ToString();
                    alienbullets.RemoveAt(i);
                }
            }
        }

        /**
         * Collision check method to check all collision items
         */
        private void CollisionCheck()
        {
            ShieldCheck();
            AlienCheck();
            AlienHitSheild();
            AlienHitPersonCheck();
            AlienBulletsCheck();
            checkShieldHitByAlien();
        }

        /**
         * Updates score with given points and displays
         */
         /*
        private void UpdateScore(int points)
        {
            score += 10;
            lblScore.Text = score.ToString();
        }
        */

        /**
         * Checks bullets hitting aliens, score gained
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
                        alien_count--;
                        logic.UpdateScore(10);
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Check if shield is hit by alien, remove shield  (DESTROYED HOUSE)
         */
        private void checkShieldHitByAlien()
        {
            for(int i = 0; i < Shields.Count; i++)
            {
                if(checkShieldHit(Shields[i]))
                {
                    Shields.RemoveAt(i);
                    this.Controls.Remove(ShieldHealth[i]);
                    ShieldHealth.RemoveAt(i);
                }
            }
        }

        /**
         * Checks if shield is hit by alien, sheild destroyed
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

        /**
         * Increments level, speed factor, and resets aliens
         */
         /*
        private void NextLevel()
        {
            level++;
            lblLevelNumber.Text = level.ToString();
            alien_speed_factor = Convert.ToInt32(alien_speed_factor * SPEEP_INCREASE_FACTOR);
            logic.ResetAliens();
            alien_count = 66;
            
        }
        */

        /**
         * Toggles credits based on parameter
         */
        private void toggleCredit(bool tog)
        {
            lblGameOver.Visible = tog;
            lblEndScore.Visible = tog;
            lblYourScore.Visible = tog;
        }

        /**
         * Blinks credits to give arcade feel (about 5 blinks then disapear)
         */
        private void CreditBlink()
        {
            if (gameover)
            {
                if (TimerCounter % 25 == 0)
                {
                    if (blinkCount != CREDIT_BLINK_COUNT)
                    {
                        blinkCount++;
                        toggleCredit(false);
                    }
                }
                if (TimerCounter % 50 == 0 && blinkCount != CREDIT_BLINK_COUNT)
                {
                    toggleCredit(true);
                    gameOverFlash++;
                    if(gameOverFlash == MAX_GAME_OVER_FLASHES)
                    {
                        toggleCredit(false);
                        gameover = false;
                        gameOverFlash = 0;
                        logic.EndGame();
                    }
                }
            }
        }

        /**
         * Animates aliens to give them a floating look
         */
        private void AnimateAliens()
        {
            if (TimerCounter % 6 == 0)
            {
                for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
                {
                    for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
                    {
                        alienGroup[i, j].MoveInPlace();
                    }
                }
            }
        }

        /**
         * Move aliens by factor and down if hit wall
         */
        private void MoveAlienByFactorAndDirection()
        {
            if (TimerCounter % alien_speed == 0)
            {
                for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
                {
                    for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
                    {
                        alienGroup[i, j].Move();
                    }
                }

                //get alien furthest to the right
                if (GetFarRightAlien() > ClientRectangle.Width - alienGroup[4, 0].GetWidth())
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
         * Tick logic for running game
         */
        private void GameRunningTickLogic()
        {
            Movement();
            CollisionCheck();
            TimerCounter++;

            if (alien_count == 0)
            {
                logic.NextLevel();
            }

            CreditBlink();

            if (TimerCounter % 100 == 0 && gameover == false)
            {
                generateAlienBullet();
            }

            // Flap the images to give them a moving look
            AnimateAliens();
            Invalidate();

            if (gameover == false)
            {
                //move by factor of speed
                MoveAlienByFactorAndDirection();
            }

            if (CheckForLanding())
            {
                WriteScore(data.getScore());
                gameover = true;
            }
            Invalidate();
        }

        /**
         * Tick logic for game over state
         */
        private void GameNotRunningTickLogic()
        {
            if (MenuCount % 50 == 0)
            {
                if (scoreScrollCount < 10)
                {
                    lblScoreScroll.Text = (scoreScrollCount + 1).ToString() + ") " + scoreUtil.getScoreAt(scoreScrollCount);
                    scoreScrollCount++;
                }
                else
                {
                    scoreScrollCount = 0;
                    lblScoreScroll.Text = (scoreScrollCount + 1).ToString() + ") " + scoreUtil.getScoreAt(scoreScrollCount);
                }
            }
        }

        /**
         * Timer Tick
         */
        private void fpsTimer_Tick(object sender, System.EventArgs e)
        {
            MenuCount++;
            if (StartScreenActive == false)
            {
                GameRunningTickLogic();
            }
            else
            {
                GameNotRunningTickLogic();
            }
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
         * Checks if alien hits the ground
         */
        private bool CheckForLanding()
        {
            for (int r = 0; r < NUMBER_OF_ALIEN_ROWS; r++)
            {
                for (int c = 0; c < NUMBER_OF_ALIENS_PER_ROW; c++)
                {
                    if ((alienGroup[r, c].beenHit == false) && alienGroup[r, c].GetBounds().Bottom >= ClientRectangle.Bottom)
                    {
                        alienGroup[r, c].beenHit = true;
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Updates score to file and displays credits
         */
        private void WriteScore(int Score)
        {
            scoreUtil.Write(Score);
            DisplayCredits();
        }

        /**
         * Display alien bullets, only from the bottom most aliens in each colummn
         */
        private void generateAlienBullet()
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
        }


        /**
         * Updates the level label
         */
        public void setLevelLabel(string level)
        {
            lblLevelNumber.Text = level;
        }

        /**
         * Updates the score label
         */
        public void setScoreLabel(string score)
        {
            lblScore.Text = score;
        }

        /**
         * Updates the lives label
         */
        public void setLivesLabel(string lives)
        {
            lblLifes.Text = lives;
        }

        /**
         * Updates the cointCount label
         */
        public void setCoinCountLabel(string count)
        {
            CoinCountLabel.Text = count;
        }

        public void setHitSpaceVisibility(Boolean visibility)
        {
            lblHitSpace.Visible = visibility;
        }


        public void UpdateCredits(int credits)
        {
            this.credits = credits;
            InsertCoinLabel.Text = "Credits x " + credits.ToString();
            if (logic.IsStartScreenActive() && credits > 0)
            {
                lblHitSpace.Show();
            }
            else
                lblHitSpace.Hide();
        }

        private void CreditFlashTimer_Tick(object sender, EventArgs e)
        {
            if (creditFlash && credits < 9)
            {
                InsertCoinLabel.Text = "Insert Coin";
                creditFlash = false;
            }
            else
            {
                InsertCoinLabel.Text = "Credits x " + credits.ToString();
                creditFlash = true;
            }
        }
        

    }
}
