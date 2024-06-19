package com.bootx.util.ali;

import com.alibaba.dashscope.utils.Constants;
import com.bootx.util.WebUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * WordArt锦书
 * @author: xiajun
 * 文档地址：https://help.aliyun.com/zh/dashscope/developer-reference/wordart-quick-start
 */
public class WordArtUtils {

    private static String baseUrl="https://dashscope.aliyuncs.com/api/v1/services/aigc/wordart";

    public static String token = "sk-48fb566bdf2d47b7be330ed85acfb883";


    /**
     * 文字纹理生成API详情
     * @param args
     */
    public static void main(String[] args) {
        Constants.apiKey = token;
        Map<String,String> headers = new HashMap<>();
        Map<String,Object> parameters = new HashMap<>();
        headers.put("X-DashScope-Async","enable");
        headers.put("Authorization","Bearer "+token);
        parameters.put("model","wordart-texture");


        Map<String,Object> input = new HashMap<>();

        Map<String,Object> image = new HashMap<>();
        image.put("image_url",1);
        input.put("image",image);

        Map<String,Object> text = new HashMap<>();
        text.put("text_content","用户输入的文字内容，小于6个字");
        text.put("ttf_url","用户传入的ttf文件");
        text.put("font_name","");
        text.put("output_image_ratio","");
        input.put("text",text);


        input.put("prompt","期望文字纹理创意样式的描述提示词，长度小于200");
        input.put("texture_style","");

        parameters.put("input",input);
        parameters.put("image_long_size","");
        parameters.put("n","1");
        parameters.put("alpha_channel",false);


        String post = WebUtils.post(baseUrl+"/texture", parameters, headers);
        System.out.println(post);
    }

    public void getTask(String taskId){
        Constants.apiKey = token;
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization","Bearer "+token);

        String s = WebUtils.get("https://dashscope.aliyuncs.com/api/v1/tasks/" + taskId, headers, null);
        System.out.println(s);
    }


    /**
     * 文字变形API详情
     */
    public static void main1() {
        Constants.apiKey = token;
        Map<String,String> headers = new HashMap<>();
        Map<String,Object> parameters = new HashMap<>();
        headers.put("X-DashScope-Async","enable");
        headers.put("Authorization","Bearer "+token);
        parameters.put("model","wordart-semantic");

        Map<String,Object> input = new HashMap<>();
        input.put("text","文字创意");
        input.put("prompt","水果，蔬菜，温暖的色彩空间");
        parameters.put("input",input);

        Map<String,Object> input1 = new HashMap<>();
        input1.put("steps",80);
        input1.put("n",2);
        input1.put("output_image_ratio","1024x1024");
        input1.put("font_name","dongfangdakai");
        parameters.put("parameters",input1);

        String post = WebUtils.post(baseUrl+"/semantic", parameters, headers);
        System.out.println(post);
    }

}
