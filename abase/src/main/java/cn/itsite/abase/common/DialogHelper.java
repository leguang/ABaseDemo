package cn.itsite.abase.common;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import com.trycatch.mysnackbar.Prompt;
import com.trycatch.mysnackbar.TSnackbar;

import cn.itsite.adialog.dialog.LoadingDialog;


/**
 * Author：leguang on 2016/11/11 0011 18:50
 * Email：langmanleguang@qq.com
 */
public class DialogHelper {
    private static final String TAG = DialogHelper.class.getSimpleName();

    public static void errorSnackbar(View view, CharSequence text) {
        TSnackbar.make(view, text, TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN)
                .setPromptThemBackground(Prompt.ERROR)
                .show();
    }

    public static void successSnackbar(View view, CharSequence text) {
        TSnackbar.make(view, text, TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN)
                .setPromptThemBackground(Prompt.SUCCESS)
                .show();
    }

    public static void loadingSnackbar(View view, CharSequence text) {
        TSnackbar.make(view, text, TSnackbar.LENGTH_INDEFINITE, TSnackbar.APPEAR_FROM_TOP_TO_DOWN)
                .setAction("取消", null)
                .setPromptThemBackground(Prompt.SUCCESS)
                .addIconProgressLoading(0, true, false)
                .show();
    }

    public static void warningSnackbar(View view, CharSequence text) {
        TSnackbar.make(view, text, TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN)
                .setPromptThemBackground(Prompt.WARNING)
                .show();
    }

    public static Dialog loading(Activity activity) {
        return new LoadingDialog(activity);
    }
}
