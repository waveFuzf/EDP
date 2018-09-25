package com.zust.EDP.util;


import net.sf.json.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApiCheckUtil {
    public JSONArray  CheckNum(String realname, String cardnum) {
        String host = "https://1.api.apistore.cn";
        String path = "/idcard3";
        String method = "POST";
        String appcode = "ede04a26829e40f4be4f61bb94ad065a";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("cardNo", cardnum);
        bodys.put("realName", realname);
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            String reponse=EntityUtils.toString(response.getEntity());
            return JSONArray.fromObject("["+reponse+"]");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
