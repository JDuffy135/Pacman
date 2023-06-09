package entity;

import main.GamePanel;
import main.KeyHandler;

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

        ghostState = "chase"; /* "idle" by default */
        idleTime = 0;
        dangerous = true;

        setDefaultValues();
        getImages();
        hitbox = new Rectangle(x, y, gp.displayedTileSize + 8, gp.displayedTileSize + 8);
        killHitbox = new Rectangle(x + 8, y + 8, gp.displayedTileSize - 8, gp.displayedTileSize - 8);
        targetX = x;
        targetY = y;
        movementCooldownTimer = 0;

        ghosts[0] = this;
    }

    /* sets default values for Blinky */
    @Override
    public void setDefaultValues()
    {
        this.x = 212;
        this.y = 212;
        this.speed = 2;

        this.direction = "left";
        this.lastDirection = "left";
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
            /*  */
        }
        else if (this.ghostState == "frightened")
        {
            /* there is no target */
        }
        else if (this.ghostState == "eaten")
        {
            /* target is set to area right above ghost house */
        }
        else if (this.ghostState == "idle")
        {
            /* there is no target */
        }
    }

    /* ghost direction change algorithm - call this method in update() */
    @Override
    public void changeDirectionGhost(CollisionHandler cHandler)
    {
        if (this.ghostState == "chase")
        {
            this.chaseLogic(cHandler);
        }
        else if (this.ghostState == "scatter")
        {
            //NOTE TO SELF: I think this will end up being the same as chaseLogic
            this.scatterLogic(cHandler);
        }
        else if (this.ghostState == "frightened")
        {
            this.frightenedLogic(cHandler); /* code in Entity class */
        }
        else if (this.ghostState == "eaten")
        {
            this.eatenLogic(cHandler); /* code in Entity class */
        }
        else if (this.ghostState == "idle")
        {
            this.idleLogic(cHandler); /* code in Entity class */
        }
    }

    /* chase mode directional changes */
    @Override
    public void chaseLogic(CollisionHandler cHandler) /* NOTE: currently fucked up */
    {
        int right, left, up, down;
        if (this.direction == "up")
        {
            /* checks up, left, and right */
            //ALGORITHM:
            //create rectangle offset in particular direction, and set value equal to 9999 if collides with wall
            //for directions that don't equal 9999, calculate distance to target
            //choose direction that minimizies distance - if multiple values are equal, go to order of precedence
            down = Integer.MAX_VALUE;

            up = checkGhostDirection("up", cHandler);
            left = checkGhostDirection("left", cHandler);
            right = checkGhostDirection("right", cHandler);

            /* special intersections where ghosts can't turn up */
            if ((this.x >= 142 && this.x <= 280 && this.y >= 390 && this.y <= 406) ||
                    (this.x >= 142 && this.x <= 280 && this.y >= 196 && this.y <= 216))
            {
                up = Integer.MAX_VALUE;
            }

            if (up >= 9999 && left >= 9999 && right >= 9999)
            {
                /* maintains direction if all sides result in wall collision */
                this.direction = this.direction;
            }
            else
            {
                this.lastDirection = this.direction;
                direction = returnDirection(up, left, down, right);

                /* if direction changes, cooldown is initiated to prevent ghost from turning around*/
                if (this.lastDirection.equals(this.direction) == false)
                {
                    this.movementCooldownTimer++;
                }
            }
        }
        else if (this.direction == "left")
        {
            /* checks left, up, and down */
            right = Integer.MAX_VALUE;

            up = checkGhostDirection("up", cHandler);
            left = checkGhostDirection("left", cHandler);
            down = checkGhostDirection("down", cHandler);

            /* special intersections where ghosts can't turn up */
            if ((this.x >= 142 && this.x <= 280 && this.y >= 390 && this.y <= 406) ||
                    (this.x >= 142 && this.x <= 280 && this.y >= 196 && this.y <= 216))
            {
                up = Integer.MAX_VALUE;
            }

            if (up >= 9999 && left >= 9999 && down >= 9999)
            {
                /* maintains direction if all sides result in wall collision */
                this.direction = this.direction;
            }
            else
            {
                this.lastDirection = this.direction;
                direction = returnDirection(up, left, down, right);

                /* if direction changes, cooldown is initiated to prevent ghost from turning around*/
                if (this.lastDirection.equals(this.direction) == false)
                {
                    this.movementCooldownTimer++;
                }
            }
        }
        else if (this.direction == "down")
        {
            /* checks down, left, and right */
            up = Integer.MAX_VALUE;

            left = checkGhostDirection("left", cHandler);
            down = checkGhostDirection("down", cHandler);
            right = checkGhostDirection("right", cHandler);
            if (left >= 9999 && down >= 9999 && right >= 9999)
            {
                /* maintains direction if all sides result in wall collision */
                this.direction = this.direction;
            }
            else
            {
                this.lastDirection = this.direction;
                direction = returnDirection(up, left, down, right);

                /* if direction changes, cooldown is initiated to prevent ghost from turning around*/
                if (this.lastDirection.equals(this.direction) == false)
                {
                    this.movementCooldownTimer++;
                }
            }
        }
        else if (this.direction == "right")
        {
            /* checks right, up, and down */
            left = Integer.MAX_VALUE;

            up = checkGhostDirection("up", cHandler);
            down = checkGhostDirection("down", cHandler);
            right = checkGhostDirection("right", cHandler);

            /* special intersections where ghosts can't turn up */
            if ((this.x >= 142 && this.x <= 280 && this.y >= 390 && this.y <= 406) ||
                    (this.x >= 142 && this.x <= 280 && this.y >= 196 && this.y <= 216))
            {
                up = Integer.MAX_VALUE;
            }

            if (up >= 9999 && down >= 9999 && right >= 9999)
            {
                /* maintains direction if all sides result in wall collision */
                this.direction = this.direction;
            }
            else
            {
                this.lastDirection = this.direction;
                direction = returnDirection(up, left, down, right);

                /* if direction changes, cooldown is initiated to prevent ghost from turning around*/
                if (this.lastDirection.equals(this.direction) == false)
                {
                    this.movementCooldownTimer++;
                }
            }
        }

        /* for debugging */
        //System.out.println(this.direction);
    }

    /* scatter mode directional changes */
    @Override
    public void scatterLogic(CollisionHandler cHandler)
    {

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
        if (this.movementCooldownTimer == 0)
        {
            this.changeDirectionGhost(gp.cHandler);
        }
        else
        {
            this.movementCooldownTimer++;
            if (this.movementCooldownTimer >= 16)
            {
                this.movementCooldownTimer = 0;
            }
        }
        gp.cHandler.checkWallCollision(this);
        //System.out.println("LEFT: " + collisionOnLeft + " RIGHT: " + collisionOnRight + " UP: " + collisionOnUp + " DOWN: " + collisionOnDown);
        System.out.println("X: " + this.x + " Y: " + this.y);
        this.moveGhost();

        //TELEPORT CHECKING
        this.teleport();

        /* adjusts blinky's sprite timer (spriteCounter) for animations */
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

    /* graphics for Blinky in game loop */
    @Override
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
//        g2.draw3DRect(this.hitbox.x, this.hitbox.y, this.hitbox.width, this.hitbox.height, true);
//        g2.draw3DRect(this.killHitbox.x, this.killHitbox.y, this.killHitbox.width, this.killHitbox.height, true);

        /* directional hitbox visualizer - delete ventually */
//        g2.draw3DRect(this.hitbox.x, this.hitbox.y - 18, hitboxSize + 2, hitboxSize + 2, true);
//        g2.draw3DRect(this.hitbox.x, this.hitbox.y + 18, hitboxSize + 2, hitboxSize + 2, true);
//        g2.draw3DRect(this.hitbox.x - 18, this.hitbox.y, hitboxSize + 2, hitboxSize + 2, true);
//        g2.draw3DRect(this.hitbox.x + 18, this.hitbox.y, hitboxSize + 2, hitboxSize + 2, true);

        /* target visuzlizer - delete eventually */
//        g2.draw3DRect(this.targetX, this.targetY, this.killHitbox.width, this.killHitbox.height, true);
    }
}
