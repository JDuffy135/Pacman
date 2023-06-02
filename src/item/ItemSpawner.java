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



    /* resets 2d array "itemBoard" */
    public void setupItemBoardArray()
    {
        try
        {
            /* NOTE: change directory to be more generic */
            Scanner scanner = new Scanner(new File("/Users/jakeduffy/Desktop/COMP SCI PROJECTS/PacMan From Scratch/res/textfiles/PelletMap.txt"));
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            for (int r = 0 ; r < height ; r++)
            {
                for (int c = 0 ; c < width ; c++)
                {
                    itemBoard[r][c] = scanner.nextInt();
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /* creates pellet objects and spawns them in their correct position on the map */
    public void createPellets()
    {
        //x-axis: starting at 16 pixels from left, move 16 pixels to the right (26 tiles wide)
        //y-axis: starting at ??? pixels from top, move 16 pixels down (29 tiles wide)
        setupItemBoardArray();

        int xPos = 18;
        int yPos = 58;
        int smallPelletCount = 0;
        int bigPelletCount = 0;
        for (int r = 0 ; r < itemBoard.length ; r++)
        {
            for (int c = 0 ; c < itemBoard[r].length ; c++)
            {
                if (itemBoard[r][c] == 1)
                {
                    smallPellets[smallPelletCount] = new SmallPellet(gp, xPos, yPos, smallPelletCount);
                    smallPelletCount++;
                }
                else if (itemBoard[r][c] == 2)
                {
                    bigPellets[bigPelletCount] = new BigPellet(gp, xPos, yPos, bigPelletCount);
                    bigPelletCount++;
                }
                xPos += 16;
            }
            xPos = 18;
            yPos += 16;
        }
    }

    /* draws pellets and fruits in every frame according to itemBoard */
    public void drawItems(Graphics2D g2)
    {
        for (int i = 0 ; i < bigPellets.length ; i++)
        {
            if (bigPellets[i] != null)
            {
                g2.drawImage(bigPelletImage, bigPellets[i].x, bigPellets[i].y, null);

                /* visualizing pellet hitboxes - will delete */
                //g2.draw3DRect(bigPellets[i].hitbox.x, bigPellets[i].hitbox.y, pHitboxSize, pHitboxSize, false);
            }
        }
        for (int i = 0 ; i < smallPellets.length ; i++)
        {
            if (smallPellets[i] != null)
            {
                g2.drawImage(smallPelletImage, smallPellets[i].x, smallPellets[i].y, null);

                /* visualizing pellet hitboxes - will delete */
                //g2.draw3DRect(smallPellets[i].hitbox.x, smallPellets[i].hitbox.y, pHitboxSize, pHitboxSize, false);
            }
        }
    }

    /* updates fruitTimer value */
    public void updateFruitTimer()
    {
        fruitTimer++;

        //NOTE UPDATE LOGIC to replicate how fruits work in actual game, this is just a placeholder
        if (fruitTimer >= 9000000000L)
        {
            spawnFruit();
            fruitTimer = 0;
        }
    }

    /* spawns fruit based on fruitTimer value at itemBoard[][] based on timer */
    public void spawnFruit()
    {

    }
}
