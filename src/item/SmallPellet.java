package item;

import java.awt.*;
import main.GamePanel;

public class SmallPellet extends Item
{
    GamePanel gp;

    public SmallPellet(GamePanel gp, int xPos, int yPos, int row, int col)
    {
        this.gp = gp;
        x = xPos;
        y = yPos;
        hitbox = new Rectangle(xPos, yPos, spSize, spSize);
        points = 10;
        image = smallPelletImage;
        arrayPositionX = row;
        arrayPositionY = col;
    }
}
