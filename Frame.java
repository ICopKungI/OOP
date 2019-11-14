/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author GE
 */
public class Frame extends JFrame{

    public Frame() throws IOException {
        setTitle("Project Game");

        setLayout(new GridLayout(1, 1, 0, 0));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start();
        setVisible(true);
    }

    private void start() throws IOException {
        Run s = new Run();
        add(s);
        pack();
        setLocationRelativeTo(null);
        setContentPane(s.page());
    }

    public static void main(String[] args) throws IOException {
        new Frame(); // runJFrame
    }

}
