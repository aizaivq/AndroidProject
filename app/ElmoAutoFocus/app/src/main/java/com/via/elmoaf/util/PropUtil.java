package com.via.elmoaf.util;

import java.lang.reflect.Method;

/**
 * Created by nick on 17-9-5.
 */

public class PropUtil {
    public static String get(String key) {
        String value = "";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, key, value));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void set(String key, String value) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method set = c.getMethod("set", String.class, String.class);
            set.invoke(c, key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
