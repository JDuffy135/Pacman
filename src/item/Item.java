package item;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Rectangle;
import entity.Pacman;
import main.UtilityTool;

import javax.imageio.ImageIO;

public class Item
{
    //REFERENCES
    //SmallPellet hitbox size: ...
        //small pellets are 1s on itemBoard
    //BigPellet hitbox size: ...
        //big pellets are 2s on itemBoard
    //Fruit hitbox size: ...
        //cherries are 3s on itemBoard
        //strawberries are 4s on itemBoard
        //oranges are 5s on itemBoard
        //apples are 6s on itemBoard


    //ITEM ATTRIBUTES
    public int x, y;
    public Rectangle hitbox;
    public int points;
    public int arrayIndex;
    public int fruitType; /* only for fruits */


    //ITEM SIZE VALUES & LOAD ITEM IMAGES
    public int pSize = 16;
    public int pHitboxSize = 4;
    public int fruitSize = 20;
    public BufferedImage smallPelletImage = getImage("SmallPellet", pSize, pSize);
    public BufferedImage bigPelletImage = getImage("LargePellet", pSize, pSize);
    public static BufferedImage currentFruitImage = null;


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
