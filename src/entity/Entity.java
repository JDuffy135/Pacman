package entity;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import main.GamePanel;

//MASTER CLASS FOR ALL ENTITIES/SPRITES
public class Entity
{
    //PACMAN VARIABLES
    public int x, y;
    public int speed;
    public Rectangle pacmanHitbox;
    public boolean collisionOn;

    //PACMAN IMAGES
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction;

    //SPRITE CLOCK FOR ANIMATIONS
    public int spriteCounter = 0;
    public int spriteNum = 1;
}
