using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpaceInvadersButBetter;
using SpaceInvadersButBetter.Controller;
using SpaceInvadersButBetter.views;

namespace UnitTests
{
    [TestClass]
    public class GameLogicTest
    {
        /**
         * Tests that the game will start when credits are greater than 0
         */ 
        [TestMethod]
        public void TestStartGameWithCredit()
        {
            GameBoxForm box = new GameBoxForm();
            GameLogic gameLogic = box.GetLogic();
            CreditSystem credit = box.GetCredit();
            credit.AddCredit();
            Assert.AreEqual(gameLogic.GetCredits(), 1);
            gameLogic.StartGame();
            Assert.AreEqual(gameLogic.GetCredits(), 0);
            Assert.IsFalse(gameLogic.IsStartScreenActive());
        }

        /**
         * Tests that the game cannot start without any credits in the system.
         */ 
        [TestMethod]
        public void TestStartGameWithNoCredits()
        {
            GameBoxForm box = new GameBoxForm();
            GameLogic gameLogic = box.GetLogic();
            gameLogic.StartGame();
            Assert.AreEqual(gameLogic.GetCredits(), 0);
            Assert.IsTrue(gameLogic.IsStartScreenActive());
        }
        /**
         * Tests that the credit counter stops counting after nine credits.
         */ 
        [TestMethod]
        public void TestMaxCredit()
        {
            GameBoxForm box = new GameBoxForm();
            GameLogic gameLogic = box.GetLogic();
            CreditSystem credit = box.GetCredit();

            credit.AddCredit();
            Assert.AreEqual(gameLogic.GetCredits(), 1);
            credit.AddCredit(8);
            Assert.AreEqual(gameLogic.GetCredits(), 9);
            credit.AddCredit(10);
            Assert.AreEqual(gameLogic.GetCredits(), 9);
            credit.AddCredit(-1);
            Assert.AreEqual(gameLogic.GetCredits(), 8);
        }
        /**
         * Tests to ensure that the game does not allow multiple StartGame calls while the
         * game is in progress and makes sure that the credit sentinel works to prevent negative
         * credits.
         */
        [TestMethod]
        public void TestStartGameMultipleStartsZeroCredits()
        {
            GameBoxForm box = new GameBoxForm();
            GameLogic gameLogic = box.GetLogic();
            CreditSystem credit = box.GetCredit();
            credit.AddCredit();
            Assert.AreEqual(gameLogic.GetCredits(), 1);
            gameLogic.StartGame();
            Assert.AreEqual(gameLogic.GetCredits(), 0);
            Assert.IsFalse(gameLogic.IsStartScreenActive());
            gameLogic.StartGame();
            Assert.AreEqual(gameLogic.GetCredits(), 0);
        }
        /**
         * Tests to ensure that the game does not allow multiple StartGame calls while the
         * game is in progress and that the credits are not decremented if StartGame is called again
         */
        [TestMethod]
        public void TestStartGameMultipleStartsMultipleCredits()
        {
            GameBoxForm box = new GameBoxForm();
            GameLogic gameLogic = box.GetLogic();
            CreditSystem credit = box.GetCredit();
            credit.AddCredit();
            Assert.AreEqual(gameLogic.GetCredits(), 1);
            credit.AddCredit();
            Assert.AreEqual(gameLogic.GetCredits(), 2);
            gameLogic.StartGame();
            Assert.AreEqual(gameLogic.GetCredits(), 1);
            Assert.IsFalse(gameLogic.IsStartScreenActive());
            gameLogic.StartGame();
            Assert.AreEqual(gameLogic.GetCredits(), 1);
        }

    }
}
