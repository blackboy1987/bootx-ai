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

import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;

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
                semaphore.release();
            }

            /**
             * 结束
             */
            @Override
            public void onComplete() {
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
}