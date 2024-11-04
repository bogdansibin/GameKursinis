import java.awt.Image;

public class Block {
    int x;
    int y;
    int width;
    int height;
    Image img;
    boolean alive = true; // used for aliens
    boolean used = false; // used for bullets

    public Block(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }
}
