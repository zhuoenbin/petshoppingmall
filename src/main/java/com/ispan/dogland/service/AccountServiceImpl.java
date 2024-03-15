package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.EmployeeRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.service.interfaceFile.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    private final Map<String,String> emailVerificationCodes = new HashMap<>();
    private UserRepository usersRepository;
    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;

    @Autowired
    public AccountServiceImpl(UserRepository usersRepository,EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder,MailService mailService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public boolean checkEmailIsEmpty(String email) {
        // 檢查資料庫中是否已經存在相同的 email
        Users tmpUser = usersRepository.findByUserEmail(email);
        return tmpUser == null? true : false;
    }
    @Override
    public void register(Users user){
        String encodePassword = encodePassword(user.getUserPassword());
        user.setUserPassword(encodePassword);
        user.setLastLoginTime(new Date());
        usersRepository.save(user);
    };


    @Override
    public Passport getPassportFromFormLogin(String username) {
        Users user = usersRepository.findByUserEmail(username);
        Passport passportDTO = null;
        if(user != null){
            user.setLastLoginTime(new Date());
            usersRepository.save(user);
            passportDTO = new Passport(user.getLastName(),user.getUserEmail(), user.getUserId(), null);
            return passportDTO;
        }else {
            Employee emp = employeeRepository.findByEmail(username);
            passportDTO = new Passport(emp.getLastName(),emp.getEmail(), emp.getEmployeeId(), null);
            return passportDTO;
        }
    }

    @Override
    public Passport getPassportFromOauth2Login(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttributes().get("email").toString();
        Users tmpUser = usersRepository.findByUserEmail(email);
        Passport passportDTO = null;

        if(tmpUser == null){
            //若users找不到該使用者，則自動新增一個
            String userFirstName= oAuth2User.getAttributes().get("name").toString();
            String userLastName= oAuth2User.getAttributes().get("family_name").toString();
            Users user = new Users(
                    userLastName,          // 姓
                    userFirstName,         // 名
                    email,              // email
                    null,     // 預設密碼
                    null,           // 性别
                    null,            // 出生日期
                    0,              // 購買違規次數
                    new Date(),     // 最後登入時間
                    null    // 用戶狀態
            );
            tmpUser = usersRepository.save(user);
            passportDTO = new Passport(userLastName,email, tmpUser.getUserId(), "ROLE_USER");
            return passportDTO;

        }else{
            //若users找得到該使用者，則直接回傳
            passportDTO = new Passport(tmpUser.getLastName(),tmpUser.getUserEmail(), tmpUser.getUserId(), "ROLE_USER");
            return passportDTO;
        }
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


    @Override
    public void sendCodeForResetPassword(String email) {
        //生成6碼亂數
        Random random = new Random();
        StringBuilder resetCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);
            resetCode.append(digit);
        }
        //email跟驗證碼連結
        emailVerificationCodes.put(email, resetCode.toString());

        mailService.sendPlainText(Collections.singletonList(email), "重設密碼驗證碼", "您的驗證碼為:" + resetCode);
    }

    @Override
    public boolean verifyCodeForResetPassword(String email, String code) {
        if(emailVerificationCodes.containsKey(email) && emailVerificationCodes.get(email).equals(code)){
            emailVerificationCodes.remove(email);
            return true;
        }
        return false;
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        Users user = usersRepository.findByUserEmail(email);
        user.setUserPassword(encodePassword(newPassword));
        usersRepository.save(user);
    }

    @Override
    public void clearVerificationCode(String email) {
        emailVerificationCodes.remove(email);
    }
}
