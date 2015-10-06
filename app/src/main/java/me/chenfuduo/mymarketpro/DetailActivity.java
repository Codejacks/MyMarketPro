package me.chenfuduo.mymarketpro;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import me.chenfuduo.mymarketpro.bean.AppInfo;
import me.chenfuduo.mymarketpro.protocol.DetailProtocol;
import me.chenfuduo.mymarketpro.view.LoadingPage;

public class DetailActivity extends AppCompatActivity {

    String packageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packageName = getIntent().getStringExtra("packageName");
        LoadingPage loadingPage = new LoadingPage(this) {
            @Override
            public LoadResult load() {
                return DetailActivity.this.load();
            }

            @Override
            protected View createSuccessView() {
                return DetailActivity.this.createSuccessView();
            }
        };

        setContentView(loadingPage);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private View createSuccessView() {
        return null;
    }

    private LoadingPage.LoadResult load() {
        DetailProtocol detailProtocol = new DetailProtocol(packageName);
        AppInfo appInfo = detailProtocol.load(0);
        if (appInfo == null){
            return LoadingPage.LoadResult.error;
        }else{
            return LoadingPage.LoadResult.success;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
