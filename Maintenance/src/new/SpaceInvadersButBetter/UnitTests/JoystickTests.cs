using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;


namespace UnitTests
{
    [TestClass]
    public class JoystickTests
    {
        [TestMethod]
        public void SetViewLeft()
        {
            SpaceInvadersButBetter.Joystick stick = new SpaceInvadersButBetter.Joystick();
            stick.setView(0);
            Assert.IsTrue(stick.getCurrentView() == 0);
        }

        [TestMethod]
        public void SetViewRight()
        {
            SpaceInvadersButBetter.Joystick stick = new SpaceInvadersButBetter.Joystick();
            stick.setView(2);
            Assert.IsTrue(stick.getCurrentView() == 2);
        }

        [TestMethod]
        public void SetViewUp()
        {
            SpaceInvadersButBetter.Joystick stick = new SpaceInvadersButBetter.Joystick();
            stick.setView(1);
            Assert.IsTrue(stick.getCurrentView() == 1);
        }
    }
}
