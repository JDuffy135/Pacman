package entity;

import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import main.GamePanel;
import main.Main;

//MASTER CLASS FOR ALL ENTITIES/SPRITES
public abstract class Entity
{
    //ENTITY ATTRIBUTES
    GamePanel gp;
    public final int hitboxOffset = 0; /* currently no point in having this here, just leaving it in case I need it again for whatever reason */
    public final int hitboxSize = 24; //same as gp.displayedTileSize + 8
    public boolean collisionOnRight = false;
    public boolean collisionOnLeft = false;
    public boolean collisionOnUp = false;
    public boolean collisionOnDown = false;
    public int x, y;
    public int speed;
    public BufferedImage image = null;
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction;
    public String lastDirection;
    public Rectangle hitbox;

    //SPRITE CLOCK FOR ANIMATIONS
    public int spriteCounter = 0;
    public int spriteNum = 1; /* 1 or 2 */


    //GHOST ONLY ATTRIBUTES
    public String ghostState; /* "idle" "idleExit" "chase" "scatter" "frightened" and "eaten" modes exist */
    public long idleTime; /* time ghost idles before spawning at start of level */
    public Rectangle killHitbox; /* when this hitbox intersects pacman's hitbox he loses a life */
    public int targetX;
    public int targetY;
    public int movementCooldownTimer;
    public boolean wallImmunity; /* only set to true when ghost is entering or exiting the ghost house */

    //ARRAY CONTAINING ALL GHOSTS
    /* exists so we can iterate through the ghosts and change all ghostStates at once */
    public static Entity[] ghosts = new Entity[4];
    /* 0 = blinky, 1 = pinky, 2 = inky, 3 = clyde */


    //PACMAN CURRENT LEVEL & TIMERS
    public static int currentLevel = 1; /* set to 1 by default */
    public static long levelTimer = 0;
    /* NOTE: didn't implement the timer yet ^^ but will be used for changing ghostStates mid-game */
    public static int frightenedTimer = 0; /* handled in Blinky update() method */


    //ARRAYLIST CONTAINING ALL WALL OBJECTS
    public static ArrayList<Wall> walls = new ArrayList<Wall>(); //static because only one global walls ArrayList


    //GHOST FIRGHTENED AND EATEN IMAGES
    public BufferedImage eyesUp = setupImage("GhostEyesUp", "/ghosts/other/", 24, 24); /* displayedTileSize + 8 = 24 */
    public BufferedImage eyesLeft = setupImage("GhostEyesLeft", "/ghosts/other/", 24, 24);
    public BufferedImage eyesDown = setupImage("GhostEyesDown", "/ghosts/other/", 24, 24);
    public BufferedImage eyesRight = setupImage("GhostEyesRight", "/ghosts/other/", 24, 24);
    public BufferedImage frightened1 = setupImage("HuntedGhost1", "/ghosts/other/", 24, 24);
    public BufferedImage frightened2 = setupImage("HuntedGhost2", "/ghosts/other/", 24, 24);
    public BufferedImage frightenedBlink1 = setupImage("HuntedGhostBlink1", "/ghosts/other/", 24, 24);
    public BufferedImage frightenedBlink2 = setupImage("HuntedGhostBlink2", "/ghosts/other/", 24, 24);


    //FRIGHTENED POINT BONUS AND POINT BONUS IMAGES
    public static int frightenedPointBonus = 200;
    public static int frightenedPointBonusTimer = 0;
    public static BufferedImage frightenedPointBonusImage = null;
    public BufferedImage pts200 = setupImage("200pts", "/misc/", 32, 32);
    public BufferedImage pts400 = setupImage("400pts", "/misc/", 32, 32);
    public BufferedImage pts800 = setupImage("800pts", "/misc/", 32, 32);
    public BufferedImage pts1600 = setupImage("1600pts", "/misc/", 32, 32);



    /* sets default entity values */
    /* ABSTRACT method - @Override in pacman and ghost classes */
    public void setDefaultValues()
    {

    }

    /* sets up images for given entity */
    /* ABSTRACT method - @Override in pacman and ghost classes */
    public void getImages()
    {

    }

    /* updates entity values in game loop */
    /* ABSTRACT method - @Override in pacman and ghost classes */
    public void update()
    {

    }

    /* updates entity drawings in game loop */
    /* ABSTRACT method - @Override in pacman and ghost classes */
    public void draw(Graphics2D g2)
    {

    }

    /* scales image so it doesn't need to be resized every time it's drawn on the screen */
    /* used for pacman and ghosts */
    public BufferedImage setupImage(String imageName, String directoryName, int width, int height)
    {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream(directoryName + imageName + ".png"));
            image = uTool.scaleImage(image, width, height);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return image;
    }


    /* adjusts entity and entity hitbox position based on direction and collisionOn<direction> values */
    /* pacman only */
    public void move()
    {
        /* moves entity and entity hitbox if there is no collision */
        if (this.direction == "up" && this.collisionOnUp == false)
        {
            this.y -= this.speed;
            this.hitbox.setLocation(this.x + hitboxOffset, this.y + hitboxOffset);
        }
        else if (this.direction == "down" && this.collisionOnDown == false)
        {
            this.y += this.speed;
            this.hitbox.setLocation(this.x + hitboxOffset, this.y + hitboxOffset);
        }
        else if (this.direction == "left" && this.collisionOnLeft == false)
        {
            this.x -= this.speed;
            this.hitbox.setLocation(this.x + hitboxOffset, this.y + hitboxOffset);
        }
        else if (this.direction == "right" && this.collisionOnRight == false)
        {
            this.x += this.speed;
            this.hitbox.setLocation(this.x + hitboxOffset, this.y + hitboxOffset);
        }
    }

    /* adjusts ghost, ghost hitbox, and ghost killHitbox position based on direction and collisionOn<direction> values */
    /* ghosts only */
    public void moveGhost()
    {
        /* moves entity and entity hitbox if there is no collision */
        if (this.direction == "up" && this.collisionOnUp == false)
        {
            this.y -= this.speed;
            this.hitbox.setLocation(this.x + hitboxOffset, this.y + hitboxOffset);
            this.killHitbox.setLocation(this.x + 8, this.y + 8);
        }
        else if (this.direction == "down" && this.collisionOnDown == false)
        {
            this.y += this.speed;
            this.hitbox.setLocation(this.x + hitboxOffset, this.y + hitboxOffset);
            this.killHitbox.setLocation(this.x + 8, this.y + 8);
        }
        else if (this.direction == "left" && this.collisionOnLeft == false)
        {
            this.x -= this.speed;
            this.hitbox.setLocation(this.x + hitboxOffset, this.y + hitboxOffset);
            this.killHitbox.setLocation(this.x + 8, this.y + 8);
        }
        else if (this.direction == "right" && this.collisionOnRight == false)
        {
            this.x += this.speed;
            this.hitbox.setLocation(this.x + hitboxOffset, this.y + hitboxOffset);
            this.killHitbox.setLocation(this.x + 8, this.y + 8);
        }
    }

    /* teleports entity to other side when traversing pipe thingies */
    /* used for pacman and ghosts */
    public void teleport()
    {
        if (this.x <= -24)
        {
            this.x = 468;
        }
        if (this.x >= 470)
        {
            this.x = -22;
        }
    }

    /* changes pacman direction based on keyboard input */
    /* ABSTRACT method - @Override in pacman class */
    public void changeDirectionPacman(CollisionHandler cHandler)
    {

    }

    /* ghost direction change algorithm and ghost house entrance/exit sequence  */
    /* ghosts only */
    public void changeDirectionGhost(CollisionHandler cHandler)
    {
        /* special case for when ghost needs to turn into ghost house */
        if (this.ghostState == "eaten" && (this.x >= 209 && this.x <= 215) && (this.y >= 208 && this.y <= 216))
        {
            this.movementCooldownTimer = 0;
        }

        if (this.movementCooldownTimer == 0)
        {
            if (this.ghostState == "chase" || this.ghostState == "scatter")
            {
                this.targetChaseLogic(cHandler); /* code in Entity class */
            }
            else if (this.ghostState == "frightened")
            {
                this.frightenedLogic(cHandler); /* code in Entity class */
            }
            else if (this.ghostState == "eaten")
            {
                /* else-if latter is the code for the entering/exiting sequence in/out of the ghost house */
                if (this.wallImmunity == false && (this.x >= 210 && this.x <= 215) && (this.y >= 208 && this.y <= 216))
                {
                    this.wallImmunity = true;
                    this.speed = 1;
                    this.direction = "down";
                }
                else if (this.wallImmunity == true && this.direction == "down")
                {
                    /* if in ghost house */
                    if (this.y >= 260)
                    {
                        this.direction = "up";
                    }
                }
                else if (this.wallImmunity == true && this.direction == "up")
                {
                    if (this.y <= 212)
                    {
                        this.wallImmunity = false;
                        this.direction = "right";
                        this.changeGhostState("chase");
                    }
                }
                else
                {
                    this.targetChaseLogic(cHandler);
                }
            }
            else if (this.ghostState == "idle")
            {
                this.idleLogic(cHandler); /* code in Entity class */
            }
            else if (this.ghostState == "idleExit")
            {
                if (this.x < 210)
                {
                    this.direction = "right";
                }
                else if (this.x > 215)
                {
                    this.direction = "left";
                }
                else if (this.x >= 210 && this.x <= 215)
                {
                    this.direction = "up";
                }

                if (this.y <= 216)
                {
                    this.direction = "right";
                    changeGhostState("chase");
                }
            }
        }
        else
        {
            this.movementCooldownTimer++;
            if (this.movementCooldownTimer >= 16)
            {
                this.movementCooldownTimer = 0;
            }
        }
    }

    /* direction changing logic when in a mmode with a target */
    /* ghosts only */
    public void targetChaseLogic(CollisionHandler cHandler)
    {
        int right, left, up, down;
        if (this.direction == "up")
        {
            /* checks up, left, and right */
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
    }

    /* direction changing logic when in frightened mode */
    /* ghosts only */
    public void frightenedLogic(CollisionHandler cHandler)
    {
        int randNum;
        String[] directionChoices = {"up", "left", "down", "right"};
        int[] directionValues = new int[4]; /* each index corresponds to directionChoices index */

        if (this.direction == "up")
        {
            /* checks up, left, and right */
            directionValues[2] = Integer.MAX_VALUE;

            directionValues[0] = checkGhostDirection("up", cHandler);
            directionValues[1] = checkGhostDirection("left", cHandler);
            directionValues[3] = checkGhostDirection("right", cHandler);
        }
        else if (this.direction == "left")
        {
            /* checks left, up, and down */
            directionValues[3] = Integer.MAX_VALUE;

            directionValues[0] = checkGhostDirection("up", cHandler);
            directionValues[1] = checkGhostDirection("left", cHandler);
            directionValues[2] = checkGhostDirection("down", cHandler);
        }
        else if (this.direction == "down")
        {
            /* checks down, left, and right */
            directionValues[0] = Integer.MAX_VALUE;

            directionValues[1] = checkGhostDirection("left", cHandler);
            directionValues[2] = checkGhostDirection("down", cHandler);
            directionValues[3] = checkGhostDirection("right", cHandler);
        }
        else if (this.direction == "right")
        {
            /* checks right, up, and down */
            directionValues[1] = Integer.MAX_VALUE;

            directionValues[0] = checkGhostDirection("up", cHandler);
            directionValues[2] = checkGhostDirection("down", cHandler);
            directionValues[3] = checkGhostDirection("right", cHandler);
        }

        //AFTER DIRECTION VALUES ARE SET...

        /* special intersections where ghosts can't turn up */
        if ((this.x >= 142 && this.x <= 280 && this.y >= 390 && this.y <= 406) ||
                (this.x >= 142 && this.x <= 280 && this.y >= 196 && this.y <= 216))
        {
            directionValues[0] = Integer.MAX_VALUE;
        }

        //GENERATE RANDOM NUMBER BETWEEN 1 AND 4: 1 = up, 2 = left, 3 = down, 4 = right
        randNum = (int)(Math.random() * 4);

        //SET DIRECTION
        if (directionValues[randNum] < 9999) /* if random direction is valid */
        {
            this.lastDirection = this.direction;
            this.direction = directionChoices[randNum];

            /* if direction changes, cooldown is initiated to prevent ghost from turning around*/
            if (this.lastDirection.equals(this.direction) == false)
            {
                this.movementCooldownTimer++;
            }
        }
        else /* if direction is not valid */
        {
            this.lastDirection = this.direction;

            if (directionValues[0] < 9999)
            {
                this.direction = directionChoices[0];
            }
            else if (directionValues[1] < 9999)
            {
                this.direction = directionChoices[1];
            }
            else if (directionValues[2] < 9999)
            {
                this.direction = directionChoices[2];
            }
            else if (directionValues[3] < 9999)
            {
                this.direction = directionChoices[3];
            }
            else
            {
                this.direction = this.direction;
            }

            /* if direction changes, cooldown is initiated to prevent ghost from turning around*/
            if (this.lastDirection.equals(this.direction) == false)
            {
                this.movementCooldownTimer++;
            }
        }
    }

    /* direction changing logic when in idle mode */
    /* ghosts only */
    public void idleLogic(CollisionHandler cHandler)
    {
        this.wallImmunity = true;
        /* directional changes for idle mode */
        if (this.y <= 246)
        {
            this.direction = "down";
        }
        else if (this.y >= 272)
        {
            this.direction = "up";
        }
    }

    /* calculates ghost's target position */
    /* ABSTRACT method - @Override for each ghost class */
    public void calculateTarget()
    {
        /* target will depend on 1.) the ghost type, and 2.) the individual ghost's ghostState */
    }

    /* calculates the distance from a point to a ghost's target */
    /* ghosts only */
    public int distanceToTarget(int x, int y)
    {
       return (int)(Math.sqrt((this.targetX - x)*(this.targetX - x) + (this.targetY - y)*(this.targetY - y)));
    }

    /* changes ghost's ghostState */
    /* ghosts only */
    public void changeGhostState(String newState)
    {
        /* "idle" "chase" "scatter" "frightened" and "eaten" ghostStates exist */
        switch(newState)
        {
            case "idle":
                this.speed = 1;
                this.ghostState = "idle";
                this.direction = "down";
                this.wallImmunity = true;
                break;
            case "chase":
                this.speed = 2;
                this.ghostState = "chase";
                this.wallImmunity = false;
                this.flipDirection();
                break;
            case "scatter":
                this.speed = 2;
                this.ghostState = "scatter";
                this.wallImmunity = false;
                flipDirection();
                break;
            case "frightened":
                this.speed = 1;
                this.ghostState = "frightened";
                this.wallImmunity = false;
                flipDirection();
                break;
            case "eaten":
                this.speed = 3;
                this.ghostState = "eaten";
                this.wallImmunity = false;
                flipDirection();
                break;
            case "idleExit":
                this.speed = 1;
                this.ghostState = "idleExit";
                this.wallImmunity = true;
                break;
        }
    }

    /* flips direction 180 degrees if doesn't result in wall collision */
    public void flipDirection()
    {
        Rectangle rect;
        if (this.direction == "up")
        {
            rect = new Rectangle(this.x, this.y + 4, this.hitbox.width, this.hitbox.height);
            if (this.gp.cHandler.checkForIntersectionsBool(this, rect) == false)
            {
                this.direction = "down";
            }
            else
            {
                this.frightenedLogic(this.gp.cHandler);
            }
        }
        else if (this.direction == "left")
        {
            rect = new Rectangle(this.x + 4, this.y, this.hitbox.width, this.hitbox.height);
            if (this.gp.cHandler.checkForIntersectionsBool(this, rect) == false)
            {
                this.direction = "right";
            }
            else
            {
                this.frightenedLogic(this.gp.cHandler);
            }
        }
        else if (this.direction == "down")
        {
            rect = new Rectangle(this.x, this.y - 4, this.hitbox.width, this.hitbox.height);
            if (this.gp.cHandler.checkForIntersectionsBool(this, rect) == false)
            {
                this.direction = "up";
            }
            else
            {
                this.frightenedLogic(this.gp.cHandler);
            }
        }
        else if (this.direction == "right")
        {
            rect = new Rectangle(this.x - 4, this.y, this.hitbox.width, this.hitbox.height);
            if (this.gp.cHandler.checkForIntersectionsBool(this, rect) == false)
            {
                this.direction = "left";
            }
            else
            {
                this.frightenedLogic(this.gp.cHandler);
            }
        }
    }

    /* returns 9999 if wall collision on given side of ghost, returns distance to target from given side otherwise */
    /* ghosts only */
    public int checkGhostDirection(String side, CollisionHandler cHandler)
    {
        if (side == "up")
        {
            if (cHandler.checkForIntersectionsBool(this, new Rectangle(this.hitbox.x, this.hitbox.y - 18, hitboxSize + 2, hitboxSize + 2)) == true)
            {
                return 9999;
            }
            else
            {
                return distanceToTarget((this.hitbox.x + (this.hitbox.width)/2), (this.hitbox.y + (this.hitbox.height)/2) - 20);
            }
        }
        else if (side == "left")
        {
            if (cHandler.checkForIntersectionsBool(this, new Rectangle(this.hitbox.x - 18, this.hitbox.y, hitboxSize + 2, hitboxSize + 2)) == true)
            {
                return 9999;
            }
            else
            {
                return distanceToTarget((this.hitbox.x + (this.hitbox.width)/2) - 20, (this.hitbox.y + (this.hitbox.height)/2));
            }
        }
        else if (side == "down")
        {
            if (cHandler.checkForIntersectionsBool(this, new Rectangle(this.hitbox.x, this.hitbox.y + 18, hitboxSize + 2, hitboxSize + 2)) == true)
            {
                return 9999;
            }
            else
            {
                return distanceToTarget((this.hitbox.x + (this.hitbox.width)/2), (this.hitbox.y + (this.hitbox.height)/2) + 20);
            }
        }
        else if (side == "right")
        {
            if (cHandler.checkForIntersectionsBool(this, new Rectangle(this.hitbox.x + 18, this.hitbox.y, hitboxSize + 2, hitboxSize + 2)) == true)
            {
                return 9999;
            }
            else
            {
                return distanceToTarget((this.hitbox.x + (this.hitbox.width)/2) + 20, (this.hitbox.y + (this.hitbox.height)/2));
            }
        }

        /* this is here simply to make the errors go away */
        System.out.println("How did you even get here...?");
        return 959595;
    }

    /* returns direction with smallest distance value */
    /* ghosts only */
    public String returnDirection(int up, int left, int down, int right)
    {
        if (Math.min(Math.min(left, right), Math.min(up, down)) == up)
        {
            return "up";
        }
        else if (Math.min(Math.min(left, right), Math.min(up, down)) == left)
        {
            if (left == up && up < 9999)
            {
                /* up takes precedence over left */
                return "up";
            }
            else
            {
                return "left";
            }
        }
        else if (Math.min(Math.min(left, right), Math.min(up, down)) == down)
        {
            if (down == up && up < 9999)
            {
                /* up takes precedence over down */
                return "up";
            }
            else if (down == left && left < 9999)
            {
                /* left takes precedence over down */
                return "left";
            }
            else
            {
                return "down";
            }
        }
        else if (Math.min(Math.min(left, right), Math.min(up, down)) == right)
        {
            if (right == up && up < 9999)
            {
                /* up takes precedence over right */
                return "up";
            }
            else if (right == left && left < 9999)
            {
                /* left takes precedence over right */
                return "left";
            }
            else if (right == down && down < 9999)
            {
                /* down takes precedence over right */
                return "down";
            }
            else
            {
                return "right";
            }
        }

        /* this is here just to get rid of the error highlight */
        return this.direction;
    }
}