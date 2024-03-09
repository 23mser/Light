package com.moskovets.light.users;

import com.moskovets.light.exceptions.AuthException;
import com.moskovets.light.exceptions.UserNotFoundException;
import com.moskovets.light.exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public void save(User user) {
        repository.save(user);
    }

    public void create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new ValidationException("Пользователь с таким именем уже существует.");
        }

        if (repository.existsByPhone(user.getPhone())) {
            throw new ValidationException("Пользователь с таким номером телефона уже существует.");
        }

        save(user);
    }

    public User findUserByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));

    }

    public User findUserById(Long userId) {
        return repository.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));

    }

    public UserDetailsService userDetailsService() {
        return this::findUserByUsername;
    }

    public void setOperatorRole(Long userId) {
        User user = findUserById(userId);
        if (user.getRoles().contains(Role.ROLE_OPERATOR)) {
            throw new AuthException("Пользователь уже имеет роль оператора.");
        }
        user.getRoles().add(Role.ROLE_OPERATOR);
        save(user);
    }

    public void checkExistUserById(Long userId) {
        if (!repository.existsById(userId)) {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }
}
