package com.moskovets.light.auth;

import com.moskovets.light.users.SignInRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Начало работы")
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public void begin() {
        authService.begin();
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authService.signIn(request);
    }
}
