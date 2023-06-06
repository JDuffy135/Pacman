package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Pacman extends Entity
{
    GamePanel gp;
    KeyHandler keyH;
    BufferedImage image;

    /* Pacman constructor */
    public Pacman(GamePanel gp, KeyHandler keyH)
    {
        this.gp = gp;
        this.keyH = keyH;
        x = 0;
        y = 0;
        speed = 0;
        hitbox = new Rectangle(x + 2, y + 2, gp.displayedTileSize + 4, gp.displayedTileSize + 4);

        collisionOnUp = false;
        collisionOnDown = false;
        collisionOnLeft = false;
        collisionOnRight = false;
        direction = "";
        lastDirection = "";

        setDefaultPacmanValues();
        getPacmanImage();

    }

    /* sets default values for pacman if this wasn't self-evident */
    public void setDefaultPacmanValues()
    {
        this.x = 212;
        this.y = 404;
        this.speed = 2;
        this.direction = "stationary";
        this.lastDirection = "right";
    }

    /* sets up the images for pacman */
    public void getPacmanImage()
    {
        up1 = setupImage("PacmanUpOpen", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        up2 = setupImage("PacmanUpClosed", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        down1 = setupImage("PacmanDownOpen", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        down2 = setupImage("PacmanDownClosed", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        right1 = setupImage("PacmanRightOpen", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        right2 = setupImage("PacmanRightClosed", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        left1 = setupImage("PacmanLeftOpen", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        left2 = setupImage("PacmanLeftClosed", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
    }

    /* scales image so it doesn't need to be resized every time it's drawn on the screen */
    public BufferedImage setupImage(String imageName, int width, int height)
    {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream("/pacman/" + imageName + ".png"));
            image = uTool.scaleImage(image, width, height);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return image;
    }

    /* changes pacman direction based on keyboard input */
    @Override
    public void changeDirectionPacman(CollisionHandler cHandler)
    {
        if (this.keyH.upPressed == true)
        {
            if (cHandler.checkForIntersectionsBool(this, new Rectangle(this.hitbox.x, this.hitbox.y - 16, hitboxSize, hitboxSize)) == false)
            {
                this.direction = "up";
                this.lastDirection = "up";
                KeyHandler.keyPressed = false;
            }
        }
        else if (this.keyH.downPressed == true)
        {
            if (cHandler.checkForIntersectionsBool(this, new Rectangle(this.hitbox.x, this.hitbox.y + 16, hitboxSize, hitboxSize)) == false)
            {
                this.direction = "down";
                this.lastDirection = "down";
                KeyHandler.keyPressed = false;
            }
        }
        else if (this.keyH.leftPressed == true)
        {
            if (cHandler.checkForIntersectionsBool(this, new Rectangle(this.hitbox.x - 16, this.hitbox.y, hitboxSize, hitboxSize)) == false)
            {
                this.direction = "left";
                this.lastDirection = "left";
                KeyHandler.keyPressed = false;
            }
        }
        else if (this.keyH.rightPressed == true)
        {
            if (cHandler.checkForIntersectionsBool(this, new Rectangle(this.hitbox.x + 16, this.hitbox.y, hitboxSize, hitboxSize)) == false)
            {
                this.direction = "right";
                this.lastDirection = "right";
                KeyHandler.keyPressed = false;
            }
        }
        else
        {
            this.direction = "stationary";
        }
    }


    //METHOD CALLED FROM GAME LOOP
    public void update()
    {
        /* resets collision values */
        this.collisionOnRight = false;
        this.collisionOnLeft = false;
        this.collisionOnUp = false;
        this.collisionOnDown = false;

        //DIRECTION CHANGING BASED ON KEYBOARD INPUT
        if (KeyHandler.keyPressed == true) { this.changeDirectionPacman(gp.cHandler); }

        //WALL AND ITEM COLLISION HANDLING & PACMAN MOVEMENT
        gp.cHandler.checkWallCollision(this);
        gp.icHandler.checkItemCollision(this);
        this.move();

        //TELEPORT CHECKING
        this.teleport();

        /* adjusts pacman sprite timer (spriteCounter) for animations */
        /* NOTE TO SELF: will do this for every individual entity that has an animation */
        spriteCounter++;
        if (spriteCounter > 6)
        {
            if (spriteNum == 1)
            {
                spriteNum = 2;
            }
            else /* if spriteNum == 2 */
            {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2)
    {
        image = null;

        switch(direction)
        {
            case "up":
                if (spriteNum == 1)
                    image = up1;
                else if (spriteNum == 2)
                    image = up2;
                break;
            case "down":
                if (spriteNum == 1)
                    image = down1;
                else if (spriteNum == 2)
                    image = down2;
                break;
            case "left":
                if (spriteNum == 1)
                    image = left1;
                else if (spriteNum == 2)
                    image = left2;
                break;
            case "right":
                if (spriteNum == 1)
                    image = right1;
                else if (spriteNum == 2)
                    image = right2;
                break;
            case "stationary":
                if (lastDirection == "up")
                {
                    image = up2;
                }
                else if (lastDirection == "down")
                {
                    image = down2;
                }
                else if (lastDirection == "left")
                {
                    image = left2;
                }
                else if (lastDirection == "right")
                {
                    image = right2;
                }
        }

        g2.drawImage(image, x, y, null);

        /* hitbox visualizer - delete eventually */
        //g2.draw3DRect(this.hitbox.x, this.hitbox.y, gp.displayedTileSize + 4, gp.displayedTileSize + 4, true);
    }
}
