using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpaceInvadersButBetter.core
{
    /**
     * Used to read and write to persistent memory (text file)
     * Holds array of top10 for sorting and display
     */
    public class ScoreUtility
    {
        int[] top5;
        string[] initials;

        /**
         * Constructor, reads in right away
         */
        public ScoreUtility()
        {
            top5 = new int[5];
            initials = new string[5];
            Read();
        }

        /**
         * Returns top high score
         */
        public int getTopScore()
        {
            return top5[0];
        }

        /**
         * Returns score at given index
         */
        public int getScoreAt(int index)
        {
            return top5[index];
        }

        public int[] GetScores()
        {
            return top5;
        }

        public string[] GetInitials()
        {
            return initials;
        }

        /**
         * Writes score to file if it is a new high score in the list
         */
        public void Write(int Score, string initial)
        {
            Read();
            for(int i = 0; i < top5.Length; i++)
            {
                if(top5[i] < Score)
                {
                    int[] newList = new int[top5.Length];
                    AddScoreAtIndex(i, Score, initial);
                    break;
                }
            }
            StreamWriter sw = new StreamWriter("highscore.txt", false);
            for (int i = 0; i < top5.Length; i++)
            {
                sw.WriteLine(initials[i]);
                sw.WriteLine(top5[i].ToString());

            }
            sw.Close();
        }

        /**
         * Adds score at index in sorted array, shifting others down
         */
        private void AddScoreAtIndex(int index, int score, string initial)
        {
            int[] newScores = new int[top5.Length];
            string[] newInitials = new string[initials.Length];
            int saveScore = 0;
            string saveInitial = "";
            for(int i = 0; i < top5.Length;  i++)
            {
                if(i < index)
                {
                    newInitials[i] = initials[i];
                    newScores[i] = top5[i];
                }
                else if(i == index)
                {
                    saveInitial = initials[i];
                    newInitials[i] = initial;

                    saveScore = top5[i];
                    newScores[i] = score;
                }
                else
                {
                    newInitials[i] = saveInitial;
                    saveInitial = initials[i];

                    newScores[i] = saveScore;
                    saveScore = top5[i];
                }
            }
            top5 = newScores;
            initials = newInitials;
        }

        /**
         * Reads in each score from the file and saves it to the array
         * Sorts it and then reverses array to give highest score at index 0
         */
        public void Read()
        {
            if(File.Exists("highscore.txt"))
            {
                int count = 0;
                String line;
                StreamReader sr = new StreamReader("highscore.txt");
                line = sr.ReadLine();
                while (line != null)
                {
                    string initial = Convert.ToString(line);
                    initials[count] = initial;
                    line = sr.ReadLine();
                    int score = Convert.ToInt32(line);
                    top5[count] = score;
                    count++;
                    line = sr.ReadLine();
                }
                sr.Close();
                //Array.Sort(top5);
                //Array.Reverse(top5);
            }
        }

        public bool IsHighScore(int score)
        {
            for(int i = 0; i < top5.Length; i++)
            {
                if(score > top5[i])
                {
                    return true;
                }
            }

            return false;
        }

        public int DeterminePosition(int score)
        {
            for(int i = 0; i < top5.Length; i++)
            {
                if(score > top5[i])
                {
                    return i + 1;
                }
            }
            return -1;
        }
    }
}
