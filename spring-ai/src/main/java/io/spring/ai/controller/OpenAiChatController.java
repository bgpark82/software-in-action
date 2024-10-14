package io.spring.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAiChatController {

    private final ChatClient chatClient;

    public OpenAiChatController(@Qualifier("openAIChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/openai")
    public String openai() {
        return chatClient.prompt()
                .user("Tell me an interesting fact about OpenAI")
                .call()
                .content();
    }
}
