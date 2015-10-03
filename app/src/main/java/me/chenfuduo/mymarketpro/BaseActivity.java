package me.chenfuduo.mymarketpro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    //增删比较快
    static List<BaseActivity> baseActivityList = new LinkedList<>();

    private KillAllActivityReceiver receiver;


    public static final String KILL_ALL_ACTIVITY_ACTION = "me.chenfuduo.mymarketpro.KILL_ALL";

    class KillAllActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        receiver = new KillAllActivityReceiver();
        IntentFilter filter = new IntentFilter(KILL_ALL_ACTIVITY_ACTION);
        registerReceiver(receiver, filter);
        synchronized (baseActivityList) {
            baseActivityList.add(this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (baseActivityList) {
            baseActivityList.remove(this);
        }

        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }

    }


    public void killAllActivity() {
        List<BaseActivity> copyList;
        synchronized (baseActivityList) {
            copyList = new LinkedList<>(baseActivityList);
        }
        for (BaseActivity activity : copyList) {
            activity.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
