using SpaceInvadersButBetter.Controller;
using SpaceInvadersButBetter.core;
using System;
using System.Drawing;
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
        private CoinDisplayManager coinManager;
        private Type coinMech = typeof(CoinDisplayManager.CoinMechs);
        GameView game;
        private GameData data;
        private GameBox bg = new GameBox();
        private GameLogic logic;
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
         * Creates joystick and coinDisplaymanager
         */
        private void InitializeGameBoxObjects()
        {
            joystick = new Joystick();
            coinManager = CoinDisplayManager.getCoinDisplaymanager();
        }

        /**
         * Paint method to draw background, joystick, and coinpile
         */
        private void Form2_Paint(object sender, PaintEventArgs e)
        {
            Graphics g = e.Graphics;
            bg.Draw(g);
            joystick.draw(g);
            coinManager.DrawCoins(g);

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
            logic = new GameLogic(this);
            data = new GameData();
            highScoreForm = new HighScoreForm(logic);
            game = new GameView(this, logic, data);
            
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

            if(coinManager.IsOverCoinPile(e.X, e.Y))
                coinManager.ProcessPileClicked();

            CoinDisplayManager.CoinMechs slotClicked = coinManager.GetCoinSlotIsOver(e.X, e.Y);
            int creditsReceived = coinManager.InsertCoin(slotClicked);
            logic.addCredit(creditsReceived);

            CoinDisplayManager.CoinMechs coinReturnClicked = coinManager.GetCoinReturnIsOver(e.X, e.Y);
            coinManager.ProcessCoinReturnClick(coinReturnClicked);

            Refresh();
        }


        /**
         * Mouse Move for moving coing to the coin slot or pile
         */
        private void GameBoxForm_MouseMove(object sender, MouseEventArgs e)
        {
            long currentTicks = DateTime.Now.Ticks;
            long ticksSinceLastRender = currentTicks - previousRenderTicks;

            if (coinManager.IsOverCoinPile(e.X, e.Y) || coinManager.IsOverCoinMech(e.X, e.Y))
                Cursor.Current = Cursors.Hand;
            else
                Cursor.Current = Cursors.Default;

            if ((ticksSinceLastRender > MIN_RENDER_TICKS) && coinManager.CheckCoinHeld())
            {
                coinManager.MovePlayersCoin(e.X, e.Y);
                this.Refresh();
                previousRenderTicks = currentTicks;
            }


        }

        public GameLogic getLogic() { return logic; }

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
    }

}
