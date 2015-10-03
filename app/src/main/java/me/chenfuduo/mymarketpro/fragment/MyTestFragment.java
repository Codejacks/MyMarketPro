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
            tv.setText("首页");
        } else if (mPage == 2) {
            tv.setText("应用");
        } else if (mPage == 3) {
            tv.setText("游戏");

        } else if (mPage == 4) {
            tv.setText("专题");

        } else if (mPage == 5) {
            tv.setText("推荐");

        } else if (mPage == 6) {
            tv.setText("分类");

        } else if (mPage == 7) {
            tv.setText("排行");

        }

        return tv;
    }
}
