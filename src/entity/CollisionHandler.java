package entity;

import main.GamePanel;
import java.awt.*;
import item.ItemCollisionHandler;

public class CollisionHandler extends Entity
{
    GamePanel gp;

    public CollisionHandler(GamePanel gp)
    {
        this.gp = gp;
    }

    /* sets collisionOn<direction> value to true if a wall is in the path of the given entity */
    public void checkWallCollision(Entity entity)
    {
        /* finds location of each side of entity's "hitbox" */
        int entityLeftX = entity.x + entity.hitboxOffset;
        int entityRightX = entityLeftX + entity.hitbox.width;
        int entityTopY = entity.y + entity.hitboxOffset;
        int entityBottomY = entityTopY + entity.hitbox.height;

        /* creates a temporary hitbox offset in the direction of key pressed to check if entity can move there */
        directionalHitbox(entity, entityLeftX, entityTopY);
    }

    /* creates a new hitbox offset by the entity's movement speed in it's current direction */
    public void directionalHitbox(Entity entity, int xLeft, int yTop)
    {
        int size = entity.hitbox.width;
        Rectangle rect;

        if (entity.direction == "up")
        {
            rect = new Rectangle(entity.hitbox.x, entity.hitbox.y - 4, size, size);
            checkForIntersections(entity, rect,1);
        }
        else if (entity.direction == "down")
        {
            rect = new Rectangle(entity.hitbox.x, entity.hitbox.y + 4, size, size);
            checkForIntersections(entity, rect,2);
        }
        else if (entity.direction == "left")
        {
            rect = new Rectangle(entity.hitbox.x - 4, entity.hitbox.y, size, size);
            checkForIntersections(entity, rect,3);
        }
        else if (entity.direction == "right")
        {
            rect = new Rectangle(entity.hitbox.x + 4, entity.hitbox.y, size, size);
            checkForIntersections(entity, rect,4);
        }
    }

    /* loops through all the wall objects and checks if any wall hitbox intersects with the directional hitbox
    sets collisionOn to true if an intersection is found */
    public void checkForIntersections(Entity entity, Rectangle dHitbox, int dir)
    {
        for (Wall w : walls)
        {
            if (w.hitbox.intersects(dHitbox) == true)
            {
                if (dir == 1)
                {
                    entity.collisionOnUp = true;
                }
                else if (dir == 2)
                {
                    entity.collisionOnDown = true;
                }
                else if (dir == 3)
                {
                    entity.collisionOnLeft = true;
                }
                else if (dir == 4)
                {
                    entity.collisionOnRight = true;
                }
                break;
            }
        }
    }

    /* returns true if an intersection occurs */
    public boolean checkForIntersectionsBool(Entity entity, Rectangle dHitbox)
    {
        for (Wall w : walls)
        {
            if (w.hitbox.intersects(dHitbox) == true)
            {
                return true;
            }
        }
        return false;
    }

    /* checks for pacman and ghost collisions & handles frightenedPointBonusTimer */
    public void checkGhostCollision(Entity entity)
    {
        /* frightenedPointBonusTimer checking/updating */
        if (frightenedPointBonusTimer > 0)
        {
            frightenedPointBonusTimer++;
            if (frightenedPointBonusTimer >= 60)
            {
                frightenedPointBonusTimer = 0;
            }
        }

        //GHOST COLLISION HANDLING
        for (Entity g : ghosts)
        {
            if (g != null && g.killHitbox.intersects(entity.hitbox))
            {
                if (g.ghostState == "frightened")
                {
                    g.changeGhostState("eaten");
                    ItemCollisionHandler.score += frightenedPointBonus;
                    frightenedPointBonusTimer = 1;
                    if (frightenedPointBonus == 200)
                    {
                        frightenedPointBonusImage = pts200;
                        frightenedPointBonus = 400;
                    }
                    else if (frightenedPointBonus == 400)
                    {
                        frightenedPointBonusImage = pts400;
                        frightenedPointBonus = 800;
                    }
                    else if (frightenedPointBonus == 800)
                    {
                        frightenedPointBonusImage = pts800;
                        frightenedPointBonus = 1600;
                    }
                    else if (frightenedPointBonus == 1600)
                    {
                        frightenedPointBonusImage = pts1600;
                        frightenedPointBonus = 200;
                    }

                    //NOTE: write code to change game state to "paused" for ~40 frames...?
                }
                else if (g.ghostState == "chase" || g.ghostState == "scatter")
                {
                    //WRITE CODE FOR THIS STUFF
                    //decrement pacman lives
                    //pause for 120 frames
                    //reset ghosts and pacman (by resetting levelTimer to 0)
                }
            }
        }
    }
}
