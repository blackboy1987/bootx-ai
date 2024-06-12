package com.bootx.util.ali;

import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesis;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisListResult;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisParam;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.task.AsyncTaskListParam;
import com.alibaba.dashscope.utils.Constants;
import com.bootx.util.WebUtils;

import java.util.HashMap;
import java.util.Map;

public class ImageCosplayUtils {

    public static String token = "sk-48fb566bdf2d47b7be330ed85acfb883";
    public static String task_id = "9d0e2fe2-ed60-4748-bdbc-91eb0f6a0708";
    public static void main(String[] args){
        Map<String,String> headers = new HashMap<>();
        Map<String,Object> parameters = new HashMap<>();
        headers.put("X-DashScope-Async","enable");
        headers.put("Authorization","Bearer "+token);
        parameters.put("model","wanx-style-cosplay-v1");

        Map<String,Object> input = new HashMap<>();
        input.put("face_image_url","https://pic.3gbizhi.com/uploadmark/20210611/a151ad51df16f00dd5dd857fa5b6cede.jpg");
        input.put("template_image_url","https://cdn.pixabay.com/photo/2018/07/09/17/44/baby-elephant-3526681_1280.png");
        input.put("model_index",1);
        parameters.put("input",input);
        /*String s = WebUtils.postBody("https://dashscope.aliyuncs.com/api/v1/services/aigc/image-generation/generation", parameters, headers);
        System.out.println(s);*/


        String s = WebUtils.get("https://dashscope.aliyuncs.com/api/v1/tasks/" + task_id, headers, parameters);
        System.out.println(s);
    }
}
