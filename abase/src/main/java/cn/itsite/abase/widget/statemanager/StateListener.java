package cn.itsite.abase.widget.statemanager;

import android.view.View;

/**
 * Author：leguang on 2017/4/12 0009 15:49
 * Email：langmanleguang@qq.com
 *
 * 页面状态点击事件监听器。
 */

public interface StateListener {

    interface OnClickListener {
        void onClick(View view);
    }
}