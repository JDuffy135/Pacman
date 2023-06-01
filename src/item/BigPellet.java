package item;

import java.awt.*;
import main.GamePanel;

public class BigPellet extends Item
{
    GamePanel gp;

    public BigPellet(GamePanel gp, int xPos, int yPos, int row, int col)
    {
        this.gp = gp;
        x = xPos;
        y = yPos;
        hitbox = new Rectangle(xPos, yPos, bpSize, bpSize);
        points = 50;
        image = bigPelletImage;
        arrayPositionX = row;
        arrayPositionY = col;
    }
}
