package item;

import entity.Entity;
import main.GamePanel;

//DETECTS ITEM COLLISIONS AND TRACKS PLAYER SCORE
public class ItemCollisionHandler extends Item
{
    GamePanel gp;
    public static int score;
    public static int pelletsEaten;

    public ItemCollisionHandler(GamePanel gp)
    {
        this.gp = gp;
        score = 0;
        pelletsEaten = 0;
    }


    /* checks if the given entity (pacman) is touching an item, and updates if collision is detected */
    /* also changes ghosts to "frightened" state when a big pellet is eaten */
    public void checkItemCollision(Entity entity)
    {
        /* Fruit */
        if ((currentFruitImage != null) && (entity.hitbox.intersects(ItemSpawner.fruit.hitbox)))
        {
            score += ItemSpawner.fruit.points;
            ItemSpawner.deleteFruit();
        }

        /* Big Pellets */
        if (entity.x <= 40 || entity.x >=400)
        {
            for (BigPellet p : bigPellets)
            {
                if (p != null && entity.hitbox.intersects(p.hitbox))
                {
                    bigPellets[p.arrayIndex] = null;
                    p.hitbox.setLocation(-5000, -5000);
                    p.x = -5000;
                    p.y = -5000;
                    score += p.points;
                    pelletsEaten++;

                    /* change ghosts to frightened mode and resets frightenedTimer */
                    for (Entity g : entity.ghosts)
                    {
                        if (g != null && (g.ghostState == "chase" || g.ghostState == "scatter"))
                        {
                            g.changeGhostState("frightened");
                        }
                    }
                    /* NOTE: frightenedTimer handled in Blinky update() method */
                    Entity.frightenedTimer = 1;
                }
            }
        }

        /* Big Pellets */
        for (SmallPellet p : smallPellets)
        {
            if (p != null && entity.hitbox.intersects(p.hitbox))
            {
                smallPellets[p.arrayIndex] = null;
                p.hitbox.setLocation(-5000, -5000);
                p.x = -5000;
                p.y = -5000;
                score += p.points;
                pelletsEaten++;
            }
        }
    }

    /* checks to see if all the pellets are eaten */
    public void checkIfWon()
    {
        if (pelletsEaten >= 244)
        {
            pelletsEaten = 0;
            Entity.currentLevel++;
            if (ItemSpawner.fruitPresent == true)
            {
                ItemSpawner.deleteFruit();
            }
            /* change game state and whatnot, because if code reaches this point, all pellets were eaten */
            System.out.println("YOU WON"); /* temp test */
        }
    }
}
