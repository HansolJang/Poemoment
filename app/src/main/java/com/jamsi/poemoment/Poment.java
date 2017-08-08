package com.jamsi.poemoment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import common.libraries.Singleton;
import common.libraries.Toast;

/**
 * Created by zipdoc on 2017. 4. 23..
 */

public class Poment extends Application {

    private static final String tag = Poment.class.getSimpleName();

    private static Poment instance;
    private static volatile Activity currentActivity = null;
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        Poment.currentActivity = currentActivity;
    }

    public static Poment getGlobalApplicationContext() {
        if(instance == null)
            throw new IllegalStateException("this application does not inherit GlobalApplication");
        return instance;
    }



    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(tag, "onCreate");

        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandlerApplication());

        Toast.init(this, new Handler(), Config._DEBUG);

        initRuntimeValues();
//        initImageLoader(this);

        loadFont();

        instance = this;

    }

    private String getStackTrace(Throwable th) {

        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);

        Throwable cause = th;
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        final String stacktraceAsString = result.toString();
        printWriter.close();

        return stacktraceAsString;
    }

    private class UncaughtExceptionHandlerApplication implements Thread.UncaughtExceptionHandler{

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {

            String error = getStackTrace(ex);
            /*
            PendingIntent intent = PendingIntent.getActivity(getApplicationContext(),
                    192837, new Intent(getApplicationContext(), BugReportActivity.class),
                    PendingIntent.FLAG_ONE_SHOT);

            AlarmManager alarmManager;
            alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    1000, intent);
            */

            Intent intent = new Intent(getApplicationContext(), BugReportActivity.class);
            if (error.length() > 4096) {
                error = error.substring(0, 4096);
            }
            Log.e(tag, error);

            intent.putExtra("stack_trace", error);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(2);

            //mUncaughtExceptionHandler.uncaughtException(thread, ex);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        Log.d(tag, "onTerminate");
    }

    private void initRuntimeValues() {
        try {
            Storage storage = Singleton.getInstance(Storage.class);
            storage.setContext(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFont() {
        TYPEDEF.POMENT_MAIN_FONT = Typeface.createFromAsset(this.getAssets(), "BareunBatangOTFM.otf");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
