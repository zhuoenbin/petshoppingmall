package com.ispan.dogland.controller;


import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.service.interfaceFile.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("test")
    public String test() {
        return "test";
    }

    @GetMapping("/userLogin")
    public String userLogin(Authentication authentication, HttpSession session){

        // 取得使用者的帳號(email)
        String username = authentication.getName();
        // 取得使用者的權限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String authority = authorities.iterator().next().getAuthority();

        //核發通行證&存入httpsession
        Passport passportDTO = accountService.getPassportFromFormLogin(username);
        passportDTO.setRole(authority);
        session.setAttribute("passportDTO", passportDTO);

        return "Hello  " + passportDTO.getUsername() + "先生/女士 你好 ! 你的權限為: " +  passportDTO.getRole();

    }
}
