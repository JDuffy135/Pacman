package entity;

import main.KeyHandler;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.ArrayList;

//MASTER CLASS FOR ALL ENTITIES/SPRITES
public abstract class Entity
{
    //ENTITY ATTRIBUTES
    public final int hitboxOffset = 2;
    public final int hitboxSize = 20; //same as gp.displayedTileSize + 4
    public boolean collisionOnRight = false;
    public boolean collisionOnLeft = false;
    public boolean collisionOnUp = false;
    public boolean collisionOnDown = false;
    public int x, y;
    public int speed;
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction;
    public String lastDirection;
    public Rectangle hitbox;

    //SPRITE CLOCK FOR ANIMATIONS
    public int spriteCounter = 0;
    public int spriteNum = 1; /* 1 or 2 */


    //GHOST ONLY ATTRIBUTES
    public String ghostState; /* "chase" "scatter" "frightened" and "eaten" modes exist */
    public boolean dangerous; /* when true, ghosts can kill pacman */
    public int targetX; /* x cooridnate of current target */
    public int targetY; /* y coordinate of current target */

    //ARRAY CONTAINING ALL GHOSTS
    /* exists so we can iterate through the ghosts and change all ghostStates at once */
    public static Entity[] ghosts = new Entity[4];
    /* 0 = blinky, 1 = pinky, 2 = inky, 3 = clyde */


    //PACMAN CURRENT LEVEL & LEVEL TIMER
    public static int currentLevel = 1; /* set to 1 by default */
    public static long levelTimer = 0;
    /* NOTE: didn't implement the timer yet ^^ but will be used for changing ghost modes mid-game */

    //ARRAYLIST CONTAINING ALL WALL OBJECTS
    public static ArrayList<Wall> walls = new ArrayList<Wall>(); //static because only one global walls ArrayList



    /* adjusts entity and entity hitbox position based on direction and collisionOn<direction> values */
    /* used for pacman and ghosts */
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
    /* abstract method - @Override in pacman class */
    public void changeDirectionPacman(CollisionHandler cHandler)
    {

    }

    /* ghost direction change algorithm  */
    /* abstract method - @Override in each ghost class */
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

    /* calculates ghost's target tile */
    /* abstract method - @Override for each ghost class */
    public void calculateTarget()
    {
        /* target will depend on 1.) the ghost type, and 2.) the individual ghost's ghostState */
    }

    /* changes ghost's ghostState */
    /* ghosts only */
    public void changeGhostState()
    {
        //PROBABLY NOT AN ABSTRACT METHOD, JUST GOTTA FIGURE OUT HOW I'M GONNA IMPLEMENT THIS
        /* might need a similar method that is abstract tho, because blinky has special chase mode properties */
    }
}
