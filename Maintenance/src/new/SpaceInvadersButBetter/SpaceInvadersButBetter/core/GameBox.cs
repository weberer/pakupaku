using System.Drawing;

namespace SpaceInvadersButBetter
{
    //---------------------------------------------------------------------
    // GameBox class to hole the image for the background of the arcade
    // view.
    //---------------------------------------------------------------------
    public class GameBox
    {
        protected Image MainImage;

        /**
         * Constructor
         */
        public GameBox() { MainImage = (Image)Resources.box; }

        /**
         * Draws image
         */
        public void Draw(Graphics g) { g.DrawImage(MainImage, 0, 0); }
    }
}
