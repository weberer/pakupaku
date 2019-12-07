using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpaceInvadersButBetter.core;
using SpaceInvadersButBetter;
using System.Drawing;

namespace UnitTests
{
    [TestClass]
    public class CoinTests
    {
        private const int COIN_MECH_HEIGHT = 19;
        private const int COIN_MECH_WIDTH = 23;
        private const int COIN_MECH_1_LEFT = 484;
        private const int COIN_MECH_2_LEFT = 510;
        private const int COIN_SLOT_TOP = 650;
        private const int COIN_RETURN_TOP = 670;

        private static readonly Rectangle COIN_SLOT_1 = new Rectangle(COIN_MECH_1_LEFT, COIN_SLOT_TOP, COIN_MECH_HEIGHT, COIN_MECH_WIDTH);
        private static readonly Rectangle COIN_SLOT_2 = new Rectangle(COIN_MECH_2_LEFT, COIN_SLOT_TOP, COIN_MECH_HEIGHT, COIN_MECH_WIDTH);
        private static readonly Rectangle COIN_RETURN_1 = new Rectangle(COIN_MECH_1_LEFT, COIN_RETURN_TOP, COIN_MECH_HEIGHT, COIN_MECH_WIDTH);
        private static readonly Rectangle COIN_RETURN_2 = new Rectangle(COIN_MECH_2_LEFT, COIN_RETURN_TOP, COIN_MECH_HEIGHT, COIN_MECH_WIDTH);
        private static readonly Rectangle COIN_PILE = new Rectangle(50, 460, 150, 150);

        /*
         * InsertCoin(coinMech) //Test with each 3 types in player coin in each 3 slots; expect 1 for quarter, 0 for dime
         * ProcessPileClicked() // no return. Test clicking on and not on pile, with coin in and not in hand. Expect return states of coin to be null/not null
         * ProcessCoinReturnClick(coinmech) //Expect
         */


        [TestMethod]
        public void CoinPickupDropTest()
        {
            CoinDisplayManager manager = CoinDisplayManager.getCoinDisplaymanager();

            // set manager to state with no held coin
            manager.DropCoin();

            manager.CreateNewRandomCoin();
            Assert.IsTrue(manager.CheckCoinHeld());

            // manager is in state with a coin being held if last test passed
            manager.DropCoin();
            Assert.IsFalse(manager.CheckCoinHeld());

            manager.DropCoin();
            manager.ProcessPileClicked();
            while (!manager.isPlayerCoinAQuarter())
                manager.CreateNewRandomCoin();

            manager.ProcessPileClicked();
            Assert.IsFalse(manager.isPlayerCoinAQuarter()); // coin was dropped, so it should be null
        }

        [TestMethod]
        public void IsPositionOverTest()
        {
            CoinDisplayManager manager = CoinDisplayManager.getCoinDisplaymanager();
            var mech1 = CoinDisplayManager.CoinMechs.Mech1;
            var mech2 = CoinDisplayManager.CoinMechs.Mech2;
            var mechNone = CoinDisplayManager.CoinMechs.None;

            // Test over Coin Pile
            var xLoc = (COIN_PILE.X + COIN_PILE.Width / 2);
            var yLoc = (COIN_PILE.Y + COIN_PILE.Height / 2);
            Assert.IsFalse(manager.IsOverCoinPile(0, 0));
            Assert.IsTrue(manager.IsOverCoinPile(xLoc, yLoc));

            // Test Over Coin Slots 
            xLoc = (COIN_SLOT_1.X + COIN_SLOT_1.Width / 2);
            yLoc = (COIN_SLOT_1.Y + COIN_SLOT_1.Height / 2);
            Assert.AreEqual(mechNone, manager.GetCoinSlotIsOver(0, 0));
            Assert.AreEqual(mech1, manager.GetCoinSlotIsOver(xLoc, yLoc));

            xLoc = (COIN_SLOT_2.X + COIN_SLOT_2.Width / 2);
            yLoc = (COIN_SLOT_2.Y + COIN_SLOT_2.Height / 2);
            Assert.AreEqual(mech2, manager.GetCoinSlotIsOver(xLoc, yLoc));

            // Test over Coin Returns
            xLoc = (COIN_RETURN_1.X + COIN_RETURN_1.Width / 2);
            yLoc = (COIN_RETURN_1.Y + COIN_RETURN_1.Height / 2);
            Assert.AreEqual(mechNone, manager.GetCoinReturnIsOver(0, 0));
            Assert.AreEqual(mech1, manager.GetCoinReturnIsOver(xLoc, yLoc));

            xLoc = (COIN_RETURN_2.X + COIN_RETURN_2.Width / 2);
            yLoc = (COIN_RETURN_2.Y + COIN_RETURN_2.Height / 2);
            Assert.AreEqual(mech2, manager.GetCoinReturnIsOver(xLoc, yLoc));

            // Test Coin Mechs
            Assert.IsFalse(manager.IsOverCoinMech(0, 0));
            Assert.IsTrue(manager.IsOverCoinMech(xLoc, yLoc));
        }

        [TestMethod]
        public void CoinLocationTests()
        {
            CoinDisplayManager manager = CoinDisplayManager.getCoinDisplaymanager();
            Point p1 = new Point(0, 0);
            Point p2 = new Point(50, 50);
            Point p3 = new Point(100,251);

            // No coin is held, so location is null
            manager.DropCoin();
            Assert.AreEqual(new Point(), manager.getPlayerCoinLocation());

            manager.ProcessPileClicked();
            manager.MovePlayersCoin(p1.X, p1.Y);
            Assert.AreEqual(p1, manager.getPlayerCoinLocation());

            manager.MovePlayersCoin(p2.X, p2.Y);
            Assert.AreEqual(p2, manager.getPlayerCoinLocation());

            manager.MovePlayersCoin(p3.X, p3.Y);
            Assert.AreEqual(p3, manager.getPlayerCoinLocation());
        }
        
        [TestMethod]
        public void coinMechanisimTests()
        {
            CoinDisplayManager manager = CoinDisplayManager.getCoinDisplaymanager();

            const int quarterValue = 1;
            const int dimeValue = 0;
            var mech1 = CoinDisplayManager.CoinMechs.Mech1;
            var mech2 = CoinDisplayManager.CoinMechs.Mech2;
            var mechNone = CoinDisplayManager.CoinMechs.None;

            CoinDisplayManager.CoinMechs[] mechs = new CoinDisplayManager.CoinMechs[] { mech1, mech2, mechNone };

            // Test All Mechs with quarter
            foreach(var mech in mechs)
            {
                // Players coin is a quarter
                manager.CreateNewRandomCoin();
                while (!manager.isPlayerCoinAQuarter())
                    manager.CreateNewRandomCoin();

                if(mech != mechNone)
                    Assert.AreEqual(quarterValue, manager.InsertCoin(mech));
                else
                    Assert.AreEqual(0, manager.InsertCoin(mech));

            }

            // Test All Mechs with dime
            foreach (var mech in mechs)
            {
                // Players coin is a quarter
                manager.CreateNewRandomCoin();
                while (manager.isPlayerCoinAQuarter())
                    manager.CreateNewRandomCoin();

                if (mech != mechNone)
                    Assert.AreEqual(dimeValue, manager.InsertCoin(mech));
                else
                    Assert.AreEqual(0, manager.InsertCoin(mech));
            }

            // At this point, both slots should have one dime in them

            Assert.AreEqual(1, manager.getNumberCoinsInReturn(mech1));
            Assert.AreEqual(1, manager.getNumberCoinsInReturn(mech2));

        }

        [TestMethod]
        public void MultipleCoinsInMechTest()
        {
            CoinDisplayManager manager = CoinDisplayManager.getCoinDisplaymanager();

            var mech1 = CoinDisplayManager.CoinMechs.Mech1;
            var mech2 = CoinDisplayManager.CoinMechs.Mech2;

            int iterations = 10;

            CoinDisplayManager.CoinMechs[] mechs = new CoinDisplayManager.CoinMechs[] { mech1, mech2 };

            foreach (var mech in mechs)
            {
                for (int i = 0; i < iterations; ++i)
                {
                    // Grab a dime from the pile
                    manager.CreateNewRandomCoin();
                    while (manager.isPlayerCoinAQuarter())
                        manager.CreateNewRandomCoin();

                    manager.InsertCoin(mech);
                }

                Assert.AreEqual(iterations, manager.getNumberCoinsInReturn(mech));

            }

            // Remove all coins from the returns
            foreach (var mech in mechs)
            {
                for (int i = 0; i < iterations; ++i)
                {
                    manager.ProcessCoinReturnClick(mech);
                    manager.DropCoin();
                }

                Assert.AreEqual(0, manager.getNumberCoinsInReturn(mech));
            }
        }

        [TestMethod]
        public void GrabReturnedCoinWhileHoldingCoin()
        {
            CoinDisplayManager manager = CoinDisplayManager.getCoinDisplaymanager();

            var mech1 = CoinDisplayManager.CoinMechs.Mech1;
            var mech2 = CoinDisplayManager.CoinMechs.Mech2;

            CoinDisplayManager.CoinMechs[] mechs = new CoinDisplayManager.CoinMechs[] { mech1, mech2 };

            foreach(var mech in mechs)
            {
                manager.DropCoin();

                //grab dime from the pile
                manager.CreateNewRandomCoin();
                while (manager.isPlayerCoinAQuarter())
                    manager.CreateNewRandomCoin();

                manager.InsertCoin(mech);

                // grab a quarter
                manager.CreateNewRandomCoin();
                while (!manager.isPlayerCoinAQuarter())
                    manager.CreateNewRandomCoin();

                manager.ProcessCoinReturnClick(mech);
                Assert.IsTrue(manager.isPlayerCoinAQuarter()); // player coin should still be a quarter
                Assert.AreEqual(1, manager.getNumberCoinsInReturn(mech)); // coins in mech should be 1;
            }
        }
    }
}
