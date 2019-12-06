using System.Drawing;

namespace SpaceInvadersButBetter.core
{
    public class UFO : GameObject
    {
        private const int FLIP_FACTOR = 3;
        private const int JUMP_DISTANCE = 1;
        private const int FALL_DISTANCE_FACTOR = 4;
        private const int STARTING_X = 500;
        private const int STARTING_Y = 50;

        private Image secondImg;
        private bool beenHit;
        private double speed = 1;

        public bool getBeenHit()
        {
            return beenHit;
        }

        public void setBeenHit(bool beenHit)
        {
            this.beenHit = beenHit;
        }

        /// <summary>
        /// Sets the movement speed, starts at the top right corner of the screen.
        /// </summary>
        public UFO(double speedFactor, Bitmap image) : base(image)
        {
            Position.X = STARTING_X;
            Position.Y = STARTING_Y;
            UpdateBounds();
            
            beenHit = false;
            speed = speedFactor;
            UpdateBounds();
        }

        /// <summary>
        /// Draws UFO
        /// </summary>
        /// <param name="g"></param>
        public override void Draw(Graphics UFO)
        {
            UpdateBounds();
            if (beenHit)
                return;
            UFO.DrawImage(MainImage, Position.X, Position.Y);
        }


        /// <summary>
        /// Moves UFO across the screen
        /// </summary>
        public void Move()
        {
            if (beenHit)
                return;
          
            Position.X -= JUMP_DISTANCE * (int)speed;
        }
    }
}
