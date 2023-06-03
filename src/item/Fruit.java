package item;

import main.GamePanel;

import java.awt.*;

public class Fruit extends Item
{
    GamePanel gp;

    public Fruit(GamePanel gp, int type)
    {
        this.gp = gp;
        x = 214;
        y = 310;
        hitbox = new Rectangle(x, y, fruitSize, fruitSize);
        fruitType = type;
        points = 0;
        updateFruitValues();
    }

    /* sets up points and image value depending on fruit type */
    public void updateFruitValues()
    {
        if (this.fruitType == 3) /* cherries */
        {
            this.points = 100;
            Item.currentFruitImage = getImage("Cherries", fruitSize, fruitSize);
        }
        else if (this.fruitType == 4) /* strawberry */
        {
            this.points = 300;
            Item.currentFruitImage = getImage("Strawberry", fruitSize, fruitSize);
        }
        else if (this.fruitType == 5) /* orange */
        {
            this.points = 500;
            Item.currentFruitImage = getImage("Orange", fruitSize, fruitSize);
        }
        else if (this.fruitType == 6) /* apple */
        {
            this.points = 700;
            Item.currentFruitImage = getImage("Apple", fruitSize, fruitSize);
        }
    }
}
