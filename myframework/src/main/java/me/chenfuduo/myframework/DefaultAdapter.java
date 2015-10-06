package me.chenfuduo.myframework;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by chenfuduo on 2015/10/4.
 */
public abstract class DefaultAdapter<T> extends BaseAdapter {

    private List<T> datas;

    public DefaultAdapter(List<T> datas) {
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
        BaseHolder mainHolder;
        if (convertView == null) {
            mainHolder = getHolder();
        } else {
            mainHolder = (BaseHolder) convertView.getTag();
        }
        T str = datas.get(position);
        mainHolder.setData(str);
        return mainHolder.getConvertView();
    }

    public abstract BaseHolder getHolder();
}
