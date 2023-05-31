package entity;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.ArrayList;

//MASTER CLASS FOR ALL ENTITIES/SPRITES
public class Entity
{
    //PACMAN AND GHOST VARIABLES
    public final int hitboxOffset = 2;
    public final int hitboxSize = 20; //same as gp.displayedTileSize + 4
    public boolean collisionOn = false;
    public int x, y;
    public int speed;
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction;

    //WALL STUFF
    public Rectangle hitbox;
    public static ArrayList<Wall> walls = new ArrayList<Wall>(); //static because only one global walls ArrayList

    //SPRITE CLOCK FOR ANIMATIONS
    public int spriteCounter = 0;
    public int spriteNum = 1;
}
