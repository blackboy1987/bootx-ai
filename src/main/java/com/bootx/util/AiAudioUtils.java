package com.bootx.util;

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

public class AiAudioUtils {
    public static String token = "sk-48fb566bdf2d47b7be330ed85acfb883";


    public static void simpleMultiModalConversationCall(String audio,String text)
            throws ApiException, NoApiKeyException, UploadFileException {
        MultiModalConversation conv = new MultiModalConversation();
        MultiModalMessage userMessage = MultiModalMessage.builder().role(Role.USER.getValue())
                .content(Arrays.asList(Collections.singletonMap("audio", audio),
                        Collections.singletonMap("text", text))).build();
        MultiModalConversationParam param = MultiModalConversationParam.builder()
                .model("qwen-audio-turbo")
                .message(userMessage)
                .build();
        MultiModalConversationResult result = conv.call(param);
        System.out.println(result);
    }

    public static void message(String content, Consumer<MessagePojo> callback) {
        Constants.apiKey = token;
        try {
            simpleMultiModalConversationCall(null,null);
        } catch (ApiException | NoApiKeyException | UploadFileException e) {
            System.out.println(e.getMessage());
        }
    }
}