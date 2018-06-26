package com.anton.kursach.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateMapping {

    private static String standardDateFormat = "MM/dd/yyyy"; //mm-dd-yyyy

    public static Long formatDateWithoutTime(Long date) {
        Long dateWithoutTime = null;
        try {
            dateWithoutTime = new SimpleDateFormat(standardDateFormat).parse(
                    new SimpleDateFormat(standardDateFormat).format(date)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateWithoutTime;
    }

    public static String formatDateToString(Long date) {
        return new SimpleDateFormat(standardDateFormat).format(date);
    }

}
