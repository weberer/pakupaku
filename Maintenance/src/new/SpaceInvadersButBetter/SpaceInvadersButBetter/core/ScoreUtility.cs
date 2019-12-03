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
        int[] top10;

        /**
         * Constructor, reads in right away
         */
        public ScoreUtility()
        {
            top10 = new int[10];
            Read();
        }

        /**
         * Returns top high score
         */
        public int getTopScore()
        {
            return top10[0];
        }

        /**
         * Returns score at given index
         */
        public int getScoreAt(int index)
        {
            return top10[index];
        }

        /**
         * Writes score to file if it is a new high score in the list
         */
        public void Write(int Score)
        {
            Read();
            for(int i = 0; i < top10.Length; i++)
            {
                if(top10[i] < Score)
                {
                    int[] newList = new int[top10.Length];
                    AddScoreAtIndex(i, Score);
                    break;
                }
            }
            StreamWriter sw = new StreamWriter("highscore.txt", false);
            for (int i = 0; i < top10.Length; i++)
            {
                sw.WriteLine(top10[i].ToString());
            }
            sw.Close();
        }

        /**
         * Adds score at index in sorted array, shifting others down
         */
        private void AddScoreAtIndex(int index, int score)
        {
            int[] newList = new int[top10.Length];
            int save = 0;
            for(int i = 0; i < top10.Length;  i++)
            {
                if(i < index)
                {
                    newList[i] = top10[i];
                }
                else if(i == index)
                {
                    save = top10[i];
                    newList[i] = score;
                }
                else
                {
                    newList[i] = save;
                    save = top10[i];
                }
            }
            top10 = newList;
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
                    int score = Convert.ToInt32(line);
                    top10[count] = score;
                    count++;
                    line = sr.ReadLine();
                }
                sr.Close();
                Array.Sort(top10);
                Array.Reverse(top10);
            }
        }
    }
}
