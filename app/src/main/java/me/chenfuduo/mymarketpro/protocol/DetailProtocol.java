package me.chenfuduo.mymarketpro.protocol;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.chenfuduo.mymarketpro.bean.AppInfo;

/**
 * Created by chenfuduo on 2015/10/5.
 */
public class DetailProtocol extends BaseProtocol<AppInfo> {

    String packageName;

    public DetailProtocol(String packageName) {
        this.packageName = packageName;
    }

    @Override
    protected AppInfo parserJson(String json) {
        try {
            JSONObject object = new JSONObject(json);
            long id = object.getLong("id");
            String name = object.getString("name");
            String packageName = object.getString("packageName");
            String iconUrl = object.getString("iconUrl");
            float stars = Float.parseFloat(object.getString("stars"));
            long size = object.getLong("size");
            String downloadUrl = object.getString("downloadUrl");
            String des = object.getString("des");
            String downloadNum = object.getString("downloadNum");
            String version = object.getString("version");
            String date = object.getString("date");
            String author = object.getString("author");
            List<String> screen = new ArrayList<>();
            JSONArray screenArray = object.getJSONArray("screen");
            for (int i = 0; i < screenArray.length(); i++) {
                screen.add(screenArray.getString(i));
            }

            List<String> safeUrl = new ArrayList<>();
            List<String> safeDesUrl = new ArrayList<>();
            List<String> safeDes = new ArrayList<>();
            List<Integer> safeDesColor = new ArrayList<>();
            JSONArray jsonArray = object.getJSONArray("safe");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                safeUrl.add(jsonObject.getString("safeUrl"));
                safeDesUrl.add(jsonObject.getString("safeDesUrl"));
                safeDes.add(jsonObject.getString("safeDes"));
                safeDesColor.add(jsonObject.getInt("safeDesColor"));

            }
            AppInfo appInfo = new AppInfo(id, name, packageName, iconUrl,
                    stars, size, downloadUrl, des, downloadNum, version, date,
                    author, screen, safeUrl, safeDesUrl, safeDes, safeDesColor);
            return appInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKey() {
        return "detail";
    }

    @Override
    protected String getParames() {
        return "&packageName=" + packageName;
    }
}
