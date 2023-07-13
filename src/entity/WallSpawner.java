package entity;

import main.GamePanel;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class WallSpawner extends Entity
{
    GamePanel gp;
    int[][] gameboard = new int[19][22];

    public WallSpawner(GamePanel gp)
    {
        this.gp = gp;
    }

    //creates a 2D array to figure out where walls need to be placed on the map
    public void instantiateGameboardArray(int[][] gameboard)
    {
        try
        {
            //NOTE: change directory to be more generic
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

    //sets up the walls for collisions
    //code is quite ugly here but it works so shush
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
            else if (h == 8) { yPos = yPos + 26; }
            else if (h != 12 && h != 20) { yPos = yPos + 24; }
            else { yPos = yPos + 22; }
        }
        Wall teleportBarrier1 = new Wall(gp, -28, 240);
        walls.add(teleportBarrier1);
        Wall teleportBarrier2 = new Wall(gp, 464, 240);
        walls.add(teleportBarrier2);
        Wall teleportBarrier3 = new Wall(gp, -28, 290);
        walls.add(teleportBarrier3);
        Wall teleportBarrier4 = new Wall(gp, 464, 290);
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
            else if (h == 8) { yPos = yPos + 26; }
            else if (h != 12 && h != 20) { yPos = yPos + 24; }
            else { yPos = yPos + 22; }
        }
        //Walls that are offscreen
//        g2.draw3DRect(-24, 240, 14, 14, false);
//        g2.draw3DRect(462, 240, 14, 14, false);
//        g2.draw3DRect(-24, 288, 14, 14, false);
//        g2.draw3DRect(462, 288, 14, 14, false);
    }
}