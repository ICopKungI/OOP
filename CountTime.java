/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author GE
 */
public class CountTime extends TimerTask {

    public static int i = 0;
    public static Timer timer;
    public static TimerTask task;

    public void run() {
        i++;
        // System.out.println("Timer ran " + ++i);
    }

    public static void Count(boolean run) {
        if (run) {
            i = 0;
            timer = new Timer();
            task = new CountTime();
            timer.schedule(task, 1000, 1000);
        } else {
            timer.cancel();
        }
    }
}
