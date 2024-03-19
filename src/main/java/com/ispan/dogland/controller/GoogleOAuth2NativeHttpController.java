package com.ispan.dogland.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.dogland.config.securityconfig.GoogleOauthConfig;
import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.service.interfaceFile.AccountService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Controller
public class GoogleOAuth2NativeHttpController {
    // 原生使用(okhttp) http request, response 執行 OAuth2 的寫法

    @Autowired
    private GoogleOauthConfig googleOauth2Config;

    @Autowired
    private AccountService accountService;
    @Autowired
    private RestTemplate restTemplate;

    private final String scope = "https://www.googleapis.com/auth/userinfo.email";

    @GetMapping("/google-login")
    public String googleLogin(HttpServletResponse response) {

        // 直接 redirect 導向 Google OAuth2 API
        String authUrl = "https://accounts.google.com/o/oauth2/v2/auth?" +
                "client_id=" + googleOauth2Config.getClientId() +
                "&response_type=code" +
                "&scope=openid%20email%20profile" +
                "&redirect_uri=" + googleOauth2Config.getRedirectUri() +
                "&state=state";
        return "redirect:" + authUrl;
    }


    @GetMapping("/google-callback")
    public String oauth2Callback(@RequestParam(required = false) String code, HttpSession httpSession)
            throws IOException {
        if (code == null) {
            String authUri = "https://accounts.google.com/o/oauth2/v2/auth?response_type=code" +
                    "&client_id=" + googleOauth2Config.getClientId() +
                    "&redirect_uri=" + googleOauth2Config.getRedirectUri() +
                    "&scope=" + scope;
            return "redirect:" + authUri;
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>(); // key 可能重複再用
            map.add("code", code);
            map.add("client_id", googleOauth2Config.getClientId());
            map.add("client_secret", googleOauth2Config.getClientSecret());
            map.add("redirect_uri", googleOauth2Config.getRedirectUri());
            map.add("grant_type", "authorization_code");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            ResponseEntity<String> response = restTemplate.postForEntity("https://oauth2.googleapis.com/token", request,
                    String.class);
            String credentials = response.getBody();
            // System.out.println("credentials" + credentials);

            JsonNode jsonNode = new ObjectMapper().readTree(credentials);
            String accessToken = jsonNode.get("access_token").asText();
            String idToken = jsonNode.get("id_token").asText();

            HttpHeaders headers2 = new HttpHeaders();
            headers2.setBearerAuth(accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers2);
            ResponseEntity<String> response2 = restTemplate.exchange(
                    "https://www.googleapis.com/oauth2/v1/userinfo?alt=json",
                    HttpMethod.GET,
                    entity,
                    String.class);

            String payloadResponse = response2.getBody();

            JsonNode payloadJsonNode = new ObjectMapper().readTree(payloadResponse);

            // Extract data from payloadJsonNode and process it
            String payloadGoogleId = payloadJsonNode.get("id").asText();
            String payloadEmail = payloadJsonNode.get("email").asText();
            String payloadName = payloadJsonNode.get("name").asText();
            String payloadPicture = payloadJsonNode.get("picture").asText();
//            String payloadLocale = payloadJsonNode.get("locale").asText();

             System.out.println("payloadGoogleId: " + payloadGoogleId);
             System.out.println("payloadEmail: " + payloadEmail);
             System.out.println("payloadName: " + payloadName);
             System.out.println("payloadPicture: " + payloadPicture);
//             System.out.println("payloadLocale: " + payloadLocale);

            Passport loginUser = null;
            if(!accountService.checkEmailIsEmpty(payloadEmail)){
                loginUser = accountService.getPassportFromFormLogin(payloadEmail);
            }else{
                Users tmpUser = new Users();
                tmpUser.setFirstName(payloadName);
                tmpUser.setLastName(payloadName);
                tmpUser.setUserEmail(payloadEmail);
                tmpUser.setUserStatus("ACTIVE");
                accountService.register(tmpUser);
                loginUser = accountService.getPassportFromFormLogin(payloadEmail);
            }
            System.out.println("loginUser: " + loginUser);
            // 將 USER 放到 SESSION 內
            httpSession.setAttribute("loginUser", loginUser);
        }
        return "redirect:http://localhost:5173/";
    }


}