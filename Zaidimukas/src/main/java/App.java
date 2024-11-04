import javax.swing.*;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        new AlienStatisticsWindow();

        int tileSize = 32;
        int rows = 16;
        int columns = 16;

        int width = tileSize * columns;
        int height = tileSize * rows;

        JFrame frame = new JFrame("Space Invaders");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        StartMenu startMenu = new StartMenu(frame, tileSize, rows, columns);
        frame.add(startMenu);
        frame.pack();
        frame.setVisible(true);
    }
}