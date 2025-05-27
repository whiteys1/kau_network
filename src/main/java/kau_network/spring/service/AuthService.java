package kau_network.spring.service;

import kau_network.spring.domain.User;
import kau_network.spring.web.dto.LoginRequest;
import kau_network.spring.web.dto.LoginResult;
import kau_network.spring.web.dto.SignupRequestDTO;

public interface AuthService {
    boolean isDuplicated(String email);
    User signup(SignupRequestDTO signupRequest);
    LoginResult login(LoginRequest loginRequest);
    void logout(User user);
//    String reissueAccessToken(String refreshToken);
}
