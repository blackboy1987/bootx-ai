package com.bootx.util;

import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesis;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisParam;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.alibaba.dashscope.utils.Constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;

public class TyWXUtils {
    public static String token = "sk-48fb566bdf2d47b7be330ed85acfb883";


    public static void basicCall() throws ApiException, NoApiKeyException {
        ImageSynthesis is = new ImageSynthesis();
        ImageSynthesisParam param =
                ImageSynthesisParam.builder()
                        .model(ImageSynthesis.Models.WANX_V1)
                        .n(4)
                        .size("1024*1024")
                        .prompt("雄鹰自由自在的在蓝天白云下飞翔")
                        .build();

        ImageSynthesisResult result = is.call(param);
        System.out.println(result);
    }

    public static void message(String content, Consumer<MessagePojo> callback) {
        Constants.apiKey = token;
        try{
            basicCall();
            //listTask();
        }catch(ApiException|NoApiKeyException e){
            System.out.println(e.getMessage());
        }
    }
}