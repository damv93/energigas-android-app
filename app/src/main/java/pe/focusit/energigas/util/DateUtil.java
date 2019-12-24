package pe.focusit.energigas.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static final String DATE_SYSTEM = "yyyy-MM-dd";
    public static final String DATE_HUMAN = "dd/MM/yyyy";
    public static final String DATETIME_SYSTEM = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_HUMAN = "dd/MM/yyyy h:mm a";

    public static void chooseDateTime(final Context ctx, long millis, final OnDateTimeListener callback){

        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);

        // Elegir fecha
        DatePickerDialog dialog = new DatePickerDialog(ctx, (view, year, monthOfYear, dayOfMonth) -> {
            cal.set(year, monthOfYear, dayOfMonth);

            // Elegir hora
            new TimePickerDialog(ctx, (timePicker, hourOfDay, minute) -> {
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                cal.set(Calendar.SECOND, 0);
                callback.onDateTime(cal.getTimeInMillis());
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show();

        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        Calendar minDate = Calendar.getInstance();
        minDate.setTimeInMillis(cal.getTimeInMillis());
        minDate.add(Calendar.DAY_OF_MONTH, -10);
        dialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
        dialog.show();
    }

    public static void chooseDateTime(final Context ctx, String date_time, final OnDateTimeListener callback){

        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_SYSTEM, Locale.getDefault());
        try{
            chooseDateTime(ctx, sdf.parse(date_time).getTime(), callback);
        } catch (Exception ignored){
            chooseDateTime(ctx, System.currentTimeMillis(), callback);
        }

    }

    public static String datetimeToHuman(String datetime){
        if (datetime != null && !datetime.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_SYSTEM, Locale.getDefault());
            try {
                return dateFormat(sdf.parse(datetime).getTime(), DATETIME_HUMAN);
            } catch (Exception ignored) {}
        }
        return "";
    }

    public static String dateFormat(long millis, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return sdf.format(calendar.getTime());
    }


    public interface OnDateTimeListener{
        void onDateTime(long datetimeInMillis);
    }

}
