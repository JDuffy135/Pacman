package entity;

import java.awt.image.BufferedImage;

//MASTER CLASS FOR ALL ENTITIES/SPRITES
public class Entity
{
    public int x, y;
    public int speed;

    //pacman images
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction;

    //sprite clock for animations
    public int spriteCounter = 0;
    public int spriteNum = 1;
}
