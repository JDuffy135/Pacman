package main;

import javax.swing.JFrame;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main
{
    public static void main(String[] args)
    {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("PACMAN REMAKE");

        gamePanel panel = new gamePanel();
        window.add(panel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}