package cn.itsite.abase.demo.architecture.contract;


import cn.itsite.abase.mvp.contract.base.BaseContract;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 */
public interface MineContract {

    interface View extends BaseContract.View {
    }

    interface Presenter extends BaseContract.Presenter {
    }

    interface Model extends BaseContract.Model {
    }
}