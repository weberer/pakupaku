using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpaceInvadersButBetter.Controller
{
    public class CreditSystem
    {
        private int credits;

        public CreditSystem()
        {
            credits = 0;
        }

        /**
         * Returns number of credits
         */
        public int GetCredits()
        {
            return credits;
        }

        public void SetCredits(int credits)
        {
            this.credits = credits;
        }

        /**
         * Increments credits
         */
        public void AddCredit()
        {
            if (credits < 9)
                credits++;
        

        }


        /**
         * Decrements Credits  (NOTE: this method isn't even used)
         */
        public void DecrementCredits()
        {
            if (credits >= 0)
            {
                credits--;
               // gameForm.UpdateCredits(credit.GetCredits());
            }
        }
        
    }

    
}
