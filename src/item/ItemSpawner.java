package item;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//CONTROLS FRUITS AND PELLETS
public class ItemSpawner extends Item
{
    GamePanel gp;
    public static Fruit fruit;
    public static int currentFruit = 3; /* fruit types range from 3 to 6 */
    public static int fruitTimer = 0; /* timer for when fruits are present */
    public static boolean fruitPresent = false; /* true when a fruit is present */

    public ItemSpawner(GamePanel gp)
    {
        this.gp = gp;
    }


    /* sets up 2D array "itemBoard" */
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
        /* sets up itemBoard[][] and increments current player level */
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
        /* draws fruit if one exists on the screen */
        if (currentFruitImage != null)
        {
            g2.drawImage(currentFruitImage, 214, 310, null);
        }

        /* draws pellets */
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


    /* checks to see if a fruit needs to be spawned, and calls spawnFruit() if necessary */
    public void fruitCheck()
    {
        if (Entity.currentLevel <= 4 && fruitPresent == false) /* fruit type matches current level */
        {
            if (ItemCollisionHandler.pelletsEaten == 70 || ItemCollisionHandler.pelletsEaten == 170)
            {
                spawnFruit(gp, currentFruit);
                if (ItemCollisionHandler.pelletsEaten >= 170) { currentFruit++; }
            }
        }
        else if (Entity.currentLevel <= 6 && fruitPresent == false) /* fruit cycle incremented every time a fruit spawns */
        {
            if (currentFruit > 6) { currentFruit = 3; }

            if (ItemCollisionHandler.pelletsEaten == 70)
            {
                spawnFruit(gp, currentFruit);
                currentFruit++;
            }
            else if (ItemCollisionHandler.pelletsEaten == 170)
            {
                spawnFruit(gp, currentFruit);
                currentFruit++;
            }
        }
        else if (Entity.currentLevel > 6 && fruitPresent == false) /* only spawns apples after level 6 */
        {
            if (ItemCollisionHandler.pelletsEaten == 70 || ItemCollisionHandler.pelletsEaten == 170)
            {
                currentFruit = 6;
                spawnFruit(gp, currentFruit);
            }
        }
    }

    public void spawnFruit(GamePanel gp, int type)
    {
        fruit = new Fruit(gp, type);
        fruitPresent = true;
    }

    /* updates fruitTimer value */
    public static void updateFruitTimer()
    {
        fruitTimer++;

        /* once 9 seconds have passed, fruit is deleted */
        if (fruitTimer >= (9 * GamePanel.FPS))
        {
            deleteFruit();
        }
    }

    /* deletes fruit if player collides with it, or if fruit timer ends */
    public static void deleteFruit()
    {
        fruit.points = 0;
        currentFruitImage = null;
        fruit.hitbox.setLocation(-5000, -5000);
        fruit.x = -5000;
        fruit.y = -5000;
        fruitPresent = false;
        fruitTimer = 0;
    }
}
