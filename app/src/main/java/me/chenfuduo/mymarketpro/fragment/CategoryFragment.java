package me.chenfuduo.mymarketpro.fragment;

import android.view.View;

import me.chenfuduo.mymarketpro.view.LoadingPage;

/**
 * Created by chenfuduo on 2015/10/1.
 */
public class CategoryFragment extends BaseFragment {
    private static final String TAG = CategoryFragment.class.getSimpleName();

    @Override
    public LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.error;
    }

    @Override
    protected View createSuccessView() {
        return null;
    }
}
