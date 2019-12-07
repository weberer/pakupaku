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
            testCoin.CreateCoin();
            while(testCoin.IsQuarter())
            {
                testCoin.DeleteCoin();
                testCoin.CreateCoin();
            }
            Assert.IsFalse(testCoin.IsQuarter());
            while(!testCoin.IsQuarter())
            {
                testCoin.DeleteCoin();
                testCoin.CreateCoin();
            }
            Assert.IsTrue(testCoin.IsQuarter());
        }

        [TestMethod]
        public void AssetsClickedTest()
        {
            Coin testCoin = new Coin();

            Assert.IsTrue(testCoin.CheckPileClicked(100, 500));
            Assert.IsFalse(testCoin.CheckPileClicked(50, 200));

            Assert.IsTrue(testCoin.CheckSlotClicked(500, 650) == 1);
            Assert.IsTrue(testCoin.CheckSlotClicked(520, 650) == 2);
            Assert.IsTrue(testCoin.CheckSlotClicked(100, 500)  == -1);

            Assert.IsTrue(testCoin.CheckGivebackClicked(490, 680) == 1);
            Assert.IsTrue(testCoin.CheckGivebackClicked(520, 680) == 2);
            Assert.IsTrue(testCoin.CheckGivebackClicked(100, 500) == -1);

        }

    }
}
