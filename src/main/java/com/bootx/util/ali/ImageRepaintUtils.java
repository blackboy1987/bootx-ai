package com.bootx.util.ali;

import com.bootx.util.WebUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 人像风格重绘
 */
public class ImageRepaintUtils {

    public static String token = "sk-48fb566bdf2d47b7be330ed85acfb883";
    public static void main(String[] args){
        Map<String,String> headers = new HashMap<>();
        Map<String,Object> parameters = new HashMap<>();
        headers.put("X-DashScope-Async","enable");
        headers.put("Authorization","Bearer "+token);
        parameters.put("model","wanx-style-repaint-v1");

        Map<String,Object> input = new HashMap<>();
        input.put("image_url","https://pic.3gbizhi.com/uploadmark/20210611/a151ad51df16f00dd5dd857fa5b6cede.jpg");
        /**
         * 想要生成的风格化类型索引：
         *      -1 参考上传图像风格
         *      0 复古漫画
         *      1 3D童话
         *      2 二次元
         *      3 小清新
         *      4 未来科技
         *      5 国画古风
         *      6 将军百战
         *      7 炫彩卡通
         *      8 清雅国风
         *      9 喜迎新年
         */
        input.put("style_index",1);
        input.put("style_ref_url","");
        parameters.put("input",input);
        String s = WebUtils.postBody("https://dashscope.aliyuncs.com/api/v1/services/aigc/image-generation/generation", parameters, headers);
        System.out.println(s);


       /* String s = WebUtils.get("https://dashscope.aliyuncs.com/api/v1/tasks/" + task_id, headers, parameters);
        System.out.println(s);*/
    }
}
