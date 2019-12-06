using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace UnitTests
{
    [TestClass]
    public class ScoreUtitityTests
    {
        SpaceInvadersButBetter.core.ScoreUtility scoreUtil = new SpaceInvadersButBetter.core.ScoreUtility();

        private void clearTextFile()
        {
            System.IO.StreamWriter sw = new System.IO.StreamWriter("highscore.txt", false);
            int index = 0;
            while(index < 5)
            {
                sw.WriteLine("AAA");
                sw.WriteLine(0);
                index++;
                
            }
            sw.Close();
        }

        [TestMethod]
        public void WriteAndReadOneScore()
        {
            clearTextFile();
            int score = 10;
            scoreUtil.Write(score, "AAA");
            scoreUtil.Read();
            int result = scoreUtil.getTopScore();
            
            Assert.AreEqual(score, result);

        }

        /**
         * Test writing new score while shifiting elements down
         * saving scores not overwriting them
         */
        [TestMethod]
        public void WriteNewTopScore()
        {
            clearTextFile();
            int prevScore = 10;
            scoreUtil.Write(prevScore, "AAA");
            int score = 50;
            scoreUtil.Write(score, "AAA");
            scoreUtil.Read();
            int result = scoreUtil.getTopScore();
            
            //top Score
            Assert.IsTrue(result == score);

            //previous Score of  10 should still be there at index 1 
            Assert.IsTrue(scoreUtil.getScoreAt(1) == prevScore);

        }

        /**
         * Test score retrival at index and sorting
         */
        [TestMethod]
        public void WriteMultipleScoresGetAtIndex()
        {
            clearTextFile();

            //list is entered in accending order
            for (int i = 0; i < 10; i++)
            {
                scoreUtil.Write(i * 10, "AAA");
            }

            int scoreAtIndex4 = 50;

            Assert.IsTrue(scoreUtil.getScoreAt(4) == scoreAtIndex4);

            //list should be sorted so index 0 should give 90
            Assert.IsTrue(scoreUtil.getScoreAt(0) == 90);
            Assert.IsTrue(scoreUtil.getTopScore() == scoreUtil.getScoreAt(0));
        }

        /**
         * TestDeterminePosition tests if the DeterminePosition method in ScoreUtility is working as intended.
         * For the top five scores, initialized as increments of 100 from 100 to 500, the scores inbetween two
         * levels should return the desired position. for a score > 500 1 should be returned, but a score >100 should
         * recieve a -1 value.
         */
         [TestMethod]
         public void TestDeterminePosition()
        {
            clearTextFile();
            scoreUtil.Write(500, "AAA");
            scoreUtil.Write(400, "AAA");
            scoreUtil.Write(300, "AAA");
            scoreUtil.Write(200, "AAA");
            scoreUtil.Write(100, "AAA");

            int test = scoreUtil.DeterminePosition(600);
            Assert.AreEqual(1, test);

            test = scoreUtil.DeterminePosition(450);
            Assert.AreEqual(2, test);

            test = scoreUtil.DeterminePosition(350);
            Assert.AreEqual(3, test);

            test = scoreUtil.DeterminePosition(250);
            Assert.AreEqual(4, test);

            test = scoreUtil.DeterminePosition(150);
            Assert.AreEqual(5, test);

            test = scoreUtil.DeterminePosition(50);
            Assert.AreEqual(-1, test);

        }

        /**
         * Tests that the initials in the high score file are properly updated when
         * a new score is entered. clearTextFile initializes the highscore text file with
         * scores of zero and initials of "AAA". this method makes sure that the "AAA" values
         * are properly updated when a new score is written.
         */
         [TestMethod]
         public void TestInitialChanging()
        {
            clearTextFile();
            scoreUtil.Write(500, "AAA");
            scoreUtil.Write(400, "BBB");
            scoreUtil.Write(300, "CCC");
            scoreUtil.Write(200, "DDD");
            scoreUtil.Write(100, "EEE");
            string[] initials = scoreUtil.GetInitials();
            Assert.AreEqual("AAA", initials[0]);
            Assert.AreEqual("BBB", initials[1]);
            Assert.AreEqual("CCC", initials[2]);
            Assert.AreEqual("DDD", initials[3]);
            Assert.AreEqual("EEE", initials[4]);

        }
    }
}

