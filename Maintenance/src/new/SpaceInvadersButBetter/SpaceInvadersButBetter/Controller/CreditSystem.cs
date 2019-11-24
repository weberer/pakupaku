using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpaceInvadersButBetter.Controller
{
    class CreditSystem
    {
        private int credits;

        public CreditSystem()
        {
            credits = 0;
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
