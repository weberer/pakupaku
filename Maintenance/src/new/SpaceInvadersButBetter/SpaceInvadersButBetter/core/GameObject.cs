using System.Drawing;

namespace SpaceInvadersButBetter.core
{
    //---------------------------------------------------------------------
    // Base class of all objects on the screen that we need to know 
    // its positioning and image details
    //---------------------------------------------------------------------
    public class GameObject : GameEntity
    {
        protected Image MainImage = null;

        protected Rectangle ImageBounds = new Rectangle(0, 0, 10, 10);
        protected Rectangle MovingBounds = new Rectangle();

        public int Width { get { return ImageBounds.Width; } }

        /**
         * Constructor
         */
        public GameObject(Bitmap image)
        {
            Position = new Point(50, 50);
            MainImage = (Image)image;
            ImageBounds.Width = MainImage.Width;
            ImageBounds.Height = MainImage.Height;
        }

        /**
         * Changes the object image
         */
        public void SetMainImage(Bitmap image)
        {
           
            MainImage = (Image)image;
            ImageBounds.Width = MainImage.Width;
            ImageBounds.Height = MainImage.Height;

        }

        /**
         * Default Constructor
         */
        public GameObject() { MainImage = null; }

        /**
         * Returns the boundary of the image
         */
        public virtual Rectangle GetBounds() {  return MovingBounds; }

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
        override
        public void Draw(Graphics g) // was virtual
        {
            UpdateBounds();
            g.DrawImage(MainImage, MovingBounds, 0, 0, ImageBounds.Width, ImageBounds.Height, GraphicsUnit.Point);
        }
    }
}
