package me.chenfuduo.mymarketpro.holder;

import android.view.View;
import android.widget.RelativeLayout;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.adapter.DefaultAdapter;
import me.chenfuduo.mymarketpro.utils.UIUtils;

/**
 * Created by chenfuduo on 2015/10/3.
 */
public class MoreViewHolder extends BaseViewHolder<Integer>{
    public static final int HAS_NO_MORE = 0;
    public static final int LOAD_ERROR = 1;
    public static final int HAS_MORE = 2;


    private RelativeLayout rl_more_loading,rl_more_error;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.load_more);
        rl_more_loading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);
        rl_more_error = (RelativeLayout) view.findViewById(R.id.rl_more_error);
        return view;
    }


    private DefaultAdapter adapter;

    public MoreViewHolder(DefaultAdapter adapter) {
        super();
        this.adapter = adapter;
    }


    @Override
    public View getConvertView() {
        loadMore();
        return super.getConvertView();
    }

    private void loadMore() {
        adapter.loadMore();
    }

    @Override
    public void refreshView(Integer integer) {
        rl_more_error.setVisibility(data==LOAD_ERROR?View.VISIBLE:View.GONE);
        rl_more_loading.setVisibility(data==HAS_MORE?View.VISIBLE:View.GONE);
    }
}
