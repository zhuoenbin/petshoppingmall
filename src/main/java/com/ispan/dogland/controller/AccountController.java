package com.ispan.dogland.controller;


import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.service.interfaceFile.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/test")
    public String test(HttpSession httpSession){
        Passport loginUser = (Passport) httpSession.getAttribute("loginUser");
        System.out.println("test = " + loginUser);
        return "test";
    }
    @GetMapping("/test1")
    public String test1(){

        return "test";
    }

    @GetMapping("/api/users/map")
    public ResponseEntity<?> testSessionValue(HttpSession httpSession){

        System.out.println("檢查登入 controller");

        Passport loginUser = (Passport) httpSession.getAttribute("loginUser");
        System.out.println("/api/users/map:loginUser = " + loginUser);
        if(loginUser == null){
            System.out.println("session attribute 空的");
            return new ResponseEntity<String>("session attribute null", HttpStatus.UNAUTHORIZED); // 401
        }

        Map<String,String> responseMap =  new HashMap<>();
        responseMap.put("userId",loginUser.getUserId().toString());
        responseMap.put("userName",loginUser.getUsername());

        return new ResponseEntity<Map<String,String>>(responseMap, HttpStatus.OK);
    }

    @PostMapping("/formLogin")
    public ResponseEntity<?> formLogin(@RequestBody Map<String,String> data, HttpSession httpSession){
        String useremail = data.get("useremail");
        String password = data.get("password");

        Passport loginUser = accountService.loginCheck(useremail, password);
        if(loginUser == null){
            return new ResponseEntity<String>("登入失敗", HttpStatus.UNAUTHORIZED); // 401
        }else{
            httpSession.setAttribute("loginUser",loginUser);
            httpSession.setAttribute("loginUser",loginUser);
            return new ResponseEntity<String>("登入成功", HttpStatusCode.valueOf(200));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?>   registerUser(@RequestBody Users userData,HttpSession httpSession){
        String useremail = userData.getUserEmail();

        if(accountService.checkEmailIsEmpty(useremail)){
            userData.setLastLoginTime(new Date());
            userData.setUserStatus("ACTIVE");
            userData.setUserViolationCount(0);
            Users user = accountService.register(userData);
            System.out.println("註冊成功");

            Passport loginUser = new Passport(user.getLastName(),user.getUserEmail(), user.getUserId(), user.getUserStatus());
            httpSession.setAttribute("loginUser",loginUser);

            return new ResponseEntity<String>("註冊成功", HttpStatusCode.valueOf(200));
        }

        return new ResponseEntity<String>("註冊失敗", HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/api/auth/logout")
    public ResponseEntity<?> usersLogout(HttpSession httpSession){

        System.out.println("登出 controller");

        httpSession.removeAttribute("loginUser");

        return new ResponseEntity<String>("logout success", HttpStatusCode.valueOf(200));
    }


    //忘記密碼:發送驗證碼到使用者Email
    @GetMapping("/forgetPassword/sendCode/{userEmail}")
    public ResponseEntity<String> sendCode(@PathVariable("userEmail") String userEmail) {
        if (!accountService.checkEmailIsEmpty(userEmail)) {
            accountService.sendCodeForResetPassword(userEmail);
            return ResponseEntity.ok("已發送驗證碼至信箱");
        }
        return ResponseEntity.badRequest().body("信箱有誤");
    }

    //忘記密碼:驗證驗證碼與Email真偽
    @PostMapping("/forgetPassword/verifyCode")
    public ResponseEntity<String> verifyCode(@RequestBody Map<String,String> data) {
        String email = data.get("email");
        String verifyCode = data.get("verifyCode");
        boolean isValid = accountService.verifyCodeForResetPassword(email, verifyCode);
        if (isValid) {
            // 驗證碼正確，導向重設密碼頁面
            return ResponseEntity.ok("verifyCode pass");
        } else {
            // 驗證碼錯誤
            return ResponseEntity.badRequest().body("verifyCode failed");
        }
    }

    //忘記密碼:重新設定密碼
    @PostMapping("/forgetPassword/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestBody)  {
        String verifyCode = requestBody.get("verifyCode");
        String email = requestBody.get("email");
        String newPassword = requestBody.get("newPassword");
        boolean isValid = accountService.verifyCodeForResetPassword(email, verifyCode);
        if (isValid) {
            accountService.resetPassword(email, newPassword);
            accountService.clearVerificationCode(email);
            return ResponseEntity.ok("resetPassword success");
        }
        return ResponseEntity.badRequest().body("resetPassword failed");
    }


}
