package com.example.webchat.controller;

import com.example.webchat.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.Candidate;
import com.google.cloud.vertexai.api.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    private static final String PROJECT_ID = "gen-lang-client-0533321177";

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public List<ChatMessage> sendMessage(ChatMessage message) {
        List<ChatMessage> messages = new ArrayList<>();
        if (message.getContent().startsWith("/")) {
            messages.add(handleCommand(message));
        } else {
            messages.add(message);
        }
        return messages;
    }

    private ChatMessage handleCommand(ChatMessage message) {
        String content = message.getContent();
        if (content.equalsIgnoreCase("/hello")) {
            message.setContent("Hello! How can I assist you today?");
        } else if (content.equalsIgnoreCase("/help")) {
            message.setContent("Available commands: /hello, /help, /AI <text>");
        } else if (content.toLowerCase().startsWith("/ai ")) {
            String query = content.substring(4);
            String response = getAIResponse(query);
            message.setContent(response);
        } else {
            message.setContent("Unknown command. Type /help for a list of available commands.");
        }
        return message;
    }

    private String getAIResponse(String query) {
        String respString = "Error";
        try (VertexAI vertexAi = new VertexAI(PROJECT_ID, "")) {
            GenerativeModel model = new GenerativeModel("gemini-1.5-flash", vertexAi);
            GenerateContentResponse response = model.generateContent(query);
            respString = "";
            for (Candidate candidate : response.getCandidatesList()) {
                System.out.println(candidate);
                for (Part p : candidate.getContent().getPartsList()) {
                    respString += p.getText() + "\n";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respString;
    }
}
