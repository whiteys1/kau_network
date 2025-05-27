package kau_network.spring.service;

import kau_network.spring.Security.JwtTokenProvider;
import kau_network.spring.domain.User;
import kau_network.spring.repository.UserRepository;
import kau_network.spring.web.dto.LoginRequest;
import kau_network.spring.web.dto.LoginResult;
import kau_network.spring.web.dto.SignupRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean isDuplicated(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public User signup(SignupRequestDTO request) {
        if (isDuplicated(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public LoginResult login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createToken(user.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        LoginResult result = new LoginResult(user, accessToken, refreshToken);

        return result;
    }

    public void logout(User user) {
        user.setRefreshToken(null);
        userRepository.save(user);
    }
}
