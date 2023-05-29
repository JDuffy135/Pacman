package entity;

import main.GamePanel;
import java.awt.*;

public class Wall extends Entity
{
    GamePanel gp;

    public Wall(GamePanel gp, int xPos, int yPos)
    {
        this.gp = gp;
        x = xPos;
        y = yPos;
        hitbox = new Rectangle(xPos, yPos, 12, 10);
    }
}
