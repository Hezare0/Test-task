package com.effectiveMobile.testTask.services.servicesAssociatedUser;

import com.effectiveMobile.testTask.Exception.EmailAlreadyExistException;
import com.effectiveMobile.testTask.Exception.EmailNotFoundException;
import com.effectiveMobile.testTask.entity.User;
import com.effectiveMobile.testTask.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return repository.save(user);
    }

    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {

        if (repository.existsByUserEmail(user.getUsername())) {
            throw new EmailAlreadyExistException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    /**
     * Получение пользователя по почте пользователя
     *
     * @return пользователь
     */
    public User getByEmail(String email) {
        return repository.findByUserEmail(email).orElseThrow(() -> new EmailNotFoundException("Пользователь не найден"));

    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(username);
    }


}
