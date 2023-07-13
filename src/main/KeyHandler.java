package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;

public class KeyHandler implements KeyListener
{
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed = false;
    public static boolean keyPressed = false;

    public KeyHandler(GamePanel gp)
    {
       this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();

        if (gp.gameState == gp.GAMEOVER_STATE)
        {
            keyPressed = true;
            if (code == KeyEvent.VK_SPACE)
            {
                gp.changeGameState(gp.START_STATE);
            }
        }

        /* pacman movement during play state and eat ghost state */
        if (gp.gameState == gp.PLAY_STATE || gp.gameState == gp.EATGHOST_STATE || gp.gameState == gp.START_STATE)
        {
            keyPressed = true;
            if (code == KeyEvent.VK_W) /* up */
            {
                downPressed = false;
                leftPressed = false;
                rightPressed = false;
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) /* down */
            {
                upPressed = false;
                leftPressed = false;
                rightPressed = false;
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) /* left */
            {
                downPressed = false;
                upPressed = false;
                rightPressed = false;
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) /* right */
            {
                downPressed = false;
                upPressed = false;
                leftPressed = false;
                rightPressed = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}
