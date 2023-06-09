package entity;

import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

//MASTER CLASS FOR ALL ENTITIES/SPRITES
public abstract class Entity
{
    //ENTITY ATTRIBUTES
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
    public String ghostState; /* "idle" "chase" "scatter" "frightened" and "eaten" modes exist */
    public long idleTime; /* time ghost idles before spawning at start of level */
    public boolean dangerous; /* when true, ghosts can kill pacman */
    public Rectangle killHitbox; /* when this hitbox intersects pacman's hitbox he loses a life */
    public int targetX;
    public int targetY;
    public int movementCooldownTimer;

    //ARRAY CONTAINING ALL GHOSTS
    /* exists so we can iterate through the ghosts and change all ghostStates at once */
    public static Entity[] ghosts = new Entity[4];
    /* 0 = blinky, 1 = pinky, 2 = inky, 3 = clyde */


    //PACMAN CURRENT LEVEL & LEVEL TIMER
    public static int currentLevel = 1; /* set to 1 by default */
    public static long levelTimer = 0;
    /* NOTE: didn't implement the timer yet ^^ but will be used for changing ghostStates mid-game */

    //ARRAYLIST CONTAINING ALL WALL OBJECTS
    public static ArrayList<Wall> walls = new ArrayList<Wall>(); //static because only one global walls ArrayList



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

    /* ghost direction change algorithm  */
    /* ABSTRACT method - @Override in each ghost class */
    public void changeDirectionGhost(CollisionHandler cHandler)
    {
        //GHOST PATHFINDING ALGORITHM STEPS (for all modes except frightened)
        /*

        STEP 1: target position is calculated (calls calculateTarget() method)

        STEP 2: for tiles 1.) directly in front of ghost, 2.) tile 90 degrees clockwise of tile 1, and 3.) tile 90 degrees
            counterclockwise from tile 1...

        1.) eliminates any direction that results in wall collision (use code similar to changeDirection in pacman class)
        2.) for the remaining tiles, the distance between the given tile and the target is calculated
        3.) the direction that minimizes distance between the next tile and the target is chosen

        NOTE: if multipe direction result in same distance to target, here is the order of prescedence for
            the directions: up, left, down, right
        */


        //FRIGHTENED MODE ALGORITHM
        /*
        NO TARGET TILE

        for tiles 1.) directly in front of ghost, 2.) tile 90 degrees clockwise of tile 1, and 3.) tile 90 degrees
            counterclockwise from tile 1...

        1.) eliminates any direction that results in wall collision (use code similar to changeDirection in pacman class)
        2.) one of the remaining directions is chosen at random

        */
    }

    /* direction changing logic when in chase mode */
    /* ABSTRACT method - @Override for each ghost class */
    public void chaseLogic(CollisionHandler cHandler)
    {

    }

    /* direction changing logic when in scatter mode */
    /* ABSTRACT method - @Override for each ghost class */
    public void scatterLogic(CollisionHandler cHandler)
    {

    }

    /* direction changing logic when in frightened mode */
    /* ghosts only */
    public void frightenedLogic(CollisionHandler cHandler)
    {

    }

    /* direction changing logic when in eaten mode */
    /* ghosts only */
    public void eatenLogic(CollisionHandler cHandler)
    {

    }

    /* direction changing logic when in idle mode */
    /* ghosts only */
    public void idleLogic(CollisionHandler cHandler)
    {

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
                this.ghostState = "idle";
                this.dangerous = false;
                //...
            case "chase":
                this.ghostState = "chase";
                this.dangerous = true;
                //flip direction
                //...
            case "scatter":
                this.ghostState = "scatter";
                this.dangerous = true;
                //flip direction
                //...
            case "frightened":
                this.ghostState = "frightened";
                this.dangerous = false;
                //flip direction
                //...
            case "eaten":
                this.ghostState = "eaten";
                this.dangerous = false;
                //flip direction
                //...
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
                /* up takes precedence over left */
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
