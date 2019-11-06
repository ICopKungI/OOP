/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Scanner;
import Project.Run;
import javafx.scene.Scene;

/**
 *
 * @author GE
 */
public class Frame extends JFrame {

//    private JFrame fr = new JFrame("Project Game");
//    private JPanel p11 = new JPanel(), p12 = new JPanel(), p13 = new JPanel(), p14 = new JPanel(), p15 = new JPanel(), p16 = new JPanel(), p17 = new JPanel(), p18 = new JPanel();
//    private JPanel p21 = new JPanel(), p22 = new JPanel(), p23 = new JPanel(), p24 = new JPanel(), p25 = new JPanel(), p26 = new JPanel(), p27 = new JPanel(), p28 = new JPanel();
//    private JPanel p31 = new JPanel(), p32 = new JPanel(), p33 = new JPanel(), p34 = new JPanel(), p35 = new JPanel(), p36 = new JPanel(), p37 = new JPanel(), p38 = new JPanel();
//    private JPanel p41 = new JPanel(), p42 = new JPanel(), p43 = new JPanel(), p44 = new JPanel(), p45 = new JPanel(), p46 = new JPanel(), p47 = new JPanel(), p48 = new JPanel();
//    private JPanel p51 = new JPanel(), p52 = new JPanel(), p53 = new JPanel(), p54 = new JPanel(), p55 = new JPanel(), p56 = new JPanel(), p57 = new JPanel(), p58 = new JPanel();
//    private JPanel p61 = new JPanel(), p62 = new JPanel(), p63 = new JPanel(), p64 = new JPanel(), p65 = new JPanel(), p66 = new JPanel(), p67 = new JPanel(), p68 = new JPanel();
//    private JPanel p71 = new JPanel(), p72 = new JPanel(), p73 = new JPanel(), p74 = new JPanel(), p75 = new JPanel(), p76 = new JPanel(), p77 = new JPanel(), p78 = new JPanel();
//    private JPanel p81 = new JPanel(), p82 = new JPanel(), p83 = new JPanel(), p84 = new JPanel(), p85 = new JPanel(), p86 = new JPanel(), p87 = new JPanel(), p88 = new JPanel();
//    private JPanel pmain = new JPanel();
//    private JLabel l1 = new JLabel("123"); //checkตัวในpanel
    public Frame() {
//        pmain.setLayout(new GridLayout(8, 8));
//        p11.add(l1); //เพิ่มตัวในpanel
//        pmain.add(p11);pmain.add(p12);pmain.add(p13);pmain.add(p14);pmain.add(p15);pmain.add(p16);pmain.add(p17);pmain.add(p18);
//        pmain.add(p21);pmain.add(p22);pmain.add(p23);pmain.add(p24);pmain.add(p25);pmain.add(p26);pmain.add(p27);pmain.add(p28);
//        pmain.add(p31);pmain.add(p32);pmain.add(p33);pmain.add(p34);pmain.add(p35);pmain.add(p36);pmain.add(p37);pmain.add(p38);
//        pmain.add(p41);pmain.add(p42);pmain.add(p43);pmain.add(p44);pmain.add(p45);pmain.add(p46);pmain.add(p47);pmain.add(p48);
//        pmain.add(p51);pmain.add(p52);pmain.add(p53);pmain.add(p54);pmain.add(p55);pmain.add(p56);pmain.add(p57);pmain.add(p58);
//        pmain.add(p61);pmain.add(p62);pmain.add(p63);pmain.add(p64);pmain.add(p65);pmain.add(p66);pmain.add(p67);pmain.add(p68);
//        pmain.add(p71);pmain.add(p72);pmain.add(p73);pmain.add(p74);pmain.add(p75);pmain.add(p76);pmain.add(p77);pmain.add(p78);
//        pmain.add(p81);pmain.add(p82);pmain.add(p83);pmain.add(p84);pmain.add(p85);pmain.add(p86);pmain.add(p87);pmain.add(p88);
//        p11.remove(l1); ลบตัวในpanel
//        pmain.setPreferredSize(new Dimension(800, 600));
        setTitle("Project Game");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1,0,0));
        start();
        setVisible(true);
//        fr.setContentPane(pmain);

    }

    private void start() {
        Run s = new Run();
        add(s);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new Frame(); // runJFrame
    }
}
