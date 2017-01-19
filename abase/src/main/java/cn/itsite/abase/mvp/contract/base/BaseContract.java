package cn.itsite.abase.mvp.contract.base;

import android.support.annotation.UiThread;

/**
 * Author：leguang on 2016/10/10 0010 20:44
 * Email：langmanleguang@qq.com
 */
public interface BaseContract {
    interface View {
        void end();

        void error(Throwable t);

        void start();
    }

    interface Presenter {

        @UiThread
        void detachView();

        void start();

    }

    interface Model {
        void start();
    }
}
