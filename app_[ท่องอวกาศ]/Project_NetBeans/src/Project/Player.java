package Project;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Canvas {

    private int x, y, width, height;
    private final BufferedImage img;

    public Player(int x, int y, int size) throws IOException {
        this.x = x;
        this.y = y;
        this.width = size;
        this.height = size;
        img = ImageIO.read(getClass().getResource("/image/pig01.gif"));
    }

    public void draw(Graphics g) {
        g.drawImage(img, x * width, y * height, this);
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
