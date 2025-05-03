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

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                             .username("admin")
                             .password(passwordEncoder.encode("admin"))
                             .roles(Set.of(Role.ADMIN))
                             .build();
            userRepository.save(admin);
        }
    }
}
