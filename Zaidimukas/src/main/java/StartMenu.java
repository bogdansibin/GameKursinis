import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

class StartMenu extends JPanel {
    private Image backgroundImage;


    public StartMenu(JFrame frame, int tileSize, int rows, int columns) {
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/wallpaper.jpeg"))).getImage();
        setPreferredSize(new Dimension(tileSize * columns, tileSize * rows));
        setLayout(new GridBagLayout());

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setFocusable(false);

        startButton.addActionListener(e -> {
            SpaceInvaders gamePanel = new SpaceInvaders(tileSize, rows, columns);
            frame.getContentPane().removeAll();
            frame.add(gamePanel);
            frame.revalidate();
            frame.repaint();
            gamePanel.requestFocus();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(startButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
