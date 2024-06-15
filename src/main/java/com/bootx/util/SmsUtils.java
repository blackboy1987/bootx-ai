package com.bootx.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
public class SmsUtils {
    private static final String ACCESS_KEY = "dxwblackboy1987";
    private static final String ACCESS_SECRET = "8226A146350519B335244B8F0228";


    private static final String SEND_URL="https://api.1cloudsp.com/api/v2/single_send";

    public static String send(String mobile,String code){
        String result = "";
       try {
           Map<String,Object> params = new HashMap<>(16);
           params.put("accesskey",ACCESS_KEY);
           params.put("secret",ACCESS_SECRET);
           params.put("sign","【爱购商城科技】");
           params.put("mobile",mobile);
           params.put("templateId","64099");
           params.put("content", URLEncoder.encode(code, StandardCharsets.UTF_8));
           result = WebUtils.postBody(SEND_URL, params);
           System.out.println(result);
       }catch (Exception ignored){

       }
        return result;
    }

    public static void main(String[] args) {
        String send = send("19971579891", "123456");
        System.out.println(send);

    }
}