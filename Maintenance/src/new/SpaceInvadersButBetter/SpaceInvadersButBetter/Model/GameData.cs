using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpaceInvadersButBetter.Model
{
    public class GameData
    {
        public int Score { get; set; }
        public int Level { get; set; }
       

        public GameData()
        {
            Level = 1;
            Score = 0;
        }

        public void resetLevelScore()
        {

            Level = 1;
            Score = 0;
        }

    }
}
