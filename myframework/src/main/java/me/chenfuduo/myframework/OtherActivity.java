package me.chenfuduo.myframework;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class OtherActivity extends AppCompatActivity {

    private ListView listView;

    private List<Drawable> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        listView = (ListView) findViewById(R.id.list);
        datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
            datas.add(drawable);
        }

        listView.setAdapter(new OtherAdapter(datas));
    }

    class OtherAdapter extends DefaultAdapter<Drawable> {


        public OtherAdapter(List<Drawable> datas) {
            super(datas);
        }

        @Override
        public BaseHolder getHolder() {
            return new OtherHolder();
        }
    }


    class OtherHolder extends BaseHolder<Drawable> {
        ImageView imageView;
        @Override
        public View initView() {
            convertView = View.inflate(OtherActivity.this, R.layout.list_item_other, null);
            this.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            return convertView;
        }
        @Override
        protected void refreshView(Drawable data) {
            this.imageView.setImageDrawable(data);
        }
    }
}
