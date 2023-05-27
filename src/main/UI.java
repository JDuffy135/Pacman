package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

//MAIN CLASS FOR PAINTING BACKGROUNDS, TEXT, AND PELLETS
public class UI
{
    GamePanel gp;
    Font primaryFont;
    BufferedImage bg;

    public UI(GamePanel gp)
    {
        this.gp = gp;

        //importing "crackman" font
        try
        {
            InputStream is = getClass().getResourceAsStream("/font/PixeloidSans-mLxMm.ttf");
            primaryFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            primaryFont = new Font("Impact", Font.PLAIN, 20);
            e.printStackTrace();
        } catch (IOException e) {
            primaryFont = new Font("Impact", Font.PLAIN, 20);
            e.printStackTrace();
        }

        getBackgroundImage();
    }

    public void getBackgroundImage()
    {
        bg = null;
        //NOTE TO SELF: use game states to determine which background is set up
        try
        {
            bg = ImageIO.read(getClass().getResourceAsStream("/misc/GameScreen.png"));
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2)
    {
        g2.drawImage(bg, 0, -8, 448, 576, null);
        g2.setFont(primaryFont.deriveFont(20F));
        g2.setColor(Color.white);
        g2.drawString("GAME SCORE", 164, 28);
    }
}
