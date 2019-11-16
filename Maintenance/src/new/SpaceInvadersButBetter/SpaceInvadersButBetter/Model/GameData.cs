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
        private int cointCount;

        public GameData()
        {

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

        public int getCoinCount()
        {
            return cointCount;
        }

        public void setCoinCount(int count)
        {
            this.cointCount = count;
        }

    }
}
