package br.com.joaogabriel.springkeycloak.service;

import br.com.joaogabriel.springkeycloak.model.User;
import org.springframework.http.ResponseEntity;

public interface LoginService<T> {

    ResponseEntity<T> login(User user);

    ResponseEntity<T> refreshToken(String refreshToken);
}
