import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener {
    private JFrame frame;
    private JPanel startMenu;
    private boolean gamePaused = false;
    private Image backgroundImg;
    private boolean isPaused = false; 
    private int playerSpeed = 5; 



    private int greenDestroyedCount = 0, blueDestroyedCount = 0, yellowDestroyedCount = 0;
    private AlienStatisticsWindow statsWindow;
    private Timer gameLoop;

    int tileSize, rows, columns, boardWidth, boardHeight;
    boolean gameOver = false;
    int score = 0;

    // Flags for smooth movement
    private boolean movingLeft = false;
    private boolean movingRight = false;

    Image shipImg;
    ArrayList<Image> alienImgArray;
    Player player;
    ArrayList<Alien> alienArray = new ArrayList<>();
    ArrayList<Bullet> bulletArray = new ArrayList<>();
    int alienVelocityX = 1;

    public SpaceInvaders(int tileSize, int rows, int columns) {
        this.tileSize = tileSize;
        this.rows = rows;
        this.columns = columns;
        this.boardWidth = tileSize * columns;
        this.boardHeight = tileSize * rows;

        setupBoard();
        loadImages();
        initializeGameObjects();

        backgroundImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/wallpaper.jpeg"))).getImage();

        statsWindow = new AlienStatisticsWindow();
        statsWindow.setVisible(true);

        gameLoop = new Timer(1000 / 100, this); // game updates every ~16 ms
        createAliens();
        gameLoop.start();
    }

    public void togglePause(JFrame frame) {
        isPaused = !isPaused;

        if (isPaused) {
            gameLoop.stop();
            showStartMenu(frame);
        } else {
            gameLoop.start();
            frame.getContentPane().removeAll();
            frame.add(this);
            frame.revalidate();
            frame.repaint();
            this.requestFocusInWindow();
        }
    }

    private void showStartMenu(JFrame frame) {
        StartMenu startMenu = new StartMenu(frame, tileSize, rows, columns);
        frame.getContentPane().removeAll();
        frame.add(startMenu);
        frame.revalidate();
        frame.repaint();
    }


    private void setupBoard() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.white);
        setFocusable(true);
        addKeyListener(this);
    }

    private void loadImages() {
        shipImg = loadImage("/ship1.png");
        alienImgArray = new ArrayList<>();
        alienImgArray.add(loadImage("/zalias.png"));
        alienImgArray.add(loadImage("/melynas.png"));
        alienImgArray.add(loadImage("/geltonas.png"));
    }

    private Image loadImage(String path) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(path))).getImage();
    }

    private void initializeGameObjects() {
        player = new Player(tileSize * columns / 2 - tileSize, tileSize * rows - tileSize * 2, tileSize * 2, tileSize, tileSize, shipImg);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {

        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        drawPlayer(g);
        drawAliens(g);
        drawBullets(g);
        drawScore(g);
    }

    private void drawPlayer(Graphics g) {
        g.drawImage(player.img, player.x, player.y, player.width, player.height, null);
    }

    private void drawAliens(Graphics g) {
        alienArray.stream()
                .filter(alien -> alien.alive)
                .forEach(alien -> g.drawImage(alien.img, alien.x, alien.y, alien.width, alien.height, null));
    }

    private void drawBullets(Graphics g) {
        g.setColor(Color.WHITE);
        bulletArray.stream()
                .filter(bullet -> !bullet.used)
                .forEach(bullet -> g.drawRect(bullet.x, bullet.y, bullet.width, bullet.height));
    }

    private void drawScore(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 42));
        g.setColor(Color.blue);
        String displayText = gameOver ? "Game Over: " + score : String.valueOf(score);
        g.drawString(displayText, 10, 35);
    }

    private void move() {
        if (movingLeft) {
            player.x = Math.max(0, player.x - playerSpeed);
        }
        if (movingRight) {
            player.x = Math.min(boardWidth - player.width, player.x + playerSpeed);
        }
        moveAliens();
        moveBullets();
        checkForNewWave();
    }

    private void moveAliens() {
        boolean changeDirection = false;

        for (Alien alien : alienArray) {
            if (alien.alive && (alien.x + alien.width >= boardWidth || alien.x <= 0)) {
                changeDirection = true;
                break;
            }
        }

        if (changeDirection) {
            alienVelocityX *= -1;
            for (Alien alien : alienArray) {
            }
        }

        for (Alien alien : alienArray) {
            if (alien.alive) {
                alien.moveHorizontally(alienVelocityX);
            }
        }

        if (alienArray.stream().noneMatch(alien -> alien.alive)) {
            gameOver = true;
        }
    }


    private void moveBullets() {
        for (Bullet bullet : bulletArray) {
            bullet.move();
            alienArray.stream()
                    .filter(alien -> !bullet.used && alien.alive && detectCollision(bullet, alien))
                    .findFirst()
                    .ifPresent(alien -> handleBulletCollision(bullet, alien));
        }
        bulletArray.removeIf(bullet -> bullet.used || bullet.y < 0);
    }

    private void checkForNewWave() {
        if (alienArray.stream().noneMatch(alien -> alien.alive)) resetAliens();
    }

    private void handleBulletCollision(Bullet bullet, Alien alien) {
        bullet.used = true;
        alien.alive = false;
        score += 100;
        updateAlienDestructionStats(alien.getImg());

    }

    private void updateAlienDestructionStats(Image alienImage) {
        if (alienImage == alienImgArray.get(0)) statsWindow.updateCyanCount(++greenDestroyedCount);
        else if (alienImage == alienImgArray.get(1)) statsWindow.updateMagentaCount(++blueDestroyedCount);
        else if (alienImage == alienImgArray.get(2)) statsWindow.updateYellowCount(++yellowDestroyedCount);
    }

    private void createAliens() {
        Random random = new Random();
        for (int c = 0; c < 6; c++) {
            for (int r = 0; r < 5; r++) {
                int imgIndex = random.nextInt(alienImgArray.size());
                alienArray.add(new Alien(tileSize + c * tileSize * 2, tileSize + r * tileSize, tileSize * 2, tileSize, alienImgArray.get(imgIndex)));
            }
        }
    }

    private void resetAliens() {
        score += alienArray.size() * 100;

        alienArray.clear();
        createAliens();
    }

    private boolean detectCollision(Bullet bullet, Alien alien) {
        return bullet.x + bullet.width > alien.x && bullet.x < alien.x + alien.width
                && bullet.y + bullet.height > alien.y && bullet.y < alien.y + alien.height; //pagooglines sita buda, radau axis-aligned bounding box metoda
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            repaint();
        } else gameLoop.stop();

        if (!isPaused && !gameOver) {
            move();
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            movingLeft = true;
        } else if (key == KeyEvent.VK_RIGHT) {
            movingRight = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent event) {
        int key = event.getKeyCode();

        if (gameOver) {
            restartGame();
        } else if (key == KeyEvent.VK_LEFT) {
            movingLeft = false;
        } else if (key == KeyEvent.VK_RIGHT) {
            movingRight = false;
        } else if (key == KeyEvent.VK_SPACE) {
            fireBullet();
        }
        if (key == KeyEvent.VK_P) {
            togglePause(frame);
        }

    }

    private void fireBullet() {
        bulletArray.add(new Bullet(player.x + (player.width / 2), player.y, tileSize / 8, tileSize / 2, -10));
    }

    private void restartGame() {
        player.x = tileSize * columns / 2 - tileSize;
        bulletArray.clear();
        alienArray.clear();
        gameOver = false;
        score = 0;
        createAliens();
        gameLoop.start();
    }
}
