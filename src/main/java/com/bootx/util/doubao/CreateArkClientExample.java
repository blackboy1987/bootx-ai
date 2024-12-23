package com.bootx.util.doubao;

import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionContentPart;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;

import java.util.ArrayList;
import java.util.List;

public class CreateArkClientExample {
    public static void main(String[] args) {
        String apiKey = Config.ARK_API_KEY;
        ArkService service = ArkService.builder().ak(apiKey).sk("WldJMk9HRTVNVGt4WVRjM05ESmxaVGsyWVRneFpqVmtObVJsTTJVNU5tWQ==").build();
        System.out.println(service);

        System.out.println("----- image input -----");
        final List<ChatMessage> messages = new ArrayList<>();
        final List<ChatCompletionContentPart> multiParts = new ArrayList<>();
        multiParts.add(ChatCompletionContentPart.builder().type("text").text(
                "这是哪里？"
        ).build());
        multiParts.add(ChatCompletionContentPart.builder().type("image_url").imageUrl(
                new ChatCompletionContentPart.ChatCompletionContentPartImageURL(
                        "https://ark-project.tos-cn-beijing.ivolces.com/images/view.jpeg"
                )
        ).build());
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER)
                .multiContent(multiParts).build();
        messages.add(userMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("ep-20241223183215-xx8ws")
                .messages(messages)
                .build();

        service.createChatCompletion(chatCompletionRequest).getChoices().forEach(choice -> {
            System.out.println(choice.getMessage().getContent());
        });

        // shutdown service
        service.shutdownExecutor();
    }
    // do your operation...
}