package co.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author TUNGDX
 */

/**
 * All utils for {@link Date}
 */
public class DateUtils {
    /**
     * Compare day between 2 {@link Date}. This is difference with
     * {@link Date#after(Date)} or {@link Date#before(Date)}. Because it only
     * compare by day (not compare hour, minutes, seconds).
     *
     * @param dateRoot      This is root
     * @param dateToCompare Date to compare with root.
     * @return true if dateToCompare > date root, false otherwise.
     */
    public static boolean checkLargerByDay(Date dateRoot, Date dateToCompare) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateRoot);

        Calendar calendarToCompare = Calendar.getInstance();
        calendarToCompare.setTime(dateToCompare);

        int lastYear = calendar.get(Calendar.YEAR);
        int newYear = calendarToCompare.get(Calendar.YEAR);
        if (lastYear == newYear) {
            int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
            int dayOfNewYearToCompare = calendarToCompare
                    .get(Calendar.DAY_OF_YEAR);
            if (dayOfYear < dayOfNewYearToCompare)
                return true;
        } else if (lastYear < newYear) {
            return true;
        }
        return false;
    }

    /**
     * Compare day between 2 {@link Date}. This is difference with
     * {@link Date#after(Date)} or {@link Date#before(Date)}. Because it only
     * compare by day (not compare hour, minutes, seconds).
     *
     * @param dateRoot    This is rootDate
     * @param dateCompare Date compare to rootDate.
     * @return -1 if dateCompare < dateRoot, 0 if equal, 1 if dateCompare = dateRoot.
     */
    public static int compareDate(Date dateRoot, Date dateCompare) {
        Calendar calendarRoot = Calendar.getInstance();
        calendarRoot.setTime(dateRoot);

        Calendar calenderCompare = Calendar.getInstance();
        calenderCompare.setTime(dateCompare);
        int rootYear = calendarRoot.get(Calendar.YEAR);
        int compareYear = calenderCompare.get(Calendar.YEAR);
        if (compareYear < rootYear)
            return -1;
        else if (compareYear > rootYear)
            return 1;
        else {
            int rootDayOfYear = calendarRoot.get(Calendar.DAY_OF_YEAR);
            int compareDayOfYear = calenderCompare.get(Calendar.DAY_OF_YEAR);
            if (compareDayOfYear < rootDayOfYear)
                return -1;
            else if (compareDayOfYear > rootDayOfYear)
                return 1;
            else
                return 0;
        }
    }

    /**
     * Convert time string to other format
     *
     * @param time       in string
     * @param fromFormat
     * @param toFormat
     * @return
     */
    public static String convertFormatDateTime(String time, String fromFormat,
                                               String toFormat) {
        SimpleDateFormat from = new SimpleDateFormat(fromFormat,
                Locale.getDefault());
        from.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat to = new SimpleDateFormat(toFormat,
                Locale.getDefault());
        try {
            Date date = from.parse(time);
            return to.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Get time in GMT as template
     *
     * @param template date in GMT. Example: yyyyMMddHHmmssSSS
     * @return
     */
    public static String getTimeInGMT(String template) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(template,
                Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(date);
    }

    /**
     * Convert time in GMT to Locale.
     *
     * @param time Time to converted.
     * @return Time in Locale.
     */
    public static String convertGMTtoLocale(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS",
                Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date date = dateFormat.parse(time);
            dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS",
                    Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getDefault());
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert time in locale to GMT.
     *
     * @param time Time to converted.
     * @return Time in GMT.
     */
    public static String convertLocaleToGMT(String time) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS",
                Locale.getDefault());
        Date date;
        try {
            date = dateFormat.parse(time);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
    }

    public static String convertLocaleToGMTByFormat(String time, String toFormat) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss",
                Locale.getDefault());
        SimpleDateFormat toDateFormat = new SimpleDateFormat(toFormat,
                Locale.getDefault());
        Date date;
        try {
            date = dateFormat.parse(time);
            toDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return toDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return time;
        }
    }

    /**
     * Get {@link Date} in GMT.
     *
     * @return date in GMT.
     */
    public static Date getDateTimeInGMT() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS",
                Locale.getDefault());
        Date date = Calendar.getInstance().getTime();
        try {
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            String d = dateFormat.format(date);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "yyyyMMddHHmmssSSS", Locale.getDefault());
            return simpleDateFormat.parse(d);

        } catch (ParseException e) {
            e.printStackTrace();
            // if exception -> get time local
            return null;
        }
    }
}
