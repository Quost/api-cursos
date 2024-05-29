package io.github.mqdev.apicursos.modules.user.controllers;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mqdev.apicursos.modules.user.entities.UserEntity;
import io.github.mqdev.apicursos.modules.user.useCases.AuthUserUseCase;
import io.github.mqdev.apicursos.modules.user.useCases.CreateUserUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthUserUseCase authUserUseCase;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody UserEntity userEntity) {
        try{
            var user = createUserUseCase.execute(userEntity);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        
        }
    }

    // o username e password são passados como basic auth no post
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestHeader("Authorization") String authorization) {
        try {
            var base64Credentials = authorization.substring("Basic".length()).trim();
            var credentials = new String(Base64.getDecoder().decode(base64Credentials)).split(":", 2);
            var username = credentials[0];
            var password = credentials[1];
            var authUserResponseDTO = authUserUseCase.execute(username, password);
            return ResponseEntity.ok(authUserResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
