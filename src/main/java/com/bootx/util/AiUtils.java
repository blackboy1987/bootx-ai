package com.bootx.util;

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
import io.reactivex.Flowable;

import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

/**
 * @author black
 */
public class AiUtils {
    public static String token = "sk-48fb566bdf2d47b7be330ed85acfb883";


    private static void streamCallWithCallback(Generation gen, Message userMsg, Consumer<MessagePojo> callback)
            throws NoApiKeyException, ApiException, InputRequiredException, InterruptedException {
        GenerationParam param = buildGenerationParam(userMsg);
        Semaphore semaphore = new Semaphore(0);
        gen.streamCall(param, new ResultCallback<GenerationResult>() {
            /**
             * 事件响应
             * @param message
             */
            @Override
            public void onEvent(GenerationResult message) {
                System.out.println(message.getOutput().getChoices().get(0).getFinishReason());
                MessagePojo messagePojo = new MessagePojo();
                messagePojo.init(message);
                callback.accept(messagePojo);
            }

            /**
             * 出错
             * @param err
             */
            @Override
            public void onError(Exception err) {
                callback.accept(MessagePojo.stop());
                semaphore.release();
            }

            /**
             * 结束
             */
            @Override
            public void onComplete() {
                callback.accept(MessagePojo.stop());
                semaphore.release();
            }
        });
        semaphore.acquire();
    }

    private static GenerationParam buildGenerationParam(Message userMsg) {
        return GenerationParam.builder()
                // 设置模型
                /**
                 * qwen-turbo
                 * qwen-plus
                 * qwen-max
                 * qwen-max-0428
                 * qwen-max-0403
                 * qwen-max-0107
                 * qwen-max-longcontext
                 */
                .model("qwen-turbo")
                .messages(Arrays.asList(userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .topP(0.8)
                .incrementalOutput(true)
                .build();
    }

    public static void message(String content, Consumer<MessagePojo> callback) {
        Constants.apiKey = token;
        try {
            Generation gen = new Generation();
            Message userMsg = Message.builder().role(Role.USER.getValue()).content(content).build();
            streamCallWithCallback(gen, userMsg,callback);
        } catch (ApiException | NoApiKeyException | InputRequiredException | InterruptedException ignored) {
        }
    }
    public static Flowable<MessagePojo> message1(String content) {
        Constants.apiKey = token;
        try {
            Generation gen = new Generation();
            Message userMsg = Message.builder().role(Role.USER.getValue()).content(content).build();
            return streamCallWithMessage(gen,userMsg);
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            e.printStackTrace();
        }
        return Flowable.just(MessagePojo.empty());
    }

    public static Flowable<MessagePojo> streamCallWithMessage(Generation gen, Message userMsg)
            throws NoApiKeyException, ApiException, InputRequiredException {
        GenerationParam param = buildGenerationParam(userMsg);
        Flowable<GenerationResult> result = gen.streamCall(param);
        return result.map(message->{
            MessagePojo messagePojo = new MessagePojo();
            messagePojo.init(message);
            return messagePojo;
        });
    }
}