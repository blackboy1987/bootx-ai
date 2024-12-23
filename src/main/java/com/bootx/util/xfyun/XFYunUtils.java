package com.bootx.util.xfyun;

import com.bootx.util.JsonUtils;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import okhttp3.*;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XFYunUtils {

    public static Flowable<String> text(String content){
        return Flowable.create(emitter -> {
            OkHttpClient client = new OkHttpClient();

            Map<String,Object> data= new HashMap<>();
            data.put("model","generalv3.5");
            List<Map<String,Object>> messages = new ArrayList<>();
            Map<String,Object> message = new HashMap<>();
            message.put("role","user");
            message.put("content",content);
            messages.add(message);
            data.put("messages",messages);
            data.put("stream",true);
            RequestBody requestBody = RequestBody.create(
                    JsonUtils.toJson(data),
                    MediaType.parse("application/json; charset=utf-8")
            );
            Request request = new Request.Builder()
                    .url("https://spark-api-open.xf-yun.com/v1/chat/completions")
                    .header("Authorization","Bearer eudorPtqEYOPInxKasvj:OlDgNuIbcPXLEZcISjUN")
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    if (!emitter.isCancelled()) {
                        emitter.onError(e);
                    }
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        BufferedSource source = response.body().source();
                        try {
                            while (!emitter.isCancelled() && !source.exhausted()) {
                                String chunk = source.readUtf8Line();
                                System.out.println(chunk);
                                if (chunk != null) {
                                    emitter.onNext(chunk);
                                }
                            }
                            emitter.onComplete();
                        } catch (IOException e) {
                            emitter.onError(e);
                        } finally {
                            source.close();
                        }
                    } else {
                        emitter.onError(new IOException("Unexpected code: " + response.code()));
                    }
                }
            });
        }, BackpressureStrategy.BUFFER);
    }
}
