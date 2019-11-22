package Project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class CountTime extends TimerTask {

    public static int t = 0;
    public static Timer timer;
    public static TimerTask task;
    public static String ans;
    public static String name_array[] = new String[6];
    public static String data_array[];
    public static int ans_sort[] = new int[6];
    public static boolean status = true;

    @Override
    public void run() {
        t++;
    }

    public static void Count(boolean run) throws IOException {
        if (run) {
            t = 0;
            timer = new Timer();
            task = new CountTime();
            timer.schedule(task, 1000, 1000);
        } else {
            saverating(Run.txt.getText());
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

    public static void saverating(String name) throws FileNotFoundException, IOException {
        getrating();//ทำให้สามารถดึกข้อมูลและบันทึกได้อย่างต่อเนื่อง
        FileOutputStream fout = new FileOutputStream("rating.dat");
        DataOutputStream dout = new DataOutputStream(fout);
        if (getrating() == null) {//รอบแรก
            ans = "";
            ans_sort[0] = t;
            name_array[0] = name;
        } else {
            int num = 0;
            data_array = getrating().split(" ");
            ans = "";
            for (int i = 1; i < Math.min(10, data_array.length); i += 2) {//ลูปเรียงอันดับตามเวลาที่ทำได้
                if (status) {
                    if (Integer.parseInt(data_array[i]) >= t) {
                        ans_sort[num] = Integer.parseInt(data_array[i]);
                        name_array[num] = data_array[i - 1];
                    } else {
                        ans_sort[num] = t;
                        name_array[num] = name;
                        status = false;
                    }
                } else {
                    ans_sort[num] = Integer.parseInt(data_array[i - 2]);
                    name_array[num] = data_array[i - 3];
                }
                num++;
            }
            status = true;
        }

        //บันทึกข้อมูลอันดับลงไปในไฟล์ rating.dat
        for (int i = 0; i < 5; i++) {
            ans += name_array[i];
            ans += " " + ans_sort[i];
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
