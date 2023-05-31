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

    //sets collisionOn value to true if entity is touching a wall
    public void checkWallCollision(Entity entity)
    {
        //finds location of each side of entity's "hitbox"
        int entityLeftX = entity.x + entity.hitboxOffset;
        int entityRightX = entityLeftX + entity.hitboxSize;
        int entityTopY = entity.y + entity.hitboxOffset;
        int entityBottomY = entityTopY + entity.hitboxSize;

        Rectangle dHitbox = directionalHitbox(entity, entityLeftX, entityRightX, entityTopY, entityBottomY);
        checkForIntersections(entity, dHitbox);

        if (entity.collisionOn == false)
        {
            switch (entity.direction)
            {
                case "up":
                    entity.hitbox.setLocation(entity.x, entity.y - entity.speed);
                case "down":
                    entity.hitbox.setLocation(entity.x, entity.y + entity.speed);
                case "left":
                    entity.hitbox.setLocation(entity.x - entity.speed, entity.y);
                case "right":
                    entity.hitbox.setLocation(entity.x + entity.speed, entity.y);
            }
        }
    }

    //creates a new hitbox offset by the entity's movement speed in it's current direction
    public Rectangle directionalHitbox(Entity entity, int xLeft, int xRight, int yTop, int yBot)
    {
        int size = entity.hitboxSize;

        switch(entity.direction)
        {
            case "up":
                return new Rectangle(xLeft, yTop - entity.speed, size, size);
            case "down":
                return new Rectangle(xLeft, yTop + entity.speed, size, size);
            case "left":
                return new Rectangle(xLeft - entity.speed, yTop, size, size);
            case "right":
                return new Rectangle(xLeft + entity.speed, yTop, size, size);
            case "stationary":
                return new Rectangle(xLeft, yTop, size, size);
        }
        return new Rectangle(xLeft, yTop, size, size);
    }

    //loops through all the wall objects and checks if any wall hitbox intersects with the directional hitbox
    //sets collisionOn to true if an intersection is found
    public void checkForIntersections(Entity entity, Rectangle dHitbox)
    {
        for (Wall w : walls)
        {
            if (w.hitbox.intersects(dHitbox) == true)
            {
                entity.collisionOn = true;
                break;
            }
        }
    }
}
