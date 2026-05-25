package kau_network.spring.web.dto;

import kau_network.spring.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResult {
    private User user;
    private String accessToken;
    private String refreshToken;
}