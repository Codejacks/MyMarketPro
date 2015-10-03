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
