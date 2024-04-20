package br.com.joaogabriel.springkeycloak.controller;

import br.com.joaogabriel.springkeycloak.model.User;
import br.com.joaogabriel.springkeycloak.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final LoginService<String> loginService;

    public AuthController(LoginService<String> loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody User user) {
        return this.loginService.login(user);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam("refresh_token") String refreshToken) {
        return this.loginService.refreshToken(refreshToken);
    }
}
