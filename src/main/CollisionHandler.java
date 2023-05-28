package main;

import entity.Entity;

public class CollisionHandler
{
    GamePanel gp;

    public CollisionHandler(GamePanel gp)
    {
        this.gp = gp;
    }

    public void checkCollision(Entity entity)
    {
        //sets "this" object's collisionOn value to true if "this" rectangle is touching a wall rectangle
    }
}
