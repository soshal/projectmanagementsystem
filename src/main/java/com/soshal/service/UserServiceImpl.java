package com.soshal.service;



import com.soshal.config.JwtProvider;
import com.soshal.modal.User;

import com.soshal.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private JwtProvider jwtUtil;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = jwtUtil.extractEmail(jwt);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        return userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public User updateUsersProjectSize(User user, int number) {
        user.setProjectSize(user.getProjectSize() + number);
        return userRepository.save(user);
    }
}
