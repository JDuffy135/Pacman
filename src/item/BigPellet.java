package item;

import java.awt.*;
import main.GamePanel;

public class BigPellet extends Item
{
    GamePanel gp;

    public BigPellet(GamePanel gp, int xPos, int yPos,/* int row, int col,*/ int index)
    {
        this.gp = gp;
        x = xPos;
        y = yPos;
        hitbox = new Rectangle(xPos + 4, yPos + 4, pHitboxSize, pHitboxSize);
        points = 50;
        arrayIndex = index;
    }
}
