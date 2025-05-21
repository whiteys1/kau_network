package kau_network.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class InfoResponse {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}
