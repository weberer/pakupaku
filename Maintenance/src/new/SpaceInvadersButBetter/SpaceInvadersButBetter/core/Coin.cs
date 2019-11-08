using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpaceInvadersButBetter.core
{
   /**
     * Class for the coin logic to allow for the drawing, movement and registering of credits within the main machine
     */
   public class Coin
   {
      private Image[] view = new Image[3];
      private int coinX = 0;
      private int coinY = 0;
      private bool slot1error;
      private bool slot2error;
      private int cointype = 0;
      private bool hasCoin = false;
      private Random rand = new Random(System.DateTime.Now.Second);
      /**
         * Constructor
         */
      public Coin()
      {
         view[0] = (Image)Resources._25Cent;
         view[1] = (Image)Resources._10Cent;
         view[2] = (Image)Resources.CoinPile;
      }

      /**
       * Checks if the location sent into the function is within the area attributed to the coin pile
       */
      public bool checkPileClicked(int xpos, int ypos)
      {
         if (xpos > 75 && xpos < 225 && ypos > 440 && ypos < 590)
         {
            return true;
         }
         return false;
      }
      /**
       * Checks if the location sent into the function is within the area attributed to one of the coin slots,
       * returning the index of the coin slot clicked
       */
      public int checkSlotClicked(int xpos, int ypos)
      {
         if (xpos > 484 && xpos < 507 && ypos > 649 && ypos < 668)
         {
            return 1;
         }
         else if (xpos > 501 && xpos < 532 && ypos > 649 && ypos < 668)
         {
            return 2;
         }
            return -1;
      }
      /**
 * Checks if the location sent into the function is within the area attributed to one of the coin return slot,
 * returning the index of the coin slot clicked
 */
      public int checkGivebackClicked(int xpos, int ypos)
      {
         if (xpos > 487 && xpos < 500 && ypos > 675 && ypos < 685)
         {
            return 1;
         }
         else if (xpos > 514 && xpos < 528 && ypos > 675 && ypos < 685)
         {
            return 2;
         }
         return -1;
      }
      /**
       * Game logic to give the coin that is in the slot given back to the player
       */
      public void takeReturned(int slot)
      {
         if (slot == 1)
            slot1error = false;
         if (slot == 2)
            slot2error = false;
         hasCoin = true;
         cointype = 1;
      }
      /**
       * Return if the currently held coin is a quarter
       */
      public bool isQuarter()
      {
         return cointype == 0;
      }
      public void createCoin()
      {
         hasCoin = true;

         if (rand.Next(0, 100) < 10)
            cointype = 1;
         else
            cointype = 0;
      }
      /**
       * Game logic to return the coin if an incorrect one is inserted
       */
      public void badCoinInserted(int slot)
      {
         if (slot == 1)
            slot1error = true;
         if (slot == 2)
            slot2error = true;
         this.deleteCoin();
      }
      /**
       * Game logic to remove a coin from the players hand
       */
      public void deleteCoin()
      {
         hasCoin = false;
      }
      /**
       * Return if the player is currently holding a coin
       */
      public bool checkCoinHeld()
      {
         return hasCoin;
      }
      /**
       * Update the location of the held coin on the screen based on the input coordinates
       */
      public void updateCoinLocation(int xpos, int ypos)
      {
         coinX = xpos;
         coinY = ypos;
      }
      /**
       * Draw coins as well as the coin pile in the correct locations
       */
      public void draw(Graphics g)
      {
         g.DrawImage(view[2], new RectangleF(new PointF(75, 440), new SizeF(150, 150)));
         if (hasCoin)
         {
            g.DrawImage(view[cointype], new RectangleF(new PointF(coinX - 25, coinY - 25), new SizeF(50, 50)));
         }

         if (slot1error)
         {
            g.DrawImage(view[1], new RectangleF(new PointF(487 - 4, 670), new SizeF(25, 25)));
         }
         if (slot2error)
         {
            g.DrawImage(view[1], new RectangleF(new PointF(514 - 4, 670), new SizeF(25, 25)));
         }

      }
   }
}
