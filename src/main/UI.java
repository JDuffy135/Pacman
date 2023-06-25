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

    //SCORE POSITION
    int highscoreX = 219;
    int scoreX = 60;

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

        /* main text */
        g2.setFont(primaryFont.deriveFont(18F));
        g2.setColor(Color.white);
        g2.drawString("HIGH SCORE", 170, 18);

        /* 1UP text */
        if (gp.itemSpawner.currentBigPelletImage != null)
        {
            g2.setFont(primaryFont.deriveFont(18F));
            g2.setColor(Color.white);
            g2.drawString("1UP", 48, 18);
        }

        /* highscore positioning */
        if (icHandler.highscore >= 1000000)
        {
            highscoreX = 190;
        }
        else if (icHandler.highscore >= 100000)
        {
            highscoreX = 194;
        }
        else if (icHandler.highscore >= 10000)
        {
            highscoreX = 200;
        }
        else if (icHandler.highscore >= 1000)
        {
            highscoreX = 204;
        }
        else if (icHandler.highscore >= 100)
        {
            highscoreX = 209;
        }
        else if (icHandler.highscore >= 10)
        {
            highscoreX = 215;
        }
        else
        {
            highscoreX = 219;
        }


        /* score positioning */
        if (icHandler.score >= 1000000)
        {
            scoreX = 28;
        }
        else if (icHandler.score >= 100000)
        {
            scoreX = 34;
        }
        else if (icHandler.score >= 10000)
        {
            scoreX = 39;
        }
        else if (icHandler.score >= 1000)
        {
            scoreX = 44;
        }
        else if (icHandler.score >= 100)
        {
            scoreX = 49;
        }
        else if (icHandler.score >= 10)
        {
            scoreX = 55;
        }
        else
        {
            scoreX = 60;
        }


        /* score and highscore visual */
        g2.setFont(primaryFont.deriveFont(16F));
        if (icHandler.score >= icHandler.highscore)
        {
            g2.drawString(String.valueOf(icHandler.score), highscoreX, 35);
        }
        else
        {
            g2.drawString(String.valueOf(icHandler.highscore), highscoreX, 35);
        }
        g2.drawString(String.valueOf(icHandler.score), scoreX, 35);


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
