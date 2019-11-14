/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author GE
 */
public class CountTime extends TimerTask {

    public static int t = 0;
    public static Timer timer;
    public static TimerTask task;
    public static String ans;
    public static String name_array[];
    public static String time_array[];
    public static int ans_sort[] = new int[6];
    public static boolean status = true;

    @Override
    public void run() {
        t++;
        // System.out.println("Timer ran " + ++i);
    }

    public static void Count(boolean run) throws IOException {
        if (run) {
            t = 0;
            timer = new Timer();
            task = new CountTime();
            timer.schedule(task, 1000, 1000);
        } else {
            saverating();
            timer.cancel();
        }
    }

    public static String getrating() throws FileNotFoundException, IOException {//ดึงข้อมูลอันดับจากไฟล์ rating.dat
        File f = new File("rating.dat");
        if (f.exists()) {
            FileInputStream fin = new FileInputStream("rating.dat");
            DataInputStream din = new DataInputStream(fin);
            try {
                ans = din.readUTF();
            } catch (IOException e) {
            }
            din.close();
            fin.close();
            return ans;
        } else {
            return "ยังไม่มีใครเล่น";
        }
    }

    public static void saverating() throws FileNotFoundException, IOException {
        getrating();//ทำให้สามารถดึกข้อมูลและบันทึกได่้อย่างต่อเนื่อง
        FileOutputStream fout = new FileOutputStream("rating.dat");
        DataOutputStream dout = new DataOutputStream(fout);
        if (getrating() == null) {//รอบแรก
            ans = "";
            ans_sort[0] = t;
        } else {
            time_array = getrating().split(" ");
            ans = "";
            for (int i = 0; i < Math.min(5, time_array.length); i++) {//ลูปเรียงอันดับตามเวลาที่ทำได้
                if (status) {
                    if (Integer.parseInt(time_array[i]) >= t) {
                        ans_sort[i] = Integer.parseInt(time_array[i]);
                    } else {
                        ans_sort[i] = t;
                        status = false;
                    }
                } else {
                    ans_sort[i] = Integer.parseInt(time_array[i - 1]);
                }
            }
            status = true;
        }

        //บันทึกข้อมูลอันดับลงไปในไฟล์ rating.dat
        for (int i = 0; i < 5; i++) {
            ans += ans_sort[i];
            if (i != 4) {
                ans += " ";
            }
        }
        dout.writeUTF(ans);
        ans = "";
        dout.close();
        fout.close();
    }
}
