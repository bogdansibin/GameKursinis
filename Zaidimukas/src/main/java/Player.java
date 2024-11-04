import java.awt.Image;

public class Player {
    int x, y, width, height, velocityX;
    Image img;

    public Player(int x, int y, int width, int height, int velocityX, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocityX = velocityX;
        this.img = img;
    }

    public void moveLeft(int boundary) {
        if (x - velocityX >= 0) {
            x -= velocityX;
        }
    }

    public void moveRight(int boundary) {
        if (x + velocityX + width <= boundary) {
            x += velocityX;
        }
    }
}
