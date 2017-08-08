package common.libraries;

import android.content.Context;
import android.os.Handler;

/**
 * Created by zipdoc on 2017. 4. 21..
 */

public class Toast {

    private static Handler handler;
    private static Context context;
    private static boolean debug;

    public static void init(Context _context, Handler _handler, boolean debug) {
        handler = _handler;
        context = _context;
        Toast.debug = debug;
    }

    public static void debug(String text) {
        debug(text, false);
    }

    /**
     *
     * @param text
     * @param flag / long or short
     */
    public static void debug(String text, boolean flag) {
        if (debug) {
            show(text, flag);
        }
    }

    public static void show(String text) {
        show(text, false);
    }

    public static void show(int res) {
        show(context.getResources().getString(res), false);
    }

    public static void show(final String text, final boolean flag) {

        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                android.widget.Toast.makeText(context, text,
                        (flag == true) ? android.widget.Toast.LENGTH_LONG : android.widget.Toast.LENGTH_SHORT).show();
            }

        });
    }
}
