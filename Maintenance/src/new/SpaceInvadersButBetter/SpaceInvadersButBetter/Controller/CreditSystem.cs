namespace SpaceInvadersButBetter.Controller
{
    public class CreditSystem
    {     
        public int Credits { get; set; }

        public CreditSystem() { Credits = 0; }      

        /**
         * Increase number of credits
         */
        public void AddCredit(int numCredits = 1)
        {
            Credits += numCredits;

            if (Credits > 9)
                Credits = 9;
        }

        /**
         * Decrements Credits  (NOTE: this method isn't even used)
         */
        public void DecrementCredits()
        {
            if (Credits > 0)
                Credits--;
        }
        
    }
}
