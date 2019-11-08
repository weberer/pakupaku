using System;
using System.Collections;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace SpaceInvadersButBetter.core
{
    //---------------------------------------------------------------------
    // The Shield class creates and specifies the characteristics of shield
    //---------------------------------------------------------------------
    public class Shield : GameObject
    {
        private int health;
        private const int DECREMENT = 10;
        private const int ALIEN_DECREMENT = 5;

        // Constructor. Initializes health to 100% for the shield.
        public Shield() : base(Resources.shield)
        {
            health = 100;
        }

        // Draws the shield
        public override void Draw(Graphics g)
        {
            base.Draw(g);
        }

        // Gets health of the shield
        public int getHealth()
        {
            return health;
        }

        // Decreases the health of the shield by the specified 
        // DECREMENT ammount when a bullet hit. 
        public void healthHit()
        {
        health = health - DECREMENT;
        }

        // Decreases the health of the shield by the specified 
        // ALIEN_DECREMENT ammount when an aliens bullet hit. 
        public void alienHealthHit()
        {
            health = health - ALIEN_DECREMENT;
        }
    }
}
