using SpaceInvadersButBetter.Controller;
using SpaceInvadersButBetter.core;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using SpaceInvadersButBetter.Model;
using SpaceInvadersButBetter.views;

namespace SpaceInvadersButBetter
{
    /**
     * Class for the Arcade machine itself for joystick and coins, game inside this
     */
    public partial class GameBoxForm : Form
    {
        const long MIN_RENDER_TICKS = 750000; // Minimum # of ticks between render cycles. Equal to 75 miliseconds 

        private Joystick joystick;
        private Coin coinPile;
        GameView game;

        private GameLogic logic;
        private GameData data;
        private GameBox bg = new GameBox();
  

        private CreditSystem credit;


        private HighScoreForm highScoreForm;
        private InitalsForm initalsForm;
        private Boolean isGame = true;
        private long previousRenderTicks;

        /**
         * Constructor
         */
        public GameBoxForm()
        {
            previousRenderTicks = DateTime.Now.Ticks;
            InitializeComponent();

            SetStyle(ControlStyles.UserPaint, true);
            SetStyle(ControlStyles.AllPaintingInWmPaint, true);

            //performance buff
            SetStyle(ControlStyles.OptimizedDoubleBuffer, true);

            InitializeGameBoxObjects();
            startGame();
        }

        /**
         * Creates joystick and coinpile
         */
        private void InitializeGameBoxObjects()
        {
            joystick = new Joystick();
            coinPile = new Coin();
        }

        /**
         * Paint method to draw background, joystick, and coinpile
         */
        private void Form2_Paint(object sender, PaintEventArgs e)
        {
            Graphics g = e.Graphics;
            bg.Draw(g);
            joystick.draw(g);
            coinPile.Draw(g);
        }

        /**
         * Changes joystick to view based on index
         * 0 = left
         * 1 = up
         * 2 = right
         */
        public void changeJoystickView(int viewIndex)
        {
            joystick.setView(viewIndex);
            this.Refresh();
        }

        /**
         * Displays the game itself
         */
        public void startGame()
        {
            data = new GameData();
            credit = new CreditSystem();
            logic = new GameLogic(this);
            logic.SetCreditSystem(credit);
            logic.SetGameData(data);
        
            /*game.SetGameLogic(logic);
            game.SetGameData(data);
            game.SetCreditSystem(credit);
            logic.SetGameView(game);*/


            highScoreForm = new HighScoreForm(logic);
            game = new GameView(this, logic, data, credit);
            

            game.Location = new Point(80, 90);
            this.Controls.Add(game);

            initalsForm = new InitalsForm(logic);
            initalsForm.Location = new Point(80, 90);
            this.Controls.Add(initalsForm);
            initalsForm.Visible = false;
            
            highScoreForm.Location = new Point(80, 90);
            this.Controls.Add(highScoreForm);
            highScoreForm.Visible = false;
        }

        /**
         * Mouse Down for coin pile click and drag
         */
        private void GameBoxForm_MouseDown(object sender, MouseEventArgs e)
        {
            coinPile.UpdateCoinLocation(e.X, e.Y);
            if (coinPile.CheckPileClicked(e.X, e.Y))
            {
                if (coinPile.CheckCoinHeld())
                    coinPile.DeleteCoin();
                else
                    coinPile.CreateCoin();
                this.Refresh();
            }


            if (coinPile.CheckSlotClicked(e.X, e.Y) != -1)
            {
                if (coinPile.CheckCoinHeld())
                {
                    if (coinPile.IsQuarter())
                    {

                        coinPile.DeleteCoin();
                        credit.AddCredit();
                        game.UpdateCredits();
                        highScoreForm.UpdateCredits(credit.Credits);

                    }
                    else
                    {
                        coinPile.DeleteCoin();
                        coinPile.BadCoinInserted(coinPile.CheckSlotClicked(e.X, e.Y));
                    }
                }
                this.Refresh();
            }

            if (coinPile.CheckGivebackClicked(e.X, e.Y) != -1 && !coinPile.CheckCoinHeld())
            {
                coinPile.TakeReturned(coinPile.CheckGivebackClicked(e.X, e.Y));
                this.Refresh();
            }
        }


        /**
         * Mouse Move for moving coing to the coin slot or pile
         */
        private void GameBoxForm_MouseMove(object sender, MouseEventArgs e)
        {
            long currentTicks = DateTime.Now.Ticks;
            long ticksSinceLastRender = currentTicks - previousRenderTicks;

            if ((ticksSinceLastRender > MIN_RENDER_TICKS) && coinPile.CheckCoinHeld())
            {
                coinPile.UpdateCoinLocation(e.X, e.Y);
                this.Refresh();
                previousRenderTicks = currentTicks;
            }

        }

        public GameLogic GetLogic() { return logic; }

        public void SwitchForms()

        {
            if(isGame)
            {
                isGame = false;
                game.Visible = false;
                highScoreForm.Visible = true;
            }
            else
            {
                isGame = true;
                highScoreForm.Visible = false;
                game.Visible = true;
            }
        }

        public CreditSystem GetCredit()
        {
            return credit;
        }
    }

}
