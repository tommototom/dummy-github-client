package com.github.app.util;

import android.net.Uri;
import android.util.TimeUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Utils {
    public static Map<String, String> splitUriQuery(Uri uri) {
        return splitUriQuery(uri.getQuery());
    }

    public static Map<String, String> splitUriQuery(String query) {
        Map<String, String> map = new HashMap<>(6);

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int i = pair.indexOf('=');
            map.put(pair.substring(0, i), pair.substring(i + 1));
        }

        return map;
    }

    public static long days(Long date) {
        return TimeUnit.DAYS.toDays(date);
    }

    public static boolean haveSameDay(long date1, long date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

}
