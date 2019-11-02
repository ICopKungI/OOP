/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.ArrayList;

/**
 *
 * @author GE
 */
public class Run extends JPanel implements Runnable {

    private Thread th;
    private boolean running = false;//เก็บสถานะการทำงาน
    public static int width = 800;
    public static int height = 800;
    //กำหนดตัวplayer
    private Player p;
    private ArrayList<Player> playerArray;
    private int x = 0, y = 0;
    private int size = 1; // กำหนดยาวขนาดplayer(ยาวงู/*/)

    //กำหนดการเคลื่อนที่ของ player
    private  boolean right = false, left = false, up =false, down = true;
    private int count = 0;
    public Run() {
        setPreferredSize(new Dimension(width, height)); // กำหนกขนาดหน้าจอ
        playerArray = new ArrayList();
        th = new Thread(this);
        th.start();
    }

    public void run() {
        running = true;
        while (running) {
//            System.out.println("OK"); //debug ว่า runThreadไหม
            tick();
            repaint();
        }
    }

//    ควบคุมการเคลื่อนที่ของวัตถุหน้าจอเกม / กำหนดค่าเริ่มต้น
    private void tick() {
        if (playerArray.size() == 0) {
            p = new Player(x, y, 10);
            playerArray.add(p);
        }
        count++;
        if(count > 350000){
            if(right){
                x--;
            }
            if(left){
                x++;
            }
            if(up){
                y--;
            }
            if(down){
                y++;
            }
            count = 0;
            p = new Player(x, y, 10);
            playerArray.add(p);
            if(playerArray.size() > size){
                playerArray.remove(0);
            }
        }
    }

//  ประมวณผลgraphics
    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, width, height);
        g.setColor(Color.black);
        for (int i = 0; i < width / 100; i++) {
            g.drawLine(i * 100, 0, i * 100, height); //วาดเส้นแกน Y
        }
        for (int i = 0; i < height / 100; i++) {
            g.drawLine(0, i * 100, width, i * 100); //วาดเส้นแกน X
        }
        for (int i = 0; i < playerArray.size(); i++) {
            playerArray.get(i).draw(g);
        }
    }

}
