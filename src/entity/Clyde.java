package entity;

import main.GamePanel;
import java.awt.*;

public class Clyde extends Entity
{
//    GamePanel gp;

    public Clyde(GamePanel gp)
    {
        this.gp = gp;
        x = 0;
        y = 0;
        speed = 0;
        image = null;
        collisionOnUp = false;
        collisionOnDown = false;
        collisionOnLeft = false;
        collisionOnRight = false;
        direction = "";
        lastDirection = "";

        ghostState = "idle"; /* default: idle */
        idleTime = 1080; /* idles for 18 seconds after first scatter sequence ends */
        frightenedTag = 0;

        setDefaultValues();
        getImages();
        hitbox = new Rectangle(x, y, gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        killHitbox = new Rectangle(x + 4, y + 4, gp.displayedTileSize - 2, gp.displayedTileSize - 2);
        targetX = x;
        targetY = y;
        movementCooldownTimer = 0;
        wallImmunity = false;

        ghosts[3] = this;
    }

    /* sets default values for Clyde */
    @Override
    public void setDefaultValues()
    {
        this.x = 244; /* default: 244 */
        this.y = 260; /* default: 260 */
        this.speed = 1;

        /* for loselife and level reset */
        if (this.hitbox != null)
        {
            this.hitbox.setLocation(x, y);
        }
        if (this.killHitbox != null)
        {
            this.killHitbox.setLocation(x + 4, y + 4);
        }

        this.direction = "down"; /* default: down */
        this.lastDirection = "down"; /* default: down */
        ghostState = "idle";
        movementCooldownTimer = 1;
        wallImmunity = true;
        frightenedTag = 0;
    }

    /* sets up the images for Clyde */
    @Override
    public void getImages()
    {
        up1 = setupImage("ClydeUp1", "/ghosts/clyde/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        up2 = setupImage("ClydeUp2", "/ghosts/clyde/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        down1 = setupImage("ClydeDown1", "/ghosts/clyde/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        down2 = setupImage("ClydeDown2", "/ghosts/clyde/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        right1 = setupImage("ClydeRight1", "/ghosts/clyde/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        right2 = setupImage("ClydeRight2", "/ghosts/clyde/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        left1 = setupImage("ClydeLeft1", "/ghosts/clyde/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        left2 = setupImage("ClydeLeft2", "/ghosts/clyde/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
    }

    /* calculates ghost's target position */
    @Override
    public void calculateTarget()
    {
        if (this.ghostState == "chase")
        {
            /* targets pacman while more than 128 pixels away from him - scatters when within range */
            if (((gp.pacman.x + gp.pacman.hitbox.width/2) - (this.x + this.hitbox.width/2)) > 128 ||
                    ((gp.pacman.y + gp.pacman.hitbox.height/2) - (this.y + this.hitbox.height/2)) > 128)
            {
                this.targetX = gp.pacman.hitbox.x + gp.pacman.hitbox.width/2;
                this.targetY = gp.pacman.hitbox.y + gp.pacman.hitbox.height/2;
            }
            else
            {
                this.targetX = 0;
                this.targetY = 524;
            }
        }
        else if (this.ghostState == "scatter")
        {
            /* target is set to bottom left corner */
            this.targetX = 0;
            this.targetY = 524;
        }
        else if (this.ghostState == "eaten" || this.ghostState == "idleExit")
        {
            /* target is set to area right above ghost house */
            this.targetX = 224;
            this.targetY = 256;
        }

        /* no target for frightened or idle mode */
    }

    @Override
    public void update()
    {
        /* resets collision values */
        this.collisionOnRight = false;
        this.collisionOnLeft = false;
        this.collisionOnUp = false;
        this.collisionOnDown = false;

        if (gp.gameState == gp.PLAY_STATE)
        {
            //PATHFINDING, WALL COLLISION CHECKING, AND MOVEMENT
            this.calculateTarget();
            this.changeDirectionGhost(gp.cHandler);
            if (this.wallImmunity != true)
            {
                gp.cHandler.checkWallCollision(this);
            }
            this.move();
        }

        //TELEPORT CHECKING
        this.teleport();

        //IDLE EXIT CHECKING
        if (levelTimer >= this.idleTime && this.ghostState == "idle")
        {
            this.changeGhostState("idleExit");
        }

        /* adjusts pinky's sprite timer (spriteCounter) for animations */
        spriteCounter++;
        if (spriteCounter > 6)
        {
            if (spriteNum < 4)
            {
                spriteNum++;
            }
            else /* if spriteNum == 2 */
            {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    /* graphics for Clyde in game loop */
    @Override
    public void draw(Graphics2D g2)
    {
        image = null;

        /* eaten sprite animation during EATGHOST_STATE */
        if (gp.gameState == gp.EATGHOST_STATE && this.frightenedTag == 1
                && (this.ghostState == "eaten" || this.ghostState == "frightened"))
        {
            image = null;
        }
        else if (this.ghostState == "eaten") /* eaten sprite animation */
        {
            switch (direction)
            {
                case "up":
                    image = eyesUp;
                    break;
                case "down":
                    image = eyesDown;
                    break;
                case "left":
                    image = eyesLeft;
                    break;
                case "right":
                    image = eyesRight;
                    break;
                case "stationary":
                    if (lastDirection == "up")
                    {
                        image = eyesUp;
                    } else if (lastDirection == "down")
                    {
                        image = eyesDown;
                    } else if (lastDirection == "left")
                    {
                        image = eyesLeft;
                    } else if (lastDirection == "right")
                    {
                        image = eyesRight;
                    }
                    break;
            }
        }
        else if (this.ghostState == "frightened" || (frightenedTimer >= 1 && this.frightenedTag == 0)) /* blue ghost animation */
        {
            /* blinking starts when 3 seconds left */
            if (frightenedTimer >= 360)
            {
                switch(spriteNum)
                {
                    case 1:
                        image = frightened1;
                        break;
                    case 2:
                        image = frightened2;
                        break;
                    case 3:
                        image = frightenedBlink1;
                        break;
                    case 4:
                        image = frightenedBlink2;
                        break;
                }
            }
            else
            {
                if (spriteNum % 2 != 0)
                {
                    image = frightened1;
                }
                else
                {
                    image = frightened2;
                }
            }
        }
        else /* normal ghost animation */
        {
            switch(direction)
            {
                case "up":
                    if (spriteNum % 2 != 0) /* odd number */
                        image = up1;
                    else
                        image = up2;
                    break;
                case "down":
                    if (spriteNum % 2 != 0)
                        image = down1;
                    else
                        image = down2;
                    break;
                case "left":
                    if (spriteNum % 2 != 0)
                        image = left1;
                    else
                        image = left2;
                    break;
                case "right":
                    if (spriteNum % 2 != 0)
                        image = right1;
                    else
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
                    break;
            }
        }

        g2.drawImage(image, x, y, null);

        /* hitbox visualizer - delete eventually */
//        g2.draw3DRect(this.hitbox.x, this.hitbox.y, this.hitbox.width, this.hitbox.height, true);
//        g2.draw3DRect(this.killHitbox.x, this.killHitbox.y, this.killHitbox.width, this.killHitbox.height, true);

        /* directional hitbox visualizer - delete ventually */
//        g2.draw3DRect(this.hitbox.x, this.hitbox.y - 18, hitboxSize, hitboxSize, true);
//        g2.draw3DRect(this.hitbox.x, this.hitbox.y + 18, hitboxSize, hitboxSize, true);
//        g2.draw3DRect(this.hitbox.x - 18, this.hitbox.y, hitboxSize, hitboxSize, true);
//        g2.draw3DRect(this.hitbox.x + 18, this.hitbox.y, hitboxSize, hitboxSize, true);

        /* target visualizer - delete eventually */
//        g2.draw3DRect(this.targetX, this.targetY, this.killHitbox.width, this.killHitbox.height, true);
    }
}
