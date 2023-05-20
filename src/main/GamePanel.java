package main;

import javax.swing.JPanel;
import java.awt.*;
import entity.Pacman;

public class GamePanel extends JPanel implements Runnable
{
    //SCREEN SETTINGS
    //original game screen resolution: 224x288 pixels
    final int originalTileSize = 8; //8x8 tiles in original game (pacman and ghosts take up 4 tiles because they are 16x16)
    final int screenScale = 2; //scale tiles by factor of 2
    public final int displayedTileSize = originalTileSize * screenScale; //actual tile size displayed (16x16)
    final int horizontalTileCount = 28;
    final int verticalTileCount = 36;
    final int screenWidth = displayedTileSize * horizontalTileCount; //448 pixels
    final int screenHeight = displayedTileSize * verticalTileCount; //576 pixels

    //initialization
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Pacman pacman = new Pacman(this, keyH);

    //global constants
    final int FPS = 60;

    //TEMPORARY/TESTING VARIABLES
    //...


    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start(); //calls run method
    }

    @Override
    public void run()
    {
        //setup for game loop
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        //main game loop
        while (gameThread != null)
        {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1)
            {
                //update sprite values
                update();

                ////calls paintComponent to update the screen based on updated sprite values
                repaint();


                //resets game loop at end of each execution
                delta--;
            }
        }
    }


    public void update()
    {
        pacman.updatePosition();
    }

    @Override
    public void paintComponent(Graphics g) //updates screen
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; //convert to Graphics2D object so that we can use 2D graphics methods

        //...
        pacman.draw(g2);

        g2.dispose();
    }
}
