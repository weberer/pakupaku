using System;
using System.Collections.Generic;
using System.Drawing;


namespace SpaceInvadersButBetter.core
{
    /**
     * Class for the coin logic to allow for the drawing, movement and registering of credits within the main machine
     */
    public class CoinDisplayManager
    {
        // Private class that represents a coin on screen
        private class Coin : GameEntity
        {
            public enum CoinTypes { Quarter, Dime }

            private static int DIME_SIZE = 30;
            private static int QUARTER_SIZE = 50;

            private static readonly Image QUARTER_IMAGE = (Image)Resources._25Cent;
            private static readonly Image DIME_IMAGE = (Image)Resources._10Cent;
            private static readonly Dictionary<CoinTypes, Image> COIN_TYPE_IMAGE_XREF = CreateCoinImageXrefDictionary();
            private static readonly Dictionary<CoinTypes, int> COIN_TYPE_SIZE_XREF = CreateCoinSizeXrefDictionary();

            public CoinTypes CoinType { get; private set; }

            // Creates Dictionary for COIN_TYPE_SIZE_XREF. Calling during COIN_TYPE_SIZE_XREF creation allows this Dictionary to be readonly.
            private static Dictionary<CoinTypes, int> CreateCoinSizeXrefDictionary()
            {
                Dictionary<CoinTypes, int> xref = new Dictionary<CoinTypes, int>();
                xref.Add(CoinTypes.Quarter, QUARTER_SIZE);
                xref.Add(CoinTypes.Dime, DIME_SIZE);

                return xref;
            }

            // Creates Dictionary for COIN_TYPE_IMAGE_XREF. Calling during COIN_TYPE_IMAGE_XREF creation allows this Dictionary to be readonly.
            private static Dictionary<CoinTypes, Image> CreateCoinImageXrefDictionary()
            {
                Dictionary<CoinTypes, Image> xref = new Dictionary<CoinTypes, Image>();
                xref.Add(CoinTypes.Quarter, QUARTER_IMAGE);
                xref.Add(CoinTypes.Dime, DIME_IMAGE);

                return xref;
            }

            public Coin(CoinTypes type) : base() { CoinType = type; }

            public Coin(CoinTypes type, int x, int y) : this(type) { UpdatePosition(x, y); }

            public bool IsQuarter() { return CoinType == CoinTypes.Quarter; }

            public void UpdatePosition(int x, int y)
            {
                X = x;
                Y = y;
            }

            override
            public void Draw(Graphics g)
            {
                // Get coin size based off of coin type
                int size = COIN_TYPE_SIZE_XREF[CoinType];

                // Coordinates to draw top left corner of the coin.
                // They are adjusted so the rendered coin is centered on the cursor
                int xDraw = X - (size / 2);
                int yDraw = Y - (size / 2);

                g.DrawImage(COIN_TYPE_IMAGE_XREF[CoinType], new Rectangle(xDraw, yDraw, size, size));
            }
        }

        public enum CoinMechs { None, Mech1, Mech2 }

        // constants to describe location/size of coin mech
        private const int COIN_MECH_HEIGHT = 19;
        private const int COIN_MECH_WIDTH = 23;
        private const int COIN_MECH_1_LEFT = 484;
        private const int COIN_MECH_2_LEFT = 510;
        private const int COIN_SLOT_TOP = 650;
        private const int COIN_RETURN_TOP = 670;
        private const int COIN_RETURN_RENDER_OFFSET = (COIN_MECH_WIDTH / 2); // Coins renderd in the coin return need the provided point at the center of the box

        // rectangles representing coin mechs and coin pile
        private static readonly Rectangle COIN_SLOT_1 = new Rectangle(COIN_MECH_1_LEFT, COIN_SLOT_TOP, COIN_MECH_HEIGHT, COIN_MECH_WIDTH);
        private static readonly Rectangle COIN_SLOT_2 = new Rectangle(COIN_MECH_2_LEFT, COIN_SLOT_TOP, COIN_MECH_HEIGHT, COIN_MECH_WIDTH);
        private static readonly Rectangle COIN_RETURN_1 = new Rectangle(COIN_MECH_1_LEFT, COIN_RETURN_TOP, COIN_MECH_HEIGHT, COIN_MECH_WIDTH);
        private static readonly Rectangle COIN_RETURN_2 = new Rectangle(COIN_MECH_2_LEFT, COIN_RETURN_TOP, COIN_MECH_HEIGHT, COIN_MECH_WIDTH);
        private static readonly Rectangle COIN_PILE = new Rectangle(50, 460, 150, 150); // no constants for decleration, as values are only used once

        private static readonly Image COIN_PILE_IMAGE = Resources.CoinPile;

        private static CoinDisplayManager coinDisplaymanager = null; // singleton object

        private Random rand = new Random();

        private Coin playersCoin; // Coin held by players

        private readonly Dictionary<CoinMechs, Stack<Coin>> COIN_RETURN_COIN_XREF = CreateCoinReturnCoinXrefDictionary();
        private readonly Dictionary<CoinMechs, Rectangle> COIN_RETURN_RECTANGLE_XREF = CreateCoinReturnRectangleXrefDictionary();

        private CoinDisplayManager() {}

        public static CoinDisplayManager getCoinDisplaymanager()
        {
            if (coinDisplaymanager == null)
                coinDisplaymanager = new CoinDisplayManager();
            return coinDisplaymanager;
        }

        private static Dictionary<CoinMechs, Stack<Coin>> CreateCoinReturnCoinXrefDictionary()
        {
            Dictionary<CoinMechs, Stack<Coin>> xref = new Dictionary<CoinMechs, Stack<Coin>>();

            xref.Add(CoinMechs.Mech1, new Stack<Coin>());
            xref.Add(CoinMechs.Mech2, new Stack<Coin>());

            return xref;
        }

        private static  Dictionary<CoinMechs, Rectangle> CreateCoinReturnRectangleXrefDictionary()
        {
            Dictionary<CoinMechs, Rectangle> xref = new Dictionary<CoinMechs, Rectangle>();

            xref.Add(CoinMechs.Mech1, COIN_RETURN_1);
            xref.Add(CoinMechs.Mech2, COIN_RETURN_2);

            return xref;
        }

        private Coin CreateCoinWithRandomType()
        {
            // Quarter = 0, Dime = 1. Get a random number [0,126), Divides by 100 to
            // reduce the range to [0,1.26), As integer division drops the remainder,
            // an integer of 0-1, is chosen, with a 1-in-5 chance of a dime spawning.
            Coin.CoinTypes newCoinType = (Coin.CoinTypes)(rand.Next(126) / 100);

            return new Coin(newCoinType);
        }

        // Picks up a coin from the specified coinReturn mech
        private void GrabReturnedCoin(CoinMechs coinReturn)
        {
            playersCoin = COIN_RETURN_COIN_XREF[coinReturn].Pop();
        }

        /**
        * Moves a coin from the player to a specified retrun
        */
        private void ReturnCoin(CoinMechs coinReturn)
        {
            var coinReturnRectangle = COIN_RETURN_RECTANGLE_XREF[coinReturn];

            if (CheckCoinHeld())
            {
                playersCoin.UpdatePosition(coinReturnRectangle.X + COIN_RETURN_RENDER_OFFSET, coinReturnRectangle.Y + COIN_RETURN_RENDER_OFFSET);
                COIN_RETURN_COIN_XREF[coinReturn].Push(playersCoin);
                playersCoin = null;
            }
        }
        
        public void DropCoin() { playersCoin = null; }

        public bool IsOverCoinPile(int xpos, int ypos) { return (xpos > COIN_PILE.Left && xpos < COIN_PILE.Right && ypos > COIN_PILE.Top && ypos < COIN_PILE.Bottom); }

        /**
        *   Processes click on coin slot where coin is held. Returns the number of credits
        *   received, 0 for dime, 1 for quarter. If coin is dime, places coin in the appropriate return;
        */
        public int InsertCoin(CoinMechs coinSlot)
        {
            int credits = 0;
            if (CheckCoinHeld() && coinSlot != CoinMechs.None)
            {
                if (playersCoin.CoinType == Coin.CoinTypes.Quarter)
                    credits = 1;
                else
                    ReturnCoin(coinSlot);
                playersCoin = null;
            }

            return credits;
        }

        public void ProcessPileClicked()
        {
            if (CheckCoinHeld())
                DropCoin();
            else
                CreateNewRandomCoin();
        }

        /**
        * Checks if player clicked a coin slot while holding a coin. If so, it returns the number of credits received from inserting
        * the coin. If no coin was held, or slot not clicked, 0 credits are returned.
        */
        public CoinMechs GetCoinSlotIsOver(int xpos, int ypos)
        {
            if (xpos > COIN_SLOT_1.Left && xpos < COIN_SLOT_1.Right && ypos > COIN_SLOT_1.Top && ypos < COIN_SLOT_1.Bottom)
                return CoinMechs.Mech1;
            else if (xpos > COIN_SLOT_2.Left && xpos < COIN_SLOT_2.Right && ypos > COIN_SLOT_2.Top && ypos < COIN_SLOT_2.Bottom)
                return CoinMechs.Mech2;

            return CoinMechs.None;
        }

        public CoinMechs GetCoinReturnIsOver(int xpos, int ypos)
        {
            if (xpos > COIN_RETURN_1.Left && xpos < COIN_RETURN_1.Right && ypos > COIN_RETURN_1.Top && ypos < COIN_RETURN_1.Bottom)
                return CoinMechs.Mech1;
            else if (xpos > COIN_RETURN_2.Left && xpos < COIN_RETURN_2.Right && ypos > COIN_RETURN_2.Top && ypos < COIN_RETURN_2.Bottom)
                return CoinMechs.Mech2;
            
            return CoinMechs.None;
        }

        public bool IsOverCoinMech(int xpos, int ypos)
        {
            return (xpos > COIN_RETURN_1.Left && xpos < COIN_RETURN_2.Right && ypos > COIN_SLOT_1.Top && ypos < COIN_RETURN_1.Bottom);
        }

        /**
        * Checks if user click was in a coin return and that the player is not currently holding a coin. If both are true,
        *  the coin from the clicked return mechanism is picked up.
        */
        public void ProcessCoinReturnClick(CoinMechs coinReturn)
        {
            if (!CheckCoinHeld() && coinReturn != CoinMechs.None) // coin cant be grabbed if already holding one
                if(coinReturn == CoinMechs.Mech1)
                    GrabReturnedCoin(CoinMechs.Mech1);
                else
                    GrabReturnedCoin(CoinMechs.Mech2);
        }

        public void CreateNewRandomCoin()
        {
            // Calculate center of coin pile
            int coinPileCenterX = COIN_PILE.X + (COIN_PILE.Top / 2);
            int coinPileCenterY = COIN_PILE.Y + (COIN_PILE.Left / 2);

            // Move coin to center of coin pile
            playersCoin = CreateCoinWithRandomType();
            MovePlayersCoin(coinPileCenterX, coinPileCenterY);
        }

        /**
        * Return if the player is currently holding a coin
        */
        public bool CheckCoinHeld() { return playersCoin != null; }
        
        /**
        * Moves the players coin to a new location on screen, and re-draws it.
        */
        public void MovePlayersCoin(int xpos, int ypos) 
        { 
            if(CheckCoinHeld())
                playersCoin.UpdatePosition(xpos, ypos); 
        }

        /** Get Point representing the X,Y location of the coin*/
        public Point getPlayerCoinLocation() 
        { 
            if(CheckCoinHeld())
                return new Point(playersCoin.X, playersCoin.Y);

            return new Point();
        }
        
        public bool isPlayerCoinAQuarter() 
        { 
            if(CheckCoinHeld())
                return playersCoin.CoinType == Coin.CoinTypes.Quarter;
            return false;
        }

        public int getNumberCoinsInReturn(CoinMechs mech) { return COIN_RETURN_COIN_XREF[mech].Count; }

        /*------ Entity Draw Methods ------ */ 

        private void DrawCoinPile(Graphics g) { g.DrawImage(COIN_PILE_IMAGE, COIN_PILE); }

        // Draws coins in coin returns
        private void DrawReturnedCoins(Graphics g)
        {
            if (COIN_RETURN_COIN_XREF[CoinMechs.Mech1].Count > 0)
                COIN_RETURN_COIN_XREF[CoinMechs.Mech1].Peek().Draw(g);
            if (COIN_RETURN_COIN_XREF[CoinMechs.Mech2].Count > 0)
                COIN_RETURN_COIN_XREF[CoinMechs.Mech2].Peek().Draw(g);
        }

        private void DrawPlayersCoin(Graphics g)
        {
            if (CheckCoinHeld())
                playersCoin.Draw(g);
        }

        public void DrawCoins(Graphics g)
        {
            DrawCoinPile(g);
            DrawReturnedCoins(g);
            DrawPlayersCoin(g);
        }
    }
}
