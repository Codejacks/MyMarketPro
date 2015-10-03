package me.chenfuduo.mymarketpro.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.chenfuduo.mymarketpro.bean.SubjectInfo;

/**
 * Created by chenfuduo on 2015/10/3.
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>> {


    @Override
    protected List<SubjectInfo> parserJson(String json) {
        List<SubjectInfo> subjectInfos = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String des = jsonObject.getString("des");
                String url = jsonObject.getString("url");
                SubjectInfo subjectInfo = new SubjectInfo(des, url);
                subjectInfos.add(subjectInfo);
            }
            return subjectInfos;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKey() {
        return "subject";
    }
}
