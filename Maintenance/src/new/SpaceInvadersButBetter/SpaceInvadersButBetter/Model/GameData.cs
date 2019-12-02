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

        
 

        public GameData()
        {
            level = 1;
            score = 0;
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

        public int GetLevel()
        {
            return level;
        }

        public void SetLevel(int level)
        {
            this.level = level;
        }

      

        //credit stuff moved to class CreditSystem 11/23 - Evan
        /*
        public int GetCredits()
        {
            return credits;
        }
        */

        /*
        public void AddCredit()
        {
            credits++;
        }

        public void DecrementCredits()
        {
            credits--;
        }
        */
    }
}
