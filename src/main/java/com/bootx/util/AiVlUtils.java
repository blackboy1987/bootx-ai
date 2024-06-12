package com.bootx.util;

import com.alibaba.dashscope.aigc.multimodalconversation.*;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.alibaba.dashscope.utils.Constants;
import io.reactivex.Flowable;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;

public class AiVlUtils {
    public static String token = "sk-48fb566bdf2d47b7be330ed85acfb883";


    public static void simpleMultiModalConversationStreamCall(String image,String text) throws ApiException, NoApiKeyException, UploadFileException {
        MultiModalConversation conv = new MultiModalConversation();
        MultiModalMessageItemImage userImage = new MultiModalMessageItemImage(image);
        MultiModalMessageItemText userText = new MultiModalMessageItemText(text);
        MultiModalConversationMessage userMessage = MultiModalConversationMessage.builder().role(Role.USER.getValue()).content(Arrays.asList(userImage, userText)).build();
        MultiModalConversationParam param = MultiModalConversationParam.builder().model(MultiModalConversation.Models.QWEN_VL_CHAT_V1).message(userMessage).build();
        Flowable<MultiModalConversationResult> result = conv.streamCall(param);
        result.blockingForEach(item->{
            System.out.println(item);
        });
    }

    public static void message(String image,String text, Consumer<MessagePojo> callback) {
        Constants.apiKey = token;
        try {
            simpleMultiModalConversationStreamCall(image,text);
        } catch (ApiException | NoApiKeyException | UploadFileException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        message("https://cdn.oaistatic.com/_next/static/media/apple-touch-icon.82af6fe1.png","这是一张什么图片",message->{
            System.out.println(message);
        });
    }
}