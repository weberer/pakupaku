using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpaceInvadersButBetter
{
    //---------------------------------------------------------------------
    // Joystick class that has 3 states to display on arrow keys
    //---------------------------------------------------------------------
    public class Joystick
    {
        private Image[] views = new Image[4];
        private int currentView;

        /**
         * Constructor with 3 images, sets to up position
         */
        public Joystick()
        {
            views[0] = (Image)Resources.joystick_left;
            views[1] = (Image)Resources.joystick_up;
            views[2] = (Image)Resources.joystick_right;
            currentView = 1;
        }

        /**
         * Sets view by given index
         */
        public void setView(int viewIndex)
        {
            currentView = viewIndex;
        }

        /**
         * Returns current view index
         */
        public int getCurrentView()
        {
            return currentView;
        }

        /**
         * Draws the image at position
         */
        public void draw(Graphics g)
        {
            g.DrawImage(views[currentView], 185, 485);
        }

    }
}
