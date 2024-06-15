package com.bootx.util.ali;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.ResultCallback;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.Constants;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

/**
 * 文本
 * @author blackboy
 */
public class TextUtils {
    static {
        Constants.apiKey = "sk-48fb566bdf2d47b7be330ed85acfb883";
    }
    public static void streamCallWithCallback(String content) throws NoApiKeyException, InputRequiredException, InterruptedException {
        Generation gen = new Generation();
        Message userMsg = Message.builder().role(Role.USER.getValue()).content(content).build();
        GenerationParam param = GenerationParam.builder()
                .model("qwen-max")
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .messages(Arrays.asList(userMsg))
                .topP(0.8)
                .incrementalOutput(true)
                .build();
        Semaphore semaphore = new Semaphore(0);
        gen.streamCall(param, new ResultCallback<GenerationResult>() {
            @Override
            public void onEvent(GenerationResult message) {
                String content1 = message.getOutput().getChoices().get(0).getMessage().getContent();
                System.out.println(content1);
            }

            @Override
            public void onError(Exception err) {
                semaphore.release();
            }

            @Override
            public void onComplete() {
                semaphore.release();
            }

        });
        semaphore.acquire();
    }
}
