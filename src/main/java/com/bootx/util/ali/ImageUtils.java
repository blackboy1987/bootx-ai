package com.bootx.util.ali;

// Copyright (c) Alibaba, Inc. and its affiliates.

import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesis;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisListResult;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisParam;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import com.alibaba.dashscope.task.AsyncTaskListParam;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.Constants;

public class ImageUtils {

    public static String token = "sk-48fb566bdf2d47b7be330ed85acfb883";
    public static void basicCall() throws ApiException, NoApiKeyException {
        ImageSynthesis is = new ImageSynthesis();
        ImageSynthesisParam param =
                ImageSynthesisParam.builder()
                        .model(ImageSynthesis.Models.WANX_V1)
                        .n(1)
                        .size("1024*1024")
                        .prompt("雄鹰自由自在的在蓝天白云下飞翔")
                        .build();

        ImageSynthesisResult result = is.call(param);
        System.out.println(result);
    }

    public static String listTask() throws ApiException, NoApiKeyException {
        ImageSynthesis is = new ImageSynthesis();
        AsyncTaskListParam param = AsyncTaskListParam.builder().build();
        ImageSynthesisListResult result = is.list(param);
        System.out.println(result);
        return result.getData().get(0).getTaskId();
    }

    public static void fetchTask(String taskId) throws ApiException, NoApiKeyException {
        ImageSynthesis is = new ImageSynthesis();
        ImageSynthesisResult result = is.fetch(taskId, token);
        // 这里是图片数据
        System.out.println(result.getOutput().getResults());
        System.out.println(result.getUsage());
    }

    public static void main(String[] args){
        Constants.apiKey = token;
        try{
            //basicCall();
            String taskId = listTask();
            fetchTask(taskId);

        }catch(ApiException|NoApiKeyException e){
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }
}
