package me.chenfuduo.mymarketpro.fragment;

import android.view.View;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import java.util.List;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.adapter.GameAdapter;
import me.chenfuduo.mymarketpro.bean.AppInfo;
import me.chenfuduo.mymarketpro.protocol.GameProtocol;
import me.chenfuduo.mymarketpro.view.BaseListView;
import me.chenfuduo.mymarketpro.view.LoadingPage;

/**
 * Created by chenfuduo on 2015/10/1.
 */
public class GameFragment extends BaseFragment {
    private static final String TAG = GameFragment.class.getSimpleName();

    List<AppInfo> appInfos;

    private GameAdapter adapter;

    @Override
    public LoadingPage.LoadResult load() {
        GameProtocol gameProtocol = new GameProtocol();
        appInfos = gameProtocol.load(0);
        return checkData(appInfos);
    }

    @Override
    protected View createSuccessView() {
        BaseListView listView = new BaseListView(getActivity());

        if (adapter == null){
            adapter = new GameAdapter(appInfos,listView);
        }

        listView.setAdapter(adapter);

        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);

        listView.setOnScrollListener(new PauseOnScrollListener
                (bitmapUtils,false,true));

        return listView;
    }
}
