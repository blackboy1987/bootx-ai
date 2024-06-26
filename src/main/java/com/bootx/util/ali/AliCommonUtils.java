package com.bootx.util.ali;

import com.bootx.util.WebUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
public class AliCommonUtils {

    public static String TOKEN = "sk-48fb566bdf2d47b7be330ed85acfb883";

    public static String create(String url, Map<String, Object> parameters) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-DashScope-Async", "enable");
        headers.put("Authorization", "Bearer " + TOKEN);
        String s = WebUtils.postBody(url, parameters, headers);
        return s;
    }

    public static String getTask(String taskId) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + TOKEN);
        String s = WebUtils.get("https://dashscope.aliyuncs.com/api/v1/tasks/"+taskId, headers,null);
        return s;
    }

}
