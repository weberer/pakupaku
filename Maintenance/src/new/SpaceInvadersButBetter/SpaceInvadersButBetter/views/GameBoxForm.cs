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

namespace SpaceInvadersButBetter
{
    /**
     * Class for the Arcade machine itself for joystick and coins, game inside this
     */
    public partial class GameBoxForm : Form
    {
        private Joystick joystick;
        private Coin coinPile;
        GameView game;
        private GameData data;
        private GameBox bg = new GameBox();
        private GameLogic logic;
        private CreditSystem credit;

        /**
         * Constructor
         */
        public GameBoxForm()
        {
            InitializeComponent();

            SetStyle(ControlStyles.UserPaint, true);
            SetStyle(ControlStyles.AllPaintingInWmPaint, true);

            //performance buff
            SetStyle(ControlStyles.OptimizedDoubleBuffer, true);

            InitializeGameBoxObjects();
            this.startGame();
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
            coinPile.draw(g);
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
            game = new GameView(this);
            game.SetGameLogic(logic);
            game.SetGameData(data);
            game.SetCreditSystem(credit);
            logic.SetGameView(game);

            game.Location = new Point(80, 90);
            this.Controls.Add(game);
        }

        /**
         * Mouse Down for coin pile click and drag
         */
        private void GameBoxForm_MouseDown(object sender, MouseEventArgs e)
        {
            coinPile.updateCoinLocation(e.X, e.Y);
            if (coinPile.checkPileClicked(e.X, e.Y))
            {
                if (coinPile.checkCoinHeld())
                    coinPile.deleteCoin();
                else
                    coinPile.createCoin();
                this.Refresh();
            }


            if (coinPile.checkSlotClicked(e.X, e.Y) != -1)
            {
                if (coinPile.checkCoinHeld())
                {
                    if (coinPile.isQuarter())
                    {
                        coinPile.deleteCoin();
                        credit.AddCredit();
                        game.UpdateCredits(credit.GetCredits());
                    }
                    else
                    {
                        coinPile.deleteCoin();
                        coinPile.badCoinInserted(coinPile.checkSlotClicked(e.X, e.Y));
                    }
                }
                this.Refresh();
            }

            if (coinPile.checkGivebackClicked(e.X, e.Y) != -1 && !coinPile.checkCoinHeld())
            {
                coinPile.takeReturned(coinPile.checkGivebackClicked(e.X, e.Y));
                this.Refresh();
            }
        }


        /**
         * Mouse Move for moving coing to the coin slot or pile
         */
        private void GameBoxForm_MouseMove(object sender, MouseEventArgs e)
        {
            if (coinPile.checkCoinHeld())
            {
                coinPile.updateCoinLocation(e.X, e.Y);
                this.Refresh();
            }

        }

        public GameLogic GetLogic()
        {
            return logic;
        }

        public CreditSystem GetCredit()
        {
            return credit;
        }
    }

}
