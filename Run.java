/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GE
 */
public class Run extends JPanel implements Runnable {

    private Thread th;
    private boolean running = false;// เก็บสถานะการทำงาน
    public static int width = 800;
    public static int height = 800;
    // กำหนดตัวplayer
    private Player p;
    private ArrayList<Player> playerArray;
    private int x = 35, y = 35;
    private int size = 1; // กำหนดยาวขนาดplayer(ยาวงู/*/)
    private boolean alive = false;

    // กำหนดการเคลื่อนที่ของ player
    private boolean right = false, left = false, up = false, down = false;
    private int countp = 0; // นับเวลาวิ่งplayer
    private int countm = 0; // นับเวลาวิ่งMonster
    private int pspeedmove = 400000; // ความเร็วในการเดิน
    // กำหนดMonster
    private Monster m;
    private int x0, x1, y2, y3;
    private int mspeedmove = 500000; // ความเร็วในการเดิน
    // 0-Right 1-Left 2-Top 3-Down
    private ArrayList<Monster> monsterArray;
    private ArrayList<Monster> monsterArray2;

    public Run() {
        setPreferredSize(new Dimension(width, height)); // กำหนกขนาดหน้าจอ
        playerArray = new ArrayList();
        monsterArray = new ArrayList();
        monsterArray2 = new ArrayList();
        addKeyListener(new KeyInner());
        setFocusable(true); // ทำให้สามารถใช้งานkeyboardได้
    }

    public void startGame() {
        pspeedmove = 400000;
        mspeedmove = 500000;
        right = false;
        left = false;
        up = false;
        down = false;
        x = 35;
        y = 35;
        alive = true;
        running = true;
        th = new Thread(this);
        th.start();
    }

    // th.start มาเรียกrun()
    public void run() {
        // running = true; //สั่งให้เดินเกม
        CountTime.Count(true);
        while (running) {
            // try {
            // // System.out.println("OK"); //debug ว่า runThreadไหม
            // Thread.sleep(10);
            // } catch (InterruptedException ex) {
            // Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            // }
            tick();
            repaint();
            // System.out.println(CountTime.i);
        }
    }

    public void stop() {
        CountTime.Count(false);
        System.out.println(CountTime.i);
        playerArray.remove(0);
        for (int i = 0; i < 4; i++) {
            int num = 0;
            if (monsterArray.size() > 0) {
                monsterArray.remove(0);
            }
            if (monsterArray2.size() > 0) {
                monsterArray2.remove(0);
            }
        }
        // alive = false;
        running = false;
        // try {
        // th.join();
        // } catch (Exception ex) {
        // System.out.println(ex.toString());
        // }
    }

    // ควบคุมการเคลื่อนที่ของวัตถุหน้าจอเกม / กำหนดค่าเริ่มต้น
    private void tick() {
        if (playerArray.size() == 0) {
            p = new Player(x, y, 5);
            playerArray.add(p);
        }
        if (monsterArray.size() == 0) {
            // Right
            int xmonster = 80;
            int ymonster = new Random().nextInt(70);
            m = new Monster(xmonster, ymonster, 10);
            monsterArray.add(m);
            // Left
            xmonster = -10;
            ymonster = new Random().nextInt(70);
            m = new Monster(xmonster, ymonster, 10);
            monsterArray.add(m);
            // Top
            xmonster = new Random().nextInt(70);
            ymonster = -10;
            m = new Monster(xmonster, ymonster, 10);
            monsterArray.add(m);
            // Down
            xmonster = new Random().nextInt(70);
            ymonster = 80;
            m = new Monster(xmonster, ymonster, 10);
            monsterArray.add(m);
        }
        // checkการชน
        for (int i = 0; i < monsterArray.size(); i++) {
            int mx = monsterArray.get(i).getX();
            int my = monsterArray.get(i).getY();
            if ((((y - 10) < my) && (my < (y + 10))) && (((x - 10) < mx) && (mx < (x + 10)))) { // ชนที่แท้ทรู by Boy
                                                                                                // 345
                System.out.println("Boom!!!!");
                CountTime.Count(false);
                stop();
            }
        }
        // for (int i = 0; i < monsterArray2.size(); i++) {
        // int mx2 = monsterArray2.get(i).getX();
        // int my2 = monsterArray2.get(i).getY();
        // if ((((y - 10) < my2) && (my2 < (y + 10))) && (((x - 10) < mx2) && (mx2 < (x
        // + 10)))) { //ชนที่แท้ทรู by Boy 345
        // System.out.println("Boom!!!!");
        // CountTime.Count(false);
        // stop();
        // }
        // }
        countp++;
        countm++;
        if (countp > pspeedmove) { // แถม ทำให้ Player ไม่เกินขอบหน้าจอ by Boy 345
            if (right && x < 70) {
                x++;
            }
            if (left && x > 0) {
                x--;
            }
            if (up && y > 0) {
                y--;
            }
            if (down && y < 70) {
                y++;
            }
            countp = 0;
            p = new Player(x, y, 10);
            playerArray.add(p);
            if (playerArray.size() > size) {
                playerArray.remove(0);
            }
        }
        // 0-Right 1-Left 2-Top 3-Down
        if (countm > mspeedmove) {
            x0 = monsterArray.get(0).getX();
            x0--;
            monsterArray.get(0).setX(x0);
            x1 = monsterArray.get(1).getX();
            x1++;
            monsterArray.get(1).setX(x1);
            y2 = monsterArray.get(2).getY();
            y2++;
            monsterArray.get(2).setY(y2);
            y3 = monsterArray.get(3).getY();
            y3--;
            monsterArray.get(3).setY(y3);
            countm = 0;
            if (monsterArray.get(0).getX() <= -15) {
                monsterArray.remove(0);
                monsterArray.remove(0);
                monsterArray.remove(0);
                monsterArray.remove(0);
                pspeedmove -= 25000;
                mspeedmove -= 25000;
                System.out.println("Player" + pspeedmove);
                System.out.println("Monster" + mspeedmove);
            }
            // if (monsterArray2.size() > 0) {
            // x0 = monsterArray2.get(0).getX();
            // x0--;
            // monsterArray2.get(0).setX(x0);
            // x1 = monsterArray2.get(1).getX();
            // x1++;
            // monsterArray2.get(1).setX(x1);
            // y2 = monsterArray2.get(2).getY();
            // y2++;
            // monsterArray2.get(2).setY(y2);
            // y3 = monsterArray2.get(3).getY();
            // y3--;
            // monsterArray2.get(3).setY(y3);
            // if (monsterArray2.get(0).getX() <= -15) {
            // monsterArray2.remove(0);
            // monsterArray2.remove(0);
            // monsterArray2.remove(0);
            // monsterArray2.remove(0);
            // }
            // } else if (monsterArray.get(0).getX() <= 35 && monsterArray2.size() == 0) {
            // int xmonster = 80;
            // int ymonster = new Random().nextInt(70);
            // m = new Monster(xmonster, ymonster, 10);
            // monsterArray2.add(m);
            // //Left
            // xmonster = -10;
            // ymonster = new Random().nextInt(70);
            // m = new Monster(xmonster, ymonster, 10);
            // monsterArray2.add(m);
            // //Top
            // xmonster = new Random().nextInt(70);
            // ymonster = -10;
            // m = new Monster(xmonster, ymonster, 10);
            // monsterArray2.add(m);
            // //Down
            // xmonster = new Random().nextInt(70);
            // ymonster = 80;
            // m = new Monster(xmonster, ymonster, 10);
            // monsterArray2.add(m);
            // }
        }
    }

    // ประมวณผลgraphics
    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, width, height);
        g.setColor(Color.black);
        // หน้าMenu
        if (!running && !alive) {
            g.setColor(Color.RED);
            g.setFont(new Font("Tahoma", Font.BOLD, 40));
            g.drawString("หลบๆๆ", width / 2 - 80, height / 2);
            g.setColor(Color.black);
            g.setFont(new Font("Tahoma", Font.BOLD, 20));
            g.drawString("กด SpaceBar เพื่อเริ่ม", width / 2 - 100, height / 2 + 30);
        } else if (!running && alive) {
            g.setColor(Color.RED);
            g.setFont(new Font("Tahoma", Font.BOLD, 40));
            g.drawString("เวลาที่ได้: " + CountTime.i + " วินาที", width / 2 - 80, height / 2);
            g.setColor(Color.black);
            g.setFont(new Font("Tahoma", Font.BOLD, 20));
            g.drawString("กด SpaceBar เพื่อเริ่มเกมใหม่", width / 2 - 100, height / 2 + 30);

        } else {
            // for (int i = 0; i < width / 100; i++) {
            // g.drawLine(i * 100, 0, i * 100, height); //วาดเส้นแกน Y
            // }
            // for (int i = 0; i < height / 100; i++) {
            // g.drawLine(0, i * 100, width, i * 100); //วาดเส้นแกน X
            // }
            // palyer
            for (int i = 0; i < playerArray.size(); i++) {
                playerArray.get(i).draw(g);
            }
            // Monster
            for (int i = 0; i < monsterArray.size(); i++) {
                monsterArray.get(i).draw(g);
            }
            for (int i = 0; i < monsterArray2.size(); i++) {
                monsterArray2.get(i).draw(g);
            }
        }
    }

    class KeyInner implements KeyListener {

        @Override
        public void keyTyped(KeyEvent ke) {
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            int key = ke.getKeyCode();
            if (key == KeyEvent.VK_RIGHT) {
                up = false;
                down = false;
                left = false;
                right = true;
            }
            if (key == KeyEvent.VK_LEFT) {
                up = false;
                down = false;
                left = true;
                right = false;
            }
            if (key == KeyEvent.VK_UP) {
                up = true;
                down = false;
                left = false;
                right = false;
            }
            if (key == KeyEvent.VK_DOWN) {
                up = false;
                down = true;
                left = false;
                right = false;
            }
            // หน้าmenu
            if (key == KeyEvent.VK_SPACE && !running && !alive) {
                startGame();
            }
            // หน้าจบเกม
            if (key == KeyEvent.VK_SPACE && !running && alive) {
                startGame();
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {
            // int key = ke.getKeyCode();
            // if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT || key ==
            // KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            // up = false;
            // down = false;
            // left = false;
            // right = false;
            // }
        }

    }
}
