package item;

import main.GamePanel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ItemSpawner extends Item
{
    GamePanel gp;
    public static long fruitTimer = 0;

    public ItemSpawner(GamePanel gp)
    {
        this.gp = gp;
    }



    //resets 2d array "itemBoard"
    public void setupItemBoardArray(int[][] itemBoard)
    {
        try
        {
            //NOTE: change directory to be more generic
            Scanner scanner = new Scanner(new File("/Users/jakeduffy/Desktop/COMP SCI PROJECTS/PacMan From Scratch/res/textfiles/PelletMap.txt"));
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            for (int c = 0 ; c < height ; c++)
            {
                for (int r = 0 ; r < width ; r++)
                {
                    itemBoard[r][c] = scanner.nextInt();
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //creates pellet objects and spawns them in their correct position on the map
    public void createPellets()
    {
        //x-axis: starting at 16 pixels from left, move 16 pixels to the right (26 tiles wide)
        //y-axis: starting at ??? pixels from top, move 16 pixels down (29 tiles wide)

    }

    //draws pellets and fruits in every frame according to itemBoard
    public void drawItems(Graphics2D g2)
    {

    }

    //updates fruitTimer value
    public void updateFruitTimer()
    {
        fruitTimer++;

        //NOTE UPDATE LOGIC to replicate how fruits work in actual game, this is just a placeholder
        if (fruitTimer >= 9000000000l)
        {
            spawnFruit();
            fruitTimer = 0;
        }
    }

    //spawns fruit based on fruitTimer value at itemBoard[][] based on timer
    public void spawnFruit()
    {

    }
}
