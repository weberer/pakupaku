﻿using System;
using System.Text;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using SpaceInvadersButBetter;
using SpaceInvadersButBetter.Controller;

namespace UnitTests
{
    /// <summary>
    /// Summary description for CreditSystemTest
    /// </summary>
    [TestClass]
    public class CreditSystemTest
    {
        #region Additional test attributes
        //
        // You can use the following additional attributes as you write your tests:
        //
        // Use ClassInitialize to run code before running the first test in the class
        // [ClassInitialize()]
        // public static void MyClassInitialize(TestContext testContext) { }
        //
        // Use ClassCleanup to run code after all tests in a class have run
        // [ClassCleanup()]
        // public static void MyClassCleanup() { }
        //
        // Use TestInitialize to run code before running each test 
        // [TestInitialize()]
        // public void MyTestInitialize() { }
        //
        // Use TestCleanup to run code after each test has run
        // [TestCleanup()]
        // public void MyTestCleanup() { }
        //
        #endregion

        [TestMethod]
        public void TestCreateCreditSystem()
        {
            CreditSystem credit = new CreditSystem();
            Assert.AreEqual(0, credit.Credits);
        }

        [TestMethod]
        public void TestAddDecrementCredit()
        {
            CreditSystem credit = new CreditSystem();
            credit.AddCredit();
            Assert.AreEqual(1, credit.Credits);
            credit.DecrementCredits();
            Assert.AreEqual(0, credit.Credits);
        }

        [TestMethod]
        public void TestCreditLimits()
        {
            CreditSystem credit = new CreditSystem();
            credit.AddCredit();
            credit.AddCredit();
            credit.AddCredit();
            credit.AddCredit();
            credit.AddCredit();
            credit.AddCredit();
            credit.AddCredit();
            credit.AddCredit();
            credit.AddCredit();
            Assert.AreEqual(9, credit.Credits);
            credit.AddCredit();
            Assert.AreEqual(9, credit.Credits);
            credit.DecrementCredits();
            credit.DecrementCredits();
            credit.DecrementCredits();
            credit.DecrementCredits();
            credit.DecrementCredits();
            credit.DecrementCredits();
            credit.DecrementCredits();
            credit.DecrementCredits();
            credit.DecrementCredits();
            Assert.AreEqual(0, credit.Credits);
            credit.DecrementCredits();
            Assert.AreEqual(0, credit.Credits);
        }
    }
}
