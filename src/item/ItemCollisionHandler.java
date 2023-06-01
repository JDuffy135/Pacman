package item;

import entity.Entity;
import main.GamePanel;

public class ItemCollisionHandler extends Item
{
    GamePanel gp;

    public ItemCollisionHandler(GamePanel gp)
    {
        this.gp = gp;
    }


    //checks if the given entity (pacman) is touching an item - calls eatItem if entity is touching an item
    public void checkItemCollision(Entity entity)
    {
        if (entity.x <= 40 || entity.x >=400)
        {
            //checks bigPellet array
        }
    }

    //updates score, updates itemBoard, and deletes the eaten pellet from its respective arraylist
    //if the item is a pellet
    public void eatItem(Entity entity)
    {

    }
}
