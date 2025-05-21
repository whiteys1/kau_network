package kau_network.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kau_network.spring.Security.CustomUserDetails;
import kau_network.spring.domain.User;
import kau_network.spring.repository.UserRepository;
import kau_network.spring.service.AuthService;
import kau_network.spring.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@Tag(name = "AUTH", description = "회원 관련 API")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API", description = "회원가입")
    public ResponseEntity<SignupResponseDTO.SignupResponse> signup(
            @RequestBody SignupRequestDTO signupRequestDTO
    ) {
        User user = authService.signup(signupRequestDTO);
        SignupResponseDTO.SignupResponse response = SignupResponseDTO.SignupResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .isRegistered(true)
                .registeredAt(LocalDateTime.now())
                .message("회원가입이 성공적으로 완료되었습니다.")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "로그인")
    public ResponseEntity<LoginResponse> login(
            @Parameter(description = "로그인 정보 입력") @RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        LoginRequest request = new LoginRequest(email, password);
        LoginResult result = authService.login(request);
        LoginResponse response = LoginResponse.builder()
                .accessToken(result.getAccessToken())
                .message("로그인되었습니다.")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @Operation(summary = "정보 조회 API", description = "정보 조회")
    public ResponseEntity<InfoResponse> getMyInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User user = userDetails.getUser();
        InfoResponse response = InfoResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();
        return ResponseEntity.ok(response);
    }
}
