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
 * 通义万相-Cosplay动漫人物生成通过输入人像图片和卡通形象图片，可快速生成人物卡通写真。目前支持3D卡通形象风格。
 * 关于该接口功能的示例图如下：
 * 3D卡通形象图（左侧为输入的人像图片+卡通形象图片，右侧为输出的效果图）
 * @author black
 */
public class ImageGenerationUtils {
    public static String TOKEN = "sk-48fb566bdf2d47b7be330ed85acfb883";

    /**
     * {"output":{"task_status":"PENDING","task_id":"dd2d527b-3009-4f93-80fd-31685247fdfa"},"request_id":"b1b0c6fa-3a85-9301-9175-500c4cfb5677"}
     * @param imageSynthesisRequest
     */
    public static ImageSynthesisResponse create(ImageSynthesisRequest imageSynthesisRequest){
        String url = "https://dashscope.aliyuncs.com/api/v1/services/aigc/image-generation/generation";
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
