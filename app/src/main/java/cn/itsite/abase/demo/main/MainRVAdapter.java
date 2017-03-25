package cn.itsite.abase.demo.main;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.itsite.abase.demo.R;

/**
 * Author：leguang on 2016/10/9 0009 15:49
 * Email：langmanleguang@qq.com
 */
public class MainRVAdapter extends BaseQuickAdapter<MainBean, BaseViewHolder> {
    private static final String TAG = MainRVAdapter.class.getName();

    public MainRVAdapter(int layoutResId, List<MainBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, MainBean item) {
        holder.setText(R.id.tv_title, item.title)
                .setText(R.id.tv_description, item.description);
    }
}
