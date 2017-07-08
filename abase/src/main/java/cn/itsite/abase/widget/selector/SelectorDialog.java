package cn.itsite.abase.widget.selector;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.aglhz.abase.R;


public class SelectorDialog extends Dialog {
    private Selector selector;

    public SelectorDialog(Context context) {
        super(context, R.style.bottom_dialog);
    }

    public SelectorDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public SelectorDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void init(Context context, Selector selector) {
        this.selector = selector;
        setContentView(selector.getView());
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = dip2px(context, 256);
        window.setAttributes(params);

        window.setGravity(Gravity.BOTTOM);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setOnAddressSelectedListener(SelectedListener listener) {
        this.selector.setSelectedListener(listener);
    }

    public static SelectorDialog show(Context context) {
        return show(context, null);
    }

    public static SelectorDialog show(Context context, SelectedListener listener) {
        SelectorDialog dialog = new SelectorDialog(context, R.style.bottom_dialog);
        dialog.selector.setSelectedListener(listener);
        dialog.show();

        return dialog;
    }

    public void setDataProvider(DataProvider dataProvider) {
        if (dataProvider != null) {
            selector.setDataProvider(dataProvider);
        }
    }

    public void setSelectedListener(SelectedListener listener) {
        if (listener != null) {
            selector.setSelectedListener(listener);
        }
    }

    public void reset() {
        if (selector != null) {
            selector.tabIndex = 0;
            for (int i = 0; i < selector.selectedIndex.length; i++) {
                selector.selectedIndex[i] = -1;
                selector.adapters[i].selectedIndex = -1;
                selector.tabs[i].setText("请选择");
                selector.adapters[i].notifyDataSetChanged();
            }
        }
    }
}
