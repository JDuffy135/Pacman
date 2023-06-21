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
    BufferedImage temp = null;
    BufferedImage lifeMarker;

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

        getImages();
    }

    public void getImages()
    {
        bg = null;
        currentbg = null;
        /* NOTE TO SELF: use game states to determine which background is set up */
        try
        {
            bg = ImageIO.read(getClass().getResourceAsStream("/misc/GameScreenBlink.png"));
            currentbg = ImageIO.read(getClass().getResourceAsStream("/misc/GameScreen.png"));
            lifeMarker = ImageIO.read(getClass().getResourceAsStream("/pacman/PacmanRightOpen.png"));
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void backgroundBlink()
    {
        temp = currentbg;
        currentbg = bg;
        bg = temp;
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

        /* start text */
        if (gp.gameState == gp.START_STATE && gp.gameStateTimer >= 2)
        {
            g2.setColor(Color.yellow);
            g2.setFont(primaryFont.deriveFont(20F));
            g2.drawString("READY!", 192, 328);
        }

        /* life markers */
        if (gp.gameState != gp.GAMEOVER_STATE)
        {
            if (Entity.lives >= 3)
            {
                g2.drawImage(lifeMarker, 42, 544, 24, 24, null);
            }
            if (Entity.lives >= 2)
            {
                g2.drawImage(lifeMarker, 12, 544, 24, 24, null);
            }
        }

        /* gameover text */
        if (gp.gameState == gp.GAMEOVER_STATE)
        {
            g2.setColor(Color.red);
            g2.setFont(primaryFont.deriveFont(20F));
            g2.drawString("GAME   OVER", 160, 328);
            g2.setFont(primaryFont.deriveFont(12F));
            g2.drawString("press space to restart", 148, 420);
        }
    }
}
