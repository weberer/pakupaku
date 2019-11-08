using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpaceInvadersButBetter.core
{
    //---------------------------------------------------------------------
    // The Bullet class creates and specifies the characteristics of a
    // bullet.
    //---------------------------------------------------------------------
    public class Bullet : GameObject
   {
      private const int bulletSpeed = 7;
      private bool isFriendly;

        // Bullet constructor. Initializes x and y coordinates and sets
        // different image based on if it came from an alien or player.
        // Parameters:
        // start_position_x: x position for bullet start.
        // start_position_y: y position for bullet start.
        // friendly: is theis an alien bullet or not.
        public Bullet(int start_position_x, int start_position_y, bool friendly) : base(Resources.Bullet)
      {
          Position.X = start_position_x;
          Position.Y = start_position_y;
          isFriendly = friendly;
          if(!friendly)
                MainImage = Resources.alien_bullet;
      }

        // Moves the bullet based on bulletSpeed.
      public void Move()
      {
          if (isFriendly)
              Position.Y -= bulletSpeed;
          else
              Position.Y += bulletSpeed;
      }

        // Returns the y position of the bullet.
      public int getY()
      {
          return Position.Y;
      }
    }
}
