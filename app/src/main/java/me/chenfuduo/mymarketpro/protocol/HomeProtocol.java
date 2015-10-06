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

    private List<String> pictures;


    @Override
    protected List<AppInfo> parserJson(String json) {
        List<AppInfo> appInfos = new ArrayList<>();
        pictures = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray picture = jsonObject.getJSONArray("picture");
            for (int i = 0; i < picture.length(); i++) {
                String string = picture.getString(i);
                pictures.add(string);
            }
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

    public List<String> getPictures() {
        return pictures;
    }

    @Override
    public String getKey() {
        return "home";
    }


}
