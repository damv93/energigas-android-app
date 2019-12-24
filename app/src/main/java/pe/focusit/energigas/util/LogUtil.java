package pe.focusit.energigas.util;

import android.util.Log;

import pe.focusit.energigas.BuildConfig;

public class LogUtil {

    private static boolean showLog = false;

    public static void v(String tag, String message) {
        if (BuildConfig.DEBUG || showLog) {
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG || showLog) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (BuildConfig.DEBUG || showLog) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (BuildConfig.DEBUG || showLog) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (BuildConfig.DEBUG || showLog) {
            Log.e(tag, message);
        }
    }

    public static void e(String tag, String message, Throwable exception) {
        if (BuildConfig.DEBUG || showLog) {
            Log.e(tag, message, exception);
        }
    }
    
}
