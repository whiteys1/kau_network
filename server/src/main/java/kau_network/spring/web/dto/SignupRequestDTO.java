package kau_network.spring.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String address;
}