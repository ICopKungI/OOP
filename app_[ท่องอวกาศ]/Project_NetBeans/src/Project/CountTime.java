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

    public static int time = 0, point = 0;
    public static Timer timer;
    public static TimerTask task;
    public static String text, data;
    public static String name_array[] = new String[6];
    public static String data_array[];
    public static int sort_num[] = new int[6];
    public static boolean status = true;//เช็คสถานะว่าคนที่เล่นล่าสุดได้เวลามากกว่าคนอื่นไหม

    @Override
    public void run() {
        time++;
    }

    public static void Count(boolean run) throws IOException {
        if (run) {
            time = 0;
            timer = new Timer();
            task = new CountTime();
            timer.schedule(task, 1000, 1000);
        } else {
            save_rating(Run.txt.getText());
            timer.cancel();
        }
    }

    public static String get_rating() throws FileNotFoundException, IOException {//ดึงข้อมูลอันดับจากไฟล์ rating.dat
        File f = new File("rating.dat");
        if (f.exists()) {
            FileInputStream fin = new FileInputStream("rating.dat");
            DataInputStream din = new DataInputStream(fin);
            try {
                data = din.readUTF();
            } catch (IOException e) {
            }
            din.close();
            fin.close();
            return data;
        } else {
            return "ยังไม่มีใครเล่น";
        }
    }

    public static void save_rating(String name) throws FileNotFoundException, IOException {
        FileOutputStream fout = new FileOutputStream("rating.dat");
        DataOutputStream dout = new DataOutputStream(fout);
        if (get_rating() == null) {//รอบแรก
            text = "";
            sort_num[0] = time;
            name_array[0] = name;
        } else {
            data_array = get_rating().split(" ");
            text = "";
            for (int i = 1; i < Math.min(10, data_array.length); i += 2) {//ลูปเรียงอันดับตามเวลาที่ทำได้
                if (status) {
                    if (Integer.parseInt(data_array[i]) >= time) {
                        sort_num[point] = Integer.parseInt(data_array[i]);
                        name_array[point] = data_array[i - 1];
                    } else {
                        sort_num[point] = time;
                        name_array[point] = name;
                        status = false;
                    }
                } else {
                    sort_num[point] = Integer.parseInt(data_array[i - 2]);
                    name_array[point] = data_array[i - 3];
                }
                point++;
            }
            point = 0;
            status = true;
        }

        //บันทึกข้อมูลอันดับลงไปในไฟล์ rating.dat
        for (int i = 0; i < 5; i++) {
            text += name_array[i];
            text += " " + sort_num[i];
            if (i != 4) {
                text += " ";
            }
        }
        dout.writeUTF(text);
        text = "";
        dout.close();
        fout.close();
    }
}
