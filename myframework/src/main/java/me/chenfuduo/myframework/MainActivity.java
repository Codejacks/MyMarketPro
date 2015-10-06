package me.chenfuduo.myframework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add("duoduo" + i);
        }
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new MainAdapter(datas));
    }

    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, OtherActivity.class);
        startActivity(intent);
    }


    class MainAdapter extends DefaultAdapter<String> {

        public MainAdapter(List<String> datas) {
            super(datas);
        }

        @Override
        public BaseHolder getHolder() {
            return new MainHolder();
        }
    }


    class MainHolder extends BaseHolder<String> {
        TextView textView;
        @Override
        public View initView() {
            convertView = View.inflate(MainActivity.this, R.layout.list_item_main, null);
            this.textView = (TextView) convertView.findViewById(R.id.textView);
            return convertView;
        }

        @Override
        protected void refreshView(String data) {
            this.textView.setText(data);
        }
    }
}
