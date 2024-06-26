package com.bootx.util.ali;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.ResultCallback;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.Constants;
import com.alibaba.dashscope.utils.JsonUtils;
import com.bootx.util.MessagePojo;
import io.reactivex.Flowable;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

/**
 * 文本
 * @author blackboy
 */
public class TextUtils {
    static {
        Constants.apiKey = "sk-48fb566bdf2d47b7be330ed85acfb883";
    }
    public static void streamCallWithCallback(String content, Consumer<MessagePojo> onEvent, Consumer<String> onError, Consumer<Integer> onComplete){
        System.out.println(content);
        Generation gen = new Generation();
        Message userMsg = Message.builder().role(Role.USER.getValue()).content(content).build();
        GenerationParam param = GenerationParam.builder()
                .model("qwen-plus")
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .messages(Collections.singletonList(userMsg))
                .topP(0.8)
                .incrementalOutput(true)
                .build();
        Semaphore semaphore = new Semaphore(0);
        try {
            gen.streamCall(param, new ResultCallback<GenerationResult>() {
                @Override
                public void onEvent(GenerationResult message) {
                    MessagePojo messagePojo = new MessagePojo();
                    messagePojo.init(message);
                    onEvent.accept(messagePojo);
                }

                @Override
                public void onError(Exception err) {
                    semaphore.release();
                    System.out.println(err.getMessage());
                    onError.accept(err.getMessage());
                }

                @Override
                public void onComplete() {
                    semaphore.release();
                    onComplete.accept(0);
                }
            });
            semaphore.acquire();
        } catch (NoApiKeyException | InputRequiredException | InterruptedException e) {
            onError.accept(e.getMessage());
        }
    }

    private static GenerationParam buildGenerationParam(Message userMsg) {
        return GenerationParam.builder()
                .model("qwen-turbo")
                .messages(Collections.singletonList(userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .topP(0.8)
                .incrementalOutput(true)
                .build();
    }

    private static void handleGenerationResult(GenerationResult message, StringBuilder fullContent, Consumer<MessagePojo> onEvent, Consumer<String> onError, Consumer<Integer> onComplete) {
        System.out.println(JsonUtils.toJson(message));
        MessagePojo messagePojo = new MessagePojo();
        messagePojo.init(message);
        onEvent.accept(messagePojo);
        if(StringUtils.equalsAnyIgnoreCase(messagePojo.getFinishReason(),"stop")){
            onComplete.accept(1);
        }
    }

    public static void streamCallWithMessage(Generation gen, Message userMsg, Consumer<MessagePojo> onEvent, Consumer<String> onError, Consumer<Integer> onComplete) {
       try {
           GenerationParam param = buildGenerationParam(userMsg);
           Flowable<GenerationResult> result = gen.streamCall(param);
           StringBuilder fullContent = new StringBuilder();
           result.blockingForEach(message -> handleGenerationResult(message, fullContent, onEvent,onError,onComplete));
       }catch (Exception e){
           onError.accept(e.getMessage());
       }
    }

    public static void streamCallWithMessage(String content, Consumer<MessagePojo> onEvent, Consumer<String> onError, Consumer<Integer> onComplete) throws NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        Message userMsg = Message.builder().role(Role.USER.getValue()).content(content).build();
        streamCallWithMessage(gen,userMsg,onEvent,onError,onComplete);
    }

}
