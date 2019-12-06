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
        private const int NUMBER_OF_ALIEN_ROWS = 6;
        private const int NUMBER_OF_ALIENS_PER_ROW = 11;
        private const int CREDIT_BLINK_COUNT = 8;

        private GameBoxForm _parent;
        private GameLogic logic; // Class that should handle all game logic
        private GameData data;
        private CreditSystem credit;


       // private int credits = 0;

    
        private bool gameover;

        private ScoreUtility scoreUtil;
        private bool StartScreenActive = true;
        private bool creditFlash = false;
        private int gameOverFlash = 0;
        private const int MAX_GAME_OVER_FLASHES = 2;

        private Timer fpsTimer;

        private int TimerCounter = 0;
        private int MenuCount = 0;
        private int blinkCount = 0;
        private int scoreScrollCount = 0;
        private Keys keydown_joystick;

        
        private List<Shield> Shields = new List<Shield>();
        private List<Label> ShieldHealth = new List<Label>();
        private SpaceShip player;
        private Alien[,] alienGroup;
        private List<Bullet> bullets;
        private List<Bullet> alienbullets;

        /**
         * Constructor
         */
        public GameView(GameBoxForm parent, GameLogic logic, GameData data, CreditSystem credit)
        {
            InitializeComponent();

            SetStyle(ControlStyles.UserPaint, true);
            SetStyle(ControlStyles.AllPaintingInWmPaint, true);

            //performance buff
            SetStyle(ControlStyles.OptimizedDoubleBuffer, true);

            _parent = parent;

            bullets = new List<Bullet>();
            alienbullets = new List<Bullet>();
            this.credit = credit;

            this.logic = logic;

            this.data = data;
            this.logic.SetGameView(this);


            scoreUtil = new ScoreUtility();

            logic.SetScoreUtil(scoreUtil);

            lblHighScore.Text = scoreUtil.getTopScore().ToString();
            InitializeStartScreen();
            InitializeGameObjects();
            this.logic.SetupCollisionHandler();
            fpsTimer.Start();

            
        }

        public void SetGameData(GameData data)
        {
            this.data = data;
        }

        public void SetCreditSystem(CreditSystem credit)
        {
            this.credit = credit;
        }

        public void SetGameLogic(GameLogic logic)
        {
            this.logic = logic;
        }

 

        public Alien[,] GetAlienGroup()
        {
            return alienGroup;
        }

        public List<Bullet> GetBullets()
        {
            return bullets;
        }

        public List<Bullet> GetAlienBullets()
        {
            return alienbullets;
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
        public void InitializeGameObjects()
        {
            logic.GameReset();
            fpsTimer = new Timer();
            fpsTimer.Tick += fpsTimer_Tick;
            fpsTimer.Interval = 20;
            data.resetLevelScore(); //level = 1; score = 0

            lblScore.Text = data.Score.ToString();
            lblLevelNumber.Text = data.Level.ToString();
            Shields = logic.InitializeObject_Shields();
            player = logic.InitializeSpaceShip();

            alienGroup = logic.InitializeAliens(data.Level);


            InitializeCredits();
        }

        public void ResetGameObjects()
        {

            lblScore.Text = data.Score.ToString();
            lblLevelNumber.Text = data.Level.ToString();


            ResetShieldLabels();
            Shields = logic.InitializeObject_Shields();

        
            alienGroup = logic.InitializeAliens(data.Level);

            bullets.Clear();
            alienbullets.Clear();
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
        private void DisplayCredits(int score)
        {
            lblGameOver.Visible = true;
            lblYourScore.Visible = true;
            lblEndScore.Visible = true;
            lblEndScore.Text = score.ToString();
            
        }

        /**
         * Removes start screen elements
         */
        public void EraseStartScreen()
        {
            if (this.Visible == false)
                logic.SwitchForms();
            SpaceInvadersLabel.Visible = false;
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
            ChangeTimer.Stop();
        }

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
            if (credit.Credits > 0)
                lblHitSpace.Visible = true;
            CreditFlashTimer.Enabled = true;
            ChangeTimer.Start();
        }

        public int GetShieldBottom(Shield shield)
        {
            return (ClientRectangle.Bottom - (shield.GetBounds().Height + 100));
        }
        public void ResetShieldLabels()
        {
            ShieldHealth.Clear();
        }
        public void SetupShieldLabel(Shield shield, int shieldX, int shieldY)
        {
            Label label = new Label();

            ShieldHealth.Add(label);
            int i = ShieldHealth.Count - 1;
            ShieldHealth[i].Text = shield.getHealth().ToString();
            ShieldHealth[i].Location = new Point(shieldX - 17, shieldY + 8);
            ShieldHealth[i].BackColor = Color.Transparent;
            ShieldHealth[i].TextAlign = ContentAlignment.MiddleCenter;
            this.Controls.Add(ShieldHealth[i]);
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
                if (bullets[i].Y < 0)
                    bullets.RemoveAt(i);
                else
                    bullets[i].Draw(g);
            }

            for (int i = 0; i < alienbullets.Count; i++)
            {
                if (alienbullets[i].Y < 0)
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
                logic.ShootButton(k);
            }
        }

        /**
         * Moves player based on key down and bullets
         */
        private void Movement()
        {
            logic.MovementHandlerPlayer(keydown_joystick, ClientRectangle.Right);
            logic.MovementHandlerBullets();
        }
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
            if (logic.IsGameOver())
            {
                bullets.Clear();
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
                    if (gameOverFlash == MAX_GAME_OVER_FLASHES)
                    {
                        toggleCredit(false);
                        logic.GameReset();
                        gameOverFlash = 0;
                        logic.EndGame();
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
            logic.CollisionCheck();
            TimerCounter++;

            logic.NextLevel();

            CreditBlink();

            if (TimerCounter % 100 == 0 && logic.IsGameOver() == false)
            {
                alienbullets = logic.GenerateAlienBullet();
            }

            // Flap the images to give them a moving look
            alienGroup = logic.AnimateAliens(TimerCounter);
            Invalidate();

            if (logic.IsGameOver() == false)
            {
                //move by factor of speed
                logic.MoveAlienByFactorAndDirection(ClientRectangle.Width, TimerCounter);
            }
            logic.CheckForLanding(ClientRectangle.Bottom);
            Invalidate();
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
        }

        /**
         * Updates score to file and displays credits
         */
        public void WriteScore(int Score)
        {
            DisplayCredits(Score);
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
        public void SetScoreLabel(string score)
        {
            lblScore.Text = score;
        }

        /**
         * Updates the lives label
         */
        public void SetLivesLabel(string lives)
        {
            lblLifes.Text = lives;
        }

        /**
         * Updates the number of credits inserted
         */
        public void UpdateCredits(int credits)
        {
            credit.Credits = credits;
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
            if (creditFlash && credit.Credits < 9)
            {
                InsertCoinLabel.Text = "Insert Coin";
                creditFlash = false;
            }
            else
            {
                InsertCoinLabel.Text = "Credits x " + credit.Credits.ToString();
                creditFlash = true;
            }
        }

        /*
         * Removes shield from screen
         */
        public void RemoveShield(int index)
        {
            this.Controls.Remove(ShieldHealth[index]);
            ShieldHealth.RemoveAt(index);
        }

        /*
         * Updates the shield health on the screen
         */
        public void UpdateShield(int health, int index)
        {
            ShieldHealth[index].Text = health.ToString();
        }

        /**
         * Redraws the bullets in their new locations
         */
        public void UpdateBullets(List<Bullet> bullets)
        {
            this.bullets = bullets;
            for (int i = 0; i < this.bullets.Count; i++)
            {
                Invalidate(this.bullets[i].GetBounds());
            }
        }


        public int GetNumberOfAlienRows()
        {
            return NUMBER_OF_ALIEN_ROWS;
        }

        public int GetNumberOfAliensPerRow()
        {
            return NUMBER_OF_ALIENS_PER_ROW;
        }

        private void ChangeTimer_Tick(object sender, EventArgs e)
        {
            logic.SwitchForms();
        }

        private void GameView_VisibleChanged(object sender, EventArgs e)
        {
            if(this.Visible == true)
            {
                ChangeTimer.Start();
                fpsTimer.Start();
                CreditFlashTimer.Start();
                lblHighScore.Text = scoreUtil.getTopScore().ToString();
                this.Focus();
            }
            else
            {
                ChangeTimer.Stop();
                fpsTimer.Stop();
                CreditFlashTimer.Stop();
            }
        }
    }

}
