package kau_network.spring.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(name = "refresh token")
    private String refreshToken;

    @Column(nullable = false)
    private String address;

}