package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
    public boolean upPressed, downPressed, leftPressed, rightPressed = false;
    public static boolean keyPressed = false;

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();
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

    @Override
    public void keyReleased(KeyEvent e)
    {
        /* NOTE: originally had push-and-hold movement mechanics */

//        int code = e.getKeyCode();
//
//        if (code == KeyEvent.VK_W) //up
//        {
//            upPressed = false;
//        }
//        if (code == KeyEvent.VK_S) //down
//        {
//            downPressed = false;
//        }
//        if (code == KeyEvent.VK_A) //left
//        {
//            leftPressed = false;
//        }
//        if (code == KeyEvent.VK_D) //right
//        {
//            rightPressed = false;
//        }
    }
}
