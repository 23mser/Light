package com.moskovets.light.auth;

import com.moskovets.light.users.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public void begin() {
        List<Role> user1Roles = new ArrayList<>();
        user1Roles.add(Role.ROLE_USER);

        User user1 = User.builder()
                .username("username1")
                .phone("1234567890")
                .password(passwordEncoder.encode("password1"))
                .roles(user1Roles)
                .build();

        userService.create(user1);
        List<Role> user2Roles = new ArrayList<>();
        user2Roles.add(Role.ROLE_OPERATOR);

        User user2 = User.builder()
                .username("username2")
                .phone("0987654321")
                .password(passwordEncoder.encode("password2"))
                .roles(user2Roles)
                .build();

        userService.create(user2);
        List<Role> user3Roles = new ArrayList<>();
        user3Roles.add(Role.ROLE_OPERATOR);
        user3Roles.add(Role.ROLE_ADMIN);

        User user3 = User.builder()
                .username("username3")
                .phone("1234509876")
                .password(passwordEncoder.encode("password3"))
                .roles(user3Roles)
                .build();

        userService.create(user3);
        List<Role> user4Roles = new ArrayList<>();
        user4Roles.add(Role.ROLE_ADMIN);

        User user4 = User.builder()
                .username("username4")
                .phone("0987612345")
                .password(passwordEncoder.encode("password4"))
                .roles(user4Roles)
                .build();

        userService.create(user4);
    }

    public JwtResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        UserDetails user = userService.userDetailsService().loadUserByUsername(request.getUsername());

        String jwt = jwtService.generateToken(user);
        return new JwtResponse(jwt);
    }
}
