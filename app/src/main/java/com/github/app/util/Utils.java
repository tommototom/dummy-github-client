package com.github.app.util;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;
import com.github.app.util.net.Connectivity;

import java.util.*;
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

    public static void notifyNetworkIssues(Context context, Exception ex) {
        String messageText;
        if (!Connectivity.isConnected(context)) {
            messageText = "Network issues";
        } else if (ex.getMessage().contains("404")) {
            messageText = "Not found";
        } else if (ex.getMessage().contains("403") || ex.getMessage().contains("401")) {
            messageText = "Access issues";
        } else {
            messageText = "Unknown error";
        }

        Toast.makeText(context, messageText, Toast.LENGTH_SHORT).show();
    }
}
