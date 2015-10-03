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
