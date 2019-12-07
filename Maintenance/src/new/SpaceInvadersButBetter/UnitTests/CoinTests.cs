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
            CoinDisplayManager testCoin = new CoinDisplayManager();
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
            CoinDisplayManager testCoin = new CoinDisplayManager();

            Assert.IsTrue(testCoin.CheckPileClicked(100, 500));
            Assert.IsFalse(testCoin.CheckPileClicked(50, 200));

            Assert.IsTrue(testCoin.CheckCoinSlotClicked(500, 650) == 1);
            Assert.IsTrue(testCoin.CheckCoinSlotClicked(520, 650) == 2);
            Assert.IsTrue(testCoin.CheckCoinSlotClicked(100, 500)  == -1);

            Assert.IsTrue(testCoin.CheckCoinReturnClicked(490, 680) == 1);
            Assert.IsTrue(testCoin.CheckCoinReturnClicked(520, 680) == 2);
            Assert.IsTrue(testCoin.CheckCoinReturnClicked(100, 500) == -1);

        }

    }
}
