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

        int xPos = -6, yPos = 33;
        for (int h = 0 ; h < 22 ; h++)
        {
            for (int w = 0 ; w < 19 ; w++)
            {
                if (gameboard[w][h] == 1)
                {
                    Wall wall = new Wall(gp, xPos, yPos);
                    walls.add(wall);
                }
                if (w == 1 || w == 16) { xPos = xPos + 17; }
                else if (w == 0) { xPos = xPos + 30; }
                else if (w == 15 || w == 17) { xPos = xPos + 31; }
                else if (w == 2) { xPos = xPos + 32; }
                else { xPos = xPos + 24; }
            }
            xPos = -6;
            if (h == 2) { yPos = yPos + 15; }
            else { yPos = yPos + 24; }
        }
        Wall teleportBarrier1 = new Wall(gp, -28, 240);
        walls.add(teleportBarrier1);
        Wall teleportBarrier2 = new Wall(gp, 464, 240);
        walls.add(teleportBarrier2);
        Wall teleportBarrier3 = new Wall(gp, -28, 288);
        walls.add(teleportBarrier3);
        Wall teleportBarrier4 = new Wall(gp, 464, 288);
        walls.add(teleportBarrier4);
    }

    //FOR TESTING PURPOSES - ONLY TO VISUALIZE WALL HITBOXES
    public void paintWalls(Graphics2D g2)
    {
        instantiateGameboardArray(gameboard);

        int xPos = -6, yPos = 33;
        for (int h = 0 ; h < 22 ; h++)
        {
            for (int w = 0 ; w < 19 ; w++)
            {
                if (gameboard[w][h] == 1)
                {
                    //NOTE: replace these drawings with 10x10 Wall objects
                    g2.draw3DRect(xPos, yPos, 14, 14, false);
                }
                if (w == 1 || w == 16) { xPos = xPos + 17; }
                else if (w == 0) { xPos = xPos + 30; }
                else if (w == 15 || w == 17) { xPos = xPos + 31; }
                else if (w == 2) { xPos = xPos + 32; }
                else { xPos = xPos + 24; }
            }
            xPos = -6;
            if (h == 2) { yPos = yPos + 15; }
            else { yPos = yPos + 24; }
        }
        //Walls that are offscreen
//        g2.draw3DRect(-24, 240, 14, 14, false);
//        g2.draw3DRect(462, 240, 14, 14, false);
//        g2.draw3DRect(-24, 288, 14, 14, false);
//        g2.draw3DRect(462, 288, 14, 14, false);
    }
}
