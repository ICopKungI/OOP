/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.GridLayout;
import javax.swing.JFrame;

/**
 *
 * @author GE
 */
public class Frame extends JFrame {
    public Frame() {
        setTitle("Project Game");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1, 0, 0));
        start();
        setVisible(true);
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
