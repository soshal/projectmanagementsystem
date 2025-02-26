package com.soshal.service;

import com.soshal.modal.User;
import com.soshal.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomeUserDetailsImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findbyEmail(username);

        if(user ==null){
            throw  new UsernameNotFoundException("User not found with email" + username);

            List<GrantedAuthority> authorities = new ArrayList<>();

            return  new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);

        }







        return null;
    }
}
