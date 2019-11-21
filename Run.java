package Project;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Run extends JPanel implements Runnable, ActionListener, MouseListener {

    private Thread th;
    private boolean running = false;// เก็บสถานะการทำงาน
    public static int width = 800;
    public static int height = 800;
    private boolean sleep = true;//สถานะโชว์ภาพหรือไม่ตอนรอ 0.5 วิ
    Cursor cursor;

    //เพลง
    private InputStream File_main;
    private AudioStream Sound_main;
    private InputStream File_button;
    private AudioStream Sound_button;
    private InputStream File_playing;
    private AudioStream Sound_playing;
    private InputStream File_rateing;
    private AudioStream Sound_rateing;
    private InputStream File_dead;
    private AudioStream Sound_dead;
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

    private JPanel cards;
    //หน้า main
    private JPanel panel_main, panel_main1, panel_main2;
    private JButton button_main1, button_main2, button_main3, button_main4;
    //หน้า dead
    private JPanel panel_dead1, panel_dead2;
    private JLabel label_dead;
    private JButton button_dead1, button_dead2, button_dead3;

    //หน้าวิธีเล่น
    private JPanel panel_sol;
    private JLabel label_sol;
    private JButton button_sol;

    //หน้าคะแนน
    private JPanel panel_rat;
    private JLabel label_rat;
    private JButton button_rat;

    //หน้าคะแนน
    private JPanel panel_et, panel_t1, panel_t2;
    protected static JTextField txt;
    private JLabel label_et;
    private JButton button_et1, button_et2;

    //ชื่อแต่ละหน้าPanal
    final static String MIAN = "MIAN";
    final static String GAME = "GAME";
    final static String SOL = "SOL";
    final static String RATING = "RATING";
    final static String END = "END";
    final static String ENTERTEXT = "ENTERTEXT";

    //รูป
    JLabel background = new JLabel("", new ImageIcon(getClass().getResource("/image/Main1.jpg")), JLabel.CENTER);

    private BufferedImage img;

    public String text_rat(boolean type) throws IOException {//แสดงผลการเล่น
        String text = "<html><div style='text-align: center; color:rgb(230, 15, 37);font-size: 24px;padding-top: 200px;'>";
        int num = 1;
        if (CountTime.getrating().equals("ยังไม่มีใครเล่น")) {
            return "<html><div style='text-align: center; color:rgb(0, 183, 255);font-size: 24px;'>" + CountTime.getrating() + "</div></html>";
        } else {
            String[] ans = CountTime.getrating().split(" ");
            for (int i = 1; i < Math.min(10, ans.length); i += 2) {
                if (!ans[i].equals("0")) {
                    text += ("อันดับที่ " + num + " : " + ans[i - 1] + " " + ans[i] + " วินาที<br><br>");
                    num++;
                }
            }
        }
        if (type) {
            text += ("เวลาที่คุณ " + txt.getText() + " ทำได้: " + CountTime.t + " วินาที");
        }
        return text + "</div></html>";
    }

    public JPanel page() throws IOException, URISyntaxException {
        cards = new JPanel(new CardLayout());//panal สลับ page

        //หน้า Main
        panel_main = new JPanel();
        panel_main1 = new JPanel();
        panel_main2 = new JPanel();
        button_main1 = new JButton();//เริ่มเกม
        button_main2 = new JButton();//อันดับ
        button_main3 = new JButton();//วิธีเล่น
        button_main4 = new JButton();//ออกจากเกม

        panel_main.setLayout(new BorderLayout());
        panel_main1.setLayout(new FlowLayout());
        panel_main2.setLayout(new GridLayout(1, 4));
        button_main1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/Untitled-1.png")))); //ตั้งรูปที่ปุ่ม
        button_main2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/rank.png")))); //ตั้งรูปที่ปุ่ม
        button_main3.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/how.png")))); //ตั้งรูปที่ปุ่ม
        button_main4.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/Q.png")))); //ตั้งรูปที่ปุ่ม
        button_main1.setBorderPainted(false);
        button_main2.setBorderPainted(false);
        button_main3.setBorderPainted(false);
        button_main4.setBorderPainted(false);
        button_main1.addMouseListener(this);
        button_main2.addMouseListener(this);
        button_main3.addMouseListener(this);
        button_main4.addMouseListener(this);

        panel_main2.add(button_main1);
        panel_main2.add(button_main2);
        panel_main2.add(button_main3);
        panel_main2.add(button_main4);
        panel_main.add(panel_main1, BorderLayout.CENTER);
        panel_main.add(panel_main2, BorderLayout.SOUTH);

        button_main1.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        button_main2.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        button_main3.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        button_main4.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        setSize(800, 800);
        button_main1.addActionListener(this);
        button_main2.addActionListener(this);
        button_main3.addActionListener(this);
        button_main4.addActionListener(this);

        File_main = new FileInputStream(new File(getClass().getResource("/sound/menu.wav").toURI()));
        Sound_main = new AudioStream(File_main);
        AudioPlayer.player.start(Sound_main);

        //หน้าตาย
        panel_dead1 = new JPanel();
        panel_dead2 = new JPanel();
        label_dead = new JLabel();
        button_dead1 = new JButton();//เล่นอีกครั้ง
        button_dead2 = new JButton();//ย้อนกลับไปหน้าหลัก
        button_dead3 = new JButton();//ออกจากเกม

        panel_dead1.setLayout(new BorderLayout());
        panel_dead2.setLayout(new GridLayout(1, 3));
        label_dead.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/died.jpg"))));
        label_dead.setHorizontalAlignment(JLabel.CENTER);
        label_dead.setVerticalAlignment(JLabel.CENTER);
        label_dead.setHorizontalTextPosition(JLabel.CENTER);
        button_dead1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/p1.png")))); //ตั้งรูปที่ปุ่ม
        button_dead2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/m1.png")))); //ตั้งรูปที่ปุ่ม
        button_dead3.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/Q1.png")))); //ตั้งรูปที่ปุ่ม
        button_dead1.setBorderPainted(false);
        button_dead2.setBorderPainted(false);
        button_dead3.setBorderPainted(false);
        button_dead1.addMouseListener(this);
        button_dead2.addMouseListener(this);
        button_dead3.addMouseListener(this);

        panel_dead2.add(button_dead1);
        panel_dead2.add(button_dead2);
        panel_dead2.add(button_dead3);
        panel_dead1.add(panel_dead2, BorderLayout.SOUTH);
        panel_dead1.add(label_dead, BorderLayout.CENTER);

        button_dead1.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        button_dead2.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        button_dead3.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        button_dead1.addActionListener(this);
        button_dead2.addActionListener(this);
        button_dead3.addActionListener(this);

        //หน้าผลคะแนน
        panel_rat = new JPanel();
        label_rat = new JLabel();
        button_rat = new JButton();//ย้อนกลับ

        panel_rat.setLayout(new BorderLayout());
        panel_rat.add(button_rat, BorderLayout.SOUTH);
        panel_rat.add(label_rat, BorderLayout.CENTER);
        label_rat.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/rank.jpg"))));
        label_rat.setHorizontalAlignment(JLabel.CENTER);
        label_rat.setVerticalAlignment(JLabel.CENTER);
        label_rat.setHorizontalTextPosition(JLabel.CENTER);
        button_rat.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/BB2.png")))); //ตั้งรูปที่ปุ่ม
        button_rat.setBorderPainted(false);
        button_rat.addMouseListener(this);

        button_rat.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        button_rat.addActionListener(this);

        //หน้าวิธีเล่น
        panel_sol = new JPanel();
        label_sol = new JLabel();
        button_sol = new JButton();//ย้อนกลับ

        panel_sol.setLayout(new BorderLayout());
        panel_sol.add(label_sol, BorderLayout.CENTER);
        panel_sol.add(button_sol, BorderLayout.SOUTH);
        label_sol.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/how2p.jpg"))));
        button_sol.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/BB2.png")))); //ตั้งรูปที่ปุ่ม
        button_sol.setBorderPainted(false);
        button_sol.addMouseListener(this);

        button_sol.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        button_sol.addActionListener(this);

        //หน้าใส่ชื่อ
        panel_et = new JPanel();
        panel_t1 = new JPanel();
        panel_t2 = new JPanel();
        label_et = new JLabel();
        txt = new JTextField();
        button_et1 = new JButton();//ตกลง
        button_et2 = new JButton();//ย้อนกลับ

        panel_et.setLayout(new BorderLayout());
        panel_t1.setLayout(new GridLayout(1, 2));
        panel_t2.setLayout(new GridLayout(1, 2));
        panel_t2.add(button_et1);
        panel_t2.add(button_et2);
        panel_t1.add(txt);
        panel_t1.add(panel_t2);
        panel_et.add(panel_t1, BorderLayout.SOUTH);
        panel_et.add(label_et, BorderLayout.CENTER);
        label_et.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/map2.jpg"))));
        label_et.setHorizontalAlignment(JLabel.CENTER);
        label_et.setVerticalAlignment(JLabel.CENTER);
        button_et1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/AA.png")))); //ตั้งรูปที่ปุ่ม
        button_et2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/BB_t.png")))); //ตั้งรูปที่ปุ่ม
        button_et1.setBorderPainted(false);
        button_et2.setBorderPainted(false);
        button_et1.addMouseListener(this);
        button_et2.addMouseListener(this);

        button_et1.setPreferredSize(new Dimension(50, 50));
        button_et1.addActionListener(this);
        button_et2.setPreferredSize(new Dimension(50, 50));
        button_et2.addActionListener(this);

        panel_main.add(background); //เพิ่มภาพในหน้า main
        background.setBounds(0, 0, 820, 820);
        cards.add(panel_main, MIAN);
        cards.add(panel_sol, SOL);
        cards.add(panel_rat, RATING);
        cards.add(this, GAME);
        cards.add(panel_dead1, END);
        cards.add(panel_et, ENTERTEXT);
        return cards;

    }

    public Run() {
        setPreferredSize(new Dimension(width, height)); // กำหนกขนาดหน้าจอ
        playerArray = new ArrayList();
        monsterArray = new ArrayList();
        monsterArray2 = new ArrayList();
        addKeyListener(new KeyInner());
        setFocusable(true); // ทำให้สามารถใช้งานkeyboardได้ - by boy ทำให้ java ตั้งใจฟัง keybord
        try {
            img = ImageIO.read(getClass().getResource("/image/map1.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
    @Override
    public void run() {
        try {
            try {
                Thread.sleep(500);//รอ 0.5 วิหลังพิมชื่อเสร็จ
                sleep = false;
            } catch (InterruptedException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            // running = true; //สั่งให้เดินเกม
            CountTime.Count(true);
        } catch (IOException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (running) {
            try {
                // try {
                // // System.out.println("OK"); //debug ว่า runThreadไหม
                // Thread.sleep(10);
                // } catch (InterruptedException ex) {
                // Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
                // }
                tick();
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            repaint();
            // System.out.println(CountTime.i);
        }
    }

    public void stop() throws IOException {
        //เสียงตอนตาย
        try {
            File_dead = new FileInputStream(new File(getClass().getResource("/sound/over 2.wav").toURI()));
        } catch (URISyntaxException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }
        Sound_dead = new AudioStream(File_dead);
        AudioPlayer.player.start(Sound_dead);
        AudioPlayer.player.stop(Sound_playing);
        CountTime.Count(false);
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
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, END);
        label_dead.setText(text_rat(true));
        running = false;
        // try {
        // th.join();
        // } catch (Exception ex) {
        // System.out.println(ex.toString());
        // }
    }

    // ควบคุมการเคลื่อนที่ของวัตถุหน้าจอเกม / กำหนดค่าเริ่มต้น
    private void tick() throws IOException {
        if (playerArray.size() == 0) {
            p = new Player(x, y, 10);
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
                System.out.println("Boom!!!!");
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
//            p = new Player(x, y, 10);
//            playerArray.add(p);
//            if (playerArray.size() > size) {
//                playerArray.remove(0);
//            }
            playerArray.get(0).setY(y);
            playerArray.get(0).setX(x);
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
//        if (!running && !alive) {
//            g.setColor(Color.RED);
//            g.setFont(new Font("Tahoma", Font.BOLD, 40));
//            g.drawString("หลบๆๆ", width / 2 - 80, height / 2);
//            g.setColor(Color.black);
//            g.setFont(new Font("Tahoma", Font.BOLD, 20));
//            g.drawString("กด SpaceBar เพื่อเริ่ม", width / 2 - 100, height / 2 + 30);
//        } else 
//        if (!running && alive) {
//            int i = 0;
//            try {
//                for (String str : CountTime.getrating().split(" ")) {
//                    if (!str.equals("0")) {
//                        g.setColor(Color.RED);
//                        g.setFont(new Font("Tahoma", Font.BOLD, 40));
//                        g.drawString("อันดับที่ " + (i / 50 + 1) + " : " + str + " วินาที\n", width / 2 - 150, height / 2 + i);
//                        i += 50;
//                    }
//                }
//            } catch (IOException ex) {
//                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            g.setColor(Color.RED);
//            g.setFont(new Font("Tahoma", Font.BOLD, 40));
//            g.drawString("เวลาที่คุณทำได้: " + CountTime.t + " วินาที\n", width / 2 - 150, height / 2 + i);
//            g.setColor(Color.black);
//            g.setFont(new Font("Tahoma", Font.BOLD, 20));
//            g.drawString("กด SpaceBar เพื่อเริ่มเกมใหม่", width / 2 - 100, height / 2 + 30 + i);
//
//        } else {
        g.drawImage(img, 0, 0, this);
        try {
            if (sleep) {
                g.drawImage(ImageIO.read(getClass().getResource("/image/pig01.gif")), 350, 350, this);//ทำให้มีรูปหมูอยู่ตรงกลางตอนรอ 0.5 วิก่อนเกมจะเริ่มจริงๆ
            }
        } catch (IOException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (running && alive) {

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
//        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //เสียงปุ่ม
        try {
            File_button = new FileInputStream(new File(getClass().getResource("/sound/select.wav").toURI()));
        } catch (URISyntaxException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Sound_button = new AudioStream(File_button);
        } catch (IOException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }
        AudioPlayer.player.start(Sound_button);

        if ((e.getSource().equals(button_et1)) || (e.getSource().equals(button_dead1))) {//กดเริ่มเกมใหม่
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, GAME);
            requestFocusInWindow();
            sleep = true;
            startGame();
            //เสียงเกม
            try {
                File_playing = new FileInputStream(new File(getClass().getResource("/sound/play game.wav").toURI()));
            } catch (URISyntaxException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Sound_playing = new AudioStream(File_playing);
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            AudioPlayer.player.start(Sound_playing);
            AudioPlayer.player.stop(Sound_main);
            AudioPlayer.player.stop(Sound_dead);
        } else if ((e.getSource().equals(button_main4)) || (e.getSource().equals(button_dead3))) {//กดออกจากเกม
            System.exit(0);
        } else if (e.getSource().equals(button_main2)) {//ไปหน้าคะแนน
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, RATING);
            //เสียงหน้าคะแนน
            try {
                File_rateing = new FileInputStream(new File(getClass().getResource("/sound/ranking.wav").toURI()));
            } catch (URISyntaxException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Sound_rateing = new AudioStream(File_rateing);
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            AudioPlayer.player.start(Sound_rateing);
            AudioPlayer.player.stop(Sound_main);
            try {
                label_rat.setText(text_rat(false));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource().equals(button_main3)) {//ไปหน้าวิธีเล่น
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, SOL);
        } else if ((e.getSource().equals(button_rat)) || (e.getSource().equals(button_dead2))) {//ไปหน้าเมนูหลัก ***เล่นเพลง
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, MIAN);
            //เสียงหน้า main menu
            try {
                File_main = new FileInputStream(new File(getClass().getResource("/sound/menu.wav").toURI()));
            } catch (URISyntaxException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Sound_main = new AudioStream(File_main);
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            AudioPlayer.player.start(Sound_main);
            AudioPlayer.player.stop(Sound_dead);
            AudioPlayer.player.stop(Sound_rateing);
        } else if (e.getSource().equals(button_main1)) {//ไปหน้าใส่ชื่อ
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, ENTERTEXT);
        } else if (e.getSource().equals(button_sol) || (e.getSource().equals(button_et2))) {//จากหน้าวิธีเล่นและหน้ากรอกชื่อไปหน้าเมนูหลัก ***ไม่เล่นเพลง
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, MIAN);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //ตั้งรูปที่ปุ่มหน้า main
        if (me.getSource().equals(button_main1)) {
            try {
                button_main1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/Untitled-2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(button_main2)) {
            try {
                button_main2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/Untitled-3.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(button_main3)) {
            try {
                button_main3.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/Untitled-4.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(button_main4)) {
            try {
                button_main4.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/Untitled-5.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //ตั้งรูปที่ปุ่มหน้า ตาย
        if (me.getSource().equals(button_dead1)) {
            try {
                button_dead1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/p2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(button_dead2)) {
            try {
                button_dead2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/m2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(button_dead3)) {
            try {
                button_dead3.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/Q2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //ตั้งรูปที่ปุ่มหน้า อันดับ
        if (me.getSource().equals(button_rat)) {
            try {
                button_rat.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/bb.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //ตั้งรูปที่ปุ่มหน้า วิธีเล่น
        if (me.getSource().equals(button_sol)) {
            try {
                button_sol.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/bb.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //ตั้งรูปที่ปุ่มหน้า ใส่ชื่อ
        if (me.getSource().equals(button_et1)) {
            try {
                button_et1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/Untitled-7.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(button_et2)) {
            try {
                button_et2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/Untitled-6.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void mouseExited(MouseEvent me) {
        //ตั้งรูปที่ปุ่มหน้า main
        if (me.getSource().equals(button_main1)) {
            try {
                button_main1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/Untitled-1.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(button_main2)) {
            try {
                button_main2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/rank.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(button_main3)) {
            try {
                button_main3.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/how.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(button_main4)) {
            try {
                button_main4.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/Q.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //ตั้งรูปที่ปุ่มหน้า ตาย
        if (me.getSource().equals(button_dead1)) {
            try {
                button_dead1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/p1.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(button_dead2)) {
            try {
                button_dead2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/m1.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(button_dead3)) {
            try {
                button_dead3.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/Q1.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //ตั้งรูปที่ปุ่มหน้า อันดับ
        if (me.getSource().equals(button_rat)) {
            try {
                button_rat.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/BB2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //ตั้งรูปที่ปุ่มหน้า วิธีเล่น
        if (me.getSource().equals(button_sol)) {
            try {
                button_sol.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/BB2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //ตั้งรูปที่ปุ่มหน้า ใส่ชื่อ
        if (me.getSource().equals(button_et1)) {
            try {
                button_et1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/AA.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(button_et2)) {
            try {
                button_et2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/BB_t.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
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
//            if (key == KeyEvent.VK_solPACE && !running && !alive) {
//                startGame();
//            }
            // หน้าจบเกม
            if (key == KeyEvent.VK_SPACE && !running && alive) {
                startGame();
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {
            // int key = ke.getKeyCode();
            // if (key == KeyEvent.VK_ratIGHT || key == KeyEvent.VK_LEFT || key ==
            // KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            // up = false;
            // down = false;
            // left = false;
            // right = false;
            // }
        }

    }
}
