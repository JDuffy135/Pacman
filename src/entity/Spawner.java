package entity;

import main.GamePanel;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Spawner extends Entity
{
    GamePanel gp;
    int[][] gameboard = new int[19][22];

    //wall size: 20x24 pixels
    //iterate 23.578 pixels to the right, and 26.182 pixels down when placing the walls

    public Spawner(GamePanel gp)
    {
        this.gp = gp;
        //when game begins, there will be a method that iterates through the Wallmap.txt file
        //and creates a wall of a specified size at each position where there is a 1 in the 2D array

        //Rectangle wall = new Rectangle();
    }

    public void instantiateGameboardArray(int[][] gameboard)
    {
        try
        {
            Scanner scanner = new Scanner(new File("/Users/jakeduffy/Desktop/COMP SCI PROJECTS/PacMan From Scratch/res/textfiles/Wallmap.txt"));
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            for (int c = 0 ; c < height ; c++)
            {
                for (int r = 0 ; r < width ; r++)
                {
                    gameboard[r][c] = scanner.nextInt();
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void createWalls(Graphics2D g2)
    {
        instantiateGameboardArray(gameboard);

        int xpos = -6, ypos = 38;
        for (int h = 0 ; h < 22 ; h++)
        {
            for (int w = 0 ; w < 19 ; w++)
            {
                if (gameboard[w][h] == 1)
                {
                    //NOTE: replace these drawings with 10x10 Wall objects
                    g2.draw3DRect(xpos, ypos, 10, 10, false);
                }
                if (w == 0 || w == 15) { xpos = xpos + 30; }
                else if (w == 1 || w == 11 || w == 16) { xpos = xpos + 20; }
                else if (w == 2 || w == 9 || w == 17) { xpos = xpos + 28; }
                else { xpos = xpos + 24; }
            }
            xpos = -4;
            if (h == 0) {ypos = ypos + 18; }
            else if (h == 2 || h ==10 ) { ypos = ypos + 21; }
            else if (h == 7 || h == 9 || h == 12 ) { ypos = ypos + 27; }
            else if (h == 16 || h == 18) { ypos = ypos + 25; }
            else { ypos = ypos + 23; }
        }
    }
}
