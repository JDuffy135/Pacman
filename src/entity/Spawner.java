package entity;

import main.GamePanel;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Spawner extends Entity
{
    GamePanel gp;
    int[][] gameboard = new int[19][22];

    public Spawner(GamePanel gp)
    {
        this.gp = gp;
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

    public void createWalls()
    {
        instantiateGameboardArray(gameboard);

        int xPos = -6, yPos = 38;
        for (int h = 0 ; h < 22 ; h++)
        {
            for (int w = 0 ; w < 19 ; w++)
            {
                if (gameboard[w][h] == 1)
                {
                    Wall wall = new Wall(gp, xPos, yPos);
                    walls.add(wall);
                }
                if (w == 0 ||w == 4 ||  w == 15 || w == 16 || w == 17) { xPos = xPos + 26; }
                else if (w == 1) { xPos = xPos + 22; }
                else if (w == 2) { xPos = xPos + 28; }
                else { xPos = xPos + 24; }
            }
            xPos = -4;
            if (h == 0) {yPos = yPos + 18; }
            else if (h == 2 || h ==10 ) { yPos = yPos + 21; }
            else if (h == 7 || h == 9 || h == 12 ) { yPos = yPos + 27; }
            else if (h == 16 || h == 18) { yPos = yPos + 25; }
            else { yPos = yPos + 23; }
        }
    }

    //FOR TESTING PURPOSES - ONLY TO VISUALIZE WALL HITBOXES
    public void paintWalls(Graphics2D g2)
    {
        instantiateGameboardArray(gameboard);

        int xPos = -6, yPos = 38;
        for (int h = 0 ; h < 22 ; h++)
        {
            for (int w = 0 ; w < 19 ; w++)
            {
                if (gameboard[w][h] == 1)
                {
                    //NOTE: replace these drawings with 10x10 Wall objects
                    g2.draw3DRect(xPos, yPos, 12, 10, false);
                }
                if (w == 0 ||w == 4 ||  w == 15 || w == 16 || w == 17) { xPos = xPos + 26; }
                else if (w == 1) { xPos = xPos + 22; }
                else if (w == 2) { xPos = xPos + 28; }
                else { xPos = xPos + 24; }
            }
            xPos = -4;
            if (h == 0) {yPos = yPos + 18; }
            else if (h == 2 || h ==10 ) { yPos = yPos + 21; }
            else if (h == 7 || h == 9 || h == 12 ) { yPos = yPos + 27; }
            else if (h == 16 || h == 18) { yPos = yPos + 25; }
            else { yPos = yPos + 23; }
        }
    }
}
