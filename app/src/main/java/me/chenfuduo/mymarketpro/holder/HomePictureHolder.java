package me.chenfuduo.mymarketpro.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.BitmapUtils;

import java.util.LinkedList;
import java.util.List;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.http.HttpHelper;
import me.chenfuduo.mymarketpro.utils.BitmapHelper;
import me.chenfuduo.mymarketpro.utils.UIUtils;
import me.chenfuduo.mymarketpro.view.IndicatorView;

/**
 * Created by chenfuduo on 2015/10/5.
 */
public class HomePictureHolder extends BaseViewHolder<List<String>> {

    private static final String TAG = HomePictureHolder.class.getSimpleName();
    private ViewPager viewPager;

    private List<String> datas;
    private AutoRunTask autoRunTask;

    private RelativeLayout.LayoutParams rl;
    private IndicatorView indicatorView;

    @Override
    public View initView() {
        // 初始化头。由于使用相对布局方便放置可以跳动的点
        RelativeLayout mHeadView = new RelativeLayout(UIUtils.getContext());
        // 设置轮播图的宽和高
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT,
                UIUtils.getDimens(R.dimen.list_header_hight));

        mHeadView.setLayoutParams(params);
        // 初始化轮播图

        viewPager = new ViewPager(UIUtils.getContext());
        // 初始化轮播图的宽和高

        rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        viewPager.setLayoutParams(rl);

        HomePictureAdapter adapter = new HomePictureAdapter();

        viewPager.setAdapter(adapter);
        // 初始化点

        indicatorView = new IndicatorView(UIUtils.getContext());

        rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 设置到屏幕的下边
        rl.addRule(mHeadView.ALIGN_PARENT_BOTTOM);
        rl.addRule(mHeadView.ALIGN_PARENT_RIGHT);
        // 设置点的背景图片
        indicatorView.setIndicatorDrawable(UIUtils
                .getDrawable(R.drawable.indicator));
        // 设置点的间距
        rl.setMargins(5, 0, 20, 10);
        indicatorView.setLayoutParams(rl);

        indicatorView.setSelection(0);

        // 把点和轮播图添加到头里面去
        mHeadView.addView(viewPager);

        mHeadView.addView(indicatorView);

        return mHeadView;
    }

    @Override
    public void refreshView(final List<String> datas) {
        this.datas = datas;
        viewPager.setAdapter(new HomePictureAdapter());
        viewPager.setCurrentItem(1000 * datas.size());
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        autoRunTask.stop();
                        break;
                    //此处是修改bug。
                    case MotionEvent.ACTION_CANCEL:
                        autoRunTask.start();
                        break;
                    case MotionEvent.ACTION_UP:
                        autoRunTask.start();
                        break;
                }
                //此处不能返回true
                //ViewPager触摸事件返回false
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicatorView.setSelection(position % datas.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        indicatorView.setCount(data.size());

        autoRunTask = new AutoRunTask();
        autoRunTask.start();
    }


    boolean flag;

    class AutoRunTask implements Runnable {

        @Override
        public void run() {
            if (flag) {
                UIUtils.removeCallbacks(this);
                int currentItem = viewPager.getCurrentItem();
                currentItem++;
                viewPager.setCurrentItem(currentItem);
                UIUtils.postDelayed(this, 1500);
            }
        }

        public void start() {
            if (!flag) {
                UIUtils.removeCallbacks(this);
                flag = true;
                UIUtils.postDelayed(this, 1500);
            }
        }

        public void stop() {
            if (flag) {
                flag = false;
                UIUtils.removeCallbacks(this);
            }
        }
    }

    class HomePictureAdapter extends PagerAdapter {

        //优化   不必没次都去创建ImageView
        LinkedList<ImageView> convertView = new LinkedList<>();

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int index = position % datas.size();
            ImageView imageView;
            if (convertView.size() > 0) {
                imageView = convertView.remove(0);
                Log.e(TAG, "instantiateItem " + "不必创建");
            } else {
                imageView = new ImageView(UIUtils.getContext());
                Log.e(TAG, "instantiateItem " + "必须创建");
            }
            BitmapUtils bitmapUtils = BitmapHelper.getInstance();
            bitmapUtils.display(imageView, HttpHelper.URL + "image?name=" + datas.get(index));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            ImageView view = (ImageView) object;
            convertView.add(view);
            container.removeView(view);
        }
    }

}
