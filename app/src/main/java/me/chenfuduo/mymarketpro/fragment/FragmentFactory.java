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
