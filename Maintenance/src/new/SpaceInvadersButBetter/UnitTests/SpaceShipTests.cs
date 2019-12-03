using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace UnitTests
{
    [TestClass]
    public class SpaceShipTests
    {

        [TestMethod]
        public void KillPlayerTest()
        {
            SpaceInvadersButBetter.core.SpaceShip player = new SpaceInvadersButBetter.core.SpaceShip();

            player.kill();

            Assert.IsFalse(!player.isAlive());
        }

        [TestMethod]
        public void hitPlayerStillAlive()
        {
            SpaceInvadersButBetter.core.SpaceShip player = new SpaceInvadersButBetter.core.SpaceShip();

            Assert.IsTrue(player.hitAndIsAlive());
        }

        [TestMethod]
        public void hitPlayerAndDead()
        {
            SpaceInvadersButBetter.core.SpaceShip player = new SpaceInvadersButBetter.core.SpaceShip();
            player.hitAndIsAlive();
            player.hitAndIsAlive();
            Assert.IsTrue(player.hitAndIsAlive() == false);
        }

        [TestMethod]
        public void ResetPlayerTest()
        {
            SpaceInvadersButBetter.core.SpaceShip player = new SpaceInvadersButBetter.core.SpaceShip();
            player.hitAndIsAlive();
            player.hitAndIsAlive();
            player.reset();
            Assert.IsFalse(player.isAlive());
            Assert.IsTrue(player.getLifes() == 3);
        }
    }
}
