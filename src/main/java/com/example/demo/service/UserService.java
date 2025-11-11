package com.example.demo.service;

import com.example.demo.dto.user.LoginRequestDto;
import com.example.demo.dto.user.SignUpRequestDto;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(SignUpRequestDto req) {
        if (userRepository.findByEmail(req.email()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User saved = userRepository.save(new User(
                                            null,
                                            req.email(),
                                            req.password(),
                                            req.name(),
                                            LocalDateTime.now()
                                        ));

        return UserResponse.from(saved);
    }

    public UserResponse login(LoginRequestDto req) {
        User user = userRepository.findByEmail(req.email())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        if (!user.getPassword().equals(req.password())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        return UserResponse.from(user);
    }
}
