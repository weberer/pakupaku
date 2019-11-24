using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpaceInvadersButBetter.core
{
    /// <summary>
    /// Defines the behavior of alien sprites
    /// </summary>
    public class Alien : GameObject
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

        /// <summary>
        /// Sets the movement speed, takes two images to switch between for animation, and position in the alien fleet
        /// Sets initial positions based on position in the fleet array
        /// </summary>
        /// <param name="speedFactor"></param>
        /// <param name="img1"></param>
        /// <param name="img2"></param>
        /// <param name="row"></param>
        /// <param name="column"></param>
        public Alien(double speedFactor, Bitmap img1, Bitmap img2, int row, int column) : base(img1)
        {
            Position.X = 20;
            Position.Y = 10;
            UpdateBounds();
            
            secondImg = (Image)img2;
            movingRight = true;
            beenHit = false;
            dead = false;
            Position.X = GetBounds().Width * column +  5;
            Position.Y = GetBounds().Height * row + 10;
            SetCounter(column * 50);
            speed = speedFactor;
            UpdateBounds();
        }



        /// <summary>
        /// Draws aliens that are still alive, decides which image to display based on counter
        /// </summary>
        /// <param name="g"></param>
        public override void Draw(Graphics g)
        {
            UpdateBounds();

            if(beenHit)
            {
                //expolsion
                return;
            }

            if(Counter % FLIP_FACTOR == 0)
            {
                g.DrawImage(MainImage, MovingBounds, 0, 0, ImageBounds.Width, ImageBounds.Height, GraphicsUnit.Point);
            }
            else
            {
                g.DrawImage(secondImg, MovingBounds, 0, 0, ImageBounds.Width, ImageBounds.Height, GraphicsUnit.Point);
            }
        }

        /// <summary>
        /// Moves individual aliens across the screen
        /// </summary>
        public void Move()
        {
            if(movingRight)
            {
                Position.X += JUMP_DISTANCE * (int)speed;
            }
            else
            {
                Position.X -= JUMP_DISTANCE * (int)speed;
            }
        }

        /// <summary>
        /// Move alien down 
        /// </summary>
        public void MoveDown()
        {
            Position.Y += GetBounds().Height / FALL_DISTANCE_FACTOR;
            UpdateBounds();
        }

        /// <summary>
        /// If aliens have stopped, trigger animation
        /// </summary>
        public void MoveInPlace()
        {
            Counter++;
        }

        /// <summary>
        /// Allows correction or offsetting of animation
        /// </summary>
        /// <param name="c"></param>
        public void SetCounter(int c)
        {
            Counter = c;
        }
    }
}
