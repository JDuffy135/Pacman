package main;

import javax.swing.JFrame;

public class Main
{
    public static void main(String[] args)
    {
        //window setup
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("PACMAN REMAKE");

        GamePanel panel = new GamePanel();
        window.add(panel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //starts the game loop
        panel.startGameThread();
    }
}