using System;
using System.Drawing;
using System.Drawing.Drawing2D;

namespace SpaceInvadersButBetter
{
    //---------------------------------------------------------------------
    // Base class of all objects on the screen that we need to know 
    // its positioning and image details
    //---------------------------------------------------------------------
    public class GameObject
    {
        protected Image MainImage = null;
        public Point Position = new Point(50, 50);
        protected Rectangle ImageBounds = new Rectangle(0, 0, 10, 10);
        protected Rectangle MovingBounds = new Rectangle();

        /**
         * Constructor
         */
        public GameObject(Bitmap image)
        {
            MainImage = (Image)image;
            ImageBounds.Width = MainImage.Width;
            ImageBounds.Height = MainImage.Height;
        }

        /**
         * Default Constructor
         */
        public GameObject()
        {
            MainImage = null;
        }

        /**
         * Returns the boundary of the image
         */
        public virtual Rectangle GetBounds()
        {
            return MovingBounds;
        }

        /**
         * Returns the width of the image
         */
        public virtual int GetWidth()
        {
            return ImageBounds.Width;
        }

        /**
         * Updates the moving bounds with the image and offset
         */
        public void UpdateBounds()
        {
            MovingBounds = ImageBounds;
            MovingBounds.Offset(Position);
        }

        /**
         * Draws the images
         */
        public virtual void Draw(Graphics g)
        {
            UpdateBounds();
            g.DrawImage(MainImage, MovingBounds, 0, 0, ImageBounds.Width, ImageBounds.Height, GraphicsUnit.Point);
        }
    }
}
