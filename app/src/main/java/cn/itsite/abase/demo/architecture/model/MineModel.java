package cn.itsite.abase.demo.architecture.model;


import cn.itsite.abase.demo.architecture.contract.MineContract;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.model.base.BaseModel;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 */

public class MineModel extends BaseModel implements MineContract.Model {
    public final String TAG = MineModel.class.getSimpleName();

    @Override
    public void start(Object request) {
        super.start(request);
    }
}