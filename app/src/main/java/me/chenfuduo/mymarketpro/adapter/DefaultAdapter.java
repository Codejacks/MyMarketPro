package me.chenfuduo.mymarketpro.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import me.chenfuduo.mymarketpro.holder.BaseViewHolder;

/**
 * Created by chenfuduo on 2015/10/3.
 */
public abstract class DefaultAdapter<Data> extends BaseAdapter {

    protected List<Data> datas;

    public DefaultAdapter(List<Data> datas) {
        this.datas = datas;
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder<Data> viewHolder;
        if (convertView == null) {
            viewHolder = getHolder();
        } else {
            viewHolder = (BaseViewHolder<Data>) convertView.getTag();
        }


        Data data = datas.get(position);

        viewHolder.setData(data);

        return viewHolder.getConvertView();
    }

    public abstract BaseViewHolder<Data> getHolder();
}
