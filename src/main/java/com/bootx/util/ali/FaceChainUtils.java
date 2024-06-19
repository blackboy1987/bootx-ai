package com.bootx.util.ali;

import com.alibaba.dashscope.utils.Constants;
import com.bootx.util.WebUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * FaceChain人物写真生成
 * https://help.aliyun.com/zh/dashscope/developer-reference/facechain-portrait-generation
 */
public class FaceChainUtils {
    private static String baseUrl="https://dashscope.aliyuncs.com/api/v1/services/aigc/wordart";

    public static String token = "sk-48fb566bdf2d47b7be330ed85acfb883";

    public static void main(String[] args) {
        Constants.apiKey = token;
        Map<String,String> headers = new HashMap<>();
        Map<String,Object> parameters = new HashMap<>();
        headers.put("X-DashScope-Async","enable");
        headers.put("Authorization","Bearer "+token);
        parameters.put("model","facechain-generation");

        parameters.put("style","f_business_female");
        parameters.put("size","768*1024");
        parameters.put("n","1");
        parameters.put("template_url","http://viapi-test.oss-cn-shanghai.aliyuncs.com/viapi-3.0domepic/facebody/CompareFace/CompareFace-left3.png");
        parameters.put("user_urls","1");

        String post = WebUtils.post(baseUrl+"/texture", parameters, headers);
        System.out.println(post);
    }

}
