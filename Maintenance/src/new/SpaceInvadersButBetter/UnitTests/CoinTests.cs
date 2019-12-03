using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpaceInvadersButBetter.core;
using SpaceInvadersButBetter;
namespace UnitTests
{
    [TestClass]
    public class CoinTests
    {
        [TestMethod]
        public void CoinTypeTest()
        {
            Coin testCoin = new Coin();
            testCoin.createCoin();
            while(testCoin.isQuarter())
            {
                testCoin.deleteCoin();
                testCoin.createCoin();
            }
            Assert.IsFalse(testCoin.isQuarter());
            while(!testCoin.isQuarter())
            {
                testCoin.deleteCoin();
                testCoin.createCoin();
            }
            Assert.IsTrue(testCoin.isQuarter());
        }

        [TestMethod]
        public void AssetsClickedTest()
        {
            Coin testCoin = new Coin();

            Assert.IsTrue(testCoin.checkPileClicked(100, 500));
            Assert.IsFalse(testCoin.checkPileClicked(50, 200));

            Assert.IsTrue(testCoin.checkSlotClicked(500, 650) == 1);
            Assert.IsTrue(testCoin.checkSlotClicked(520, 650) == 2);
            Assert.IsTrue(testCoin.checkSlotClicked(100, 500)  == -1);

            Assert.IsTrue(testCoin.checkGivebackClicked(490, 680) == 1);
            Assert.IsTrue(testCoin.checkGivebackClicked(520, 680) == 2);
            Assert.IsTrue(testCoin.checkGivebackClicked(100, 500) == -1);

        }

    }
}
