public class Bullet {
    int x, y, width, height, velocityY;
    boolean used;

    public Bullet(int x, int y, int width, int height, int velocityY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocityY = velocityY;
        this.used = false;
    }

    public void move() {
        y += velocityY;
    }
}
