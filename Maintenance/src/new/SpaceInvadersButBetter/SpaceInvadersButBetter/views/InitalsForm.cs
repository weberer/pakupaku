using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using SpaceInvadersButBetter.Controller;

namespace SpaceInvadersButBetter.views
{
    public partial class InitalsForm : UserControl
    {
        GameLogic logic;
        private int initialSize = 0;
        public InitalsForm(GameLogic logic)
        {
            InitializeComponent();
            this.logic = logic;
            logic.SetInitialsForm(this);
        }

        private void InitalsForm_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar.Equals((char)Keys.Enter))
            {
                if (initialSize > 0)
                {
                    string initials = txtInitials.Text;
                    txtInitials.Text = "";
                    logic.ExitInitialsForm(initials);

                }
            }
            else if (e.KeyChar.Equals((char)Keys.Back))
            {
                if (initialSize > 0)
                {
                    string text = txtInitials.Text;
                    string newInitial = "";
                    for(int i = 0; i < initialSize - 1; i++)
                    {
                        newInitial += text[i];
                    }
                    txtInitials.Text = newInitial;
                    initialSize--;
                    if (txtInitials.Text.Length > 0)
                        lblHitEnter.Visible = true;
                    else
                        lblHitEnter.Visible = false;
                }
                
            }
            else if(Char.IsLetter(e.KeyChar))
            {
                if (initialSize < 3)
                {
                    KeysConverter kc = new KeysConverter();
                    string text = txtInitials.Text;
                    text += kc.ConvertToString(e.KeyChar);
                    text = text.ToUpper();
                    txtInitials.Text = text;
                    lblHitEnter.Visible = true;
                    initialSize++;
                }

            }
        }

        private void InitalsForm_VisibleChanged(object sender, EventArgs e)
        {
            if (this.Visible)
            {
                txtInitials.Text = string.Empty;
                initialSize = 0;
            }
        }

        public void SetupElements(string score, string place)
        {
            lblScore.Text = score;
            lblPlace.Text = place;
        }

    }
}
