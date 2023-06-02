package item;

import java.awt.*;
import main.GamePanel;

public class SmallPellet extends Item
{
    GamePanel gp;

    public SmallPellet(GamePanel gp, int xPos, int yPos, int index)
    {
        this.gp = gp;
        x = xPos;
        y = yPos;
        hitbox = new Rectangle(xPos + 4, yPos + 4, pHitboxSize, pHitboxSize);
        points = 10;
        arrayIndex = index;
    }
}
