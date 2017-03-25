package cn.itsite.abase.demo.common;

import android.view.View;

import com.trycatch.mysnackbar.Prompt;
import com.trycatch.mysnackbar.TSnackbar;

/**
 * Author：leguang on 2016/11/11 0011 18:50
 * Email：langmanleguang@qq.com
 */
public class DialogHelper {
    private final String TAG = this.getClass().getSimpleName();

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
                .setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPromptThemBackground(Prompt.SUCCESS)
                .addIconProgressLoading(0, true, false)
                .show();
    }

    public static void warningSnackbar(View view, CharSequence text) {
        TSnackbar.make(view, text, TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN)
                .setPromptThemBackground(Prompt.WARNING)
                .show();
    }
}
