package com.ispan.dogland.service.interfaceFile;

import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.entity.Users;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface AccountService {
    public boolean checkEmailIsEmpty(String email);

    public void register(Users user);

    public Passport getPassportFromFormLogin(String username);

    public Passport getPassportFromOauth2Login(OAuth2User oAuth2User);

    public String encodePassword(String password);

    public void sendCodeForResetPassword(String email);

    public boolean verifyCodeForResetPassword(String email, String code);

    public void resetPassword(String email, String newPassword);

    public void clearVerificationCode(String email);
}
