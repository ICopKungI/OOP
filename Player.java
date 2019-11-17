/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author GE
 */
public class Player extends Canvas {

    private int x, y, width, height;
    private BufferedImage img;

    public Player(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.width = size;
        this.height = size;
        try {
            img = ImageIO.read(new File("C:\\Users\\User\\Downloads\\OOP\\image\\pig01.gif"));
        } catch (IOException ex) {
        }
    }
    public void draw(Graphics g) {
//        g.setColor(Color.red);
//        g.fillRect(x * width, y * height, width * 10, height * 10); // กำหนดกรอบplayer
//        g.setColor(Color.black);
//        g.fillRect(x * width + 2, y * height + 2, width * 10 - 4, height * 10 - 4); // กำหนดplayer
        g.drawImage(img, x*width, y*height, this);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
