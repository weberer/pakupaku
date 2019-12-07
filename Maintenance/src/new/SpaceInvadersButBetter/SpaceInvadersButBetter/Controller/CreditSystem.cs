using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpaceInvadersButBetter.Controller
{
    public class CreditSystem
    {
     
        public int Credits { get; set; }

        public CreditSystem()
        {
            Credits = 0;
        }

        /**
         * Returns number of credits
         */
      

        /**
         * Increments credits
         */
        public void AddCredit()
        {
            if (Credits < 9)
                Credits++;
        

        }


        /**
         * Decrements Credits  (NOTE: this method isn't even used)
         */
        public void DecrementCredits()
        {
            if (Credits > 0)
            {
                Credits--;
               // gameForm.UpdateCredits(credit.GetCredits());
            }
        }
        
    }

    
}
