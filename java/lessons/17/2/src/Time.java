import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Time {
    String date = "";

    public static void main(String[] args) {
        new Time().getTime();
    }

    public void getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMM dd HH:mm:ss", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        date = dateFormat.format(calendar.getTime());
        System.out.println(date);
    }
}
