# 2015.10.01上午
## 环境搭建
将WebInfo导入到SD卡根目录,启动WebServer服务，模拟服务器。
## ActionBar的显示

## ActionBar的搜索功能
配置下`/res/menu/menu_main.xml`文件：
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".MainActivity">
    <item
        android:id="@+id/action_settings"
        android:orderInCategory="100"
        android:title="@string/action_settings"
        app:showAsAction="never" />
    <item
        android:icon="@drawable/ic_action_search"
        android:id="@+id/action_search"
        android:orderInCategory="100"
        android:title="@string/action_search"
        app:showAsAction="ifRoom"
        app:actionViewClass="android.support.v7.widget.SearchView"
        />
</menu>
```
接着在Activity中,实现`SearchView.OnQueryTextListener`接口,并实现该接口的两个方法：
```java
@Override
    public boolean onQueryTextSubmit(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        return true;
    }
```
接着找到`SearchView`:
```java
@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_search){
            Toast.makeText(MainActivity.this,"搜搜",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
```

## ActionBar的返回功能

ActionBar的返回功能有两种处理方式。假如现在有两个Activity,一个是MainActivity，一个是TestActvity,现在从MainActivity点击跳转到
TestActivity,在TestActivity的ActionBar上有返回上一级的图标,点击这个图标,既可以返回到MainActivity。

首先在TestActivity中得到这个图标：
```java
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
```

那么现在这个图标需要响应返回上一级的功能。
一种处理方式是这样的：
```java
@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }else if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
```
这个图标的id就是`android.R.id.home`。

其二的处理方式是这样的,在清单文件中配置:
```xml
        <activity
            android:name=".TestActivity"
            android:label="@string/title_activity_test"
            android:parentActivityName=".MainActivity">
            <meta-data
                android:name="android.support.PARENT_ACTIVITY"
                android:value=".MainActivity">
            </meta-data>
        </activity>
```

## TabLayout,DrawerLayout,NavigationView,Toolbar搭建程序主界面

![效果图](http://7xljei.com1.z0.glb.clouddn.com/hibirad.gif)

布局：
```xml
<android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:orientation="vertical"
        android:layout_height="match_parent">


        <android.support.v7.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            android:background="?attr/colorPrimary"
            android:fitsSystemWindows="true" />


        <android.support.design.widget.TabLayout
            android:id="@+id/tabLayout"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="?attr/colorPrimary"
            android:scrollbars="horizontal"
            app:layout_scrollFlags="scroll|enterAlways" />

        <android.support.v4.view.ViewPager
            android:id="@+id/viewpager"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@android:color/white" />

    </LinearLayout>


    <android.support.design.widget.NavigationView
        android:id="@+id/nvView"
        android:layout_width="200dp"
        android:layout_height="match_parent"
        android:fitsSystemWindows="true"
        android:layout_gravity="start"
        android:background="@color/deep_purple_600"
        app:menu="@menu/drawer_view" />


</android.support.v4.widget.DrawerLayout>
```

主界面：

```java
package me.chenfuduo.mymarketpro;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import me.chenfuduo.mymarketpro.adapter.MyTestPagerAdapter;

public class TestActivity extends AppCompatActivity {


    private MyTestPagerAdapter pagerAdapter;

    private ViewPager viewPager;


    TabLayout tabLayout;


    private DrawerLayout mDrawer;

    private NavigationView nvDrawer;

    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        pagerAdapter = new MyTestPagerAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);

        setupToolbar();
        setupTablayout();

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        drawerToggle = setupDrawerToggle();

        mDrawer.setDrawerListener(drawerToggle);

        setupDrawerContent(nvDrawer);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        drawerToggle.syncState();

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }


    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView nvDrawer) {

        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
    }

    private void selectDrawerItem(MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                break;
            case R.id.nav_second_fragment:
                break;
            case R.id.nav_third_fragment:
                break;
            case R.id.nav_fourth_fragment:
                break;

            default:
        }


        menuItem.setChecked(true);
        //setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    private void setupTablayout() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }else if(id == android.R.id.home){
            mDrawer.openDrawer(GravityCompat.START);
            return true;
        }else if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
```

辅助Adapter：
```java
package me.chenfuduo.mymarketpro.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.chenfuduo.mymarketpro.fragment.MyTestFragment;

/**
 * Created by chenfuduo on 2015/9/17.
 */
public class MyTestPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"科大","工大","安大"};
    private Context context;

    public MyTestPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return MyTestFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }



}
```

辅助Fragment：
```java
package me.chenfuduo.mymarketpro.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by chenfuduo on 2015/10/1.
 */
public class MyTestFragment extends Fragment {

    private static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    public static MyTestFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MyTestFragment myFragment = new MyTestFragment();
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(16);


        if (mPage == 1) {
            //科大
            tv.setText("科大");
        } else if (mPage == 2) {
            //工大
            tv.setText("工大");
        } else if (mPage == 3) {
            //安大
            tv.setText("安大");

        }

        return tv;
    }
}
```

侧滑菜单的menu布局：
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <group android:checkableBehavior="single">

        <item
            android:id="@+id/nav_first_fragment"
            android:title="精品应用"
            app:itemTextColor="@color/teal_500" />

        <item
            android:id="@+id/nav_second_fragment"

            android:title="玩机器人"
            app:itemTextColor="@color/teal_500" />

        <item
            android:id="@+id/nav_third_fragment"

            android:title="给我建议"
            app:itemTextColor="@color/teal_500" />

        <item
            android:id="@+id/nav_fourth_fragment"

            android:title="关于软件"
            app:itemTextColor="@color/teal_500" />

    </group>

</menu>
```

该Activity的主题为：
```xml
 <style name="Base.Theme.Test" parent="Theme.AppCompat.Light.NoActionBar">

        <item name="colorPrimary">@color/teal_500</item>

        <!--   darker variant for the status bar and contextual app bars -->
        <item name="colorPrimaryDark">@color/teal_400</item>

        <!--   theme UI controls like checkboxes and text fields e.g. FloatActionButton -->
        <item name="colorAccent">@color/teal_900</item>

        <!-- Title Text Color -->
        <item name="android:textColorPrimary">@color/my_primary_text</item>

        <!-- color of the menu overflow icon (three vertical dots) -->
        <item name="android:textColorSecondary">@color/my_secondary_text</item>


        <item name="android:windowBackground">@color/white</item>
    </style>
```
# 2015.10.01晚上

## 主界面

晚上使用`TabLayout`,`DrawerLayout`,`NavigationView`,`Toolbar`,`ViewPager`搭建程序主界面。
![效果图如下](http://7xljei.com1.z0.glb.clouddn.com/mainframe.gif)
具体需要注意的是：由于tab的页卡比较多，因此在实现的时候，注意下面的：
```java
//如果是三个，则是下面的固定的模式
//tabLayout.setTabMode(TabLayout.MODE_FIXED);
tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
```
其他的和上午的处理一致，此处是模版代码，以后可以直接拿来用。


## 抽取BaseActivity
主要是管理Activity方便，没有做UI方面的，比如统一的initView啥的。
```java
package me.chenfuduo.mymarketpro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    //增删比较快
    static List<BaseActivity> baseActivityList = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
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
```

## FragmentFactory

Fragment的工场，我这里使用`ArrayMap`替换`HashMap`，做了优化。(`SparseArray`也可以)
```java
package me.chenfuduo.mymarketpro.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.util.Log;


/**
 * Created by chenfuduo on 2015/10/1.
 */
public class FragmentFactory {

    private static final String TAG = FragmentFactory.class.getSimpleName();
    private static ArrayMap<Integer, Fragment> fragmentArrayMap = new ArrayMap<>();

    public static Fragment createFragment(int position) {
        Fragment fragment;
        fragment = fragmentArrayMap.get(position);
        if (fragment == null) {

            Log.e(TAG, "createFragment " + "Fragment为null执行");

            if (position == 0) {
                fragment = new HomeFragment();
            } else if (position == 1) {
                fragment = new AppFragment();
            } else if (position == 2) {
                fragment = new GameFragment();
            } else if (position == 3) {
                fragment = new SubjectFragment();
            } else if (position == 4) {
                fragment = new RecommentFragment();
            } else if (position == 5) {
                fragment = new CategoryFragment();
            } else if (position == 6) {
                fragment = new HotFragment();
            }
            if (fragment != null) {
                fragmentArrayMap.put(position, fragment);
            }

        }


        return fragment;

    }

}

```
在adapter中使用：
```java
package me.chenfuduo.mymarketpro.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.fragment.FragmentFactory;
import me.chenfuduo.mymarketpro.fragment.MyTestFragment;

/**
 * Created by chenfuduo on 2015/9/17.
 */
public class MyTestPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[];
    private Context context;

    public MyTestPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabTitles = context.getResources().getStringArray(R.array.tab_names);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createFragment(position);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }



}
```

## 广播退出Activity

在BaseActivity中：
```java
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


    private static final String KILL_ALL_ACTIVITY_ACTION = "me.chenfuduo.mymarketpro.KILL_ALL";

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
```
当在其他的Activity中，想要退出时：
```java
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent(BaseActivity.KILL_ALL_ACTIVITY_ACTION));
    }
```
# 2015.10.02上午

## 显示界面的架子

在HomeFragment中：
```java
 @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, " HomeFragment onCreateView ");
        frameLayout = new FrameLayout(getActivity());
        init();
        showPage();
        show();
        return frameLayout;
    }
```
每一个界面都有：加载中,加载成功,加载失败,加载数据为空这几种不同的布局,将这些布局添加到FrameLayout中进行管理。

```java
//加载中
    private View loadingView;
    //加载失败
    private View errorView;
    //为空
    private View emptyView;
    //加载成功
    private View successView;


    //在FrameLayout中添加四种界面。加载成功;加载失败;加载成功,但是没数据;加载中。
    private void init() {
        loadingView = createLoadingView();
        if (loadingView != null) {
            frameLayout.addView(loadingView, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        errorView = createErrorView();
        if (errorView != null) {
            frameLayout.addView(errorView, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        emptyView = createEmptyView();
        if (emptyView != null) {
            frameLayout.addView(emptyView, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }
```
去加载不同的布局：
```java
    //加载中的布局
    private View createLoadingView() {
        View view = View.inflate(getActivity(), R.layout.loading_page_loading, null);
        return view;
    }

    //加载失败的布局
    private View createErrorView() {
        View view = View.inflate(getActivity(), R.layout.loading_page_error, null);
        return view;
    }

    //为空的布局
    private View createEmptyView() {
        View view = View.inflate(getActivity(), R.layout.loading_page_empty, null);
        return view;
    }
```
## 五种状态
```java
//五种状态
    static final int STATE_UNKOWN = 0;
    static final int STATE_LOADING = 1;
    static final int STATE_ERROR = 2;
    static final int STATE_EMPTY = 3;
    static final int STATE_SUCCESS = 4;

    static int state = STATE_UNKOWN;
```

根据不同状态,显示不同的界面

```java
    //根据不同状态,显示不同的界面
    //五种状态,未知;加载中;错误;空;加载成功
    private void showPage() {
        if (loadingView != null) {
            loadingView.setVisibility(
                    state == STATE_UNKOWN || state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        }
        if (errorView != null) {
            errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        }

    }
```


请求服务器数据,得到状态,这里只做模拟，后面再去修改。

```java
    //请求服务器,服务器返回只有三种状态
    //错误,空,成功
    public enum LoadResult {
        error(2), empty(3), success(4);
        int value;

        //枚举类的构造器只能使用private
        LoadResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    //根据服务器的数据,切换状态
    private void show() {

        if (state == STATE_ERROR || state == STATE_EMPTY){
            state = STATE_LOADING;
        }


        //请求服务器,获取服务器上的数据,进行判断
        //请求服务器,返回一个结果
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                final LoadResult result = load();

                //这里报过空指针
                //当快速退出,快速进入的时候,偶尔出现
                //可能与Fragment工场的缓存有关
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null) {
                            state = result.getValue();
                            //状态改变了,重新判断当前应该显示哪个界面
                            showPage();
                        }
                    }
                });
            }
        }).start();
        showPage();
    }

    private LoadResult load() {
        return LoadResult.error;

    }
```

![效果图](http://7xljei.com1.z0.glb.clouddn.com/frame1002.gif)

## 复用FrameLayout的问题

log日志是：
```
java.lang.IllegalStateException The specified child already has a parent.
You must call removeView() on the child's parent first.
```
解决方法：
```java
 @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, " HomeFragment onCreateView ");
        if (frameLayout == null) {
            frameLayout = new FrameLayout(getActivity());
            init();
        }else{
            ViewUtils.removeSelfFromParent(frameLayout);
        }
        showPage();
        show();
        return frameLayout;
    }
```

把自身从父View中移除:

```java
    /** 把自身从父View中移除 */
	public static void removeSelfFromParent(View view) {
		if (view != null) {
			ViewParent parent = view.getParent();
			if (parent != null && parent instanceof ViewGroup) {
				ViewGroup group = (ViewGroup) parent;
				group.removeView(view);
			}
		}
	}
```

# 2015.10.02下午

## 抽取到BaseFragment中
上午在HomeFragment中写了一大堆的代码,但是在其他的Fragment中,仍然用得到这些,将其抽取到BaseFragment中：
```java
package me.chenfuduo.mymarketpro.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.utils.ViewUtils;

/**
 * Created by chenfuduo on 2015/10/2.
 */
public abstract class BaseFragment extends Fragment {
    //五种状态
    static final int STATE_UNKOWN = 0;
    static final int STATE_LOADING = 1;
    static final int STATE_ERROR = 2;
    static final int STATE_EMPTY = 3;
    static final int STATE_SUCCESS = 4;

    //不能使用static
    // static int state = STATE_UNKOWN;

    int state = STATE_UNKOWN;


    //四种界面
    //加载中
    private View loadingView;
    //加载失败
    private View errorView;
    //为空
    private View emptyView;
    //加载成功
    private View successView;


    FrameLayout frameLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (frameLayout == null) {
            frameLayout = new FrameLayout(getActivity());
            init();
        } else {
            ViewUtils.removeSelfFromParent(frameLayout);
        }
        return frameLayout;
    }


    //在FrameLayout中添加四种界面。加载成功;加载失败;加载成功,但是没数据;加载中。
    private void init() {
        loadingView = createLoadingView();
        if (loadingView != null) {
            frameLayout.addView(loadingView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }

        errorView = createErrorView();
        if (errorView != null) {
            frameLayout.addView(errorView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }

        emptyView = createEmptyView();
        if (emptyView != null) {
            frameLayout.addView(emptyView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
        showPage();// 根据不同的状态显示不同的界面
    }


    //根据不同状态,显示不同的界面
    //五种状态,未知;加载中;错误;空;加载成功
    private void showPage() {
        if (loadingView != null) {
            loadingView.setVisibility(
                    state == STATE_UNKOWN || state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        }
        if (errorView != null) {
            errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        }


        if (state == STATE_SUCCESS) {
            successView = createSuccessView();
            if (successView != null) {
                frameLayout.addView(successView, new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

                successView.setVisibility(View.VISIBLE);
            }
        } else {
            if (successView != null) {
                successView.setVisibility(View.INVISIBLE);
            }
        }
    }


    //加载中的布局
    private View createLoadingView() {
        View view = View.inflate(getActivity(), R.layout.loading_page_loading, null);
        return view;
    }

    //加载失败的布局
    private View createErrorView() {
        View view = View.inflate(getActivity(), R.layout.loading_page_error, null);
        Button page_bt = (Button) view.findViewById(R.id.page_bt);
        page_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        return view;
    }

    //为空的布局
    private View createEmptyView() {
        View view = View.inflate(getActivity(), R.layout.loading_page_empty, null);
        return view;
    }

    //请求服务器,服务器返回只有三种状态
    //错误,空,成功
    public enum LoadResult {
        error(2), empty(3), success(4);
        int value;

        //枚举类的构造器只能使用private
        LoadResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    //根据服务器的数据,切换状态
    public void show() {

        if (state == STATE_ERROR || state == STATE_EMPTY) {
            state = STATE_LOADING;
        }


        //请求服务器,获取服务器上的数据,进行判断
        //请求服务器,返回一个结果
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1500);
                final LoadResult result = load();

                //这里报过空指针
                //当快速退出,快速进入的时候,偶尔出现
                //可能与Fragment工场的缓存有关

                if (getActivity() != null) {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result != null) {
                                state = result.getValue();
                                //state = 2 + new Random().nextInt(3);
                                //状态改变了,重新判断当前应该显示哪个界面
                                showPage();
                            }
                        }
                    });
                }
            }
        }).start();
        showPage();
    }

    /**
     * 请求服务器数据
     *
     * @return
     */
    public abstract LoadResult load();

    /**
     * 创建成功的界面
     *
     * @return
     */
    protected abstract View createSuccessView();


}
```

然后HomeFragment以及其他的几个Fragment继承自BaseFragment：
```java
package me.chenfuduo.mymarketpro.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by chenfuduo on 2015/10/1.
 */
public class HomeFragment extends BaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();

    @Override
    protected View createSuccessView() {
        TextView tv = new TextView(getActivity());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(16);
        tv.setText("成功的界面");
        return tv;
    }

    @Override
    public LoadResult load() {
        return LoadResult.success;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();
    }
}
```
前面的Fragment工厂也修改如下：
```java
package me.chenfuduo.mymarketpro.fragment;

import android.support.v4.util.ArrayMap;
import android.util.Log;


/**
 * Created by chenfuduo on 2015/10/1.
 */
public class FragmentFactory {

    private static final String TAG = FragmentFactory.class.getSimpleName();
    private static ArrayMap<Integer, BaseFragment> fragmentArrayMap = new ArrayMap<>();

    //ArrayMap和SparseArray都能达到优化
   // private static SparseArray<Fragment> sparseFragments = new SparseArray<>();

    public static BaseFragment createFragment(int position) {
        BaseFragment fragment;
        fragment = fragmentArrayMap.get(position);
        //sparseFragments.get(position);
        // sparseFragments.put(position,fragment);
        if (fragment == null) {

            Log.e(TAG, "createFragment " + "Fragment为null执行");

            if (position == 0) {
                fragment = new HomeFragment();
            } else if (position == 1) {
                fragment = new AppFragment();
            } else if (position == 2) {
                fragment = new GameFragment();
            } else if (position == 3) {
                fragment = new SubjectFragment();
            } else if (position == 4) {
                fragment = new RecommentFragment();
            } else if (position == 5) {
                fragment = new CategoryFragment();
            } else if (position == 6) {
                fragment = new HotFragment();
            }
            if (fragment != null) {
                fragmentArrayMap.put(position, fragment);
            }
        }
        return fragment;
    }
}
```

同时MainActivity的ViewPager在滑动的时候,让其重新加载：
```java
viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment =
                        FragmentFactory.createFragment(position);
                fragment.show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
```

## 抽取到LoadingPage

出现了下面的错误:
```
java.lang.NullPointerException
            at android.view.LayoutInflater.from(LayoutInflater.java:210)
            at android.view.View.inflate(View.java:16118)
            at me.chenfuduo.mymarketpro.view.LoadingPage.createLoadingView(LoadingPage.java:108)
            at me.chenfuduo.mymarketpro.view.LoadingPage.init(LoadingPage.java:56)
            at me.chenfuduo.mymarketpro.view.LoadingPage.<init>(LoadingPage.java:51)
            at me.chenfuduo.mymarketpro.view.LoadingPage.<init>(LoadingPage.java:46)
            at me.chenfuduo.mymarketpro.view.LoadingPage.<init>(LoadingPage.java:42)
            at me.chenfuduo.mymarketpro.fragment.BaseFragment$1.<init>(BaseFragment.java:24)
            at me.chenfuduo.mymarketpro.fragment.BaseFragment.onCreateView(BaseFragment.java:24)
```
检查了半天,原来清单文件的Application节点忘了name属性添加上去了。囧。

BaseFragment中的一些代码抽取到LoadingPage中：
```java
package me.chenfuduo.mymarketpro.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.utils.UIUtils;

/**
 * Created by chenfuduo on 2015/10/2.
 */
public abstract class LoadingPage extends FrameLayout {

    //五种状态
    static final int STATE_UNKOWN = 0;
    static final int STATE_LOADING = 1;
    static final int STATE_ERROR = 2;
    static final int STATE_EMPTY = 3;
    static final int STATE_SUCCESS = 4;

    //不能使用static
    // static int state = STATE_UNKOWN;

    int state = STATE_UNKOWN;


    //四种界面
    //加载中
    private View loadingView;
    //加载失败
    private View errorView;
    //为空
    private View emptyView;
    //加载成功
    private View successView;

    public LoadingPage(Context context) {
        this(context, null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //在FrameLayout中添加四种界面。加载成功;加载失败;加载成功,但是没数据;加载中。
    private void init() {
        loadingView = createLoadingView();
        if (loadingView != null) {
            this.addView(loadingView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }

        errorView = createErrorView();
        if (errorView != null) {
            this.addView(errorView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }

        emptyView = createEmptyView();
        if (emptyView != null) {
            this.addView(emptyView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
        showPage();// 根据不同的状态显示不同的界面
    }

    //根据不同状态,显示不同的界面
    //五种状态,未知;加载中;错误;空;加载成功
    private void showPage() {
        if (loadingView != null) {
            loadingView.setVisibility(
                    state == STATE_UNKOWN || state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        }
        if (errorView != null) {
            errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        }


        if (state == STATE_SUCCESS) {
            successView = createSuccessView();
            if (successView != null) {
                this.addView(successView, new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

                successView.setVisibility(View.VISIBLE);
            }
        } else {
            if (successView != null) {
                successView.setVisibility(View.INVISIBLE);
            }
        }
    }

    //加载中的布局
    private View createLoadingView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.loading_page_loading, null);
        return view;
    }

    //加载失败的布局
    private View createErrorView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.loading_page_error, null);
        Button page_bt = (Button) view.findViewById(R.id.page_bt);
        page_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        return view;
    }

    //为空的布局
    private View createEmptyView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.loading_page_empty, null);
        return view;
    }


    //请求服务器,服务器返回只有三种状态
    //错误,空,成功
    public enum LoadResult {
        error(2), empty(3), success(4);
        int value;

        //枚举类的构造器只能使用private
        LoadResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    //根据服务器的数据,切换状态
    public void show() {

        if (state == STATE_ERROR || state == STATE_EMPTY) {
            state = STATE_LOADING;
        }


        //请求服务器,获取服务器上的数据,进行判断
        //请求服务器,返回一个结果
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1500);
                final LoadResult result = load();

                //这里报过空指针
                //当快速退出,快速进入的时候,偶尔出现
                //可能与Fragment工场的缓存有关
                UIUtils.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null) {
                            state = result.getValue();
                            //state = 2 + new Random().nextInt(3);
                            //状态改变了,重新判断当前应该显示哪个界面
                            showPage();
                        }
                    }
                });
            }
        }).start();
        showPage();
    }

    /**
     * 请求服务器数据
     *
     * @return
     */
    public abstract LoadResult load();

    /**
     * 创建成功的界面
     *
     * @return
     */
    protected abstract View createSuccessView();

}
```
那么现在BaseFrament就是这样了：
```java
package me.chenfuduo.mymarketpro.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.chenfuduo.mymarketpro.utils.ViewUtils;
import me.chenfuduo.mymarketpro.view.LoadingPage;

/**
 * Created by chenfuduo on 2015/10/2.
 */
public abstract class BaseFragment extends Fragment {

    LoadingPage loadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (loadingPage == null) {
            loadingPage = new LoadingPage(getActivity()){

                @Override
                public LoadResult load() {
                    return BaseFragment.this.load();
                }

                @Override
                protected View createSuccessView() {
                    return BaseFragment.this.createSuccessView();
                }
            };
        } else {
            ViewUtils.removeSelfFromParent(loadingPage);
        }
        return loadingPage;
    }


    /**
     * 请求服务器数据
     *
     * @return
     */
    public abstract LoadingPage.LoadResult load();

    /**
     * 创建成功的界面
     *
     * @return
     */
    protected abstract View createSuccessView();


    public void show(){
        if (loadingPage != null){
            loadingPage.show();
        }
    }

}
```

## 线程池原理

```java
package me.chenfuduo.mymarketpro.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * 一个简易的线程池管理类，提供三个线程池
 */
public class ThreadManager {
	public static final String DEFAULT_SINGLE_POOL_NAME = "DEFAULT_SINGLE_POOL_NAME";

	private static ThreadPoolProxy mLongPool = null;
	private static Object mLongLock = new Object();

	private static ThreadPoolProxy mShortPool = null;
	private static Object mShortLock = new Object();

	private static ThreadPoolProxy mDownloadPool = null;
	private static Object mDownloadLock = new Object();

	private static Map<String, ThreadPoolProxy> mMap = new HashMap<>();
	private static Object mSingleLock = new Object();

	/** 获取下载线程 */
	public static ThreadPoolProxy getDownloadPool() {
		synchronized (mDownloadLock) {
			if (mDownloadPool == null) {
				mDownloadPool = new ThreadPoolProxy(3, 3, 5L);
			}
			return mDownloadPool;
		}
	}

	/** 获取一个用于执行长耗时任务的线程池，避免和短耗时任务处在同一个队列而阻塞了重要的短耗时任务，通常用来联网操作 */
	public static ThreadPoolProxy getLongPool() {
		synchronized (mLongLock) {
			if (mLongPool == null) {
				mLongPool = new ThreadPoolProxy(5, 5, 5L);
			}
			return mLongPool;
		}
	}

	/** 获取一个用于执行短耗时任务的线程池，避免因为和耗时长的任务处在同一个队列而长时间得不到执行，通常用来执行本地的IO/SQL */
	public static ThreadPoolProxy getShortPool() {
		synchronized (mShortLock) {
			if (mShortPool == null) {
				mShortPool = new ThreadPoolProxy(2, 2, 5L);
			}
			return mShortPool;
		}
	}

	/** 获取一个单线程池，所有任务将会被按照加入的顺序执行，免除了同步开销的问题 */
	public static ThreadPoolProxy getSinglePool() {
		return getSinglePool(DEFAULT_SINGLE_POOL_NAME);
	}

	/** 获取一个单线程池，所有任务将会被按照加入的顺序执行，免除了同步开销的问题 */
	public static ThreadPoolProxy getSinglePool(String name) {
		synchronized (mSingleLock) {
			ThreadPoolProxy singlePool = mMap.get(name);
			if (singlePool == null) {
				singlePool = new ThreadPoolProxy(1, 1, 5L);
				mMap.put(name, singlePool);
			}
			return singlePool;
		}
	}

	public static class ThreadPoolProxy {
		private ThreadPoolExecutor mPool;
		private int mCorePoolSize;
		private int mMaximumPoolSize;
		private long mKeepAliveTime;

		private ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
			mCorePoolSize = corePoolSize;
			mMaximumPoolSize = maximumPoolSize;
			mKeepAliveTime = keepAliveTime;
		}

		/** 执行任务，当线程池处于关闭，将会重新创建新的线程池 */
		public synchronized void execute(Runnable run) {
			if (run == null) {
				return;
			}
			if (mPool == null || mPool.isShutdown()) {
				//参数说明
				//当线程池中的线程小于mCorePoolSize，直接创建新的线程加入线程池执行任务
				//当线程池中的线程数目等于mCorePoolSize，将会把任务放入任务队列BlockingQueue中
				//当BlockingQueue中的任务放满了，将会创建新的线程去执行，
				//但是当总线程数大于mMaximumPoolSize时，将会抛出异常，交给RejectedExecutionHandler处理
				//mKeepAliveTime是线程执行完任务后，且队列中没有可以执行的任务，存活的时间，后面的参数是时间单位
				//ThreadFactory是每次创建新的线程工厂
				mPool = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize, mKeepAliveTime,
						TimeUnit.MILLISECONDS,
						new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new AbortPolicy());
			}
			mPool.execute(run);
		}

		/** 取消线程池中某个还未执行的任务 */
		public synchronized void cancel(Runnable run) {
			if (mPool != null && (!mPool.isShutdown() || mPool.isTerminating())) {
				mPool.getQueue().remove(run);
			}
		}

		/** 取消线程池中某个还未执行的任务 */
		public synchronized boolean contains(Runnable run) {
			if (mPool != null && (!mPool.isShutdown() || mPool.isTerminating())) {
				return mPool.getQueue().contains(run);
			} else {
				return false;
			}
		}

		/** 立刻关闭线程池，并且正在执行的任务也将会被中断 */
		public void stop() {
			if (mPool != null && (!mPool.isShutdown() || mPool.isTerminating())) {
				mPool.shutdownNow();
			}
		}

		/** 平缓关闭单任务线程池，但是会确保所有已经加入的任务都将会被执行完毕才关闭 */
		public synchronized void shutdown() {
			if (mPool != null && (!mPool.isShutdown() || mPool.isTerminating())) {
				mPool.shutdownNow();
			}
		}
	}
}
```

那么LoadingPage使用线程池管理：

```java
ThreadManager.getLongPool().execute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1500);
                final LoadResult result = load();

                //这里报过空指针
                //当快速退出,快速进入的时候,偶尔出现
                //可能与Fragment工场的缓存有关
                UIUtils.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null) {
                            state = result.getValue();
                            //state = 2 + new Random().nextInt(3);
                            //状态改变了,重新判断当前应该显示哪个界面
                            showPage();
                        }
                    }
                });
            }
        });
```

# 2015.10.02晚上

## 请求服务器的框架

```java

package me.chenfuduo.mymarketpro.protocol;

/**
 * Created by chenfuduo on 2015/10/2.
 */
public class HomeProtocol {

    public void load(int index) {
        String json = loadLocal(index);
        if (json == null) {
            json = loadServer();
            if (json != null){
                saveLocal(json,index);
            }
        }
        if (json!=null){
            parserJson(json);
        }
    }
}
```

## xUtils网络请求导致的错误

Android6.0后,删除了HttpClient,所以在使用它的时候，会发生下面的错误：
```
Error:(42, 75) 错误: 无法访问HttpRequestBase
找不到org.apache.http.client.methods.HttpRequestBase的类文件
```
详细参考这个：
[android6.0SDK中删除HttpClient的相关类的解决方法](http://blog.csdn.net/langwang2/article/details/48806241)


我直接放弃这个框架了。

## 联网请求数据

如上,放弃xUtils，使用Async-Http-Client或者自己写。这里都是自己写的,工具类在http包下：
```java
private String loadServer(final int index) {
        /*xUtils框架请求由于Android6.0删除HttpClient会导致报错
        HttpUtils httpUtils = new HttpUtils();
        try {
            ResponseStream responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET,
                    "http://127.0.0.1:8090/home");
            System.out.println(responseStream.toString());
        } catch (HttpException e) {
            e.printStackTrace();
        }
        */

        /*
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get("http://127.0.0.1:8090/home" + "?index=" + index, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e(TAG, "onSuccess " + response);
                System.out.println(response);
                Message message = Message.obtain();
                message.obj = response;
                sendMessage(message);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

        });
        */

        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + "home?index=" + index);

        String json = httpResult.getString();
        Log.e(TAG, "loadServer " + json);
        System.out.println(json);

        return json;
    }
```

## 保存缓存到本地

```java
private void saveLocal(String json, int index) {
        //在第一行写一个过期的时间
        String dir = FileUtils.getCacheDir();
        File file = new File(dir, "home_" + index);
        BufferedWriter bw = null;
        try {
            FileWriter fileWriter = new FileWriter(file);
            bw = new BufferedWriter(fileWriter);
            bw.write(System.currentTimeMillis() + 1000 * 100 + "");
            bw.newLine();
            bw.write(json);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
```

工具类：

```java
package me.chenfuduo.mymarketpro.utils;

import android.os.Environment;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class FileUtils {

	public static final String ROOT_DIR = "chenfuduo";
	public static final String DOWNLOAD_DIR = "download";
	public static final String CACHE_DIR = "cache";
	public static final String ICON_DIR = "icon";

	/** 判断SD卡是否挂载 */
	public static boolean isSDCardAvailable() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			return true;
		} else {
			return false;
		}
	}

	/** 获取下载目录 */
	public static String getDownloadDir() {
		return getDir(DOWNLOAD_DIR);
	}

	/** 获取缓存目录 */
	public static String getCacheDir() {
		return getDir(CACHE_DIR);
	}

	/** 获取icon目录 */
	public static String getIconDir() {
		return getDir(ICON_DIR);
	}

	/** 获取应用目录，当SD卡存在时，获取SD卡上的目录，当SD卡不存在时，获取应用的cache目录 */
	public static String getDir(String name) {
		StringBuilder sb = new StringBuilder();
		if (isSDCardAvailable()) {
			sb.append(getExternalStoragePath());
		} else {
			sb.append(getCachePath());
		}
		sb.append(name);
		sb.append(File.separator);
		String path = sb.toString();
		if (createDirs(path)) {
			return path;
		} else {
			return null;
		}
	}

	/** 获取SD下的应用目录 */
	public static String getExternalStoragePath() {
		StringBuilder sb = new StringBuilder();
		sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
		sb.append(File.separator);
		sb.append(ROOT_DIR);
		sb.append(File.separator);
		return sb.toString();
	}

	/** 获取应用的cache目录 */
	public static String getCachePath() {
		File f = UIUtils.getContext().getCacheDir();
		if (null == f) {
			return null;
		} else {
			return f.getAbsolutePath() + "/";
		}
	}

	/** 创建文件夹 */
	public static boolean createDirs(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists() || !file.isDirectory()) {
			return file.mkdirs();
		}
		return true;
	}

	/** 复制文件，可以选择是否删除源文件 */
	public static boolean copyFile(String srcPath, String destPath, boolean deleteSrc) {
		File srcFile = new File(srcPath);
		File destFile = new File(destPath);
		return copyFile(srcFile, destFile, deleteSrc);
	}

	/** 复制文件，可以选择是否删除源文件 */
	public static boolean copyFile(File srcFile, File destFile, boolean deleteSrc) {
		if (!srcFile.exists() || !srcFile.isFile()) {
			return false;
		}
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = in.read(buffer)) > 0) {
				out.write(buffer, 0, i);
				out.flush();
			}
			if (deleteSrc) {
				srcFile.delete();
			}
		} catch (Exception e) {
			LogUtils.e(e);
			return false;
		} finally {
			IOUtils.close(out);
			IOUtils.close(in);
		}
		return true;
	}

	/** 判断文件是否可写 */
	public static boolean isWriteable(String path) {
		try {
			if (StringUtils.isEmpty(path)) {
				return false;
			}
			File f = new File(path);
			return f.exists() && f.canWrite();
		} catch (Exception e) {
			LogUtils.e(e);
			return false;
		}
	}

	/** 修改文件的权限,例如"777"等 */
	public static void chmod(String path, String mode) {
		try {
			String command = "chmod " + mode + " " + path;
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);
		} catch (Exception e) {
			LogUtils.e(e);
		}
	}

	/**
	 * 把数据写入文件
	 * @param is       数据流
	 * @param path     文件路径
	 * @param recreate 如果文件存在，是否需要删除重建
	 * @return 是否写入成功
	 */
	public static boolean writeFile(InputStream is, String path, boolean recreate) {
		boolean res = false;
		File f = new File(path);
		FileOutputStream fos = null;
		try {
			if (recreate && f.exists()) {
				f.delete();
			}
			if (!f.exists() && null != is) {
				File parentFile = new File(f.getParent());
				parentFile.mkdirs();
				int count = -1;
				byte[] buffer = new byte[1024];
				fos = new FileOutputStream(f);
				while ((count = is.read(buffer)) != -1) {
					fos.write(buffer, 0, count);
				}
				res = true;
			}
		} catch (Exception e) {
			LogUtils.e(e);
		} finally {
			IOUtils.close(fos);
			IOUtils.close(is);
		}
		return res;
	}

	/**
	 * 把字符串数据写入文件
	 * @param content 需要写入的字符串
	 * @param path    文件路径名称
	 * @param append  是否以添加的模式写入
	 * @return 是否写入成功
	 */
	public static boolean writeFile(byte[] content, String path, boolean append) {
		boolean res = false;
		File f = new File(path);
		RandomAccessFile raf = null;
		try {
			if (f.exists()) {
				if (!append) {
					f.delete();
					f.createNewFile();
				}
			} else {
				f.createNewFile();
			}
			if (f.canWrite()) {
				raf = new RandomAccessFile(f, "rw");
				raf.seek(raf.length());
				raf.write(content);
				res = true;
			}
		} catch (Exception e) {
			LogUtils.e(e);
		} finally {
			IOUtils.close(raf);
		}
		return res;
	}

	/**
	 * 把字符串数据写入文件
	 * @param content 需要写入的字符串
	 * @param path    文件路径名称
	 * @param append  是否以添加的模式写入
	 * @return 是否写入成功
	 */
	public static boolean writeFile(String content, String path, boolean append) {
		return writeFile(content.getBytes(), path, append);
	}

	/**
	 * 把键值对写入文件
	 * @param filePath 文件路径
	 * @param key      键
	 * @param value    值
	 * @param comment  该键值对的注释
	 */
	public static void writeProperties(String filePath, String key, String value, String comment) {
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(filePath)) {
			return;
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File f = new File(filePath);
		try {
			if (!f.exists() || !f.isFile()) {
				f.createNewFile();
			}
			fis = new FileInputStream(f);
			Properties p = new Properties();
			p.load(fis);// 先读取文件，再把键值对追加到后面
			p.setProperty(key, value);
			fos = new FileOutputStream(f);
			p.store(fos, comment);
		} catch (Exception e) {
			LogUtils.e(e);
		} finally {
			IOUtils.close(fis);
			IOUtils.close(fos);
		}
	}

	/** 根据值读取 */
	public static String readProperties(String filePath, String key, String defaultValue) {
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(filePath)) {
			return null;
		}
		String value = null;
		FileInputStream fis = null;
		File f = new File(filePath);
		try {
			if (!f.exists() || !f.isFile()) {
				f.createNewFile();
			}
			fis = new FileInputStream(f);
			Properties p = new Properties();
			p.load(fis);
			value = p.getProperty(key, defaultValue);
		} catch (IOException e) {
			LogUtils.e(e);
		} finally {
			IOUtils.close(fis);
		}
		return value;
	}

	/** 把字符串键值对的map写入文件 */
	public static void writeMap(String filePath, Map<String, String> map, boolean append, String comment) {
		if (map == null || map.size() == 0 || StringUtils.isEmpty(filePath)) {
			return;
		}
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File f = new File(filePath);
		try {
			if (!f.exists() || !f.isFile()) {
				f.createNewFile();
			}
			Properties p = new Properties();
			if (append) {
				fis = new FileInputStream(f);
				p.load(fis);// 先读取文件，再把键值对追加到后面
			}
			p.putAll(map);
			fos = new FileOutputStream(f);
			p.store(fos, comment);
		} catch (Exception e) {
			LogUtils.e(e);
		} finally {
			IOUtils.close(fis);
			IOUtils.close(fos);
		}
	}

	/** 把字符串键值对的文件读入map */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public static Map<String, String> readMap(String filePath, String defaultValue) {
		if (StringUtils.isEmpty(filePath)) {
			return null;
		}
		Map<String, String> map = null;
		FileInputStream fis = null;
		File f = new File(filePath);
		try {
			if (!f.exists() || !f.isFile()) {
				f.createNewFile();
			}
			fis = new FileInputStream(f);
			Properties p = new Properties();
			p.load(fis);
			map = new HashMap<String, String>((Map) p);// 因为properties继承了map，所以直接通过p来构造一个map
		} catch (Exception e) {
			LogUtils.e(e);
		} finally {
			IOUtils.close(fis);
		}
		return map;
	}

	/** 改名 */
	public static boolean copy(String src, String des, boolean delete) {
		File file = new File(src);
		if (!file.exists()) {
			return false;
		}
		File desFile = new File(des);
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(file);
			out = new FileOutputStream(desFile);
			byte[] buffer = new byte[1024];
			int count = -1;
			while ((count = in.read(buffer)) != -1) {
				out.write(buffer, 0, count);
				out.flush();
			}
		} catch (Exception e) {
			LogUtils.e(e);
			return false;
		} finally {
			IOUtils.close(in);
			IOUtils.close(out);
		}
		if (delete) {
			file.delete();
		}
		return true;
	}
}
```


## 读取本地缓存

```java
 private String loadLocal(int index) {
        //如果发现文件过期,就不去使用本地的缓存
        String dir = FileUtils.getCacheDir();//获取缓存所在文件夹
        File file = new File(dir, "home_" + index);
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);
            long outOfDate = Long.parseLong(br.readLine());
            if (System.currentTimeMillis() > outOfDate) {
                return null;
            } else {
                String str;
                StringWriter sw = null;
                while ((str = br.readLine()) != null) {
                    sw = new StringWriter();
                    sw.write(str);
                }
                return sw.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
```


## json解析

```java
 private List<AppInfo> parserJson(String json) {

        List<AppInfo> appInfos = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                long id = object.getLong("id");
                String name = object.getString("name");
                String packageName = object.getString("packageName");
                String iconUrl = object.getString("iconUrl");
                float stars = Float.parseFloat(object.getString("stars"));
                String downloadUrl = object.getString("downloadUrl");
                long size = object.getLong("size");
                String des = object.getString("des");
                AppInfo info = new AppInfo(id, name, packageName,
                        iconUrl, stars, downloadUrl, des, size);
                appInfos.add(info);
            }
            return appInfos;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
```
没有使用任何的框架。


## 布局的搭建

两点,一是adapter,一是xml文件。

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="113dp"
        android:layout_marginLeft="8dp"
        android:layout_marginRight="8dp"
        android:background="@drawable/list_item_bg" >

        <RelativeLayout
            android:id="@+id/item_top"
            android:layout_width="match_parent"
            android:layout_height="72dp" >

            <ImageView
                android:id="@+id/item_icon"
                android:layout_width="48dp"
                android:layout_height="48dp"
                android:layout_centerVertical="true"
                android:layout_marginLeft="8dp"
                android:layout_marginRight="8dp"
                android:src="@drawable/ic_default" />

            <RelativeLayout
                android:id="@+id/item_action"
                android:layout_width="70dp"
                android:layout_height="match_parent"
                android:layout_alignParentRight="true"
                android:gravity="center" >

                <FrameLayout
                    android:id="@+id/action_progress"
                    android:layout_centerHorizontal="true"
                    android:layout_width="27dp"
                    android:layout_height="27dp" />

                <TextView
                    android:id="@+id/action_txt"
                    android:layout_width="fill_parent"
                    android:layout_height="wrap_content"
                    android:layout_below="@id/action_progress"
                    android:layout_marginTop="5dp"
                    android:ellipsize="end"
                    android:gravity="center"
                    android:singleLine="true"
                    android:textColor="#ff7a7a7a"
                    android:textSize="12dp" />
            </RelativeLayout>

            <RelativeLayout
                android:id="@+id/item_content"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_centerVertical="true"
                android:layout_toLeftOf="@id/item_action"
                android:layout_toRightOf="@id/item_icon" >

                <TextView
                    android:id="@+id/item_title"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:ellipsize="end"
                    android:singleLine="true"
                    android:textColor="#ff333333"
                    android:textSize="16dp" />

                <RatingBar
                    android:id="@+id/item_rating"
                    android:layout_width="wrap_content"
                    android:layout_height="14dp"
                    android:layout_below="@id/item_title"
                    android:layout_marginBottom="2dp"
                    android:layout_marginTop="2dp"
                    android:isIndicator="true"
                    android:progressDrawable="@drawable/ratingbar_small"
                    android:rating="2.5" />

                <TextView
                    android:id="@+id/item_size"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_below="@id/item_rating"
                    android:singleLine="true"
                    android:textColor="#ff7a7a7a"
                    android:textSize="12dp" />
            </RelativeLayout>
        </RelativeLayout>

        <View
            android:id="@+id/item_divider"
            android:layout_width="match_parent"
            android:layout_height="1dp"
            android:layout_below="@id/item_top"
            android:layout_marginLeft="8dp"
            android:layout_marginRight="8dp"
            android:background="@color/item_divider" />

        <TextView
            android:id="@+id/item_bottom"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_below="@id/item_divider"
            android:layout_marginLeft="12dp"
            android:layout_marginRight="12dp"
            android:ellipsize="end"
            android:gravity="center_vertical"
            android:singleLine="true"
            android:textColor="#ff717171"
            android:textSize="14dp" />
    </RelativeLayout>

</FrameLayout>
```

至于这里为什么外层加了一层帧布局,与ListView的item的高度有关。

## 加载界面

其他的都很简单,就是图片的处理难点。
```java
package me.chenfuduo.mymarketpro.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.bean.AppInfo;
import me.chenfuduo.mymarketpro.http.HttpHelper;
import me.chenfuduo.mymarketpro.utils.BitmapHelper;
import me.chenfuduo.mymarketpro.utils.UIUtils;

/**
 * Created by chenfuduo on 2015/10/2.
 */
public class HomeAdapter extends BaseAdapter {

    private List<AppInfo> appInfos;

    private Context context;

    public HomeAdapter(List<AppInfo> appInfos, Context context) {
        this.appInfos = appInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return appInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return appInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(UIUtils.getContext(), R.layout.list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.item_icon = (ImageView) convertView.findViewById(R.id.item_icon);
            viewHolder.item_title = (TextView) convertView.findViewById(R.id.item_title);
            viewHolder.item_size = (TextView) convertView.findViewById(R.id.item_size);
            viewHolder.item_bottom = (TextView) convertView.findViewById(R.id.item_bottom);
            viewHolder.item_rating = (RatingBar) convertView.findViewById(R.id.item_rating);
            convertView.setTag(viewHolder);
        }else{
           viewHolder= (ViewHolder) convertView.getTag();
        }


        AppInfo appInfo = appInfos.get(position);

        viewHolder.item_title.setText(appInfo.getName());
        String size = Formatter.formatFileSize(UIUtils.getContext(), appInfo.getSize());
        viewHolder.item_size.setText(size);
        viewHolder.item_bottom.setText(appInfo.getDes());

        float stars = appInfo.getStars();
        viewHolder.item_rating.setRating(stars);
       // Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + appInfo.getIconUrl()).into(viewHolder.item_icon);

        BitmapUtils bitmapUtils = BitmapHelper.getInstance();
        bitmapUtils.display(viewHolder.item_icon, HttpHelper.URL + "image?name=" + appInfo.getIconUrl());



        return convertView;
    }

    static class ViewHolder{
        ImageView item_icon;
        TextView item_title,item_size,item_bottom;
        RatingBar item_rating;
    }

}
```
快速滑动的时候,停止加载：
```java
package me.chenfuduo.mymarketpro.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import java.util.List;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.adapter.HomeAdapter;
import me.chenfuduo.mymarketpro.bean.AppInfo;
import me.chenfuduo.mymarketpro.protocol.HomeProtocol;
import me.chenfuduo.mymarketpro.utils.BitmapHelper;
import me.chenfuduo.mymarketpro.view.LoadingPage;

/**
 * Created by chenfuduo on 2015/10/1.
 */
public class HomeFragment extends BaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();

    List<AppInfo> appInfos;

    private HomeAdapter adapter;

    private BitmapUtils bitmapUtils = BitmapHelper.getInstance();

    @Override
    protected View createSuccessView() {
        ListView listView = new ListView(getActivity());

        if (adapter == null){
            adapter = new HomeAdapter(appInfos,getActivity());
        }

        listView.setAdapter(adapter);

        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);

        listView.setOnScrollListener(new PauseOnScrollListener
                (bitmapUtils,false,true));

        return listView;
    }

    @Override
    public LoadingPage.LoadResult load() {
        HomeProtocol protocol = new HomeProtocol();
        appInfos = protocol.load(0);
        /*for (AppInfo info :
                appInfos) {
            System.out.println(info.toString());

        }*/
        return checkData(appInfos);
    }

    private LoadingPage.LoadResult checkData(List<AppInfo> appInfos) {
        if (appInfos == null){
            return LoadingPage.LoadResult.error;
        }else{
            if (appInfos.size() == 0){
                return LoadingPage.LoadResult.empty;
            }else{
                return LoadingPage.LoadResult.success;
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();
    }
}
```

辅助类：
```java
package me.chenfuduo.mymarketpro.utils;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by chenfuduo on 2015/10/3.
 */
public class BitmapHelper {
    private static BitmapUtils bitmapUtils;

    public static BitmapUtils getInstance() {
        if (bitmapUtils == null){
            //缓存图片的路径
            //加载图片  消耗最多百分比的内存
            bitmapUtils = new BitmapUtils(UIUtils.getContext(),
                    FileUtils.getIconDir(),0.5f);
        }
        return bitmapUtils;
    }

    private BitmapHelper() {
    }
}

```

最终实现的效果如下：
![效果图](http://7xljei.com1.z0.glb.clouddn.com/uiframe.gif)

# 2015.10.03上午

## 抽取BaseProtocol
前面写了HomeProtocol,现在要完成的是专题界面,但是发现基本逻辑一致,于是进行抽取到基类。

```java
package me.chenfuduo.mymarketpro.protocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import me.chenfuduo.mymarketpro.http.HttpHelper;
import me.chenfuduo.mymarketpro.utils.FileUtils;

/**
 * Created by chenfuduo on 2015/10/3.
 */
public abstract class BaseProtocol<T> {

    public T load(int index) {
        String json = loadLocal(index);
        if (json == null) {
            json = loadServer(index);
            if (json != null) {
                saveLocal(json, index);
            }
        }/*else{
            Log.e(TAG, "load " +"复用本地缓存了");
        }*/
        if (json != null) {
            return parserJson(json);
        } else {
            return null;
        }
    }

    /**
     * 解析json
     * @param json
     * @return
     */
    protected abstract T parserJson(String json);


    private String loadLocal(int index) {
        //如果发现文件过期,就不去使用本地的缓存
        String dir = FileUtils.getCacheDir();//获取缓存所在文件夹
        File file = new File(dir, getKey() + "_" + index);
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);
            long outOfDate = Long.parseLong(br.readLine());
            if (System.currentTimeMillis() > outOfDate) {
                return null;
            } else {
                String str;
                StringWriter sw = null;
                while ((str = br.readLine()) != null) {
                    sw = new StringWriter();
                    sw.write(str);
                }
                return sw.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void saveLocal(String json, int index) {
        //在第一行写一个过期的时间
        String dir = FileUtils.getCacheDir();
        File file = new File(dir, getKey() + "_" + index);
        BufferedWriter bw = null;
        try {
            FileWriter fileWriter = new FileWriter(file);
            bw = new BufferedWriter(fileWriter);
            bw.write(System.currentTimeMillis() + 1000 * 100 + "");
            bw.newLine();
            bw.write(json);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private String loadServer(final int index) {
        /*xUtils框架请求由于Android6.0删除HttpClient会导致报错
        HttpUtils httpUtils = new HttpUtils();
        try {
            ResponseStream responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET,
                    "http://127.0.0.1:8090/home");
            System.out.println(responseStream.toString());
        } catch (HttpException e) {
            e.printStackTrace();
        }
        */

        /*
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get("http://127.0.0.1:8090/home" + "?index=" + index, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e(TAG, "onSuccess " + response);
                System.out.println(response);
                Message message = Message.obtain();
                message.obj = response;
                sendMessage(message);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

        });
        */

        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey() + "?index=" +  index);

        String json = httpResult.getString();

        return json;
    }


    /**
     * 访问网络关键字
     * @return
     */
    public abstract String getKey();
}
```
这样的话,HomeProtocol就成了这样：
```java

package me.chenfuduo.mymarketpro.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.chenfuduo.mymarketpro.bean.AppInfo;

/**
 * Created by chenfuduo on 2015/10/2.
 */
public class HomeProtocol extends BaseProtocol<List<AppInfo>>{

    @Override
    protected List<AppInfo> parserJson(String json) {
        List<AppInfo> appInfos = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                long id = object.getLong("id");
                String name = object.getString("name");
                String packageName = object.getString("packageName");
                String iconUrl = object.getString("iconUrl");
                float stars = Float.parseFloat(object.getString("stars"));
                String downloadUrl = object.getString("downloadUrl");
                long size = object.getLong("size");
                String des = object.getString("des");
                AppInfo info = new AppInfo(id, name, packageName,
                        iconUrl, stars, downloadUrl, des, size);
                appInfos.add(info);
            }
            return appInfos;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKey() {
        return "home";
    }


}
```
看起来也爽了很多。

再者是SubjectProtocol：
```java
package me.chenfuduo.mymarketpro.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.chenfuduo.mymarketpro.bean.SubjectInfo;

/**
 * Created by chenfuduo on 2015/10/3.
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>> {


    @Override
    protected List<SubjectInfo> parserJson(String json) {
        List<SubjectInfo> subjectInfos = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String des = jsonObject.getString("des");
                String url = jsonObject.getString("url");
                SubjectInfo subjectInfo = new SubjectInfo(des, url);
                subjectInfos.add(subjectInfo);
            }
            return subjectInfos;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKey() {
        return "subject";
    }
}
```


如果不进行抽取,代码会重复很多。


## 专题界面完成


首先是adapter:
```java
package me.chenfuduo.mymarketpro.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.bean.SubjectInfo;
import me.chenfuduo.mymarketpro.http.HttpHelper;
import me.chenfuduo.mymarketpro.utils.BitmapHelper;
import me.chenfuduo.mymarketpro.utils.UIUtils;

/**
 * Created by chenfuduo on 2015/10/3.
 */
public class SubjectAdapter extends BaseAdapter {

    private List<SubjectInfo> subjectInfos;

    private Context context;

    public SubjectAdapter(List<SubjectInfo> subjectInfos, Context context) {
        this.subjectInfos = subjectInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return subjectInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return subjectInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(UIUtils.getContext(), R.layout.subject_item, null);
            viewHolder = new ViewHolder();
            viewHolder.item_icon = (ImageView) convertView.findViewById(R.id.item_icon);
            viewHolder.item_txt = (TextView) convertView.findViewById(R.id.item_txt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SubjectInfo subjectInfo = subjectInfos.get(position);

        String des = subjectInfo.getDes();
        String url = subjectInfo.getUrl();

        viewHolder.item_txt.setText(des);
        BitmapUtils bitmapUtils = BitmapHelper.getInstance();
        bitmapUtils.display(viewHolder.item_icon, HttpHelper.URL + "image?name=" + url);

        return convertView;
    }

    static class ViewHolder {
        ImageView item_icon;
        TextView item_txt;
    }

}
```
然后是条目的布局：

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
             android:layout_width="match_parent"
             android:layout_height="match_parent">

    <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="9dip"
            android:layout_marginRight="9dip"
            android:background="@drawable/list_item_bg">

        <me.chenfuduo.mymarketpro.view.RatioLayout xmlns:chenfuduo="http://schemas.android.com/apk/res-auto"
                                         android:id="@+id/icon_wrapper"
                                         android:layout_width="match_parent"
                                         android:layout_height="wrap_content"
                                         android:padding="5dp"
                                         chenfuduo:ratio="2.43">

            <ImageView
                    android:src="@drawable/ic_default"
                    android:id="@+id/item_icon"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:scaleType="fitCenter"/>
        </me.chenfuduo.mymarketpro.view.RatioLayout>

        <TextView
                android:id="@+id/item_txt"
                style="@style/TitleStyle"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:layout_below="@id/icon_wrapper"
                android:ellipsize="end"
                android:gravity="center_vertical"
                android:paddingBottom="5dp"
                android:paddingLeft="5dp"
                android:paddingRight="5dp"
                android:singleLine="true"/>
    </RelativeLayout>
</FrameLayout>
```

外层嵌套FrameLayout的原理和上面说的一致。

至于这里的自定义控件,这里先不做说明。

```java
package me.chenfuduo.mymarketpro.fragment;

import android.view.View;
import android.widget.ListView;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import java.util.List;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.adapter.SubjectAdapter;
import me.chenfuduo.mymarketpro.bean.SubjectInfo;
import me.chenfuduo.mymarketpro.protocol.SubjectProtocol;
import me.chenfuduo.mymarketpro.utils.UIUtils;
import me.chenfuduo.mymarketpro.view.LoadingPage;

/**
 * Created by chenfuduo on 2015/10/1.
 */
public class SubjectFragment extends BaseFragment {
    List<SubjectInfo> subjectInfos;

    SubjectAdapter adapter;
    @Override
    public LoadingPage.LoadResult load() {

        SubjectProtocol subjectProtocol = new SubjectProtocol();
        subjectInfos = subjectProtocol.load(0);
        return checkData(subjectInfos);
    }

    @Override
    protected View createSuccessView() {
        ListView listView = new ListView(UIUtils.getContext());
        if (adapter == null){
            adapter = new SubjectAdapter(subjectInfos,UIUtils.getContext());
        }
        listView.setAdapter(adapter);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);

        listView.setOnScrollListener(new PauseOnScrollListener
                (bitmapUtils, false, true));
        return listView;
    }
}
```

那么完成的效果图如下：


![效果图](http://7xljei.com1.z0.glb.clouddn.com/subjectui.gif)


## BaseListView

ListView有些样式是一样的,对其进行抽取。
```java
package me.chenfuduo.mymarketpro.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.utils.UIUtils;

/**
 * Created by chenfuduo on 2015/10/3.
 */
public class BaseListView extends ListView {
    public BaseListView(Context context) {
        this(context, null);
    }

    public BaseListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //点击条目的颜色
        this.setSelector(R.drawable.nothing);
        //拖拽的颜色
        this.setCacheColorHint(R.drawable.nothing);
        //每个条目的分割线
        this.setDivider(UIUtils.getDrawable(R.drawable.nothing));
    }
}
```

# 2015.10.03下午

## 抽取Adapter和ViewHolder

下面是抽取的步骤：

> * 抽取Adapter 共性的方法
> * 把getView方法里 和holder相关的逻辑 摘取到Holder代码中
> * 把Holder 相关的代码 抽取到BaseHolder中
> * 把adapter 中getVIew 方法 抽取到了DefaultAdpater中,
其中每个子类getView方法中holder不太一样,所以定义了抽象方法getHolder 要求子类去实现holder。


那么:
DefaultAdapter:
```java
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
```

BaseViewHolder:

```java
package me.chenfuduo.mymarketpro.holder;

import android.view.View;

/**
 * Created by chenfuduo on 2015/10/3.
 */
public abstract class BaseViewHolder<Data> {

    View convertView;

    Data data;

    public BaseViewHolder() {
        convertView =  initView();
        convertView.setTag(this);
    }

    public abstract View initView();

    public void setData(Data data) {
        this.data = data;
        refreshView(data);
    }

    public View getConvertView() {
        return convertView;
    }


    public abstract void refreshView(Data data);
}
```

HomeAdapter:

```java
package me.chenfuduo.mymarketpro.adapter;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.bean.AppInfo;
import me.chenfuduo.mymarketpro.holder.BaseViewHolder;
import me.chenfuduo.mymarketpro.http.HttpHelper;
import me.chenfuduo.mymarketpro.utils.BitmapHelper;
import me.chenfuduo.mymarketpro.utils.UIUtils;

/**
 * Created by chenfuduo on 2015/10/2.
 */
public class HomeAdapter extends DefaultAdapter<AppInfo> {
    public HomeAdapter(List<AppInfo> appInfos) {
        super(appInfos);
    }

    @Override
    public BaseViewHolder<AppInfo> getHolder() {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
        }
        return viewHolder;
    }

    static class ViewHolder extends BaseViewHolder<AppInfo>{
        ImageView item_icon;
        TextView item_title, item_size, item_bottom;
        RatingBar item_rating;

        @Override
        public View initView() {
            View convertView = View.inflate(UIUtils.getContext(), R.layout.list_item, null);
            this.item_icon = (ImageView) convertView.findViewById(R.id.item_icon);
            this.item_title = (TextView) convertView.findViewById(R.id.item_title);
            this.item_size = (TextView) convertView.findViewById(R.id.item_size);
            this.item_bottom = (TextView) convertView.findViewById(R.id.item_bottom);
            this.item_rating = (RatingBar) convertView.findViewById(R.id.item_rating);
            return convertView;
        }

        @Override
        public void refreshView(AppInfo data) {
            this.item_title.setText(data.getName());
            String size = Formatter.formatFileSize(UIUtils.getContext(), data.getSize());
            this.item_size.setText(size);
            this.item_bottom.setText(data.getDes());

            float stars = data.getStars();
            this.item_rating.setRating(stars);
            // Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + appInfo.getIconUrl()).into(viewHolder.item_icon);

            BitmapUtils bitmapUtils = BitmapHelper.getInstance();
            bitmapUtils.display(this.item_icon, HttpHelper.URL + "image?name=" + data.getIconUrl());

        }


    }

}
```


SubjectAdapter:

```java
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
```

调用：

```java
 @Override
    protected View createSuccessView() {
        BaseListView listView = new BaseListView(getActivity());

        if (adapter == null){
            adapter = new HomeAdapter(appInfos);
        }

        listView.setAdapter(adapter);

        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);

        listView.setOnScrollListener(new PauseOnScrollListener
                (bitmapUtils,false,true));

        return listView;
    }
```


## 游戏和应用界面

游戏和应用界面两者完全一致。

首先看应用的。


AppProtocol:

```java
package me.chenfuduo.mymarketpro.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.chenfuduo.mymarketpro.bean.AppInfo;

/**
 * Created by chenfuduo on 2015/10/3.
 */
public class AppProtocol extends BaseProtocol<List<AppInfo>> {

    @Override
    protected List<AppInfo> parserJson(String json) {
        List<AppInfo> appInfos = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                long id = object.getLong("id");
                String name = object.getString("name");
                String packageName = object.getString("packageName");
                String iconUrl = object.getString("iconUrl");
                float stars = Float.parseFloat(object.getString("stars"));
                String downloadUrl = object.getString("downloadUrl");
                long size = object.getLong("size");
                String des = object.getString("des");
                AppInfo info = new AppInfo(id, name, packageName,
                        iconUrl, stars, downloadUrl, des, size);
                appInfos.add(info);
            }
            return appInfos;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKey() {
        return "app";
    }
}
```

AppAdapter:


```java
package me.chenfuduo.mymarketpro.adapter;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.bean.AppInfo;
import me.chenfuduo.mymarketpro.holder.BaseViewHolder;
import me.chenfuduo.mymarketpro.http.HttpHelper;
import me.chenfuduo.mymarketpro.utils.BitmapHelper;
import me.chenfuduo.mymarketpro.utils.UIUtils;

/**
 * Created by chenfuduo on 2015/10/3.
 */
public class AppAdapter extends DefaultAdapter<AppInfo> {
    public AppAdapter(List<AppInfo> appInfos) {
        super(appInfos);
    }

    @Override
    public BaseViewHolder<AppInfo> getHolder() {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
        }
        return viewHolder;
    }


    static class ViewHolder extends BaseViewHolder<AppInfo>{
        ImageView item_icon;
        TextView item_title, item_size, item_bottom;
        RatingBar item_rating;

        @Override
        public View initView() {
            View convertView = View.inflate(UIUtils.getContext(), R.layout.list_item, null);
            this.item_icon = (ImageView) convertView.findViewById(R.id.item_icon);
            this.item_title = (TextView) convertView.findViewById(R.id.item_title);
            this.item_size = (TextView) convertView.findViewById(R.id.item_size);
            this.item_bottom = (TextView) convertView.findViewById(R.id.item_bottom);
            this.item_rating = (RatingBar) convertView.findViewById(R.id.item_rating);
            return convertView;
        }

        @Override
        public void refreshView(AppInfo data) {
            this.item_title.setText(data.getName());
            String size = Formatter.formatFileSize(UIUtils.getContext(), data.getSize());
            this.item_size.setText(size);
            this.item_bottom.setText(data.getDes());

            float stars = data.getStars();
            this.item_rating.setRating(stars);
            // Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + appInfo.getIconUrl()).into(viewHolder.item_icon);

            BitmapUtils bitmapUtils = BitmapHelper.getInstance();
            bitmapUtils.display(this.item_icon, HttpHelper.URL + "image?name=" + data.getIconUrl());

        }


    }

}
```
AppFragment:

```java
package me.chenfuduo.mymarketpro.fragment;

import android.view.View;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import java.util.List;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.adapter.AppAdapter;
import me.chenfuduo.mymarketpro.bean.AppInfo;
import me.chenfuduo.mymarketpro.protocol.AppProtocol;
import me.chenfuduo.mymarketpro.view.BaseListView;
import me.chenfuduo.mymarketpro.view.LoadingPage;

/**
 * Created by chenfuduo on 2015/10/1.
 */
public class AppFragment extends BaseFragment {
    private static final String TAG = AppFragment.class.getSimpleName();

    List<AppInfo> appInfos;

    private AppAdapter adapter;

    @Override
    public LoadingPage.LoadResult load() {
        AppProtocol appProtocol = new AppProtocol();
        appInfos = appProtocol.load(0);
        return checkData(appInfos);
    }

    @Override
    protected View createSuccessView() {
        BaseListView listView = new BaseListView(getActivity());

        if (adapter == null){
            adapter = new AppAdapter(appInfos);
        }

        listView.setAdapter(adapter);

        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);

        listView.setOnScrollListener(new PauseOnScrollListener
                (bitmapUtils,false,true));

        return listView;
    }
}
```

游戏和应用的实现原理完全一致,只是协议中的getKey()返回的是game而已。到这里,应用做成的效果如下：

![效果图](http://7xljei.com1.z0.glb.clouddn.com/gameandappui.gif)

