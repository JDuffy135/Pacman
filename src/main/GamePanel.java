package main;

import javax.swing.JPanel;
import java.awt.*;

import entity.CollisionHandler;
import entity.Pacman;
import entity.WallSpawner;
import item.ItemCollisionHandler;
import item.ItemSpawner;

public class GamePanel extends JPanel implements Runnable
{
    //SCREEN SETTINGS
    /* original game screen resolution: 224x288 pixels */
    final int originalTileSize = 8; /* 8x8 tiles in original game (pacman and ghosts take up 4 tiles because they are 16x16) */
    final int screenScale = 2; /* scale tiles by factor of 2 */
    public final int displayedTileSize = originalTileSize * screenScale; /* actual tile size displayed (16x16) */
    final int horizontalTileCount = 28;
    final int verticalTileCount = 36;
    final int screenWidth = displayedTileSize * horizontalTileCount; /* 448 pixels */
    final int screenHeight = displayedTileSize * verticalTileCount; /* 576 pixels */

    //SYSTEM
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public UI ui = new UI(this);
    public CollisionHandler cHandler = new CollisionHandler(this);
    public ItemCollisionHandler icHandler = new ItemCollisionHandler(this);
    WallSpawner wallSpawner = new WallSpawner(this);
    ItemSpawner itemSpawner = new ItemSpawner(this);

    //FPS
    final public static int FPS = 60;

    //SPRITES
    Pacman pacman = new Pacman(this, keyH);

    //GAME STATE
    public int gameState;
    public final int menuState = 0;
    public final int playState = 1;


    //TEMPORARY VARIABLES FOR TESTING
    boolean flag = false;


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
        gameThread.start(); /* calls run method */
    }

    @Override
    public void run()
    {
        /* setup for game loop */
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        //MAIN GAME LOOP
        while (gameThread != null)
        {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1)
            {
                /* update sprite values */
                update();

                /* calls paintComponent to update the screen based on updated sprite values */
                repaint();

                /* resets game loop at end of each execution */
                delta--;
                drawCount++;
            }

            /* display FPS */
            if (timer >= 1000000000)
            {
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    /* updates sprites and values */
    public void update()
    {
        //CHECKS IF ALL PELLETS EATEN
        icHandler.checkIfWon();

        //DEALS WITH FRUIT SPAWNING
        itemSpawner.fruitCheck();
        if (ItemSpawner.fruitPresent == true)
        {
            itemSpawner.updateFruitTimer();
        }

        //UPDATES PACMAN ENTITY AND DEALS WITH PLAYER COLLISIONS
        pacman.update();
    }

    @Override
    public void paintComponent(Graphics g) /* updates screen and images */
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; /* converts g to a Graphics2D object so that we can use 2D graphics methods */

        //DEBUG
        long drawStart = 0;
        drawStart = System.nanoTime();


        //MENU SCREEN
        //...


        //GAME BOARD AND PELLETS/FRUITS
        ui.draw(g2, icHandler);
        itemSpawner.drawItems(g2);
        //wallSpawner.paintWalls(g2); //for debugging


        //SINGLE EXECUTION COMMANDS
        if (flag == false)
        {
            /* using a flag just because I want this to run once - will eventually fix with game states */
            wallSpawner.createWalls();
            itemSpawner.createPellets();
        }
        flag = true;


        //PLAYER
        pacman.draw(g2);


        //GHOSTS
        //...


        //DEBUG CONTINUED
        long drawEnd = System.nanoTime();
        long timePassed = drawEnd - drawStart;
        System.out.println("Draw Time: " + timePassed);

        g2.dispose();
    }
}
