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
   public partial class InitalsForm : UserControl
   {
      public InitalsForm()
      {
         InitializeComponent();
      }

      private void InitalsForm_KeyPress(object sender, KeyPressEventArgs e)
      {
         if (txtInitials.Text.Length > 0)
            lblHitEnter.Visible = true;
         else
            lblHitEnter.Visible = false;
      }

      private void InitalsForm_VisibleChanged(object sender, EventArgs e)
      {
         if (this.Visible)
            txtInitials.Text = string.Empty;
      }
   }
}
