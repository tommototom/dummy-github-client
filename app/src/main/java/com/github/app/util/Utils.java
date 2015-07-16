package com.github.app.util;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

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
}
