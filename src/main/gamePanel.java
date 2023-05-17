package main;

import javax.swing.JPanel;
import java.awt.*;

public class gamePanel extends JPanel implements Runnable
{
    //SCREEN SETTINGS
    //original game screen resolution: 224x288 pixels
    final int originalTileSize = 8; // 8x8 tiles in original game (pacman and ghosts take up 4 tiles because they are 16x16)
    final int screenScale = 2; //scale tiles by factor of 2
    final int displayedTileSize = originalTileSize * screenScale; //actual tile size displayed (16x16)
    final int horizontalTileCount = 28;
    final int verticalTileCount = 36;
    final int screenWidth = displayedTileSize * horizontalTileCount; //448 pixels
    final int screenHeight = displayedTileSize * verticalTileCount; //576 pixels

    Thread gameThread;

    public gamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start(); //calls run method
    }

    @Override
    //game loop
    public void run()
    {

    }

}
