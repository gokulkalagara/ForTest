package com.maya.testfrost.utils;

import android.util.Log;



public class Logger {
    public static boolean logStatus = true;
    public static final String TAG = "VIDEO PLAYER";

    private Logger() {

    }

    public static void error(String s, Throwable throwable) {
        if (logStatus) {
            Log.e(TAG, s, throwable);
        }

    }

    public static void e(String s) {
        if (logStatus) {
            Log.e(TAG, "" + s);
        }
    }

    public static void e(String tag, String s) {
        if (logStatus) {
            Log.e(tag, "" + s);
        }


    }

    public static void d(String s) {
        if (logStatus) {
            Log.d(TAG, "" + s);
        }


    }

    public static void d(String tag, String s) {
        if (logStatus) {
            Log.d(tag, "" + s);
        }
    }

    public static void w(String s) {
        if (logStatus) {
            Log.w(TAG, "" + s);
        }

    }

    public static void w(String tag, String s) {
        if (logStatus) {
            Log.w(tag, "" + s);
        }

    }

    public static void i(String s) {
        if (logStatus) {
            Log.i(TAG, "" + s);
        }


    }

    public static void i(String tag, String s) {
        if (logStatus) {
            Log.w(tag, "" + s);
        }

    }


    public static void v(String s) {
        if (logStatus) {
            Log.v(TAG, "" + s);
        }
    }

    public static void v(String tag, String s) {
        if (logStatus) {
            Log.v(tag, "" + s);
        }
    }

    public static void e(String zup, String s, Throwable e) {
        if (logStatus) {
            Log.e(zup, s, e);
        }
    }
}
