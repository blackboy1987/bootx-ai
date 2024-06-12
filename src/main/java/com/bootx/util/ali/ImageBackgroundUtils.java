package com.bootx.util.ali;

import com.bootx.util.WebUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 图像背景生成
 */
public class ImageBackgroundUtils {

    public static String token = "sk-48fb566bdf2d47b7be330ed85acfb883";
    public static String task_id = "9d0e2fe2-ed60-4748-bdbc-91eb0f6a0708";
    public static void main(String[] args){
        Map<String,String> headers = new HashMap<>();
        Map<String,Object> body = new HashMap<>();
        headers.put("Authorization","Bearer "+token);
        headers.put("X-DashScope-Async","enable");
        //headers.put("X-DashScope-WorkSpace","enable");


        body.put("model","wanx-background-generation-v2");

        Map<String,Object> input = new HashMap<>();
        input.put("base_image_url","https://pic.3gbizhi.com/uploadmark/20210611/a151ad51df16f00dd5dd857fa5b6cede.jpg");
        input.put("ref_image_url","https://cdn.pixabay.com/photo/2018/07/09/17/44/baby-elephant-3526681_1280.png");
        input.put("ref_prompt","");
        input.put("neg_ref_prompt","");

        Map<String,Object> reference_edge = new HashMap<>();
        reference_edge.put("background_edge","background_edge");
        input.put("reference_edge",reference_edge);

        input.put("title","title");
        input.put("sub_title","sub_title");
        body.put("input",input);


        Map<String,Object> parameters = new HashMap<>();
        parameters.put("n",1);
        parameters.put("noise_level",1);
        parameters.put("ref_prompt_weight",0.3);
        parameters.put("scene_type","GENERAL");
        body.put("parameters",parameters);

        String s = WebUtils.postBody("https://dashscope.aliyuncs.com/api/v1/services/aigc/background-generation/generation", body, headers);
        System.out.println(s);


        String s1 = WebUtils.get("https://dashscope.aliyuncs.com/api/v1/tasks/" + task_id, headers, parameters);
        System.out.println(s1);
    }
}
