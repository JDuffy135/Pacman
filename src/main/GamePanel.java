package main;

import javax.swing.JPanel;
import java.awt.*;
import entity.*;
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
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public UI ui = new UI(this);
    public CollisionHandler cHandler = new CollisionHandler(this);
    public ItemCollisionHandler icHandler = new ItemCollisionHandler(this);
    WallSpawner wallSpawner = new WallSpawner(this);
    ItemSpawner itemSpawner = new ItemSpawner(this);

    //FPS
    final public static int FPS = 60;

    //SPRITES
    public Pacman pacman = new Pacman(this, keyH);
    public Blinky blinky = new Blinky(this);
    public Pinky pinky = new Pinky(this);
    public Inky inky = new Inky(this);
    public Clyde clyde = new Clyde(this);

    //GAME STATE
    public static int gameState = 1; /* 0 BY DEFAULT */
    public final int START_STATE = 0; /* startup sequence */
    public final int PLAY_STATE = 1; /* gameplay state */
    public final int EATGHOST_STATE = 2; /* game pauses temporarily when a ghost is eaten */
    public final int LOSELIFE_STATE = 3; /* game pauses temporarily and sprite positions are reset */
    public final int BOARDRESET_STATE = 4; /* board reset sequence */
    public final int GAMEOVER_STATE = 5; /* gameover screen */


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

    //MAIN UPDATE METHOD: updates game values in game loop
    public void update()
    {
        switch (gameState)
        {
            case START_STATE:
                //...
                break;
            case PLAY_STATE:
                updatePlayState();
                break;
            case EATGHOST_STATE:
                //...
                break;
                //TO BE CONTINUED
        }
    }

    /* called by gameloop "update()" method when gameState == START_STATE */
    public void updateStartState()
    {

    }

    /* called by gameloop "update()" method when gameState == PLAY_STATE */
    public void updatePlayState()
    {
        //LEVEL TIMER
        Entity.levelTimer++;

        //CHECKS IF ALL PELLETS EATEN
        icHandler.checkIfWon();

        //DEALS WITH FRUIT SPAWNING
        itemSpawner.fruitCheck();
        if (itemSpawner.fruitPresent == true)
        {
            itemSpawner.updateFruitTimer();
        }

        //UPDATES PACMAN & HANDLES WALL AND GHOST COLLISIONS
        pacman.update();

        //UPDATES GHOSTS
        if (Entity.levelTimer >= 180) /* timer delay currently here for debugging */
        {
            blinky.update();
            pinky.update();
            inky.update();
            clyde.update();
        }
    }

    //MAIN DRAW METHOD: paints objects on screen
    @Override
    public void paintComponent(Graphics g) /* updates screen and images */
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; /* converts g to a Graphics2D object so that we can use 2D graphics methods */

        //DEBUG
//        long drawStart = 0;
//        drawStart = System.nanoTime();


        //MENU SCREEN
        //...


        //BACKGROUND, TEXT, GAME BOARD AND PELLETS/FRUITS
        ui.draw(g2, icHandler);
        itemSpawner.drawItems(g2);
        //wallSpawner.paintWalls(g2); /* for debugging */


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
        blinky.draw(g2);
        pinky.draw(g2);
        inky.draw(g2);
        clyde.draw(g2);


        //DEBUG CONTINUED
//        long drawEnd = System.nanoTime();
//        long timePassed = drawEnd - drawStart;
//        System.out.println("Draw Time: " + timePassed);

        g2.dispose();
    }
}
