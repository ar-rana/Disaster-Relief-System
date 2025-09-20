package com.example.server.service;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class AIService {

    private ChatClient chatClient;

    public AIService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.defaultSystem("""
                You are a Disaster AID request categorization System.
                You MUST categorize the request in ONE of the FOUR Categories:-
                'BASIC', 'MODERATE', 'HIGH', 'VERY_HIGH'.
                You MUST Respond with ONLY the Category and NOTHING ELSE.
                """).build();
    }

    public String getCriticality(String request) {
        log.info("[AI_S] Categorizing: {}", request);
        UserMessage userMessage = new UserMessage(request);

        Prompt ask = new Prompt(userMessage);
        return chatClient.prompt(ask).call().content().trim();
    }
}
