using SpaceInvadersButBetter.core;
using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace SpaceInvadersButBetter
{
    public partial class GameForm : Form
    {
        private const int NUMBER_OF_SHIELDS = 4;

        private Shield[] Shields = new Shield[4];

        public GameForm()
        {
            InitializeComponent();

            SetStyle(ControlStyles.UserPaint, true);
            SetStyle(ControlStyles.AllPaintingInWmPaint, true);

            //performance buff
            SetStyle(ControlStyles.OptimizedDoubleBuffer, true);

            InitializeGameObjects();
        }


        private void InitializeGameObjects()
        {
            InitializeObject_Shields();
        }

        private void InitializeObject_Shields()
        {
            for(int i = 0; i < NUMBER_OF_SHIELDS; i++)
            {
                Shields[i] = new Shield();
                Shields[i].Position.X = (Shields[i].GetBounds().Width + 30 + (i*135));
                Shields[i].Position.Y = ClientRectangle.Bottom - (Shields[i].GetBounds().Height + 100);
            }
        }

        private void Form1_Paint(object sender, PaintEventArgs e)
        {
            Graphics g = e.Graphics;

            for (int i = 0; i < NUMBER_OF_SHIELDS; i++)
            {
                Shields[i].Draw(g);
            }
        }

        private void Form1_MouseClick(object sender, MouseEventArgs e)
        {
            Console.WriteLine("Pressed");
        }

        private void Form1_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == 'a' || e.KeyChar == 'A')
                Console.WriteLine("Left");
            else if (e.KeyChar == 'd' || e.KeyChar == 'D')
                Console.WriteLine("Right");
            else if (e.KeyChar == ' ')
                Console.WriteLine("Shoot");
        }


    }
}
