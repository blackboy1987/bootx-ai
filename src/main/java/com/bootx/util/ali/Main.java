package com.bootx.util.ali;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationMessage;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalMessageItemImage;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalMessageItemText;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.alibaba.dashscope.utils.Constants;


public class Main {
    static {
        Constants.apiKey = "sk-48fb566bdf2d47b7be330ed85acfb883";
    }
    public static void multiRoundConversationCall() throws ApiException, NoApiKeyException, UploadFileException {
        MultiModalConversation conv = new MultiModalConversation();
        MultiModalMessageItemText systemText = new MultiModalMessageItemText("你现在是一位五星级大厨。接下来教我怎么做菜");
        MultiModalConversationMessage systemMessage = MultiModalConversationMessage.builder()
                .role(Role.SYSTEM.getValue()).content(Arrays.asList(systemText)).build();



        MultiModalMessageItemText userText = new MultiModalMessageItemText("番茄炒鸡蛋怎么做?");
        MultiModalConversationMessage userMessage =
                MultiModalConversationMessage.builder().role(Role.USER.getValue())
                        .content(Arrays.asList(userText)).build();
        List<MultiModalConversationMessage> messages = new ArrayList<>();
        messages.add(systemMessage);
        messages.add(userMessage);
        MultiModalConversationParam param = MultiModalConversationParam.builder()
                .model(MultiModalConversation.Models.QWEN_VL_PLUS)
                .messages(messages)
                .build();
        MultiModalConversationResult result = conv.call(param);
        System.out.println(result);
        MultiModalMessageItemText assistentText = new MultiModalMessageItemText(
                result.getOutput().getChoices().get(0).getMessage().getContent().get(0).get("text").toString());
        MultiModalConversationMessage assistentMessage = MultiModalConversationMessage.builder()
                .role(Role.ASSISTANT.getValue()).content(Arrays.asList(assistentText)).build();
        messages.add(assistentMessage);
        userText = new MultiModalMessageItemText("感觉鸡蛋放的少了？");
        messages.add(MultiModalConversationMessage.builder().role(Role.USER.getValue())
                .content(Arrays.asList(userText)).build());
        param.setMessages(new ArrayList<Object>(messages));
        result = conv.call(param);
        System.out.print(result);
    }

    public static void main(String[] args) {
        try {
            multiRoundConversationCall();
        } catch (ApiException | NoApiKeyException | UploadFileException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}