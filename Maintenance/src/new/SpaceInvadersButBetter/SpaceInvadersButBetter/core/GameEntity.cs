using System.Drawing;

namespace SpaceInvadersButBetter.core
{
    public abstract class GameEntity
    {
        protected Point Position;

        protected GameEntity() { Position = new Point(); }
        
        public int X { get { return Position.X; } set { Position.X = value; } }
        public int Y { get { return Position.Y; } set { Position.Y = value; } }

        public abstract void Draw(Graphics g);
    }
}
