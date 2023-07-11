package rs.raf.projekat_jun_sava_ivkovic_rn1220_mihailo_trajkovic_rn320.helper;

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
}
