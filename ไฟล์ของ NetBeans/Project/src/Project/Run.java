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
    private InputStream F_main;
    private AudioStream S_main;
    private InputStream F_button;
    private AudioStream S_button;
    private InputStream F_playing;
    private AudioStream S_playing;
    private InputStream F_rateing;
    private AudioStream S_rateing;
    private InputStream F_dead;
    private AudioStream S_dead;
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
    private JPanel p_main, p1_m, p2_m;
    private JButton btn1_m, btn2_m, btn3_m, btn4_m;
    //หน้า end
    private JPanel p_end, p_e;
    private JLabel lb_e;
    private JButton btn1_e, btn2_e, btn3_e;

    //หน้าวิธีเล่น
    private JPanel p_sol;
    private JLabel lb_s;
    private JButton btn_s;

    //หน้าคะแนน
    private JPanel p_rat;
    private JLabel lb_r;
    private JButton btn_r;

    //หน้าคะแนน
    private JPanel p_et, p_t1, p_t2;
    protected static JTextField txt;
    private JLabel lb_et;
    private JButton btn_et1, btn_et2;

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
        p_main = new JPanel();
        p1_m = new JPanel();
        p2_m = new JPanel();
        btn1_m = new JButton();//เริ่มเกม
        btn2_m = new JButton();//อันดับ
        btn3_m = new JButton();//วิธีเล่น
        btn4_m = new JButton();//ออกจากเกม

        p_main.setLayout(new BorderLayout());
        p1_m.setLayout(new FlowLayout());
        p2_m.setLayout(new GridLayout(1, 4));
        btn1_m.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png")))); //ตั้งรูปที่ปุ่ม
        btn2_m.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png")))); //ตั้งรูปที่ปุ่ม
        btn3_m.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png")))); //ตั้งรูปที่ปุ่ม
        btn4_m.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png")))); //ตั้งรูปที่ปุ่มช
        btn1_m.addMouseListener(this);
        btn2_m.addMouseListener(this);
        btn3_m.addMouseListener(this);
        btn4_m.addMouseListener(this);

        p2_m.add(btn1_m);
        p2_m.add(btn2_m);
        p2_m.add(btn3_m);
        p2_m.add(btn4_m);
        p_main.add(p1_m, BorderLayout.CENTER);
        p_main.add(p2_m, BorderLayout.SOUTH);

        btn1_m.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        btn2_m.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        btn3_m.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        btn4_m.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        setSize(800, 800);
        btn1_m.addActionListener(this);
        btn2_m.addActionListener(this);
        btn3_m.addActionListener(this);
        btn4_m.addActionListener(this);

        F_main = new FileInputStream(new File(getClass().getResource("/sound/menu.wav").toURI()));
        S_main = new AudioStream(F_main);
        AudioPlayer.player.start(S_main);

        //หน้าตาย
        p_end = new JPanel();
        p_e = new JPanel();
        lb_e = new JLabel();
        btn1_e = new JButton();//เล่นอีกครั้ง
        btn2_e = new JButton();//ย้อนกลับไปหน้าหลัก
        btn3_e = new JButton();//ออกจากเกม

        p_end.setLayout(new BorderLayout());
        p_e.setLayout(new GridLayout(1, 3));
        lb_e.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/died.jpg"))));
        lb_e.setHorizontalAlignment(JLabel.CENTER);
        lb_e.setVerticalAlignment(JLabel.CENTER);
        lb_e.setHorizontalTextPosition(JLabel.CENTER);
        btn1_e.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png")))); //ตั้งรูปที่ปุ่ม
        btn2_e.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png")))); //ตั้งรูปที่ปุ่ม
        btn3_e.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png")))); //ตั้งรูปที่ปุ่ม
        btn1_e.addMouseListener(this);
        btn2_e.addMouseListener(this);
        btn3_e.addMouseListener(this);

        p_e.add(btn1_e);
        p_e.add(btn2_e);
        p_e.add(btn3_e);
        p_end.add(p_e, BorderLayout.SOUTH);
        p_end.add(lb_e, BorderLayout.CENTER);

        btn1_e.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        btn2_e.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        btn3_e.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        btn1_e.addActionListener(this);
        btn2_e.addActionListener(this);
        btn3_e.addActionListener(this);

        //หน้าผลคะแนน
        p_rat = new JPanel();
        lb_r = new JLabel();
        btn_r = new JButton();//ย้อนกลับ

        p_rat.setLayout(new BorderLayout());
        p_rat.add(btn_r, BorderLayout.SOUTH);
        p_rat.add(lb_r, BorderLayout.CENTER);
        lb_r.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/rank.jpg"))));
        lb_r.setHorizontalAlignment(JLabel.CENTER);
        lb_r.setVerticalAlignment(JLabel.CENTER);
        lb_r.setHorizontalTextPosition(JLabel.CENTER);
        btn_r.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png")))); //ตั้งรูปที่ปุ่ม
        btn_r.addMouseListener(this);

        btn_r.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        btn_r.addActionListener(this);

        //หน้าวิธีเล่น
        p_sol = new JPanel();
        lb_s = new JLabel();
        btn_s = new JButton();//ย้อนกลับ

        p_sol.setLayout(new BorderLayout());
        p_sol.add(lb_s, BorderLayout.CENTER);
        p_sol.add(btn_s, BorderLayout.SOUTH);
        lb_s.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/how2p.jpg"))));
        btn_s.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png")))); //ตั้งรูปที่ปุ่ม
        btn_s.addMouseListener(this);

        btn_s.setPreferredSize(new Dimension(50, 50));//ตั้งขนาดปุ่ม
        btn_s.addActionListener(this);

        //หน้าใส่ชื่อ
        p_et = new JPanel();
        p_t1 = new JPanel();
        p_t2 = new JPanel();
        lb_et = new JLabel();
        txt = new JTextField();
        btn_et1 = new JButton();//ตกลง
        btn_et2 = new JButton();//ย้อนกลับ

        p_et.setLayout(new BorderLayout());
        p_t1.setLayout(new GridLayout(1, 2));
        p_t2.setLayout(new GridLayout(1, 2));
        p_t2.add(btn_et1);
        p_t2.add(btn_et2);
        p_t1.add(txt);
        p_t1.add(p_t2);
        p_et.add(p_t1, BorderLayout.SOUTH);
        p_et.add(lb_et, BorderLayout.CENTER);
        lb_et.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/map2.jpg"))));
        lb_et.setHorizontalAlignment(JLabel.CENTER);
        lb_et.setVerticalAlignment(JLabel.CENTER);
        btn_et1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png")))); //ตั้งรูปที่ปุ่ม
        btn_et2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png")))); //ตั้งรูปที่ปุ่ม
        btn_et1.addMouseListener(this);
        btn_et2.addMouseListener(this);

        btn_et1.setPreferredSize(new Dimension(50, 50));
        btn_et1.addActionListener(this);
        btn_et2.setPreferredSize(new Dimension(50, 50));
        btn_et2.addActionListener(this);

        p_main.add(background); //เพิ่มภาพในหน้า main
        background.setBounds(0, 0, 800, 800);
        cards.add(p_main, MIAN);
        cards.add(p_sol, SOL);
        cards.add(p_rat, RATING);
        cards.add(this, GAME);
        cards.add(p_end, END);
        cards.add(p_et, ENTERTEXT);
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
            F_dead = new FileInputStream(new File(getClass().getResource("/sound/over 2.wav").toURI()));
        } catch (URISyntaxException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }
        S_dead = new AudioStream(F_dead);
        AudioPlayer.player.start(S_dead);
        AudioPlayer.player.stop(S_playing);
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
        lb_e.setText(text_rat(true));
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
            F_button = new FileInputStream(new File(getClass().getResource("/sound/select.wav").toURI()));
        } catch (URISyntaxException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            S_button = new AudioStream(F_button);
        } catch (IOException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }
        AudioPlayer.player.start(S_button);

        if ((e.getSource().equals(btn_et1)) || (e.getSource().equals(btn1_e))) {//กดเริ่มเกมใหม่
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, GAME);
            requestFocusInWindow();
            sleep = true;
            startGame();
            //เสียงเกม
            try {
                F_playing = new FileInputStream(new File(getClass().getResource("/sound/play game.wav").toURI()));
            } catch (URISyntaxException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                S_playing = new AudioStream(F_playing);
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            AudioPlayer.player.start(S_playing);
            AudioPlayer.player.stop(S_main);
            AudioPlayer.player.stop(S_dead);
        } else if ((e.getSource().equals(btn4_m)) || (e.getSource().equals(btn3_e))) {//กดออกจากเกม
            System.exit(0);
        } else if (e.getSource().equals(btn2_m)) {//ไปหน้าคะแนน
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, RATING);
            //เสียงหน้าคะแนน
            try {
                F_rateing = new FileInputStream(new File(getClass().getResource("/sound/ranking.wav").toURI()));
            } catch (URISyntaxException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                S_rateing = new AudioStream(F_rateing);
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            AudioPlayer.player.start(S_rateing);
            AudioPlayer.player.stop(S_main);
            try {
                lb_r.setText(text_rat(false));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource().equals(btn3_m)) {//ไปหน้าวิธีเล่น
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, SOL);
        } else if ((e.getSource().equals(btn_r)) || (e.getSource().equals(btn2_e))) {//ไปหน้าเมนูหลัก ***เล่นเพลง
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, MIAN);
            //เสียงหน้า main menu
            try {
                F_main = new FileInputStream(new File(getClass().getResource("/sound/menu.wav").toURI()));
            } catch (URISyntaxException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                S_main = new AudioStream(F_main);
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
            AudioPlayer.player.start(S_main);
            AudioPlayer.player.stop(S_dead);
            AudioPlayer.player.stop(S_rateing);
        } else if (e.getSource().equals(btn1_m)) {//ไปหน้าใส่ชื่อ
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, ENTERTEXT);
        } else if (e.getSource().equals(btn_s) || (e.getSource().equals(btn_et2))) {//จากหน้าวิธีเล่นและหน้ากรอกชื่อไปหน้าเมนูหลัก ***ไม่เล่นเพลง
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
        if (me.getSource().equals(btn1_m)) {
            try {
                btn1_m.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/1.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(btn2_m)) {
            try {
                btn2_m.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/1.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(btn3_m)) {
            try {
                btn3_m.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/1.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(btn4_m)) {
            try {
                btn4_m.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/1.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //ตั้งรูปที่ปุ่มหน้า ตาย
        if (me.getSource().equals(btn1_e)) {
            try {
                btn1_e.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/1.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(btn2_e)) {
            try {
                btn2_e.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/1.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(btn3_e)) {
            try {
                btn3_e.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/1.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //ตั้งรูปที่ปุ่มหน้า อันดับ
        if (me.getSource().equals(btn_r)) {
            try {
                btn_r.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/1.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //ตั้งรูปที่ปุ่มหน้า วิธีเล่น
        if (me.getSource().equals(btn_s)) {
            try {
                btn_s.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/1.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //ตั้งรูปที่ปุ่มหน้า ใส่ชื่อ
        if (me.getSource().equals(btn_et1)) {
            try {
                btn_et1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/1.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(btn_et2)) {
            try {
                btn_et2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/1.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void mouseExited(MouseEvent me) {
        //ตั้งรูปที่ปุ่มหน้า main
        if (me.getSource().equals(btn1_m)) {
            try {
                btn1_m.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(btn2_m)) {
            try {
                btn2_m.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(btn3_m)) {
            try {
                btn3_m.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(btn4_m)) {
            try {
                btn4_m.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //ตั้งรูปที่ปุ่มหน้า ตาย
        if (me.getSource().equals(btn1_e)) {
            try {
                btn1_e.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(btn2_e)) {
            try {
                btn2_e.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(btn3_e)) {
            try {
                btn3_e.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //ตั้งรูปที่ปุ่มหน้า อันดับ
        if (me.getSource().equals(btn_r)) {
            try {
                btn_r.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //ตั้งรูปที่ปุ่มหน้า วิธีเล่น
        if (me.getSource().equals(btn_s)) {
            try {
                btn_s.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //ตั้งรูปที่ปุ่มหน้า ใส่ชื่อ
        if (me.getSource().equals(btn_et1)) {
            try {
                btn_et1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png"))));
            } catch (IOException ex) {
                Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
            }
        }if (me.getSource().equals(btn_et2)) {
            try {
                btn_et2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/image/2.png"))));
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
//            if (key == KeyEvent.VK_SPACE && !running && !alive) {
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
