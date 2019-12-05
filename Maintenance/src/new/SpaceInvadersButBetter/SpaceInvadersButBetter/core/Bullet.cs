namespace SpaceInvadersButBetter.core
{
    //---------------------------------------------------------------------
    // The Bullet class creates and specifies the characteristics of a
    // bullet.
    //---------------------------------------------------------------------
    public class Bullet : GameObject
   {
      private const int BULLET_SPEED = 7;

      private bool isFriendly;

        // Bullet constructor. Initializes x and y coordinates and sets
        // different image based on if it came from an alien or player.
        // Parameters:
        // start_position_x: x position for bullet start.
        // start_position_y: y position for bullet start.
        // friendly: is theis an alien bullet or not.
        public Bullet(int start_position_x, int start_position_y, bool friendly) : base(Resources.Bullet)
      {
          X = start_position_x;
          Y = start_position_y;
          isFriendly = friendly;
          if(!friendly)
                MainImage = Resources.alien_bullet;
      }

        // Moves the bullet based on bulletSpeed.
      public void Move()
      {
          if (isFriendly)
              Y -= BULLET_SPEED;
          else
              Y += BULLET_SPEED;
      }
    }
}
