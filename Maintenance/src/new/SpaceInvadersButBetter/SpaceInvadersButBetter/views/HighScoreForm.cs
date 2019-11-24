using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace SpaceInvadersButBetter.views
{
    public partial class HighScoreForm : UserControl
    {
        private const int MAX_TICKS = 5;
        private int timerCount = 0;
        private GameBoxForm boxForm;
        private Boolean creditFlash = false;
        private int credits = 0;

        public HighScoreForm(GameBoxForm view)
        {
            InitializeComponent();
            this.boxForm = view;
        }

        private void HighScoreForm_Load(object sender, EventArgs e)
        {

        }

        private void HighScoreForm_VisibleChanged(object sender, EventArgs e)
        {
            if (this.Visible == true)
            {
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
        }

        private void RevealSecondScore()
        {
            lbl2.Visible = true;
        }

        private void RevealThirdScore()
        {
            lbl3.Visible = true;
        }

        private void RevealFourthScore()
        {
            lbl4.Visible = true;
        }

        private void RevealFifthScore()
        {
            lbl5.Visible = true;
        }
        private void ReturnTimer_Tick(object sender, EventArgs e)
        {
            HideScores();
            ReturnTimer.Stop();
            CreditFlashTimer.Stop();
            this.Visible = false;
            boxForm.SwitchForms();
        }
        private void HideScores()
        {
            lbl1.Visible = false;
            lbl2.Visible = false;
            lbl3.Visible = false;
            lbl4.Visible = false;
            lbl5.Visible = false;
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
                    boxForm.BeginNewGame();
                }
            }
        }
    }
}
