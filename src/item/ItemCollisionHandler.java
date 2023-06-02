package item;

import entity.Entity;
import main.GamePanel;

public class ItemCollisionHandler extends Item
{
    GamePanel gp;
    public static int score;

    public ItemCollisionHandler(GamePanel gp)
    {
        this.gp = gp;
        score = 0;
    }


    /* checks if the given entity (pacman) is touching an item, and updates if collision is detected */
    public void checkItemCollision(Entity entity)
    {
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
                }
            }
        }

        for (SmallPellet p : smallPellets)
        {
            if (p != null && entity.hitbox.intersects(p.hitbox))
            {
                smallPellets[p.arrayIndex] = null;
                p.hitbox.setLocation(-5000, -5000);
                p.x = -5000;
                p.y = -5000;
                score += p.points;
            }
        }
    }

    /* checks to see if all the pellets are eaten */
    public void checkIfWon()
    {
        for (BigPellet bigPellet : bigPellets)
        {
            if (bigPellet != null)
            {
                return;
            }
        }
        for (SmallPellet smallPellet : smallPellets)
        {
            if (smallPellet != null)
            {
                return;
            }
        }

        /* change game state and whatnot, because if code reaches this point, all pellets were eaten */
        System.out.println("YOU WON"); /* temp test */
    }
}
