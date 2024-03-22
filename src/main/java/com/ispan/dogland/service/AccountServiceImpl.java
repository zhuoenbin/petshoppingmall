package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.EmployeeRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.service.interfaceFile.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public Passport loginCheck(String email, String password) {
        Users tmpUser = usersRepository.findByUserEmail(email);
        Employee tmpEmp = employeeRepository.findByEmail(email);
        if(tmpUser != null){
            if(passwordEncoder.matches(password, tmpUser.getUserPassword())){
                tmpUser.setLastLoginTime(new Date());
                usersRepository.save(tmpUser);
                return new Passport(tmpUser.getLastName(),tmpUser.getUserEmail(), tmpUser.getUserId(), tmpUser.getUserStatus());
            }
        }
        if(tmpEmp != null){
            if(passwordEncoder.matches(password,tmpEmp.getPassword())){
                return new Passport(tmpEmp.getLastName(),tmpEmp.getEmail(), tmpEmp.getEmployeeId(), tmpEmp.getDbAuthority());
            }
        }
        System.out.println("4");
        return null;
    }

    @Override
    public boolean checkEmailIsEmpty(String email) {
        // 檢查資料庫中是否已經存在相同的 email
        Users tmpUser = usersRepository.findByUserEmail(email);
        Employee tmpEmp = employeeRepository.findByEmail(email);
        if(tmpUser != null || tmpEmp != null){
            return false;
        }
        return true;
    }
    @Override
    public Users register(Users user){
        if(user.getUserPassword() !=null){
            String encodePassword = encodePassword(user.getUserPassword());
            user.setUserPassword(encodePassword);
            user.setUserViolationCount(0);
            user.setUserStatus("ACTIVE");
        }
        user.setLastLoginTime(new Date());
        return usersRepository.save(user);
    }

    @Override
    public Users getUserDetail(String email) {
        return usersRepository.findByUserEmail(email);
    }


    @Override
    public Passport getPassportFromFormLogin(String username) {
        Users user = usersRepository.findByUserEmail(username);
        Passport passportDTO = null;
        if(user != null){
            user.setLastLoginTime(new Date());
            usersRepository.save(user);
            passportDTO = new Passport(user.getLastName(),user.getUserEmail(), user.getUserId(), user.getUserStatus());
            return passportDTO;
        }else {
            Employee emp = employeeRepository.findByEmail(username);
            passportDTO = new Passport(emp.getLastName(),emp.getEmail(), emp.getEmployeeId(), emp.getDbAuthority());
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
