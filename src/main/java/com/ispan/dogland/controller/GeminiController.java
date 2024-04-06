package com.ispan.dogland.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class GeminiController {

    @PostMapping(value = "/generateContent")
    public ResponseEntity<String> generateContent(@RequestBody Map<String, Object> request) {

        String content = (String) request.get("content");
        String apiKey = "AIzaSyD8L-U56UHVFpQm4eh5uPp04ySNkObpzFQ";
        String url = "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent?key=" + apiKey;


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        String requestBody = "{"
                + "\"contents\": [{"
                + "\"parts\": [{\"text\": \"" + content + "\"}]"
                + "}],"
                + "\"safetySettings\": [{"
                + "\"category\": \"HARM_CATEGORY_DANGEROUS_CONTENT\","
                + "\"threshold\": \"BLOCK_NONE\""
                + "}],"
                + "\"generationConfig\": {"
                + "\"temperature\": 1.0,"
                + "\"maxOutputTokens\": 30,"
                + "\"topP\": 0.8,"
                + "\"topK\": 10"
                + "}"
                + "}";


        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

//        String promptFeedback = extractPromptFeedback(responseEntity.getBody());
//        System.out.println(promptFeedback);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseJson = mapper.readTree(responseEntity.getBody());
            for(int i =0;i<=3;i++){
                JsonNode promptFeedbackNode = responseJson.path("promptFeedback").get("safetyRatings").get(i);
                System.out.println(promptFeedbackNode.get("category").toString());
                System.out.println(promptFeedbackNode.get("probability").toString());
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());

    }


}


