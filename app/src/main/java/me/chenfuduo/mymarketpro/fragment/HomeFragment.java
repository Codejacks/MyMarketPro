package me.chenfuduo.mymarketpro.fragment;

import android.os.Bundle;
import android.view.View;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import java.util.List;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.adapter.HomeAdapter;
import me.chenfuduo.mymarketpro.bean.AppInfo;
import me.chenfuduo.mymarketpro.protocol.HomeProtocol;
import me.chenfuduo.mymarketpro.view.BaseListView;
import me.chenfuduo.mymarketpro.view.LoadingPage;

/**
 * Created by chenfuduo on 2015/10/1.
 */
public class HomeFragment extends BaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();

    List<AppInfo> appInfos;

    private HomeAdapter adapter;



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



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();
    }
}
