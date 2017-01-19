package cn.itsite.abase.common;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Author：leguang on 2016/11/11 0011 18:50
 * Email：langmanleguang@qq.com
 */
public class DialogHelper {
    private static final String TAG = DialogHelper.class.getSimpleName();

    private static AlertDialog.Builder mBuilder;
    /**
     * Show the TSnackbar from top to down.
     */
    public static final int APPEAR_FROM_TOP_TO_DOWN = 0;

    /**
     * Show the TSnackbar from top to down.
     */
    public static final int APPEAR_FROM_BOTTOM_TO_TOP = 1;


    public static void dialog(Context context, int icon, String title, String content, String positiveText, String negativeText, final Call call) {
        if (mBuilder == null && context != null) {
            mBuilder = new AlertDialog.Builder(context);
        }
        mBuilder.setIcon(icon)
                .setTitle(title)
                .setMessage(content)
                .setNegativeButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        call.onNegative();
                    }
                })
                .setPositiveButton(negativeText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        call.onPositive();
                    }
                }).show();
    }

    public static void dialog(Context context, int icon, int title, int content, int positiveText, int negativeText, final Call call) {
        if (mBuilder == null && context != null) {
            mBuilder = new AlertDialog.Builder(context);
        }
        mBuilder.setIcon(icon)
                .setTitle(title)
                .setMessage(content)
                .setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        call.onNegative();
                    }
                })
                .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        call.onPositive();
                    }
                }).show();
    }


    public interface Call {
        void onNegative();

        void onPositive();
    }

    public interface ListCall {
        void onSelection(Dialog dialog, View itemView, int which, CharSequence text);
    }
}
