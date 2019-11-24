namespace SpaceInvadersButBetter.views
{
    partial class HighScoreForm
    {
        /// <summary> 
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary> 
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.lblHitSpace = new System.Windows.Forms.Label();
            this.InsertCoinLabel = new System.Windows.Forms.Label();
            this.lblHeader = new System.Windows.Forms.Label();
            this.lbl1 = new System.Windows.Forms.Label();
            this.lbl2 = new System.Windows.Forms.Label();
            this.lbl3 = new System.Windows.Forms.Label();
            this.lbl4 = new System.Windows.Forms.Label();
            this.lbl5 = new System.Windows.Forms.Label();
            this.RevealTimer = new System.Windows.Forms.Timer(this.components);
            this.ReturnTimer = new System.Windows.Forms.Timer(this.components);
            this.CreditFlashTimer = new System.Windows.Forms.Timer(this.components);
            this.SuspendLayout();
            // 
            // lblHitSpace
            // 
            this.lblHitSpace.AutoSize = true;
            this.lblHitSpace.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblHitSpace.ForeColor = System.Drawing.Color.Yellow;
            this.lblHitSpace.Location = new System.Drawing.Point(172, 362);
            this.lblHitSpace.Name = "lblHitSpace";
            this.lblHitSpace.Size = new System.Drawing.Size(189, 31);
            this.lblHitSpace.TabIndex = 20;
            this.lblHitSpace.Text = "Hit Space Bar!";
            this.lblHitSpace.Visible = false;
            // 
            // InsertCoinLabel
            // 
            this.InsertCoinLabel.AutoSize = true;
            this.InsertCoinLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 20F);
            this.InsertCoinLabel.ForeColor = System.Drawing.Color.White;
            this.InsertCoinLabel.Location = new System.Drawing.Point(192, 331);
            this.InsertCoinLabel.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.InsertCoinLabel.Name = "InsertCoinLabel";
            this.InsertCoinLabel.Size = new System.Drawing.Size(146, 31);
            this.InsertCoinLabel.TabIndex = 19;
            this.InsertCoinLabel.Text = "Insert Coin";
            this.InsertCoinLabel.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            // 
            // lblHeader
            // 
            this.lblHeader.AutoSize = true;
            this.lblHeader.Font = new System.Drawing.Font("Microsoft Sans Serif", 27.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblHeader.ForeColor = System.Drawing.Color.White;
            this.lblHeader.Location = new System.Drawing.Point(159, 0);
            this.lblHeader.Name = "lblHeader";
            this.lblHeader.Size = new System.Drawing.Size(222, 42);
            this.lblHeader.TabIndex = 21;
            this.lblHeader.Text = "High Scores";
            // 
            // lbl1
            // 
            this.lbl1.AutoSize = true;
            this.lbl1.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbl1.ForeColor = System.Drawing.Color.White;
            this.lbl1.Location = new System.Drawing.Point(31, 64);
            this.lbl1.Name = "lbl1";
            this.lbl1.Size = new System.Drawing.Size(37, 31);
            this.lbl1.TabIndex = 22;
            this.lbl1.Text = "1:";
            this.lbl1.Visible = false;
            // 
            // lbl2
            // 
            this.lbl2.AutoSize = true;
            this.lbl2.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbl2.ForeColor = System.Drawing.Color.White;
            this.lbl2.Location = new System.Drawing.Point(31, 119);
            this.lbl2.Name = "lbl2";
            this.lbl2.Size = new System.Drawing.Size(37, 31);
            this.lbl2.TabIndex = 23;
            this.lbl2.Text = "2:";
            this.lbl2.Visible = false;
            // 
            // lbl3
            // 
            this.lbl3.AutoSize = true;
            this.lbl3.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbl3.ForeColor = System.Drawing.Color.White;
            this.lbl3.Location = new System.Drawing.Point(31, 174);
            this.lbl3.Name = "lbl3";
            this.lbl3.Size = new System.Drawing.Size(37, 31);
            this.lbl3.TabIndex = 24;
            this.lbl3.Text = "3:";
            this.lbl3.Visible = false;
            // 
            // lbl4
            // 
            this.lbl4.AutoSize = true;
            this.lbl4.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbl4.ForeColor = System.Drawing.Color.White;
            this.lbl4.Location = new System.Drawing.Point(31, 229);
            this.lbl4.Name = "lbl4";
            this.lbl4.Size = new System.Drawing.Size(37, 31);
            this.lbl4.TabIndex = 25;
            this.lbl4.Text = "4:";
            this.lbl4.Visible = false;
            // 
            // lbl5
            // 
            this.lbl5.AutoSize = true;
            this.lbl5.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbl5.ForeColor = System.Drawing.Color.White;
            this.lbl5.Location = new System.Drawing.Point(31, 284);
            this.lbl5.Name = "lbl5";
            this.lbl5.Size = new System.Drawing.Size(37, 31);
            this.lbl5.TabIndex = 26;
            this.lbl5.Text = "5:";
            this.lbl5.Visible = false;
            // 
            // RevealTimer
            // 
            this.RevealTimer.Enabled = true;
            this.RevealTimer.Interval = 2000;
            this.RevealTimer.Tick += new System.EventHandler(this.RevealTimer_Tick);
            // 
            // ReturnTimer
            // 
            this.ReturnTimer.Interval = 18000;
            this.ReturnTimer.Tick += new System.EventHandler(this.ReturnTimer_Tick);
            // 
            // CreditFlashTimer
            // 
            this.CreditFlashTimer.Enabled = true;
            this.CreditFlashTimer.Interval = 1000;
            this.CreditFlashTimer.Tick += new System.EventHandler(this.CreditFlashTimer_Tick);
            // 
            // HighScoreForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Black;
            this.Controls.Add(this.lbl5);
            this.Controls.Add(this.lbl4);
            this.Controls.Add(this.lbl3);
            this.Controls.Add(this.lbl2);
            this.Controls.Add(this.lbl1);
            this.Controls.Add(this.lblHeader);
            this.Controls.Add(this.lblHitSpace);
            this.Controls.Add(this.InsertCoinLabel);
            this.Name = "HighScoreForm";
            this.Size = new System.Drawing.Size(540, 410);
            this.Load += new System.EventHandler(this.HighScoreForm_Load);
            this.VisibleChanged += new System.EventHandler(this.HighScoreForm_VisibleChanged);
            this.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.HighScoreForm_KeyPress);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lblHitSpace;
        private System.Windows.Forms.Label InsertCoinLabel;
        private System.Windows.Forms.Label lblHeader;
        private System.Windows.Forms.Label lbl1;
        private System.Windows.Forms.Label lbl2;
        private System.Windows.Forms.Label lbl3;
        private System.Windows.Forms.Label lbl4;
        private System.Windows.Forms.Label lbl5;
        private System.Windows.Forms.Timer RevealTimer;
        private System.Windows.Forms.Timer ReturnTimer;
        private System.Windows.Forms.Timer CreditFlashTimer;
    }
}
