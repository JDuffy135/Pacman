package entity;

import main.GamePanel;
import java.awt.*;

public class Wall extends Entity
{
    GamePanel gp;

    //Wall object constructor - pretty straight forward not gonna lie
    public Wall(GamePanel gp, int xPos, int yPos)
    {
        this.gp = gp;
        x = xPos;
        y = yPos;
        hitbox = new Rectangle(xPos, yPos, 14, 14);
    }
}
