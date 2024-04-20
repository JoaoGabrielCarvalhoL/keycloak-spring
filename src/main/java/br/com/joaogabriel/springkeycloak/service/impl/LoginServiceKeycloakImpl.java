package br.com.joaogabriel.springkeycloak.service.impl;

import br.com.joaogabriel.springkeycloak.component.HttpComponent;
import br.com.joaogabriel.springkeycloak.model.User;
import br.com.joaogabriel.springkeycloak.service.LoginService;
import br.com.joaogabriel.springkeycloak.utils.HttpParamsMapBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class LoginServiceKeycloakImpl implements LoginService<String> {

    @Value("${keycloak.auth-server-url}")
    private  String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.user-login.grant-type}")
    private String grantType;

    private final HttpComponent httpComponent;

    public LoginServiceKeycloakImpl(HttpComponent httpComponent) {
        this.httpComponent = httpComponent;
    }

    @Override
    public ResponseEntity<String> login(User user) {
        httpComponent.httpHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = HttpParamsMapBuilder.builder()
                .withClient(clientId)
                .withClientSecret(clientSecret)
                .withGrantType(grantType)
                .withUsername(user.getUsername())
                .withPassword(user.getPassword())
                .build();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpComponent.httpHeaders());
        try {
            ResponseEntity<String> response =
                    httpComponent
                            .restTemplate()
                            .postForEntity(keycloakServerUrl + "/protocol/openid-connect/token", request, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException exception) {
            return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> refreshToken(String refreshToken) {
        httpComponent.httpHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = HttpParamsMapBuilder.builder()
                .withClient(clientId)
                .withClientSecret(clientSecret)
                .withGrantType("refresh_token")
                .withRefreshToken(refreshToken)
                .build();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpComponent.httpHeaders());

        try {
            ResponseEntity<String> response =
                    httpComponent
                            .restTemplate()
                            .postForEntity(keycloakServerUrl + "/protocol/openid-connect/token", request, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException exception) {
            return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
        }
    }
}
