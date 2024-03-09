package com.moskovets.light.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи")
public class UserController {

    private final UserService service;

    @PatchMapping("/set-operator/{userId}")
    @Operation(summary = "Назначить роль OPERATOR")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void setOperatorRole(@PathVariable(name = "userId") Long userId) {
        service.setOperatorRole(userId);
    }
}
