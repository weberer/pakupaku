namespace SpaceInvadersButBetter
{
    partial class GameView
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
            this.lblScoreTitle = new System.Windows.Forms.Label();
            this.lblHighScoreTitle = new System.Windows.Forms.Label();
            this.lblHighScore = new System.Windows.Forms.Label();
            this.lblScore = new System.Windows.Forms.Label();
            this.SpaceInvadersLabel = new System.Windows.Forms.Label();
            this.InsertCoinLabel = new System.Windows.Forms.Label();
            this.lblLevel = new System.Windows.Forms.Label();
            this.lblLevelNumber = new System.Windows.Forms.Label();
            this.lblLifes = new System.Windows.Forms.Label();
            this.lblLifesLabel = new System.Windows.Forms.Label();
            this.lblGameOver = new System.Windows.Forms.Label();
            this.lblYourScore = new System.Windows.Forms.Label();
            this.lblEndScore = new System.Windows.Forms.Label();
            this.lblScoreScroll = new System.Windows.Forms.Label();
            this.lblHitSpace = new System.Windows.Forms.Label();
            this.CreditFlashTimer = new System.Windows.Forms.Timer(this.components);
            this.SuspendLayout();
            // 
            // lblScoreTitle
            // 
            this.lblScoreTitle.AutoSize = true;
            this.lblScoreTitle.BackColor = System.Drawing.Color.Black;
            this.lblScoreTitle.ForeColor = System.Drawing.SystemColors.Control;
            this.lblScoreTitle.Location = new System.Drawing.Point(26, 4);
            this.lblScoreTitle.Name = "lblScoreTitle";
            this.lblScoreTitle.Size = new System.Drawing.Size(44, 13);
            this.lblScoreTitle.TabIndex = 0;
            this.lblScoreTitle.Text = "SCORE";
            // 
            // lblHighScoreTitle
            // 
            this.lblHighScoreTitle.AutoSize = true;
            this.lblHighScoreTitle.ForeColor = System.Drawing.SystemColors.Control;
            this.lblHighScoreTitle.Location = new System.Drawing.Point(462, 4);
            this.lblHighScoreTitle.Name = "lblHighScoreTitle";
            this.lblHighScoreTitle.Size = new System.Drawing.Size(58, 13);
            this.lblHighScoreTitle.TabIndex = 2;
            this.lblHighScoreTitle.Text = "HI-SCORE";
            // 
            // lblHighScore
            // 
            this.lblHighScore.ForeColor = System.Drawing.SystemColors.Control;
            this.lblHighScore.Location = new System.Drawing.Point(448, 21);
            this.lblHighScore.Name = "lblHighScore";
            this.lblHighScore.Size = new System.Drawing.Size(89, 13);
            this.lblHighScore.TabIndex = 3;
            this.lblHighScore.Text = "10";
            this.lblHighScore.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lblScore
            // 
            this.lblScore.ForeColor = System.Drawing.SystemColors.Control;
            this.lblScore.Location = new System.Drawing.Point(3, 21);
            this.lblScore.Name = "lblScore";
            this.lblScore.Size = new System.Drawing.Size(89, 13);
            this.lblScore.TabIndex = 4;
            this.lblScore.Text = "10";
            this.lblScore.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // SpaceInvadersLabel
            // 
            this.SpaceInvadersLabel.AutoSize = true;
            this.SpaceInvadersLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 58F);
            this.SpaceInvadersLabel.ForeColor = System.Drawing.Color.White;
            this.SpaceInvadersLabel.Location = new System.Drawing.Point(-15, 43);
            this.SpaceInvadersLabel.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.SpaceInvadersLabel.Name = "SpaceInvadersLabel";
            this.SpaceInvadersLabel.Size = new System.Drawing.Size(598, 178);
            this.SpaceInvadersLabel.TabIndex = 6;
            this.SpaceInvadersLabel.Text = "Space Invaders \r\nBut Better";
            this.SpaceInvadersLabel.TextAlign = System.Drawing.ContentAlignment.TopCenter;
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
            this.InsertCoinLabel.TabIndex = 7;
            this.InsertCoinLabel.Text = "Insert Coin";
            this.InsertCoinLabel.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            // 
            // lblLevel
            // 
            this.lblLevel.AutoSize = true;
            this.lblLevel.ForeColor = System.Drawing.SystemColors.Control;
            this.lblLevel.Location = new System.Drawing.Point(174, 4);
            this.lblLevel.Name = "lblLevel";
            this.lblLevel.Size = new System.Drawing.Size(40, 13);
            this.lblLevel.TabIndex = 9;
            this.lblLevel.Text = "LEVEL";
            // 
            // lblLevelNumber
            // 
            this.lblLevelNumber.ForeColor = System.Drawing.SystemColors.Control;
            this.lblLevelNumber.Location = new System.Drawing.Point(148, 21);
            this.lblLevelNumber.Name = "lblLevelNumber";
            this.lblLevelNumber.Size = new System.Drawing.Size(89, 13);
            this.lblLevelNumber.TabIndex = 11;
            this.lblLevelNumber.Text = "10";
            this.lblLevelNumber.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lblLifes
            // 
            this.lblLifes.ForeColor = System.Drawing.SystemColors.Control;
            this.lblLifes.Location = new System.Drawing.Point(308, 21);
            this.lblLifes.Name = "lblLifes";
            this.lblLifes.Size = new System.Drawing.Size(89, 13);
            this.lblLifes.TabIndex = 12;
            this.lblLifes.Text = "10";
            this.lblLifes.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lblLifesLabel
            // 
            this.lblLifesLabel.AutoSize = true;
            this.lblLifesLabel.ForeColor = System.Drawing.SystemColors.Control;
            this.lblLifesLabel.Location = new System.Drawing.Point(335, 6);
            this.lblLifesLabel.Name = "lblLifesLabel";
            this.lblLifesLabel.Size = new System.Drawing.Size(36, 13);
            this.lblLifesLabel.TabIndex = 13;
            this.lblLifesLabel.Text = "LIFES";
            // 
            // lblGameOver
            // 
            this.lblGameOver.BackColor = System.Drawing.Color.Black;
            this.lblGameOver.Font = new System.Drawing.Font("Microsoft Sans Serif", 58F);
            this.lblGameOver.ForeColor = System.Drawing.Color.Red;
            this.lblGameOver.Location = new System.Drawing.Point(-24, 43);
            this.lblGameOver.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.lblGameOver.Name = "lblGameOver";
            this.lblGameOver.Size = new System.Drawing.Size(598, 178);
            this.lblGameOver.TabIndex = 14;
            this.lblGameOver.Text = "GAME OVER";
            this.lblGameOver.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            // 
            // lblYourScore
            // 
            this.lblYourScore.AutoSize = true;
            this.lblYourScore.BackColor = System.Drawing.Color.Black;
            this.lblYourScore.Font = new System.Drawing.Font("Microsoft Sans Serif", 20F);
            this.lblYourScore.ForeColor = System.Drawing.Color.Red;
            this.lblYourScore.Location = new System.Drawing.Point(164, 132);
            this.lblYourScore.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.lblYourScore.Name = "lblYourScore";
            this.lblYourScore.Size = new System.Drawing.Size(197, 31);
            this.lblYourScore.TabIndex = 15;
            this.lblYourScore.Text = "YOUR SCORE";
            this.lblYourScore.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            // 
            // lblEndScore
            // 
            this.lblEndScore.BackColor = System.Drawing.Color.Black;
            this.lblEndScore.Font = new System.Drawing.Font("Microsoft Sans Serif", 20F);
            this.lblEndScore.ForeColor = System.Drawing.Color.Red;
            this.lblEndScore.Location = new System.Drawing.Point(80, 173);
            this.lblEndScore.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.lblEndScore.Name = "lblEndScore";
            this.lblEndScore.Size = new System.Drawing.Size(372, 31);
            this.lblEndScore.TabIndex = 16;
            this.lblEndScore.Text = "100";
            this.lblEndScore.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            // 
            // lblScoreScroll
            // 
            this.lblScoreScroll.ForeColor = System.Drawing.SystemColors.Control;
            this.lblScoreScroll.Location = new System.Drawing.Point(3, 21);
            this.lblScoreScroll.Name = "lblScoreScroll";
            this.lblScoreScroll.Size = new System.Drawing.Size(89, 13);
            this.lblScoreScroll.TabIndex = 17;
            this.lblScoreScroll.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lblHitSpace
            // 
            this.lblHitSpace.AutoSize = true;
            this.lblHitSpace.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblHitSpace.ForeColor = System.Drawing.Color.Yellow;
            this.lblHitSpace.Location = new System.Drawing.Point(172, 362);
            this.lblHitSpace.Name = "lblHitSpace";
            this.lblHitSpace.Size = new System.Drawing.Size(189, 31);
            this.lblHitSpace.TabIndex = 18;
            this.lblHitSpace.Text = "Hit Space Bar!";
            this.lblHitSpace.Visible = false;
            // 
            // CreditFlashTimer
            // 
            this.CreditFlashTimer.Enabled = true;
            this.CreditFlashTimer.Interval = 1000;
            this.CreditFlashTimer.Tick += new System.EventHandler(this.CreditFlashTimer_Tick);
            // 
            // GameView
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.Black;
            this.Controls.Add(this.lblHitSpace);
            this.Controls.Add(this.lblScoreScroll);
            this.Controls.Add(this.lblEndScore);
            this.Controls.Add(this.lblYourScore);
            this.Controls.Add(this.lblLifesLabel);
            this.Controls.Add(this.lblLifes);
            this.Controls.Add(this.lblLevelNumber);
            this.Controls.Add(this.lblLevel);
            this.Controls.Add(this.InsertCoinLabel);
            this.Controls.Add(this.SpaceInvadersLabel);
            this.Controls.Add(this.lblScore);
            this.Controls.Add(this.lblHighScore);
            this.Controls.Add(this.lblHighScoreTitle);
            this.Controls.Add(this.lblScoreTitle);
            this.Controls.Add(this.lblGameOver);
            this.Name = "GameView";
            this.Size = new System.Drawing.Size(540, 410);
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.Form1_Paint);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.GameControlObject_KeyDown);
            this.KeyUp += new System.Windows.Forms.KeyEventHandler(this.GameControlObject_KeyUp);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lblScoreTitle;
        private System.Windows.Forms.Label lblHighScoreTitle;
        private System.Windows.Forms.Label lblHighScore;
        private System.Windows.Forms.Label lblScore;
        private System.Windows.Forms.Label SpaceInvadersLabel;
        private System.Windows.Forms.Label InsertCoinLabel;
        private System.Windows.Forms.Label lblLevel;
        private System.Windows.Forms.Label lblLevelNumber;
        private System.Windows.Forms.Label lblLifes;
        private System.Windows.Forms.Label lblLifesLabel;
        private System.Windows.Forms.Label lblGameOver;
        private System.Windows.Forms.Label lblYourScore;
        private System.Windows.Forms.Label lblEndScore;
        private System.Windows.Forms.Label lblScoreScroll;
        private System.Windows.Forms.Label lblHitSpace;
        private System.Windows.Forms.Timer CreditFlashTimer;
    }
}
