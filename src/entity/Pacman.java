package entity;

import main.GamePanel;
import main.KeyHandler;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pacman extends Entity
{
    GamePanel gp;
    KeyHandler keyH;
    public String lastDirection = "right";
    BufferedImage image;

    //Pacman constructor
    public Pacman(GamePanel gp, KeyHandler keyH)
    {
        this.gp = gp;
        this.keyH = keyH;
        x = 0;
        y = 0;
        speed = 0;
        collisionOn = false;

        setDefaultPacmanValues();
        getPacmanImage();
        //hitbox = new Rectangle(x + 2, y + 2, gp.displayedTileSize + 2, gp.displayedTileSize + 2);

    }

    public void setDefaultPacmanValues()
    {
        this.x = 212;
        this.y = 404;
        this.speed = 2;
        direction = "right";
        lastDirection = "right";
    }

    public void getPacmanImage()
    {
        try
        {
            up1 = ImageIO.read(getClass().getResourceAsStream("/pacman/PacmanUpOpen.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/pacman/PacmanUpClosed.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/pacman/PacmanDownOpen.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/pacman/PacmanDownClosed.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/pacman/PacmanRightOpen.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/pacman/PacmanRightClosed.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/pacman/PacmanLeftOpen.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/pacman/PacmanLeftClosed.png"));
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void update()
    {
        //moves according to key pressed
        if (keyH.upPressed == true)
        {
            direction = "up";
            lastDirection = "up";
            if (this.collisionOn == false)
            {
                this.y -= speed;
            }
        }
        else if (keyH.downPressed == true)
        {
            direction = "down";
            lastDirection = "down";
            if (this.collisionOn == false)
            {
                this.y += speed;
            }
        }
        else if (keyH.leftPressed == true)
        {
            direction = "left";
            lastDirection = "left";
            if (this.collisionOn == false)
            {
                this.x -= speed;
            }
        }
        else if (keyH.rightPressed == true)
        {
            direction = "right";
            lastDirection = "right";
            if (this.collisionOn == false)
            {
                this.x += speed;
            }
        }
        else
        {
           direction = "stationary";
        }


        //collision detection
        collisionOn = false;
        gp.cHandler.checkWallCollision(this);


        //adjusts global sprite timer (spriteCounter) for sprite animations
        spriteCounter++;
        if (spriteCounter > 6)
        {
            if (spriteNum == 1)
            {
                spriteNum = 2;
            }
            else //if spriteNum == 2
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

        g2.drawImage(image, x, y, gp.displayedTileSize + 8, gp.displayedTileSize + 8, null);

        //hitbox visualizer - delete eventually
        //g2.draw3DRect(x + 2, y + 2, gp.displayedTileSize + 2, gp.displayedTileSize + 2, true);
    }
}
