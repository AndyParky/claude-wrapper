package com.example.claudewrapper.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONObject;

@Service
public class ClaudeService {

    @Value("${claude.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api.anthropic.com/v1/messages";

    public String askClaude(String question) {
        RestTemplate restTemplate = new RestTemplate();

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "claude-3-opus-20240229");
        requestBody.put("max_tokens", 500);
        requestBody.put("messages", new org.json.JSONArray()
                .put(new JSONObject()
                        .put("role", "user")
                        .put("content", question)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", apiKey);
        headers.set("anthropic-version", "2023-06-01");

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
            JSONObject json = new JSONObject(response.getBody());
            return json.getJSONArray("content").getJSONObject(0).getString("text");
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
