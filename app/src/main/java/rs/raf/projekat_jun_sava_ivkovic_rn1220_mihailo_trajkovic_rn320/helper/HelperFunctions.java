package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class HelperFunctions {

    public static Date trimDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        return date;
    }

    public static String formatDate(Date date){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return dtf.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    public static void loadImage(ImageView imageView, String url){

        new Thread(() -> {
            try {
                File imgFile = new File(url);
                if(imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageView.setImageBitmap(myBitmap);
                }
                else {
                    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
                    con.connect();
                    InputStream input = con.getInputStream();
                    final Bitmap bm = BitmapFactory.decodeStream(input);
                    imageView.setImageBitmap(bm);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}
