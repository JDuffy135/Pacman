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
    public BufferedImage image;
    public int arrayPositionX, arrayPositionY; //NOTE: itemBoard[arrayPositionX][arrayPositionY] will be set to 0 when
                                               //pellet/fruit is eaten so that it isn't redrawn in the next frame
    public int fruitType; //null for pellets


    //LOAD ITEM IMAGES
    public int spSize = 4;
    public int bpSize = 12;
    public int fruitSize = 20;
    public BufferedImage smallPelletImage = getImage("SmallPellet", spSize, spSize);
    public BufferedImage bigPelletImage = getImage("SmallPellet", bpSize, bpSize);
    public BufferedImage cherriesImage = getImage("Cherries", fruitSize, fruitSize);
    public BufferedImage strawberryImage = getImage("Strawberry", fruitSize, fruitSize);
    public BufferedImage orangeImage = getImage("Orange", fruitSize, fruitSize);
    public BufferedImage appleImage = getImage("Apple", fruitSize, fruitSize);


    //BOARD CONTAINING ALL PELLETS AND FRUITS
    public static int[][] itemBoard = new int[26][29];

    //ARRAYLISTS CONTAINING ALL ON-SCREEN PELLETS
    public static ArrayList<SmallPellet> smallPellets = new ArrayList<SmallPellet>();
    public static ArrayList<BigPellet> bigPellets = new ArrayList<BigPellet>();



    //scales image to proper size and returns for quick access
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
