package io.github.mqdev.apicursos.modules.user.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.mqdev.apicursos.exceptions.UserAlreadyExistsException;
import io.github.mqdev.apicursos.modules.user.entities.UserEntity;
import io.github.mqdev.apicursos.modules.user.repositories.UserRepository;

public class CreateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Object execute(UserEntity userEntity) {
        this.userRepository.findByUsername(userEntity.getUsername())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException();
                });

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return this.userRepository.save(userEntity);
    }

}
