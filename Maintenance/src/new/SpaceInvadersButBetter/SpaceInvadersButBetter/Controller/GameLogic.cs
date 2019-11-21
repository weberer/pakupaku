using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using SpaceInvadersButBetter.core;
using SpaceInvadersButBetter.Model;

namespace SpaceInvadersButBetter.Controller
{
   //---------------------------------------------------------------------
   // The GameLogic class handles game logic and communicates it back to the view
   //---------------------------------------------------------------------
   public class GameLogic
   {

      private bool gameover;



      //increments with higher levels
      private int alien_speed = 6;
      private int alien_speed_factor = 1;
      private int alien_count = 66;
      private const int MAX_ALIENS = 66;

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
      private List<Bullet> bullets = new List<Bullet>();
      private List<Bullet> alienbullets = new List<Bullet>();




      private GameView gameForm;
      private GameBoxForm gameBox;
      private GameData data;
      public GameLogic(GameBoxForm boxForm)
      {
         credits = 0;
         gameBox = boxForm;
         data = new GameData();
      }
      public void SetGameView(GameView view)
      {
         this.gameForm = view;
      }
      /**
       * Creates Aliens for board
       */


      private bool StartScreenActive = true;




      public Alien[,] InitializeAliens(int level)
      {
         for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
         {
            for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
            {
               alienGroup[i, j] = new Alien(alien_speed_factor, Resources.invader_open, Resources.invader_closed, (2 + i), j);
            }
         }
         return alienGroup;
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
               gameForm.UpdateBullets(bullets);
            }
            /*else //This is handled in EndGame now -Michael 11/19
            {

                //ResetGameStates();
                //ResetPlayer();
                //ResetAliens();
                //ResetShields();
                //ResetBullets();
            }*/
         }
      }

      /**
       * Resets game states for logic
       */
      private void ResetGameStates()
      {
         gameover = false;
         alien_speed = 6;
         alien_speed_factor = 1;
         alien_count = MAX_ALIENS;
         data.resetLevelScore();
         gameForm.setLevelLabel(data.getLevel().ToString());
         gameForm.setScoreLabel(data.getScore().ToString());
      }

      /**
      * Resets player and lifes
      */
      private void ResetPlayer()
      {
         player.reset();
         gameForm.setLivesLabel(player.getLifes().ToString());
         gameForm.UpdatePlayer(player);
      }

      /**
       * Clears aliens and resets them
       */
      public void ResetAliens()
      {
         Array.Clear(alienGroup, 0, alienGroup.Length);
         for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
         {
            for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
            {
               alienGroup[i, j] = new Alien(alien_speed_factor, Resources.invader_open, Resources.invader_closed, (2 + i + 0), j);
            }
         }
         gameForm.UpdateAlienList(alienGroup);
      }

      /**
       * Clears sheilds and resets them
       */
      /*private void ResetShields()
      {
          Shields.Clear();
          gameForm.InitializeObject_Shields();
      }*/

      /**
      * Clears bullet lists
      */
      private void ResetBullets()
      {
         alienbullets.Clear();
         bullets.Clear();
      }

      /**
       * Updates score with given points and displays
       */
      public void UpdateScore(int points)
      {
         int score = data.getScore();
         score += 10;
         data.setScore(score);
         gameForm.setScoreLabel(score.ToString());
      }

      /**
       * Increments level, speed factor, and resets aliens
       */
      public void NextLevel()
      {
         int level = data.getLevel();
         data.setLevel(++level); //level++
         gameForm.setLevelLabel(level.ToString()); //update level on screen


         alien_speed_factor = Convert.ToInt32(alien_speed_factor * SPEEP_INCREASE_FACTOR);
         ResetAliens();
         alien_count = MAX_ALIENS;
      }

      /**
       * Creates 4 sheilds
       */

      public List<Shield> InitializeObject_Shields()
      {
         Shields.Clear();
         for (int i = 0; i < NUMBER_OF_SHIELDS; i++)
         {
            Shield shield = new Shield();
            Shields.Add(shield);
            int shieldX = Shields[i].GetBounds().Width + 30 + (i * 135);
            int shieldY = gameForm.GetShieldBottom(shield);
            Shields[i].Position.X = shieldX;
            Shields[i].Position.Y = shieldY;

            gameForm.SetupShieldLabel(shield, shieldX, shieldY);
         }
         return Shields;
      }


      //ResetGameStates();
      //ResetPlayer();
      //ResetAliens();
      //ResetSheilds();
      //ResetBullets();
      //       }
      //    }

      public void addCredit()
      {
         if (data.GetCredits() < 9)
            data.AddCredit();
         gameForm.UpdateCredits(data.GetCredits());

      }
      public void DecrementCredits()
      {
         if (credits > 0)
         {
            data.DecrementCredits();
            gameForm.UpdateCredits(data.GetCredits());
         }
      }
      public bool IsStartScreenActive()
      {
         return StartScreenActive;
      }
      public void StartGame()
      {
         if (data.GetCredits() > 0)
         {
            if (StartScreenActive)
            {
               gameForm.EraseStartScreen();
               data.DecrementCredits();
               StartScreenActive = false;
            }
         }
      }
      public void EndGame()
      {
         //High Score Handling Code here.
         gameForm.ResetGameObjects();
         ResetGameStates();
         //ResetPlayer();
         ResetBullets();
         StartScreenActive = true;
         gameForm.ShowStartScreen();

      }

      /**
       * Testing method to fetch the credits int for the test method.
       */
      public int GetCredits()
      {
         return data.GetCredits();
      }

      /**
       * Creates player spaceship object
       */
      public SpaceShip InitializeSpaceShip()
      {
         player = new SpaceShip();
         gameForm.setLivesLabel(player.getLifes().ToString());
         return player;

      }

      /**
       * Calls all Collision checkers for the game.
       */
      public void CollisionCheck()
      {
         ShieldCheck();
         AlienCheck();
         AlienHitSheild();
         AlienHitPersonCheck();
         AlienBulletsCheck();
         checkShieldHitByAlien();
      }

      /**
       * Checks for bullet collision with sheild
       */
      private void ShieldCheck()
      {
         for (int i = 0; i < bullets.Count; i++)
         {
            if (Shields.Count > 0) //Shield Check
            {
               if (bullets[i].Position.Y < Shields[0].Position.Y)
               {
                  bool delete = false;
                  int shieldIndexHit = -1;
                  for (int j = 0; j < Shields.Count; j++)
                     if (Shields[j].Position.X < bullets[i].Position.X && (Shields[j].Position.X + Resources.shield.Width - 20) > bullets[i].Position.X)
                     {
                        delete = true;
                        shieldIndexHit = j;
                     }
                  if (delete && shieldIndexHit != -1)
                  {
                     bullets.RemoveAt(i);
                     Shields[shieldIndexHit].healthHit();
                     gameForm.UpdateShield(Shields[shieldIndexHit].getHealth(), shieldIndexHit);
                     if (Shields[shieldIndexHit].getHealth() <= 0)
                     {
                        Shields.RemoveAt(shieldIndexHit);
                        gameForm.RemoveShield(shieldIndexHit);
                     }
                  }
               }
            }
         }
      }

      private void AlienHitSheild()
      {
         for (int i = 0; i < alienbullets.Count; i++) // alien shot hit sheild
         {
            if (Shields.Count > 0) //Shield Check
            {
               if (alienbullets[i].Position.Y > Shields[0].Position.Y)
               {
                  bool delete = false;
                  int shieldIndexHit = -1;
                  for (int j = 0; j < Shields.Count; j++)
                     if (Shields[j].Position.X < alienbullets[i].Position.X && (Shields[j].Position.X + Resources.shield.Width - 20) > alienbullets[i].Position.X)
                     {
                        delete = true;
                        shieldIndexHit = j;
                     }
                  if (delete && shieldIndexHit != -1)
                  {
                     alienbullets.RemoveAt(i);
                     Shields[shieldIndexHit].alienHealthHit();
                     gameForm.UpdateShield(Shields[shieldIndexHit].getHealth(), shieldIndexHit);
                     if (Shields[shieldIndexHit].getHealth() <= 0)
                     {
                        Shields.RemoveAt(shieldIndexHit);
                        gameForm.RemoveShield(shieldIndexHit);
                        gameForm.UpdateAlienBullets(alienbullets);
                     }
                  }
               }
            }
         }
      }


      public List<Bullet> GenerateAlienBullet()
      {
         List<Tuple<Alien, int>> bottomAliens = new List<Tuple<Alien, int>>();
         Alien nearest = null;
         for (int i = 0; i < 11; i++)
         {
            for (int k = 5; k > -1; k--)
               if (!alienGroup[k, i].beenHit)
               {
                  int posDiff = Math.Abs(alienGroup[k, i].Position.X - player.Position.X);
                  Tuple<Alien, int> pair = new Tuple<Alien, int>(alienGroup[k, i], posDiff);
                  bottomAliens.Add(pair);
                  break;
               }
         }

         if (bottomAliens.Count != 0)
         {
            int finalDiff = bottomAliens[0].Item2;
            nearest = bottomAliens[0].Item1;
            foreach (Tuple<Alien, int> pair in bottomAliens)
            {
               if (finalDiff > pair.Item2)
               {
                  finalDiff = pair.Item2;
                  nearest = pair.Item1;
               }
            }
         }

         if (nearest != null)
         {

            int startX = nearest.Position.X + 10;
            int startY = nearest.Position.Y + 30;
            Bullet bullet = new Bullet(startX, startY, false);
            alienbullets.Add(bullet);
         }
         return alienbullets;
      }

      public void MovementHandlerPlayer(Keys joystick, int right)
      {
         if (joystick == Keys.Left)
         {
            player.MoveLeft();
         }
         else if (joystick == Keys.Right)
         {
            player.MoveRight(right);
         }
         gameForm.UpdatePlayer(player);
      }
      public void MovementHandlerBullets()
      {
         for (int i = 0; i < bullets.Count; i++)
            bullets[i].Move();
         gameForm.UpdateBullets(bullets);
         for (int i = 0; i < alienbullets.Count; i++)
            alienbullets[i].Move();
      }


      public void AlienCheck()
      {
         for (int i = 0; i < bullets.Count; i++) //Alien Check
         {
            if (checkBulletHit(bullets[i]))
            {
               bullets.RemoveAt(i);
               gameForm.UpdateBullets(bullets);
            }
         }
      }

      private bool checkBulletHit(Bullet b)
      {
         for (int r = 0; r < NUMBER_OF_ALIEN_ROWS; r++)
         {
            for (int c = 0; c < NUMBER_OF_ALIENS_PER_ROW; c++)
            {
               if ((alienGroup[r, c].beenHit == false) && alienGroup[r, c].GetBounds().IntersectsWith(b.GetBounds()))
               {
                  alienGroup[r, c].beenHit = true;
                  alien_count--;
                  UpdateScore(10);
                  return true;
               }
            }
         }
         return false;
      }

      private void AlienHitPersonCheck()
      {
         for (int r = 0; r < NUMBER_OF_ALIEN_ROWS; r++) //person hit  by alien check
         {
            for (int c = 0; c < NUMBER_OF_ALIENS_PER_ROW; c++)
            {
               if ((alienGroup[r, c].beenHit == false) && alienGroup[r, c].GetBounds().IntersectsWith(player.GetBounds()))
               {
                  //hit
                  alienGroup[r, c].beenHit = true;
                  player.kill();
                  gameover = true;
                  gameForm.WriteScore(data.getScore());
               }
            }
         }
      }

      private void AlienBulletsCheck()
      {
         for (int i = 0; i < alienbullets.Count; i++)
         {
            if (player.GetBounds().IntersectsWith(alienbullets[i].GetBounds()))
            {
               if (!player.hitAndIsAlive())
               {
                  player.kill();
                  gameover = true;
                  gameForm.WriteScore(data.getScore());
               }
               gameForm.setLivesLabel(player.getLifes().ToString());
               alienbullets.RemoveAt(i);
               gameForm.UpdateAlienBullets(alienbullets);
            }
         }
      }

      /**
         * Check if shield is hit by alien, remove shield  (DESTROYED HOUSE)
         */
      private void checkShieldHitByAlien()
      {
         for (int i = 0; i < Shields.Count; i++)
         {
            if (checkShieldHit(Shields[i]))
            {
               Shields.RemoveAt(i);
               gameForm.RemoveShield(i);
            }
         }
      }

      /**
         * Checks if shield is hit by alien, sheild destroyed
         */
      private bool checkShieldHit(Shield s)
      {
         for (int r = 0; r < NUMBER_OF_ALIEN_ROWS; r++)
         {
            for (int c = 0; c < NUMBER_OF_ALIENS_PER_ROW; c++)
            {
               if ((alienGroup[r, c].beenHit == false) && alienGroup[r, c].GetBounds().IntersectsWith(s.GetBounds()) && s.getHealth() > 0)
               {
                  return true;
               }
            }
         }
         return false;
      }


      public Alien[,] AnimateAliens(int timerCounter)
      {
         if (timerCounter % 6 == 0)
         {
            for (int i = 0; i < NUMBER_OF_ALIEN_ROWS; i++)
            {
               for (int j = 0; j < NUMBER_OF_ALIENS_PER_ROW; j++)
               {
                  alienGroup[i, j].MoveInPlace();
               }
            }
         }
         return alienGroup;
      }
      public bool CheckForLanding()
      {
         for (int r = 0; r < NUMBER_OF_ALIEN_ROWS; r++)
         {
            for (int c = 0; c < NUMBER_OF_ALIENS_PER_ROW; c++)
            {
               if ((alienGroup[r, c].beenHit == false) && alienGroup[r, c].GetBounds().Bottom >= ClientRectangle.Bottom)
               {
                  alienGroup[r, c].beenHit = true;
                  return true;
               }
            }
         }
         return false;
      }
   }
}
   
