package entity;

import main.GamePanel;
import java.awt.*;

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
        int entityRightX = entityLeftX + entity.hitboxSize;
        int entityTopY = entity.y + entity.hitboxOffset;
        int entityBottomY = entityTopY + entity.hitboxSize;

        /* creates a temporary hitbox offset in the direction of key pressed to check if entity can move there */
        directionalHitbox(entity, entityLeftX, entityTopY);
    }

    /* creates a new hitbox offset by the entity's movement speed in it's current direction */
    public void directionalHitbox(Entity entity, int xLeft, int yTop)
    {
        int size = entity.hitboxSize;
        Rectangle rect;

        switch(entity.direction)
        {
            case "up":
                rect = new Rectangle(entity.hitbox.x, entity.hitbox.y - 4, size, size);
                checkForIntersections(entity, rect,1);
            case "down":
                rect = new Rectangle(entity.hitbox.x, entity.hitbox.y + 4, size, size);
                checkForIntersections(entity, rect,2);
            case "left":
                rect = new Rectangle(entity.hitbox.x - 4, entity.hitbox.y, size, size);
                checkForIntersections(entity, rect,3);
            case "right":
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
}
