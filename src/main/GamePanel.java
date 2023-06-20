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
    public static int gameState = 0; /* 0 BY DEFAULT */
    public final int START_STATE = 0; /* startup sequence */
    public final int PLAY_STATE = 1; /* gameplay state */
    public final int EATGHOST_STATE = 2; /* game pauses temporarily when a ghost is eaten */
    public final int LOSELIFE_STATE = 3; /* game pauses temporarily and sprite positions are reset */
    public final int WIN_STATE = 4; /* board reset sequence */
    public final int GAMEOVER_STATE = 5; /* gameover screen */
    boolean flag = false; /* used for methods that only need to be executed once */
    public int gameStateTimer = 0;


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

    /* use this whenever changing game state */
    public void changeGameState(int newState)
    {
        gameStateTimer = 0;

        switch (newState)
        {
            case START_STATE:
                flag = false;
                Entity.levelTimer = 0;
                break;
            case PLAY_STATE:
                /* resets level timer and flag value for single execution methods */
                if (gameState == START_STATE)
                {
                    flag = false;
                    Entity.levelTimer = 0;
                }
                break;
            case EATGHOST_STATE:
                //...
                break;
            case LOSELIFE_STATE:
                //...
                break;
            case WIN_STATE:
                //...
                break;
            case GAMEOVER_STATE:
                //...
                break;
        }

        gameState = newState;
    }

    //MAIN UPDATE METHOD: updates game values in game loop
    public void update()
    {
        System.out.println(Entity.levelTimer);
        gameStateTimer++;
        switch (gameState)
        {
            case START_STATE:
                updateStartState();
                break;
            case PLAY_STATE:
                updatePlayState();
                break;
            case EATGHOST_STATE:
                updateEatGhostState();
                break;
            case LOSELIFE_STATE:
                //...
                break;
            case WIN_STATE:
                updateWinState();
                break;
            case GAMEOVER_STATE:
                //...
                break;
        }
    }

    /* called by gameloop "update()" method when gameState == START_STATE */
    public void updateStartState()
    {
        /* single execution commands for game setup */
        if (gameStateTimer == 1)
        {
            //WALLS AND PELLETS
            if (Entity.walls.size() < 1)
            {
                wallSpawner.createWalls();
            }
            itemSpawner.createPellets();

            //RESET PACMAN POSITION
            pacman.setDefaultValues();

            //RESET GHOSTS
            blinky.setDefaultValues();
            pinky.setDefaultValues();
            inky.setDefaultValues();
            clyde.setDefaultValues();

//            System.out.println("THIS IS FOR TESTING TO SEE HOW MANY TIMES THIS EXECUTES " + Entity.walls.size());
        }

        //CHANGES GAME STATE TO PLAY STATE
        if (gameStateTimer >= 270)
        {
            changeGameState(PLAY_STATE);
        }
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
        blinky.update();
        pinky.update();
        inky.update();
        clyde.update();
    }

    public void updateEatGhostState()
    {
        //UPDATES PACMAN & HANDLES WALL AND GHOST COLLISIONS
        pacman.update();

        //UPDATES GHOSTS
        blinky.update();
        pinky.update();
        inky.update();
        clyde.update();

        if (gameStateTimer >= 40)
        {
            changeGameState(PLAY_STATE);
        }
    }

    public void updateLoseLifeState()
    {

    }

    public void updateWinState()
    {
        System.out.println("LEVEL COMPLETE!");
        System.out.println("NEW LEVEL: " + Entity.currentLevel + ", PELLETS EATEN: " + icHandler.pelletsEaten);

        //FRIGHTENED MODE RESET
        Entity.frightenedTimer = 0;
        Entity.frightenedPointBonus = 200;

        //...
        /* need to make the gameboard blink */

        if (gameStateTimer >= 360)
        {
            changeGameState(START_STATE);
        }
    }

    //MAIN DRAW METHOD: paints objects on screen
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; /* converts g to a Graphics2D object so that we can use 2D graphics methods */

        //DEBUG
//        long drawStart = 0;
//        drawStart = System.nanoTime();

//        switch (gameState)
//        {
//            case START_STATE:
//                //...
//                break;
//            case PLAY_STATE:
//                //BACKGROUND, TEXT, GAME BOARD AND PELLETS/FRUITS
//                ui.draw(g2, icHandler);
//                itemSpawner.drawItems(g2);
////                wallSpawner.paintWalls(g2); /* for debugging */
//                //PLAYER
//                pacman.draw(g2);
//                //GHOSTS
//                blinky.draw(g2);
//                pinky.draw(g2);
//                inky.draw(g2);
//                clyde.draw(g2);
//                break;
//            case EATGHOST_STATE:
//                //...
//                break;
//            //TO BE CONTINUED
//        }

        //BACKGROUND, TEXT, GAME BOARD AND PELLETS/FRUITS
        ui.draw(g2, icHandler);
        itemSpawner.drawItems(g2);
//        wallSpawner.paintWalls(g2); /* for debugging */

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
