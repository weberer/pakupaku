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
            for (int i = 0; i < 10; i++)
            {
                sw.WriteLine(0);
            }
            sw.Close();
        }

        [TestMethod]
        public void WriteAndReadOneScore()
        {
            clearTextFile();
            int score = 10;
            scoreUtil.Write(score);
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
            scoreUtil.Write(prevScore);
            int score = 50;
            scoreUtil.Write(score);
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
                scoreUtil.Write(i * 10);
            }

            int scoreAtIndex4 = 50;

            Assert.IsTrue(scoreUtil.getScoreAt(4) == scoreAtIndex4);

            //list should be sorted so index 0 should give 90
            Assert.IsTrue(scoreUtil.getScoreAt(0) == 90);
            Assert.IsTrue(scoreUtil.getTopScore() == scoreUtil.getScoreAt(0));
        }
    }
}

