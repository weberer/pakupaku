using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpaceInvadersButBetter.Model
{
    public class GameData
    {
        private int score;
        private int level;
        private int credits;

        public GameData()
        {
            level = 1;
            score = 0;
            credits = 0;
        }

        public void resetLevelScore()
        {
            level = 1;
            score = 0;
        }

        public int getScore()
        {
            return score;
        }

        public void setScore(int score)
        {
            this.score = score;
        }

        public int getLevel()
        {
            return level;
        }

        public void setLevel(int level)
        {
            this.level = level;
        }


        public int GetCredits()
        {
            return credits;
        }

        public void AddCredit()
        {
            credits++;
        }

        public void DecrementCredits()
        {
            credits--;
        }
    }
}
