package com.bootx.util;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.Constants;
import com.alibaba.dashscope.utils.JsonUtils;
import io.reactivex.Flowable;

import java.util.Collections;

/**
 * @author black
 */
public class AiUtils {
    public static String token = "sk-48fb566bdf2d47b7be330ed85acfb883";

    private static GenerationParam buildGenerationParam(Message userMsg,String prompt) {
        return GenerationParam.builder()
                .model("qwen-turbo")
                .prompt(prompt)
                .messages(Collections.singletonList(userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .topP(0.8)
                .incrementalOutput(true)
                .build();
    }

    public static Flowable<MessagePojo> message(String content,String prompt) {
        Constants.apiKey = token;
        try {
            Generation gen = new Generation();
            Message userMsg = Message.builder().role(Role.USER.getValue()).content(content).build();
            return streamCallWithMessage(gen,userMsg,prompt);
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            e.printStackTrace();
        }
        return Flowable.just(MessagePojo.stop());
    }

    public static Flowable<MessagePojo> streamCallWithMessage(Generation gen, Message userMsg,String prompt)
            throws NoApiKeyException, ApiException, InputRequiredException {
        GenerationParam param = buildGenerationParam(userMsg,prompt);
        Flowable<GenerationResult> result = gen.streamCall(param);
        return result.map(message->{
            MessagePojo messagePojo = new MessagePojo();
            messagePojo.init(message);
            return messagePojo;
        });
    }
}