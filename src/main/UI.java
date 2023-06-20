package main;

import item.ItemCollisionHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import entity.Entity;

//MAIN CLASS FOR PAINTING BACKGROUNDS AND TEXT
public class UI
{
    GamePanel gp;
    Font primaryFont;
    BufferedImage bg;
    BufferedImage currentbg;

    public UI(GamePanel gp)
    {
        this.gp = gp;

        /* importing font */
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
        currentbg = null;
        /* NOTE TO SELF: use game states to determine which background is set up */
        try
        {
            bg = ImageIO.read(getClass().getResourceAsStream("/misc/GameScreen.png"));
            currentbg = bg;
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, ItemCollisionHandler icHandler)
    {
        /* background */
        g2.drawImage(currentbg, 0, -8, 448, 576, null);

        /* text and score */
        g2.setFont(primaryFont.deriveFont(20F));
        g2.setColor(Color.white);
        g2.drawString("GAME SCORE", 162, 28);
        g2.setFont(primaryFont.deriveFont(16F));
        g2.drawString(String.valueOf(icHandler.score), 320, 27);
    }
}
