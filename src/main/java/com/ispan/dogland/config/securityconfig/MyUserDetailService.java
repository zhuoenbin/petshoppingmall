package com.ispan.dogland.config.securityconfig;

import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailService implements UserDetailsService {



    private UserRepository userRepository;


    public MyUserDetailService() {
    }

    @Autowired
    public MyUserDetailService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users theUser = userRepository.findByUserEmail(username);

      if(theUser != null){
            String memberEmail = theUser.getUserEmail();
            String memberPassword = theUser.getUserPassword();
            String memberAuthority ="ROLE_USER";
            List<GrantedAuthority> authorities = convertToAuthority(memberAuthority);
            return new User(memberEmail, memberPassword, authorities);

        }else{
            throw new UsernameNotFoundException("Member not found for: " + username);
        }
    }

    private List<GrantedAuthority> convertToAuthority(String memberAuthority){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(memberAuthority));
        return authorities;
    }
}
