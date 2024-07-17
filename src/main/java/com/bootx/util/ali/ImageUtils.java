package com.bootx.util.ali;

import com.bootx.util.WebUtils;

import java.util.HashMap;
import java.util.Map;

public class ImageUtils {

    public static String token = "sk-48fb566bdf2d47b7be330ed85acfb883";

    public static void create(Map<String,Object> parameters,String model){
        Map<String,String> headers = new HashMap<>();
        headers.put("X-DashScope-Async","enable");
        headers.put("Authorization","Bearer "+token);
        parameters.put("model",model);



        String url="https://dashscope.aliyuncs.com/api/v1/services/aigc/background-generation/generation/";
        String s = WebUtils.postBody(url, parameters, headers);
        System.out.println(s);
    }

    public static String getTask(String taskId){
        Map<String,String> headers = new HashMap<>();
        headers.put("X-DashScope-Async","enable");
        headers.put("Authorization","Bearer "+token);
        String s = WebUtils.get("https://dashscope.aliyuncs.com/api/v1/tasks/" + taskId, headers, null);
        return s;
    }



    public static void main(String[] args) {
        Map<String,Object> params = new HashMap<>();

        Map<String,Object> input = new HashMap<>();
        /**
         * 透明背景的主体图像URL。
         * 需要为带透明背景的RGBA 四通道图像，支持png格式，
         * 分辨率长边不超过2048像素。
         * 输出图像的分辨率与该输入图相同
         */
        input.put("base_image_url","");
        /**
         * 引导图URL, 支持 jpg, png，webp等常见格式图像；
         */
        input.put("ref_image_url","");
        /**
         * 引导文本提示词，支持中英双语，不超过70个单词。
         */
        input.put("ref_prompt","");
        /**
         * 负向提示词，不希望出现的内容。
         * 大部分情况下建议缺省该参数，这样会使用模型内置的默认值
         */
        input.put("neg_ref_prompt","");

        Map<String,Object> reference_edge = new HashMap<>();

        /**
         * 前景元素，需要为带透明背景的RGBA 四通道图像，分辨率和主体图像相同，
         * 如果不同则自动会resize到和主体图像同一个分辨率。
         * 前景元素生成的图层在主体前面，可以对主体形成遮挡。
         * 元素图像的生成方式参考边缘引导元素生成方法介绍
         */
        reference_edge.put("foreground_edge","");
        /**
         * 背景元素，生成图层在主体的后面，
         * 如果重叠会被主体遮挡，其它提要求同前景元素
         */
        reference_edge.put("background_edge","");
        input.put("reference_edge",reference_edge);

        /**
         * 图像上添加文字主标题。算法自动确定文字的大小和位置，限制1-8个字符
         */
        input.put("title","");
        /**
         * 图像上添加文字副标题。算法自动确定文字的大小和位置，限制1-10个字符。
         * 仅当title不为空时生效
         */
        input.put("sub_title","");
        params.put("input",input);
        /**
         * 生成图像数量，默认值1，取值范围[1,4]
         */
        params.put("n",4);
        /**
         * 当ref_image_url不为空时生效。
         * 在图像引导的过程中添加随机变化，
         * 数值越大与参考图相似度越低，
         * 默认值300，取值范围[0,999]
         */
        params.put("noise_level","");
        /**
         * 仅当ref_image_url和ref_prompt同时输入时生效，
         * 该参数设定文本和图像引导的权重。
         * ref_prompt_weight表示文本的权重，
         * 图像引导的权重为1-ref_prompt_weight。
         * 默认值0.5，取值范围 [0,1]
         */
        params.put("ref_prompt_weight","");

        /**
         * 使用场景，当前包含3种场景：
         * GENERAL: 通用场景，默认值
         * ROOM: 室内家居场景
         * COSMETIC：美妆场景，也适用于大部分小商品摆放场景
         */
        params.put("scene_type","");

        create(params,"stable-diffusion-xl-1024-v1-0");

    }

}
