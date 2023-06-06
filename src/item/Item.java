package item;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Rectangle;
import entity.Pacman;
import main.UtilityTool;

import javax.imageio.ImageIO;

public abstract class Item
{
    //REFERENCES
    //small pellets are 1s on itemBoard
    //big pellets are 2s on itemBoard
    //cherries fruitType = 3
    //strawberry fruitType = 4
    //orange fruitType = 5
    //apple fruitType = 6


    //ITEM ATTRIBUTES
    public int x, y;
    public Rectangle hitbox;
    public int points;
    public int arrayIndex;
    public int fruitType; /* only for fruits - hence "fruit"Type */


    //ITEM SIZE VALUES & LOAD ITEM IMAGES
    public int pSize = 16;
    public int pHitboxSize = 4;
    public int fruitSize = 20;
    public BufferedImage smallPelletImage = getImage("SmallPellet", pSize, pSize);
    public BufferedImage bigPelletImage = getImage("LargePellet", pSize, pSize);

    /* used for displaying the current fruit */
    public static BufferedImage currentFruitImage = null;

    /* all fruit images scaled once for performance optimization */
    public BufferedImage cherriesImage = getImage("Cherries", fruitSize, fruitSize);
    public BufferedImage strawberryImage = getImage("Strawberry", fruitSize, fruitSize);
    public BufferedImage orangeImage = getImage("Orange", fruitSize, fruitSize);
    public BufferedImage appleImage = getImage("Apple", fruitSize, fruitSize);


    //2D ARRAY REPRESENTATION OF GAMEBOARD
    /* 0 = empty space or wall, 1 = small pellet, 2 = big pellet */
    public static int[][] itemBoard = new int[29][26];

    //ARRAYS CONTAINING ALL ON-SCREEN PELLETS
    /* originally I was going to use arraylists, but since "remove()" method is O(n) runtime and the number
    //of pellets is constant, I decided on arrays instead */
    public static SmallPellet[] smallPellets = new SmallPellet[240];
    public static BigPellet[] bigPellets = new BigPellet[4];



    /* scales image to proper size and returns for quick access */
    public BufferedImage getImage(String itemName, int width, int height)
    {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream("/misc/" + itemName + ".png"));
            image = uTool.scaleImage(image, width, height);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return image;
    }
}
