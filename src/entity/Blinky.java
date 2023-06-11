package entity;

import main.GamePanel;

import java.awt.*;

public class Blinky extends Entity
{
    GamePanel gp;

    public Blinky(GamePanel gp)
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

        ghostState = "chase"; /* default: chase (idle for other ghosts) */
        idleTime = 0;

        setDefaultValues();
        getImages();
        hitbox = new Rectangle(x, y, gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        killHitbox = new Rectangle(x + 8, y + 8, gp.displayedTileSize - 8, gp.displayedTileSize - 8);
        targetX = x;
        targetY = y;
        movementCooldownTimer = 0;
        wallImmunity = false;

        ghosts[0] = this;
    }

    /* sets default values for Blinky */
    @Override
    public void setDefaultValues()
    {
        this.x = 212; /* default: 212 */
        this.y = 212; /* default: 212 */
        this.speed = 2;

        this.direction = "left"; /* default: left (up for other ghosts since they start in idle) */
        this.lastDirection = "left"; /* default: left (up for other ghosts since they start in idle) */
    }

    /* sets up the images for Blinky */
    @Override
    public void getImages()
    {
        up1 = setupImage("BlinkyUp1", "/ghosts/blinky/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        up2 = setupImage("BlinkyUp2", "/ghosts/blinky/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        down1 = setupImage("BlinkyDown1", "/ghosts/blinky/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        down2 = setupImage("BlinkyDown2", "/ghosts/blinky/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        right1 = setupImage("BlinkyRight1", "/ghosts/blinky/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        right2 = setupImage("BlinkyRight2", "/ghosts/blinky/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        left1 = setupImage("BlinkyLeft1", "/ghosts/blinky/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        left2 = setupImage("BlinkyLeft2", "/ghosts/blinky/", gp.displayedTileSize + 8, gp.displayedTileSize + 8);
    }

    /* calculates ghost's target position */
    @Override
    public void calculateTarget()
    {
        /* target will depend on 1.) the ghost type, and 2.) the individual ghost's ghostState */
        if (this.ghostState == "chase")
        {
            /* target is set to center of pacman's hitbox */
            this.targetX = gp.pacman.hitbox.x + gp.pacman.hitbox.width/2;
            this.targetY = gp.pacman.hitbox.y + gp.pacman.hitbox.height/2;
        }
        else if (this.ghostState == "scatter")
        {
            /* target is set to top right corner */
            this.targetX = 408;
            this.targetY = 0;
        }
        else if (this.ghostState == "eaten")
        {
            /* target is set to area right above ghost house */
            this.targetX = 224;
            this.targetY = 256;
        }

        /* no target for frightened or idle mode */
    }


    /* main method calls for game loop */
    @Override
    public void update()
    {
        /* resets collision values */
        this.collisionOnRight = false;
        this.collisionOnLeft = false;
        this.collisionOnUp = false;
        this.collisionOnDown = false;

        //PATHFINDING, WALL COLLISION CHECKING, AND MOVEMENT
        this.calculateTarget();
        this.changeDirectionGhost(gp.cHandler);
        if (this.wallImmunity != true)
        {
            gp.cHandler.checkWallCollision(this);
        }
        this.moveGhost();

        //TELEPORT CHECKING
        this.teleport();

        /* adjusts blinky's sprite timer (spriteCounter) for animations */
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

    /* graphics for Blinky in game loop */
    @Override
    public void draw(Graphics2D g2)
    {
        image = null;

        /* default sprite animation */
        if (this.ghostState == "chase" || this.ghostState == "scatter" || this.ghostState == "idle" || (this.ghostState == "eaten" && this.wallImmunity == true && this.direction != "down"))
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
        else if (this.ghostState == "frightened") //NOTE: will eventually adjust the blinking so that it mimics actual game mechanics
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
        else if (this.ghostState == "eaten")
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

        g2.drawImage(image, x, y, null);

        /* hitbox visualizer - delete eventually */
//        g2.draw3DRect(this.hitbox.x, this.hitbox.y, this.hitbox.width, this.hitbox.height, true);
//        g2.draw3DRect(this.killHitbox.x, this.killHitbox.y, this.killHitbox.width, this.killHitbox.height, true);

        /* directional hitbox visualizer - delete ventually */
//        g2.draw3DRect(this.hitbox.x, this.hitbox.y - 18, hitboxSize + 2, hitboxSize + 2, true);
//        g2.draw3DRect(this.hitbox.x, this.hitbox.y + 18, hitboxSize + 2, hitboxSize + 2, true);
//        g2.draw3DRect(this.hitbox.x - 18, this.hitbox.y, hitboxSize + 2, hitboxSize + 2, true);
//        g2.draw3DRect(this.hitbox.x + 18, this.hitbox.y, hitboxSize + 2, hitboxSize + 2, true);

        /* target visualizer - delete eventually */
        g2.draw3DRect(this.targetX, this.targetY, this.killHitbox.width, this.killHitbox.height, true);
    }
}
