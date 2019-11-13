/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author GE
 */
public class Frame extends JFrame implements ActionListener {

    private JPanel p1, p2, p3;
    private JButton btn1, btn2, btn3, btn4;

    public Frame() {
//        p1 = new JPanel();
//        p2 = new JPanel();
//        p3 = new JPanel();
//        btn1 = new JButton("เริ่มเกม");
//        btn2 = new JButton("อันดับ");
//        btn3 = new JButton("วิธีเล่น");
//        btn4 = new JButton("ออกจากเกม");
        setTitle("Project Game");

        setLayout(new GridLayout(1, 1, 0, 0));
//        p1.setLayout(new BorderLayout());
//        p2.setLayout(new FlowLayout());
//        p3.setLayout(new GridLayout(1, 4));
//
//        p3.add(btn1);
//        p3.add(btn2);
//        p3.add(btn3);
//        p3.add(btn4);
//        p1.add(p2, BorderLayout.CENTER);
//        p1.add(p3, BorderLayout.SOUTH);
//        add(p1);

//        btn1.setPreferredSize(new Dimension(100, 100));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(800, 800);
//        start(); ของคอป
        setVisible(true);
//        btn1.addActionListener(this);
    }

    private void start() {
        Run s = new Run();
        add(s);
        pack();
        setLocationRelativeTo(null);
        s.startGame();
    }

    public static void main(String[] args) {
        new Frame(); // runJFrame
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn1)) {
            getContentPane().remove(p1);
            invalidate();
            validate();
            start();
        }
    }
}
