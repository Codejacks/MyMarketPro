package me.chenfuduo.mymarketpro.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.bean.SubjectInfo;
import me.chenfuduo.mymarketpro.holder.BaseViewHolder;
import me.chenfuduo.mymarketpro.http.HttpHelper;
import me.chenfuduo.mymarketpro.utils.BitmapHelper;
import me.chenfuduo.mymarketpro.utils.UIUtils;

/**
 * Created by chenfuduo on 2015/10/3.
 */
public class SubjectAdapter extends DefaultAdapter<SubjectInfo> {

    public SubjectAdapter(List<SubjectInfo> subjectInfos) {
        super(subjectInfos);
    }

    @Override
    public BaseViewHolder<SubjectInfo> getHolder() {
        return new ViewHolder();
    }

    static class ViewHolder extends BaseViewHolder<SubjectInfo>{
        ImageView item_icon;
        TextView item_txt;

        @Override
        public View initView() {
            View convertView = View.inflate(UIUtils.getContext(), R.layout.subject_item, null);
            this.item_icon = (ImageView) convertView.findViewById(R.id.item_icon);
            this.item_txt = (TextView) convertView.findViewById(R.id.item_txt);
            return convertView;
        }

        @Override
        public void refreshView(SubjectInfo data) {
            String des = data.getDes();
            String url = data.getUrl();
            this.item_txt.setText(des);
            BitmapUtils bitmapUtils = BitmapHelper.getInstance();
            bitmapUtils.display(this.item_icon, HttpHelper.URL + "image?name=" + url);
        }

    }

}
