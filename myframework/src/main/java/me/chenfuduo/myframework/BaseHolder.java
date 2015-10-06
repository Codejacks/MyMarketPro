package me.chenfuduo.myframework;

import android.view.View;

/**
 * Created by chenfuduo on 2015/10/5.
 */
public abstract class BaseHolder<T> {

    protected T data;

    protected View convertView;
    public BaseHolder() {
        convertView = initView();
        convertView.setTag(this);
    }

    public void setData(T data) {
        this.data = data;
        refreshView(data);
    }


    public View getConvertView() {
        return convertView;
    }

    protected abstract void refreshView(T data);

    public abstract View initView();
}
