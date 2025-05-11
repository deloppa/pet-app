package com.pet.pet_app.config;

import com.pet.pet_app.model.Role;
import com.pet.pet_app.model.User;
import com.pet.pet_app.repository.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class InitConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void initializeUser(String username, String password,
                               Set<Role> roles) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User user = User.builder()
                            .username(username)
                            .password(passwordEncoder.encode(password))
                            .roles(roles)
                            .build();
            userRepository.save(user);
        }
    }

    @Override
    public void run(String... args) {
        initializeUser("super_admin", "super_admin", Set.of(Role.SUPER_ADMIN));
        initializeUser("admin", "admin", Set.of(Role.ADMIN));
        initializeUser("user", "user", Set.of(Role.USER));
    }
}
