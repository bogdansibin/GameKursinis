import java.awt.Image;

public class Alien {
    int x, y, width, height;
    boolean alive;
    Image img;

    public Alien(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.alive = true;
        this.img = img;
    }

    public void moveHorizontally(int velocityX) {
        x += velocityX;
    }

    public Image getImg() {
        return img; //reikalingas, kad suskaiciuoti kokie ateiviai sunaikinti
    }
}
