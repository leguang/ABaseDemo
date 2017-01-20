package cn.itsite.abase.log;

import android.util.Log;


/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class BaseLog {
    private final String TAG = this.getClass().getSimpleName();
    private static final int MAX_LENGTH = 4000;

    public static void printDefault(int type, String tag, String msg) {

        int index = 0;
        int length = msg.length();
        int countOfSub = length / MAX_LENGTH;

        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + MAX_LENGTH);
                printSub(type, tag, sub);
                index += MAX_LENGTH;
            }
            printSub(type, tag, msg.substring(index, length));
        } else {
            printSub(type, tag, msg);
        }
    }

    private static void printSub(int type, String tag, String sub) {
        switch (type) {
            case ALog.V:
                Log.v(tag, sub);
                break;
            case ALog.D:
                Log.d(tag, sub);
                break;
            case ALog.I:
                Log.i(tag, sub);
                break;
            case ALog.W:
                Log.w(tag, sub);
                break;
            case ALog.E:
                Log.e(tag, sub);
                break;
            case ALog.A:
                Log.wtf(tag, sub);
                break;
        }
    }

}
