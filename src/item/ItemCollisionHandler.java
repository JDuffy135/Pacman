package item;

import entity.Entity;
import main.GamePanel;

//DETECTS ITEM COLLISIONS AND TRACKS PLAYER SCORE
public class ItemCollisionHandler extends Item
{
    GamePanel gp;
    public static int score;
    public static int highscore = 0;
    public static int pelletsEaten;
    int wakaTimer = 0;

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
        /* wakaTimer adjustment */
        if (wakaTimer >= 15)
        {
            wakaTimer = 0;
        }
        if (wakaTimer >= 1)
        {
            wakaTimer++;
        }

        /* Fruit */
        if ((currentFruitImage != null) && (entity.hitbox.intersects(ItemSpawner.fruit.hitbox)))
        {
            /* eatfruit sound effect */
            gp.sfx.setFile(2);
            gp.sfx.play();

            /* score adjustment and delete fruit */
            score += ItemSpawner.fruit.points;
            ItemSpawner.deleteFruit();
        }

        /* Big Pellets */
        if (entity.x <= 40 || entity.x >= 400)
        {
            for (BigPellet p : bigPellets)
            {
                if (p != null && entity.hitbox.intersects(p.hitbox))
                {
                    /* waka sound effect */
                    if (wakaTimer == 0)
                    {
                        gp.sfx.setFile(10);
                        gp.sfx.play();
                        wakaTimer++;
                    }

                    /* point adjustment and nullify pellet from gameboard */
                    bigPellets[p.arrayIndex] = null;
                    p.hitbox.setLocation(-5000, -5000);
                    p.x = -5000;
                    p.y = -5000;
                    score += p.points;
                    pelletsEaten++;

                    /* change ghosts to frightened mode and resets frightenedTimer and point bonus */
                    for (Entity g : entity.ghosts)
                    {
                        g.frightenedTag = 0;
                        if (g != null && (g.ghostState == "chase" || g.ghostState == "scatter"))
                        {
                            g.changeGhostState("frightened");
                        }
                    }
                    /* NOTE: frightenedTimer handled in Blinky update() method */
                    Entity.frightenedTimer = 1;
                    Entity.frightenedPointBonus = 200;
                }
            }
        }

        /* Small Pellets */
        for (SmallPellet p : smallPellets)
        {
            if (p != null && entity.hitbox.intersects(p.hitbox))
            {
                /* waka sound effect */
                if (wakaTimer == 0)
                {
                    gp.sfx.setFile(10);
                    gp.sfx.play();
                    wakaTimer++;
                }

                /* point adjustment and nullify pellet from gameboard */
                smallPellets[p.arrayIndex] = null;
                p.hitbox.setLocation(-5000, -5000);
                p.x = -5000;
                p.y = -5000;
                score += p.points;
                pelletsEaten++;
            }
        }
    }

    /* checks to see if all the pellets are eaten - adjusts pelletsEaten, currentLevel, and gameState accordingly */
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
            Entity.levelTimer = 0;
            gp.changeGameState(gp.WIN_STATE);
        }
    }
}
