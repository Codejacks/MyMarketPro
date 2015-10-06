package me.chenfuduo.mymarketpro.fragment;

import android.view.View;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import java.util.List;

import me.chenfuduo.mymarketpro.R;
import me.chenfuduo.mymarketpro.adapter.SubjectAdapter;
import me.chenfuduo.mymarketpro.bean.SubjectInfo;
import me.chenfuduo.mymarketpro.protocol.SubjectProtocol;
import me.chenfuduo.mymarketpro.utils.UIUtils;
import me.chenfuduo.mymarketpro.view.BaseListView;
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
        BaseListView listView = new BaseListView(UIUtils.getContext());
        if (adapter == null){
            adapter = new SubjectAdapter(subjectInfos,listView);
        }
        listView.setAdapter(adapter);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);

        listView.setOnScrollListener(new PauseOnScrollListener
                (bitmapUtils, false, true));
        return listView;
    }
}
