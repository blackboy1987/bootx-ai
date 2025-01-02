package com.bootx.util.ali2;

import java.util.Arrays;

import com.alibaba.dashscope.aigc.generation.*;
import com.alibaba.dashscope.utils.Constants;
import com.bootx.util.ali2.text.TextResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import io.reactivex.Flowable;
import java.lang.System;
import java.util.function.Consumer;

public class TextChat {
    private static final Logger logger = LoggerFactory.getLogger(TextChat.class);

    static {
        Constants.apiKey = "sk-48fb566bdf2d47b7be330ed85acfb883";
    }

    public static Flowable<TextResponse> streamCallWithMessage(Generation gen, Message userMsg,Consumer<TextResponse> callback)
            throws NoApiKeyException, ApiException, InputRequiredException {
        GenerationParam param = buildGenerationParam(userMsg);
        Flowable<GenerationResult> result = gen.streamCall(param);
        return result.map(TextResponse::init);
    }
    private static GenerationParam buildGenerationParam(Message userMsg) {
        return GenerationParam.builder()
                .model("qwen-plus")
                .messages(Arrays.asList(userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .incrementalOutput(true)
                .build();
    }

    public static Flowable<TextResponse> chat(String content) {
        try {
            Generation gen = new Generation();
            Message userMsg = Message.builder().role(Role.USER.getValue()).content(content).build();
            return streamCallWithMessage(gen, userMsg,textResponse -> {
                System.out.println(JsonUtils.toJson(textResponse));
            });
        } catch (ApiException | NoApiKeyException | InputRequiredException  e) {
            logger.error("An exception occurred: {}", e.getMessage());
        }
        return null;
    }
}