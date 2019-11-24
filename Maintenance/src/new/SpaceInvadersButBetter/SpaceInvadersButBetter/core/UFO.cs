using System.Drawing;

namespace SpaceInvadersButBetter.core
{
    public class UFO : GameObject
    {
        private const int FLIP_FACTOR = 3;
        private const int JUMP_DISTANCE = 5;
        private const int FALL_DISTANCE_FACTOR = 4;

        private Image secondImg;
        private int Counter = 0;
        public bool movingRight;
        public bool beenHit;
        public bool dead;
        private double speed = 1;

        public UFO(double speedFactor, Bitmap image, int row, int column) : base(image)
        {
            Position.X = 20;
            Position.Y = 10;
            UpdateBounds();

            secondImg = (Image)img2;
            movingRight = true;
            beenHit = false;
            dead = false;
            Position.X = GetBounds().Width * column + 5;
            Position.Y = GetBounds().Height * row + 10;
            SetCounter(column * 50);
            speed = speedFactor;
            UpdateBounds();
        }
    }
}
