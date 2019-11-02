/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author GE
 */
public class Player {

    private int x, y, width, height;

    public Player(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.width = size;
        this.height = size;
    }
    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fillRect(x*width, y*height, width*10, height*10); // กำหนดกรอบplayer
        g.setColor(Color.black);
        g.fillRect(x*width+2, y*height+2, width*10-4, height*10-4); //กำหนดplayer
    }
}
