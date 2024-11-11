package com.bootx.util.ali.text2image;

import com.bootx.util.JsonUtils;
import com.bootx.util.ali.AliCommonUtils;
import com.bootx.util.ali.text2image.request.ImageSynthesisRequest;
import com.bootx.util.ali.text2image.response.ImageSynthesisResponse;
import com.bootx.util.ali.text2image.response.TaskResponse;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

/**
 * 通义万相-文本生成图像是基于自研的Composer组合生成框架的AI绘画创作大模型，
 * 能够根据用户输入的文字内容，生成符合语义描述的多样化风格的图像。
 * 通过知识重组与可变维度扩散模型，加速收敛并提升最终生成图片的效果，
 * 布局自然、细节丰富、画面细腻、结果逼真。
 * AI深度理解中英文文本语义，让文字秒变精致AI画作。
 * @author black
 */
public class Text2ImageUtils {
    public static String TOKEN = "sk-48fb566bdf2d47b7be330ed85acfb883";

    /**
     * {"output":{"task_status":"PENDING","task_id":"dd2d527b-3009-4f93-80fd-31685247fdfa"},"request_id":"b1b0c6fa-3a85-9301-9175-500c4cfb5677"}
     * @param imageSynthesisRequest
     */
    public static ImageSynthesisResponse create(ImageSynthesisRequest imageSynthesisRequest){
        String url = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text2image/image-synthesis";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("model", imageSynthesisRequest.getModel());
        parameters.put("input", imageSynthesisRequest.getInput());
        parameters.put("parameters", imageSynthesisRequest.getParameters());
        String s = AliCommonUtils.create(url, parameters);
        System.out.println(s);
        return JsonUtils.toObject(s, new TypeReference<ImageSynthesisResponse>() {
        });
    }

    public static TaskResponse getTask(String taskId){
        String s = AliCommonUtils.getTask(taskId);
        TaskResponse taskResponse = JsonUtils.toObject(s, new TypeReference<TaskResponse>() {
        });
        return taskResponse;
    }


    public static void main(String[] args) {
        getTask("dd2d527b-3009-4f93-80fd-31685247fdfa");
    }

}
