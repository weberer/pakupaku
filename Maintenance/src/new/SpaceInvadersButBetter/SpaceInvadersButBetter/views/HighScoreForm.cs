using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using SpaceInvadersButBetter.core;
using SpaceInvadersButBetter.Controller;

namespace SpaceInvadersButBetter.views
{
    public partial class HighScoreForm : UserControl
    {
        private const int MAX_TICKS = 5;
        private int timerCount = 0;
        private ScoreUtility score;
        private GameLogic logic;
        private Boolean creditFlash = false;
        private int credits = 0;
        

        public HighScoreForm(GameLogic logic)
        {
            InitializeComponent();
            this.logic = logic;
            logic.SetHighScoreView(this);
        }

        private void HighScoreForm_Load(object sender, EventArgs e)
        {

        }

        private void AddScoreUtility(ScoreUtility score)
        {
            this.score = score;
        }

        private void HighScoreForm_VisibleChanged(object sender, EventArgs e)
        {
            if (this.Visible == true)
            {

                int[] scores = score.GetScores();
                string[] initials = score.GetInitials();
                lblIni1.Text = initials[0];
                lblIni2.Text = initials[1];
                lblIni3.Text = initials[2];
                lblIni4.Text = initials[3];
                lblIni5.Text = initials[4];

                lblScore1.Text = scores[0].ToString();
                lblScore2.Text = scores[1].ToString();
                lblScore3.Text = scores[2].ToString();
                lblScore4.Text = scores[3].ToString();
                lblScore5.Text = scores[4].ToString();

                timerCount = 0;
                RevealTimer.Start();
                ReturnTimer.Stop();
                CreditFlashTimer.Start();
                this.Focus();
            }
            else
            {
                HideScores();
                RevealTimer.Stop();
                ReturnTimer.Stop();
                CreditFlashTimer.Stop();
            }
        }

        private void RevealTimer_Tick(object sender, EventArgs e)
        {
            if (timerCount == 5)
            {
                RevealTimer.Stop();
                ReturnTimer.Start();
            }
            else
            { 
                if (timerCount == 0)
                    RevealFirstScore();
                else if (timerCount == 1)
                    RevealSecondScore();
                else if (timerCount == 2)
                    RevealThirdScore();
                else if (timerCount == 3)
                    RevealFourthScore();
                else if (timerCount == 4)
                    RevealFifthScore();
                timerCount++;
            }  
        }

        private void RevealFirstScore()
        {
            lbl1.Visible = true;
            lblIni1.Visible = true;
            lblScore1.Visible = true;
        }

        private void RevealSecondScore()
        {
            lbl2.Visible = true;
            lblIni2.Visible = true;
            lblScore2.Visible = true;
        }

        private void RevealThirdScore()
        {
            lbl3.Visible = true;
            lblIni3.Visible = true;
            lblScore3.Visible = true;
        }

        private void RevealFourthScore()
        {
            lbl4.Visible = true;
            lblIni4.Visible = true;
            lblScore4.Visible = true;
        }

        private void RevealFifthScore()
        {
            lbl5.Visible = true;
            lblIni5.Visible = true;
            lblScore5.Visible = true;
        }
        private void ReturnTimer_Tick(object sender, EventArgs e)
        {
            HideScores();
            ReturnTimer.Stop();
            CreditFlashTimer.Stop();
            this.Visible = false;
            logic.SwitchForms();
        }
        private void HideScores()
        {
            lbl1.Visible = false;
            lbl2.Visible = false;
            lbl3.Visible = false;
            lbl4.Visible = false;
            lbl5.Visible = false;

            lblIni1.Visible = false;
            lblIni2.Visible = false;
            lblIni3.Visible = false;
            lblIni4.Visible = false;
            lblIni5.Visible = false;

         lblScore1.Visible = false;
         lblScore2.Visible = false;
         lblScore3.Visible = false;
         lblScore4.Visible = false;
         lblScore5.Visible = false;

      }

        private void CreditFlashTimer_Tick(object sender, EventArgs e)
        {
            if (creditFlash && credits < 9)
            {
                InsertCoinLabel.Text = "Insert Coin";
                creditFlash = false;
            }
            else
            {
                InsertCoinLabel.Text = "Credits x " + credits.ToString();
                creditFlash = true;
            }
        }
        public void UpdateCredits(int credits)
        {
            this.credits = credits;
            InsertCoinLabel.Text = "Credits x " + credits.ToString();
            if (credits > 0)
            {
                lblHitSpace.Show();
            }
            else
                lblHitSpace.Hide();
        }

        private void HighScoreForm_KeyPress(object sender, KeyPressEventArgs e)
        {
            Keys k = (Keys)e.KeyChar;
            if(k == Keys.Space)
            {
                 if (credits > 0)
                {
                    HideScores();
                    logic.BeginNewGame();
                }
            }
        }

        public void SetScoreUtil(ScoreUtility util)
        {
            this.score = util;
        }
   }
}
