using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using SpaceInvadersButBetter.core;

namespace SpaceInvadersButBetter.Controller
{
    //---------------------------------------------------------------------
    // The GameLogic class handles game logic and communicates it back to the view
    //---------------------------------------------------------------------
    public class GameLogic
    {
        private int coinCount;
        private int score;
        private bool gameover;
        
        

        //increments with higher levels
        private int alien_speed = 6;
        private int alien_speed_factor = 1;
        private int alien_count = 66;

        
        private int TimerCounter = 0;
        private int MenuCount = 0;
        private int blinkCount = 0;
        private int scoreScrollCount = 0;
        

        private const int NUMBER_OF_SHIELDS = 4;
        private const int NUMBER_OF_ALIEN_ROWS = 6;
        private const int NUMBER_OF_ALIENS_PER_ROW = 11;
        private const int CREDIT_BLINK_COUNT = 8;
        private const double SPEEP_INCREASE_FACTOR = 1.25;

        private int credits;
        private List<Shield> Shields = new List<Shield>();
        
        private SpaceShip player;
        private Alien[,] alienGroup = new Alien[NUMBER_OF_ALIEN_ROWS, NUMBER_OF_ALIENS_PER_ROW];
        private int level;
        private List<Bullet> bullets = new List<Bullet>();
        private List<Bullet> alienbullets = new List<Bullet>();

        private GameView gameForm;
        public GameLogic(GameView view)
        {
            credits = 0;
            gameForm = view;
        }

        
        public void InitializeAliens(int level)
        {
            for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
            {
                for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
                {
                    alienGroup[i, j] = new Alien(alien_speed_factor, Resources.invader_open, Resources.invader_closed, (2 + i), j);
                }
            }
        }

        /**
         * Method called when space bar is hit to shoot a bullet or reset game in over
         */
        public void ShootButton(Keys keydown_shoot)
        {
            if (keydown_shoot == Keys.Space)
            {
                if (!gameover)
                {
                    int startX = player.Position.X + (Resources.space_ship.Width / 2) - 10;
                    int startY = player.Position.Y - (Resources.space_ship.Height / 2) + 10;
                    Bullet bullet = new Bullet(startX, startY, true);
                    bullets.Add(bullet);
                }
                else
                {
                    ResetGameStates();
                    ResetPlayer();
                    ResetAliens();
                    ResetSheilds();
                    ResetBullets();
                }
            }
        }
    }
}
